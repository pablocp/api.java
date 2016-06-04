package com.wedeploy.api;

import com.wedeploy.api.query.Aggregation;
import com.wedeploy.api.query.Filter;
import com.wedeploy.api.query.Query;
import com.wedeploy.api.realtime.RealTime;
import com.wedeploy.api.sdk.Auth;
import com.wedeploy.api.sdk.ContentType;
import com.wedeploy.api.sdk.Cookie;
import com.wedeploy.api.sdk.MultiMap;
import com.wedeploy.api.sdk.Request;
import com.wedeploy.api.sdk.RequestImpl;
import com.wedeploy.api.sdk.Response;
import com.wedeploy.api.serializer.Serializer;
import com.wedeploy.api.transport.impl.DefaultTransport;
import com.wedeploy.api.transport.impl.Transport;
import com.wedeploy.api.util.Util;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Java client.
 */
public class WeDeploy {

	public static String DOMAIN;

	public static String MASTER_TOKEN;

	public static final String METHOD_DELETE = "DELETE";

	public static final String METHOD_GET = "GET";

	public static final String METHOD_PATCH = "PATCH";

	public static final String METHOD_POST = "POST";

	public static final String METHOD_PUT = "PUT";

	/**
	 * Static factory for creating WeDeploy client.
	 */
	public static WeDeploy container(String containerId) {
		if (DOMAIN == null) {
			return new WeDeploy("/");
		}

		return new WeDeploy(containerId + '.' + DOMAIN);
	}

	/**
	 * Static factory for creating WeDeploy client.
	 */
	public static WeDeploy url(String url) {
		return new WeDeploy(url);
	}

	/**
	 * Main constructor.
	 */
	public WeDeploy(String url) {
		this.url = url;
	}

	/**
	 * See {@link Query.Builder}
	 */
	public WeDeploy aggregate(Aggregation aggregation) {
		getOrCreateQuery().aggregate(aggregation);
		return this;
	}

	/**
	 * See {@link Query.Builder}
	 */
	public WeDeploy aggregate(String name, String field, String operator) {
		return aggregate(Aggregation.of(name, field, operator));
	}

	/**
	 * Authenticates with an auth instance.
	 */
	public WeDeploy auth(Auth auth) {
		this.auth = auth;
		return this;
	}

	/**
	 * Authenticates with a token.
	 */
	public WeDeploy auth(String token) {
		auth(Auth.create(token));
		return this;
	}

	/**
	 * Authenticates with username and password.
	 */
	public WeDeploy auth(String username, String password) {
		auth(Auth.create(username, password));
		return this;
	}

	/**
	 * Sets the content type header of the request.
	 * @param contentType
	 */
	public WeDeploy contentType(ContentType contentType) {
		header("Content-Type", contentType.toString());
		return this;
	}

	public WeDeploy cookie(Cookie cookie) {
		this.cookies.add(cookie);
		return this;
	}

