package com.liferay.launchpad.api;

import com.liferay.launchpad.sdk.ContentType;
import com.liferay.launchpad.sdk.PodMultiMap;
import com.liferay.launchpad.sdk.RequestImpl;
import com.liferay.launchpad.sdk.Response;
import com.liferay.launchpad.sdk.ValuesUtil;
import com.liferay.launchpad.serializer.LaunchpadSerializer;

import java.util.concurrent.CompletableFuture;

/**
 * Java client.
 */
public class Launchpad {

	public static final String METHOD_DELETE = "DELETE";

	public static final String METHOD_GET = "GET";

	public static final String METHOD_PATCH = "PATCH";

	public static final String METHOD_POST = "POST";

	public static final String METHOD_PUT = "PUT";

	/**
	 * Static factory for creating launchpad client.
	 */
	public static Launchpad url(String url) {
		return new Launchpad(url);
	}

	/**
	 * Main constructor.
	 */
	public Launchpad(String url) {
		this.url = url;
	}

	/**
	 * Executes DELETE request.
	 */
	public Response delete() {
		return delete(null);
	}

	/**
	 * Executes DELETE request.
	 */
	public Response delete(final Object body) {
		return send(METHOD_DELETE, body);
	}

	/**
	 * Executes DELETE request.
	 */
	public Response delete(final String body) {
		return send(METHOD_DELETE, body);
	}

	/**
	 * Executes DELETE request asynchronously.
	 */
	public CompletableFuture<Response> deleteAsync() {
		return deleteAsync(null);
	}

	/**
	 * Serializes object and executes DELETE request asynchronously.
	 */
	public CompletableFuture<Response> deleteAsync(final Object body) {
		return sendAsync(METHOD_DELETE, body);
	}

	/**
	 * Executes DELETE request asynchronously.
	 */
	public CompletableFuture<Response> deleteAsync(final String body) {
		return sendAsync(METHOD_DELETE, body);
	}

	/**
	 * Executes GET request.
	 */
	public Response get() {
		return send(METHOD_GET, null);
	}

	/**
	 * Executes GET request.
	 */
	public Response get(final Object body) {
		return send(METHOD_GET, body);
	}

	/**
	 * Executes GET request.
	 */
	public Response get(final String body) {
		return send(METHOD_GET, body);
	}

	/**
	 * Executes GET request asynchronously.
	 */
	public CompletableFuture<Response> getAsync() {
		return sendAsync(METHOD_GET, null);
	}

	/**
	 * Executes GET request asynchronously.
	 */
	public CompletableFuture<Response> getAsync(final Object body) {
		return sendAsync(METHOD_GET, body);
	}

	/**
	 * Executes GET request asynchronously.
	 */
	public CompletableFuture<Response> getAsync(final String body) {
		return sendAsync(METHOD_GET, body);
	}

	/**
	 * Adds new header. If the header with the same name already exists, it will
	 * not be overwritten, but new value will be stored. The order is preserved.
	 */
	public Launchpad header(String name, String value) {
		headers.set(name, value);
		return this;
	}

