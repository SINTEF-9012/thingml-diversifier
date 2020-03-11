package no.sintef.thingml.diversifier;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.eclipse.emf.ecore.EObject;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.AnnotatedElement;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;

import no.sintef.thingml.diversifier.strategies.Strategy;

public class Manager {

	public Random rnd;

	//private Checker checker;
	private List<Strategy> strategies = new LinkedList<>();
	public  Mode mode = Mode.STATIC;
	public String compiler;

	private final boolean verbose = true;

	private Manager() {}

	public Manager(long seed, Mode mode, String compiler) {
		this.mode = mode;
		//checker = new Checker();	
		rnd = new Random(seed);
		this.compiler = compiler;
	}

	public void add(Strategy s) {
		strategies.add(s);
	}

	public void run(ThingMLModel model) throws Exception {
		final long start = System.currentTimeMillis();
		for(Strategy s : strategies) {
			s.apply(model);			
		}
		System.out.println("Diversification took " + (System.currentTimeMillis() - start) + "ms.");	
	}

	/*boolean hasErrors(ThingMLModel model) {
		File tmp = new File(FileUtils.getTempDirectoryPath() + UUID.randomUUID() + ".thingml");
		checker.validateModel(model);
		return model.eResource().getErrors().size() > 0 || checker.hasErrors();
	}

	private void printErrors(Strategy s, ThingMLModel model, Checker checker) {
		final List<Diagnostic> errors = model.eResource().getErrors();
		if (errors.size() > 0) {
			System.err.println("Strategy " + s.type.name + " has produced " + errors.size() + " EMF errors:");
			for (Diagnostic d : errors) {    
				String location = d.getLocation();
				if (location == null) {
					location = model.eResource().getURI().toFileString();
				}
				System.err.println("Error in file  " + location + " (" + d.getLine() + ", " + d.getColumn() + "): " + d.getMessage());            	
			}
		}
		if (checker.getErrors().size() > 0) {
			System.err.println("Strategy " + s.type.name + " has produced " + errors.size() + " ThingML errors:");
			String location = "";
			for (Issue error : checker.getErrors()) {
				if (error.getUriToProblem()!=null && error.getUriToProblem().toFileString()!=null && !location.equals(error.getUriToProblem().toFileString())) {
					System.err.println("Error(s) in " + error.getUriToProblem().toFileString());
					location = error.getUriToProblem().toFileString();
				}
				System.err.println("\t[line " + error.getLineNumber() + "]: " + error.getMessage());
			}
		}
	}*/

	public static boolean diversify(EObject o) {
		if (o == null) return true;
		if (!(o instanceof AnnotatedElement)) return diversify(o.eContainer());
		AnnotatedElement ae = (AnnotatedElement) o;
		if (AnnotatedElementHelper.isDefined(ae, "diversify", "not") || AnnotatedElementHelper.hasFlag(ae, "stl")) 
			return false;
		else
			return diversify(ae.eContainer());
	}

	public static Function findRandom(Thing thing) {
		Function rnd = null;
		List<Thing> includes = new ArrayList<Thing>();
		includes.add(thing);
		outer: while(rnd == null && !includes.isEmpty()) {
			for(Thing t : includes) {
				for(Function f : t.getFunctions()) {
					if (f.getName().equals("rnd")) {
						rnd = f;
						break outer;
					}	
				}
			}
			List<Thing> temp = new ArrayList<Thing>();
			temp.addAll(includes);
			includes.clear();
			for(Thing t : temp) {
				includes.addAll(t.getIncludes());
			}
		}
		return rnd;
	}

}
