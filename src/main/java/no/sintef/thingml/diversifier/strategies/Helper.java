package no.sintef.thingml.diversifier.strategies;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.TyperHelper;
import org.thingml.xtext.thingML.InternalPort;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.PrimitiveType;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLFactory;
import org.thingml.xtext.thingML.Type;

public class Helper {
    
	public static InternalPort createOrGetInternalPort(Thing thing) {        
        for (Port p : thing.getPorts()) {
            if (!(p instanceof InternalPort)) continue;
            if (p.getName().equals("diversified")) {
                return (InternalPort)p;
            }
        }
        /*final PlatformAnnotation a = ThingMLFactory.eINSTANCE.createPlatformAnnotation();
        a.setName("sync_send");
        a.setValue("true");*/
        final InternalPort result = ThingMLFactory.eINSTANCE.createInternalPort(); 
        result.setName("diversified");
        //result.getAnnotations().add(a);
        thing.getPorts().add(result);
        return result;
    }

	public static PrimitiveType getPrimitiveType(Type t, EObject o) {
		for(Type ty : ThingMLHelpers.findContainingModel(o).getTypes()) {
			if (ty instanceof PrimitiveType && EcoreUtil.equals(TyperHelper.getBroadType(ty), t)) {
				return (PrimitiveType) ty;
			}
		}
		throw new NullPointerException("Cannot find Type " + t.getName() +"in the ThingML model containing " + o);	
	}
}
