package no.sintef.thingml.diversifier;

public enum Strategies {
	RANDOM ("random", "A random sequence of strategies"),
	ADD_PARAM ("add-rnd", "Adds a random parameter in messages"), 
	ADD_CONST ("add-const", "Adds a random constant parameter in messages"), 
	OFF_PARAM ("off-param", "Offsets parameters in messages by a random value"),
	SHIFT_PARAM ("shift-param", "Shifts parameters by a random number of bits"),
	SPLIT_PARAM ("split-param", "Splits parameters into two complementary parameters"),
	DUP_MSG ("dup-msg", "Duplicates messages, so that original or copy will be sent randomly"),
	SHUFF_PORT ("shuff-port", "Shuffles the ports within each thing"),
	SHUFF_MSG ("shuff-msg", "Shuffles the messages within each thing"),
	SHUFF_PARAM ("shuff-param", "Shuffles the parameters of messages"),
	FAKE_NEWS ("fake-news", "Sends a random, and purely irrelevant, tweet (message) somewhere in a randomly chosen block of code");
	
	public final String name;
	public final String description;
	
	Strategies(String name, String description) {
		this.name = name;
		this.description = description;
	}
}
