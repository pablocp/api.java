package com.wedeploy.api.sdk;

/**
 * Error 409 errors.
 */
public class Error409<T> extends ErrorBase<T, Error409<T>> {

	public static final String[][] VALS = {
		{
			//0
			"conflict",
			"Indicates that the request could not be processed because of conflict in the request, such as an edit conflict between multiple simultaneous updates."
		}
	};

	public Error409(ErrorData<T> errorData, String message) {
		super(errorData, 409, message, "Conflict");
	}

	public Error409<T> conflict() {
		return conflict(null);
	}

	public Error409<T> conflict(String message) {
		return error(0, message);
	}

	@Override
	protected String[] vals(int index) {
		return VALS[index];
	}

}