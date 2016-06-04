package com.wedeploy.api.realtime.impl;

import com.wedeploy.api.realtime.RealTime;
import com.wedeploy.api.realtime.RealTimeFactory;

import java.net.URISyntaxException;

import java.util.Map;
public class SocketIORealTimeFactory implements RealTimeFactory {

	@Override
	public RealTime createRealTime(String url, Map<String, Object> options)
		throws URISyntaxException {

		return new SocketIORealTime(url, options);
	}

}