	/**
	 * Serializes an object and sets query parameter.
	 */
	public Launchpad param(String name, Object value) {
		String valueString = null;

		if (value != null) {
			final LaunchpadSerializer serializer = resolveSerializer();

			valueString = serializer.serialize(value);
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
	public Launchpad param(String name, String value) {
		params.set(name, value);
		return this;
	}

	/**
	 * Executes PATCH request.
	 */
	public Response patch() {
		return patch(null);
	}

	/**
	 * Executes PATCH request.
	 */
	public Response patch(final Object body) {
		return send(METHOD_PATCH, body);
	}

	/**
	 * Executes PATCH request.
	 */
	public Response patch(final String body) {
		return send(METHOD_PATCH, body);
	}

	/**
	 * Executes PATCH request asynchronously.
	 */
	public CompletableFuture<Response> patchAsync() {
		return patchAsync(null);
	}

	/**
	 * Serializes object and executes PATCH request asynchronously.
	 */
	public CompletableFuture<Response> patchAsync(final Object body) {
		return sendAsync(METHOD_PATCH, body);
	}

	/**
	 * Executes PATCH request asynchronously.
	 */
	public CompletableFuture<Response> patchAsync(final String body) {
		return sendAsync(METHOD_PATCH, body);
	}

	/**
	 * Creates new {@link Launchpad}.
	 */
	public Launchpad path(String path) {
		return new Launchpad(url, path)
			.use(currentTransport)
			.use(launchpadSerializer);
	}

	/**
	 * Executes POST request.
	 */
	public Response post() {
		return post(null);
	}

	/**
	 * Executes POST request.
	 */
	public Response post(final Object body) {
		return send(METHOD_POST, body);
	}

	/**
	 * Executes POST request.
	 */
	public Response post(final String body) {
		return send(METHOD_POST, body);
	}

	/**
	 * Executes POST request asynchronously.
	 */
	public CompletableFuture<Response> postAsync() {
		return postAsync(null);
	}

	/**
	 * Serializes object and executes POST request asynchronously.
	 */
	public CompletableFuture<Response> postAsync(final Object body) {
		return sendAsync(METHOD_POST, body);
	}

	/**
	 * Executes POST request asynchronously.
	 */
	public CompletableFuture<Response> postAsync(final String body) {
		return sendAsync(METHOD_POST, body);
	}

	/**
	 * Executes PUT request.
	 */
	public Response put() {
		return put(null);
	}

	/**
	 * Executes PUT request.
	 */
	public Response put(final Object body) {
		return send(METHOD_PUT, body);
	}

	/**
	 * Executes PUT request.
	 */
	public Response put(final String body) {
		return send(METHOD_PUT, body);
	}

	/**
	 * Executes PUT request asynchronously.
	 */
	public CompletableFuture<Response> putAsync() {
		return putAsync(null);
	}

	/**
	 * Serializes object and PUT request asynchronously.
	 */
	public CompletableFuture<Response> putAsync(final Object body) {
		return sendAsync(METHOD_PUT, body);
	}

	/**
	 * Executes PUT request asynchronously.
	 */
	public CompletableFuture<Response> putAsync(final String body) {
		return sendAsync(METHOD_PUT, body);
	}

	/**
	 * Returns full URL.
	 */
	public String url() {
		return url;
	}

	/**
	 * Specifies custom serializer implementation.
	 */
	public Launchpad use(LaunchpadSerializer launchpadSerializer) {
		this.launchpadSerializer = launchpadSerializer;
		return this;
	}

	/**
	 * Specifies {@link Transport} implementation.
	 */
	public Launchpad use(Transport transport) {
		this.currentTransport = transport;
		return this;
	}

	/**
	 * Continuations constructor, used from existing instance, therefore
	 * no need to configure the client.
	 */
	protected Launchpad(String baseUrl, String url) {
		this.url = Util.joinPaths(baseUrl, url);
	}

	/**
	 * Resolves body string from body object. Sets content type to json is
	 * body object is not {@code null}.
	 */
	protected String resolveBodyString(Object body) {
		String bodyJson = null;

		if (body != null) {
			headers.set("Content-Type", ContentType.JSON.toString());

			final LaunchpadSerializer launchpadSerializer = resolveSerializer();

			bodyJson = launchpadSerializer.serialize(body);
		}

		return bodyJson;
	}

	/**
	 * Creates new request implementation. Prepares method name, body and
	 * header and parameters map.
	 */
	protected RequestImpl resolveRequest(String methodName, String body) {
		final RequestImpl request = new RequestImpl(url());

		request.method(methodName);
		request.body(body);

		headers.forEach(
			entry -> request.header(
				entry.getKey(), ValuesUtil.toString(entry.getValue())));

		params.forEach(
			entry -> request.param(
				entry.getKey(), ValuesUtil.toString(entry.getValue())));
		return request;
	}

	/**
	 * Resolves serializer.
	 */
	protected LaunchpadSerializer resolveSerializer() {
		if (launchpadSerializer == null) {
			launchpadSerializer = LaunchpadSerializer.get();
		}

		return launchpadSerializer;
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
	 * {@link #send(String, String) sends it}.
	 */
	protected Response send(final String methodName, final Object body) {
		String bodyJson = resolveBodyString(body);

		return send(methodName, bodyJson);
	}

	/**
	 * Uses transport to send request with given method name and body
	 * synchronously.
	 */
	protected Response send(final String methodName, final String body) {
		final Transport transport = resolveTransport();

		final RequestImpl request = resolveRequest(methodName, body);

		Response response = transport.send(request);

		return response;
	}

	/**
	 * Serializes input object to a JSON string and
	 * {@link #sendAsync(String, String) sends it}.
	 */
	protected CompletableFuture<Response> sendAsync(
		final String methodName, final Object body) {

		String bodyJson = resolveBodyString(body);

		return sendAsync(methodName, bodyJson);
	}

	/**
	 * Uses transport to send request with given method name and body
	 * asynchronously.
	 */
	protected CompletableFuture<Response> sendAsync(
		final String methodName, final String body) {

		final Transport transport = resolveTransport();

		final RequestImpl request = resolveRequest(methodName, body);

		return transport.sendAsync(request);
	}

	protected Transport currentTransport;
	protected final PodMultiMap headers = PodMultiMap.newMultiMap();
	protected LaunchpadSerializer launchpadSerializer;
	protected final PodMultiMap params = PodMultiMap.newMultiMap();
	protected final String url;

}