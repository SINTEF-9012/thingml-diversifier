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
import org.thingml.xtext.helpers.ActionHelper;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.StateContainerHelper;
import org.thingml.xtext.thingML.ActionBlock;
import org.thingml.xtext.thingML.AnnotatedElement;
import org.thingml.xtext.thingML.CompositeState;
import org.thingml.xtext.thingML.ConditionalAction;
import org.thingml.xtext.thingML.EventReference;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.FunctionCallExpression;
import org.thingml.xtext.thingML.Handler;
import org.thingml.xtext.thingML.IntegerLiteral;
import org.thingml.xtext.thingML.LowerExpression;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.PlatformAnnotation;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.PrintAction;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.PropertyReference;
import org.thingml.xtext.thingML.ReceiveMessage;
import org.thingml.xtext.thingML.Region;
import org.thingml.xtext.thingML.SendAction;
import org.thingml.xtext.thingML.State;
import org.thingml.xtext.thingML.StateContainer;
import org.thingml.xtext.thingML.StringLiteral;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLFactory;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.thingML.Transition;
import org.thingml.xtext.thingML.VariableAssignment;

import no.sintef.thingml.diversifier.Manager;
import no.sintef.thingml.diversifier.Mode;

public class SplitMessages extends Strategy {
	
	final Map<Thing, Map<Message, Port>> mappings = new HashMap<>();
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
			        //if (m.getParameters().size() < 2) return;
			        int splitAt = Manager.rnd.nextInt(msg.getParameters().size());
			        //if (splitAt == 0 || splitAt == m.getParameters().size()) return;
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
						
	                    if (mappings.get(t) == null) mappings.put(t, new HashMap<>());
	                    final Map<Message, Port> msgMappings = mappings.get(t);
	                    msgMappings.put(m, port);
	                    final Port ip = Helper.createOrGetInternalPort(t);
	                    //System.out.println(" removing message " + m.getName() + " from received messages of port " + port.getName() + " of thing " + thing.getName());
	                    port.getReceives().remove(m);
	                    //System.out.println(" adding message " + first.getName() + " and " + second.getName() + " to received messages of port " + port.getName() + " of thing " + thing.getName());
	                    port.getReceives().add(first);
	                    port.getReceives().add(second);
	                    //System.out.println(" adding message " + m.getName() + " to internal port " + ip.getName() + " of thing " + thing.getName());
	                    ip.getReceives().add(m);
	                    ip.getSends().add(m);
	                    createRegion(t, port, m, first, second);
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
    
    private void createRegion(Thing t, Port port, Message m, Message m1, Message m2) {
        if (t.getBehaviour() != null) {
            for (StateContainer r : StateContainerHelper.allContainedRegions(t.getBehaviour())) {
                if (r instanceof Region)
                    if (r.getName().equals("generate_" + m.getName() + "_from_" + m1.getName() + "_and_" + m2.getName()))
                        return;//FIXME: this is a hack to avoid creating two similar regions...
            }

            final Region region = ThingMLFactory.eINSTANCE.createRegion();
            final CompositeState _init = ThingMLFactory.eINSTANCE.createCompositeState();
            Map<String, Property> props = new HashMap<>();
            for (Parameter param : m.getParameters()) {
                final Property prop = ThingMLFactory.eINSTANCE.createProperty();
                prop.setReadonly(false);
                prop.setName(param.getName());
                prop.setTypeRef(EcoreUtil.copy(param.getTypeRef()));
                _init.getProperties().add(prop);
                props.put(prop.getName(), prop);
            }
            _init.setName("INIT");
            final PlatformAnnotation a = ThingMLFactory.eINSTANCE.createPlatformAnnotation();
            a.setName("diversify");
            a.setValue("not");

            final State s1 = ThingMLFactory.eINSTANCE.createState();
            s1.setName("S1");
            s1.getAnnotations().add(EcoreUtil.copy(a));
            final State s2 = ThingMLFactory.eINSTANCE.createState();
            s2.setName("S2");
            s2.getAnnotations().add(EcoreUtil.copy(a));
            final State s3 = ThingMLFactory.eINSTANCE.createState();
            s3.setName("S3");
            s3.getAnnotations().add(EcoreUtil.copy(a));
            final State s4 = ThingMLFactory.eINSTANCE.createState();
            s4.setName("S4");
            s4.getAnnotations().add(EcoreUtil.copy(a));
            region.setName("generate_" + m.getName() + "_from_" + m1.getName() + "_and_" + m2.getName());
            region.setInitial(_init);
            region.getSubstate().add(_init);
            _init.getSubstate().add(s1);
            _init.getSubstate().add(s2);
            _init.getSubstate().add(s3);
            _init.getSubstate().add(s4);
            _init.setInitial(s1);


            final ActionBlock block = ThingMLFactory.eINSTANCE.createActionBlock();
            if (debug) {
                final PrintAction print = ThingMLFactory.eINSTANCE.createPrintAction();
                print.setLine(true);
                final StringLiteral msg = ThingMLFactory.eINSTANCE.createStringLiteral();
                msg.setStringValue(Helper.createOrGetInternalPort(t).getName() + "!" + m.getName());
                print.getMsg().add(msg);
                block.getActions().add(print);
            }
            final SendAction send = ThingMLFactory.eINSTANCE.createSendAction();
            send.setPort(Helper.createOrGetInternalPort(t));
            send.setMessage(m);
            block.getActions().add(send);
            s4.setEntry(block);
            for (Parameter param : m.getParameters()) {
                final PropertyReference ref = ThingMLFactory.eINSTANCE.createPropertyReference();
                ref.setProperty(props.get(param.getName()));
                send.getParameters().add(ref);
            }


            final Transition s4tos1 = ThingMLFactory.eINSTANCE.createTransition();
            s4tos1.setTarget(s1);
            s4.getOutgoing().add(s4tos1);

            buildTransitions(m1, port, s1, s2, props);
            buildTransitions(m2, port, s1, s3, props);
            buildTransitions(m1, port, s3, s4, props);
            buildTransitions(m2, port, s2, s4, props);

            t.getBehaviour().getRegion().add(region);
        }
    }
    
