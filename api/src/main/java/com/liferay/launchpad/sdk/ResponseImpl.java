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

/**
 * Default {@link Response} implementation.
 */
public class ResponseImpl extends Base<Response> implements Response {

	public ResponseImpl(Request request) {
		this.request = request;
		request.response(this);
	}

	@Override
	public boolean isCommitted() {
		return (body() != null);
	}

	@Override
	public Response pipe(Response response) {
		PodMultiMap<String> headers = this.headers();

		headers.forEach(
			entry -> response.header(entry.getKey(), entry.getValue()));

		response
			.status(this.statusCode(), this.statusMessage())
			.body(this.body())
			.end();

		return this;
	}

	@Override
	public Request request() {
		return request;
	}

	@Override
	public Response status(int statusCode, String statusMessage) {
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
		return this;
	}

	@Override
	public int statusCode() {
		return statusCode;
	}

	@Override
	public String statusMessage() {
		return statusMessage;
	}

	protected Request request;
	protected int statusCode;
	protected String statusMessage;

}