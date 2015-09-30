package com.liferay.launchpad.api;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import com.liferay.launchpad.sdk.PodMultiMap;
import com.liferay.launchpad.sdk.impl.PodMultiMapImpl;

import org.junit.Test;
public class UtilTest {

	@Test
	public void testAddParametersToUrlQueryString() {
		String url = "http://foo.bar";
		PodMultiMap<String> params = new PodMultiMapImpl<>(true);
		params.add("foo", "bar");
		params.add("search", "{foo:bar}");
		assertEquals(
			"http://foo.bar?foo=bar&search=%7Bfoo%3Abar%7D",
			Util.addParametersToUrlQueryString(url, params));
	}

	@Test
	public void testEncodeURIComponent() {
		assertEquals("", Util.encodeURIComponent(""));
		assertEquals("!'()*-._~", Util.encodeURIComponent("!'()*-._~"));
		assertEquals("%20", Util.encodeURIComponent(" "));
		assertEquals("%7B%7D", Util.encodeURIComponent("{}"));
	}

	@Test
	public void testJoinPaths_separators() {
		assertEquals("foo", Util.joinPaths("foo", ""));
		assertEquals("/foo", Util.joinPaths("", "foo"));
		assertEquals("foo", Util.joinPaths("foo/", ""));
		assertEquals("/foo", Util.joinPaths("", "foo/"));
		assertEquals("foo/bar", Util.joinPaths("foo/", "/bar"));
		assertEquals("foo/bar", Util.joinPaths("foo/", "bar"));
		assertEquals("foo/bar", Util.joinPaths("foo", "bar"));
		assertEquals("foo/bar", Util.joinPaths("foo", "/bar"));
	}

	@Test
	public void testJoinPaths_withFullUrls() {
		assertEquals(
			"http://localhost:123", Util.joinPaths("http://localhost:123", ""));
	}

	@Test
	public void testParseUrls() {
		assertArrayEquals(
			new String[] {"localhost:8080", "/path/a", ""},
			Util.parseUrl("http://localhost:8080/path/a"));
		assertArrayEquals(
			new String[] {"localhost:8080", "/path/a", ""},
			Util.parseUrl("//localhost:8080/path/a"));
		assertArrayEquals(
			new String[] {"localhost:8080", "/path/a", ""},
			Util.parseUrl("localhost:8080/path/a"));
		assertArrayEquals(
			new String[] {"", "/path/a", ""}, Util.parseUrl("/path/a"));
		assertArrayEquals(
			new String[] {"", "/path/a", "?foo=1"},
			Util.parseUrl("/path/a?foo=1"));
		assertArrayEquals(
			new String[] {"localhost:8080", "/", ""},
			Util.parseUrl("localhost:8080"));
		assertArrayEquals(
			new String[] {"localhost:8080", "/", ""},
			Util.parseUrl("localhost:8080/"));
	}

}