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

package com.liferay.launchpad.security;

/**
 * @author Lais Andrade
 */
public enum HashAlgorithm {

	MD5("md5"), SHA1("sha-1"), SHA256("sha-256"), SHA384("sha-384"),
	SHA512("sha-512"), BCRYPT("bcrypt"), PBKDF2("pbkdf2");

	public String getAlgorithm() {
		return algorithm;
	}

	private HashAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	private final String algorithm;

}