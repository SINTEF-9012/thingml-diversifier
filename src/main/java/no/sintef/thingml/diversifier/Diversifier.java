package no.sintef.thingml.diversifier;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.utils.ThingMLInjector;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.constraints.Types;
import org.thingml.xtext.helpers.ActionHelper;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.StateContainerHelper;
import org.thingml.xtext.helpers.TyperHelper;
import org.thingml.xtext.thingML.Action;
import org.thingml.xtext.thingML.ActionBlock;
import org.thingml.xtext.thingML.AnnotatedElement;
import org.thingml.xtext.thingML.ByteLiteral;
import org.thingml.xtext.thingML.CharLiteral;
import org.thingml.xtext.thingML.CompositeState;
import org.thingml.xtext.thingML.ConditionalAction;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.EventReference;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.ExternExpression;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.FunctionCallExpression;
import org.thingml.xtext.thingML.Handler;
import org.thingml.xtext.thingML.IntegerLiteral;
import org.thingml.xtext.thingML.InternalPort;
import org.thingml.xtext.thingML.InternalTransition;
import org.thingml.xtext.thingML.LocalVariable;
import org.thingml.xtext.thingML.LowerExpression;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.PlatformAnnotation;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.PrimitiveType;
import org.thingml.xtext.thingML.PrintAction;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.PropertyReference;
import org.thingml.xtext.thingML.ReceiveMessage;
import org.thingml.xtext.thingML.Region;
import org.thingml.xtext.thingML.SendAction;
import org.thingml.xtext.thingML.State;
import org.thingml.xtext.thingML.StateContainer;
import org.thingml.xtext.thingML.StringLiteral;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLFactory;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.thingML.Transition;
import org.thingml.xtext.thingML.Type;
import org.thingml.xtext.thingML.TypeRef;
import org.thingml.xtext.thingML.VariableAssignment;

class Diversifier {

    //TODO: Introduce probabilities of doing changes and randomness so as Diversifying multiple time the same input configuration actually generates multiple diversified results
    //NOTE: Done to some extent

    private final boolean debug = false;
    private byte code;
    private int param = 0;
    private Random rnd;

    /* -- Select which diversifications to do -- */
    private boolean onlySummary = false;
    private boolean onlyLogs = false;
    private boolean addRandomParameters = false;
    private boolean duplicateMessages = false;
    private boolean doRuntimeRandomness = false;


    public Diversifier(long seed) {
        rnd = new Random(seed);
        code = 0;//(byte) rnd.nextInt(255);
    }

    public static void main(String args[]) {
        if (args.length < 1) {
            System.out.println("Please provide path to a ThingML model containing at least one configuration.");
            return;
        }
        final File model = new File(args[0]);
        if (Files.notExists(model.toPath())) {
            System.out.println("Input file does not exist.");
            return;
        }

        final ThingMLModel input = ThingMLCompiler.flattenModel(ThingMLCompiler.loadModel(model));

        // Number of diversified models to generate
        int number = 1;
        if (args.length >= 2) {
        	number = Integer.parseInt(args[1]);
        }

        // Specify output directory
        String outDir = "";
        if (args.length >= 4) {
        	outDir = args[3];
        }

        // Specify random seed for reproducible model-generation
        long seed = input.hashCode()*System.currentTimeMillis(); // Special perfect seed algorithm
        if (args.length >= 5) {
        	seed = Long.parseLong(args[4]);
        }

        final Diversifier diversifier = new Diversifier(seed);
        // Specify profile to use
        if (args.length >= 3) {
        	switch (args[2]) {
        	case "onlysummary":
        		diversifier.onlySummary = true;
        	case "onlylogs":
        		diversifier.onlyLogs = true;
        		break;
        	case "runtimesummary":
        		diversifier.onlySummary = true;
        	case "runtime":
        		diversifier.doRuntimeRandomness = true;
        		diversifier.duplicateMessages = true;
        		break;
        	case "defaultsummary":
        		diversifier.onlySummary = true;
        	case "default":
        	default:
        	}
        }


        final long start = System.currentTimeMillis();
        // Run diversification multiple times
        for (int i = 0; i < number; i++) {
			String finalOutDir = outDir;
			int finalNumber = number;
			int finalI = i;
			final ThingMLModel clone = ThingMLCompiler.flattenModel(input);

			final List<Configuration> configs = new ArrayList<Configuration>(clone.getConfigs());

			for (Configuration cfg : configs) {
				System.out.println("Diversifying configuration " + cfg.getName());
				diversifier.diversify(cfg);
			}

			String saveName = model.getName().substring(0, model.getName().lastIndexOf(".")) + (finalNumber > 1 ? finalI + 1 : "") + ".thingml";
			File saveTo = new File(model.getParent(), "/diversified/" + saveName);
			if (!finalOutDir.isEmpty()) saveTo = new File(finalOutDir, saveName);

			try {
				System.out.println("saving to " + saveTo.getAbsolutePath());
				ThingMLCompiler.saveAsThingML(clone, saveTo.getAbsolutePath());
			} catch (IOException e) {
				System.err.println("Cannot save ThingML model to " + saveTo.getAbsolutePath());
				e.printStackTrace();
			}
		}
		System.out.println("took " + (System.currentTimeMillis() - start) + "ms.");
    }

