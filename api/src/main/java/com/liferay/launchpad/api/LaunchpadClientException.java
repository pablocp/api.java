package com.liferay.launchpad.api;
public class LaunchpadClientException extends RuntimeException {

	public LaunchpadClientException(String message) {
		super(message);
	}

	public LaunchpadClientException(String message, Throwable cause) {
		super(message, cause);
	}

}