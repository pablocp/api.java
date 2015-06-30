package com.liferay.launchpad.api;

import com.liferay.launchpad.sdk.PodMultiMap;
import com.liferay.launchpad.sdk.PodMultiMapFactory;
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
		headers.set(name, value);
		return (T)this;
	}

	/**
	 * Returns headers map.
	 */
	public PodMultiMap headers() {
		return headers;
	}

	protected String body;
	protected PodMultiMap headers = PodMultiMapFactory.newMultiMap();

}