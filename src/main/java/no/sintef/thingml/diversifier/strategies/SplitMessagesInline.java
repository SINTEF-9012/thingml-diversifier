package no.sintef.thingml.diversifier.strategies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.xtext.constraints.Types;
import org.thingml.xtext.helpers.ActionHelper;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.ActionBlock;
import org.thingml.xtext.thingML.AndExpression;
import org.thingml.xtext.thingML.AnnotatedElement;
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
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.PrimitiveType;
import org.thingml.xtext.thingML.PrintAction;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.PropertyReference;
import org.thingml.xtext.thingML.ReceiveMessage;
import org.thingml.xtext.thingML.SendAction;
import org.thingml.xtext.thingML.State;
import org.thingml.xtext.thingML.StringLiteral;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLFactory;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.thingML.ThingMLPackage;
import org.thingml.xtext.thingML.TypeRef;
import org.thingml.xtext.thingML.VariableAssignment;

import no.sintef.thingml.diversifier.Manager;
import no.sintef.thingml.diversifier.Mode;

public class SplitMessagesInline extends Strategy {

	final Map<Message, List<Message>> duplicates = new HashMap<>();

	@Override
	protected void doApply(ThingMLModel model) {
		//We need two passes, as the messages may be defined in one thing and then included in other things using them in ports

		//PASS 1: Copy all messages
		final TreeIterator<EObject> it = model.eAllContents();
		while (it.hasNext()) {
			final EObject o = it.next();
			if (o instanceof Thing) {
				final Thing t = (Thing) o;
				//if (!Manager.diversify(t)) return;
				final List<Message> msgs = new ArrayList<>();
				msgs.addAll(t.getMessages());
				for (Message msg : msgs) {				
					if (!Manager.diversify(msg)) continue;	
					int splitAt = Manager.rnd.nextInt(msg.getParameters().size());
					System.out.println("Splitting message " + msg.getName() + " at index " + splitAt + "...");
					final Message first = createMessage((Thing) msg.eContainer(), msg, msg.getParameters().subList(0, splitAt));
					final Message second = createMessage((Thing) msg.eContainer(), msg, msg.getParameters().subList(splitAt, msg.getParameters().size()));
					t.getMessages().add(first);
					t.getMessages().add(second);
					final List<Message> messages = new ArrayList<>();
					messages.add(first);
					messages.add(second);
					duplicates.put(msg, messages);
				}
			}
		}

		//PASS 2: Update ports
		final TreeIterator<EObject> it2 = model.eAllContents();
		while (it2.hasNext()) {
			final EObject o = it2.next();
			if (o instanceof Thing) {
				final Thing t = (Thing) o;
				
				final List<Port> ports = new ArrayList<>();
				ports.addAll(t.getPorts());
				for (Port port : ports) {
					if (!Manager.diversify(port)) continue;
					
					final List<Message> sent = new ArrayList<>();
					sent.addAll(port.getSends());
					for(Message m : sent) {
						List<Message> messages = duplicates.get(m);
						if (messages == null) continue;
						//System.out.println(" adding messages " + first.getName() + " and " + second.getName() + " to sent messages of port " + port.getName() + " of thing " + thing.getName());
						final Message first = messages.get(0);
						final Message second = messages.get(1);
						port.getSends().add(first);
						port.getSends().add(second);
						//System.out.println(" removing message " + m.getName() + " from sent messages of port " + port.getName() + " of thing " + thing.getName());
						port.getSends().remove(m);
						splitSendAction(t, port, m, first, second, first.getParameters().size());
					}

					final List<Message> received = new ArrayList<>();
					received.addAll(port.getReceives());
					for(Message m : received) {
						List<Message> messages = duplicates.get(m);
						if (messages == null) continue;

						final Message first = messages.get(0);
						final Message second = messages.get(1);

						port.getReceives().remove(m);
						port.getReceives().add(first);
						port.getReceives().add(second);

						updateHandlers(t, port, m);
					}
				}				
			}
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

	private void splitSendAction(Thing thing, Port p, Message m, Message first, Message second, int splitAt) {
		for (SendAction send : ActionHelper.getAllActions(thing, SendAction.class)) {
			EObject eo = send.eContainer();
			while (eo != null) {
				if (eo instanceof AnnotatedElement) {
					final AnnotatedElement a = (AnnotatedElement) eo;
					if (AnnotatedElementHelper.isDefined(a, "diversify", "not")) {
						return;
					}
				}
				eo = eo.eContainer();
			}
			if (EcoreUtil.equals(send.getMessage(), m) && EcoreUtil.equals(send.getPort(), p)) {
				final SendAction send1 = ThingMLFactory.eINSTANCE.createSendAction();
				send1.setMessage(first);
				send1.setPort(send.getPort());
				for (Expression e : send.getParameters().subList(0, splitAt)) {
					send1.getParameters().add(EcoreUtil.copy(e));
				}

				final SendAction send2 = ThingMLFactory.eINSTANCE.createSendAction();
				send2.setMessage(second);
				send2.setPort(send.getPort());
				for (Expression e : send.getParameters().subList(splitAt, m.getParameters().size())) {
					send2.getParameters().add(EcoreUtil.copy(e));
				}

				final Function rnd = Manager.findRandom(thing);
				if (rnd == null || Manager.mode==Mode.STATIC) {
					final ActionBlock b1 = ThingMLFactory.eINSTANCE.createActionBlock();
					if (Manager.rnd.nextBoolean()) {
						b1.getActions().add(send1);
						b1.getActions().add(send2);
					} else {
						b1.getActions().add(send2);
						b1.getActions().add(send1);
					}

					final Object parent = send.eContainer().eGet(send.eContainingFeature());
					if (parent instanceof EList) {
						EList list = (EList) parent;
						final int index = list.indexOf(send);
						list.add(index, b1);
						list.remove(send);
					} else {
						final EObject o = send.eContainer();
						o.eSet(send.eContainingFeature(), b1);
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

					final Object parent = send.eContainer().eGet(send.eContainingFeature());
					if (parent instanceof EList) {
						EList list = (EList) parent;
						final int index = list.indexOf(send);
						list.add(index, ca);
						list.remove(send);
					} else {
						final EObject o = send.eContainer();
						o.eSet(send.eContainingFeature(), ca);
					}
				}
			}
		}
	}

	private void updateHandlers(Thing thing, Port p, Message m) {
		final Set<String> log = new HashSet<>();
		Map<String, Property> props = new HashMap<>();
		Property prop1 = null, prop2 = null;
		
		final TreeIterator<EObject> it = thing.eAllContents();
		while (it.hasNext()) {
			final EObject o = it.next();
			if (o instanceof Handler) {
				final Handler t = (Handler) o;
				if (t.getEvent() != null && t.getEvent() instanceof ReceiveMessage) {
					final ReceiveMessage rm = (ReceiveMessage) t.getEvent();
					if (EcoreUtil.equals(rm.getMessage(), m) && EcoreUtil.equals(rm.getPort(), p)) {
						final State source = (State)t.eContainer();   
						final Message m1 = duplicates.get(m).get(0);
						final Message m2 = duplicates.get(m).get(1);
																	
						if (!log.contains(source.getName() + "_" + p.getName() + "_" + m.getName())) {
							PrimitiveType bool = Helper.getPrimitiveType(Types.BOOLEAN_TYPE, thing);
							prop1 = ThingMLFactory.eINSTANCE.createProperty();
							prop1.setReadonly(false);
							prop1.setName("received_" + p.getName() + "_" + m1.getName());
							final TypeRef tr1 = ThingMLFactory.eINSTANCE.createTypeRef();
							tr1.setType(bool);
							prop1.setTypeRef(tr1);
							source.getProperties().add(prop1);
							props.put("received_" + p.getName() + "_" + m1.getName(), prop1);

							prop2 = ThingMLFactory.eINSTANCE.createProperty();
							prop2.setReadonly(false);
							prop2.setName("received_" + p.getName() + "_" + m2.getName());
							final TypeRef tr2 = ThingMLFactory.eINSTANCE.createTypeRef();
							tr2.setType(bool);
							prop2.setTypeRef(tr2);
							source.getProperties().add(prop2);
							props.put("received_" + p.getName() + "_" + m2.getName(), prop2);

							for (Parameter param : m.getParameters()) {
								final Property prop = ThingMLFactory.eINSTANCE.createProperty();
								prop.setReadonly(false);
								prop.setName(p.getName() + "_" + m.getName() + "_" + param.getName());
								prop.setTypeRef(EcoreUtil.copy(param.getTypeRef()));
								source.getProperties().add(prop);
								props.put(p.getName() + "_" + m.getName() + "_" + param.getName(), prop);
							}
														
							buildTransition(m1, p, source, m);
							buildTransition(m2, p, source, m);    
							
							log.add(source.getName() + "_" + p.getName() + "_" + m.getName());
						}
						
						prop1 = props.get("received_" + p.getName() + "_" + m1.getName());
						prop2 = props.get("received_" + p.getName() + "_" + m2.getName());
						
						final PropertyReference pr1 = ThingMLFactory.eINSTANCE.createPropertyReference();
						pr1.setProperty(prop1);
						final PropertyReference pr2 = ThingMLFactory.eINSTANCE.createPropertyReference();
						pr2.setProperty(prop2);
						final AndExpression and = ThingMLFactory.eINSTANCE.createAndExpression();
						and.setLhs(pr1);
						and.setRhs(pr2);
						
						if (t.getGuard() != null) {
							final AndExpression andGuard = ThingMLFactory.eINSTANCE.createAndExpression();
							final Expression guard = replaceEventRefByPropRef(t.getGuard(), props);
							final ExpressionGroup group = ThingMLFactory.eINSTANCE.createExpressionGroup();
							group.setTerm(guard);						
							andGuard.setLhs(and);
							andGuard.setRhs(group);
							t.setGuard(andGuard);
						} else {
							t.setGuard(and);
						}
						
						ActionBlock b = null;
						if (t.getAction() == null) {
							b = ThingMLFactory.eINSTANCE.createActionBlock();
						} else if (t.getAction() instanceof ActionBlock) {
							b = (ActionBlock)replaceEventRefByPropRef(t.getAction(), props);
						} else {
							b = ThingMLFactory.eINSTANCE.createActionBlock();
							b.getActions().add(replaceEventRefByPropRef(t.getAction(), props));
						}
						
						final BooleanLiteral bool_false = ThingMLFactory.eINSTANCE.createBooleanLiteral();
						bool_false.setBoolValue(false);
						final VariableAssignment va1 = ThingMLFactory.eINSTANCE.createVariableAssignment();
						va1.setProperty(prop1);
						va1.setExpression(bool_false);
						b.getActions().add(va1);
						final VariableAssignment va2 = ThingMLFactory.eINSTANCE.createVariableAssignment();
						va2.setProperty(prop2);
						va2.setExpression(EcoreUtil.copy(bool_false));
						b.getActions().add(va2);						
						
						t.eUnset(ThingMLPackage.eINSTANCE.getHandler_Event());
					}
				}
			} 
		}
	}    

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
	
	private void buildTransition(Message m, Port p, State from, Message orig) {
		System.out.println("Building new handler in " + from.getName() + " on event " + p.getName() + "?" + m.getName());
		final ReceiveMessage rm = ThingMLFactory.eINSTANCE.createReceiveMessage();
		rm.setName("ev");
		rm.setPort(p);
		rm.setMessage(m);
		final InternalTransition t = ThingMLFactory.eINSTANCE.createInternalTransition();
		from.getInternal().add(t);
		t.setEvent(rm);
		final ActionBlock b1 = buildActionForTransition(from, rm, orig);
        t.setAction(b1);
	}    

	private ActionBlock buildActionForTransition(State source, ReceiveMessage rm, Message orig) {
		final ActionBlock b = ThingMLFactory.eINSTANCE.createActionBlock();
		if (debug) {
			final PrintAction print = ThingMLFactory.eINSTANCE.createPrintAction();
			print.setLine(true);
			final StringLiteral msg = ThingMLFactory.eINSTANCE.createStringLiteral();
			msg.setStringValue(rm.getPort().getName() + "?" + rm.getMessage().getName());
			print.getMsg().add(msg);
			b.getActions().add(print);
		}
		for (Parameter p : rm.getMessage().getParameters()) {			
			Property prop = null;
			for(Property pr : source.getProperties()) {
				if (pr.getName().equals(rm.getPort().getName() + "_" + orig.getName() + "_" + p.getName())) {
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
			b.getActions().add(va);
		}
		
		final VariableAssignment va = ThingMLFactory.eINSTANCE.createVariableAssignment();		
		Property prop = null;
		for(Property pr : source.getProperties()) {
			if (pr.getName().equals("received_" + rm.getPort().getName() + "_" + rm.getMessage().getName())) {
				prop = pr;
				break;
			}			
		}		
		va.setProperty(prop);
		final BooleanLiteral lit = ThingMLFactory.eINSTANCE.createBooleanLiteral();
		lit.setBoolValue(true);
		va.setExpression(lit);
		b.getActions().add(va);
		
		return b;
	}

}
