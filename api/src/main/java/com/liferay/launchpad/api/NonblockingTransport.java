package com.liferay.launchpad.api;

import java.util.concurrent.CompletableFuture;

/**
 * Just an interface that defines completable future.
 */
@MultiJava(version = 8)
public interface NonblockingTransport
	extends Transport<CompletableFuture<ClientResponse>> {
}