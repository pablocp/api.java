package com.liferay.launchpad.api;

/**
 * Transport implementations defines how the data will be actually transfered.
 * Transport instances are stateless.
 */
public interface Transport {

	/**
	 * Sends client request and returns client response.
	 */
	ClientResponse send(ClientRequest clientRequest);

}