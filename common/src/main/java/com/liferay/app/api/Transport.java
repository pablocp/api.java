package com.liferay.app.api;

public interface Transport {

	String send(LaunchpadClient lc, String method, String body);

}