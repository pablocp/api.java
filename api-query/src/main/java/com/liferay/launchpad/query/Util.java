package com.liferay.launchpad.query;

import java.util.HashMap;
import java.util.Map;

/**
 */
public class Util {

	public static Map wrap(String name, Object value) {
		Map map = new HashMap();
		map.put(name, value);
		return map;
	}

}