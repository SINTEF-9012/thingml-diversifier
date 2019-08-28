package no.sintef.thingml.diversifier.strategies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.constraints.Types;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.ActionBlock;
import org.thingml.xtext.thingML.AndExpression;
import org.thingml.xtext.thingML.BooleanLiteral;
import org.thingml.xtext.thingML.ConditionalAction;
import org.thingml.xtext.thingML.EventReference;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.ExpressionGroup;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.FunctionCallExpression;
import org.thingml.xtext.thingML.Handler;
import org.thingml.xtext.thingML.IntegerLiteral;
import org.thingml.xtext.thingML.InternalTransition;
import org.thingml.xtext.thingML.LowerExpression;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.NotExpression;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.PrimitiveType;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.PropertyReference;
import org.thingml.xtext.thingML.ReceiveMessage;
import org.thingml.xtext.thingML.SendAction;
import org.thingml.xtext.thingML.State;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLFactory;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.thingML.Transition;
import org.thingml.xtext.thingML.TypeRef;
import org.thingml.xtext.thingML.VariableAssignment;

import no.sintef.thingml.diversifier.Manager;
import no.sintef.thingml.diversifier.Mode;

public class SplitMessagesInline extends Strategy {

	final Map<String, List<Message>> duplicates = new HashMap<>();
	final Map<Thing, List<Message>> msgsToRemove = new HashMap<>();
	final Map<State, List<Handler>> internalsToRemove = new HashMap<>();
	final Map<State, List<Handler>> transitionsToRemove = new HashMap<>();
	
	final Map<InternalTransition, Property> recProps = new HashMap<>();
	final Map<Property, PropertyReference> recPropRefs = new HashMap<>();
	
	final Map<State, Map<String, Property>> props = new HashMap<State, Map<String,Property>>();
	
	//TODO: apply same strategy as for DuplicateMessage regarding how we browser the model and how we protect messages from STL

