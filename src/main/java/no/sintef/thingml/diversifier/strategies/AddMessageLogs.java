package no.sintef.thingml.diversifier.strategies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.ActionHelper;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.ActionBlock;
import org.thingml.xtext.thingML.CastExpression;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.ExpressionGroup;
import org.thingml.xtext.thingML.ExternExpression;
import org.thingml.xtext.thingML.IntegerLiteral;
import org.thingml.xtext.thingML.LocalVariable;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.PlusExpression;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.PrimitiveType;
import org.thingml.xtext.thingML.PrintAction;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.PropertyReference;
import org.thingml.xtext.thingML.SendAction;
import org.thingml.xtext.thingML.StringLiteral;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLFactory;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.thingML.Type;
import org.thingml.xtext.thingML.VariableAssignment;

import no.sintef.thingml.diversifier.Manager;

public class AddMessageLogs extends Strategy {

	boolean onlySummary = false;

	@Override
	protected void doApply(ThingMLModel model) {
		Type byteType = null;
		//Type intType = null;
		for(Type t : ThingMLHelpers.allTypes(model)) {
			if (!(t instanceof PrimitiveType)) continue;
			PrimitiveType pt = (PrimitiveType)t;
			if (AnnotatedElementHelper.isDefined(pt, "type_checker", "Integer") && pt.getByteSize()==1) {
				byteType = pt;
			}/* else if (AnnotatedElementHelper.isDefined(pt, "type_checker", "Integer") && pt.getByteSize()>=4) {
				intType = pt;
			}*/
		}
		
		
		
    	long maxInfo = 0;//We use this to pad the logs related to the positions of bytes containing information
    	for (Thing thing : ThingMLHelpers.allThings(model)) {
    		if (!Manager.diversify(thing)) continue;
    		if (thing.isFragment()) continue;
    		
    		for (SendAction send : ActionHelper.getAllActions(thing, SendAction.class)) {
    			long info = 0;
    			for(Parameter p : send.getMessage().getParameters()) {
            		if (!AnnotatedElementHelper.hasFlag(p, "noise")) {
            			info++;
            		}
            	}
    			if (info > maxInfo)
    				maxInfo = info;
    		}
    	}

    	for (Thing thing : ThingMLHelpers.allThings(model)) {
    		if (!Manager.diversify(thing)) continue;
           	Property counter = null;
        	for (Property p : ThingMLHelpers.allProperties(thing)) {
        		if (p.getName().equals("bytesSentCounter")) {
        			counter = p;
        			break;
        		}
        	}

    		if (thing.isFragment()) continue;
    		int var_counter = 0;
    		// Add counter and pretty print on all send actions
    		for (SendAction send : ActionHelper.getAllActions(thing, SendAction.class)) {
                final Port ip = Helper.createOrGetInternalPort(thing);
                if (!EcoreUtil.equals(ip, send.getPort())) {
                	// Wrap the send action in a block
                	final ActionBlock block = ThingMLFactory.eINSTANCE.createActionBlock();
                	if (send.eContainingFeature().getUpperBound() == -1) {//Collection
                        final EList list = (EList) send.eContainer().eGet(send.eContainingFeature());
                        final int index = list.indexOf(send);
                        list.add(index, block);
                        list.remove(send);
                    } else {
                        send.eContainer().eSet(send.eContainingFeature(), block);
                    }

                	// Calculate message size
                	long messageBytes = 1; // Code
                	for (Parameter p : send.getMessage().getParameters()) {
                		final PrimitiveType type = (PrimitiveType)p.getTypeRef().getType();
                		messageBytes += type.getByteSize();
                	}

                	if (!onlySummary) {
	                	// Set parameters to variables
	                	final List<LocalVariable> args = new ArrayList<LocalVariable>();
	                	for (int i = 0; i < send.getMessage().getParameters().size(); i++) {
	                		final Expression expr = send.getParameters().get(i);
	                		final Parameter par = send.getMessage().getParameters().get(i);

	                		final LocalVariable argVar = ThingMLFactory.eINSTANCE.createLocalVariable();
	                		argVar.setName(send.getMessage().getName()+"Arg"+i + "_" + var_counter);
	                		argVar.setReadonly(true);
	                		argVar.setTypeRef(EcoreUtil.copy(par.getTypeRef()));
	                		argVar.setInit(EcoreUtil.copy(expr));

	                		block.getActions().add(argVar);
	                		args.add(argVar);
	                		
	                		var_counter++;
	    				}

	                	final StringLiteral comma = ThingMLFactory.eINSTANCE.createStringLiteral();
	    				comma.setStringValue(",");

	    				final StringLiteral zeroStrLit = ThingMLFactory.eINSTANCE.createStringLiteral();
	    				zeroStrLit.setStringValue("0");
	    				final StringLiteral oneStrLit = ThingMLFactory.eINSTANCE.createStringLiteral();
	    				oneStrLit.setStringValue("1");

	                	// Print all parameter names
	                	final PrintAction printNames = ThingMLFactory.eINSTANCE.createPrintAction();
	                	printNames.setLine(true);
	                	block.getActions().add(printNames);
	                	final StringLiteral nameprefix = ThingMLFactory.eINSTANCE.createStringLiteral();
	                	nameprefix.setStringValue("@" + thing.getName() + "@");
	                	printNames.getMsg().add(nameprefix);
	                	
	                	// Print all parameter values
	                	final PrintAction printValues = ThingMLFactory.eINSTANCE.createPrintAction();
	                	printValues.setLine(true);
	                	block.getActions().add(printValues);
	                	final StringLiteral valueprefix = ThingMLFactory.eINSTANCE.createStringLiteral();
	                	valueprefix.setStringValue("!");
	                	printValues.getMsg().add(valueprefix);

	                	// Add the msgID to the prints
	                	final byte code = (byte) Short.parseShort(AnnotatedElementHelper.annotationOrElse(send.getMessage(), "code", "0"));
	                	final StringLiteral codeliteral = ThingMLFactory.eINSTANCE.createStringLiteral();
	                	codeliteral.setStringValue("" + code);
	                	printValues.getMsg().add(codeliteral);
	    				printValues.getMsg().add(EcoreUtil.copy(comma));

	    				final StringLiteral msgNameLiteral = ThingMLFactory.eINSTANCE.createStringLiteral();
	    				msgNameLiteral.setStringValue(send.getMessage().getName());
	    				printNames.getMsg().add(msgNameLiteral);
	    				printNames.getMsg().add(EcoreUtil.copy(comma));

	                	for (int i = 0; i < send.getMessage().getParameters().size(); i++) {
	                		final Parameter p = send.getMessage().getParameters().get(i);
	                		final PrimitiveType type = (PrimitiveType)p.getTypeRef().getType();
	                		final PropertyReference ref = ThingMLFactory.eINSTANCE.createPropertyReference();
	                		ref.setProperty(args.get(i));

	                		for (int j = 0; j <= type.getByteSize(); j++) {	                				                			
	                			final CastExpression cast = ThingMLFactory.eINSTANCE.createCastExpression();
	                			cast.setType(byteType);
	                			final ExpressionGroup group = ThingMLFactory.eINSTANCE.createExpressionGroup();	                			
	    						final ExternExpression expr = ThingMLFactory.eINSTANCE.createExternExpression();
	    						expr.setExpression("((");
	    						expr.getSegments().add(EcoreUtil.copy(ref));
	    						final ExternExpression bitshift = ThingMLFactory.eINSTANCE.createExternExpression();
	    						bitshift.setExpression(" >> "+8*(type.getByteSize()-1-j)+") & 0xFF)");
	    						expr.getSegments().add(bitshift);
	    						group.setTerm(expr);
	    						cast.setTerm(group);

	    						// Print value
	    						printValues.getMsg().add(cast);
	    						printValues.getMsg().add(EcoreUtil.copy(comma));

	    						// Print names
	    						final StringLiteral paramNameLiteral = ThingMLFactory.eINSTANCE.createStringLiteral();
	    						if (AnnotatedElementHelper.hasFlag(p, "noise"))
	    							paramNameLiteral.setStringValue("_");
	    						else
	    							paramNameLiteral.setStringValue(p.getName());
	    						printNames.getMsg().add(paramNameLiteral);
	    						printNames.getMsg().add(EcoreUtil.copy(comma));
	    					}
	                	}

	                	// Send original message
	                	for (int i = 0; i < send.getMessage().getParameters().size(); i++) {
	                		final PropertyReference ref = ThingMLFactory.eINSTANCE.createPropertyReference();
	                		ref.setProperty(args.get(i));
	                		send.getParameters().set(i, ref);
	                	}
                	}
                	block.getActions().add(send);

                	// Increase byte counter
                	if (counter != null) {
                		final VariableAssignment va = ThingMLFactory.eINSTANCE.createVariableAssignment();
                		va.setProperty(counter);
                		final PlusExpression plus = ThingMLFactory.eINSTANCE.createPlusExpression();
                		va.setExpression(plus);
                		final PropertyReference counterRef = ThingMLFactory.eINSTANCE.createPropertyReference();
                		counterRef.setProperty(counter);
                		plus.setLhs(counterRef);
                		final IntegerLiteral lit = ThingMLFactory.eINSTANCE.createIntegerLiteral();
                		lit.setIntValue(messageBytes);
                		plus.setRhs(lit);
                		block.getActions().add(va);
                	}
                }
    		}
    	}
	}

}
