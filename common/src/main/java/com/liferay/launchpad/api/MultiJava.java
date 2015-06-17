package com.liferay.launchpad.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)

/**
 * Source-only annotation for classes that exist with the same name in the
 * modules with different Java target.
 */
public @interface MultiJava {
	/**
	 * Java major version.
	 */
	int version();
}