	@Override
	protected void doApply(ThingMLModel model) {
		//We need two passes, as the messages may be defined in one thing and then included in other things using them in ports

		//PASS 1: Split all messages
		final TreeIterator<EObject> it = model.eAllContents();
		while (it.hasNext()) {
			final EObject o = it.next();
			if (o instanceof Thing) {
				final Thing t = (Thing) o;
				if (AnnotatedElementHelper.hasFlag(t, "stl")) continue;
				//if (!Manager.diversify(t)) return;
				final List<Message> msgs = new ArrayList<>();
				msgs.addAll(t.getMessages());
				for (Message msg : msgs) {				
					if (!Manager.diversify(msg) || msg.getParameters().size() == 0) continue;	
					int splitAt = Manager.rnd.nextInt(msg.getParameters().size());
					System.out.println("Splitting message " + msg.getName() + " at index " + splitAt + "...");
					final Message first = createMessage((Thing) msg.eContainer(), msg, msg.getParameters().subList(0, splitAt));
					final Message second = createMessage((Thing) msg.eContainer(), msg, msg.getParameters().subList(splitAt, msg.getParameters().size()));
					t.getMessages().add(first);
					t.getMessages().add(second);
					final List<Message> messages = new ArrayList<>();
					messages.add(first);
					messages.add(second);
					duplicates.put(t.getName()+msg.getName(), messages);
					List<Message> toRemove = msgsToRemove.get(t);
					if (toRemove == null) {
						toRemove = new ArrayList<Message>();
						msgsToRemove.put(t, toRemove);
					}
					toRemove.add(msg);
				}
			}
		}

		//PASS 2: Update ports
		final TreeIterator<EObject> it2 = model.eAllContents();
		while (it2.hasNext()) {
			final EObject o = it2.next();
			if (o instanceof Thing) {
				final Thing t = (Thing) o;
				if (AnnotatedElementHelper.hasFlag(t, "stl")) continue;
				//final List<Port> ports = new ArrayList<>();
				//ports.addAll(t.getPorts());
				for (Port port : ThingMLHelpers.allPorts(t)) {
					if (!Manager.diversify(port)) continue;
					
					final List<Message> sent = new ArrayList<>();
					sent.addAll(port.getSends());
					for(Message msg : sent) {
						final Thing root = ThingMLHelpers.findContainingThing(msg);
						if (AnnotatedElementHelper.hasFlag(root, "stl")) continue;				
						if (!Manager.diversify(msg) || msg.getParameters().size() == 0) continue;
						List<Message> messages = duplicates.get(root.getName()+msg.getName());
						if (messages == null) continue;
						final Message first = messages.get(0);
						final Message second = messages.get(1);
						System.out.println("adding messages " + first.getName() + " and " + second.getName() + " to sent messages of port " + port.getName() + " of thing " + t.getName());						
						port.getSends().add(first);
						port.getSends().add(second);
						port.getSends().remove(msg);
					}

					final List<Message> received = new ArrayList<>();
					received.addAll(port.getReceives());
					for(Message msg : received) {
						final Thing root = ThingMLHelpers.findContainingThing(msg);
						if (AnnotatedElementHelper.hasFlag(root, "stl")) continue;				
						if (!Manager.diversify(msg) || msg.getParameters().size() == 0) continue;
						List<Message> messages = duplicates.get(root.getName()+msg.getName());
						if (messages == null) continue;

						final Message first = messages.get(0);
						final Message second = messages.get(1);
						System.out.println("adding messages " + first.getName() + " and " + second.getName() + " to received messages of port " + port.getName() + " of thing " + t.getName());
						port.getReceives().add(first);
						port.getReceives().add(second);
						port.getReceives().remove(msg);
					}
				}
			}
		}
		
		final TreeIterator<EObject> it4 = model.eAllContents();
		while (it4.hasNext()) {
			final EObject o = it4.next();
			if (o instanceof Handler) {
				final Handler h = (Handler) o;
				if (h.getEvent() == null || !(h.getEvent() instanceof ReceiveMessage)) continue;
				if (AnnotatedElementHelper.hasFlag(ThingMLHelpers.findContainingThing(h), "stl")) continue;				
				final ReceiveMessage rm = (ReceiveMessage) h.getEvent();
				final Thing root = ThingMLHelpers.findContainingThing(rm.getMessage());
				if (!Manager.diversify(rm.getMessage()) || rm.getMessage().getParameters().size() == 0) continue;
				if (AnnotatedElementHelper.hasFlag(root, "stl")) continue;
				System.out.println("Updating handler " + ((ReceiveMessage)h.getEvent()).getPort().getName() + "?" + ((ReceiveMessage)h.getEvent()).getMessage().getName());
				if (h instanceof InternalTransition) {
					final InternalTransition t = (InternalTransition) h;
					updateHandlers(root, t);
				} else if (h instanceof Transition) {
					final Transition t = (Transition) h;
					updateHandlers(root, t);
				}				
			}
		}
		
		final TreeIterator<EObject> it3 = model.eAllContents();
		while (it3.hasNext()) {
			final EObject o = it3.next();
			if (!(o instanceof SendAction)) continue;
			final SendAction sa = (SendAction) o;
			if (!Manager.diversify(sa.getMessage()) || sa.getMessage().getParameters().size() == 0) continue;
			splitSendAction(sa);			
		}
		
		//Cleanup
		for(Entry<Thing, List<Message>> e : msgsToRemove.entrySet()) {
			e.getKey().getMessages().removeAll(e.getValue());
		}
		
		for(Entry<State, List<Handler>> e : internalsToRemove.entrySet()) {
			e.getKey().getInternal().removeAll(e.getValue());
		}
		
		for(Entry<State, List<Handler>> e : transitionsToRemove.entrySet()) {
			e.getKey().getOutgoing().removeAll(e.getValue());
		}
		
	}

