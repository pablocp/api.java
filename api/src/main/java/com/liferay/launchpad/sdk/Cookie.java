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
 * Cookie object. Simple cookie data holder, cookie header parser and generator.
 */
public class Cookie {

	/**
	 * Create a new cookie.
	 * @param cookie The encoded string of the cookie.
	 * @return The cookie.
	 */
	public static Cookie cookie(String cookie) {
		return new Cookie(cookie);
	}

	/**
	 * Create a new cookie.
	 * @param name The name of the cookie.
	 * @param value The cookie value.
	 * @return The cookie.
	 */
	public static Cookie cookie(String name, String value) {
		return new Cookie(name, value);
	}

	/**
	 * Returns the comment describing the purpose of this cookie, or
	 * <code>null</code> if the cookie has no comment.
	 */
	public String comment() {
		return comment;
	}

	/**
	 * Specifies a comment that describes a cookie's purpose.
	 * The comment is useful if the browser presents the cookie
	 * to the user.
	 */
	public Cookie comment(String comment) {
		this.comment = comment;
		return this;
	}

	/**
	 * Returns the domain name set for this cookie. The form of
	 * the domain name is set by RFC 2109.
	 */
	public String domain() {
		return domain.toLowerCase();
	}

	/**
	 * Specifies the domain within which this cookie should be presented.
	 * <p>
	 * The form of the domain name is specified by RFC 2109. A domain
	 * name begins with a dot (<code>.foo.com</code>) and means that
	 * the cookie is visible to servers in a specified Domain Name System
	 * (DNS) zone (for example, <code>www.foo.com</code>, but not
	 * <code>a.b.foo.com</code>). By default, cookies are only returned
	 * to the server that sent them.
	 */
	public Cookie domain(String domain) {
		this.domain = domain;
		return this;
	}

	/**
	 * Encode the cookie to a string. This is what is used in the Set-Cookie header.
	 */
	public String encode() {
		StringBuilder cookie = new StringBuilder();

		cookie.append(name).append('=').append(value);

		if (maxAge != null) {
			cookie.append("; Max-Age=").append(maxAge);
		}

		if (expires != null) {
			cookie.append("; Expires=").append(expires);
		}

		if (comment != null) {
			cookie.append("; Comment=").append(comment);
		}

		if (domain != null) {
			cookie.append("; Domain=").append(domain);
		}

		if (path != null) {
			cookie.append("; Path=").append(path);
		}

		if (secure == true) {
			cookie.append("; Secure");
		}

		if (version != null) {
			cookie.append("; Version=").append(version);
		}

		if (httpOnly) {
			cookie.append("; HttpOnly");
		}

		return cookie.toString();
	}

	/**
	 * Gets expires date.
	 */
	public String expires() {
		return expires;
	}

	/**
	 * Sets expires date. Indicates when the cookie should no longer be sent to
	 * the server and therefore may be deleted by the browser.
	 */
	public Cookie expires(String expires) {
		this.expires = expires;
		return this;
	}

	/**
	 * If this cookie is HTTP only.
	 */
	public boolean httpOnly() {
		return httpOnly;
	}

	/**
	 * Determines if this cookie is HTTP only.
	 * If set to true, this cookie cannot be accessed by a client
	 * side script. However, this works only if the browser supports it.
	 * For for information, please look
	 * <a href="http://www.owasp.org/index.php/HTTPOnly">here</a>.
	 *
	 * @param httpOnly True if the cookie is HTTP only, otherwise false.
	 */
	public Cookie httpOnly(boolean httpOnly) {
		this.httpOnly = httpOnly;
		return this;
	}

	/**
	 * Returns the maximum age of the cookie, specified in seconds,
	 * By default, <code>-1</code> indicating the cookie will persist
	 * until browser shutdown.
	 */
	public Long maxAge() {
		return maxAge;
	}

	/**
	 * Sets the maximum age of the cookie in seconds.
	 * <p>
	 * A positive value indicates that the cookie will expire
	 * after that many seconds have passed. Note that the value is
	 * the <i>maximum</i> age when the cookie will expire, not the cookie's
	 * current age.
	 * <p>
	 * A negative value means
	 * that the cookie is not stored persistently and will be deleted
	 * when the Web browser exits. A zero value causes the cookie
	 * to be deleted.
	 */
	public Cookie maxAge(Long maxAge) {
		this.maxAge = maxAge;
		return this;
	}

	/**
	 * Returns the name of the cookie. The name cannot be changed after
	 * creation.
	 */
	public String name() {
		return name;
	}

