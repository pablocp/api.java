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

/**
 * Common content types.
 */
public class ContentType {

	public static final ContentType TEXT = new ContentType(
		"text/plain", "UTF-8");
	public static final ContentType HTML = new ContentType(
		"text/html", "UTF-8");
	public static final ContentType JSON = new ContentType(
		"application/json", "UTF-8");
	public static final ContentType FORM_URLENCODED = new ContentType(
		"application/x-www-form-urlencoded", "UTF-8");
	public static final ContentType MULTIPART_FORM = new ContentType(
		"multipart/form-data", "UTF-8");

	public ContentType(String contentType) {
		ContentTypeHeaderResolver cthr = new ContentTypeHeaderResolver(
			contentType);

		this.contentType = cthr.getMimeType();
		this.charset = cthr.getEncoding();

		if (charset == null) {
			completeContentType = this.contentType;
		}
		else {
			completeContentType = this.contentType + "; charset=" + charset;
		}
	}

	public ContentType(String contentType, String charset) {
		this.contentType = contentType;
		this.charset = charset;

		if (charset == null) {
			completeContentType = contentType;
		}
		else {
			completeContentType = contentType + "; charset=" + charset;
		}
	}

	/**
	 * Returns content-type value.
	 */
	public String contentType() {
		return contentType;
	}

	/**
	 * Returns content-type charset.
	 */
	public String charset() {return charset; }

	@Override
	public String toString() {
		return completeContentType;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		ContentType that = (ContentType)o;

		return completeContentType.equals(that.completeContentType);
	}

	/**
	 * Returns <code>true</code> if content-type is equals, ignoring the
	 * charset.
	 */
	public boolean isSame(String type) {
		if (type == null) {
			return false;
		}

		int ndx = type.indexOf(';');

		if (ndx != -1) {
			type = type.substring(0, ndx);
		}

		return type.equals(this.contentType);
	}

	@Override
	public int hashCode() {
		return completeContentType.hashCode();
	}

	private final String completeContentType;
	private final String contentType;
	private final String charset;

}