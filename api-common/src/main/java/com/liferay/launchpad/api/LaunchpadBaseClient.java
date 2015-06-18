package com.liferay.launchpad.api;

import java.util.ArrayList;
import java.util.List;

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
		headers.add(new Entry<String, String>(name, value));
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
	 * Adds a query. If the query with the same name already exists, it will not
	 * be overwritten, but new value will be stored. The order is preserved.
	 */
	public C query(String name, String value) {
		queries.add(new Entry<String, String>(name, value));
		return (C)this;
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

		final ClientRequest clientRequest = new ClientRequest();

		clientRequest.url(url());
		clientRequest.method(methodName);

		clientRequest.headers = headers;
		clientRequest.queries = queries;

		clientRequest.body(body);

		return transport.send(clientRequest);
	}

	protected Transport<F> customTransport;
	protected final List<Entry<String, String>> headers =
		new ArrayList<Entry<String, String>>();
	protected final List<Entry<String, String>> queries =
		new ArrayList<Entry<String, String>>();
	protected final String url;

}