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

package com.wedeploy.api.sdk;

/**
 * Data context.
 */
public interface Data {

	/**
	 * Commits the data operation wrapped by this request handler.
	 * The response is set, but not ended. You must end the response in the
	 * handler as you would naturally.
	 * Returns <code>true</code> if operation executed successfully,
	 * <code>false</code> otherwise.
	 */
	public boolean commit();

	/**
	 * Commits the data operation using the given value as request body.
	 * @see #commit()
	 */
	public boolean commit(Object value);

	/**
	 * Creates a data snapshot.
	 */
	public DataSnapshot snapshot();

	/**
	 * Retrieves a snapshot of the existing data.
	 */
	public <T> T storedValue();

	/**
	 * Retrieves a snapshot of the existing data and parses into the target type.
	 * @see #storedValue()
	 */
	public <T> T storedValue(Class<T> type);

	/**
	 * Parses the body depending on content-type. If content-type is NOT set,
	 * it will use assume the "plain/text" content type.
	 * Returns {@link Request#bodyValue()} if data not committed,
	 * {@link Response#bodyValue()} otherwise.
	 * If body is not set, returns <code>null</code>.
	 * If body can not be parsed, throws an Exception.
	 * @see #commit()
	 */
	public <T> T value();

	/**
	 * Parses the body depending on content-type into the target type.
	 * @see #value()
	 */
	public <T> T value(Class<T> type);

}