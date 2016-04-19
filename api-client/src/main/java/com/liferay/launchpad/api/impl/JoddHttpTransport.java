package com.liferay.launchpad.api.impl;

import com.liferay.launchpad.api.BlockingTransport;
import com.liferay.launchpad.sdk.ContentType;
import com.liferay.launchpad.sdk.Request;
import com.liferay.launchpad.sdk.Response;
import com.liferay.launchpad.sdk.ResponseImpl;

import jodd.http.HttpBrowser;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

import java.util.List;
import java.util.stream.Collectors;

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

		List<jodd.http.Cookie> cookies = request.cookies().values()
			.stream()
			.map(cookie -> new jodd.http.Cookie(cookie.encode()))
			.collect(Collectors.toList());

		httpRequest.cookies(
			cookies.toArray(new jodd.http.Cookie[cookies.size()]));

		request.headers().forEach(
			header -> httpRequest.header(header.getKey().toString(), header.getValue()));

		request.params().forEach(
			param -> httpRequest.query(param.getKey().toString(), param.getValue()));

		request.forms().forEach(
			param -> httpRequest.form(param.getKey().toString(), param.getValue()));

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

		response.headers().forEach(
			header -> clientResponse.headers().add(
				header.getKey(), header.getValue()));

		return clientResponse;
	}

}
