package com.liferay.launchpad.api;

import com.liferay.launchpad.sdk.Request;
import com.liferay.launchpad.sdk.RequestImpl;
import com.liferay.launchpad.sdk.Response;
import com.liferay.launchpad.sdk.ResponseImpl;
public class TestTransport extends BlockingTransport {

	public Request getRequest() {
		return request;
	}

	public Response getResponse() {
		return response;
	}

	@Override
	protected ResponseImpl sendBlockingRequest(RequestImpl request) {
		this.request = request;
		this.response = new ResponseImpl(request);

		response.statusCode(200);
		response.body(request.body());

		return (ResponseImpl)response;
	}

	private Request request;
	private Response response;

}