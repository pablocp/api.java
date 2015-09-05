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
 * HTTP request.
 */
public interface Request {

	/**
	 * Returns the base URL corresponding to the the HTTP request.
	 */
	public String baseUrl();

	/**
	 * Returns the body content.
	 */
	public String body();

	/**
	 * Sets the JSON body content.
	 */
	public Request body(Object body);

	/**
	 * Sets the body content.
	 */
	public Request body(String body);

	/**
	 * Sets the body content and common {@link ContentType content-type}.
	 * Just a shortcut call.
	 */
	public Request body(String body, ContentType contentType);

	/**
	 * Gets the content type header.
	 */
	public String contentType();

	/**
	 * Sets the content type header.
	 */
	public Request contentType(ContentType contentType);

	/**
	 * Returns the context that this request belongs to.
	 */
	public Context context();

	/**
	 * @return A map of all the cookies.
	 */
	Map<String, Cookie> cookies();

	/**
	 * Adds a cookie to the response.
	 */
	public Request cookie(Cookie cookie);

	/**
	 * Gets cookie value by name.
	 */
	public Cookie cookie(String name);

	/**
	 * Returns array of uploaded files. Returns <code>null</code> if nothing
	 * was uploaded.
	 */
	public FileUpload[] fileUploads();

	/**
	 * Gets header value by name.
	 */
	public String header(String name);

	/**
	 * Sets the first header name with the specified value.
	 */
	public Request header(String name, String value);

	/**
	 * Returns headers.
	 */
	public PodMultiMap<String> headers();

	/**
	 * Checks if the request contains a content type header with the
	 * specified value.
	 */
	public boolean isContentType(ContentType contentType);

	/**
	 * Returns HTTP method, in uppercase.
	 */
	public String method();

	/**
	 * Sets method.
	 */
	public Request method(String method);

	/**
	 * Delegates this request to the next handler.
	 */
	public void next();

	/**
	 * Gets parameter value by name.
	 */
	public String param(String name);

	/**
	 * Sets parameter value.
	 */
	public Request param(String name, String value);

	/**
	 * Returns request parameters.
	 */
	public PodMultiMap<String> params();

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
	 * Returns action path of the URI. Returned path does not contain the query
	 * part. Returns <code>"/"</code> for the root.
	 */
	public String path();

	/**
	 * Returns the query string of the URL.
	 */
	public String query();

	/**
	 * Returns associated response of this request.
	 */
	public Response response();

	/**
	 * Associates response to this request.
	 */
	public void response(Response response);

	/**
	 * Returns the absolute URL corresponding to the the HTTP request.
	 */
	public String url();

	/**
	 * Returns parsed {@link #params() request params}
	 * merged with {@link #parse parsed body}, if it is an object.
	 */
	public default Map<String, Object> values() {
		return values(null);
	}

	/**
	 * Returns parsed {@link #params() request params}
	 * merged with {@link #parse parsed body}, if it is an object.
	 */
	public <T> T values(Class<T> type);

}