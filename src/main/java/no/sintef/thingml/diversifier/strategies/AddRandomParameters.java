package no.sintef.thingml.diversifier.strategies;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.constraints.Types;
import org.thingml.xtext.helpers.TyperHelper;
import org.thingml.xtext.thingML.ByteLiteral;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.FunctionCallExpression;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.PlatformAnnotation;
import org.thingml.xtext.thingML.PrimitiveType;
import org.thingml.xtext.thingML.SendAction;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLFactory;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.thingML.Type;
import org.thingml.xtext.thingML.TypeRef;

import no.sintef.thingml.diversifier.Manager;
import no.sintef.thingml.diversifier.Mode;

public class AddRandomParameters extends Strategy {
	
	private int param = 0;
	private Map<String, Integer> params = new HashMap<>();

	@Override
	protected void doApply(ThingMLModel model) {
		final TreeIterator<EObject> it = model.eAllContents();
        while (it.hasNext()) {
            final EObject o = it.next();
            if (o instanceof Message) {            	
            	final Message m = (Message) o;
            	if (!Manager.diversify(m)) continue;
                System.out.println("Adding random parameter to message " + m.getName());
                final PlatformAnnotation annot = ThingMLFactory.eINSTANCE.createPlatformAnnotation();
                annot.setName("noise");
                        
                int insertAt = (m.getParameters().size() == 0) ? 0 : Manager.rnd.nextInt(m.getParameters().size());
                final Parameter randomP = ThingMLFactory.eINSTANCE.createParameter();
                randomP.setName("r" + (param++));
                randomP.getAnnotations().add(annot);
                final TypeRef typeref = ThingMLFactory.eINSTANCE.createTypeRef();
                Type bt = null;
                for (Type t : model.getTypes()) {
                    if (t instanceof PrimitiveType) {
                        final PrimitiveType pt = (PrimitiveType) t;
                        if (TyperHelper.getBroadType(pt) == Types.BYTE_TYPE) {
                            bt = pt;
                            break;
                        }
                    }
                }
                typeref.setType(bt);
                randomP.setTypeRef(typeref);
                m.getParameters().add(insertAt, randomP);
                params.put(((Thing)m.eContainer()).getName() + "_" + m.getName(), insertAt);
            }
        }
		final TreeIterator<EObject> it2 = model.eAllContents();
        while (it2.hasNext()) {
            final EObject o = it2.next();
            if (o instanceof SendAction) {
            	final SendAction sa = (SendAction) o;
            	if (!Manager.diversify(sa.getMessage())) continue;
                final Function rnd = Manager.findRandom(ThingMLHelpers.findContainingThing(sa));
                if (rnd != null && Manager.mode == Mode.DYNAMIC) {
                    final FunctionCallExpression call = ThingMLFactory.eINSTANCE.createFunctionCallExpression();
                    call.setFunction(rnd);
                    sa.getParameters().add(params.get(((Thing)sa.getMessage().eContainer()).getName() + "_" + sa.getMessage().getName()), call);
                } else {
                    final ByteLiteral b = ThingMLFactory.eINSTANCE.createByteLiteral();
                    b.setByteValue((byte) Manager.rnd.nextInt(256));
                    sa.getParameters().add(params.get(((Thing)sa.getMessage().eContainer()).getName() + "_" + sa.getMessage().getName()), b);
                }
            }
        }
	}

}
