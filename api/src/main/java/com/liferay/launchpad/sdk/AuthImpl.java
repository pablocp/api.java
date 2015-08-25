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
 * Auth implementation.
 */
public class AuthImpl implements Auth {

	public AuthImpl(String token) {
		this.token = token;
		this.username = null;
		this.password = null;
	}

	public AuthImpl(String username, String password) {
		this.token = null;
		this.username = username;
		this.password = password;
	}

	@Override
	public Map<String, Object> configuration() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, Object> data() {
		return data;
	}

	@Override
	public boolean hasPassword() {
		return password() != null;
	}

	@Override
	public CompletableFuture<Boolean> hasRole(String role) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasToken() {
		return token() != null;
	}

	@Override
	public boolean hasUsername() {
		return username() != null;
	}

	@Override
	public CompletableFuture<Boolean> isPermitted(String permission) {
		throw new UnsupportedOperationException();
	}

	public String password() {
		return password;
	}

	@Override
	public Set<String> permissions() {
		return permissions;
	}

	@Override
	public Set<String> roles() {
		return roles;
	}

	public String token() {
		return token;
	}

	public String username() {
		return username;
	}

	protected Map<String, Object> data;
	protected String password;
	protected Set<String> permissions;
	protected Set<String> roles;
	protected String token;
	protected String username;

}