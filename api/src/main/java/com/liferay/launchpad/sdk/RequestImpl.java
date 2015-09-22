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

import java.io.File;

import java.net.MalformedURLException;
import java.net.URL;

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
	public Request form(String name, byte[] data) {
		form.set(name, data);
		return null;
	}

	@Override
	public Request form(String name, File file) {
		form.set(name, file);
		return this;
	}

	@Override
	public Request form(String name, String value) {
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

		// TODO(igor): !!!workaround!!!

		if (url.startsWith("/")) {
			url = "http://localhost:8080" + url;
		}

		this.url = url;

		try {
			URL urlParsed = new URL(url);

			baseUrl =
				urlParsed.getProtocol() + "://" + urlParsed.getHost() + ":" +
					urlParsed.getPort();

			path = urlParsed.getPath();

			query = urlParsed.getQuery();
		}
		catch (MalformedURLException e) {
			throw new PodException("Invalid URL: " + url, e);
		}
	}

}