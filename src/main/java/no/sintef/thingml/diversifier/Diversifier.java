package no.sintef.thingml.diversifier;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.thingML.*;

import java.util.*;

class Diversifier {

	private Map<Instance, Thing> diversifiedThings = new HashMap<Instance, Thing>();
	private int functionCount = 0;

	/**
	 * Inlines a function call
	 * @param call
	 */
	private void inlineFunctionCall(FunctionCallStatement call) {

	}

	/**
	 * Inlines a function call
	 * @param call
	 */
	private void inlineFunctionCall(FunctionCallExpression call) {

	}

	/**
	 * Transforms a an action block in a call to a function
	 * implementing this block. Note that the block can be
	 * pre-existing or created on the fly by arbitrarily
	 * grouping successive n-actions in a block
	 * @param block
	 */
	private void asFunctionCall(ActionBlock block) {
		final Thing t = ThingMLHelpers.findContainingThing(block);
		final Object parent = block.eContainer().eGet(block.eContainingFeature());
		if (parent instanceof EList) {

		} else {

		}
		final Function f = ThingMLFactory.eINSTANCE.createFunction();
		f.setAbstract(false);
		f.setBody();
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
		final Thing thingMsgs = (Thing) m.eContainer();
		for(Parameter p : m.getParameters()) {
			final Message pmsg = ThingMLFactory.eINSTANCE.createMessage();
			pmsg.setName(m.getName() + p.getName());
			pmsg.getParameters().add(p);
		}
		thingMsgs.getMessages().remove(m);
	}

	/**
	 * Introduces a proxy, i.e. an instance between
	 * both sides of the connector c that simply receives
	 * and re-sends message m
	 * @param c
	 * @param m
	 */
	private void introduceProxy(Connector c, Message m) {
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