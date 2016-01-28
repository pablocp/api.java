package com.liferay.launchpad.log.impl;

import com.liferay.launchpad.log.PodLogger;
import com.liferay.launchpad.log.PodLoggerFactoryInterface;

import java.util.ArrayList;

public class TestPodLoggerFactory implements PodLoggerFactoryInterface {

	@Override
	public PodLogger getLogger(String name) {
		return new TestPodLogger(name, new ArrayList<>());
	}
}
