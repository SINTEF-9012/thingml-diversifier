package no.sintef.thingml.diversifier;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.compilers.ThingMLCompiler;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.constraints.Types;
import org.thingml.xtext.helpers.ActionHelper;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.StateContainerHelper;
import org.thingml.xtext.helpers.TyperHelper;
import org.thingml.xtext.thingML.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

class Diversifier {

    //TODO: Introduce probabilities of doing changes and randomness so as Diversifying multiple time the same input configuration actually generates multiple diversified results
    //NOTE: Done to some extent

    private int functionCount = 0;
    private byte code;
    private int param = 0;
    private Random rnd;

    private final boolean debug = true;

    public Diversifier(int seed) {
        rnd = new Random(seed * System.currentTimeMillis() - seed);
        code = (byte) rnd.nextInt(255);
    }

    public static void main(String args[]) {
        if (args.length < 1) {
            System.out.println("Please provide path to a ThingML model containing at least one configuration.");
            return;
        }
        final File model = new File(args[0]);
        if (Files.notExists(model.toPath())) {
            System.out.println("Input file does not exist.");
            return;
        }

        final ThingMLModel input = ThingMLCompiler.loadModel(model);
        final Diversifier diversifier = new Diversifier(input.hashCode());
        final List<Configuration> configs = new ArrayList<>();
        configs.addAll(input.getConfigs());
        for (Configuration cfg : configs) {
            System.out.println("Diversifying configuration " + cfg.getName());
            int amount = 1;
            if (args.length >= 2) {
                amount = Integer.parseInt(args[1]);
            }
            int iterations = 1;
            if (args.length >= 3) {
                iterations = Integer.parseInt(args[2]);
            }
            diversifier.diversify(cfg, amount, iterations);
        }
        try {
            ThingMLCompiler.flattenModel(input);
            ThingMLCompiler.saveAsThingML(input, new File(model.getParent(), "/diversified/" + model.getName()).getAbsolutePath());
        } catch (RuntimeException e) {
            //Nasty dirty hack to hide the fact that sometime, save fails because the model is ill-formed. We could have done better with more money...
            e.printStackTrace();
            Diversifier.main(args);
        } catch (IOException e) {
            e.printStackTrace();
            Diversifier.main(args);
        }
    }

