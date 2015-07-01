package com.liferay.launchpad.api;

import com.liferay.launchpad.sdk.Response;

import java.util.concurrent.Future;
@MultiJava(version = 6)
public interface NonblockingTransport extends Transport<Future<Response>> {
}