    public void diversify(Configuration cfg) {
        final ThingMLModel model = (ThingMLModel) cfg.eContainer();

        if (!this.onlyLogs) {
	        /**
	         * Add random parameters so that they all have the same sizes.
	         * (This also gives some more flexibility for the split transformation).
	         */
	        // Even out size
	        if (this.addRandomParameters) {
		        int maxSize = 0;
		        final TreeIterator<EObject> it6 = model.eAllContents();
		        while (it6.hasNext()) {
		            final EObject o = it6.next();
		            if (o instanceof Message) {
		                final Message m = (Message) o;
		                if (m.getParameters().size() > maxSize)
		                    maxSize = m.getParameters().size();
		            }
		        }

		        final TreeIterator<EObject> it5 = model.eAllContents();
		        while (it5.hasNext()) {
		            final EObject o = it5.next();
		            if (o instanceof Message) {
		                final Message m = (Message) o;
		                final int size = m.getParameters().size();
		                for (int i = size; i < maxSize; i++) {
		                    addRandomParameter(m, model);
		                }
		            }
		        }
	        }

	        // Swaps messages in metamodel (should make different codes)
	        for (Thing t : ThingMLHelpers.allThings(model)) {
	            if (!AnnotatedElementHelper.isDefined(t, "diversify", "not")) {
	                changeOrderOfMessages(t);
	            }
	        }

	        // Swap order of parameters in messages (and also re-order corresponding send actions)
	        for (Thing t : ThingMLHelpers.allThings(model)) {
	        	if (!AnnotatedElementHelper.isDefined(t, "diversify", "not")) {
	        		for (Message m : t.getMessages()) {
	        			changeOrderOfParameter(m);
	        		}
	        	}
	        }

	        // Upsize parameters
	        for (Thing t : ThingMLHelpers.allThings(model)) {
	        	if (!AnnotatedElementHelper.isDefined(t, "diversify", "not")) {
	        		for (Message m : t.getMessages()) {
	        			for (Parameter p : m.getParameters()) {
	        				upSizeParameter(p);
	        			}
	        		}
	        	}
	        }

	        // Makes new options for messages
	        if (this.duplicateMessages) {
		        final TreeIterator<EObject> it = model.eAllContents();
		        while (it.hasNext()) {
		            final EObject o = it.next();
		            if (o instanceof Message) {
		                final Thing t = ThingMLHelpers.findContainingThing(o);
		                if (!AnnotatedElementHelper.isDefined(t, "diversify", "not")) {
		                    final Message m = (Message) o;
		                    duplicateMessage(m, model);
		                }
		            }
		        }
	        }

	        // Split
	        List<Message> msgs = new ArrayList<>();
	        final TreeIterator<EObject> it2 = model.eAllContents();
	        while (it2.hasNext()) {
	            final EObject o = it2.next();
	            if (o instanceof Message) {
	                final Message m = (Message) o;
	                splitMessage(model, m);
	            }
	        }

	        // Re-make long messages
	        if (this.addRandomParameters) {
		        int maxSize = 0;
		        final TreeIterator<EObject> it10 = model.eAllContents();
		        while (it10.hasNext()) {
		            final EObject o = it10.next();
		            if (o instanceof Message) {
		                final Message m = (Message)o;
		                if (m.getParameters().size()>maxSize)
		                    maxSize = m.getParameters().size();
		            }
		        }
		        final TreeIterator<EObject> it11 = model.eAllContents();
		        while (it11.hasNext()) {
		            final EObject o = it11.next();
		            if (o instanceof Message) {
		                final Message m = (Message)o;
		                final int size = m.getParameters().size();
		                for (int i = size; i<maxSize; i++) {
		                    addRandomParameter(m, model);
		                }
		            }
		        }
	        }

	        // adds code annotation
	        final TreeIterator<EObject> it3 = model.eAllContents();
	        while (it3.hasNext()) {
	            final EObject o = it3.next();
	            if (o instanceof Message) {
	                final Thing t = ThingMLHelpers.findContainingThing(o);
	                if (!AnnotatedElementHelper.isDefined(t, "diversify", "not")) {
	                    final Message m = (Message) o;
	                    generateCodeForMessage(m);
	                }
	            }
	        }
        }

        addLogs(model);
    }

