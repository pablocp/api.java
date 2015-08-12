package com.liferay.launchpad.api.impl;

import com.liferay.launchpad.api.BlockingTransport;
import com.liferay.launchpad.sdk.Cookie;
import com.liferay.launchpad.sdk.Request;
import com.liferay.launchpad.sdk.Response;
import com.liferay.launchpad.sdk.ResponseImpl;
import com.liferay.launchpad.sdk.ValuesUtil;

import java.util.Map;

import jodd.http.HttpBrowser;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

/**
 * Transport implementation that uses Jodd HTTP client (http://jodd.org).
 */
public class JoddHttpTransport extends BlockingTransport {

	@Override
	public Response send(Request request) {
		String url = request.url();

		final HttpRequest httpRequest = new HttpRequest()
						.method(request.method())
						.set(url);

		for (Cookie cookie : request.cookies().values()) {

			// TODO(igor): use new Jodd for helper method

			httpRequest.header("Cookie", cookie.encode());
		}

		for (Map.Entry<String, Object> entry : request.headers()) {
			httpRequest.header(
				entry.getKey(), ValuesUtil.toString(entry.getValue()));
		}

		for (Map.Entry<String, Object> entry : request.params()) {
			httpRequest.query(
				entry.getKey(), ValuesUtil.toString(entry.getValue()));
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