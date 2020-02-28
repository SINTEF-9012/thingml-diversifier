package no.sintef.thingml.diversifier;

public enum Strategies {
	RANDOM ("random", "A random sequence of strategies"),
	ADD_PARAM ("add-param", "Adds a random parameter in messages"), 
	OFF_PARAM ("off-param", "Offsets parameters in messages by a random value"), 
	DUP_MSG ("dup-msg", "Duplicates messages, so that original or copy will be sent randomly"),
	SHUFF_PORT ("shuff-port", "Shuffles the ports within each thing"),
	SHUFF_MSG ("shuff-msg", "Shuffles the messages within each thing"),
	SHUFF_PARAM ("shuff-param", "Shuffles the parameters of messages"),
	SPLIT_MSG ("split-msg", "Splits messages, so that two messages should be sent (in random order) to reconstruct the orinal messages"),
	UP_PARAM ("up-param", "Upsizes the type of parameters within messages");
	
	public final String name;
	public final String description;
	
	Strategies(String name, String description) {
		this.name = name;
		this.description = description;
	}
}
