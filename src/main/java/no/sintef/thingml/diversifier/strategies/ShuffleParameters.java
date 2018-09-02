package no.sintef.thingml.diversifier.strategies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.ActionHelper;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.SendAction;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;

import no.sintef.thingml.diversifier.Manager;

public class ShuffleParameters extends Strategy {

	@Override
	protected void doApply(ThingMLModel model) {
		final TreeIterator<EObject> it = model.eAllContents();
        while (it.hasNext()) {
            final EObject o = it.next();
            if (o instanceof Message) {
                final Message m = (Message) o;
        		if (m.getParameters().size() == 0 || !Manager.diversify(m)) continue;
                System.out.println("Changing parameter order for message " + m.getName());
                final List<Parameter> original = new ArrayList<>();
                original.addAll(m.getParameters());
                final List<Parameter> shuffled = new ArrayList<Parameter>();
                shuffled.addAll(m.getParameters());
                Collections.shuffle(shuffled, Manager.rnd);
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
        }
	}

}
