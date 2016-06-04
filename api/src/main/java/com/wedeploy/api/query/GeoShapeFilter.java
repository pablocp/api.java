package com.wedeploy.api.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Geo shape filter.
 */
public final class GeoShapeFilter extends Filter {

	public GeoShapeFilter shape(Object shape) {
		shapes.add(shape);
		return this;
	}

	protected GeoShapeFilter(String field, Object...shapes) {
		super(field, "gs", Util.wrap("type", "geometrycollection"));

		this.shapes = new ArrayList();
		((Map)this.value).put("geometries", this.shapes);

		for (Object shape : shapes) {
			shape(shape);
		}
	}

	private List<Object> shapes;

}