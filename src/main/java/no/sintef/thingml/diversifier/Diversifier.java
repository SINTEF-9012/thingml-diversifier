package no.sintef.thingml.diversifier;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.compilers.ThingMLCompiler;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.ThingHelper;
import org.thingml.xtext.thingML.*;

import java.io.File;
import java.nio.file.Files;
import java.util.*;

class Diversifier {

    //TODO: Introduce probabilities of doing changes and randomness so as Diversifying multiple time the same input configuration actually generates multiple diversified results

	private Map<Instance, Thing> diversifiedThings = new HashMap<Instance, Thing>();
	private int functionCount = 0;
	private byte code;

	/**
	 * Inlines a function call
	 * @param call
	 */
	private void inlineFunctionCall(FunctionCallStatement call) {
		//TODO: later... maybe not so useful and tricky to get completely right
	}

	/**
	 * Inlines a function call
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
	 * @param block
	 */
	private void asFunctionCall(ActionBlock block) {
		//TODO: later... maybe not so useful and tricky to get completely right
	}

	/**
	 * Upsize parameter e.g. Byte -> Integer
	 * @param p
	 */
	private void upSizeParameter(Parameter p) {

	}


	/**
	 * Change order of messages
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

	/**
	 * For this connector c, message m will be splitted
	 * into m.parameters# messages. Both sides (instances
	 * and their instance-specific types) will be updated
	 * to properly handle sending/receiving of split message
	 * @param c
	 * @param m
	 */
	private void splitMessage(Connector c, Message m) {
        final Thing client = diversifiedThings.get(c.getCli());
        final Thing server = diversifiedThings.get(c.getSrv());
		for(Parameter p : m.getParameters()) {
		    //FIXME: avoid code duplication down there
			final Message pmsg = ThingMLFactory.eINSTANCE.createMessage();
			pmsg.setName(m.getName() + p.getName());
			pmsg.getParameters().add(EcoreUtil.copy(p));
            client.getMessages().add(pmsg);
            if (c.getRequired().getSends().contains(m)) {
                c.getRequired().getSends().add(pmsg);
            }
            if (c.getRequired().getReceives().contains(m)) {
                c.getRequired().getReceives().add(pmsg);
            }

			final Message pmsg2 = EcoreUtil.copy(pmsg);
			server.getMessages().add(pmsg2);
            if (c.getProvided().getSends().contains(m)) {
                c.getProvided().getSends().add(pmsg);
            }
            if (c.getProvided().getReceives().contains(m)) {
                c.getProvided().getReceives().add(pmsg);
            }
		}

		//NOTE: this probably does not work in case ports have multiple connectors...
        /*if (c.getRequired().getSends().contains(m)) {
            c.getRequired().getSends().remove(m);
        }
        if (c.getRequired().getReceives().contains(m)) {
            c.getRequired().getReceives().remove(m);
        }
        if (c.getProvided().getSends().contains(m)) {
            c.getProvided().getSends().remove(m);
        }
        if (c.getProvided().getReceives().contains(m)) {
            c.getProvided().getReceives().remove(m);
        }*/
    }

	/**
	 * Introduces a proxy, i.e. an instance between
	 * both sides of the connector c that simply receives
	 * and re-sends all messages normally going through connector
	 * @param c
	 */
	private void introduceProxy(Connector c) {
		//TODO: Use the ThingML injector to instantiate the Thing proxy from text (a bit too annoying to do it programmatically...)
	}

	private void diversify(Thing t) {
		changeOrderOfMessages(t);
		for (Thing i : ThingHelper.allIncludedThings(t)) {
			changeOrderOfMessages(i);
		}
		for (Message m : ThingMLHelpers.allMessages(t)) {
			changeOrderOfParameter(m);
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
     * @param cfg
     */
	private void splitConfiguration(Configuration cfg) {

    }

	public void diversify(Configuration cfg) {
		code = 0x00;
		diversifiedThings.clear();
		final ThingMLModel model = (ThingMLModel) cfg.eContainer();
		for(Instance i : cfg.getInstances()) {
			//We create a new Thing for each instance
			final Thing t = EcoreUtil.copy(i.getType());
			t.setName(t.getName() + i.getName());
			//instance does now instantiate new type
			i.setType(t);

			for(AbstractConnector c : cfg.getConnectors()) {
				if (c instanceof Connector) {
					if (((Connector) c).getCli() == i) {
						for (Port port : t.getPorts()) {
							if (port.getName().equals(((Connector) c).getRequired().getName())) {
								((Connector) c).setRequired((RequiredPort) port);
								break;
							}
						}
					} else if (((Connector) c).getSrv() == i) {
						for (Port port : t.getPorts()) {
							if (port.getName().equals(((Connector) c).getProvided().getName())) {
								((Connector) c).setProvided((ProvidedPort) port);
								break;
							}
						}
					}
				}
			}
			model.getTypes().add(t);
			diversifiedThings.put(i, t);
			diversify(t);
		}
		/*for(AbstractConnector c : cfg.getConnectors()) {
			if (c instanceof Connector)
				diversify((Connector)c);
		}*/
	}

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
		for(Configuration cfg : input.getConfigs()) {
			System.out.println("Diversifying configuration " + cfg.getName());
			diversifier.diversify(cfg);
		}
		ThingMLCompiler.flattenModel(input);
		ThingMLCompiler.saveAsThingML(input, new File(model.getParent(), "/diversified/" + model.getName()).getAbsolutePath());
	}
}