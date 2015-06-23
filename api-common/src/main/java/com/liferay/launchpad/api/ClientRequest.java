package com.liferay.launchpad.api;

public class ClientRequest extends ClientMessage<ClientRequest> {

	/**
	 * Returns request method.
	 */
	public String method() {
		return method;
	}

	/**
	 * Sets request method.
	 */
	public ClientRequest method(String method) {
		this.method = method;
		return this;
	}

	/**
	 * Returns queries.
	 */
	public MultiMap queries() {
		return queries;
	}

	public ClientRequest query(String name, boolean value) {
		queries.add(name, String.valueOf(value));
		return this;
	}

	public ClientRequest query(String name, int value) {
		queries.add(name, String.valueOf(value));
		return this;
	}

	/**
	 * Adds new query value.
	 */
	public ClientRequest query(String name, String value) {
		queries.add(name, value);
		return this;
	}

	/**
	 * Returns request url.
	 */
	public String url() {
		return url;
	}

	/**
	 * Sets the request path.
	 */
	public ClientRequest url(String path) {
		this.url = path;
		return this;
	}

	protected String method;
	protected MultiMap queries = new MultiMap();
	protected String url;

}