	private Message createMessage(Thing t, Message m, List<Parameter> params) {
		final Message msg = ThingMLFactory.eINSTANCE.createMessage();
		String name = m.getName();
		if (params.size() == 0)
			name = name + "_";
		for (Parameter p : params) {
			name = name + p.getName();
			msg.getParameters().add(EcoreUtil.copy(p));
		}
		msg.setName(name);
		System.out.println("Creating new message " + msg.getName());
		t.getMessages().add(msg);
		return msg;
	}

	private void splitSendAction(SendAction sa) {
		final Thing root = ThingMLHelpers.findContainingThing(sa.getMessage());
		if (root==null) return;
		final List<Message> splits = duplicates.get(root.getName()+sa.getMessage().getName());
		if (splits == null || splits.size() != 2) return;
		
		final Message m1 = splits.get(0);
		final Message m2 = splits.get(1);
				
		final SendAction send1 = ThingMLFactory.eINSTANCE.createSendAction();
		send1.setMessage(m1);
		send1.setPort(sa.getPort());
		for (Expression e : sa.getParameters().subList(0, m1.getParameters().size())) {
			send1.getParameters().add(EcoreUtil.copy(e));
		}

		final SendAction send2 = ThingMLFactory.eINSTANCE.createSendAction();
		send2.setMessage(m2);
		send2.setPort(sa.getPort());
		for (Expression e : sa.getParameters().subList(m1.getParameters().size(), sa.getParameters().size())) {
			send2.getParameters().add(EcoreUtil.copy(e));
		}

		final Function rnd = Manager.findRandom(ThingMLHelpers.findContainingThing(sa));			
		if (rnd == null || Manager.mode==Mode.STATIC) {
			final ActionBlock b1 = ThingMLFactory.eINSTANCE.createActionBlock();
			if (Manager.rnd.nextBoolean()) {
				b1.getActions().add(send1);
				b1.getActions().add(send2);
			} else {
				b1.getActions().add(send2);
				b1.getActions().add(send1);
			}

			final Object parent = sa.eContainer().eGet(sa.eContainingFeature());
			if (parent instanceof EList) {
				EList list = (EList) parent;
				final int index = list.indexOf(sa);
				list.add(index, b1);
				list.remove(sa);
			} else {
				final EObject o = sa.eContainer();
				o.eSet(sa.eContainingFeature(), b1);
			}
		} else {
			final ConditionalAction ca = ThingMLFactory.eINSTANCE.createConditionalAction();
			final LowerExpression lower = ThingMLFactory.eINSTANCE.createLowerExpression();
			final FunctionCallExpression call = ThingMLFactory.eINSTANCE.createFunctionCallExpression();
			call.setFunction(rnd);
			final IntegerLiteral threshold = ThingMLFactory.eINSTANCE.createIntegerLiteral();
			threshold.setIntValue(Manager.rnd.nextInt(256));
			lower.setLhs(call);
			lower.setRhs(threshold);
			ca.setCondition(lower);

			final ActionBlock b1 = ThingMLFactory.eINSTANCE.createActionBlock();
			b1.getActions().add(send1);
			b1.getActions().add(send2);
			ca.setAction(b1);
			final ActionBlock b2 = ThingMLFactory.eINSTANCE.createActionBlock();
			b2.getActions().add(EcoreUtil.copy(send2));
			b2.getActions().add(EcoreUtil.copy(send1));
			ca.setElseAction(b2);

			final Object parent = sa.eContainer().eGet(sa.eContainingFeature());
			if (parent instanceof EList) {
				EList list = (EList) parent;
				final int index = list.indexOf(sa);
				list.add(index, ca);
				list.remove(sa);
			} else {
				final EObject o = sa.eContainer();
				o.eSet(sa.eContainingFeature(), ca);
			}
		}
	}   
	
