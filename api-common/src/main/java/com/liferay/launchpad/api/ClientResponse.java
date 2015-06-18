package com.liferay.launchpad.api;

public class ClientResponse extends ClientMessage<ClientResponse> {

	public ClientResponse(ClientRequest clientRequest) {
		this.clientRequest = clientRequest;
	}

	/**
	 * Returns request that created this response.
	 */
	public ClientRequest request() {
		return this.clientRequest;
	}

	/**
	 * Returns the status code.
	 */
	public int statusCode() {
		return statusCode;
	}

	/**
	 * Sets the status code.
	 */
	public ClientResponse statusCode(int statusCode) {
		this.statusCode = statusCode;
		return this;
	}

	protected final ClientRequest clientRequest;
	protected int statusCode;

}