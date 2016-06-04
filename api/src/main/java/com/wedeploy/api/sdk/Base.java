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

import com.wedeploy.api.serializer.Parser;
import com.wedeploy.api.serializer.Serializer;

import java.io.UnsupportedEncodingException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
public abstract class Base<R> {

	public String body() {
		return getBodyAsString();
	}

	public R body(byte[] body) {
		setBody(body);
		return (R)this;
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

	public byte[] bodyBytes() {
		return body;
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

		return Parser
			.get(contentType)
			.parseAsList(getBodyAsString(), componentType);
	}

	public <K, V> Map<K, V> bodyMap(Class<K> keyType, Class<V> valueType) {
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

		return Parser
			.get(contentType)
			.parseAsMap(getBodyAsString(), keyType, valueType);
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

		return Parser
			.get(contentType)
			.parse(getBodyAsString());
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

		return Parser
			.get(contentType)
			.parse(getBodyAsString(), type);
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

	public MultiMap<String> headers() {
		return headers;
	}

	public boolean isContentType(ContentType contentType) {
		return contentType.isSame(contentType());
	}

	protected String getBodyAsString() {
		if (body == null) {
			return null;
		}

		try {
			return new String(body, BODY_ENCODING);
		}
		catch (UnsupportedEncodingException uee) {
			return null;
		}
	}

	protected void setBody(byte[] value) {
		this.body = value;
	}

	protected void setBody(String value) {
		if (value == null) {
			setBody((byte[])null);
		}
		else {
			try {
				setBody(value.getBytes(BODY_ENCODING));
			}
			catch (UnsupportedEncodingException uee) {
				setBody((byte[])null);
			}
		}
	}

	protected R setBodyObject(Object body) {
		if (body == null) {
			setBody((byte[])null);

			return (R)this;
		}

		if (body instanceof String) {
			setBody((String)body);
			return (R)this;
		}

		String bodyJson = Serializer
			.get(ContentType.JSON)
			.serialize(body);

		contentType(ContentType.JSON);

		setBody(bodyJson);

		return (R)this;
	}

	protected static String BODY_ENCODING = "UTF-8";

	protected Map<String, Cookie> cookies = new HashMap<>();
	protected MultiMap<String> headers = MultiMap.newMultiMap();

	private byte[] body;

}