	/**
	 * Sets the cookie name and checks for validity.
	 */
	public Cookie name(String name) {
		if (name.contains(";") || name.contains(",") || name.startsWith("$")) {
			throw new IllegalArgumentException("Invalid cookie name:" + name);
		}

		for (int n = 0; n < name.length(); n++) {
			char c = name.charAt(n);

			if (c <= 0x20 || c >= 0x7f) {
				throw new IllegalArgumentException(
					"Invalid cookie name:" + name);
			}
		}

		this.name = name;
		return this;
	}

	/**
	 * Returns the path on the server
	 * to which the browser returns this cookie. The
	 * cookie is visible to all subpaths on the server.
	 */
	public String path() {
		return path;
	}

	/**
	 * Specifies a path for the cookie
	 * to which the client should return the cookie.
	 * <p>
	 * The cookie is visible to all the pages in the directory
	 * you specify, and all the pages in that directory's subdirectories.
	 * A cookie's path must include the servlet that set the cookie,
	 * for example, <i>/catalog</i>, which makes the cookie
	 * visible to all directories on the server under <i>/catalog</i>.
	 *
	 * <p>Consult RFC 2109 (available on the Internet) for more
	 * information on setting path names for cookies.
	 */
	public Cookie path(String path) {
		this.path = path;
		return this;
	}

	/**
	 * Returns <code>true</code> if the browser is sending cookies
	 * only over a secure protocol, or <code>false</code> if the
	 * browser can send cookies using any protocol.
	 */
	public boolean secure() {
		return secure;
	}

	/**
	 * Indicates to the browser whether the cookie should only be sent
	 * using a secure protocol, such as HTTPS or SSL.
	 */
	public Cookie secure(boolean secure) {
		this.secure = secure;
		return this;
	}

	/**
	 * Returns the value of the cookie.
	 */
	public String value() {
		return value;
	}

	/**
	 * Assigns a new value to a cookie after the cookie is created.
	 * If you use a binary value, you may want to use BASE64 encoding.
	 */
	public Cookie value(String value) {
		this.value = value;
		return this;
	}

	/**
	 * Returns the version of the protocol this cookie complies
	 * with. Version 1 complies with RFC 2109,
	 * and version 0 complies with the original
	 * cookie specification drafted by Netscape. Cookies provided
	 * by a browser use and identify the browser's cookie version.
	 */
	public Integer version() {
		return version;
	}

	/**
	 * Sets the version of the cookie protocol this cookie complies
	 * with. Version 0 complies with the original Netscape cookie
	 * specification. Version 1 complies with RFC 2109.
	 */
	public Cookie version(Integer version) {
		this.version = version;
		return this;
	}

	/**
	 * Parses cookie data from given user-agent string.
	 */
	protected Cookie(String cookie) {
		int from = 0;
		int ndx = 0;

		while (ndx < cookie.length()) {
			ndx = cookie.indexOf(';', from);

			if (ndx == -1) {
				ndx = cookie.length();
			}

			int ndx2 = cookie.indexOf('=', from);

			String name = null;
			String value = null;

			if (ndx2 != -1 && ndx2 < ndx) {
				name = cookie.substring(from, ndx2).trim();
				value = cookie.substring(ndx2 + 1, ndx).trim();
			} else {
				name = cookie.substring(from, ndx).trim();
				value = null;
			}

			if (name.equalsIgnoreCase("Max-Age")) {
				try {
					if (value != null) {
						maxAge(Long.parseLong(value));
					}
				}
				catch (NumberFormatException nfe) {
					throw new PodException("Invalid cookie Max-Age: " + value);
				}
			} else if (name.equalsIgnoreCase("Comment")) {
				comment(value);
			} else if (name.equalsIgnoreCase("Domain")) {
				domain(value);
			} else if (name.equalsIgnoreCase("Path")) {
				path(value);
			} else if (name.equalsIgnoreCase("Secure")) {
				secure(true);
			} else if (name.equalsIgnoreCase("Version")) {
				try {
					if (value != null) {
						version(Integer.parseInt(value));
					}
				}
				catch (NumberFormatException nfe) {
					throw new PodException("Invalid cookie Version: " + value);
				}
			} else if (name.equalsIgnoreCase("HttpOnly")) {
				httpOnly(true);
			} else if (name.equalsIgnoreCase("Expires")) {
				expires(value);
			} else {
				name(name);
				value(value);
			}

			from = ndx + 1;
		}
	}

	/**
	 * Creates cookie with specified name and value.
	 * <p>
	 * The name must conform to RFC 2109. That means it can contain
	 * only ASCII alphanumeric characters and cannot contain commas,
	 * semicolons, or white space or begin with a $ character.
	 * <p>
	 * The value can be anything the server chooses to send.
	 */
	protected Cookie(String name, String value) {
		name(name);
		value(value);
	}

	private String comment;
	private String domain;
	private String expires;
	private boolean httpOnly;
	private Long maxAge;
	private String name;
	private String path;
	private boolean secure;
	private String value;
	private Integer version;

}