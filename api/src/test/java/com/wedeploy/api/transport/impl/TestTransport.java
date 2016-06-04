package com.wedeploy.api.transport.impl;

import com.wedeploy.api.sdk.Request;
import com.wedeploy.api.sdk.Response;
import com.wedeploy.api.sdk.ResponseImpl;
public class TestTransport extends BlockingTransport {

	public Request getRequest() {
		return request;
	}

	public Response getResponse() {
		return response;
	}

	@Override
	public Response send(Request request) {
		this.request = request;
		this.response = new ResponseImpl(request);

		response.header("Content-Type", "application/json");
		response.status(200);
		response.body(request.body());

		return response;
	}

	private Request request;
	private Response response;

}