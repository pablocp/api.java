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

package com.liferay.launchpad.sdk;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * Auth.
 */
public interface Auth {

	/**
	 * Creates authentication instance.
	 */
	public static Auth create(String token) {
		return new AuthImpl(token);
	}

	/**
	 * Creates authentication instance.
	 */
	public static Auth create(String username, String password) {
		return new AuthImpl(username, password);
	}

	/**
	 * Returns the authentication data.
	 */
	public Map<String, Object> data();

	/**
	 * Returns if the authentication password is set.
	 */
	public boolean hasPassword();

	/**
	 * Is the user has a role.
	 */
	public CompletableFuture<Boolean> hasRole(String role);

	/**
	 * Returns if the authentication token is set.
	 */
	public boolean hasToken();

	/**
	 * Returns if the authentication user name is set.
	 */
	public boolean hasUsername();

	/**
	 * Is the user authorised to a permission.
	 */
	public CompletableFuture<Boolean> isPermitted(String permission);

	/**
	 * Returns the authentication password.
	 */
	public String password();

	/**
	 * Returns the permissions.
	 */
	public Set<String> permissions();

	/**
	 * Returns the roles.
	 */
	public Set<String> roles();

	/**
	 * Returns the authentication token.
	 */
	public String token();

	/**
	 * Returns the authentication user name.
	 */
	public String username();

}