	private InternalTransition createHandler(Message m1, ReceiveMessage rm, Handler t, State source) {		
		
		Property prop1 = null;
		PropertyReference pr1 = null;
		for(Property p : source.getProperties()) {
			if (p.getName().equals("received_" + rm.getPort().getName() + "_" + m1.getName())) {
				prop1 = p;
				pr1 = recPropRefs.get(prop1);
				break;
			}
		}
				
		if (prop1 == null) {
			final PrimitiveType bool = Helper.getPrimitiveType(Types.BOOLEAN_TYPEREF, ThingMLHelpers.findContainingThing(source));				
			System.out.println("Creating new property received_" + rm.getPort().getName() + "_" + m1.getName() + " in state " + source.getName());
			prop1 = ThingMLFactory.eINSTANCE.createProperty();
			prop1.setReadonly(false);
			prop1.setName("received_" + rm.getPort().getName() + "_" + m1.getName());
			final TypeRef tr1 = ThingMLFactory.eINSTANCE.createTypeRef();
			tr1.setType(bool);
			prop1.setTypeRef(tr1);
			source.getProperties().add(prop1);
			pr1 = ThingMLFactory.eINSTANCE.createPropertyReference();
			pr1.setProperty(prop1);
		}
		
		for(InternalTransition it : source.getInternal()) {
			if (it.getEvent() != null) {
				final ReceiveMessage rec = (ReceiveMessage)it.getEvent();
				if (EcoreUtil.equals(rec.getPort(), rm.getPort()) && EcoreUtil.equals(rec.getMessage(), m1)) {
					System.out.println("NOT creating new internal transtion in " + source.getName() + " on " + rm.getPort().getName() + "?" + m1.getName());
					return it;
				}
			}
		}
		
		System.out.println("Creating new internal transtion in " + source.getName() + " on " + rm.getPort().getName() + "?" + m1.getName());		
		final InternalTransition t1 = ThingMLFactory.eINSTANCE.createInternalTransition();
		final ReceiveMessage rm1 = ThingMLFactory.eINSTANCE.createReceiveMessage();//EcoreUtil.copy(rm);
		rm1.setMessage(m1);
		rm1.setPort(rm.getPort());
		if (rm.getName() != null)
			rm1.setName(rm.getName());
		t1.setEvent(rm1);
		final ActionBlock b1 = ThingMLFactory.eINSTANCE.createActionBlock();
		//Mark message has received
		final BooleanLiteral true1 = ThingMLFactory.eINSTANCE.createBooleanLiteral();
		true1.setBoolValue(true);
		final VariableAssignment va1 = ThingMLFactory.eINSTANCE.createVariableAssignment();
		va1.setProperty(prop1);
		va1.setExpression(true1);
		b1.getActions().add(va1);
		//Save params
		if (rm.getName() != null) {
			for (Parameter p : m1.getParameters()) {			
				Property prop = null;
				for(Property pr : source.getProperties()) {
					if (pr.getName().equals(rm.getPort().getName() + "_" + rm.getMessage().getName() + "_" + p.getName())) {
						prop = pr;
						break;
					}
				}
				final VariableAssignment va = ThingMLFactory.eINSTANCE.createVariableAssignment();
				va.setProperty(prop);
				final EventReference ref = ThingMLFactory.eINSTANCE.createEventReference();
				ref.setReceiveMsg(rm1);
				ref.setParameter(p);
				va.setExpression(ref);
				b1.getActions().add(va);
			}
		}
		t1.setAction(b1);
		source.getInternal().add(t1);
		
		recProps.put(t1, prop1);
		recPropRefs.put(prop1, pr1);
		
		return t1;
	}
	
