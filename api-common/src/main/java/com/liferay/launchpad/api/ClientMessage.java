package com.liferay.launchpad.api;

public abstract class ClientMessage<T extends ClientMessage> {

	/**
	 * Returns the body.
	 */
	public String body() {
		return body;
	}

	/**
	 * Sets the body value.
	 */
	public T body(String body) {
		this.body = body;
		return (T)this;
	}

	/**
	 * Sets the header value.
	 */
	public T header(String name, String value) {
		headers.add(name, value);
		return (T)this;
	}

	/**
	 * Returns headers map.
	 */
	public MultiMap headers() {
		return headers;
	}

	protected String body;
	protected MultiMap headers = new MultiMap();

}