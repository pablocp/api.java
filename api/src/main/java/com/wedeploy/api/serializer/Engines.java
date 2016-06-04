package com.wedeploy.api.serializer;
public class Engines {

	public Engines(Serializer serializer, Parser parser) {
		this.parser = parser;
		this.serializer = serializer;
	}

	public Parser getParser() {
		return parser;
	}

	public Serializer getSerializer() {
		return serializer;
	}

	private final Parser parser;
	private final Serializer serializer;

}