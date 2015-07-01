package com.liferay.launchpad.api;

import com.liferay.launchpad.sdk.PodMultiMap;
import com.liferay.launchpad.sdk.PodMultiMapFactory;
import com.liferay.launchpad.sdk.Request;
import com.liferay.launchpad.sdk.RequestImpl;

/**
 * Base client contains code that is same for all java versions.
 */
public abstract class LaunchpadBaseClient<F, C> {

	/**
	 * Executes DELETE request.
	 */
	public F delete() {
		return delete(null);
	}

	/**
	 * Executes DELETE request.
	 */
	public F delete(final String body) {
		return sendAsync("DELETE", body);
	}

	/**
	 * Executes GET request.
	 */
	public F get() {
		return sendAsync("GET", null);
	}

	/**
	 * Adds new header. If the header with the same name already exists, it will
	 * not be overwritten, but new value will be stored. The order is preserved.
	 */
	public C header(String name, String value) {
		headers.set(name, value);
		return (C)this;
	}

	/**
	 * Sets a query parameter. If the parameter with the same name already
	 * exists, it will be overwritten.
	 */
	public C param(String name, String value) {
		params.set(name, value);
		return (C)this;
	}

	/**
	 * Executes PATCH request.
	 */
	public F patch() {
		return patch(null);
	}

	/**
	 * Executes PATCH request.
	 */
	public F patch(final String body) {
		return sendAsync("PATCH", body);
	}

	/**
	 * Executes POST request.
	 */
	public F post() {
		return post(null);
	}

	/**
	 * Executes POST request.
	 */
	public F post(final String body) {
		return sendAsync("POST", body);
	}

	/**
	 * Executes PUT request.
	 */
	public F put() {
		return put(null);
	}

	/**
	 * Executes PUT request.
	 */
	public F put(final String body) {
		return sendAsync("PUT", body);
	}

	/**
	 * Returns full URL.
	 */
	public String url() {
		return url;
	}

	/**
	 * Specifies {@link Transport} implementation.
	 */
	public C use(Transport<F> transport) {
		this.customTransport = transport;
		return (C)this;
	}

	/**
	 * Main constructor.
	 */
	protected LaunchpadBaseClient(String url) {
		this.url = url;
	}

	/**
	 * Continuations constructor, used from existing instance, therefore
	 * no need to configure the client.
	 */
	protected LaunchpadBaseClient(String baseUrl, String url) {
		this.url = Util.joinPaths(baseUrl, url);
	}

	/**
	 * Resolves transport. Throws exception if transport is missing.
	 */
	protected Transport<F> resolveTransport() {
		Transport<F> transport = customTransport;

		if (transport == null) {
			TransportBinder transportBinder = Binder.getTransportBinder();

			if (transportBinder != null) {
				transport = transportBinder.initTransport();
			}
		}

		System.out.println("transport = " + transport);

		if (transport == null) {
			throw new LaunchpadClientException("Transport not defined!");
		}

		return transport;
	}

	/**
	 * Uses transport to send request with given method name and body
	 * asynchronously.
	 */
	protected F sendAsync(final String methodName, final String body) {
		final Transport<F> transport = resolveTransport();

		final Request request = new RequestImpl();

		request.url(url());
		request.method(methodName);
		request.headers(headers);
		request.params(params);
		request.body(body);

		return transport.send(request);
	}

	protected Transport<F> customTransport;
	protected final PodMultiMap headers = PodMultiMapFactory.newMultiMap();
	protected final PodMultiMap params = PodMultiMapFactory.newMultiMap();
	protected final String url;

}