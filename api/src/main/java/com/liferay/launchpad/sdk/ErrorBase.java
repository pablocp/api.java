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

abstract class ErrorBase<T, E extends ErrorBase> implements Error<T> {

	protected ErrorBase(
			ErrorData<T> errorData, int status, String message,
			String defaultMessage) {

		this.errorData = errorData;
		this.errorData.set(status, message, defaultMessage);
	}

	@Override
	public ErrorData<T> data() {
		return errorData;
	}

	@Override
	public final T into(T response) {
		return errorData.into(response);
	}

	@Override
	public final E error(String reason, String message) {
		errorData.add(reason, message);
		return (E)this;
	}

	@Override
	public final E header(String name, String value) {
		errorData.header(name, value);
		return (E)this;
	}

	protected E error(int index, String message) {
		if (message == null) {
			message = vals(index)[1];
		}

		errorData.add(vals(index)[0], message);

		return (E)this;
	}

	protected abstract String[] vals(int index);

	private final ErrorData<T> errorData;

}