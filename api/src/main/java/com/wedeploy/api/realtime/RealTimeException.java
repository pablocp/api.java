package com.wedeploy.api.realtime;

import com.wedeploy.api.sdk.realtime.SocketIOException;

/**
 * @author cirocosta
 */
public class RealTimeException implements SocketIOException {

	/**
	 * Creates a new specialized {@link Exception} that is responsible for
	 * holding a generic {@link Object} error object.
	 *
	 * @param message error message contained in an {@link Object}
	 */
	public RealTimeException(Object message) {
		this.msg = message;
	}

	/**
	 * @return the generic {@link Object} that contains the error data.
	 */
	@Override
	public Object getObjectMessage() {
		return this.msg;
	}

	private Object msg;

}