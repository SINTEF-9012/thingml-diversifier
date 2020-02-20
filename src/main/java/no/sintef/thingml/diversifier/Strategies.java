package no.sintef.thingml.diversifier;

public enum Strategies {
	/* Network/serialization strategies */
	RANDOM ("random", "A random sequence of strategies"),
	ADD_PARAM ("add-param", "Add a random parameter in messages"), 
	DUP_MSG ("dup-msg", "Duplicate messages, so that original or copy will be sent randomly"),  
	SHUFF_MSG ("shuff-msg", "Shuffles the messages within each thing"),
	SHUFF_PARAM ("shuff-param", "Shuffles the parameters of messages"),
	SPLIT_MSG ("split-msg", "Split messages, so that two messages should be sent (in random order) to reconstruct the orinal messages"),
	UP_PARAM ("up-param", "Upsize the type of parameters within messages"),
	CODE_MSG ("code-msg", "Add code to message"),
	LOG_MSG ("log-msg", "Logs messages"),
	PRELOG_MSG ("pre-log-msg", "Logs messages"),
	POSTLOG_MSG ("post-log-msg", "Logs messages");
	
	public final String name;
	public final String description;
	
	Strategies(String name, String description) {
		this.name = name;
		this.description = description;
	}
}
