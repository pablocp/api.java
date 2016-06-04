package com.wedeploy.api.sdk.realtime;

/**
 * An {@link Exception} that contains a generic {@link Object}
 * message as opposed to the traditional {@link String}.
 *
 * <p>
 *    Used internally to deliver non-string error messages with the
 *    {@code ERROR} event.
 * </p>
 */
public interface SocketIOException {

	/**
	 * @return the generic {@link Object} that contains the error data.
	 */
	public Object getObjectMessage();

}