package com.liferay.app.api;

import java.util.ArrayList;
import java.util.List;
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
	 * Returns request path.
	 */
	public String path() {
		return path;
	}

	/**
	 * Sets the request path.
	 */
	public ClientRequest path(String path) {
		this.path = path;
		return this;
	}

	/**
	 * Returns a list of all queries.
	 */
	public List<Entry<String, String>> queries() {
		return queries;
	}

	/**
	 * Adds new query value;
	 */
	public ClientRequest query(String name, String value) {
		queries.add(new Entry<String, String>(name, value));
		return this;
	}

	protected String method;
	protected String path;
	protected List<Entry<String, String>> queries
		= new ArrayList<Entry<String, String>>();

}