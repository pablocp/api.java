package com.liferay.launchpad.api;

import com.liferay.launchpad.sdk.RequestImpl;
import com.liferay.launchpad.sdk.Response;
import com.liferay.launchpad.sdk.ResponseImpl;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
@MultiJava(version = 6)
public abstract class BlockingTransport
		extends BaseBlockingTransport<Future<Response>> {

	@Override
	public final Future<Response> send(
			final RequestImpl request, final ResponseConsumer responseConsumer) {

		return executor.submit(new Callable<Response>() {
			@Override
			public Response call() throws Exception {
				ResponseImpl response = sendBlockingRequest(request);

				Util.validateResponse(response);

				if (responseConsumer != null) {
					responseConsumer.acceptResponse(response);
				}

				return response;
			}
		});
	}

	protected BlockingTransport() {
		super(null);
	}

	protected BlockingTransport(int numberOfThreads) {
		super(numberOfThreads);
	}

	/**
	 * Sends blocking request and returns the response.
	 */
	protected abstract ResponseImpl sendBlockingRequest(RequestImpl request);

}