	private void finalizeHandler(InternalTransition t1, InternalTransition t, Property prop1, Property prop2, PropertyReference pr) {		
		
		final BooleanLiteral _false = ThingMLFactory.eINSTANCE.createBooleanLiteral();
		_false.setBoolValue(false);
		
		final ConditionalAction if1 = ThingMLFactory.eINSTANCE.createConditionalAction();
		if (t.getGuard() != null) {
			final AndExpression and = ThingMLFactory.eINSTANCE.createAndExpression();
			and.setLhs(EcoreUtil.copy(pr));
			final ExpressionGroup group = ThingMLFactory.eINSTANCE.createExpressionGroup();
			group.setTerm(replaceEventRefByPropRef(EcoreUtil.copy(t.getGuard()), props.get(t.eContainer())));
			and.setRhs(group);			
			if1.setCondition(and);
		} else {	
			if1.setCondition(EcoreUtil.copy(pr));
		}
		final ActionBlock b1 = ThingMLFactory.eINSTANCE.createActionBlock();
		final VariableAssignment reset1 = ThingMLFactory.eINSTANCE.createVariableAssignment();
		reset1.setProperty(prop1);
		reset1.setExpression(EcoreUtil.copy(_false));
		final VariableAssignment reset2 = ThingMLFactory.eINSTANCE.createVariableAssignment();
		reset2.setProperty(prop2);
		reset2.setExpression(EcoreUtil.copy(_false));
		b1.getActions().add(reset1);
		b1.getActions().add(reset2);
		final ActionBlock b2 = ThingMLFactory.eINSTANCE.createActionBlock();
		b2.getActions().add(replaceEventRefByPropRef(EcoreUtil.copy(t.getAction()), props.get(t.eContainer())));
		b2.getActions().add(b1);
		if1.setAction(b2);		
		((ActionBlock)t1.getAction()).getActions().add(if1);
	}
	
	private void initParams(Message m1, Message m2, State source, ReceiveMessage rm) {
		final List<Parameter> params = new ArrayList<>();
		params.addAll(m1.getParameters());
		params.addAll(m2.getParameters());
		for (Parameter param : params) {			
			final String pname = rm.getPort().getName() + "_" + rm.getMessage().getName() + "_" + param.getName();
			if (props.get(source) == null) {
				final Map<String, Property> map = new HashMap<String, Property>();
				props.put(source, map);
			}
			if (props.get(source).get(pname) != null) continue;
			System.out.println("Creating new property " + pname + " in state " + source.getName());
			final Property prop = ThingMLFactory.eINSTANCE.createProperty();
			prop.setReadonly(false);
			prop.setName(pname);
			prop.setTypeRef(EcoreUtil.copy(param.getTypeRef()));
			source.getProperties().add(prop);
			props.get(source).put(pname, prop);
		}
	}
	
	private void updateHandlers(Thing root, InternalTransition t) {
		final ReceiveMessage rm = (ReceiveMessage)t.getEvent();
		final State source = (State)t.eContainer();
		final Message m1 = duplicates.get(root.getName() + rm.getMessage().getName()).get(0);
		final Message m2 = duplicates.get(root.getName() + rm.getMessage().getName()).get(1);						
		
		initParams(m1, m2, source, rm);
		
		final InternalTransition t1 = createHandler(m1, rm, t, source);
		final InternalTransition t2 = createHandler(m2, rm, t, source);
		
		if (t1 == null || t2 == null) return;
		
		final Property prop1 = recProps.get(t1);
		final Property prop2 = recProps.get(t2);
		final PropertyReference pr1 = recPropRefs.get(prop1);
		final PropertyReference pr2 = recPropRefs.get(prop2);
		
		finalizeHandler(t1, t, prop1, prop2, pr2);
		finalizeHandler(t2, t, prop2, prop1, pr1);
		
		List<Handler> toRemove = internalsToRemove.get(source);
		if (toRemove == null) {
			toRemove = new ArrayList<Handler>();
			internalsToRemove.put(source, toRemove);
		}
		toRemove.add(t);
	}
	
