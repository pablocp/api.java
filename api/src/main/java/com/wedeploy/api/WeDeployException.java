package com.wedeploy.api;
public class WeDeployException extends RuntimeException {

	public WeDeployException(String message) {
		super(message);
	}

	public WeDeployException(String message, Throwable cause) {
		super(message, cause);
	}

}