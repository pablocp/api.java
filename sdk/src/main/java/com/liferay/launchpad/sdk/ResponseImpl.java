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
 */
public class ResponseImpl extends Base<Response> implements Response {

	public ResponseImpl(Request request) {
		this.request = request;
		request.response(this);
	}

	@Override
	public boolean isCommitted() {
		return (body != null);
	}

	@Override
	public Response pipe(Response response) {
		PodMultiMap headers = this.headers();

		headers.forEach(
			entry -> response.header(
				entry.getKey(), ValuesUtil.toString(entry.getValue())));

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
	public int statusCode() {
		return statusCode;
	}

	@Override
	public Response statusCode(int statusCode) {
		this.statusCode = statusCode;
		return this;
	}

	@Override
	public String statusMessage() {
		return statusMessage;
	}

	@Override
	public Response statusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
		return this;
	}

	protected Request request;
	protected int statusCode;
	protected String statusMessage;

}