package com.liferay.launchpad.query;

import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;
public class RangeTest {

	@Test
	public void testRange() throws Exception {
		JSONAssert.assertEquals("{\"from\":1}", Range.from(1).toString(), true);
		JSONAssert.assertEquals("{\"to\":1}", Range.to(1).toString(), true);
		JSONAssert.assertEquals(
			"{\"from\":1,\"to\":2}", Range.range(1, 2).toString(), true);
	}

}