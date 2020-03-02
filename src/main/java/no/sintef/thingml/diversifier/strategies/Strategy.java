package no.sintef.thingml.diversifier.strategies;

import org.thingml.xtext.thingML.ThingMLModel;

import no.sintef.thingml.diversifier.Manager;

public abstract class Strategy {
	
	public static boolean debug = false;
	protected final Manager manager;
	public long executionTime = -1;
	public final int probability; //an integer from 0 (0% chance of applying strategy to elements) to 10 (100% chance of applying strategy to elements)	
	public Strategy(Manager manager) {
		this.manager = manager;
		probability = 10;
	}
	
	public Strategy(Manager manager, int probability) {
		this.manager = manager;		
		this.probability = probability;
	}
	
	public void apply(ThingMLModel model) {
        final long start = System.currentTimeMillis();
        doApply(model);
        executionTime = System.currentTimeMillis() - start;
	}
	
	protected abstract void doApply(ThingMLModel model);
}
