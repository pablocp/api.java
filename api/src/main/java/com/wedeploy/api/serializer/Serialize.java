package com.wedeploy.api.serializer;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
public @interface Serialize {

	/**
	 * Specifies if a property should be included or excluded from the JSON serialization.
	 */
	public boolean include() default true;

	/**
	 * Defines different property name for annotated item.
	 * Used both for serialization and parsing.
	 */
	public String name() default "";

	/**
	 * Defines if JSON serialization of a class
	 * works in a <b>strict</b> mode when only
	 * fields with the annotation are serialized.
	 */
	public boolean strict() default false;

}