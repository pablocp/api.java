package com.liferay.launchpad.api;

import com.liferay.launchpad.sdk.RequestImpl;
import com.liferay.launchpad.sdk.ResponseImpl;

import java.util.Map;

import jodd.http.HttpBrowser;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

/**
 * Transport implementation that uses Jodd HTTP client (http://jodd.org).
 */
public class JoddHttpTransport extends BlockingTransport {

	@Override
	protected ResponseImpl sendBlockingRequest(RequestImpl request) {
		String url = request.url();

		final HttpRequest httpRequest = new HttpRequest()
						.method(request.method())
						.set(url);

		for (Map.Entry<String, String> entry : request.headers()) {
			httpRequest.header(entry.getKey(), entry.getValue());
		}

		for (Map.Entry<String, String> entry : request.params()) {
			httpRequest.query(entry.getKey(), entry.getValue());
		}

		String body = request.body();

		if (body != null) {
			httpRequest.body(body);
		}

		HttpBrowser httpBrowser = new HttpBrowser();

		HttpResponse response = httpBrowser.sendRequest(httpRequest);

		ResponseImpl clientResponse = new ResponseImpl(request);

		clientResponse.statusCode(response.statusCode());
		clientResponse.statusMessage(response.statusPhrase());
		clientResponse.body(response.body());

		Map<String, String[]> responseHeaders = response.headers();

		for (Map.Entry<String, String[]> entry : responseHeaders.entrySet()) {
			String name = entry.getKey();
			String[] values = entry.getValue();

			for (String value : values) {
				clientResponse.header(name, value);
			}
		}

		return clientResponse;
	}

}