    private void addLogs(ThingMLModel model) {
    	long maxInfo = 0;//We use this to pad the logs related to the positions of bytes containing information
    	for (Thing thing : ThingMLHelpers.allThings(model)) {
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
    		if (thing.isFragment()) continue;    		    		    		    	
    		// Add counter and pretty print on all send actions
    		for (SendAction send : ActionHelper.getAllActions(thing, SendAction.class)) {
                final Port ip = createOrGetInternalPort(thing);
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
	                		argVar.setName(send.getMessage().getName()+"Arg"+i);
	                		argVar.setTypeRef(EcoreUtil.copy(par.getTypeRef()));
	                		argVar.setInit(EcoreUtil.copy(expr));

	                		block.getActions().add(argVar);
	                		args.add(argVar);
	    				}

	                	final CharLiteral comma = ThingMLFactory.eINSTANCE.createCharLiteral();
	    				comma.setCharValue((byte) 44);

	    				final StringLiteral zeroStrLit = ThingMLFactory.eINSTANCE.createStringLiteral();
	    				zeroStrLit.setStringValue("0");
	    				final StringLiteral oneStrLit = ThingMLFactory.eINSTANCE.createStringLiteral();
	    				oneStrLit.setStringValue("1");

	                	// Print all parameter values
	                	final PrintAction printValues = ThingMLFactory.eINSTANCE.createPrintAction();
	                	printValues.setLine(true);
	                	block.getActions().add(printValues);
	                	final StringLiteral valueprefix = ThingMLFactory.eINSTANCE.createStringLiteral();
	                	valueprefix.setStringValue("!");
	                	printValues.getMsg().add(valueprefix);

	                	// Print all parameter types
	                	final PrintAction printTypes = ThingMLFactory.eINSTANCE.createPrintAction();
	                	printTypes.setLine(true);
	                	block.getActions().add(printTypes);
	                	final StringLiteral typeprefix = ThingMLFactory.eINSTANCE.createStringLiteral();
	                	typeprefix.setStringValue(":");
	                	printTypes.getMsg().add(typeprefix);
	                	
	                	// Print all parameter names
	                	final PrintAction printNames = ThingMLFactory.eINSTANCE.createPrintAction();
	                	printNames.setLine(true);
	                	block.getActions().add(printNames);
	                	final StringLiteral nameprefix = ThingMLFactory.eINSTANCE.createStringLiteral();
	                	nameprefix.setStringValue("@" + thing.getName() + "@");
	                	printNames.getMsg().add(nameprefix);
	                	
	                	// Print all parameter names
	                	final PrintAction printPositions = ThingMLFactory.eINSTANCE.createPrintAction();
	                	printPositions.setLine(true);
	                	block.getActions().add(printPositions);
	                	final StringLiteral positionprefix = ThingMLFactory.eINSTANCE.createStringLiteral();
	                	positionprefix.setStringValue("#");
	                	printPositions.getMsg().add(positionprefix);

	                	// Print all parameter weakness
	                	final PrintAction printWeak = ThingMLFactory.eINSTANCE.createPrintAction();
	                	printWeak.setLine(true);
	                	block.getActions().add(printWeak);
	                	final StringLiteral weakprefix = ThingMLFactory.eINSTANCE.createStringLiteral();
	                	weakprefix.setStringValue("?");
	                	printWeak.getMsg().add(weakprefix);

	                	// Add the msgID to the prints
	                	//final byte code = (byte) Short.parseShort(AnnotatedElementHelper.annotationOrElse(send.getMessage(), "code", "0x00").substring(2), 16);
	                	final byte code = (byte) Short.parseShort(AnnotatedElementHelper.annotationOrElse(send.getMessage(), "code", "0"));
	                	final StringLiteral codeliteral = ThingMLFactory.eINSTANCE.createStringLiteral();
	                	codeliteral.setStringValue("" + code);
	                	printValues.getMsg().add(codeliteral);
	    				printValues.getMsg().add(EcoreUtil.copy(comma));

	    				final StringLiteral codeTypeLiteral = ThingMLFactory.eINSTANCE.createStringLiteral();
	    				codeTypeLiteral.setStringValue(Types.BYTE_TYPE.getName());
	    				printTypes.getMsg().add(codeTypeLiteral);
	    				printTypes.getMsg().add(EcoreUtil.copy(comma));
	    				
	    				final StringLiteral msgNameLiteral = ThingMLFactory.eINSTANCE.createStringLiteral();
	    				msgNameLiteral.setStringValue(send.getMessage().getName());
	    				printNames.getMsg().add(msgNameLiteral);
	    				printNames.getMsg().add(EcoreUtil.copy(comma));

	    				printWeak.getMsg().add(EcoreUtil.copy(zeroStrLit));
	    				printWeak.getMsg().add(EcoreUtil.copy(comma));

	                	for (int i = 0; i < send.getMessage().getParameters().size(); i++) {
	                		final Parameter p = send.getMessage().getParameters().get(i);
	                		final PrimitiveType type = (PrimitiveType)p.getTypeRef().getType();
	                		final PropertyReference ref = ThingMLFactory.eINSTANCE.createPropertyReference();
	                		ref.setProperty(args.get(i));

	                		for (int j = 0; j < type.getByteSize(); j++) {

	    						final ExternExpression expr = ThingMLFactory.eINSTANCE.createExternExpression();
	    						expr.setExpression("((");
	    						expr.getSegments().add(EcoreUtil.copy(ref));

	    						final ExternExpression bitshift = ThingMLFactory.eINSTANCE.createExternExpression();
	    						bitshift.setExpression(" >> "+8*(type.getByteSize()-1-j)+") & 0xFF)");
	    						expr.getSegments().add(bitshift);

	    						// Print value
	    						printValues.getMsg().add(expr);
	    						printValues.getMsg().add(EcoreUtil.copy(comma));

	    						// Print type
	    						final StringLiteral paramTypeLiteral = ThingMLFactory.eINSTANCE.createStringLiteral();
	    						paramTypeLiteral.setStringValue(type.getName());
	    						printTypes.getMsg().add(paramTypeLiteral);
	    						printTypes.getMsg().add(EcoreUtil.copy(comma));
	    						
	    						// Print names
	    						final StringLiteral paramNameLiteral = ThingMLFactory.eINSTANCE.createStringLiteral();
	    						if (AnnotatedElementHelper.hasFlag(p, "noise"))
	    							paramNameLiteral.setStringValue("_");
	    						else
	    							paramNameLiteral.setStringValue(p.getName());
	    						printNames.getMsg().add(paramNameLiteral);
	    						printNames.getMsg().add(EcoreUtil.copy(comma));

	    						// Print weakness
	    						if (AnnotatedElementHelper.hasAnnotation(send.getMessage().getParameters().get(i), "weakparam")) {
	    							printWeak.getMsg().add(EcoreUtil.copy(oneStrLit));
	    						} else {
	    							printWeak.getMsg().add(EcoreUtil.copy(zeroStrLit));
	    						}
	    						printWeak.getMsg().add(EcoreUtil.copy(comma));
	    					}
	                	}
	                	
	                	final List<Parameter> orderedParams = new ArrayList<>();
	                	orderedParams.addAll(send.getMessage().getParameters());
	                	Collections.sort(orderedParams, (p1, p2) -> p1.getName().compareTo(p2.getName()));
	                	boolean isJustNoise = true;
	                	for(Parameter p : orderedParams) {
	                		if (!AnnotatedElementHelper.hasFlag(p, "noise")) {
	                			isJustNoise = false;
	                			break;
	                		}
	                	}
	                	if (!isJustNoise) {
	                		final IntegerLiteral start = ThingMLFactory.eINSTANCE.createIntegerLiteral();//ThingMLInjector.parseString(ThingMLInjector.grammar().getExpressionRule(), "bytesSentCounter");
	                		start.setIntValue(0);
        					printPositions.getMsg().add(start);
        					printPositions.getMsg().add(EcoreUtil.copy(comma));
	                		for(Parameter p : orderedParams) {
		                		if (!AnnotatedElementHelper.hasFlag(p, "noise")) {
		                			long offset = 1;//message code is offset 0
		                			for(Parameter p2 : send.getMessage().getParameters()) {
		                				if (p2!=p) {
		                					offset += ((PrimitiveType)p2.getTypeRef().getType()).getByteSize();
		                				} else{		                							                				
		                					final IntegerLiteral position = ThingMLFactory.eINSTANCE.createIntegerLiteral();//ThingMLInjector.parseString(ThingMLInjector.grammar().getExpressionRule(), "(bytesSentCounter + " + offset + ")");
		                					position.setIntValue(offset);
		                					printPositions.getMsg().add(position);
		                					printPositions.getMsg().add(EcoreUtil.copy(comma));
		                					break;
		                				}
		                			}
		                		}
		                	}
	                		//Padding
	                		for(int i = orderedParams.size(); i < maxInfo; i++) {
	                			final IntegerLiteral position = ThingMLFactory.eINSTANCE.createIntegerLiteral();
            					position.setIntValue(0);
            					printPositions.getMsg().add(position);
            					printPositions.getMsg().add(EcoreUtil.copy(comma));
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
                	Action increaseByteCounter = ThingMLInjector.parseString(ThingMLInjector.grammar().getActionRule(), "bytesSentCounter = bytesSentCounter + "+messageBytes);
                	block.getActions().add(increaseByteCounter);
                	ThingMLInjector.linkFrom(increaseByteCounter);
                }
    		}
    	}
    }