	private void updateHandlers(Thing root, Transition t) {
		System.out.println("Splitting transition " + ((State)t.eContainer()).getName() + " --" + ((ReceiveMessage)t.getEvent()).getPort().getName() + "?" + ((ReceiveMessage)t.getEvent()).getMessage().getName() + "--> " + t.getTarget().getName());
		final ReceiveMessage rm = (ReceiveMessage)t.getEvent();
		final State source = (State)t.eContainer();		
		
		final Message m1 = duplicates.get(root.getName() + rm.getMessage().getName()).get(0);
		final Message m2 = duplicates.get(root.getName() + rm.getMessage().getName()).get(1);
		
		initParams(m1, m2, source, rm);						
		
		final InternalTransition it1 = createHandler(m1, rm, t, source);
		final InternalTransition it2 = createHandler(m2, rm, t, source);
		
		final Property prop1 = recProps.get(it1);
		final Property prop2 = recProps.get(it2);
		final PropertyReference pr1 = recPropRefs.get(prop1);
		final PropertyReference pr2 = recPropRefs.get(prop2);
		
		if (it1 != null && it2 != null) {
			final NotExpression n1 = ThingMLFactory.eINSTANCE.createNotExpression();
			n1.setTerm(EcoreUtil.copy(pr2));
			it1.setGuard(n1);
		
			final NotExpression n2 = ThingMLFactory.eINSTANCE.createNotExpression();
			n2.setTerm(EcoreUtil.copy(pr1));
			it2.setGuard(n2);	
		}
		
		createTransition(m1, m2, rm, t, source, prop1, prop2, pr2);
		createTransition(m2, m1, rm, t, source, prop2, prop1, pr1);
		
		List<Handler> toRemove = transitionsToRemove.get(source);
		if (toRemove == null) {
			toRemove = new ArrayList<Handler>();
			transitionsToRemove.put(source, toRemove);
		}
		toRemove.add(t);
	}
	
	private void createTransition(Message m1, Message m2, ReceiveMessage rm, Transition t, State source, Property prop1, Property prop2, PropertyReference pr) {		
		System.out.println("Creating new transtion " + source.getName() + " --" + rm.getPort().getName() + "?" + m1.getName() + "--> " + t.getTarget().getName());
		final Transition t1 = ThingMLFactory.eINSTANCE.createTransition();
		final ReceiveMessage rm1 = ThingMLFactory.eINSTANCE.createReceiveMessage();//EcoreUtil.copy(rm);
		rm1.setMessage(m1);
		rm1.setPort(rm.getPort());
		if (rm.getName() != null)
			rm1.setName(rm.getName());
		t1.setEvent(rm1);
		t1.setTarget(t.getTarget());
		
		Map<String, Property> props2 = new HashMap<>();
		for(Parameter p : m2.getParameters()) {			
			final String pname = rm.getPort().getName() + "_" + rm.getMessage().getName() + "_" + p.getName();
			props2.put(pname, props.get(source).get(pname));
		}		
		
		if (t.getGuard() != null) {
			final Expression guard = updateEventRef(replaceEventRefByPropRef(EcoreUtil.copy(t.getGuard()), props2), rm1);	
			//final Expression guard = replaceEventRefByPropRef(updateEventRef(EcoreUtil.copy(t.getGuard()), m1, rm1), props2);
			final AndExpression and = ThingMLFactory.eINSTANCE.createAndExpression();
			and.setLhs(EcoreUtil.copy(pr));
			final ExpressionGroup group = ThingMLFactory.eINSTANCE.createExpressionGroup();
			group.setTerm(guard);
			and.setRhs(group);						
			t1.setGuard(and);
		} else {	
			t1.setGuard(EcoreUtil.copy(pr));
		}
		
		final ActionBlock b1 = ThingMLFactory.eINSTANCE.createActionBlock();
		//Save params
		if (rm.getName() != null) {
			for (Parameter p : m1.getParameters()) {			
				Property prop = null;
				for(Property pro : source.getProperties()) {
					if (pro.getName().equals(rm.getPort().getName() + "_" + rm.getMessage().getName() + "_" + p.getName())) {
						prop = pro;
						break;
					}
				}
				final VariableAssignment va = ThingMLFactory.eINSTANCE.createVariableAssignment();
				va.setProperty(prop);
				final EventReference ref = ThingMLFactory.eINSTANCE.createEventReference();
				ref.setReceiveMsg(rm1);
				ref.setParameter(p);
				va.setExpression(ref);
				b1.getActions().add(va);
			}
		}
		
		if (t.getAction() != null) {
			//b1.getActions().add(updateEventRef(replaceEventRefByPropRef(EcoreUtil.copy(t.getAction()), props), m1, rm1));
			b1.getActions().add(replaceEventRefByPropRef(updateEventRef(EcoreUtil.copy(t.getAction()), rm1), props.get(source)));
		}
		
		final BooleanLiteral _false = ThingMLFactory.eINSTANCE.createBooleanLiteral();
		_false.setBoolValue(false);
				
		final VariableAssignment reset1 = ThingMLFactory.eINSTANCE.createVariableAssignment();
		reset1.setProperty(prop1);
		reset1.setExpression(EcoreUtil.copy(_false));
		final VariableAssignment reset2 = ThingMLFactory.eINSTANCE.createVariableAssignment();
		reset2.setProperty(prop2);
		reset2.setExpression(EcoreUtil.copy(_false));
		b1.getActions().add(reset1);
		b1.getActions().add(reset2);
		
		t1.setAction(b1);
		source.getOutgoing().add(t1);		
	}
	
