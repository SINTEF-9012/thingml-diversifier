package no.sintef.thingml.diversifier.strategies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.constraints.Types;
import org.thingml.xtext.helpers.ActionHelper;
import org.thingml.xtext.helpers.StateHelper;
import org.thingml.xtext.helpers.TyperHelper;
import org.thingml.xtext.thingML.CastExpression;
import org.thingml.xtext.thingML.EventReference;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.ExpressionGroup;
import org.thingml.xtext.thingML.Handler;
import org.thingml.xtext.thingML.IntegerLiteral;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.MinusExpression;
import org.thingml.xtext.thingML.Parameter;
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
        		if (debug) System.out.println("Offseting parameters for message " + m.getName());

                for (SendAction send : ActionHelper.getAllActions(model, SendAction.class)) {
                    if (EcoreUtil.equals(send.getMessage(), m)) {
                        final List<Expression> params = new ArrayList<>();
                        params.addAll(send.getParameters());
                        int index = 0;
                        for (Expression p : params) {
                        	final Parameter param = send.getMessage().getParameters().get(index);
                            final TypeRef type = TyperHelper.getBroadType(param.getTypeRef());
                            index++;
                            if (!(type.getType() instanceof PrimitiveType) || !TyperHelper.isA(type, Types.INTEGER_TYPEREF))
                            	continue;
                            if (manager.rnd.nextBoolean())
                            	continue;
                            final ExpressionGroup group = ThingMLFactory.eINSTANCE.createExpressionGroup();
                            final PlusExpression plus = ThingMLFactory.eINSTANCE.createPlusExpression();
                            plus.setLhs(EcoreUtil.copy(p));
                            final IntegerLiteral i = ThingMLFactory.eINSTANCE.createIntegerLiteral();
                            final int offset = manager.rnd.nextInt(Math.max(4, (int)(8*((PrimitiveType)param.getTypeRef().getType()).getByteSize())));
                            i.setIntValue(offset);
                            plus.setRhs(i);
                            group.setTerm(plus);
                            EcoreUtil.replace(p, group);
                            mapping.put(param, offset);
                        }
                    }
                }               
            }
        }
        
        for(EventReference er : ThingMLHelpers.getAllExpressions(model, EventReference.class)) {
        	if (mapping.containsKey(er.getParameter())) {
        		final ExpressionGroup group = ThingMLFactory.eINSTANCE.createExpressionGroup();
        		final MinusExpression minus = ThingMLFactory.eINSTANCE.createMinusExpression();
        		if (er.eContainer() instanceof CastExpression) {
        			final ExpressionGroup group2 = ThingMLFactory.eINSTANCE.createExpressionGroup();
        			group2.setTerm((CastExpression)er.eContainer());
        			minus.setLhs(group2);
        		}
        		else minus.setLhs(er);
        		final IntegerLiteral offset = ThingMLFactory.eINSTANCE.createIntegerLiteral();
        		offset.setIntValue(mapping.get(er.getParameter()));
        		minus.setRhs(offset);
        		group.setTerm(minus);
        		EcoreUtil.replace(er, group);
        		/*final EObject container = er.eContainer();
        		final EStructuralFeature containment = er.eContainingFeature();
        		if (containment.isMany()) {
        			Object list = container.eGet(containment);
        			if (list instanceof EList<?>) {
        				EList<Expression> eList = (EList<Expression>) list;
        				eList.add(eList.indexOf(er), minus);
        				eList.remove(er);
        			}
        		} else {
        			container.eSet(containment, minus);
        		}  */
        	}
        
        /*final TreeIterator<EObject> it2 = model.eAllContents();
        while (it2.hasNext()) {
            final EObject o = it2.next();
            if (o instanceof Handler) {
                final Handler h = (Handler) o;
                List<EventReference> refs = new ArrayList<EventReference>();
                refs.addAll(ThingMLHelpers.getAllExpressions(h, EventReference.class));
                for(EventReference er : refs) {
                	              		
                		//EcoreUtil.replace(er, minus);
                	}
                }
            }*/
        }
	}

}