	/**
	 * See {@link Query.Builder}
	 */
	public WeDeploy count() {
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
	 * See {@link Query.Builder}
	 */
	public WeDeploy filter(Filter filter) {
		getOrCreateQuery().filter(filter);
		return this;
	}

	/**
	 * See {@link Query.Builder}
	 */
	public WeDeploy filter(String field, Object value) {
		return filter(Filter.field(field, value));
	}

	/**
	 * See {@link Query.Builder}
	 */
	public WeDeploy filter(String field, String operator, Object value) {
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
	public WeDeploy form(String name, Object value) {
		forms.set(name, value);
		return this;
	}

	/**
	 * Returns the form parameter.
	 */
	public MultiMap<Object> forms() {
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
	public WeDeploy header(String name, String value) {
		headers.set(name, value);
		return this;
	}

	/**
	 * See {@link Query.Builder}
	 */
	public WeDeploy highlight(String field) {
		getOrCreateQuery().highlight(field);
		return this;
	}

	/**
	 * See {@link Query.Builder}
	 */
	public WeDeploy limit(int limit) {
		getOrCreateQuery().limit(limit);
		return this;
	}

	/**
	 * See {@link Query.Builder}
	 */
	public WeDeploy offset(int offset) {
		getOrCreateQuery().offset(offset);
		return this;
	}

	/**
	 * Serializes an object using JSON serializer and sets query parameter.
	 */
	public WeDeploy param(String name, Object value) {
		String valueString = null;

		if (value != null) {
			Serializer serializer = Serializer.get(ContentType.JSON);

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
	public WeDeploy param(String name, String value) {
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
	 * Creates new {@link WeDeploy}.
	 */
	public WeDeploy path(String path) {
		return new WeDeploy(url, path)
			.use(currentTransport)
			.auth(auth);
	}

	/**
	 * Creates new {@link WeDeploy}.
	 */
	public WeDeploy path(String... paths) {
		WeDeploy instance = this;

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
	 * See {@link Query.Builder}
	 */
	public WeDeploy search(Filter filter) {
		getOrCreateQuery().search(filter);
		return this;
	}

	/**
	 * See {@link Query.Builder}
	 */
	public WeDeploy search(String text) {
		return search(Filter.match(text));
	}

	/**
	 * See {@link Query.Builder}
	 */
	public WeDeploy search(String field, String text) {
		return search(Filter.match(field, text));
	}

	/**
	 * See {@link Query.Builder}
	 */
	public WeDeploy search(String field, String operator, Object value) {
		return search(Filter.field(field, operator, value));
	}

	/**
	 * See {@link Query.Builder}
	 */
	public WeDeploy sort(String field) {
		return sort(field, "asc");
	}

	/**
	 * See {@link Query.Builder}
	 */
	public WeDeploy sort(String field, String direction) {
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
	public WeDeploy use(Transport transport) {
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

		String query = Util.addParametersToQueryString(
			clientRequest.query(), clientRequest.params());

		String clientUrl = Util.joinPathAndQuery(clientRequest.path(), query);

		Map<String, String> optionsQuery = new HashMap<>();
		optionsQuery.put("url", clientUrl);

		if (options == null) {
			options = new HashMap<>();
		}

		options.putIfAbsent("forceNew", true);
		options.putIfAbsent("path", clientRequest.path());
		options.putIfAbsent("query", optionsQuery);

		try {
			return RealTime.io(clientRequest.baseUrl(), options);
		}
		catch (NullPointerException npe) {
			throw new WeDeployException("Socket.io client not loaded");
		}
		catch (Exception e) {
			throw new WeDeployException(e.getMessage());
		}
	}

	/**
	 * Continuations constructor, used from existing instance, therefore
	 * no need to configure the client.
	 */
	protected WeDeploy(String baseUrl, String url) {
		this.url = Util.joinPaths(baseUrl, url);
	}

	/**
	 * Resolves authentication.
	 */
	protected void resolveAuthentication(Request request) {

		// uses session or basic

		if (auth.hasSessionToken()) {
			request.cookie(Cookie.cookie("sessionToken", auth.sessionToken()));
		}
		else if (auth.hasUsername() && auth.hasPassword()) {
			String credentials = auth.username() + ":" + auth.password();

			request.header(
				"Authorization",
				"Basic " +
					Base64.getEncoder().encodeToString(credentials.getBytes()));
		}

		// overrides with token, if any present

		if (auth.hasToken()) {
			request.header("Authorization", "Bearer " + auth.token());
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

		final Serializer serializer;

		String existingContentType = headers.get("Content-Type");

		if (existingContentType == null) {
			headers.set("Content-Type", ContentType.JSON.toString());

			serializer = Serializer.get(ContentType.JSON);
		}
		else {
			serializer = Serializer.get(new ContentType(existingContentType));
		}

		// TODO(igor): Move api query checking outside?

		Package pkg = body.getClass().getPackage();

		if (pkg != null) {
			switch (pkg.getName()) {
				case "com.wedeploy.api.query":
					return body.toString();
			}
		}

		return serializer.serialize(body);
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

		cookies.forEach(request::cookie);

		headers.forEach(
			entry -> request.headers().add(entry.getKey(), entry.getValue()));

		params.forEach(
			entry -> request.params().add(entry.getKey(), entry.getValue()));

		forms.forEach(
			entry -> request.forms().add(entry.getKey(), entry.getValue()));

		if (convertBody) {
			convertBodyToParams(request);
			request.body((byte[])null);
		}

		return request;
	}

	/**
	 * Resolves transport. Throws exception if transport is missing.
	 */
	protected Transport resolveTransport() {
		if (currentTransport == null) {
			currentTransport = DefaultTransport.defaultTransport();
		}

		if (currentTransport == null) {
			throw new WeDeployException("Transport not specified!");
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
	protected final List<Cookie> cookies = new ArrayList<>();
	protected Transport currentTransport;
	protected final MultiMap<Object> forms = MultiMap.newMultiMap();
	protected final MultiMap<String> headers = MultiMap.newMultiMap();
	protected final MultiMap<String> params = MultiMap.newMultiMap();
	protected Query.Builder query;
	protected final String url;

	/**
	 * Converts the request body object to query params.
	 */
	private void convertBodyToParams(Request request) {
		ContentType contentType = ContentType.JSON;
		String existingContentType = request.header("Content-Type");

		if (existingContentType != null) {
			contentType = new ContentType(existingContentType);
		}

		final Serializer serializer = Serializer.get(contentType);

		request.forms().forEach(
			entry -> request.param(
				entry.getKey(), serializer.serialize(entry.getValue())));

		try {
			Map<String, Object> bodyJson = request.bodyValue();

			bodyJson.forEach(
				(name, value) ->
					request.param(name, serializer.serialize(value)));
		}
		catch (NullPointerException | ClassCastException e) {

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