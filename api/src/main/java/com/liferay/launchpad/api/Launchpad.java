package com.liferay.launchpad.api;

import com.liferay.launchpad.query.Aggregation;
import com.liferay.launchpad.query.Filter;
import com.liferay.launchpad.query.Query;
import com.liferay.launchpad.sdk.Auth;
import com.liferay.launchpad.sdk.ContentType;
import com.liferay.launchpad.sdk.Cookie;
import com.liferay.launchpad.sdk.PodMultiMap;
import com.liferay.launchpad.sdk.Request;
import com.liferay.launchpad.sdk.RequestImpl;
import com.liferay.launchpad.sdk.Response;
import com.liferay.launchpad.serializer.LaunchpadSerializer;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
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
	 * See {@link com.liferay.launchpad.query.Query.Builder}
	 */
	public Launchpad aggregate(Aggregation aggregation) {
		getOrCreateQuery().aggregate(aggregation);
		return this;
	}

	/**
	 * See {@link com.liferay.launchpad.query.Query.Builder}
	 */
	public Launchpad aggregate(String name, String field, String operator) {
		return aggregate(Aggregation.of(name, field, operator));
	}

	/**
	 * Authenticates with an auth instance.
	 */
	public Launchpad auth(Auth auth) {
		this.auth = auth;
		return this;
	}

	/**
	 * Authenticates with a token.
	 */
	public Launchpad auth(String token) {
		auth(Auth.create(token));
		return this;
	}

	/**
	 * Authenticates with username and password.
	 */
	public Launchpad auth(String username, String password) {
		auth(Auth.create(username, password));
		return this;
	}

	/**
	 * See {@link com.liferay.launchpad.query.Query.Builder}
	 */
	public Launchpad count() {
		getOrCreateQuery().count();
		return this;
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
	 * See {@link com.liferay.launchpad.query.Query.Builder}
	 */
	public Launchpad filter(Filter filter) {
		getOrCreateQuery().filter(filter);
		return this;
	}

	/**
	 * See {@link com.liferay.launchpad.query.Query.Builder}
	 */
	public Launchpad filter(String field, Object value) {
		return filter(Filter.field(field, value));
	}

	/**
	 * See {@link com.liferay.launchpad.query.Query.Builder}
	 */
	public Launchpad filter(String field, String operator, Object value) {
		return filter(Filter.field(field, operator, value));
	}

	/**
	 * Gets the form parameter.
	 */
	public Object form(String name) {
		return forms.get(name);
	}

	/**
	 * Sets the form parameter.
	 */
	public Launchpad form(String name, Object value) {
		forms.set(name, value);
		return this;
	}

	/**
	 * Returns the form parameter.
	 */
	public PodMultiMap<Object> forms() {
		return forms;
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
	 * See {@link com.liferay.launchpad.query.Query.Builder}
	 */
	public Launchpad highlight(String field) {
		getOrCreateQuery().highlight(field);
		return this;
	}

	/**
	 * See {@link com.liferay.launchpad.query.Query.Builder}
	 */
	public Launchpad limit(int limit) {
		getOrCreateQuery().limit(limit);
		return this;
	}

	/**
	 * See {@link com.liferay.launchpad.query.Query.Builder}
	 */
	public Launchpad offset(int offset) {
		getOrCreateQuery().offset(offset);
		return this;
	}

	/**
	 * Serializes an object using JSON serializer and sets query parameter.
	 */
	public Launchpad param(String name, Object value) {
		String valueString = null;

		if (value != null) {
			LaunchpadSerializer serializer = LaunchpadSerializer.get(
				ContentType.JSON);

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
			.auth(auth);
	}

	/**
	 * Creates new {@link Launchpad}.
	 */
	public Launchpad path(String... paths) {
		Launchpad instance = this;

		for (String path : paths) {
			instance = instance.path(path);
		}

		return instance;
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
	 * See {@link com.liferay.launchpad.query.Query.Builder}
	 */
	public Launchpad search(Filter filter) {
		getOrCreateQuery().search(filter);
		return this;
	}

	/**
	 * See {@link com.liferay.launchpad.query.Query.Builder}
	 */
	public Launchpad search(String text) {
		return search(Filter.match(text));
	}

	/**
	 * See {@link com.liferay.launchpad.query.Query.Builder}
	 */
	public Launchpad search(String field, String text) {
		return search(Filter.match(field, text));
	}

	/**
	 * See {@link com.liferay.launchpad.query.Query.Builder}
	 */
	public Launchpad search(String field, String operator, Object value) {
		return search(Filter.field(field, operator, value));
	}

	/**
	 * See {@link com.liferay.launchpad.query.Query.Builder}
	 */
	public Launchpad sort(String field) {
		return sort(field, "asc");
	}

	/**
	 * See {@link com.liferay.launchpad.query.Query.Builder}
	 */
	public Launchpad sort(String field, String direction) {
		getOrCreateQuery().sort(field, direction);
		return this;
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
	public Launchpad use(Transport transport) {
		this.currentTransport = transport;
		return this;
	}

	/**
	 * Creates new socket.io instance.
	 */
	public RealTime watch() {
		return this.watch(null);
	}

	public RealTime watch(Object body) {
		return this.watch(resolveBodyString(body), null);
	}

	public RealTime watch(Object body, Map<String, Object> options) {
		return this.watch(resolveBodyString(body), options);
	}

	public RealTime watch(String body) {
		return this.watch(body, null);
	}

	public RealTime watch(String body, Map<String, Object> options) {
		Request clientRequest = this.resolveRequest(METHOD_GET, body, true);

		String url[] = Util.parseUrl(
			Util.addParametersToUrlQueryString(
				clientRequest.url(), clientRequest.params()));

		if (options == null) {
			options = new HashMap<>();
			options.put("forceNew", true);
		}

		options.putIfAbsent("path", url[1]);

		try {
			return RealTime.io(
				"http://" + url[0] + "?url=" + Util.encodeURIComponent(url[1] + url[2]),
				options);
		}
		catch (NullPointerException e) {
			throw new LaunchpadClientException("Socket.io client not loaded");
		}
		catch (Exception e) {
			throw new LaunchpadClientException(e.getMessage());
		}
	}

	/**
	 * Continuations constructor, used from existing instance, therefore
	 * no need to configure the client.
	 */
	protected Launchpad(String baseUrl, String url) {
		this.url = Util.joinPaths(baseUrl, url);
	}

	/**
	 * Resolves authentication.
	 */
	protected void resolveAuthentication(Request request) {
		if (auth.hasToken()) {
			request.cookie(Cookie.cookie("token", auth.token()));
		}
		else if (auth.hasUsername() && auth.hasPassword()) {
			String credentials = auth.username() + ":" + auth.password();
			request.header(
				"Authorization", "Basic " +
					Base64.getEncoder().encodeToString(credentials.getBytes()));
		}
	}

	/**
	 * Resolves body string from body object. If object is <code>null</code>,
	 * null will be returned. Object is serialized using the existing content
	 * type. If content type is not set, JSON is assumed; and will be set
	 * as content type as well.
	 */
	protected String resolveBodyString(Object body) {
		if (body == null) {
			return null;
		}

		final LaunchpadSerializer launchpadSerializer;

		String existingContentType = headers.get("Content-Type");

		if (existingContentType == null) {
			headers.set("Content-Type", ContentType.JSON.toString());

			launchpadSerializer = LaunchpadSerializer.get(ContentType.JSON);
		}
		else {
			launchpadSerializer = LaunchpadSerializer.get(
				new ContentType(existingContentType));
		}

		// TODO(igor): Move api query checking outside?

		Package pkg = body.getClass().getPackage();

		if (pkg != null) {
			switch (pkg.getName()) {
				case "com.liferay.launchpad.query":
					return body.toString();
			}
		}

		return launchpadSerializer.serialize(body);
	}

	/**
	 * Creates new request implementation. Prepares method name, body and
	 * header and parameters map.
	 */
	protected RequestImpl resolveRequest(String methodName, String body) {
		return resolveRequest(methodName, body, false);
	}

	protected RequestImpl resolveRequest(
		String methodName, String body, boolean convertBody) {

		final RequestImpl request = new RequestImpl(url());

		request.method(methodName);
		request.body(body);

		if (request.body() == null) {
			if (query != null) {
				request.body(resolveBodyString(query));
				query = null;
			}
		}

		if (auth != null) {
			resolveAuthentication(request);
		}

		headers.forEach(
			entry -> request.headers().add(entry.getKey(), entry.getValue()));

		params.forEach(
			entry -> request.params().add(entry.getKey(), entry.getValue()));

		forms.forEach(
			entry -> request.forms().add(entry.getKey(), entry.getValue()));

		if (convertBody) {
			convertBodyToParams(request);
			request.body(null);
		}

		return request;
	}

	/**
	 * Resolves transport. Throws exception if transport is missing.
	 */
	protected Transport resolveTransport() {
		if (currentTransport == null) {
			currentTransport = DefaultTransport.getDefaultTransport();
		}

		if (currentTransport == null) {
			throw new LaunchpadClientException("Transport not specified!");
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

		return transport.send(request);
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

	protected Auth auth;
	protected Transport currentTransport;
	protected final PodMultiMap<Object> forms = PodMultiMap.newMultiMap();
	protected final PodMultiMap<String> headers = PodMultiMap.newMultiMap();
	protected final PodMultiMap<String> params = PodMultiMap.newMultiMap();
	protected Query.Builder query;
	protected final String url;

	/**
	 * Converts the request body object to query params.
	 */
	private void convertBodyToParams(Request request) {
		String existingContentType = request.header("Content-Type");

		if (existingContentType == null) {
			existingContentType = "application/json";
		}

		final LaunchpadSerializer launchpadSerializer = LaunchpadSerializer.get(
			new ContentType(existingContentType));

		try {
			Map<String, Object> bodyJson = request.bodyValue();

			bodyJson.forEach(
				(name, value) ->
					request.param(name, launchpadSerializer.serialize(value)));
		}
		catch (ClassCastException ignore) {

			// body content is ignored.

		}
	}

	private Query.Builder getOrCreateQuery() {
		if (query == null) {
			query = Query.builder();
		}

		return query;
	}

}