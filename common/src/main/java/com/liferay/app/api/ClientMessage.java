package com.liferay.app.api;

import java.util.ArrayList;
import java.util.List;
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
		headers.add(new Entry<String, String>(name, value));
		return (T)this;
	}

	/**
	 * Returns list of headers.
	 */
	public List<Entry<String, String>> headers() {
		return headers;
	}

	protected String body;
	protected List<Entry<String, String>> headers =
		new ArrayList<Entry<String, String>>();

}