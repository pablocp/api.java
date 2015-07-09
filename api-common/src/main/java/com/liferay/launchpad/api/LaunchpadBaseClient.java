package com.liferay.launchpad.api;

import com.liferay.launchpad.sdk.ContentType;
import com.liferay.launchpad.sdk.PodMultiMap;
import com.liferay.launchpad.sdk.PodMultiMapFactory;
import com.liferay.launchpad.sdk.RequestImpl;
import com.liferay.launchpad.sdk.ResponseImpl;

/**
 * Base client contains code that is same for all java versions.
 */
public abstract class LaunchpadBaseClient<F, C> {

	/**
	 * Executes DELETE request.
	 */
	public F delete() {
		return delete(null);
	}

	/**
	 * Executes DELETE request.
	 */
	public F delete(final String body) {
		return sendAsync("DELETE", body);
	}

	/**
	 * Serializes object and executes DELETE request.
	 */
	public F delete(final Object body) {
		return sendAsync("DELETE", body);
	}

	/**
	 * Executes GET request.
	 */
	public F get() {
		return sendAsync("GET", null);
	}

	/**
	 * Adds new header. If the header with the same name already exists, it will
	 * not be overwritten, but new value will be stored. The order is preserved.
	 */
	public C header(String name, String value) {
		headers.set(name, value);
		return (C)this;
	}

	/**
	 * Defines model class for response body.
	 */
	public C model(Class<?> modelClass) {
		this.modelClass = modelClass;
		return (C)this;
	}

	/**
	 * Sets a query parameter. If the parameter with the same name already
	 * exists, it will be overwritten.
	 */
	public C param(String name, String value) {
		params.set(name, value);
		return (C)this;
	}

	/**
	 * Serializes an object and sets query parameter.
	 */
	public C param(String name, Object value) {
		String valueString;

		if (value != null) {
			final JsonEngine jsonEngine = resolveJsonEngine();

			valueString = jsonEngine.serializeToJson(value);
		}
		else {
			valueString = null;
		}

		params.set(name, valueString);

		return (C)this;
	}

	/**
	 * Executes PATCH request.
	 */
	public F patch() {
		return patch(null);
	}

	/**
	 * Executes PATCH request.
	 */
	public F patch(final String body) {
		return sendAsync("PATCH", body);
	}

	/**
	 * Serializes object and executes PATCH request.
	 */
	public F patch(final Object body) {
		return sendAsync("PATCH", body);
	}

	/**
	 * Executes POST request.
	 */
	public F post() {
		return post(null);
	}

	/**
	 * Executes POST request.
	 */
	public F post(final String body) {
		return sendAsync("POST", body);
	}

	/**
	 * Serializes object and executes POST request.
	 */
	public F post(final Object body) {
		return sendAsync("POST", body);
	}

	/**
	 * Executes PUT request.
	 */
	public F put() {
		return put(null);
	}

	/**
	 * Executes PUT request.
	 */
	public F put(final String body) {
		return sendAsync("PUT", body);
	}

	/**
	 * Serializes object and PUT request.
	 */
	public F put(final Object body) {
		return sendAsync("PUT", body);
	}

	/**
	 * Returns full URL.
	 */
	public String url() {
		return url;
	}

	/**
	 * Specifies {@link Transport} implementation.
	 */
	public C use(Transport<F> transport) {
		this.currentTransport = transport;
		return (C)this;
	}

	/**
	 * Specifies {@link JsonEngine} implementation.
	 */
	public C use(JsonEngine jsonEngine) {
		this.currentJsonEngine = jsonEngine;
		return (C)this;
	}

	/**
	 * Main constructor.
	 */
	protected LaunchpadBaseClient(String url) {
		this.url = url;
	}

	/**
	 * Continuations constructor, used from existing instance, therefore
	 * no need to configure the client.
	 */
	protected LaunchpadBaseClient(String baseUrl, String url) {
		this.url = Util.joinPaths(baseUrl, url);
	}

	/**
	 * Resolves JSON engine. Throws exception if JSON engine is missing.
	 */
	protected JsonEngine resolveJsonEngine() {
		if (currentJsonEngine == null) {
			JsonEngineBinder jsonEngineBinder = Binder.getJsonEngineBinder();

			if (jsonEngineBinder != null) {
				currentJsonEngine = jsonEngineBinder.initJsonEngine();
			}
		}

		if (currentJsonEngine == null) {
			throw new LaunchpadClientException("JsonEngine not defined!");
		}

		return currentJsonEngine;
	}

	/**
	 * Resolves transport. Throws exception if transport is missing.
	 */
	protected Transport<F> resolveTransport() {
		if (currentTransport == null) {
			TransportBinder<F> transportBinder = Binder.getTransportBinder();

			if (transportBinder != null) {
				currentTransport = transportBinder.initTransport();
			}
		}

		if (currentTransport == null) {
			throw new LaunchpadClientException("Transport not defined!");
		}

		return currentTransport;
	}

	/**
	 * Serializes input object to a JSON string and
	 * {@link #sendAsync(String, String) sends it}.
	 */
	protected F sendAsync(final String methodName, final Object body) {
		String bodyJson;

		if (body != null) {
			headers.set("Content-Type", ContentType.JSON.contentType());

			final JsonEngine jsonEngine = resolveJsonEngine();

			bodyJson = jsonEngine.serializeToJson(body);
		}
		else {
			bodyJson = null;
		}

		return sendAsync(methodName, bodyJson);
	}

	/**
	 * Uses transport to send request with given method name and body
	 * asynchronously.
	 */
	protected F sendAsync(final String methodName, final String body) {
		final Transport<F> transport = resolveTransport();

		final RequestImpl request = new RequestImpl(url());

		request.method(methodName);
		request.headers(headers);
		request.params(params);
		request.body(body);

		return transport.send(request, responseConsumer);
	}

	protected final Transport.ResponseConsumer responseConsumer
			= new Transport.ResponseConsumer() {

		@Override
		public void acceptResponse(ResponseImpl response) {
			if (modelClass == null) {
				return;
			}

			final JsonEngine jsonEngine = resolveJsonEngine();

			String body = response.body();

			if (body != null) {
				Object bodyObject = jsonEngine.parseJsonToModel(
					body, modelClass);

				response.bodyObject(bodyObject);
			}
		}
	};

	protected final PodMultiMap headers = PodMultiMapFactory.newMultiMap();
	protected final PodMultiMap params = PodMultiMapFactory.newMultiMap();
	protected final String url;
	protected JsonEngine currentJsonEngine;
	protected Transport<F> currentTransport;
	protected Class<?> modelClass;

}