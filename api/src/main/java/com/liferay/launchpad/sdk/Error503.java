package com.liferay.launchpad.sdk;

/**
 * Error 503 errors.
 */
public class Error503<T> extends ErrorBase<T, Error503<T>> {

	public static final String[][] VALS = {
		{
			//0
			"serviceUnavailable", "The server is currently unavailable (because it is overloaded or down for maintenance)."
		}
	};

	public Error503(ErrorData<T> errorData, String message) {
		super(errorData, 503, message, "Service unavailable");
	}

	public Error503<T> serviceUnavailable() {
		return serviceUnavailable(null);
	}

	public Error503<T> serviceUnavailable(String message) {
		return error(0, message);
	}

	@Override
	protected String[] vals(int index) {
		return VALS[index];
	}

}