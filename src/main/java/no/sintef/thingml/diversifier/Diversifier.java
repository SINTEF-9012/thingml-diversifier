package no.sintef.thingml.diversifier;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.compilers.ThingMLCompiler;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.*;
import org.thingml.xtext.thingML.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

class Diversifier {

    //TODO: Introduce probabilities of doing changes and randomness so as Diversifying multiple time the same input configuration actually generates multiple diversified results

    //private Map<Instance, Thing> diversifiedThings = new HashMap<Instance, Thing>();
    private int functionCount = 0;
    private byte code;

    public static void main(String args[]) {
        if (args.length < 1) {
            System.out.println("Please provide path to a ThingML model containing at least one configuration.");
            return;
        }
        final File model = new File(args[0]);
        if (Files.notExists(model.toPath()))
            return;

        final Diversifier diversifier = new Diversifier();

        final ThingMLModel input = ThingMLCompiler.loadModel(model);
        for (Configuration cfg : input.getConfigs()) {
            System.out.println("Diversifying configuration " + cfg.getName());
            diversifier.diversify(cfg);
        }
        try {
            ThingMLCompiler.flattenModel(input);
            ThingMLCompiler.saveAsThingML(input, new File(model.getParent(), "/diversified/" + model.getName()).getAbsolutePath());
        } catch (RuntimeException e) {
            //Nasty dirty hack to hide the fact that sometime, save fails because the model is ill-formed. We could have done better with more money...
            Diversifier.main(args);
        } catch (IOException e) {
            Diversifier.main(args);
        }
    }

    /**
     * Inlines a function call
     *
     * @param call
     */
    private void inlineFunctionCall(FunctionCallStatement call) {
        //TODO: later... maybe not so useful and tricky to get completely right
    }

    /**
     * Inlines a function call
     *
     * @param call
     */
    private void inlineFunctionCall(FunctionCallExpression call) {
        //TODO: later... maybe not so useful and tricky to get completely right
    }

    /**
     * Transforms a an action block in a call to a function
     * implementing this block. Note that the block can be
     * pre-existing or created on the fly by arbitrarily
     * grouping successive n-actions in a block
     *
     * @param block
     */
    private void asFunctionCall(ActionBlock block) {
        //TODO: later... maybe not so useful and tricky to get completely right
    }

    /**
     * Upsize parameter e.g. Byte -> Integer
     *
     * @param p
     */
    private void upSizeParameter(Parameter p) {

    }

    /**
     * Change order of messages
     *
     * @param t
     */
    private void changeOrderOfMessages(Thing t) {
        System.out.println("Shuffling messages of thing " + t.getName());
        final List<Message> shuffled = new ArrayList<Message>();
        shuffled.addAll(t.getMessages());
        Collections.shuffle(shuffled);
        t.getMessages().clear();
        t.getMessages().addAll(shuffled);
    }

    /**
     * Change order of parameters
     *
     * @param m
     */
    private void changeOrderOfParameter(Message m) {
        final List<Parameter> shuffled = new ArrayList<Parameter>();
        shuffled.addAll(m.getParameters());
        Collections.shuffle(shuffled);
        m.getParameters().clear();
        m.getParameters().addAll(shuffled);
    }

    private void generateCodeForMessage(Message m) {
        if (!AnnotatedElementHelper.hasAnnotation(m, "code")) {
            final PlatformAnnotation a = ThingMLFactory.eINSTANCE.createPlatformAnnotation();
            a.setName("code");
            a.setValue(String.format("0x%02X", (code++)));
            m.getAnnotations().add(a);
        }
    }

    private Message createMessage(Thing t, Message m, List<Parameter> params) {
        final Message msg = ThingMLFactory.eINSTANCE.createMessage();
        String name = m.getName();
        for (Parameter p : params) {
            name = name + p.getName();
            System.out.println("DEBUG: " + name);
            msg.getParameters().add(p);
        }
        msg.setName(name);
        t.getMessages().add(msg);
        for (Thing thing : ThingMLHelpers.allThings(ThingMLHelpers.findContainingModel(t))) {
            for (Port port : thing.getPorts()) {
                if (port.getSends().contains(m)) {
                    port.getSends().add(msg);
                }
                if (port.getReceives().contains(m)) {
                    port.getReceives().add(msg);
                }
            }
        }
        return msg;
    }

