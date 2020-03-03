package no.sintef.thingml.diversifier.strategies;

import java.util.ArrayList;
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
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.TyperHelper;
import org.thingml.xtext.thingML.CastExpression;
import org.thingml.xtext.thingML.EventReference;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.ExpressionGroup;
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

	public OffsetParameters(Manager manager, int i) {
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
        		if (debug) System.out.println("Offseting parameters for message " + m.getName());

                for (SendAction send : ActionHelper.getAllActions(model, SendAction.class)) {
                    if (EcoreUtil.equals(send.getMessage(), m)) {
                        final List<Expression> params = new ArrayList<>();
                        params.addAll(send.getParameters());
                        int index = 0;
                        int count = 0;
                        for (Expression p : params) {
                        	final Parameter param = send.getMessage().getParameters().get(index);
                        	if (AnnotatedElementHelper.hasFlag(param, "noise")) continue; //This is a random parameter, no point in offsetting it
                            final TypeRef type = TyperHelper.getBroadType(param.getTypeRef());
                            index++;
                            if (!(type.getType() instanceof PrimitiveType) || !TyperHelper.isA(type, Types.INTEGER_TYPEREF))
                            	continue;
                            if (manager.rnd.nextInt(10)<((params.size()-count)*probability/params.size()))
                            	continue;
                            count++;
                            final ExpressionGroup group = ThingMLFactory.eINSTANCE.createExpressionGroup();
                            final PlusExpression plus = ThingMLFactory.eINSTANCE.createPlusExpression();
                            plus.setLhs(EcoreUtil.copy(p));
                            final IntegerLiteral i = ThingMLFactory.eINSTANCE.createIntegerLiteral();
                            final int offset = Math.max(1, manager.rnd.nextInt(Math.max(2, (int)(3*((PrimitiveType)param.getTypeRef().getType()).getByteSize()))));
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
        		final IntegerLiteral offset = ThingMLFactory.eINSTANCE.createIntegerLiteral();
        		offset.setIntValue(mapping.get(er.getParameter()));
           		final ExpressionGroup group2 = ThingMLFactory.eINSTANCE.createExpressionGroup();
           		final MinusExpression minus = ThingMLFactory.eINSTANCE.createMinusExpression();
           		if (er.eContainer() instanceof CastExpression) {
           			final CastExpression cast = (CastExpression) er.eContainer();
           			EcoreUtil.replace(cast, group2);
           			minus.setLhs(cast);
           		}
           		else {
           			EcoreUtil.replace(er, group2);
           			minus.setLhs(er);
           		}
       			minus.setRhs(offset);
           		group2.setTerm(minus);
        	}
        }
	}

}
