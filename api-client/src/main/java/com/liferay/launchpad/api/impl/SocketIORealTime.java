package com.liferay.launchpad.api.impl;

import com.liferay.launchpad.api.RealTime;

import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.Map;
public class SocketIORealTime extends RealTime {

	public SocketIORealTime(String url, Map<String, Object> options)
		throws URISyntaxException {

		this(new URI(url), fromMap(options));
	}

	@Override
	public void close() {
		socket.close();
	}

	@Override
	public RealTime emit(String event, Object... args) {
		socket.emit(event, args);
		return this;
	}

	@Override
	public RealTime on(String event, Listener fn) {
		socket.on(event, args -> fn.call(args));
		return this;
	}

	@SuppressWarnings("unchecked")
	protected static IO.Options fromMap(Map<String, Object> options) {
		IO.Options opts = new IO.Options();
		opts.forceNew = (boolean)options.getOrDefault("forceNew", false);
		opts.path = (String)options.getOrDefault("path", null);
		return opts;
	}

	private SocketIORealTime(URI uri, IO.Options options) {
		socket = IO.socket(uri, options).connect();
	}

	private Socket socket;

}