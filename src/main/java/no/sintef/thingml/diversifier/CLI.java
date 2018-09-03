package no.sintef.thingml.diversifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.compilers.ThingMLCompiler;
import org.thingml.xtext.thingML.ThingMLModel;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import no.sintef.thingml.diversifier.strategies.AddMessageCode;
import no.sintef.thingml.diversifier.strategies.AddMessageLogs;
import no.sintef.thingml.diversifier.strategies.AddRandomParameters;
import no.sintef.thingml.diversifier.strategies.DuplicateMessages;
import no.sintef.thingml.diversifier.strategies.ShuffleMessages;
import no.sintef.thingml.diversifier.strategies.ShuffleParameters;
import no.sintef.thingml.diversifier.strategies.SplitMessages;
import no.sintef.thingml.diversifier.strategies.SplitMessagesInline;
import no.sintef.thingml.diversifier.strategies.UpsizeParameters;

public class CLI {

	@Parameter(names = {"--strategy", "-s"}, description = "A diversification strategy to be applied")
	List<String> strategies;

	@Parameter(names = {"--input", "-i"}, description = "The input ThingML model")
	String input;
	
	@Parameter(names = {"--output", "-o"}, description = "Output directory for the generated ThingML model(s)")
	String output;
	
	@Parameter(names = {"--mode", "-m"}, description = "Mode: static or dynamic diversity")
	String mode = Mode.STATIC.name().toLowerCase();

	@Parameter(names = {"--number", "-n"}, description = "Number of output models to produce")
	long number = 1;
	
	@Parameter(names = {"--random", "-r"}, description = "Random seed")
	long seed = 1;
	

	@Parameter(names = {"--help", "-h"}, help = true)
	private boolean help;

	public static void main(String[] args) throws Exception {
		final CLI cli = new CLI();
		final JCommander jcom = JCommander.newBuilder().addObject(cli).build();
		jcom.parse(args);

		if (cli.help || cli.input == null || cli.strategies.size() == 0) {
			printUsage(jcom);
			System.exit(0);
		}

		final File input = new File(cli.input);
		if (!input.exists() || !FilenameUtils.getExtension(cli.input).equals("thingml")) throw new FileNotFoundException("Input file " + cli.input + " cannot be found");		
		
		final ThingMLModel model = ThingMLCompiler.flattenModel(ThingMLCompiler.loadModel(input));
		
		Mode mode = Mode.STATIC;
		if (cli.mode.equals(Mode.DYNAMIC.name().toLowerCase())) mode = Mode.DYNAMIC;
		
		final Manager manager = new Manager(cli.seed, mode);

		/*if (manager.hasErrors(model))
			throw new Error("Input model " + cli.input + " contains error(s). Please check your model in the ThingML IDE.");*/

		for(String s : cli.strategies) {
			if (s.equals(Strategies.ADD_PARAM.name)) {
				manager.add(new AddRandomParameters()); 
			} else if (s.equals(Strategies.DUP_MSG.name)) {
				manager.add(new DuplicateMessages()); 
			} else if (s.equals(Strategies.SHUFF_MSG.name)) {
				manager.add(new ShuffleMessages()); 
			} else if (s.equals(Strategies.SHUFF_PARAM.name)) {
				manager.add(new ShuffleParameters()); 
			} else if (s.equals(Strategies.SPLIT_MSG.name)) {
				manager.add(new SplitMessages()); 
				//manager.add(new SplitMessagesInline());
			} else if (s.equals(Strategies.UP_PARAM.name)) {
				manager.add(new UpsizeParameters()); 
			} else if (s.equals(Strategies.CODE_MSG.name)) {
				manager.add(new AddMessageCode()); 
			} else if (s.equals(Strategies.LOG_MSG.name)) {
				manager.add(new AddMessageLogs()); 
			} else {
				printUsage(jcom);
				throw new UnsupportedOperationException("Diversification strategy " + s + " is not supported.");
			}
		}

		if (cli.output == null) {
			cli.output = input.getParent() + "/diversified/";			
		}
		final File outputDir = new File(cli.output);
		outputDir.mkdirs();
		
		for(int i = 0; i < cli.number; i++) {//TODO: See if we can multi-thread this
			final ThingMLModel copy = EcoreUtil.copy(model);
			manager.run(copy);			
			String saveName = FilenameUtils.getBaseName(cli.input) + i + ".thingml";
			File saveTo = new File(outputDir, saveName);					
			ThingMLCompiler.saveAsThingML(copy, saveTo.getAbsolutePath());
			System.out.println("Saved to " + saveTo.getAbsolutePath());// + (manager.hasErrors(copy)?" with errors":" without error."));
		}

	}

	private static void printUsage(JCommander jcom) {
		System.out.println(" --- thingml-diversifier help ---");

		System.out.println("Typical usages: ");        
		System.out.println("  java -jar <thingml-diversifivier-XXX.jar>\\ ");
		System.out.println("    -i <my-model.thingml>\\ ");
		System.out.println("    -o <output-dir>\\ ");
		System.out.println("    -m <mode>\\ ");
		System.out.println("    -n <number>\\ ");
		System.out.println("    -s <first-strategy> [-s <other-strageties>]*");

		jcom.usage();

		System.out.println("Valid -s strategies are:");
		for (Strategies s : Strategies.values()) {
			System.out.println("  └╼  " + s.name + ":\t\t" + s.description);
		}
		
		System.out.println("\nValid -m modes are:");
		for (Mode m : Mode.values()) {
			System.out.println("  └╼  " + m.name().toLowerCase());
		}
	}

}
