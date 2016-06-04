package com.wedeploy.api.sdk;

import java.util.List;
import java.util.Map;

/**
 * Response wrapper.
 */
public class ResponseWrapper implements Response {

	public ResponseWrapper(Response response) {
		this.response = response;
	}

	@Override
	public String body() {
		return response.body();
	}

	@Override
	public Response body(byte[] body) {
		return response.body(body);
	}

	@Override
	public Response body(Object body) {
		return response.body(body);
	}

	@Override
	public Response body(String body) {
		return response.body(body);
	}

	@Override
	public Response body(String body, ContentType contentType) {
		return response.body(body, contentType);
	}

	@Override
	public byte[] bodyBytes() {
		return response.bodyBytes();
	}

	@Override
	public <T> List<T> bodyList(Class<T> componentType) {
		return response.bodyList(componentType);
	}

	@Override
	public <K, V> Map<K, V> bodyMap(Class<K> keyType, Class<V> valueType) {
		return response.bodyMap(keyType, valueType);
	}

	@Override
	public <V> Map<String, V> bodyMap(Class<V> valueType) {
		return response.bodyMap(valueType);
	}

	@Override
	public <T> T bodyValue() {
		return response.bodyValue();
	}

	@Override
	public <T> T bodyValue(Class<T> type) {
		return response.bodyValue(type);
	}

	@Override
	public String contentType() {
		return response.contentType();
	}

	@Override
	public Response contentType(ContentType contentType) {
		return response.contentType(contentType);
	}

	@Override
	public Context context() {
		return response.context();
	}

	@Override
	public Response cookie(Cookie cookie) {
		return response.cookie(cookie);
	}

	@Override
	public Cookie cookie(String name) {
		return response.cookie(name);
	}

	@Override
	public Map<String, Cookie> cookies() {
		return response.cookies();
	}

	@Override
	public void end() {
		response.end();
	}

	@Override
	public void end(Object body) {
		response.end(body);
	}

	@Override
	public void end(String body) {
		response.end(body);
	}

	@Override
	public void end(String body, ContentType contentType) {
		response.end(body, contentType);
	}

	@Override
	public String header(String name) {
		return response.header(name);
	}

	@Override
	public Response header(String name, String value) {
		return response.header(name, value);
	}

	@Override
	public MultiMap<String> headers() {
		return response.headers();
	}

	@Override
	public boolean isCommitted() {
		return response.isCommitted();
	}

	@Override
	public boolean isContentType(ContentType contentType) {
		return response.isContentType(contentType);
	}

	@Override
	public Response pipe(Response response) {
		return this.response.pipe(response);
	}

	@Override
	public void redirect(String url) {
		response.redirect(url);
	}

	@Override
	public Request request() {
		return response.request();
	}

	@Override
	public Session session() {
		return response.session();
	}

	@Override
	public Response status(int statusCode) {
		return response.status(statusCode);
	}

	@Override
	public Response status(int statusCode, String statusMessage) {
		return response.status(statusCode, statusMessage);
	}

	@Override
	public int statusCode() {
		return response.statusCode();
	}

	@Override
	public String statusMessage() {
		return response.statusMessage();
	}

	@Override
	public boolean succeeded() {
		return response.succeeded();
	}

	protected final Response response;

}