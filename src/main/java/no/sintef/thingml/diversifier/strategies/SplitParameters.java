package no.sintef.thingml.diversifier.strategies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.ActionHelper;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.EventReference;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.ExpressionGroup;
import org.thingml.xtext.thingML.IntegerLiteral;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.MinusExpression;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.PlatformAnnotation;
import org.thingml.xtext.thingML.PlusExpression;
import org.thingml.xtext.thingML.PrimitiveType;
import org.thingml.xtext.thingML.SendAction;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLFactory;
import org.thingml.xtext.thingML.ThingMLModel;

import no.sintef.thingml.diversifier.Manager;

public class SplitParameters extends Strategy {

	public SplitParameters(Manager manager) {
		super(manager);
	}

	public SplitParameters(Manager manager, int i) {
		super(manager, i);
	}

	@Override
	protected void doApply(ThingMLModel model) {
		final Map<Parameter, Parameter> mappings = new HashMap<Parameter, Parameter>();
		
		
		final TreeIterator<EObject> it = model.eAllContents();
		while (it.hasNext()) {
			final EObject o = it.next();
			if (!(o instanceof Thing)) continue;
			final Thing thing = (Thing)o;
			if (AnnotatedElementHelper.hasFlag(thing, "stl")) continue;			
			final List<Message> msgs = new ArrayList<Message>();
			msgs.addAll(thing.getMessages());
			for (Message msg : msgs) {
				if (!Manager.diversify(msg)) continue;
				final List<Parameter> params = new ArrayList<Parameter>();
				params.addAll(msg.getParameters());
				for(Parameter p : params) {
					int prob = probability;
					if (AnnotatedElementHelper.hasFlag(p, "split")) continue;
					if (manager.rnd.nextInt(10)<prob) {
						if (!AnnotatedElementHelper.hasFlag(p, "split")) {
		                	final PlatformAnnotation annot = ThingMLFactory.eINSTANCE.createPlatformAnnotation();
		                	annot.setName("split");
		                	p.getAnnotations().add(annot);
		                }
						
						final Parameter newP = EcoreUtil.copy(p);
						newP.setName(p.getName() + "_split");
						msg.getParameters().add(newP);						                                                
                        mappings.put(p, newP);
					}
				}
			}
		}
		
		for (SendAction send : ActionHelper.getAllActions(model, SendAction.class)) {			
			for(Parameter p : send.getMessage().getParameters()) {
				if (!mappings.containsKey(p)) continue;
				final ExpressionGroup g = ThingMLFactory.eINSTANCE.createExpressionGroup();
				final Expression e = send.getParameters().get(send.getMessage().getParameters().indexOf(p));
				final PlusExpression plus = ThingMLFactory.eINSTANCE.createPlusExpression();
				plus.setLhs(EcoreUtil.copy(e));
				final IntegerLiteral i = ThingMLFactory.eINSTANCE.createIntegerLiteral();
				final int offset = Math.max(1, manager.rnd.nextInt(Math.max(2, (int)(3*((PrimitiveType)p.getTypeRef().getType()).getByteSize()))));
				i.setIntValue(offset);
				plus.setRhs(i);
				EcoreUtil.replace(e, g);
				g.setTerm(plus);
				send.getParameters().add(EcoreUtil.copy(i));
			}
		}
			
		for(EventReference er : ThingMLHelpers.getAllExpressions(model, EventReference.class)) {
        	if (mappings.containsKey(er.getParameter())) {
        		final ExpressionGroup group = ThingMLFactory.eINSTANCE.createExpressionGroup();
        		EcoreUtil.replace(er, group);
        		final MinusExpression minus = ThingMLFactory.eINSTANCE.createMinusExpression();
        		minus.setLhs(er);
        		final EventReference newEr = EcoreUtil.copy(er);         				
        		newEr.setParameter(mappings.get(er.getParameter()));
        		minus.setRhs(newEr);
        		group.setTerm(minus);
        	}
        }
		
		
	}

}