	private <T extends EObject> T updateEventRef(T e, ReceiveMessage rm) {
		final TreeIterator<EObject> it = e.eAllContents();
		while (it.hasNext()) {
			final EObject o = it.next();
			if (o instanceof EventReference) {	
				final EventReference er = (EventReference) o;
				Parameter param = null;
				for(Parameter p : rm.getMessage().getParameters()) {
					if (EcoreUtil.equals(((ReceiveMessage)er.getReceiveMsg()).getPort(), rm.getPort())
							&& rm.getMessage().getName().startsWith(((ReceiveMessage) er.getReceiveMsg()).getMessage().getName())
							&& p.getName().equals(er.getParameter().getName())) 
					{
						param = p;
						break;
					}
				}
				if (param != null) {
					final EventReference er1 = EcoreUtil.copy(er);
					System.out.println("Updating event reference " + ((ReceiveMessage)er.getReceiveMsg()).getPort().getName() + "?" + ((ReceiveMessage)er.getReceiveMsg()).getMessage().getName() + "." + er.getParameter().getName()
							+ " to "  + rm.getPort().getName() + "?" + rm.getMessage().getName() + "." + param.getName());
					er1.setParameter(param);
					er1.setReceiveMsg(rm);
					
					final Object ref = er.eContainer().eGet(er.eContainingFeature());
					if (ref instanceof EList) {
						((EList)ref).add(((EList)ref).indexOf(er), er1);
						((EList)ref).remove(er);
					} else {
						er.eContainer().eSet(er.eContainingFeature(), er1);
					}
				}
			}
		}
		return e;
	}
	
	private <T extends EObject> T replaceEventRefByPropRef(T e, Map<String, Property> props) {
		final TreeIterator<EObject> it = e.eAllContents();
		while (it.hasNext()) {
			final EObject o = it.next();
			if (o instanceof EventReference) {				
				final EventReference er = (EventReference) o;		
				final ReceiveMessage rm = (ReceiveMessage)er.getReceiveMsg();
				final Property p = props.get(rm.getPort().getName() + "_" + rm.getMessage().getName() + "_" + er.getParameter().getName());																
				if (p == null) continue;
				System.out.println("Updating event reference " + ((ReceiveMessage)er.getReceiveMsg()).getPort().getName() + "?" + ((ReceiveMessage)er.getReceiveMsg()).getMessage().getName() + "." + er.getParameter().getName()
						+ " to property reference to " + p.getName());
				final PropertyReference pr = ThingMLFactory.eINSTANCE.createPropertyReference();
				pr.setProperty(p);
				final Object ref = er.eContainer().eGet(er.eContainingFeature());
				if (ref instanceof EList) {
					((EList)ref).add(((EList)ref).indexOf(er), pr);
					((EList)ref).remove(er);
				} else {
					er.eContainer().eSet(er.eContainingFeature(), pr);
				}
			}
		}
		return e;
	}

}
