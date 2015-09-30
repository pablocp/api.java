package com.liferay.launchpad.api.impl;

import com.liferay.launchpad.api.RealTime;
import com.liferay.launchpad.api.RealTimeFactory;

import java.net.URISyntaxException;

import java.util.Map;
public class SocketIORealTimeFactory implements RealTimeFactory {

	@Override
	public RealTime createRealTime(String url, Map<String, Object> options)
		throws URISyntaxException {

		return new SocketIORealTime(url, options);
	}

}