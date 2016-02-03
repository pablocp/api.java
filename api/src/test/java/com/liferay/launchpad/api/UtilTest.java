package com.liferay.launchpad.api;

import static org.junit.Assert.assertEquals;

import com.liferay.launchpad.sdk.PodMultiMap;
import com.liferay.launchpad.sdk.TestPodMultiMap;

import org.junit.Test;
public class UtilTest {

	@Test
	public void testAddParametersToQueryString_encodingParams() {
		PodMultiMap<String> params = new TestPodMultiMap<>(true);
		params.add("foo", "bar");
		params.add("search", "{foo:bar}");

		assertEquals(
			"search=%7Bfoo%3Abar%7D&foo=bar",
			Util.addParametersToQueryString(null, params));
		assertEquals(
			"search=%7Bfoo%3Abar%7D&foo=bar",
			Util.addParametersToQueryString("", params));
		assertEquals(
			"a=b&search=%7Bfoo%3Abar%7D&foo=bar",
			Util.addParametersToQueryString("a=b", params));
	}

	@Test
	public void testEncodeURIComponent() {
		assertEquals("", Util.encodeURIComponent(""));
		assertEquals("!'()*-._~", Util.encodeURIComponent("!'()*-._~"));
		assertEquals("%20", Util.encodeURIComponent(" "));
		assertEquals("%7B%7D", Util.encodeURIComponent("{}"));
	}

	@Test
	public void testEncodeURIComponent_unsupportedEncodingException() {
		String encoding = Util.DEFAULT_ENCODING;

		try {
			Util.DEFAULT_ENCODING = "unsupported";
			assertEquals(" ", Util.encodeURIComponent(" "));
		}
		finally {
			Util.DEFAULT_ENCODING = encoding;
		}
	}

	@Test
	public void testJoinPathAndQuery() {
		assertEquals("/path/a", Util.joinPathAndQuery("/path/a", null));
		assertEquals("/path/a/", Util.joinPathAndQuery("/path/a/", null));
		assertEquals("/path/a", Util.joinPathAndQuery("/path/a", ""));
		assertEquals("/path/a?a=b", Util.joinPathAndQuery("/path/a", "a=b"));
		assertEquals("", Util.joinPathAndQuery("", null));
		assertEquals("", Util.joinPathAndQuery(null, null));
		assertEquals("", Util.joinPathAndQuery(null, ""));
		assertEquals("?a=b", Util.joinPathAndQuery(null, "a=b"));
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
	public void testUtil_constructorDummyCoverage() {
		new Util();
	}

}