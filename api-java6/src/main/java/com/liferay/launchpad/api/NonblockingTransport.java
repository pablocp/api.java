package com.liferay.launchpad.api;

import java.util.concurrent.Future;
@MultiJava(version = 6)
public interface NonblockingTransport
	extends Transport<Future<ClientResponse>> {
}