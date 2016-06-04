package com.wedeploy.api.sdk;

import java.util.List;
import java.util.Map;

/**
 * Request wrapper.
 */
public class RequestWrapper implements Request {

	public RequestWrapper(Request request) {
		this.request = request;
	}

	@Override
	public Auth auth() {
		return request.auth();
	}

	@Override
	public String baseUrl() {
		return request.baseUrl();
	}

	@Override
	public String body() {
		return request.body();
	}

	@Override
	public Request body(byte[] body) {
		return request.body(body);
	}

	@Override
	public Request body(Object body) {
		return request.body(body);
	}

	@Override
	public Request body(String body) {
		return request.body(body);
	}

	@Override
	public Request body(String body, ContentType contentType) {
		return request.body(body, contentType);
	}

	@Override
	public byte[] bodyBytes() {
		return request.bodyBytes();
	}

	@Override
	public <T> List<T> bodyList(Class<T> componentType) {
		return request.bodyList(componentType);
	}

	@Override
	public <K, V> Map<K, V> bodyMap(Class<K> keyType, Class<V> valueType) {
		return request.bodyMap(keyType, valueType);
	}

	@Override
	public <V> Map<String, V> bodyMap(Class<V> valueType) {
		return request.bodyMap(valueType);
	}

	@Override
	public <T> T bodyValue() {
		return request.bodyValue();
	}

	@Override
	public <T> T bodyValue(Class<T> type) {
		return request.bodyValue(type);
	}

	@Override
	public String contentType() {
		return request.contentType();
	}

	@Override
	public Request contentType(ContentType contentType) {
		return request.contentType(contentType);
	}

	@Override
	public Context context() {
		return request.context();
	}

	@Override
	public Request cookie(Cookie cookie) {
		return request.cookie(cookie);
	}

	@Override
	public Cookie cookie(String name) {
		return request.cookie(name);
	}

	@Override
	public Map<String, Cookie> cookies() {
		return request.cookies();
	}

	@Override
	public FileUpload[] fileUploads() {
		return request.fileUploads();
	}

	@Override
	public Object form(String name) {
		return request.form(name);
	}

	@Override
	public Request form(String name, Object value) {
		return request.form(name, value);
	}

	@Override
	public MultiMap<Object> forms() {
		return request.forms();
	}

	@Override
	public String header(String name) {
		return request.header(name);
	}

	@Override
	public Request header(String name, String value) {
		return request.header(name, value);
	}

	@Override
	public MultiMap<String> headers() {
		return request.headers();
	}

	@Override
	public boolean isContentType(ContentType contentType) {
		return request.isContentType(contentType);
	}

	@Override
	public String method() {
		return request.method();
	}

	@Override
	public Request method(String method) {
		return request.method(method);
	}

	@Override
	public void next() {
		request.next();
	}

	@Override
	public void next(OnResponseEndCallback onResponseEnd) {
		request.next(onResponseEnd);
	}

	@Override
	public String param(String name) {
		return request.param(name);
	}

	@Override
	public Request param(String name, String value) {
		return request.param(name, value);
	}

	@Override
	public MultiMap<String> params() {
		return request.params();
	}

	@Override
	public String path() {
		return request.path();
	}

	@Override
	public String query() {
		return request.query();
	}

	@Override
	public Response response() {
		return request.response();
	}

	@Override
	public void response(Response response) {
		request.response(response);
	}

	@Override
	public Session session() {
		return request.session();
	}

	@Override
	public String url() {
		return request.url();
	}

	@Override
	public MultiMap<Object> values() {
		return request.values();
	}

	@Override
	public <T> T values(Class<T> type) {
		return request.values(type);
	}

	protected final Request request;

}