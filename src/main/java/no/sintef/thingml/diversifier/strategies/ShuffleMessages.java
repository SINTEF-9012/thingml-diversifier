package no.sintef.thingml.diversifier.strategies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;

import no.sintef.thingml.diversifier.Manager;

public class ShuffleMessages extends Strategy {

	@Override
	protected void doApply(ThingMLModel model) {
		final TreeIterator<EObject> it = model.eAllContents();
        while (it.hasNext()) {
            final EObject o = it.next();
            if (o instanceof Thing) {
            	final Thing t = (Thing) o;
            	if (Manager.diversify(t)) {
            		System.out.println("Shuffling messages of thing " + t.getName());
            		final List<Message> shuffled = new ArrayList<Message>();
            		shuffled.addAll(t.getMessages());
            		Collections.shuffle(shuffled, Manager.rnd);
            		t.getMessages().clear();
            		t.getMessages().addAll(shuffled);
            	}
            }
        }
	}

}
