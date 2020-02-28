package no.sintef.thingml.diversifier.strategies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;

import no.sintef.thingml.diversifier.Manager;

public class ShufflePort extends Strategy {

	public ShufflePort(Manager manager) {
		super(manager);
	}

	@Override
	protected void doApply(ThingMLModel model) {
		final TreeIterator<EObject> it = model.eAllContents();
        while (it.hasNext()) {
            final EObject o = it.next();
            if (o instanceof Thing) {
            	final Thing t = (Thing) o;
            	if (Manager.diversify(t)) {
            		if (debug) System.out.println("Shuffling ports of thing " + t.getName());
            		final List<Port> shuffled = new ArrayList<Port>();
            		shuffled.addAll(t.getPorts());
            		Collections.shuffle(shuffled, manager.rnd);
            		t.getPorts().clear();
            		t.getPorts().addAll(shuffled);
            	}
            }
        }
	}

}
