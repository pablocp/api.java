package com.liferay.launchpad.api;

import com.liferay.launchpad.sdk.Response;

import java.util.concurrent.CompletableFuture;

/**
 * Just an interface that defines completable future.
 */
public interface NonblockingTransport
	extends Transport<CompletableFuture<Response>> {
}