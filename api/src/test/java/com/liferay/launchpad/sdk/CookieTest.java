package com.liferay.launchpad.sdk;

import org.junit.Assert;
import org.junit.Test;
public class CookieTest {

	@Test
	public void testConstructor_withAllFields() {
		Cookie cookie = Cookie.cookie(
			"a=b;max-age=0; Comment=comment; domain = domain ;path=path;" +
			"expires=expires; HttpOnly; secure=anything; version=1");

		Assert.assertEquals("a", cookie.name());
		Assert.assertEquals("b", cookie.value());
		Assert.assertEquals("comment", cookie.comment());
		Assert.assertEquals("domain", cookie.domain());
		Assert.assertEquals("expires", cookie.expires());
		Assert.assertEquals(true, cookie.httpOnly());
		Assert.assertEquals(0, (long)cookie.maxAge());
		Assert.assertEquals("path", cookie.path());
		Assert.assertEquals(true, cookie.secure());
		Assert.assertEquals(1, (int)cookie.version());
	}

	@Test
	public void testConstructor_withEmptyString() {
		Cookie cookie = Cookie.cookie("");

		Assert.assertNull(cookie.name());
		Assert.assertNull(cookie.value());
	}

	@Test(expected = PodException.class)
	public void testConstructor_withInvalidMaxAge() {
		Cookie.cookie("a=b; max-age=invalid;");
	}

	@Test(expected = PodException.class)
	public void testConstructor_withInvalidVersion() {
		Cookie.cookie("a=b; version=invalid;");
	}

	@Test
	public void testConstructor_withMultipleNames() {
		Cookie cookie = Cookie.cookie("a=b; c=d");

		Assert.assertEquals("c", cookie.name());
		Assert.assertEquals("d", cookie.value());
	}

	@Test(expected = NullPointerException.class)
	public void testConstructor_withNullString() {
		Cookie.cookie(null);
	}

	@Test
	public void testEncode() {
		Cookie cookie = Cookie.cookie("name", "value");

		cookie.maxAge(0L);
		cookie.expires("expires");
		cookie.comment("comment");
		cookie.domain("domain");
		cookie.path("path");
		cookie.secure(true);
		cookie.version(1);
		cookie.httpOnly(true);

		Assert.assertEquals(
			"name=value; Max-Age=0; Expires=expires; Comment=comment; " +
				"Domain=domain; Path=path; Secure; Version=1; HttpOnly",
			cookie.encode());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testName_withIllegalCharacter() {
		Cookie cookie = Cookie.cookie("");

		cookie.name(";");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testName_withInvalidCharacter() {
		Cookie cookie = Cookie.cookie("");

		cookie.name("ç");
	}

}