package no.sintef.thingml.diversifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.compilers.ThingMLCompiler;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.thingML.Type;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import no.sintef.thingml.diversifier.strategies.AddConstantParameters;
import no.sintef.thingml.diversifier.strategies.AddFakeNews;
import no.sintef.thingml.diversifier.strategies.AddRandomParameters;
import no.sintef.thingml.diversifier.strategies.BitShiftParameters;
import no.sintef.thingml.diversifier.strategies.DuplicateMessages;
import no.sintef.thingml.diversifier.strategies.OffsetParameters;
import no.sintef.thingml.diversifier.strategies.ShuffleMessages;
import no.sintef.thingml.diversifier.strategies.ShuffleParameters;
import no.sintef.thingml.diversifier.strategies.ShufflePort;
import no.sintef.thingml.diversifier.strategies.SplitParameters;
import no.sintef.thingml.diversifier.strategies.Strategy;

public class CLI {

	@Parameter(names = {"--strategy", "-s"}, description = "A diversification strategy to be applied")
	List<String> strategies;
	
	@Parameter(names = {"--input", "-i"}, description = "The input ThingML model")
	String input;
	
	@Parameter(names = {"--output", "-o"}, description = "Output directory for the generated ThingML model(s)")
	String output;
	
	@Parameter(names = {"--mode", "-m"}, description = "Mode: static or dynamic diversity")
	String mode = Mode.STATIC.name().toLowerCase();

	@Parameter(names = {"--amount", "-a"}, description = "Amount of output models to produce")
	long number = 1;
	
	@Parameter(names = {"--random", "-r"}, description = "Random seed")
	long seed = 1;
	
	@Parameter(names = {"--debug", "-d"}, description = "Engage debug mode (verbose logs)")
	boolean debug = false;
	
	@Parameter(names = {"--compiler", "-c"}, description = "ThingML compiler")
	String compiler;
	
	@Parameter(names = {"--noisy", "-n"}, description = "In -s <n> mode, allow for noisy mutations (i.e. enables fake-news and add-rnd).")
	boolean noisy = false;

