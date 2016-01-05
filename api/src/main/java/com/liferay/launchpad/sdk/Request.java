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
	 * Returns the auth.
	 */
	public Auth auth();

	/**
	 * Returns the base URL corresponding to the the HTTP request.
	 */
	public String baseUrl();

	/**
	 * Returns the body content as a string.
	 */
	public String body();

	/**
	 * Sets the raw body content.
	 */
	public Request body(byte[] body);

	/**
	 * Sets the body content, assuming JSON content type.
	 */
	public Request body(Object body);

	/**
	 * Sets the body content by passing the string value.
	 */
	public Request body(String body);

	/**
	 * Sets the raw body content and the {@link ContentType content-type}.
	 * This is just a shortcut method.
	 */
	public Request body(String body, ContentType contentType);

	/**
	 * Returns the raw body content.
	 */
	public byte[] bodyBytes();

	/**
	 * Returns parsed {@link #body() body content} as a List.
	 */
	public <T> List<T> bodyList(Class<T> componentType);

	/**
	 * Parses the body depending on content-type. If content-type is NOT set,
	 * it will use assume the "plain/text" content type.
	 * Returns parsed {@link #body() body content}.
	 * If body is not set, returns <code>null</code>.
	 * If body can not be parsed, throws an Exception.
	 */
	public <T> T bodyValue();

	/**
	 * Parses the body depending on content-type into the target type.
	 * @see #bodyValue()
	 */
	public <T> T bodyValue(Class<T> type);

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
	 * Returns the form parameter.
	 */
	public Object form(String name);

	/**
	 * Sets the form parameter.
	 */
	public Request form(String name, Object value);

	/**
	 * Returns form parameters.
	 */
	public PodMultiMap<Object> forms();

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
	 * Delegates this request to the next handler and executes
	 * the runnable on the response end.
	 */
	public void next(OnResponseEndCallback onResponseEnd);

	/**
	 * Gets parameter value by name.
	 */
	public String param(String name);

	/**
	 * Sets parameter value.
	 */
	public Request param(String name, String value);

	/**
	 * Returns map of string request parameters. They consist of:
	 * <ol>
	 *     <li>query parameters</li>
	 *     <li>form parameters</li>
	 *     <li>parameters from api.json (serialized to string)</li>
	 *     <li>path parameters</li>
	 * </ol>
	 * <p>
	 * Parameters are accumulated in given order.
	 */
	public PodMultiMap<String> params();

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
	 * Returns current session.
	 */
	public Session session();

	/**
	 * Returns the absolute URL corresponding to the the HTTP request.
	 */
	public String url();

	/**
	 * Merges all the inputs in best possible way. The following is merged
	 * in given order:
	 * <ol>
	 *     <li>{@link #params() parameters} - parsed to a JSON, if possible</li>
	 *     <li>{@link #bodyValue()} parsed body}</li>
	 * </ol>
	 * Values with the same names and different sources are accumulated in
	 * multi map. Always returns an object, never a <code>null</code>.
	 * When content type is one of the form content types, body is ignored.
	 */
	public PodMultiMap<Object> values();

	/**
	 * Returns merged values to a certain type.
	 * @see #values()
	 */
	public <T> T values(Class<T> type);

}