    private void updateHandlers(Thing thing, Port p, Message m) {
        TreeIterator<EObject> it = thing.eAllContents();
        while (it.hasNext()) {
            final EObject o = it.next();
            if (o instanceof Handler) {
                final Handler h = (Handler) o;
                if (/*!AnnotatedElementHelper.isDefined((State)h.eContainer(), "diversify", "not") && */h.getEvent() != null) {
                    final ReceiveMessage rm = (ReceiveMessage) h.getEvent();
                    if (EcoreUtil.equals(rm.getMessage(), m) && EcoreUtil.equals(rm.getPort(), p))
                        rm.setPort(Helper.createOrGetInternalPort(ThingMLHelpers.findContainingThing(rm)));
                }
            }
        }
    }
    
    private void buildTransitions(Message m, Port p, State from, State to, Map<String, Property> props) {
        System.out.println("Building new transition from " + from.getName() + " to " + to.getName() + " on event " + p.getName() + "?" + m.getName());
        final ReceiveMessage rm1 = ThingMLFactory.eINSTANCE.createReceiveMessage();
        rm1.setName("ev");
        rm1.setPort(p);
        rm1.setMessage(m);
        final Transition t = ThingMLFactory.eINSTANCE.createTransition();
        t.setTarget(to);
        from.getOutgoing().add(t);
        t.setEvent(rm1);
        final ActionBlock b1 = buildActionForTransition(m, props, rm1);
        t.setAction(b1);
    }    
    
    private ActionBlock buildActionForTransition(Message m, Map<String, Property> props, ReceiveMessage rm) {
        final ActionBlock b = ThingMLFactory.eINSTANCE.createActionBlock();
        if (debug) {
            final PrintAction print = ThingMLFactory.eINSTANCE.createPrintAction();
            print.setLine(true);
            final StringLiteral msg = ThingMLFactory.eINSTANCE.createStringLiteral();
            msg.setStringValue(rm.getPort().getName() + "?" + rm.getMessage().getName());
            print.getMsg().add(msg);
            b.getActions().add(print);
        }
        for (Parameter p1 : m.getParameters()) {
            final VariableAssignment va = ThingMLFactory.eINSTANCE.createVariableAssignment();
            va.setProperty(props.get(p1.getName()));
            final EventReference ref = ThingMLFactory.eINSTANCE.createEventReference();
            ref.setReceiveMsg(rm);
            ref.setParameter(p1);
            va.setExpression(ref);
            b.getActions().add(va);
        }
        return b;
    }
    
}


