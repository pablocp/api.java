package com.liferay.app.api;

public interface Transport {

	String send(LaunchpadClientDef lc, String method, String body);

}