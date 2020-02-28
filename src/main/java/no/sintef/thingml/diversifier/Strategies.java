package no.sintef.thingml.diversifier;

public enum Strategies {
	RANDOM ("random", "A random sequence of strategies"),
	ADD_PARAM ("add-param", "Add a random parameter in messages"), 
	DUP_MSG ("dup-msg", "Duplicate messages, so that original or copy will be sent randomly"),
	SHUFF_PORT ("shuff-port", "Shuffles the ports within each thing"),
	SHUFF_MSG ("shuff-msg", "Shuffles the messages within each thing"),
	SHUFF_PARAM ("shuff-param", "Shuffles the parameters of messages"),
	SPLIT_MSG ("split-msg", "Split messages, so that two messages should be sent (in random order) to reconstruct the orinal messages"),
	UP_PARAM ("up-param", "Upsize the type of parameters within messages");
	
	public final String name;
	public final String description;
	
	Strategies(String name, String description) {
		this.name = name;
		this.description = description;
	}
}
