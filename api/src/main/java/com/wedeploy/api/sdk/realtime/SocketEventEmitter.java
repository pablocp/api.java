/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation; either version
 * 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */

package com.wedeploy.api.sdk.realtime;

import java.util.List;

/**
 * SocketIO emitter.
 */
public interface SocketEventEmitter {

	/**
	 * Emits `event` with the given args.
	 */
	public SocketEventEmitter emit(String event, Object... args);

	/**
	 * Checks if this emitter has `event` handlers.
	 */
	public boolean hasListeners(String event);

	/**
	 * Returns an array of listeners for `event`.
	 */
	public List<SocketListener> listeners(String event);

	/**
	 * Remove all registered listeners.
	 */
	public SocketEventEmitter off();

	/**
	 * Remove the given callback for `event`.
	 */
	public SocketEventEmitter off(String event);

	/**
	 * Remove the given callback for `event`.
	 */
	public SocketEventEmitter off(String event, SocketListener fn);

	/**
	 * Listen on the given `event` with listener `fn`.
	 */
	public SocketEventEmitter on(String event, SocketListener fn);

	/**
	 * Adds an `event` listener that will be invoked a single
	 * time then automatically removed.
	 */
	public SocketEventEmitter once(String event, SocketListener fn);

}