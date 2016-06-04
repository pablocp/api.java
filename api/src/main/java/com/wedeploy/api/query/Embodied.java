package com.wedeploy.api.query;

/**
 * Represents embodied objects.
 */
public interface Embodied {

	Object body();

	default String bodyAsJson() {
		return Util.toString(body());
	}

}