    /**
     * For this thing t, message m will be splitted
     * into m.parameters# messages. Both sides (instances
     * and their instance-specific types) will be updated
     * to properly handle sending/receiving of split message
     *
     * @param t
     * @param m
     */
    private void splitMessage(Thing t, Message m) {
        if (m.getParameters().size() < 2) return;
        List<Parameter> firsts = new ArrayList<>();
        List<Parameter> lasts = new ArrayList<>();
        int splitAt = (int) Math.round(Math.random()) % m.getParameters().size();
        int i = 0;
        for (Parameter p : m.getParameters()) {
            if (i < splitAt) {
                firsts.add(EcoreUtil.copy(p));
            } else {
                lasts.add(EcoreUtil.copy(p));
            }
            i++;
        }
        System.out.println("DEBUG: " + firsts.size() + " + " + lasts.size() + " = " + m.getParameters().size());
        if (firsts.size() > 0 && lasts.size() > 0) {
            final Message first = createMessage((Thing) m.eContainer(), m, firsts);
            final Message second = createMessage((Thing) m.eContainer(), m, lasts);
            for (Thing thing : ThingMLHelpers.allThings(ThingMLHelpers.findContainingModel(t))) {
                //Updating send actions to send two messages instead of one
                for (SendAction send : ActionHelper.getAllActions(thing, SendAction.class)) {
                    if (send.getMessage().equals(m)) {
                        final ActionBlock block = ThingMLFactory.eINSTANCE.createActionBlock();
                        final SendAction send1 = ThingMLFactory.eINSTANCE.createSendAction();
                        send1.setMessage(first);
                        send1.setPort(send.getPort());
                        for (Expression p : send.getParameters()) {
                            if (send.getParameters().indexOf(p) >= splitAt)
                                break;
                            send1.getParameters().add(EcoreUtil.copy(p));
                        }
                        block.getActions().add(send1);
                        final SendAction send2 = ThingMLFactory.eINSTANCE.createSendAction();
                        send2.setMessage(second);
                        send2.setPort(send.getPort());
                        for (Expression p : send.getParameters()) {
                            if (send.getParameters().indexOf(p) < splitAt)
                                continue;
                            send2.getParameters().add(EcoreUtil.copy(p));
                        }
                        block.getActions().add(send2);

                        final Object parent = send.eContainer().eGet(send.eContainingFeature());
                        if (parent instanceof EList) {
                            EList list = (EList) parent;
                            final int index = list.indexOf(send);
                            list.add(index, block);
                            list.remove(send);
                        } else {
                            final EObject o = send.eContainer();
                            o.eSet(send.eContainingFeature(), block);
                        }
                    }
                }

                //Move original message into an internal port
                final List<Port> ports = new ArrayList<>();
                ports.addAll(thing.getPorts());
                for (Port port : ports) {
                    boolean found = false;
					/*if (port.getSends().contains(m)) {
						found = true;
						final Port iport = createOrGetInternalPort(thing, port);
						port.getSends().remove(m);
						iport.getSends().add(m);
						iport.getReceives().add(m);
					}*/
                    if (port.getReceives().contains(m)) {
                        found = true;
                        final Port iport = createOrGetInternalPort(thing, port);
                        port.getReceives().remove(m);
                        iport.getSends().add(m);
                        iport.getReceives().add(m);
                    }
                    if (found) {
                        final Port iport = createOrGetInternalPort(thing, port);
                        createRegion(thing, port, iport, m, first, second);
                        updateHandlers(thing, port, iport, m);
                    }
                }
            }

            //((Thing) m.eContainer()).getMessages().remove(m);

        }


        //TODO: update send/receive logic to send/receive multiple messages instead of the original message
    }

