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
 * DataSnapshot.
 */
public interface DataSnapshot {

	/**
	 * Navigates in key path, e.g. "/foo" -> data.child("1") -> /foo/1.
	 */
	public DataSnapshot child(String... names);

	/**
	 * Navigates to the document path,
	 * e.g. "/foo/1/a" -> $data.document() -> /foo/1.
	 */
	public DataSnapshot document();

	/**
	 * Checks if the data exists.
	 */
	public boolean exists();

	/**
	 * Navigates to parent key path, e.g. "/foo/1/child" -> data.parent() -> /foo/1.
	 */
	public DataSnapshot parent();

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
	 * Checks if the data does not exists in the collection.
	 */
	public boolean unique();

	/**
	 * Parses the body depending on content-type. If content-type is NOT set,
	 * it will use assume the "plain/text" content type.
	 * Returns {@link Request#bodyValue()} if data not committed,
	 * {@link Response#bodyValue()} otherwise.
	 * If body is not set, returns <code>null</code>.
	 * If body can not be parsed, throws an Exception.
	 */
	public <T> T value();

	/**
	 * Parses the body depending on content-type into the target type.
	 * @see #value()
	 */
	public <T> T value(Class<T> type);

}