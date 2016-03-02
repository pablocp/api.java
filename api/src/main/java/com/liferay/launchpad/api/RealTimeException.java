package com.liferay.launchpad.api;

import com.liferay.launchpad.sdk.io.SocketIOException;

/**
 * @author cirocosta
 */
public class RealTimeException implements SocketIOException {

	private Object msg;

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
}
