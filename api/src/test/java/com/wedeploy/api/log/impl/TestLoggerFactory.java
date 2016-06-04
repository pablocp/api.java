package com.wedeploy.api.log.impl;

import com.wedeploy.api.log.Logger;
import com.wedeploy.api.log.LoggerFactoryInterface;

import java.util.ArrayList;
public class TestLoggerFactory implements LoggerFactoryInterface {

	@Override
	public Logger getLogger(String name) {
		return new TestLogger(name, new ArrayList<>());
	}

}