package no.sintef.thingml.diversifier.strategies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.constraints.Types;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.ActionBlock;
import org.thingml.xtext.thingML.BooleanLiteral;
import org.thingml.xtext.thingML.ConditionalAction;
import org.thingml.xtext.thingML.EventReference;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.FunctionCallExpression;
import org.thingml.xtext.thingML.Handler;
import org.thingml.xtext.thingML.IntegerLiteral;
import org.thingml.xtext.thingML.InternalTransition;
import org.thingml.xtext.thingML.LowerExpression;
import org.thingml.xtext.thingML.Message;
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

public class SplitMessagesInline2 extends Strategy {

	final Map<String, List<Message>> duplicates = new HashMap<>();
	
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
						if (!Manager.diversify(msg)) continue;
						List<Message> messages = duplicates.get(root.getName()+msg.getName());
						if (messages == null) continue;
						//System.out.println(" adding messages " + first.getName() + " and " + second.getName() + " to sent messages of port " + port.getName() + " of thing " + thing.getName());
						final Message first = messages.get(0);
						final Message second = messages.get(1);
						port.getSends().add(first);
						port.getSends().add(second);
						//System.out.println(" removing message " + m.getName() + " from sent messages of port " + port.getName() + " of thing " + thing.getName());
						//port.getSends().remove(m);
					}

					final List<Message> received = new ArrayList<>();
					received.addAll(port.getReceives());
					for(Message msg : received) {
						final Thing root = ThingMLHelpers.findContainingThing(msg);
						if (AnnotatedElementHelper.hasFlag(root, "stl")) continue;				
						if (!Manager.diversify(msg)) continue;
						List<Message> messages = duplicates.get(root.getName()+msg.getName());
						if (messages == null) continue;

						final Message first = messages.get(0);
						final Message second = messages.get(1);

						port.getReceives().add(first);
						port.getReceives().add(second);

						updateHandlers(t, port, msg);
					}
				}				
			}
		}
		
		final TreeIterator<EObject> it3 = model.eAllContents();
		while (it3.hasNext()) {
			final EObject o = it3.next();
			if (!(o instanceof SendAction)) continue;
			final SendAction sa = (SendAction) o;
			splitSendAction(sa);
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
			
			/*sa.getPort().getSends().remove(sa.getMessage());
			root.getMessages().remove(sa.getMessage());*/

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

	private void updateHandlers(Thing thing, Port p, Message m) {
		final Thing root = ThingMLHelpers.findContainingThing(m);
		if (AnnotatedElementHelper.hasFlag(root, "stl")) return;
		if (AnnotatedElementHelper.hasFlag(thing, "stl")) return;
		
		final TreeIterator<EObject> it = thing.eAllContents();
		while (it.hasNext()) {
			final EObject o = it.next();
			if (o instanceof Handler) {
				final Handler h = (Handler) o;
				if (h.getEvent() == null || !(h.getEvent() instanceof ReceiveMessage)) continue;
				final ReceiveMessage rm = (ReceiveMessage) h.getEvent();
				if (!EcoreUtil.equals(rm.getMessage(), m) || !EcoreUtil.equals(rm.getPort(), p)) continue;
								
				if (h instanceof InternalTransition) {
					final InternalTransition t = (InternalTransition) h;
					updateHandlers(root, t);
				} else if (h instanceof Transition) {
					final Transition t = (Transition) h;
					updateHandlers(root, t);
				}
				
			}
		}
	}   
	
	private Handler createHandler(Message m1, ReceiveMessage rm, InternalTransition t, State source, Property prop1, Property prop2, Map<String, Property> props) {
		for (Parameter param : m1.getParameters()) {
			final Property prop = ThingMLFactory.eINSTANCE.createProperty();
			prop.setReadonly(false);
			prop.setName(rm.getPort().getName() + "_" + rm.getMessage().getName() + "_" + param.getName());
			prop.setTypeRef(EcoreUtil.copy(param.getTypeRef()));
			source.getProperties().add(prop);
			props.put(rm.getPort().getName() + "_" + rm.getMessage().getName() + "_" + param.getName(), prop);
		}
		
		final InternalTransition t1 = ThingMLFactory.eINSTANCE.createInternalTransition();
		final ReceiveMessage rm1 = ThingMLFactory.eINSTANCE.createReceiveMessage();
		rm1.setMessage(m1);
		rm1.setPort(rm.getPort());
		rm1.setName("ev");
		t1.setEvent(rm1);
		if (t.getGuard()!=null) t1.setGuard(EcoreUtil.copy(t.getGuard()));
		final ActionBlock b1 = ThingMLFactory.eINSTANCE.createActionBlock();
		//Mark message has received
		final BooleanLiteral true1 = ThingMLFactory.eINSTANCE.createBooleanLiteral();
		true1.setBoolValue(true);
		final VariableAssignment va1 = ThingMLFactory.eINSTANCE.createVariableAssignment();
		va1.setProperty(prop1);
		va1.setExpression(true1);
		b1.getActions().add(va1);
		//Save params
		for (Parameter p : rm.getMessage().getParameters()) {			
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
			ref.setReceiveMsg(rm);
			ref.setParameter(p);
			va.setExpression(ref);
			b1.getActions().add(va);
		}
		t1.setAction(b1);
		source.getInternal().add(t1);
		
		return t1;
	}
	
	private void updateHandlers(Thing root, InternalTransition t) {
		final ReceiveMessage rm = (ReceiveMessage)t.getEvent();
		final State source = (State)t.eContainer();
		final Message m1 = duplicates.get(root.getName() + rm.getMessage().getName()).get(0);
		final Message m2 = duplicates.get(root.getName() + rm.getMessage().getName()).get(1);
		
		PrimitiveType bool = Helper.getPrimitiveType(Types.BOOLEAN_TYPE, root);
			
		final Property prop1 = ThingMLFactory.eINSTANCE.createProperty();
		prop1.setReadonly(false);
		prop1.setName("received_" + rm.getPort().getName() + "_" + m1.getName());
		final TypeRef tr1 = ThingMLFactory.eINSTANCE.createTypeRef();
		tr1.setType(bool);
		prop1.setTypeRef(tr1);
		source.getProperties().add(prop1);
		
		final Property prop2 = ThingMLFactory.eINSTANCE.createProperty();
		prop2.setReadonly(false);
		prop2.setName("received_" + rm.getPort().getName() + "_" + m2.getName());
		final TypeRef tr2 = ThingMLFactory.eINSTANCE.createTypeRef();
		tr2.setType(bool);
		prop2.setTypeRef(tr2);
		source.getProperties().add(prop2);	
		
		Map<String, Property> props = new HashMap<>();
		
		final Handler t1 = createHandler(m1, rm, t, source, prop1, prop2, props);
		final Handler t2 = createHandler(m2, rm, t, source, prop1, prop2, props);
		
		final PropertyReference pr2 = ThingMLFactory.eINSTANCE.createPropertyReference();
		pr2.setProperty(prop2);
		final ConditionalAction if1 = ThingMLFactory.eINSTANCE.createConditionalAction();
		if1.setCondition(pr2);
		if1.setAction(replaceEventRefByPropRef(EcoreUtil.copy(t.getAction()), props));
		((ActionBlock)t1.getAction()).getActions().add(if1);

		final PropertyReference pr1 = ThingMLFactory.eINSTANCE.createPropertyReference();
		pr1.setProperty(prop1);
		final ConditionalAction if2 = ThingMLFactory.eINSTANCE.createConditionalAction();
		if2.setCondition(pr1);
		if2.setAction(replaceEventRefByPropRef(EcoreUtil.copy(t.getAction()), props));
		((ActionBlock)t2.getAction()).getActions().add(if2);
		
		source.getInternal().remove(t);
	}
	
	private void updateHandlers(Thing root, Transition t) {}

	private <T extends EObject> T replaceEventRefByPropRef(T e, Map<String, Property> props) {
		final TreeIterator<EObject> it = e.eAllContents();
		while (it.hasNext()) {
			final EObject o = it.next();
			if (o instanceof EventReference) {				
				final EventReference er = (EventReference) o;
				final PropertyReference pr = ThingMLFactory.eINSTANCE.createPropertyReference();
				final ReceiveMessage rm = (ReceiveMessage)er.getReceiveMsg();				
				final Property p = props.get(rm.getPort().getName() + "_" + rm.getMessage().getName() + "_" + er.getParameter().getName());
				if (p == null) continue;
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
