package no.sintef.thingml.diversifier.strategies;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.TyperHelper;
import org.thingml.xtext.thingML.PrimitiveType;
import org.thingml.xtext.thingML.Type;

public class Helper {

	public static PrimitiveType getPrimitiveType(Type t, EObject o) {
		for(Type ty : ThingMLHelpers.findContainingModel(o).getTypes()) {
			if (ty instanceof PrimitiveType && EcoreUtil.equals(TyperHelper.getBroadType(ty), t)) {
				return (PrimitiveType) ty;
			}
		}
		throw new NullPointerException("Cannot find Type " + t.getName() +"in the ThingML model containing " + o);	
	}
}