	@Parameter(names = {"--help", "-h"}, help = true)
	private boolean help;

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && args[0].equals("thingml")) {//Exposes the original ThingML CLI
			org.thingml.compilers.commandline.Main.main(Arrays.copyOfRange(args, 1, args.length));
			return;
		}
		
		final CLI cli = new CLI();
		final JCommander jcom = JCommander.newBuilder().addObject(cli).build();
		jcom.parse(args);
		
		if (cli.help || cli.input == null || cli.strategies.size() == 0) {
			printUsage(jcom);
			System.exit(0);
		}
		
		if (cli.debug)
			Strategy.debug = true;

		final File input = new File(cli.input);
		if (!input.exists() || !FilenameUtils.getExtension(cli.input).equals("thingml")) throw new FileNotFoundException("Input file " + cli.input + " cannot be found");		
		
		final ThingMLModel model = ThingMLHelpers.flattenModel(ThingMLCompiler.loadModel(input));
		
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
		
		final Mode mode = (cli.mode.equals(Mode.DYNAMIC.name().toLowerCase())) ? Mode.DYNAMIC : Mode.STATIC;		
		
		if (cli.output == null) {
			cli.output = input.getParent() + "/diversified/";			
		}
		final File outputDir = new File(cli.output);
		outputDir.mkdirs();
		
		for(int i = 0; i < cli.number; i++) {//TODO: See if we can multi-thread this
			final ThingMLModel copy = EcoreUtil.copy(model);
			final Manager manager = CLI.init(jcom, 31*i+cli.seed, mode, cli.strategies, cli.compiler, cli.noisy);
			manager.run(copy);				
			String saveName = FilenameUtils.getBaseName(cli.input) + i + ".thingml";
			File saveTo = new File(outputDir, saveName);					
			ThingMLCompiler.saveAsThingML(copy, saveTo.getAbsolutePath());
			System.out.println("Saved to " + saveTo.getAbsolutePath());// + (manager.hasErrors(copy)?" with errors":" without error."));
		}

	}
	
	private static Manager init(JCommander jcom, long seed,  Mode mode, List<String> strategies, String compiler, boolean isNoisy) {
		final Manager manager = new Manager(seed, mode, compiler);
		for(String s : strategies) {
			if (s.matches("\\d+")) {	try {
				final int seq = Integer.parseInt(s);						
				for(int i = 0; i < seq; i++) {
					/*if (isNoisy)
						manager.add(new AddFakeNews(manager, 1));*/
					manager.add(new ShufflePort(manager));
					manager.add(new ShuffleParameters(manager));
					//manager.add(new AddConstantParameters(manager, 3));
					manager.add(new DuplicateMessages(manager, 5));
					manager.add(new ShuffleMessages(manager));
					manager.add(new SplitParameters(manager, 3));
					manager.add(new ShuffleParameters(manager));					
					manager.add(new OffsetParameters(manager, 5));
					manager.add(new BitShiftParameters(manager, 5));
					if (isNoisy)
						manager.add(new AddRandomParameters(manager, 3));
				} } catch (NumberFormatException nfe) {/*all good!*/}
			} else if (s.equals(Strategies.ADD_PARAM.name)) {
				manager.add(new AddRandomParameters(manager)); 
			} else if (s.equals(Strategies.OFF_PARAM.name)) {
				manager.add(new OffsetParameters(manager)); 
			} else if (s.equals(Strategies.SHIFT_PARAM.name)) {
				manager.add(new BitShiftParameters(manager)); 
			} else if (s.equals(Strategies.SPLIT_PARAM.name)) {
				manager.add(new SplitParameters(manager)); 
			} else if (s.equals(Strategies.DUP_MSG.name)) {
				manager.add(new DuplicateMessages(manager)); 
			} else if (s.equals(Strategies.SHUFF_PORT.name)) {
				manager.add(new ShufflePort(manager)); 
			} else if (s.equals(Strategies.SHUFF_MSG.name)) {
				manager.add(new ShuffleMessages(manager)); 
			} else if (s.equals(Strategies.SHUFF_PARAM.name)) {
				manager.add(new ShuffleParameters(manager)); 
			} else if (s.equals(Strategies.FAKE_NEWS.name)) {
				manager.add(new AddFakeNews(manager)); 
			} else {
				printUsage(jcom);
				throw new UnsupportedOperationException("Diversification strategy " + s + " is not supported.");
			}
		}
		
		return manager;
	}

	private static void logo() {
		System.out.println("  _____ _     _             __  __ _           ");
		System.out.println(" |_   _| |__ (_)_ __   __ _|  \\/  | |          ");
		System.out.println("   | | | '_ \\| | '_ \\ / _\\`| |\\/| | |          ");
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
		System.out.println("    -a <number>\\ ");
		System.out.println("    -s <first-strategy> [-s <other-strageties>]* or -s <n:int>");

		jcom.usage();

		System.out.println("Valid -s strategies are:");
		for (Strategies s : Strategies.values()) {
			System.out.println("  └╼  " + s.name + ":\t\t" + s.description);
		}
		System.out.println("  └╼  <n:int>:\t\tgoes n times through all strategies, applying them or not with a given probability");
		
		System.out.println("\nValid -m modes are:");
		for (Mode m : Mode.values()) {
			System.out.println("  └╼  " + m.name().toLowerCase());
		}
		
		System.out.println("\n-------------------------------------\n");
		System.out.println("To call ThingML compilers and tools: ");
		System.out.println("  java -jar <thingml-diversifivier-XXX.jar> thingml <thingml-options>");
		org.thingml.compilers.commandline.Main.main(new String[]{"-h"});
		
	}

}
