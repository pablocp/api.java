package com.liferay.launchpad.api.impl;

import com.liferay.launchpad.api.BlockingTransport;
import com.liferay.launchpad.sdk.ContentType;
import com.liferay.launchpad.sdk.Cookie;
import com.liferay.launchpad.sdk.Request;
import com.liferay.launchpad.sdk.Response;
import com.liferay.launchpad.sdk.ResponseImpl;

import java.util.Map;

import jodd.http.HttpBrowser;
import jodd.http.HttpMultiMap;
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

			// TODO(igor): use new Jodd for helper method for cookie

			httpRequest.header("Cookie", cookie.encode());
		}

		for (Map.Entry<String, String> entry : request.headers()) {
			httpRequest.header(entry.getKey(), entry.getValue());
		}

		for (Map.Entry<String, String> entry : request.params()) {
			httpRequest.query(entry.getKey(), entry.getValue());
		}

		for (Map.Entry<String, Object> entry : request.forms()) {
			httpRequest.form(entry.getKey(), entry.getValue());
		}

		String body = request.body();

		if (body != null) {
			String contentTypeString = request.contentType();

			ContentType contentType = ContentType.TEXT;

			if (contentTypeString != null) {
				contentType = new ContentType(contentTypeString);
			}

			httpRequest.bodyText(
				body, contentType.contentType(), contentType.charset());
		}

		HttpBrowser httpBrowser = new HttpBrowser();

		HttpResponse response = httpBrowser.sendRequest(httpRequest);

		ResponseImpl clientResponse = new ResponseImpl(request);

		clientResponse.status(response.statusCode(), response.statusPhrase());
		clientResponse.body(response.bodyText());

		HttpMultiMap<String> responseHeaders = response.headers();

		for (Map.Entry<String, String> entry : responseHeaders) {
			String name = entry.getKey();
			String value = entry.getValue();

			clientResponse.header(name, value);
		}

		return clientResponse;
	}

}