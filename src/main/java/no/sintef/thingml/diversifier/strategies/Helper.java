package no.sintef.thingml.diversifier.strategies;

import org.thingml.xtext.thingML.InternalPort;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLFactory;

public class Helper {
    public static Port createOrGetInternalPort(Thing thing) {
        Port result = null;
        for (Port p : thing.getPorts()) {
            if (!(p instanceof InternalPort)) continue;
            if (p.getName().equals("diversified")) {
                result = p;
                break;
            }
        }
        if (result == null) {
            result = ThingMLFactory.eINSTANCE.createInternalPort();
            result.setName("diversified");
            thing.getPorts().add(result);
        }
        return result;
    }
}
