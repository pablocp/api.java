package com.liferay.app.api.impl;

import com.liferay.app.api.Entry;
import com.liferay.app.api.LaunchpadClientDef;
import com.liferay.app.api.Transport;

import jodd.http.HttpBrowser;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

/**
 * Transport implementation that uses Jodd HTTP client (http://jodd.org).
 */
public class JoddHttpTransport implements Transport {

	@Override
	public String send(LaunchpadClientDef lc, String method, String body) {
		String url = lc.fullPath();

		final HttpRequest httpRequest = new HttpRequest()
						.method(method.toUpperCase())
						.set(url);

		lc.forEachHeader(new Entry.EntryConsumer<String, String>() {
			@Override
			public void accept(String key, String value) {
				httpRequest.header(key, value);
			}
		});

		lc.forEachQuery(new Entry.EntryConsumer<String, String>() {
			@Override
			public void accept(String key, String value) {
				httpRequest.query(key, value);
			}
		});

		if (body != null) {
			httpRequest.body(body);
		}

		HttpBrowser httpBrowser = new HttpBrowser();

		HttpResponse response = httpBrowser.sendRequest(httpRequest);

		return response.body();
	}

}