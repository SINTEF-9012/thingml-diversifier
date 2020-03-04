package no.sintef.thingml.diversifier.strategies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
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
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.PlatformAnnotation;
import org.thingml.xtext.thingML.PrimitiveType;
import org.thingml.xtext.thingML.SendAction;
import org.thingml.xtext.thingML.ThingMLFactory;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.thingML.TypeRef;

import no.sintef.thingml.diversifier.Manager;

public class BitShiftParameters extends Strategy {

	public BitShiftParameters(Manager manager) {
		super(manager);
	}

	public BitShiftParameters(Manager manager, int i) {
		super(manager, i);
	}

	@Override
	protected void doApply(ThingMLModel model) {
		final Map<Parameter, Integer> mapping = new HashMap<Parameter, Integer>();
		final TreeIterator<EObject> it = model.eAllContents();
        while (it.hasNext()) {
            final EObject o = it.next();
            if (o instanceof Message) {
                final Message m = (Message) o;
                //if (AnnotatedElementHelper.hasFlag(ThingMLHelpers.findContainingThing(m), "stl")) continue;
        		if (m.getParameters().size() == 0 || !Manager.diversify(m)) continue;
        		if (debug) System.out.println("Bit-shifting parameters for message " + m.getName());

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
                            if (!(type.getType() instanceof PrimitiveType) || !TyperHelper.isA(type, Types.INTEGER_TYPEREF))
                            	continue;
                            if (AnnotatedElementHelper.hasFlag(param, "shift")) {
                            	continue;
                            }
                            int prob = probability;
                            if (AnnotatedElementHelper.hasFlag(param, "offset")) {
                            	prob = Math.max(1, probability - 1);
                            }
                            if (manager.rnd.nextInt(10)<prob)
                            	continue;
                            
                            if (!AnnotatedElementHelper.hasFlag(param, "shift")) {
                            	final PlatformAnnotation a = ThingMLFactory.eINSTANCE.createPlatformAnnotation();
                            	a.setName("shift");
                            	param.getAnnotations().add(a);
                            }
                            if (AnnotatedElementHelper.hasFlag(param, "offset")) {
                            	PlatformAnnotation toRemove = null;
                            	for(PlatformAnnotation a : param.getAnnotations()) {
                            		if (a.getName().equals("offset")) {
                            			toRemove = a;
                            			break;
                            		}
                            	}
                            	param.getAnnotations().remove(toRemove);
                            }
                            
                            final ExpressionGroup group = ThingMLFactory.eINSTANCE.createExpressionGroup();	                			
            				final ExternExpression expr = ThingMLFactory.eINSTANCE.createExternExpression();
            				expr.setExpression("(((");
            				expr.getSegments().add(EcoreUtil.copy(p));            				
            				
            				final ExternExpression expr2 = ThingMLFactory.eINSTANCE.createExternExpression();
            				final int shift = Math.max(1, manager.rnd.nextInt(Math.max(2, (int)(3*((PrimitiveType)param.getTypeRef().getType()).getByteSize()))));
            				expr2.setExpression(") << " + shift + ") | ((");
            				expr2.getSegments().add(EcoreUtil.copy(p));
            				expr.getSegments().add(expr2);
            				
            				final ExternExpression expr3 = ThingMLFactory.eINSTANCE.createExternExpression();
            				expr3.setExpression(") >>> " + (-shift) + "))");
            				expr.getSegments().add(expr3);
            				
            				group.setTerm(expr);
            				EcoreUtil.replace(p, group);
                            mapping.put(param, shift);
                        }
                    }
                }               
            }
        }
        
        for(EventReference er : ThingMLHelpers.getAllExpressions(model, EventReference.class)) {
        	if (mapping.containsKey(er.getParameter())) {
        		final int shift = mapping.get(er.getParameter());
        		
        		final ExpressionGroup group2 = ThingMLFactory.eINSTANCE.createExpressionGroup();
        		final ExternExpression expr = ThingMLFactory.eINSTANCE.createExternExpression();
				expr.setExpression("(");
				Expression instance = null;
        		if (er.eContainer() instanceof CastExpression) {
           			final CastExpression cast = (CastExpression) er.eContainer();
           			EcoreUtil.replace(cast, group2);
           			instance = cast;
           		}
           		else {
           			EcoreUtil.replace(er, group2);
           			instance = er;
           		}
				
				final ExternExpression bitshift = ThingMLFactory.eINSTANCE.createExternExpression();
				bitshift.setExpression("((");
				bitshift.getSegments().add(EcoreUtil.copy(instance));            				
				
				final ExternExpression expr2 = ThingMLFactory.eINSTANCE.createExternExpression();
				expr2.setExpression(") >>> " + shift + ") | ((");
				expr2.getSegments().add(EcoreUtil.copy(instance));
				bitshift.getSegments().add(expr2);
				
				final ExternExpression expr3 = ThingMLFactory.eINSTANCE.createExternExpression();
				expr3.setExpression(") << " + (-shift) + "))");
				bitshift.getSegments().add(expr3);
				expr.getSegments().add(bitshift);
				
				group2.setTerm(expr);	
        	}
        }
	}

}