    private Function findRandom(Thing thing) {
        Function rnd = null;
        for (Function f : ThingMLHelpers.allFunctions(thing)) {
            if (f.getName().equals("rnd")) {
                rnd = f;
                break;
            }
        }
        return rnd;
    }

    private void duplicateMessage(Message m, ThingMLModel model) {
        final Thing t = ThingMLHelpers.findContainingThing(m);
        for (Message msg : t.getMessages()) {
            if (msg.getName().equals(m.getName() + "bis"))
                return;
        }
        final Message m2 = EcoreUtil.copy(m);
        m2.setName(m.getName() + "bis");
        t.getMessages().add(m2);

        for (Thing thing : ThingMLHelpers.allThings(model)) {
            for (Port port : thing.getPorts()) {
                if (port.getSends().contains(m)) {
                    //System.out.println(" adding messages " + m2.getName() + " to sent messages of port " + port.getName() + " of thing " + thing.getName());
                    port.getSends().add(m2);
                    duplicateSendAction(thing, port, m, m2);
                }
                if (port.getReceives().contains(m)) {
                    //System.out.println(" adding message " + m2.getName() + " to received messages of  port " + port.getName() + " of thing " + thing.getName());
                    port.getReceives().add(m2);
                    duplicateHandlers(thing, port, m, m2);
                }
            }
        }
    }

