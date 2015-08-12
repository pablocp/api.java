package com.liferay.launchpad.sdk;

/**
 * Error 408 errors.
 */
public class Error408<T> extends ErrorBase<T, Error408<T>> {

	public static final String[][] VALS = {
		{	//0
			"requestTimeout",
			"The server did not produce a response within the time that the server was prepared to wait."
		}
	};

	public Error408(ErrorData<T> errorData, String message) {
		super(errorData, 408, message, "Request Timeout");
	}

	public Error408<T> requestTimeout() {
		return error(0);
	}

	public Error408<T> requestTimeout(String message) {
		return error(0, message);
	}

	@Override
	protected String[] vals(int index) {
		return VALS[index];
	}

}