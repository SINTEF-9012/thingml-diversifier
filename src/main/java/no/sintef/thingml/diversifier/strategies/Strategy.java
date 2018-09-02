package no.sintef.thingml.diversifier.strategies;

import org.thingml.xtext.thingML.ThingMLModel;

public abstract class Strategy {
	
	protected static final boolean debug = false;
	
	public long executionTime = -1;
	
	protected Strategy() {}
	
	public void apply(ThingMLModel model) {
        final long start = System.currentTimeMillis();
        doApply(model);
        executionTime = System.currentTimeMillis() - start;
	}
	
	protected abstract void doApply(ThingMLModel model);
}
