package no.sintef.thingml.diversifier.strategies;

import org.thingml.xtext.thingML.ThingMLModel;

import no.sintef.thingml.diversifier.Manager;

public abstract class Strategy {
	
	protected static final boolean debug = false;
	protected final Manager manager;
	public long executionTime = -1;
	
	public Strategy(Manager manager) {
		this.manager = manager;
	}
	
	public void apply(ThingMLModel model) {
        final long start = System.currentTimeMillis();
        doApply(model);
        executionTime = System.currentTimeMillis() - start;
	}
	
	protected abstract void doApply(ThingMLModel model);
}
