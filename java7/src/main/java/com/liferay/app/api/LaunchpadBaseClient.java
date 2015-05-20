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
	 * Adds new header. If the header with the same name already exists, it will
	 * not be overwritten, but new value will be stored. The order is preserved.
	 */
	public C header(String name, String value) {
		headers.add(new Entry<>(name, value));
		return (C)this;
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