    private void duplicateSendAction(Thing thing, Port p, Message m, Message m2) {
        final Function rnd = findRandom(thing);
        if (rnd == null) return;
        for (SendAction send : ActionHelper.getAllActions(thing, SendAction.class)) {
            if (EcoreUtil.equals(send.getMessage(), m) && EcoreUtil.equals(send.getPort(), p)) {
                final ConditionalAction ca = ThingMLFactory.eINSTANCE.createConditionalAction();
                final LowerExpression lower = ThingMLFactory.eINSTANCE.createLowerExpression();
                final FunctionCallExpression call = ThingMLFactory.eINSTANCE.createFunctionCallExpression();
                call.setFunction(rnd);
                final IntegerLiteral threshold = ThingMLFactory.eINSTANCE.createIntegerLiteral();
                threshold.setIntValue(this.rnd.nextInt(256));
                lower.setLhs(call);
                lower.setRhs(threshold);
                ca.setCondition(lower);

                final Object parent = send.eContainer().eGet(send.eContainingFeature());
                if (parent instanceof EList) {
                    EList list = (EList) parent;
                    final int index = list.indexOf(send);
                    list.add(index, ca);
                } else {
                    final EObject o = send.eContainer();
                    o.eSet(send.eContainingFeature(), ca);
                }

                final ActionBlock b1 = ThingMLFactory.eINSTANCE.createActionBlock();
                b1.getActions().add(send);
                ca.setAction(b1);
                final SendAction send2 = EcoreUtil.copy(send);
                send2.setMessage(m2);
                final ActionBlock b2 = ThingMLFactory.eINSTANCE.createActionBlock();
                b2.getActions().add(send2);
                ca.setElseAction(b2);
            }
        }
    }

    private void addRandomParameter(Message m, ThingMLModel model) {
        System.out.println("Adding random parameter to message " + m.getName());
        final PlatformAnnotation annot = ThingMLFactory.eINSTANCE.createPlatformAnnotation();
        annot.setName("noise");
                
        int insertAt = 0;
        if (m.getParameters().size() > 0)
            insertAt = rnd.nextInt(m.getParameters().size());
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

        //Update send actions
        for (Thing thing : ThingMLHelpers.allThings(model)) {
            final Function rnd = findRandom(thing);
            for (SendAction send : ActionHelper.getAllActions(thing, SendAction.class)) {
                if (EcoreUtil.equals(send.getMessage(), m)) {
                    if (rnd != null) {
                        final FunctionCallExpression call = ThingMLFactory.eINSTANCE.createFunctionCallExpression();
                        call.setFunction(rnd);
                        send.getParameters().add(insertAt, call);
                    } else {
                        final ByteLiteral b = ThingMLFactory.eINSTANCE.createByteLiteral();
                        b.setByteValue((byte) this.rnd.nextInt(256));
                        send.getParameters().add(insertAt, b);
                    }
                }
            }
        }
    }

    /**
     * Upsize parameter e.g. Byte -> Integer
     *
     * @message m
     */
    private void upSizeParameter(Parameter p) {
    	if (AnnotatedElementHelper.isDefined(p, "upsize", "not")) return;

    	final PrimitiveType original = (PrimitiveType)p.getTypeRef().getType();
    	final ThingMLModel model = ThingMLHelpers.findContainingModel(p);

    	// Find all compatible types
    	final List<PrimitiveType> options = new ArrayList<PrimitiveType>();

    	// Check all types in model, and find a different one, that is compatible, and same size or bigger
        for (Type t : ThingMLHelpers.allSimpleTypes(model)) {
        	if (t instanceof PrimitiveType) {
        		final PrimitiveType other = (PrimitiveType)t;
        		if (TyperHelper.isA(original, other) && other.getByteSize() >= original.getByteSize()) {
        			options.add(other);
        		}
        	}
        }

        // Select a new type (might be the same as the original)
        final PrimitiveType newtype = options.get(this.rnd.nextInt(options.size()));
        p.getTypeRef().setType(newtype);
    }

    /**
     * Change order of messages
     *
     * @param t
     */
    private void changeOrderOfMessages(Thing t) {
        System.out.println("Shuffling messages of thing " + t.getName());
        final List<Message> shuffled = new ArrayList<Message>();
        shuffled.addAll(t.getMessages());
        Collections.shuffle(shuffled, this.rnd);
        t.getMessages().clear();
        t.getMessages().addAll(shuffled);
    }

    /**
     * Change order of parameters
     *
     * @param m
     */
    private void changeOrderOfParameter(Message m) {
        if (m.getParameters().size() == 0) return;
        System.out.println("Changing parameter order for message " + m.getName());
        final List<Parameter> original = new ArrayList<>();
        original.addAll(m.getParameters());
        final List<Parameter> shuffled = new ArrayList<Parameter>();
        shuffled.addAll(m.getParameters());
        Collections.shuffle(shuffled, this.rnd);
        m.getParameters().clear();
        m.getParameters().addAll(shuffled);

        //Re-ordering actual parameters in send actions
        for (Thing thing : ThingMLHelpers.allThings(ThingMLHelpers.findContainingModel(m))) {
            for (SendAction send : ActionHelper.getAllActions(thing, SendAction.class)) {
                if (EcoreUtil.equals(send.getMessage(), m)) {
                    final List<Expression> params = new ArrayList<>();
                    for (Parameter p : send.getMessage().getParameters()) {
                        final int index = original.indexOf(p);
                        params.add(send.getParameters().get(index));
                    }
                    send.getParameters().clear();
                    send.getParameters().addAll(params);
                }
            }
        }
    }

