package com.liferay.launchpad.api;

import com.liferay.launchpad.sdk.Response;

/**
 * Exception used to break the completable future flow in case when response
 * returns error.
 */
public class ResponseErrorException extends RuntimeException {

	public ResponseErrorException(Response response) {
		super("Response returned an error " + response.statusCode() + ": " +
			response.statusMessage());

		this.response = response;
	}

	public Response getResponse() {
		return response;
	}

	private final Response response;

}