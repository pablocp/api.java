package com.liferay.launchpad.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Geo builder.
 */
public final class Geo {

	public static BoundingBox bbox(Object upperLeft, Object lowerRight) {
		return new BoundingBox(upperLeft, lowerRight);
	}

	public static Circle circle(Object center, String radius) {
		return new Circle(center, radius);
	}

	public static Object line(Object...points) {
		Map map = new HashMap();
		map.put("type", "linestring");
		map.put("coordinates", Arrays.asList(points));
		return map;
	}

	public static Object point(double lat, double lon) {
		return Arrays.asList(lon, lat);
	}

	private Geo() { }

	public static Polygon polygon(Object...points) {
		return new Polygon(points);
	}

	public static final class BoundingBox implements Embodied {

		@Override
		public Map body() {
			return body;
		}

		public List<Object> getPoints() {
			return (List)body.get("coordinates");
		}

		protected BoundingBox(Object upperLeft, Object lowerRight) {
			body.put("type", "envelope");
			body.put("coordinates", Arrays.asList(upperLeft, lowerRight));
		}

		private final Map<String, Object> body = new HashMap<>();

	}

	public static final class Circle implements Embodied {

		@Override
		public Map body() {
			return body;
		}

		public Object getCenter() {
			return body.get("coordinates");
		}

		public String getRadius() {
			return (String)body.get("radius");
		}

		protected Circle(Object center, String radius) {
			body.put("type", "circle");
			body.put("coordinates", center);
			body.put("radius", radius);
		}

		private final Map<String, Object> body = new HashMap<>();

	}

	public static final class Polygon implements Embodied {

		@Override
		public Map body() {
			return body;
		}

		public Polygon hole(Object...points) {
			lines.add(Arrays.asList(points));
			return this;
		}

		protected Polygon(Object...points) {
			lines = new ArrayList();
			lines.add(Arrays.asList(points));

			body.put("type", "polygon");
			body.put("coordinates", lines);
		}

		private final Map<String, Object> body = new HashMap();
		private final List lines;

	}

}