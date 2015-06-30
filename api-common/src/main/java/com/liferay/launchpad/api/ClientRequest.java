package com.liferay.launchpad.api;

import com.liferay.launchpad.sdk.PodMultiMap;
import com.liferay.launchpad.sdk.PodMultiMapFactory;
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
	public PodMultiMap queries() {
		return queries;
	}

	public ClientRequest query(String name, boolean value) {
		queries.set(name, String.valueOf(value));
		return this;
	}

	public ClientRequest query(String name, int value) {
		queries.set(name, String.valueOf(value));
		return this;
	}

	/**
	 * Sets new query value for key name.
	 */
	public ClientRequest query(String name, String value) {
		queries.set(name, value);
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
	protected PodMultiMap queries = PodMultiMapFactory.newMultiMap();
	protected String url;

}