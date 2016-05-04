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

import java.net.URI;
import java.net.URISyntaxException;

/**
 * {@link Request} implementation.
 */
public class RequestImpl extends Base<Request> implements Request {

	public RequestImpl(String url) {
		this.url(url);
	}

	@Override
	public Auth auth() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String baseUrl() {
		return baseUrl;
	}

	@Override
	public FileUpload[] fileUploads() {
		return fileUploads;
	}

	@Override
	public Object form(String name) {
		return form.get(name);
	}

	@Override
	public Request form(String name, Object value) {
		form.set(name, value);
		return this;
	}

	@Override
	public PodMultiMap<Object> forms() {
		return form;
	}

	/**
	 * Sets the file uploads.
	 */
	Request fileUploads(FileUpload... fileUploads) {
		this.fileUploads = fileUploads;
		return this;
	}

	@Override
	public String method() {
		return method;
	}

	@Override
	public Request method(String method) {
		this.method = method;
		return this;
	}

	@Override
	public void next() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void next(OnResponseEndCallback onResponseEnd) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Gets the parameter value.
	 */
	public String param(String name) {
		return this.params().get(name);
	}

	@Override
	public Request param(String name, String value) {
		params.set(name, value);
		return this;
	}

	@Override
	public PodMultiMap<String> params() {
		return params;
	}

	@Override
	public String path() {
		return path;
	}

	@Override
	public String query() {
		return query;
	}

	@Override
	public Response response() {
		return response;
	}

	@Override
	public void response(Response response) {
		this.response = response;
	}

	@Override
	public Session session() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String url() {
		return url;
	}

	@Override
	public PodMultiMap<Object> values() {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T values(Class<T> type) {
		throw new UnsupportedOperationException();
	}

	protected String baseUrl;
	protected FileUpload[] fileUploads;
	protected PodMultiMap<Object> form = PodMultiMap.newMultiMap();
	protected String method;
	protected PodMultiMap<String> params = PodMultiMap.newMultiMap();
	protected String path;
	protected String query;
	protected Response response;
	protected String url;

	private void url(String url) {
		this.url = url;

		// clear attributes

		this.baseUrl = null;
		this.path = null;
		this.query = null;

		if (url == null) {
			return;
		}

		try {
			if (url.charAt(0) == '/') {
				int separator = url.indexOf('?');

				if (separator == -1) {
					path = url;
				}
				else {
					path = url.substring(0, separator);
					query = url.substring(separator+1, url.length());
				}
			}
			else {
				if (!url.contains("://")) {
					url = "http://" + url;
				}

				URI parsedUrl = new URI(url);

				if (parsedUrl.getHost() != null) {
					baseUrl = parsedUrl.getScheme() + "://";

					baseUrl += parsedUrl.getHost();

					if (parsedUrl.getPort() != -1) {
						baseUrl += ":" + parsedUrl.getPort();
					}

					path = parsedUrl.getRawPath();
					query = parsedUrl.getRawQuery();
				}
			}
		}
		catch (URISyntaxException urise) {
			throw new PodException("Invalid URL: " + url, urise);
		}
	}

}