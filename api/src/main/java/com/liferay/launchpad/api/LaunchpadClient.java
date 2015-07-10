package com.liferay.launchpad.api;

import com.liferay.launchpad.sdk.ContentType;
import com.liferay.launchpad.sdk.PodMultiMap;
import com.liferay.launchpad.sdk.RequestImpl;
import com.liferay.launchpad.sdk.Response;
import com.liferay.launchpad.sdk.json.JsonParser;

import java.util.concurrent.CompletableFuture;

/**
 * Java client.
 */
public class LaunchpadClient {

	/**
	 * Static factory for creating launchpad client.
	 */
	public static LaunchpadClient url(String url) {
		return new LaunchpadClient(url);
	}

	/**
	 * Main constructor.
	 */
	public LaunchpadClient(String url) {
		this.url = url;
	}

	/**
	 * Executes DELETE request.
	 */
	public CompletableFuture<Response> delete() {
		return delete(null);
	}

	/**
	 * Serializes object and executes DELETE request.
	 */
	public CompletableFuture<Response> delete(final Object body) {
		return sendAsync("DELETE", body);
	}

	/**
	 * Executes DELETE request.
	 */
	public CompletableFuture<Response> delete(final String body) {
		return sendAsync("DELETE", body);
	}

	/**
	 * Executes GET request.
	 */
	public CompletableFuture<Response> get() {
		return sendAsync("GET", null);
	}

	/**
	 * Adds new header. If the header with the same name already exists, it will
	 * not be overwritten, but new value will be stored. The order is preserved.
	 */
	public LaunchpadClient header(String name, String value) {
		headers.set(name, value);
		return this;
	}

	/**
	 * Serializes an object and sets query parameter.
	 */
	public LaunchpadClient param(String name, Object value) {
		String valueString = null;

		if (value != null) {
			final JsonEngine jsonEngine = resolveJsonEngine();

			valueString = jsonEngine.serializeToJson(value);
		}
		else {
			valueString = null;
		}

		params.set(name, valueString);

		return this;
	}

	/**
	 * Sets a query parameter. If the parameter with the same name already
	 * exists, it will be overwritten.
	 */
	public LaunchpadClient param(String name, String value) {
		params.set(name, value);
		return this;
	}

	/**
	 * Executes PATCH request.
	 */
	public CompletableFuture<Response> patch() {
		return patch(null);
	}

	/**
	 * Serializes object and executes PATCH request.
	 */
	public CompletableFuture<Response> patch(final Object body) {
		return sendAsync("PATCH", body);
	}

	/**
	 * Executes PATCH request.
	 */
	public CompletableFuture<Response> patch(final String body) {
		return sendAsync("PATCH", body);
	}

	/**
	 * Creates new {@link LaunchpadClient}.
	 */
	public LaunchpadClient path(String path) {
		return new LaunchpadClient(url, path)
			.use(currentTransport)
			.use(currentJsonEngine);
	}

	/**
	 * Executes POST request.
	 */
	public CompletableFuture<Response> post() {
		return post(null);
	}

	/**
	 * Serializes object and executes POST request.
	 */
	public CompletableFuture<Response> post(final Object body) {
		return sendAsync("POST", body);
	}

	/**
	 * Executes POST request.
	 */
	public CompletableFuture<Response> post(final String body) {
		return sendAsync("POST", body);
	}

	/**
	 * Executes PUT request.
	 */
	public CompletableFuture<Response> put() {
		return put(null);
	}

	/**
	 * Serializes object and PUT request.
	 */
	public CompletableFuture<Response> put(final Object body) {
		return sendAsync("PUT", body);
	}

	/**
	 * Executes PUT request.
	 */
	public CompletableFuture<Response> put(final String body) {
		return sendAsync("PUT", body);
	}

	/**
	 * Returns full URL.
	 */
	public String url() {
		return url;
	}

	/**
	 * Specifies {@link JsonEngine} implementation.
	 */
	public LaunchpadClient use(JsonEngine jsonEngine) {
		this.currentJsonEngine = jsonEngine;
		return this;
	}

	/**
	 * Specifies {@link Transport} implementation.
	 */
	public LaunchpadClient use(Transport transport) {
		this.currentTransport = transport;
		return this;
	}

	/**
	 * Continuations constructor, used from existing instance, therefore
	 * no need to configure the client.
	 */
	protected LaunchpadClient(String baseUrl, String url) {
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
	protected Transport resolveTransport() {
		if (currentTransport == null) {
			TransportBinder transportBinder = Binder.getTransportBinder();

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
	protected CompletableFuture<Response> sendAsync(
		final String methodName, final Object body) {

		String bodyJson = null;

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
	protected CompletableFuture<Response> sendAsync(
		final String methodName, final String body) {

		final Transport transport = resolveTransport();

		final RequestImpl request = new RequestImpl(url());

		request.method(methodName);
		request.headers(headers);
		request.params(params);
		request.body(body);

		return transport.send(request, responseConsumer);
	}

	protected JsonEngine currentJsonEngine;
	protected Transport currentTransport;
	protected final PodMultiMap headers = PodMultiMap.newMultiMap();
	protected final PodMultiMap params = PodMultiMap.newMultiMap();

	protected final Transport.ResponseConsumer responseConsumer =
			response -> {
				final JsonEngine jsonEngine = resolveJsonEngine();

				final String body = response.body();

				response.setJsonParser(new JsonParser() {
					@Override
					public <T> T parse(String json) {
						return jsonEngine.parseJsonToModel(body);
					}

					@Override
					public <T> T parse(String json, Class<T> type) {
						return jsonEngine.parseJsonToModel(body, type);
					}
				});

				response.setJsonSerializer((object, deep) ->
					jsonEngine.serializeToJson(object));
			};

	protected final String url;

}