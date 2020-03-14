package no.sintef.thingml.diversifier.strategies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.compilers.java.JavaHelper;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.constraints.Types;
import org.thingml.xtext.helpers.ActionHelper;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.TyperHelper;
import org.thingml.xtext.thingML.CastExpression;
import org.thingml.xtext.thingML.EventReference;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.ExpressionGroup;
import org.thingml.xtext.thingML.ExternExpression;
import org.thingml.xtext.thingML.IntegerLiteral;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.MinusExpression;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.PlatformAnnotation;
import org.thingml.xtext.thingML.PlusExpression;
import org.thingml.xtext.thingML.PrimitiveType;
import org.thingml.xtext.thingML.SendAction;
import org.thingml.xtext.thingML.ThingMLFactory;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.thingML.TypeRef;

import no.sintef.thingml.diversifier.Manager;

public class OffsetParameters extends Strategy {

	public OffsetParameters(Manager manager) {
		super(manager);
	}

	public OffsetParameters(Manager manager, int i) {
		super(manager, i);
	}

	@Override
	protected void doApply(ThingMLModel model) {
		final Map<Parameter, Long> mapping = new HashMap<Parameter, Long>();
		final TreeIterator<EObject> it = model.eAllContents();
        while (it.hasNext()) {
            final EObject o = it.next();
            if (o instanceof Message) {
                final Message m = (Message) o;
                //if (AnnotatedElementHelper.hasFlag(ThingMLHelpers.findContainingThing(m), "stl")) continue;
        		if (m.getParameters().size() == 0 || !Manager.diversify(m)) continue;
        		if (debug) System.out.println("Offseting parameters for message " + m.getName());

                for (SendAction send : ActionHelper.getAllActions(model, SendAction.class)) {
                    if (EcoreUtil.equals(send.getMessage(), m)) {
                        final List<Expression> params = new ArrayList<>();
                        params.addAll(send.getParameters());
                        int index = 0;
                        for (Expression p : params) {
                        	final Parameter param = send.getMessage().getParameters().get(index);
                        	if (AnnotatedElementHelper.hasFlag(param, "noise")) continue; //This is a random parameter, no point in offsetting it
                            final TypeRef type = TyperHelper.getBroadType(param.getTypeRef());
                            index++;
                            if (!(param.getTypeRef().getType() instanceof PrimitiveType) || !TyperHelper.isA(type, Types.INTEGER_TYPEREF))
                            	continue;
                            if (AnnotatedElementHelper.hasFlag(param, "offset")) {
                            	continue;
                            }
                            int prob = probability;
                            if (AnnotatedElementHelper.hasFlag(param, "shift")) {
                            	prob = 1;
                            }
                            if (manager.rnd.nextInt(10)<prob)
                            	continue;
                            
                            if (!AnnotatedElementHelper.hasFlag(param, "offset")) {
                            	final PlatformAnnotation a = ThingMLFactory.eINSTANCE.createPlatformAnnotation();
                            	a.setName("offset");
                            	param.getAnnotations().add(a);
                            }
                            if (AnnotatedElementHelper.hasFlag(param, "shift")) {
                            	PlatformAnnotation toRemove = null;
                            	for(PlatformAnnotation a : param.getAnnotations()) {
                            		if (a.getName().equals("shift")) {
                            			toRemove = a;
                            			break;
                            		}
                            	}
                            	param.getAnnotations().remove(toRemove);
                            }
                            
                            final ExpressionGroup group = ThingMLFactory.eINSTANCE.createExpressionGroup();
                            final ExternExpression ext = ThingMLFactory.eINSTANCE.createExternExpression();
            				if ("java".equals(manager.compiler)) {					
            					ext.setExpression("(" + JavaHelper.getJavaType(param.getTypeRef().getType(), param.getTypeRef().isIsArray()) + ")");
            					ext.getSegments().add(group);
            				}	
                            
                            
                            final PlusExpression plus = ThingMLFactory.eINSTANCE.createPlusExpression();
                            plus.setLhs(EcoreUtil.copy(p));
                            final IntegerLiteral i = ThingMLFactory.eINSTANCE.createIntegerLiteral();
                            final int size = (int) Helper.getSize(param.getTypeRef().getType());
                            final long offset = (long) (Math.pow(2, 8*size-2 ) + manager.rnd.nextInt((int)(Math.pow(2, 8*size-1 ))));
                            i.setIntValue(offset);
                            plus.setRhs(i);
                            group.setTerm(plus);
            				if ("java".equals(manager.compiler))
            					EcoreUtil.replace(p, ext);
            				else
            					EcoreUtil.replace(p, group);
                            mapping.put(param, offset);
                        }
                    }
                }               
            }
        }
        
        for(EventReference er : ThingMLHelpers.getAllExpressions(model, EventReference.class)) {
        	if (mapping.containsKey(er.getParameter())) {
        		final IntegerLiteral offset = ThingMLFactory.eINSTANCE.createIntegerLiteral();
        		offset.setIntValue(mapping.get(er.getParameter()));
           		final ExpressionGroup group2 = ThingMLFactory.eINSTANCE.createExpressionGroup();
           		final ExternExpression ext = ThingMLFactory.eINSTANCE.createExternExpression();
				if ("java".equals(manager.compiler)) {					
					ext.setExpression("(" + JavaHelper.getJavaType(er.getParameter().getTypeRef().getType(), er.getParameter().getTypeRef().isIsArray()) + ")");
					ext.getSegments().add(group2);
				}	
           		
           		final MinusExpression minus = ThingMLFactory.eINSTANCE.createMinusExpression();
           		if (er.eContainer() instanceof CastExpression) {
           			final CastExpression cast = (CastExpression) er.eContainer();
           			if ("java".equals(manager.compiler))
           				EcoreUtil.replace(cast, ext);
           			else
           				EcoreUtil.replace(cast, group2);
           			minus.setLhs(cast);
           		}
           		else {
           			if ("java".equals(manager.compiler))
           				EcoreUtil.replace(er, ext);
           			else
           				EcoreUtil.replace(er, group2);
           			minus.setLhs(er);
           		}
       			minus.setRhs(offset);
           		group2.setTerm(minus);
        	}
        }
	}

}