    private void generateCodeForMessage(Message m) {
        PlatformAnnotation a = null;
        for (PlatformAnnotation annot : m.getAnnotations()) {
            if (annot.getName().equals("code")) {
                a = annot;
                break;
            }
        }
        if (a == null) {
            a = ThingMLFactory.eINSTANCE.createPlatformAnnotation();
            m.getAnnotations().add(a);
        }
        a.setName("code");
        //a.setValue(String.format("0x%02X", code));
        a.setValue(""+code);
        if (code < 127) code++;
        else code = -128;
    }

    private Message createMessage(Thing t, Message m, List<Parameter> params) {
        final Message msg = ThingMLFactory.eINSTANCE.createMessage();
        String name = m.getName();
        if (params.size() == 0)
            name = name + "_";
        for (Parameter p : params) {
            name = name + p.getName();
            msg.getParameters().add(EcoreUtil.copy(p));
        }
        msg.setName(name);
        System.out.println("Creating new message " + msg.getName());
        t.getMessages().add(msg);
        return msg;
    }

    private void splitSendAction(Thing thing, Port p, Message m, Message first, Message second, int splitAt) {
        for (SendAction send : ActionHelper.getAllActions(thing, SendAction.class)) {
            EObject eo = send.eContainer();
            while (eo != null) {
                if (eo instanceof AnnotatedElement) {
                    final AnnotatedElement a = (AnnotatedElement) eo;
                    if (AnnotatedElementHelper.isDefined(a, "diversify", "not")) {
                        return;
                    }
                }
                eo = eo.eContainer();
            }
            if (EcoreUtil.equals(send.getMessage(), m) && EcoreUtil.equals(send.getPort(), p)) {
                final SendAction send1 = ThingMLFactory.eINSTANCE.createSendAction();
                send1.setMessage(first);
                send1.setPort(send.getPort());
                for (Expression e : send.getParameters().subList(0, splitAt)) {
                    send1.getParameters().add(EcoreUtil.copy(e));
                }

                final SendAction send2 = ThingMLFactory.eINSTANCE.createSendAction();
                send2.setMessage(second);
                send2.setPort(send.getPort());
                for (Expression e : send.getParameters().subList(splitAt, m.getParameters().size())) {
                    send2.getParameters().add(EcoreUtil.copy(e));
                }

                final Function rnd = findRandom(thing);
                if (rnd == null || !this.doRuntimeRandomness) {
                	final ActionBlock b1 = ThingMLFactory.eINSTANCE.createActionBlock();
                	if (this.rnd.nextBoolean()) {
                		b1.getActions().add(send1);
                        b1.getActions().add(send2);
                	} else {
                		b1.getActions().add(send2);
                        b1.getActions().add(send1);
                	}

                    final Object parent = send.eContainer().eGet(send.eContainingFeature());
                    if (parent instanceof EList) {
                        EList list = (EList) parent;
                        final int index = list.indexOf(send);
                        list.add(index, b1);
                        list.remove(send);
                    } else {
                        final EObject o = send.eContainer();
                        o.eSet(send.eContainingFeature(), b1);
                    }
                } else {
                    final ConditionalAction ca = ThingMLFactory.eINSTANCE.createConditionalAction();
                    final LowerExpression lower = ThingMLFactory.eINSTANCE.createLowerExpression();
                    final FunctionCallExpression call = ThingMLFactory.eINSTANCE.createFunctionCallExpression();
                    call.setFunction(rnd);
                    final IntegerLiteral threshold = ThingMLFactory.eINSTANCE.createIntegerLiteral();
                    threshold.setIntValue(this.rnd.nextInt(256));
                    lower.setLhs(call);
                    lower.setRhs(threshold);
                    ca.setCondition(lower);

                    final ActionBlock b1 = ThingMLFactory.eINSTANCE.createActionBlock();
                    b1.getActions().add(send1);
                    b1.getActions().add(send2);
                    ca.setAction(b1);
                    final ActionBlock b2 = ThingMLFactory.eINSTANCE.createActionBlock();
                    b2.getActions().add(EcoreUtil.copy(send2));
                    b2.getActions().add(EcoreUtil.copy(send1));
                    ca.setElseAction(b2);

                    final Object parent = send.eContainer().eGet(send.eContainingFeature());
                    if (parent instanceof EList) {
                        EList list = (EList) parent;
                        final int index = list.indexOf(send);
                        list.add(index, ca);
                        list.remove(send);
                    } else {
                        final EObject o = send.eContainer();
                        o.eSet(send.eContainingFeature(), ca);
                    }
                }
            }
        }
    }

