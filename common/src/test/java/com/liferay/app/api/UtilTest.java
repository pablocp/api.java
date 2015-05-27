package com.liferay.app.api;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
public class UtilTest {

	@Test
	public void testJoinPaths_separators() {
		assertEquals("foo/bar", Util.joinPaths("foo/", "/bar"));
		assertEquals("foo/bar", Util.joinPaths("foo/", "bar"));
		assertEquals("foo/bar", Util.joinPaths("foo", "bar"));
		assertEquals("foo/bar", Util.joinPaths("foo", "/bar"));
	}

}