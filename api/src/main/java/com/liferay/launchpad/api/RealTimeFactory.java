package com.liferay.launchpad.api;

import java.net.URISyntaxException;

import java.util.Map;
public interface RealTimeFactory {

	public RealTime createRealTime(String url, Map<String, Object> options)
		throws URISyntaxException;

	public static class Default {

		protected Default() {}

		public static RealTimeFactory factory;

	}

}