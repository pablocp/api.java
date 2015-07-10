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

	public static Line line(Object...points) {
		return new Line(points);
	}

	public static Point point(double lat, double lon) {
		return new Point(lat, lon);
	}

	private Geo() { }

	public static Polygon polygon(Object...points) {
		return new Polygon(points);
	}

	public static final class Point implements Embodied {
		@Override
		public Object body() {
			return body;
		}

		@Override
		public String toString() {
			return Util.toString(body);
		}

		protected Point(double lat, double lon) {
			body = Arrays.asList(lon, lat);
		}

		private List body;
	}

	public static final class Line implements Embodied {
		@Override
		public Object body() {
			return body;
		}

		@Override
		public String toString() {
			return Util.toString(body);
		}

		protected Line(Object... points) {
			body.put("type", "linestring");
			body.put("coordinates", Arrays.asList(points));
		}

		private Map<String, Object> body = new HashMap<>();
	}

	public static final class BoundingBox implements Embodied {

		@Override
		public Map body() {
			return body;
		}

		@Override
		public String toString() {
			return Util.toString(this);
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

		@Override
		public String toString() {
			return Util.toString(this);
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

		@Override
		public String toString() {
			return Util.toString(this);
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