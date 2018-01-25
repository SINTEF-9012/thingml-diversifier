package no.sintef.thingml.diversifier;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.xtext.thingML.*;

import java.util.*;

class Diversifier {

    //TODO: Introduce probabilities of doing changes and randomness so as Diversifying multiple time the same input configuration actually generates multiple diversified results

	private Map<Instance, Thing> diversifiedThings = new HashMap<Instance, Thing>();
	private int functionCount = 0;

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

	}

	private void diversify(Connector c) {
        
	}

	public void diversify(Configuration cfg) {
		diversifiedThings.clear();
		for(Instance i : cfg.getInstances()) {
			//We create a new Thing for each instance
			final Thing t = EcoreUtil.copy(i.getType());
			t.setName(t.getName() + i.getName());
			//instance does now instantiate new type
			i.setType(t);
			diversifiedThings.put(i, t);
			diversify(t);
		}
		for(AbstractConnector c : cfg.getConnectors()) {
			if (c instanceof Connector)
				diversify((Connector)c);
		}
	}
	
}