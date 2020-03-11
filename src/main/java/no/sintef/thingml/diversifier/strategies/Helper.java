package no.sintef.thingml.diversifier.strategies;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.TyperHelper;
import org.thingml.xtext.thingML.Enumeration;
import org.thingml.xtext.thingML.PrimitiveType;
import org.thingml.xtext.thingML.ThingMLFactory;
import org.thingml.xtext.thingML.Type;
import org.thingml.xtext.thingML.TypeRef;

public class Helper {
	
	public static PrimitiveType getPrimitiveType(TypeRef t, EObject o) {
		for(Type ty : ThingMLHelpers.findContainingModel(o).getTypes()) {
			if (ty instanceof PrimitiveType) {
				final TypeRef tr = ThingMLFactory.eINSTANCE.createTypeRef();
                tr.setType(ty);			
				if (EcoreUtil.equals(TyperHelper.getBroadType(tr), t)) {			
					return (PrimitiveType) ty;
				}
			}
		}
		throw new NullPointerException("Cannot find Type " + t.getType().getName() +"in the ThingML model containing " + o);	
	}

	
	public static long getSize(Type t) {
		long size = 1; //by default, assume Byte type
		if (t instanceof PrimitiveType)
			size = ((PrimitiveType)t).getByteSize();
		else if (t instanceof Enumeration)
			size = ((PrimitiveType)((Enumeration)t).getTypeRef().getType()).getByteSize();		
		return size;
	}
}
