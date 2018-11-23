package no.sintef.thingml.diversifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.compilers.ThingMLCompiler;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.thingML.Type;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import no.sintef.thingml.diversifier.strategies.AddMessageCode;
import no.sintef.thingml.diversifier.strategies.AddMessageLogs;
import no.sintef.thingml.diversifier.strategies.AddMessageLogsPost;
import no.sintef.thingml.diversifier.strategies.AddMessageLogsPre;
import no.sintef.thingml.diversifier.strategies.AddRandomParameters;
import no.sintef.thingml.diversifier.strategies.DuplicateMessages;
import no.sintef.thingml.diversifier.strategies.ShuffleMessages;
import no.sintef.thingml.diversifier.strategies.ShuffleParameters;
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
		
		//Remove useless stuff in the flatten model (not point in diversifying what won't be instantiated
		final List<Type> types = new ArrayList<>();
		types.addAll(model.getTypes());
		for(Type t : types) {
			if (!(t instanceof Thing)) continue;
			final Thing thing = (Thing)t;
			boolean remove = true;
			outer: for(Configuration cfg : model.getConfigs()) {
				for(Thing thing2 : ConfigurationHelper.allUsedThings(cfg)) {
					if (EcoreUtil.equals(thing, thing2)) {
						remove = false;
						break outer;
					}
				}
			}
			if (remove) {
				model.getTypes().remove(t);
			}
		}
		
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
				manager.add(new SplitMessagesInline());
			} else if (s.equals(Strategies.UP_PARAM.name)) {
				manager.add(new UpsizeParameters()); 
			} else if (s.equals(Strategies.CODE_MSG.name)) {
				manager.add(new AddMessageCode()); 
			} else if (s.equals(Strategies.LOG_MSG.name)) {
				manager.add(new AddMessageLogs()); 
			} else if (s.equals(Strategies.PRELOG_MSG.name)) {
				manager.add(new AddMessageLogsPre()); 
			} else if (s.equals(Strategies.POSTLOG_MSG.name)) {
				manager.add(new AddMessageLogsPost()); 
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

	private static void logo() {
		System.out.println("  _____ _     _             __  __ _           ");
		System.out.println(" |_   _| |__ (_)_ __   __ _|  \\/  | |          ");
		System.out.println("   | | | '_ \\| | '_ \\ / _\\` | |\\/| | |          ");
		System.out.println("   | | | | | | | | | | (_| | |  | | |___       ");
		System.out.println("   |_| |_| |_|_|_| |_|\\__, |_|  |_|_____|      ");
		System.out.println("  ____  _             |___/  _  __ _           ");
		System.out.println(" |  _ \\(_)_   _____ _ __ ___(_)/ _(_) ___ _ __ ");
		System.out.println(" | | | | \\ \\ / / _ \\ '__/ __| | |_| |/ _ \\ '__|");
		System.out.println(" | |_| | |\\ V /  __/ |  \\__ \\ |  _| |  __/ |   ");
		System.out.println(" |____/|_| \\_/ \\___|_|  |___/_|_| |_|\\___|_|   ");
		System.out.println("                                               ");
	}
	
	private static void printUsage(JCommander jcom) {		
		logo();

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
