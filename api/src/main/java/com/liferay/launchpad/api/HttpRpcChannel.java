package com.liferay.launchpad.api;

import com.google.protobuf.*;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

/**
 * Created by Pablo on 10/20/15.
 */
public class HttpRpcChannel implements RpcChannel {

	public HttpRpcChannel(String serviceURL) {
		this.serviceURL = serviceURL;
	}

	@Override
	public void callMethod(
		Descriptors.MethodDescriptor method, RpcController rpcController,
		Message request, Message responsePrototype, RpcCallback<Message> done) {

		Message response = null;

		try {
			byte[] requestBytes = request.toByteArray();

			String url = serviceURL + "/" + method.getName();

			HttpRequest httpRequest = HttpRequest.post(url)
										.body(requestBytes,
											"application/x-protobuf");
			HttpResponse httpResponse = httpRequest.send();

			if (httpResponse.statusCode() == 200) {
				response = responsePrototype.toBuilder()
					.mergeFrom(httpResponse.bodyBytes())
					.build();
			}
			else {
				rpcController.setFailed(httpResponse.statusPhrase());
			}
		}
		catch (InvalidProtocolBufferException ipbe) {
			rpcController.setFailed("Could not parse the server response.");
		}
		finally {
			done.run(response);
		}
	}

	private String serviceURL;
}
