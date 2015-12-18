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

package com.liferay.launchpad.sdk.io;

/**
 * SocketIO server.
 */
public interface SocketIO {

	/**
	 * Expose main namespace (/) emit.
	 */
	public SocketNamespace emit(String event, Object... args);

	/**
	 * Looks up a namespace.
	 */
	public SocketNamespace of(String name);

	public SocketNamespace of(String name, SocketListener fn);

	/**
	 * Expose main namespace (/) on.
	 */
	public SocketNamespace on(String event, SocketListener fn);

	/**
	 * Expose main namespace (/) to.
	 */
	public SocketNamespace to(String room, String event, Object... args);

	/**
	 * Expose main namespace (/) use.
	 */
	public SocketNamespace use(SocketMiddleware fn);

	/**
	 * Blacklist an event for the sockets in this server.
	 */
	public void blacklist(String event);

}
