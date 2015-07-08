package com.liferay.launchpad.api;

import com.liferay.launchpad.sdk.Request;
import com.liferay.launchpad.sdk.Response;
import com.liferay.launchpad.sdk.ResponseImpl;

public class TestTransport extends BlockingTransport {

	private Request request;
	private Response response;

	public Request getRequest() {
		return request;
	}

	public Response getResponse() {
		return response;
	}

	@Override
	protected Response sendBlockingRequest(Request request) {
		this.request = request;
		this.response = new ResponseImpl(request);

		response.statusCode(200);
		return response;
	}
}