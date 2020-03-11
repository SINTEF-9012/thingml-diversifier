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
import org.thingml.xtext.helpers.ActionHelper;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.ActionBlock;
import org.thingml.xtext.thingML.ByteLiteral;
import org.thingml.xtext.thingML.Enumeration;
import org.thingml.xtext.thingML.EventReference;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.ExpressionGroup;
import org.thingml.xtext.thingML.ExternExpression;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.FunctionCallExpression;
import org.thingml.xtext.thingML.IntegerLiteral;
import org.thingml.xtext.thingML.LocalVariable;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.MinusExpression;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.PlatformAnnotation;
import org.thingml.xtext.thingML.PlusExpression;
import org.thingml.xtext.thingML.PrimitiveType;
import org.thingml.xtext.thingML.PropertyReference;
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
					//if (Helper.getSize(p.getTypeRef().getType())<2) continue;
					if (AnnotatedElementHelper.hasFlag(p, "split")) continue;
					if (p.getTypeRef().getType() instanceof Enumeration) continue;
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
			final ActionBlock block = ThingMLFactory.eINSTANCE.createActionBlock();
			EcoreUtil.replace(send, block);
			boolean isEmptyBlock = true;
			for(Parameter p : send.getMessage().getParameters()) {
				if (!mappings.containsKey(p)) continue;
				isEmptyBlock = false;
				final ExpressionGroup g = ThingMLFactory.eINSTANCE.createExpressionGroup();				
				final ExternExpression ext = ThingMLFactory.eINSTANCE.createExternExpression();
				if ("java".equals(manager.compiler)) {					
					ext.setExpression("(" + JavaHelper.getJavaType(p.getTypeRef().getType(), p.getTypeRef().isIsArray()) + ")");
					ext.getSegments().add(g);
				}								
				final Expression e = send.getParameters().get(send.getMessage().getParameters().indexOf(p));
				final PlusExpression plus = ThingMLFactory.eINSTANCE.createPlusExpression();
				plus.setLhs(EcoreUtil.copy(e));							
				
				final LocalVariable rndVar = ThingMLFactory.eINSTANCE.createLocalVariable();
				rndVar.setReadonly(true);
				rndVar.setName("rnd_" + p.getName() + "_" + send.hashCode());
				rndVar.setTypeRef(EcoreUtil.copy(p.getTypeRef()));
				block.getActions().add(rndVar);	
				final Function rnd = Manager.findRandom(ThingMLHelpers.findContainingThing(block));
                if (rnd != null) {
                    final FunctionCallExpression call = ThingMLFactory.eINSTANCE.createFunctionCallExpression();
                    call.setFunction(rnd);
                    rndVar.setInit(call);               
                } else {
    				final long size = Helper.getSize(p.getTypeRef().getType());	
    				final IntegerLiteral i = ThingMLFactory.eINSTANCE.createIntegerLiteral();
    				final int offset = Math.max(1, manager.rnd.nextInt((int)Math.round((Math.pow(2, 8*size-4)))));
    				i.setIntValue(offset);
    				rndVar.setInit(i);
                }				
                final PropertyReference ref = ThingMLFactory.eINSTANCE.createPropertyReference();
                ref.setProperty(rndVar);
				plus.setRhs(ref);
				if ("java".equals(manager.compiler))
					EcoreUtil.replace(e, ext);
				else
					EcoreUtil.replace(e, g);
				g.setTerm(plus);
				send.getParameters().add(EcoreUtil.copy(ref));
			}
			if (isEmptyBlock)
				EcoreUtil.replace(block, send);
			else
				block.getActions().add(send);
		}
			
		for(EventReference er : ThingMLHelpers.getAllExpressions(model, EventReference.class)) {
        	if (mappings.containsKey(er.getParameter())) {
        		final ExpressionGroup group = ThingMLFactory.eINSTANCE.createExpressionGroup();
        		final ExternExpression ext = ThingMLFactory.eINSTANCE.createExternExpression();
				if ("java".equals(manager.compiler)) {					
					ext.setExpression("(" + JavaHelper.getJavaType(er.getParameter().getTypeRef().getType(), er.getParameter().getTypeRef().isIsArray()) + ")");
					ext.getSegments().add(group);
				}
				if ("java".equals(manager.compiler))
					EcoreUtil.replace(er, ext);
				else
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