    private void createRegion(Thing t, Port p, Port iport, Message m, Message m1, Message m2) {
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
            final State s1 = ThingMLFactory.eINSTANCE.createState();
            s1.setName("S1");
            final State s2 = ThingMLFactory.eINSTANCE.createState();
            s2.setName("S2");
            final State s3 = ThingMLFactory.eINSTANCE.createState();
            s3.setName("S3");
            region.setName("generate_" + m.getName() + "_from_" + m1.getName() + "_and_" + m2.getName());
            region.setInitial(_init);
            region.getSubstate().add(_init);
            _init.getSubstate().add(s1);
            _init.getSubstate().add(s2);
            _init.getSubstate().add(s3);
            _init.setInitial(s1);


		    final SendAction send = ThingMLFactory.eINSTANCE.createSendAction();
		    send.setPort(iport);
		    send.setMessage(m);
		    s3.setEntry(send);
		    for(Parameter param : m.getParameters()) {
		        final PropertyReference ref = ThingMLFactory.eINSTANCE.createPropertyReference();
		        ref.setProperty(props.get(param.getName()));
		        send.getParameters().add(ref);
            }


            final Transition s3tos1 = ThingMLFactory.eINSTANCE.createTransition();
            s3tos1.setTarget(s1);
            s3.getOutgoing().add(s3tos1);


			Port po1 = null;
			for(Port _p : t.getPorts()) {
				if (p.getReceives().contains(m)) {
					po1 = _p;
					break;
				}
			}
			if (po1 == null)
			    po1 = p;
            Port po2 = null;
            for(Port _p : t.getPorts()) {
                if (p.getReceives().contains(m)) {
                    po2 = _p;
                    break;
                }
            }
            if (po2 == null)
                po2 = p;

            buildTransitions(m1, po1, s1, s2, props);
            buildTransitions(m2, po2, s1, s2, props);
            buildTransitions(m1, po1, s2, s3, props);
            buildTransitions(m2, po2, s2, s3, props);


            t.getBehaviour().getRegion().add(region);
        }
    }

    private void buildTransitions(Message m, Port p, State from, State to, Map<String, Property> props) {
        final ReceiveMessage rm1 = ThingMLFactory.eINSTANCE.createReceiveMessage();
        rm1.setName("e");
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
        for(Parameter p1 : m.getParameters()) {
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

    private void updateHandlers(Thing thing, Port port, Port iport, Message m) {
        //Update handlers so that they listen to the internal port where the original message will be re-built
        System.out.println("creating internal port in thing " + thing.getName());
        Map<Message, List<Handler>> mess = StateHelper.allMessageHandlers(thing).get(port);
        if (mess == null) return;
        for (List<Handler> handlers : mess.values()) {
            for (Handler handler : handlers) {
                ReceiveMessage rm = (ReceiveMessage) handler.getEvent();
                if (rm.getMessage().equals(m)) {
                    rm.setPort(iport);
                }
            }
        }
    }

    private Port createOrGetInternalPort(Thing thing, Port port) {
        Port result = null;
        for (Port p : thing.getPorts()) {
            if (!(p instanceof InternalPort)) continue;
            if (p.getName().endsWith(port.getName())) {
                result = p;
                break;
            }
        }
        if (result == null) {
            result = ThingMLFactory.eINSTANCE.createInternalPort();
            result.setName("i_" + port.getName());
            thing.getPorts().add(result);
        }
        return result;
    }

    /**
     * Introduces a proxy, i.e. an instance between
     * both sides of the connector c that simply receives
     * and re-sends all messages normally going through connector
     *
     * @param c
     */
    private void introduceProxy(Connector c) {
        //TODO: Use the ThingML injector to instantiate the Thing proxy from text (a bit too annoying to do it programmatically...)
    }

    private void diversifyMessages(Thing t) {
        int i = 0;
        List<Message> msgs = new ArrayList<>();
        for (Port p : t.getPorts()) {
            msgs.addAll(p.getReceives());
            msgs.addAll(p.getSends());
        }
        for (Message m : msgs) {
            //if (i%2 == 0)
            changeOrderOfParameter(m);
            //else
            splitMessage(t, m);
            //i++;
        }
    }

    private void diversify(Thing t) {
        changeOrderOfMessages(t);
        for (Thing i : ThingHelper.allIncludedThings(t)) {
            changeOrderOfMessages(i);
        }

        diversifyMessages(t);
        for (Thing inc : ThingHelper.allIncludedThings(t)) {
            diversifyMessages(inc);
        }

        for (Message m : ThingMLHelpers.allMessages(t)) {
            generateCodeForMessage(m);
        }
    }

    private void diversify(Connector c) {

    }

    /**
     * Splits configuration into a random number of configurations,
     * each configuration having at least one instance. Replace connectors
     * by external connectors as needed
     * NOTE: we should just use one protocol that works everywhere for now e.g. MQTT , but of course
     * protocols could also be replaced, etc.
     *
     * @param cfg
     */
    private void splitConfiguration(Configuration cfg) {

    }

    public void diversify(Configuration cfg) {
        code = 0x00;
        //diversifiedThings.clear();
        //List<Thing> toRemove = new ArrayList<>();
        final ThingMLModel model = (ThingMLModel) cfg.eContainer();
        for (Instance i : cfg.getInstances()) {
            //toRemove.add(i.getType());
            //We create a new Thing for each instance
            //final Thing t = EcoreUtil.copy(i.getType()); //NOTE: it turns out that for now, instance-specific types are equivalent to the original type... so we do not really need to make those clones...
            //t.setName(t.getName() + i.getName());
            //instance does now instantiate new type
            //i.setType(t);

            //Remapping connectors' ports to the cloned things
            for (AbstractConnector c : cfg.getConnectors()) {
                if (c instanceof Connector) {
                    if (((Connector) c).getCli() == i) {
                        for (Port port : i.getType().getPorts()) {
                            if (port.getName().equals(((Connector) c).getRequired().getName())) {
                                ((Connector) c).setRequired((RequiredPort) port);
                                break;
                            }
                        }
                    } else if (((Connector) c).getSrv() == i) {
                        for (Port port : i.getType().getPorts()) {
                            if (port.getName().equals(((Connector) c).getProvided().getName())) {
                                ((Connector) c).setProvided((ProvidedPort) port);
                                break;
                            }
                        }
                    }
                }
            }

            //model.getTypes().add(t);
            //diversifiedThings.put(i, t);

        }
        //for (Thing t : toRemove) {
        //	model.getTypes().remove(t);
        //}
        for (AbstractConnector c : cfg.getConnectors()) {
            if (c instanceof Connector)
                diversify((Connector) c);
        }
        for (Thing t : ThingMLHelpers.allThings(model)) {
            diversify(t);
        }
        //for(Instance i : cfg.getInstances()) {
        //	diversify(diversifiedThings.get(i));
        //}
    }
}