    public void diversify(Configuration cfg, int amount, int iterations) {
        final ThingMLModel model = (ThingMLModel) cfg.eContainer();

        for (int i = 0; i < iterations; i++) {
            for (Thing t : ThingMLHelpers.allThings(model)) {
                if (!AnnotatedElementHelper.isDefined(t, "diversify", "not")) {
                    changeOrderOfMessages(t);
                }
            }
            final TreeIterator<EObject> it = model.eAllContents();
            while (it.hasNext()) {
                final EObject o = it.next();
                if (o instanceof Message) {
                    final Thing t = ThingMLHelpers.findContainingThing(o);
                    if (!AnnotatedElementHelper.isDefined(t, "diversify", "not")) {
                        final Message m = (Message) o;
                        addRandomParameter(m, model);
                        upSizeParameter(m);
                        changeOrderOfParameter(m);
                    }
                }
            }
        }

        for (int i = 0; i < iterations; i++) {
            List<Message> msgs = new ArrayList<>();
            final TreeIterator<EObject> it2 = model.eAllContents();
            while (it2.hasNext()) {
                final EObject o = it2.next();
                if (o instanceof Thing) {
                    final Thing t = (Thing) o;
                    if (!AnnotatedElementHelper.isDefined(t, "diversify", "not")) {
                        for(Port p : t.getPorts()) {
                            if (!(p instanceof InternalPort)) {
                                final List<Message> sent = new ArrayList<>();
                                sent.addAll(p.getSends());
                                for(Message m : sent) {
                                    if (!msgs.contains(m)) {
                                        msgs.add(m);
                                        splitMessage(model, m);
                                    }
                                }
                                final List<Message> received = new ArrayList<>();
                                sent.addAll(p.getReceives());
                                for(Message m : received) {
                                    if (!msgs.contains(m)) {
                                        msgs.add(m);
                                        splitMessage(model, m);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        for (int i = 0; i < iterations; i++) {
            final TreeIterator<EObject> it3 = model.eAllContents();
            while (it3.hasNext()) {
                final EObject o = it3.next();
                if (o instanceof Message) {
                    final Thing t = ThingMLHelpers.findContainingThing(o);
                    if (!AnnotatedElementHelper.isDefined(t, "diversify", "not")) {
                        final Message m = (Message) o;
                        addRandomParameter(m, model);
                        changeOrderOfParameter(m);
                        generateCodeForMessage(m);
                    }
                }
            }
        }

        addLogs(model);
        //splitConfiguration(cfg, amount);
    }

    private void addLogs(ThingMLModel model) {
        //pretty print messages on send
        for (SendAction send : ActionHelper.getAllActions(model, SendAction.class)) {
            final Thing t = ThingMLHelpers.findContainingThing(send);
            final Port ip = createOrGetInternalPort(t);
            if (!EcoreUtil.equals(ip, send.getPort())) {
                final PrintAction print = ThingMLFactory.eINSTANCE.createPrintAction();
                print.setLine(true);
                final byte code = (byte)Short.parseShort(AnnotatedElementHelper.annotationOrElse(send.getMessage(), "code", "0x00").substring(2), 16);
                final StringLiteral codeliteral = ThingMLFactory.eINSTANCE.createStringLiteral();
                final StringLiteral comma = ThingMLFactory.eINSTANCE.createStringLiteral();
                codeliteral.setStringValue(""+code);
                comma.setStringValue(", ");
                print.getMsg().add(codeliteral);
                print.getMsg().add(EcoreUtil.copy(comma));
                for (Expression e : send.getParameters()) {
                    print.getMsg().add(EcoreUtil.copy(e));
                    print.getMsg().add(EcoreUtil.copy(comma));
                }
                final ActionBlock block = ThingMLFactory.eINSTANCE.createActionBlock();
                block.getActions().add(print);
                final Object parent = send.eContainer().eGet(send.eContainingFeature());
                if (parent instanceof EList) {
                    EList list = (EList) parent;
                    final int index = list.indexOf(send);
                    block.getActions().add(send);
                    list.add(index, block);
                    list.remove(send);
                } else {
                    block.getActions().add(send);
                    final EObject o = send.eContainer();
                    o.eSet(send.eContainingFeature(), block);
                }
            }
        }
    }

    private void addRandomParameter(Message m, ThingMLModel model) {
        System.out.println("Adding random parameter to message " + m.getName());
        int insertAt = rnd.nextInt(m.getParameters().size());
        final Parameter randomP = ThingMLFactory.eINSTANCE.createParameter();
        randomP.setName("r" + (param++));
        final TypeRef typeref = ThingMLFactory.eINSTANCE.createTypeRef();
        Type bt = null;
        for (Type t : model.getTypes()) {
            if (t instanceof PrimitiveType) {
                final PrimitiveType pt = (PrimitiveType) t;
                if (TyperHelper.getBroadType(pt) == Types.BYTE_TYPE) {
                    bt = pt;
                    break;
                }
            }
        }
        typeref.setType(bt);
        randomP.setTypeRef(typeref);
        m.getParameters().add(insertAt, randomP);

        //Update send actions
        for (Thing thing : ThingMLHelpers.allThings(model)) {
            for (SendAction send : ActionHelper.getAllActions(thing, SendAction.class)) {
                if (EcoreUtil.equals(send.getMessage(), m)) {
                    final FunctionCallExpression call = ThingMLFactory.eINSTANCE.createFunctionCallExpression();
                    Function rnd = null;
                    for (Function f : ThingMLHelpers.allFunctions(thing)) {
                        if (f.getName().equals("rnd")) {
                            rnd = f;
                            break;
                        }
                    }
                    call.setFunction(rnd);
                    send.getParameters().add(insertAt, call);
                }
            }
        }
    }

    /**
     * Upsize parameter e.g. Byte -> Integer
     *
     * @message m
     */
    private void upSizeParameter(Message m) {
        if (m.getParameters().size() == 0) return;
        int upsizeAt = rnd.nextInt(m.getParameters().size());
        final Parameter p = m.getParameters().get(upsizeAt);
        if (AnnotatedElementHelper.isDefined(p,"upsize", "not")) return;
        System.out.println("Upsizing parameter " + p.getName());
        final Type actual = TyperHelper.getBroadType(p.getTypeRef().getType());
        final ThingMLModel model = ThingMLHelpers.findContainingModel(p);
        for (Type t : ThingMLHelpers.allSimpleTypes(model)) {
            final Type newtype = TyperHelper.getBroadType(t);
            if (TyperHelper.isA(actual, newtype)) {
                p.getTypeRef().setType(t);
            }
        }
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
        if (m.getParameters().size() == 0) return;
        System.out.println("Changing parameter order for message " + m.getName());
        final List<Parameter> original = new ArrayList<>();
        original.addAll(m.getParameters());
        final List<Parameter> shuffled = new ArrayList<Parameter>();
        shuffled.addAll(m.getParameters());
        Collections.shuffle(shuffled);
        m.getParameters().clear();
        m.getParameters().addAll(shuffled);

        //Re-ordering actual parameters in send actions
        for (Thing thing : ThingMLHelpers.allThings(ThingMLHelpers.findContainingModel(m))) {
            for (SendAction send : ActionHelper.getAllActions(thing, SendAction.class)) {
                if (EcoreUtil.equals(send.getMessage(), m)) {
                    final List<Expression> params = new ArrayList<>();
                    for (Parameter p : send.getMessage().getParameters()) {
                        final int index = original.indexOf(p);
                        params.add(send.getParameters().get(index));
                    }
                    send.getParameters().clear();
                    send.getParameters().addAll(params);
                }
            }
        }
    }

    private void generateCodeForMessage(Message m) {
        PlatformAnnotation a = null;
        for (PlatformAnnotation annot : m.getAnnotations()) {
            if (annot.getName().equals("code")) {
                a = annot;
                break;
            }
        }
        if (a == null) {
            a = ThingMLFactory.eINSTANCE.createPlatformAnnotation();
            m.getAnnotations().add(a);
        }
        a.setName("code");
        a.setValue(String.format("0x%02X", code));
        if (code < 127) code++;
        else code = -128;
    }

    private Message createMessage(Thing t, Message m, List<Parameter> params) {
        final Message msg = ThingMLFactory.eINSTANCE.createMessage();
        String name = m.getName();
        for (Parameter p : params) {
            name = name + p.getName();
            msg.getParameters().add(EcoreUtil.copy(p));
        }
        msg.setName(name);

        for (Message message : t.getMessages()) {
            if (message.getName().equals(msg.getName()))
                return null;
        }

        System.out.println("Creating new message " + msg.getName());
        t.getMessages().add(msg);
        return msg;
    }

    private void splitSendAction(Thing thing, Port p, Message m, Message first, Message second, int splitAt) {
        for (SendAction send : ActionHelper.getAllActions(thing, SendAction.class)) {
            EObject eo = send.eContainer();
            while(eo != null) {
                if (eo instanceof AnnotatedElement) {
                    final AnnotatedElement a = (AnnotatedElement)eo;
                    if (AnnotatedElementHelper.isDefined(a, "diversify", "not")) {
                        return;
                    }
                }
                eo = eo.eContainer();
            }


            if (EcoreUtil.equals(send.getMessage(), m) && EcoreUtil.equals(send.getPort(), p)) {
                final ActionBlock block = ThingMLFactory.eINSTANCE.createActionBlock();

                final SendAction send1 = ThingMLFactory.eINSTANCE.createSendAction();
                send1.setMessage(first);
                send1.setPort(send.getPort());
                for (Expression e : send.getParameters().subList(0, splitAt)) {
                    send1.getParameters().add(EcoreUtil.copy(e));
                }
                block.getActions().add(send1);

                final SendAction send2 = ThingMLFactory.eINSTANCE.createSendAction();
                send2.setMessage(second);
                send2.setPort(send.getPort());
                for (Expression e : send.getParameters().subList(splitAt, m.getParameters().size())) {
                    send2.getParameters().add(EcoreUtil.copy(e));
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
    }

    /**
     * For this thing t, message m will be splitted
     * into m.parameters# messages. Both sides (instances
     * and their instance-specific types) will be updated
     * to properly handle sending/receiving of split message
     *
     * @param m
     */
    private void splitMessage(ThingMLModel model, Message m) {
        if (m.getParameters().size() < 2) return;
        int splitAt = rnd.nextInt(m.getParameters().size());
        if (splitAt == 0 || splitAt == m.getParameters().size()) return;

        final Message first = createMessage((Thing) m.eContainer(), m, m.getParameters().subList(0, splitAt));
        final Message second = createMessage((Thing) m.eContainer(), m, m.getParameters().subList(splitAt, m.getParameters().size()));
        if (first == null || second == null) return;

        System.out.println("Splitting message " + m.getName());
        final Map<Thing, Map<Message, Port>> mappings = new HashMap<>();

        for (Thing thing : ThingMLHelpers.allThings(model)) {
            final List<Port> ports = new ArrayList<>();
            ports.addAll(thing.getPorts());
            for (Port port : ports) {
                if (port.getSends().contains(m)) {
                    System.out.println(" adding messages " + first.getName() + " and " + second.getName() + " to sent messages of port " + port.getName() + " of thing " + thing.getName());
                    port.getSends().add(first);
                    port.getSends().add(second);
                    System.out.println(" removing message " + m.getName() + " from sent messages of port " + port.getName() + " of thing " + thing.getName());
                    port.getSends().remove(m);
                    splitSendAction(thing, port, m, first, second, splitAt);
                }
                if (port.getReceives().contains(m)) {
                    if (mappings.get(thing) == null) mappings.put(thing, new HashMap<>());
                    final Map<Message, Port> msgMappings = mappings.get(thing);
                    msgMappings.put(m, port);
                    final Port ip = createOrGetInternalPort(thing);
                    System.out.println(" removing message " + m.getName() + " from received messages of port " + port.getName() + " of thing " + thing.getName());
                    port.getReceives().remove(m);
                    System.out.println(" adding message " + first.getName() + " and " + second.getName() + " to received messages of port " + port.getName() + " of thing " + thing.getName());
                    port.getReceives().add(first);
                    port.getReceives().add(second);
                    System.out.println(" adding message " + m.getName() + " to internal port " + ip.getName() + " of thing " + thing.getName());
                    ip.getReceives().add(m);
                    ip.getSends().add(m);
                    createRegion(thing, port, m, first, second);
                    updateHandlers(thing, port, m);
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
                msg.setStringValue(createOrGetInternalPort(t).getName() + "!" + m.getName());
                print.getMsg().add(msg);
                block.getActions().add(print);
            }
            final SendAction send = ThingMLFactory.eINSTANCE.createSendAction();
            send.setPort(createOrGetInternalPort(t));
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

    private void updateHandlers(Thing thing, Port p, Message m) {
        TreeIterator<EObject> it = thing.eAllContents();
        while (it.hasNext()) {
            final EObject o = it.next();
            if (o instanceof Handler) {
                final Handler h = (Handler) o;
                if (/*!AnnotatedElementHelper.isDefined((State)h.eContainer(), "diversify", "not") && */h.getEvent() != null) {
                    final ReceiveMessage rm = (ReceiveMessage) h.getEvent();
                    if (EcoreUtil.equals(rm.getMessage(), m) && EcoreUtil.equals(rm.getPort(), p))
                        rm.setPort(createOrGetInternalPort(ThingMLHelpers.findContainingThing(rm)));
                }
            }
        }
    }

    private Port createOrGetInternalPort(Thing thing) {
        Port result = null;
        for (Port p : thing.getPorts()) {
            if (!(p instanceof InternalPort)) continue;
            if (p.getName().equals("diversified")) {
                result = p;
                break;
            }
        }
        if (result == null) {
            result = ThingMLFactory.eINSTANCE.createInternalPort();
            result.setName("diversified");
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

    /**
     * Splits configuration into a random number of configurations,
     * each configuration having at least one instance. Replace connectors
     * by external connectors as needed
     * NOTE: we should just use one protocol that works everywhere for now e.g. MQTT , but of course
     * protocols could also be replaced, etc.
     *
     * @param cfg
     */
    private void splitConfiguration(Configuration cfg, int amount) {
        if (cfg.getInstances().size() > amount) {
            System.out.println("Splitting configuration " + cfg.getName() + " in " + amount);
            final ThingMLModel model = (ThingMLModel) cfg.eContainer();
            int offset = model.getConfigs().size();
            for (int i = 0; i < amount; i++) {
                final Configuration c = ThingMLFactory.eINSTANCE.createConfiguration();
                c.setName(cfg.getName() + i);
                model.getConfigs().add(c);
            }
            final List<Instance> shuffled = new ArrayList<Instance>();
            shuffled.addAll(cfg.getInstances());
            Collections.shuffle(shuffled);
            cfg.getInstances().clear();
            cfg.getInstances().addAll(shuffled);
            List<Instance> instances = new ArrayList<>();
            instances.addAll(cfg.getInstances());
            int id = 0;
            for (Instance i : instances) {
                final Configuration c = model.getConfigs().get((id % amount) + offset);
                System.out.println("Adding instance " + i.getName() + " to configuration " + c.getName());
                c.getInstances().add(i);
                id++;
            }
            final List<AbstractConnector> connectors = new ArrayList<>();
            connectors.addAll(cfg.getConnectors());
            for (AbstractConnector c : connectors) {
                if (c instanceof Connector) {
                    final Connector conn = (Connector) c;
                    final Instance i1 = conn.getCli();
                    final Instance i2 = conn.getSrv();
                    if (i1.eContainer() == i2.eContainer()) {
                        ((Configuration) i1.eContainer()).getConnectors().add(conn);
                    } else {
                        final Configuration cfg1 = (Configuration) i1.eContainer();
                        final ExternalConnector ext1 = ThingMLFactory.eINSTANCE.createExternalConnector();
                        ext1.setInst(i1);
                        ext1.setPort(conn.getRequired());
                        ext1.setProtocol(model.getProtocols().get(0));//FIXME
                        cfg1.getConnectors().add(ext1);
                        final Configuration cfg2 = (Configuration) i2.eContainer();
                        final ExternalConnector ext2 = ThingMLFactory.eINSTANCE.createExternalConnector();
                        ext2.setInst(i2);
                        ext2.setPort(conn.getProvided());
                        ext2.setProtocol(model.getProtocols().get(0));//FIXME
                        cfg2.getConnectors().add(ext2);
                    }
                } else {//External connector
                    final ExternalConnector ext = (ExternalConnector) c;
                    final Configuration conf = (Configuration) ext.getInst().eContainer();
                    conf.getConnectors().add(ext);
                }
            }
            model.getConfigs().remove(cfg);
        } else {
            System.err.println("Cannot split configuration with " + cfg.getInstances().size() + " instances in " + amount);
        }
    }
}