    /**
     * For this thing t, message m will be splitted
     * into m.parameters# messages. Both sides (instances
     * and their instance-specific types) will be updated
     * to properly handle sending/receiving of split message
     *
     * @param m
     */
    private void splitMessage(ThingMLModel model, Message m) {
        //if (m.getParameters().size() < 2) return;
        int splitAt = rnd.nextInt(m.getParameters().size());
        //if (splitAt == 0 || splitAt == m.getParameters().size()) return;
        System.out.println("Splitting message " + m.getName() + " at index " + splitAt + "...");
        final Message first = createMessage((Thing) m.eContainer(), m, m.getParameters().subList(0, splitAt));
        final Message second = createMessage((Thing) m.eContainer(), m, m.getParameters().subList(splitAt, m.getParameters().size()));

        final Map<Thing, Map<Message, Port>> mappings = new HashMap<>();

        for (Thing thing : ThingMLHelpers.allThings(model)) {
            final List<Port> ports = new ArrayList<>();
            ports.addAll(thing.getPorts());
            for (Port port : ports) {
                if (port.getSends().contains(m)) {
                    //System.out.println(" adding messages " + first.getName() + " and " + second.getName() + " to sent messages of port " + port.getName() + " of thing " + thing.getName());
                    port.getSends().add(first);
                    port.getSends().add(second);
                    //System.out.println(" removing message " + m.getName() + " from sent messages of port " + port.getName() + " of thing " + thing.getName());
                    port.getSends().remove(m);
                    splitSendAction(thing, port, m, first, second, splitAt);
                }
                if (port.getReceives().contains(m)) {
                    if (mappings.get(thing) == null) mappings.put(thing, new HashMap<>());
                    final Map<Message, Port> msgMappings = mappings.get(thing);
                    msgMappings.put(m, port);
                    final Port ip = createOrGetInternalPort(thing);
                    //System.out.println(" removing message " + m.getName() + " from received messages of port " + port.getName() + " of thing " + thing.getName());
                    port.getReceives().remove(m);
                    //System.out.println(" adding message " + first.getName() + " and " + second.getName() + " to received messages of port " + port.getName() + " of thing " + thing.getName());
                    port.getReceives().add(first);
                    port.getReceives().add(second);
                    //System.out.println(" adding message " + m.getName() + " to internal port " + ip.getName() + " of thing " + thing.getName());
                    ip.getReceives().add(m);
                    ip.getSends().add(m);
                    createRegion(thing, port, m, first, second);
                    updateHandlers(thing, port, m);
                }
            }
        }
    }

    private void createRegion(Thing t, Port port, Message m, Message m1, Message m2) {
        if (t.getBehaviour() != null) {
            for (StateContainer r : StateContainerHelper.allContainedRegions(t.getBehaviour())) {
                if (r instanceof Region)
                    if (r.getName().equals("generate_" + m.getName() + "_from_" + m1.getName() + "_and_" + m2.getName()))
                        return;//FIXME: this is a hack to avoid creating two similar regions...
            }

            final Region region = ThingMLFactory.eINSTANCE.createRegion();
            final CompositeState _init = ThingMLFactory.eINSTANCE.createCompositeState();
            Map<String, Property> props = new HashMap<>();
            for (Parameter param : m.getParameters()) {
                final Property prop = ThingMLFactory.eINSTANCE.createProperty();
                prop.setReadonly(false);
                prop.setName(param.getName());
                prop.setTypeRef(EcoreUtil.copy(param.getTypeRef()));
                _init.getProperties().add(prop);
                props.put(prop.getName(), prop);
            }
            _init.setName("INIT");
            final PlatformAnnotation a = ThingMLFactory.eINSTANCE.createPlatformAnnotation();
            a.setName("diversify");
            a.setValue("not");

            final State s1 = ThingMLFactory.eINSTANCE.createState();
            s1.setName("S1");
            s1.getAnnotations().add(EcoreUtil.copy(a));
            final State s2 = ThingMLFactory.eINSTANCE.createState();
            s2.setName("S2");
            s2.getAnnotations().add(EcoreUtil.copy(a));
            final State s3 = ThingMLFactory.eINSTANCE.createState();
            s3.setName("S3");
            s3.getAnnotations().add(EcoreUtil.copy(a));
            final State s4 = ThingMLFactory.eINSTANCE.createState();
            s4.setName("S4");
            s4.getAnnotations().add(EcoreUtil.copy(a));
            region.setName("generate_" + m.getName() + "_from_" + m1.getName() + "_and_" + m2.getName());
            region.setInitial(_init);
            region.getSubstate().add(_init);
            _init.getSubstate().add(s1);
            _init.getSubstate().add(s2);
            _init.getSubstate().add(s3);
            _init.getSubstate().add(s4);
            _init.setInitial(s1);


            final ActionBlock block = ThingMLFactory.eINSTANCE.createActionBlock();
            if (debug) {
                final PrintAction print = ThingMLFactory.eINSTANCE.createPrintAction();
                print.setLine(true);
                final StringLiteral msg = ThingMLFactory.eINSTANCE.createStringLiteral();
                msg.setStringValue(createOrGetInternalPort(t).getName() + "!" + m.getName());
                print.getMsg().add(msg);
                block.getActions().add(print);
            }
            final SendAction send = ThingMLFactory.eINSTANCE.createSendAction();
            send.setPort(createOrGetInternalPort(t));
            send.setMessage(m);
            block.getActions().add(send);
            s4.setEntry(block);
            for (Parameter param : m.getParameters()) {
                final PropertyReference ref = ThingMLFactory.eINSTANCE.createPropertyReference();
                ref.setProperty(props.get(param.getName()));
                send.getParameters().add(ref);
            }


            final Transition s4tos1 = ThingMLFactory.eINSTANCE.createTransition();
            s4tos1.setTarget(s1);
            s4.getOutgoing().add(s4tos1);

            buildTransitions(m1, port, s1, s2, props);
            buildTransitions(m2, port, s1, s3, props);
            buildTransitions(m1, port, s3, s4, props);
            buildTransitions(m2, port, s2, s4, props);

            t.getBehaviour().getRegion().add(region);
        }
    }

