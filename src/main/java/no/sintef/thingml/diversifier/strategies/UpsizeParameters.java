package no.sintef.thingml.diversifier.strategies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.TyperHelper;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.PrimitiveType;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.thingML.Type;

import no.sintef.thingml.diversifier.Manager;

public class UpsizeParameters extends Strategy {

	@Override
	protected void doApply(ThingMLModel model) {
		final TreeIterator<EObject> it = model.eAllContents();
        while (it.hasNext()) {
            final EObject o = it.next();
            if (o instanceof Parameter) {
            	final Parameter p = (Parameter) o;
            	if (!(p.eContainer() instanceof Message)) return;
            	if (AnnotatedElementHelper.isDefined(p, "upsize", "not")) return;
            	final PrimitiveType original = (PrimitiveType)p.getTypeRef().getType();            	
            	// Find all compatible types
            	final List<PrimitiveType> options = new ArrayList<PrimitiveType>();
            	// Check all types in model, and find a different one, that is compatible, and same size or bigger
                for (Type t : ThingMLHelpers.allSimpleTypes(model)) {
                	if (t instanceof PrimitiveType) {
                		final PrimitiveType other = (PrimitiveType)t;
                		if (TyperHelper.isA(original, other) && other.getByteSize() >= original.getByteSize()) {
                			options.add(other);
                		}
                	}
                }
                // Select a new type (might be the same as the original)
                final PrimitiveType newtype = options.get(Manager.rnd.nextInt(options.size()));
                p.getTypeRef().setType(newtype);
            }
        }
	}

}
