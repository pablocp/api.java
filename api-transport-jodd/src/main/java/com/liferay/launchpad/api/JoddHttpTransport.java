package com.liferay.launchpad.api;

import java.util.Map;

import jodd.http.HttpBrowser;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

/**
 * Transport implementation that uses Jodd HTTP client (http://jodd.org).
 */
public class JoddHttpTransport extends BlockingTransport {

	@Override
	protected ClientResponse sendBlockingRequest(ClientRequest clientRequest) {
		String url = clientRequest.url();

		final HttpRequest httpRequest = new HttpRequest()
						.method(clientRequest.method())
						.set(url);

		for (Map.Entry<String, String> entry : clientRequest.headers()) {
			httpRequest.header(entry.getKey(), entry.getValue());
		}

		for (Map.Entry<String, String> entry : clientRequest.queries()) {
			httpRequest.query(entry.getKey(), entry.getValue());
		}

		String body = clientRequest.body();

		if (body != null) {
			httpRequest.body(body);
		}

		HttpBrowser httpBrowser = new HttpBrowser();

		HttpResponse response = httpBrowser.sendRequest(httpRequest);

		ClientResponse clientResponse = new ClientResponse(clientRequest);

		clientResponse.statusCode(response.statusCode());
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