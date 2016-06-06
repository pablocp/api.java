package com.wedeploy.api.log.impl;

import com.wedeploy.api.log.Logger;
import com.wedeploy.api.log.LoggerFactoryInterface;
public class LoggerFactory implements LoggerFactoryInterface {

	@Override
	public Logger getLogger(String name) {
		return new Slf4jLogger(name);
	}

}