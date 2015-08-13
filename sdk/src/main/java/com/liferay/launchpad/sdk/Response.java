/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation; either version
 * 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */

package com.liferay.launchpad.sdk;

import java.util.List;
import java.util.Map;

/**
 * HTTP response.
 */
public interface Response {

	/**
	 * Returns body content.
	 */
	public String body();

	/**
	 * Sets the JSON body content.
	 */
	public Response body(Object body);

	/**
	 * Sets the body content. Body content can be set more then once.
	 */
	public Response body(String body);

	/**
	 * Sets the body content and common {@link ContentType content-type}.
	 * Just a shortcut call.
	 */
	public Response body(String body, ContentType contentType);

	/**
	 * Gets the content type header.
	 */
	public String contentType();

	/**
	 * Sets the content type header.
	 */
	public Response contentType(ContentType contentType);

	/**
	 * Returns the context that this response belongs to.
	 */
	public Context context();

	/**
	 * @return A map of all the cookies.
	 */
	Map<String, Cookie> cookies();

	/**
	 * Adds a cookie to the response.
	 */
	public Response cookie(Cookie cookie);

	/**
	 * Gets cookie value by name.
	 */
	public Cookie cookie(String name);

	/**
	 * Closes the response. Shortcut for {@code body(null)}.
	 */
	public void end();

	/**
	 * Sets the body and clsoes the response.
	 */
	public default void end(Object body) {
		body(body);
		end();
	}

	/**
	 * Sets the body content and closes the response.
	 */
	public default void end(String body) {
		body(body);
		end();
	}

	/**
	 * Sets the body content and common {@link ContentType content-type}
	 * and closes the response.
	 */
	public default void end(String body, ContentType contentType) {
		body(body, contentType);
		end();
	}

	/**
	 * Gets header value by name.
	 */
	public String header(String name);

	/**
	 * Sets the first header name with the specified value.
	 */
	public Response header(String name, String value);

	/**
	 * Returns headers.
	 */
	public PodMultiMap headers();

	/**
	 * Returns <code>true</code> if response is already committed.
	 */
	public boolean isCommitted();

	/**
	 * Checks if the response contains a content type header with the specified value.
	 */
	public boolean isContentType(ContentType contentType);

	/**
	 * Returns parsed {@link #body() body content}.
	 * If body is not set, returns <code>null</code>.
	 */
	public <T> T parse();

	/**
	 * Returns parsed {@link #body() body content}.
	 * If body is not set, returns <code>null</code>.
	 */
	public <T> T parse(Class<T> type);

	/**
	 * Returns parsed {@link #body() body content}.
	 * If body is not set, returns <code>null</code>.
	 */
	public <T> List<T> parseList(Class<T> componentType);

	/**
	 * Pipes response to another response.
	 */
	public Response pipe(Response response);

	/**
	 * Redirects response to an url.
	 */
	public default void redirect(String url) {
		this.status(302, "Found.");
		this.header("Location", url);
		this.end();
	}

	/**
	 * Returns associated request of this response.
	 */
	public Request request();

	/**
	 * Sets the response status code and message.
	 */
	public default Response status(int statusCode, String statusMessage) {
		statusCode(statusCode);
		statusMessage(statusMessage);
		return this;
	}

	/**
	 * Returns status code.
	 */
	public int statusCode();

	/**
	 * Sets the status code.
	 */
	public Response statusCode(int statusCode);

	/**
	 * Returns status message.
	 */
	public String statusMessage();

	/**
	 * Sets the status message.
	 */
	public Response statusMessage(String statusMessage);

	/**
	 * Checks if response succeeded. Any status code 2xx or 3xx is considered
	 * valid.
	 */
	public default boolean succeeded() {
		return statusCode() >= 200 && statusCode() <= 399;
	}

}