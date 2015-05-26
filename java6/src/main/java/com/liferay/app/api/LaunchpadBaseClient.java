package com.liferay.app.api;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Base client contains code that is same for all java versions.
 */
public abstract class LaunchpadBaseClient<F, C> {

	public LaunchpadBaseClient(String url) {
		this.url = url;
	}

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

	public void forEachHeader(
		Entry.EntryConsumer<String, String> headerConsumer) {

		for (Entry<String, String> header : headers) {
			headerConsumer.accept(header.key(), header.value());
		}
	}

	public void forEachQuery(
		Entry.EntryConsumer<String, String> queryConsumer) {

		for (Entry<String, String> query : queries) {
			queryConsumer.accept(query.key(), query.value());
		}
	}

	/**
	 * Returns full URL path.
	 */
	public String fullPath() {
		return url;
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
		headers.add(new Entry<>(name, value));
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
		queries.add(new Entry<>(name, value));
		return (C)this;
	}

	protected LaunchpadBaseClient(String baseUrl, String url) {
		this.url = baseUrl + url;
	}

	/**
	 * Uses transport to send request with given method name and body
	 * asynchronously.
	 */
	protected F sendAsync(final String methodName, final String body) {
		final Transport transport = TransportFactory.instance().getDefault();

		return (F)asyncRunner.runAsync(new Callable<String>() {
			@Override
			public String call() throws Exception {
				return transport.send(
					(LaunchpadClient)LaunchpadBaseClient.this, methodName,
					body);
			}
		});
	}

	protected static AsyncRunner asyncRunner;

	protected final List<Entry<String, String>> headers = new ArrayList<>();
	protected final List<Entry<String, String>> queries = new ArrayList<>();
	protected final String url;

}