    private void buildTransitions(Message m, Port p, State from, State to, Map<String, Property> props) {
        System.out.println("Building new transition from " + from.getName() + " to " + to.getName() + " on event " + p.getName() + "?" + m.getName());
        final ReceiveMessage rm1 = ThingMLFactory.eINSTANCE.createReceiveMessage();
        rm1.setName("ev");
        rm1.setPort(p);
        rm1.setMessage(m);
        final Transition t = ThingMLFactory.eINSTANCE.createTransition();
        t.setTarget(to);
        from.getOutgoing().add(t);
        t.setEvent(rm1);
        final ActionBlock b1 = buildActionForTransition(m, props, rm1);
        t.setAction(b1);

    }

    private ActionBlock buildActionForTransition(Message m, Map<String, Property> props, ReceiveMessage rm) {
        final ActionBlock b = ThingMLFactory.eINSTANCE.createActionBlock();
        if (debug) {
            final PrintAction print = ThingMLFactory.eINSTANCE.createPrintAction();
            print.setLine(true);
            final StringLiteral msg = ThingMLFactory.eINSTANCE.createStringLiteral();
            msg.setStringValue(rm.getPort().getName() + "?" + rm.getMessage().getName());
            print.getMsg().add(msg);
            b.getActions().add(print);
        }
        for (Parameter p1 : m.getParameters()) {
            final VariableAssignment va = ThingMLFactory.eINSTANCE.createVariableAssignment();
            va.setProperty(props.get(p1.getName()));
            final EventReference ref = ThingMLFactory.eINSTANCE.createEventReference();
            ref.setReceiveMsg(rm);
            ref.setParameter(p1);
            va.setExpression(ref);
            b.getActions().add(va);
        }
        return b;
    }

    private void updateHandlers(Thing thing, Port p, Message m) {
        TreeIterator<EObject> it = thing.eAllContents();
        while (it.hasNext()) {
            final EObject o = it.next();
            if (o instanceof Handler) {
                final Handler h = (Handler) o;
                if (/*!AnnotatedElementHelper.isDefined((State)h.eContainer(), "diversify", "not") && */h.getEvent() != null) {
                    final ReceiveMessage rm = (ReceiveMessage) h.getEvent();
                    if (EcoreUtil.equals(rm.getMessage(), m) && EcoreUtil.equals(rm.getPort(), p))
                        rm.setPort(createOrGetInternalPort(ThingMLHelpers.findContainingThing(rm)));
                }
            }
        }
    }

    private void duplicateHandlers(Thing thing, Port p, Message m, Message m2) {
        TreeIterator<EObject> it = thing.eAllContents();
        while (it.hasNext()) {
            final EObject o = it.next();
            if (o instanceof Handler) {
                final Handler h = (Handler) o;
                if (h.getEvent() != null && h.getEvent() instanceof ReceiveMessage) {
                    final ReceiveMessage rm = (ReceiveMessage) h.getEvent();
                    if (EcoreUtil.equals(m, rm.getMessage()) && EcoreUtil.equals(p, rm.getPort())) {
                        System.out.println("Duplicating handler " + p.getName() + "?" + m.getName() + " in thing " + thing.getName());
                        Handler h2 = null;
                        if (h instanceof Transition) {
                            h2 = ThingMLFactory.eINSTANCE.createTransition();
                            ((State) h.eContainer()).getOutgoing().add((Transition) h2);
                            ((Transition) h2).setTarget(((Transition) h).getTarget());
                        } else {
                            h2 = ThingMLFactory.eINSTANCE.createInternalTransition();
                            ((State) h.eContainer()).getInternal().add((InternalTransition) h2);
                        }
                        if (h.getName() != null)
                            h2.setName(h.getName() + "bis");
                        final ReceiveMessage rm2 = ThingMLFactory.eINSTANCE.createReceiveMessage();
                        rm2.setName(rm.getName());
                        rm2.setPort(rm.getPort());
                        rm2.setMessage(m2);
                        h2.setEvent(rm2);

                        if (h.getGuard() != null) {
                            h2.setGuard(EcoreUtil.copy(h.getGuard()));
                            for (EventReference ref : ThingMLHelpers.getAllExpressions(h2.getGuard(), EventReference.class)) {
                                //System.out.println("Fixing event ref on parameter " + ref.getParameter().getName() + " of message " + m2.getName());
                                ref.setReceiveMsg(rm2);
                                ref.setParameter(m2.getParameters().get(m.getParameters().indexOf(ref.getParameter())));
                            }
                        }

                        if (h.getAction() != null) {
                            h2.setAction(EcoreUtil.copy(h.getAction()));
                            for (EventReference ref : ThingMLHelpers.getAllExpressions(h2.getAction(), EventReference.class)) {
                                //System.out.println("Fixing event ref on parameter " + ref.getParameter().getName() + " of message " + m2.getName());
                                ref.setReceiveMsg(rm2);
                                ref.setParameter(m2.getParameters().get(m.getParameters().indexOf(ref.getParameter())));
                            }
                        }
                    }
                }
            }
        }
    }

    private Port createOrGetInternalPort(Thing thing) {
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
