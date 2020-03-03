package no.sintef.thingml.diversifier.strategies;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.constraints.Types;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.TyperHelper;
import org.thingml.xtext.thingML.ByteLiteral;
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
import no.sintef.thingml.diversifier.Strategies;

/**
 * 
 * FIXME: Generate better names, especially to avoid conflicts when we apply this strategy multiple time. As of now, it should work if applied once in static mode and once in dynamic mode
 *
 */
public class AddConstantParameters extends Strategy {
	
	public AddConstantParameters(Manager manager) {
		super(manager);
	}

	public AddConstantParameters(Manager manager, int i) {
		super(manager, i);
	}

	private static int param = 0;
	private Map<String, Integer> params = new HashMap<>();

	@Override
	protected void doApply(ThingMLModel model) {
		final TreeIterator<EObject> it = model.eAllContents();
        while (it.hasNext()) {
            final EObject o = it.next();
            if (o instanceof Message) {            	
            	final Message m = (Message) o;
            	if (!Manager.diversify(m)) continue;
            	int count = 0;
            	for (Parameter p : m.getParameters()) {
            		if (AnnotatedElementHelper.hasFlag(p, "noise")) count++;
            	}
            	int prob = (m.getParameters().isEmpty()) ? probability : (m.getParameters().size()-count)*probability/m.getParameters().size();
            	
            	if (manager.rnd.nextInt(10)<prob) {
            		final PlatformAnnotation annot = ThingMLFactory.eINSTANCE.createPlatformAnnotation();
                    annot.setName("diversify");
                    annot.setValue(Strategies.ADD_PARAM.name);
                    m.getAnnotations().add(annot);
            	} else continue;
                if (debug) System.out.println("Adding random parameter to message " + m.getName());
                final PlatformAnnotation annot = ThingMLFactory.eINSTANCE.createPlatformAnnotation();
                annot.setName("noise");
                        
                int insertAt = (m.getParameters().size() == 0) ? 0 : manager.rnd.nextInt(m.getParameters().size());
                final Parameter randomP = ThingMLFactory.eINSTANCE.createParameter();
                randomP.setName("val" + (param++));
                randomP.getAnnotations().add(annot);
                final TypeRef typeref = ThingMLFactory.eINSTANCE.createTypeRef();
                Type bt = null;
                for (Type t : model.getTypes()) {
                    if (t instanceof PrimitiveType) {
                        final PrimitiveType pt = (PrimitiveType) t;
                        final TypeRef tr = ThingMLFactory.eINSTANCE.createTypeRef();
                        tr.setType(pt);
                        if (TyperHelper.getBroadType(tr) == Types.INTEGER_TYPEREF && pt.getByteSize()==1) {
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
            	if (!AnnotatedElementHelper.isDefined(sa.getMessage(), "diversify", Strategies.ADD_PARAM.name)) continue;
                final ByteLiteral b = ThingMLFactory.eINSTANCE.createByteLiteral();
                b.setByteValue((byte) manager.rnd.nextInt(256));
                sa.getParameters().add(params.get(((Thing)sa.getMessage().eContainer()).getName() + "_" + sa.getMessage().getName()), b);
            }
        }
        
        for(Thing t : ThingMLHelpers.allThings(model)) {
        	for(Message m : t.getMessages()) {
        		PlatformAnnotation a = null;
            	for(PlatformAnnotation annot : m.getAnnotations()) {
            		if (annot.getName().equals("diversify") && annot.getValue().equals(Strategies.ADD_PARAM.name)) {
            			a = annot;
            			break;
            		}
            	}
            	if (a != null) m.getAnnotations().remove(a);
        	}
        }
                
	}

}
