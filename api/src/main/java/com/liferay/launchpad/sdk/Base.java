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

import com.liferay.launchpad.serializer.LaunchpadParser;
import com.liferay.launchpad.serializer.LaunchpadSerializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
public abstract class Base<R> {

	public String body() {
		return body;
	}

	public R body(Object body) {
		setBodyObject(body);
		return (R)this;
	}

	public R body(String body) {
		setBody(body);
		return (R)this;
	}

	public R body(String body, ContentType contentType) {
		contentType(contentType);
		body(body);
		return (R)this;
	}

	public <T> List<T> bodyList(Class<T> componentType) {
		if (body == null) {
			return null;
		}

		String contentTypeValue = contentType();
		ContentType contentType = null;

		if (contentTypeValue == null) {
			contentType = ContentType.TEXT;
		}
		else {
			contentType = new ContentType(contentTypeValue);
		}

		return LaunchpadParser
			.get(contentType.contentType())
			.parseAsList(body, componentType);
	}

	public <T> T bodyValue() {
		if (body == null) {
			return null;
		}

		String contentTypeValue = contentType();
		ContentType contentType = null;

		if (contentTypeValue == null) {
			contentType = ContentType.TEXT;
		}
		else {
			contentType = new ContentType(contentTypeValue);
		}

		return LaunchpadParser
			.get(contentType.contentType())
			.parse(body);
	}

	public <T> T bodyValue(Class<T> type) {
		if (body == null) {
			return null;
		}

		String contentTypeValue = contentType();
		ContentType contentType = null;

		if (contentTypeValue == null) {
			contentType = ContentType.TEXT;
		}
		else {
			contentType = new ContentType(contentTypeValue);
		}

		return LaunchpadParser
			.get(contentType.contentType())
			.parse(body, type);
	}

	public String contentType() {
		return headers().get("Content-Type");
	}

	public R contentType(ContentType contentType) {
		header("Content-Type", contentType.toString());
		return (R)this;
	}

	public Context context() {
		throw new UnsupportedOperationException();
	}

	public R cookie(Cookie cookie) {
		cookies.put(cookie.name(), cookie);
		return (R)this;
	}

	/**
	 * Gets cookie value by name.
	 */
	public Cookie cookie(String name) {
		return this.cookies().get(name);
	}

	public Map<String, Cookie> cookies() {
		return cookies;
	}

	public void end() {
	}

	/**
	 * Gets header value by name.
	 */
	public String header(String name) {
		return this.headers().get(name);
	}

	public R header(String name, String value) {
		headers.set(name, value);
		return (R)this;
	}

	public PodMultiMap<String> headers() {
		return headers;
	}

	public R headers(PodMultiMap<String> headers) {
		this.headers = headers;
		return (R)this;
	}

	public boolean isContentType(ContentType contentType) {
		return contentType.isSame(contentType());
	}

	/**
	 * Returns <code>true</code> if content type is either form url encoded
	 * or multi-part form.
	 */
	public boolean isFormContentType() {
		return (isContentType(ContentType.FORM_URLENCODED) ||
			isContentType(ContentType.MULTIPART_FORM));
	}

	protected void setBody(String value) {
		this.body = value;
	}

	protected R setBodyObject(Object body) {
		if (body == null) {
			setBody(null);

			return (R)this;
		}

		if (body instanceof String) {
			setBody((String)body);
			return (R)this;
		}

		String bodyJson = LaunchpadSerializer
			.get(ContentType.JSON.contentType())
			.serialize(body);

		contentType(ContentType.JSON);

		setBody(bodyJson);

		return (R)this;
	}

	protected Map<String, Cookie> cookies = new HashMap<>();
	protected PodMultiMap<String> headers = PodMultiMap.newMultiMap();

	private String body;

}