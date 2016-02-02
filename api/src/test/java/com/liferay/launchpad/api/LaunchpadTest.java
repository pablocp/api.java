package com.liferay.launchpad.api;

import com.liferay.launchpad.api.impl.TestTransport;
import com.liferay.launchpad.query.Aggregation;
import com.liferay.launchpad.query.Filter;
import com.liferay.launchpad.query.Query;
import com.liferay.launchpad.sdk.AuthImpl;
import com.liferay.launchpad.sdk.ContentType;
import com.liferay.launchpad.sdk.Cookie;
import com.liferay.launchpad.sdk.PodMultiMapFactory;
import com.liferay.launchpad.sdk.Request;
import com.liferay.launchpad.sdk.Response;
import com.liferay.launchpad.sdk.TestPodMultiMap;
import com.liferay.launchpad.serializer.Engines;
import com.liferay.launchpad.serializer.LaunchpadSerializerEngine;
import com.liferay.launchpad.serializer.impl.JsonLaunchpadParser;
import com.liferay.launchpad.serializer.impl.JsonLaunchpadSerializer;

import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
public class LaunchpadTest {

	@BeforeClass
	public static void setup() {
		PodMultiMapFactory.Default.factory = TestPodMultiMap::new;
		LaunchpadSerializerEngine.instance().registerEngines(
			ContentType.JSON.contentType(),
			new Engines(
				new JsonLaunchpadSerializer(), new JsonLaunchpadParser()),
			true);
	}

	@Test
	public void testAggregate_withAggregation() {
		Launchpad.url("url")
			.use(new TestTransport() {
				@Override
				public Response send(Request request) {
					Assert.assertNotNull(request.body());
					Assert.assertTrue(
						request.body().contains(
							Aggregation.count("name", "field").toString()));
					return super.send(request);
				}

			})
			.aggregate(Aggregation.count("name", "field"))
			.get();
	}

	@Test
	public void testAggregate_withParams() {
		Launchpad.url("url")
			.use(new TestTransport() {
				@Override
				public Response send(Request request) {
					Assert.assertNotNull(request.body());
					Assert.assertTrue(
						request.body().contains(
							Aggregation.of("name", "field", "op").toString()));
					return super.send(request);
				}

			})
			.aggregate("name", "field", "op")
			.get();
	}

	@Test
	public void testAuth_withAuth() {
		Launchpad.url("url")
			.use(new TestTransport() {
				@Override
				public Response send(Request request) {
					Assert.assertEquals(
						"token", request.cookie("token").value());
					return super.send(request);
				}

			})
			.auth(new AuthImpl("token"))
			.get();
	}

	@Test
	public void testAuth_withTokenString() {
		Launchpad.url("url")
			.use(new TestTransport() {
				@Override
				public Response send(Request request) {
					Assert.assertEquals(
						"token", request.cookie("token").value());
					return super.send(request);
				}

			})
			.auth("token")
			.get();
	}

	@Test
	public void testAuth_withUserAndPassword() {
		Launchpad.url("url")
			.use(new TestTransport() {
				@Override
				public Response send(Request request) {
					Assert.assertEquals(
						"Basic " + Base64.getEncoder().encodeToString(
							"user:pass".getBytes()),
						request.header("Authorization"));
					return super.send(request);
				}

			})
			.auth("user", "pass")
			.get();
	}

	@Test
	public void testContentType_withContentType() {
		Launchpad.url("url")
			.use(new TestTransport() {
				@Override
				public Response send(Request request) {
					Assert.assertEquals(
						ContentType.TEXT.toString(),
						request.header("Content-Type"));
					return super.send(request);
				}

			})
			.contentType(ContentType.TEXT)
			.get();
	}

	@Test
	public void testCookie() {
		Cookie cookie = Cookie.cookie("key", "value");

		Launchpad.url("url")
			.use(new TestTransport() {
				@Override
				public Response send(Request request) {
					Assert.assertEquals(cookie, request.cookie("key"));
					return super.send(request);
				}

			})
			.cookie(cookie)
			.get();
	}

	@Test
	public void testCount() {
		Launchpad.url("url")
			.use(new TestTransport() {
				@Override
				public Response send(Request request) {
					Assert.assertNotNull(request.body());
					Assert.assertTrue(request.body().contains(
						Query.count().toString()));
					return super.send(request);
				}

			})
			.count()
			.get();
	}

	@Test
	public void testDefaultTransport_constructorDummyCoverage() {
		new DefaultTransport();
	}

	@Test
	public void testDelete() {
		Launchpad.url("url").use(getTransportFor("DELETE", null)).delete();
	}

	@Test
	public void testDelete_withObjectBody() {
		Launchpad.url("url").use(getTransportFor("DELETE", "10")).delete(10);
	}

	@Test
	public void testDelete_withStringBody() {
		Launchpad.url("url").use(getTransportFor("DELETE", "a")).delete("a");
	}

	@Test
	public void testDeleteAsync() {
		Launchpad.url("url")
			.use(getTransportFor("DELETE", null))
			.deleteAsync()
			.thenAccept(response -> Assert.assertTrue(response.succeeded()))
			.exceptionally(exception -> {
				Assert.fail(exception.getMessage());
				return null;
			});
	}

	@Test
	public void testDeleteAsync_withObjectBody() {
		Launchpad.url("url")
			.use(getTransportFor("DELETE", "10"))
			.deleteAsync(10)
			.thenAccept(response -> Assert.assertTrue(response.succeeded()))
			.exceptionally(exception -> {
				Assert.fail(exception.getMessage());
				return null;
			});
	}

	@Test
	public void testDeleteAsync_withStringBody() {
		Launchpad.url("url")
			.use(getTransportFor("DELETE", "a"))
			.deleteAsync("a")
			.thenAccept(response -> Assert.assertTrue(response.succeeded()))
			.exceptionally(exception -> {
				Assert.fail(exception.getMessage());
				return null;
			});
	}

	@Test
	public void testFilter_withFieldAndValue() {
		Launchpad.url("url")
			.use(new TestTransport() {
				@Override
				public Response send(Request request) {
					Assert.assertNotNull(request.body());
					Assert.assertTrue(
						request.body().contains(
							Filter.field("field", "value").toString()));
					return super.send(request);
				}

			})
			.filter("field", "value")
			.get();
	}

	@Test
	public void testFilter_withFieldOperatorAndValue() {
		Launchpad.url("url")
			.use(new TestTransport() {
				@Override
				public Response send(Request request) {
					Assert.assertNotNull(request.body());
					Assert.assertTrue(
						request.body().contains(
							Filter.field("field", "op", "value").toString()));
					return super.send(request);
				}

			})
			.filter("field", "op", "value")
			.get();
	}

	@Test
	public void testFilter_withFilter() {
		Launchpad.url("url")
			.use(new TestTransport() {
				@Override
				public Response send(Request request) {
					Assert.assertNotNull(request.body());
					Assert.assertTrue(
						request.body().contains(
							Filter.field("field", "value").toString()));
					return super.send(request);
				}

			})
			.filter(Filter.field("field", "value"))
			.get();
	}

	@Test
	public void testForm() {
		Launchpad launchpad = Launchpad.url("url")
			.use(new TestTransport() {
				@Override
				public Response send(Request request) {
					Assert.assertEquals("str", request.form("string"));
					Assert.assertEquals(1, request.form("int"));
					Assert.assertEquals(
						0.5, (double)request.form("double"), 1e-8);
					Assert.assertEquals(false, request.form("boolean"));
					Assert.assertNull(request.form("null"));
					return super.send(request);
				}

			})
			.form("string", "str")
			.form("int", 1)
			.form("double", 0.5)
			.form("boolean", false)
			.form("null", null);

		Assert.assertEquals("str", launchpad.forms().get("string"));
		Assert.assertEquals(1, launchpad.form("int"));
		Assert.assertEquals(0.5, (double)launchpad.form("double"), 1e-8);
		Assert.assertEquals(false, launchpad.form("boolean"));

		launchpad.get();
	}

	@Test
	public void testForm_withOverride() {
		Launchpad.url("url")
			.use(new TestTransport() {
				@Override
				public Response send(Request request) {
					Assert.assertEquals(2, request.form("key"));
					return super.send(request);
				}

			})
			.form("key", 1)
			.form("key", 2)
			.get();
	}

	@Test
	public void testGet() {
		Launchpad.url("url").use(getTransportFor("GET", null)).get();
	}

	@Test
	public void testGet_withObjectBody() {
		Launchpad.url("url").use(getTransportFor("GET", "10")).get(10);
	}

	@Test
	public void testGet_withStringBody() {
		Launchpad.url("url").use(getTransportFor("GET", "a")).get("a");
	}

	@Test
	public void testGetAsync() {
		Launchpad.url("url")
			.use(getTransportFor("GET", null))
			.getAsync()
			.thenAccept(response -> Assert.assertTrue(response.succeeded()))
			.exceptionally(exception -> {
				Assert.fail(exception.getMessage());
				return null;
			});
	}

	@Test
	public void testGetAsync_withObjectBody() {
		Launchpad.url("url")
			.use(getTransportFor("GET", "10"))
			.getAsync(10)
			.thenAccept(response -> Assert.assertTrue(response.succeeded()))
			.exceptionally(exception -> {
				Assert.fail(exception.getMessage());
				return null;
			});
	}

	@Test
	public void testGetAsync_withStringBody() {
		Launchpad.url("url")
			.use(getTransportFor("GET", "a"))
			.getAsync("a")
			.thenAccept(response -> Assert.assertTrue(response.succeeded()))
			.exceptionally(exception -> {
				Assert.fail(exception.getMessage());
				return null;
			});
	}

	@Test
	public void testHeader() {
		Launchpad.url("url")
			.use(new TestTransport() {
				@Override
				public Response send(Request request) {
					Assert.assertEquals("value", request.header("key"));
					return super.send(request);
				}

			})
			.header("key", "value")
			.get();
	}

	@Test
	public void testHighlight() {
		Launchpad.url("url")
			.use(new TestTransport() {
				@Override
				public Response send(Request request) {
					Assert.assertNotNull(request.body());
					Assert.assertTrue(
						request.body().contains(
							Query.highlight("field").toString()));
					return super.send(request);
				}

			})
			.highlight("field")
			.get();
	}

	@Test
	public void testLimit() {
		Launchpad.url("url")
			.use(new TestTransport() {
				@Override
				public Response send(Request request) {
					Assert.assertNotNull(request.body());
					Assert.assertTrue(
						request.body().contains(Query.limit(1).toString()));
					return super.send(request);
				}

			})
			.limit(1)
			.get();
	}

	@Test
	public void testOffset() {
		Launchpad.url("url")
			.use(new TestTransport() {
				@Override
				public Response send(Request request) {
					Assert.assertNotNull(request.body());
					Assert.assertTrue(
						request.body().contains(Query.offset(1).toString()));
					return super.send(request);
				}

			})
			.offset(1)
			.get();
	}

	@Test
	public void testParam() {
		Launchpad.url("url")
			.use(new TestTransport() {
				@Override
				public Response send(Request request) {
					Assert.assertEquals("str", request.param("string"));
					Assert.assertEquals("1", request.param("int"));
					Assert.assertEquals("0.5", request.param("double"));
					Assert.assertEquals("false", request.param("boolean"));
					Assert.assertNull(request.param("null"));
					return super.send(request);
				}

			})
			.param("string", "str")
			.param("int", 1)
			.param("double", 0.5)
			.param("boolean", false)
			.param("null", (Object)null)
			.get();
	}

	@Test
	public void testParam_withOverride() {
		Launchpad.url("url")
			.use(new TestTransport() {
				@Override
				public Response send(Request request) {
					Assert.assertEquals("2", request.param("key"));
					return super.send(request);
				}

			})
			.param("key", 1)
			.param("key", 2)
			.get();
	}

	@Test
	public void testPatch() {
		Launchpad.url("url").use(getTransportFor("PATCH", null)).patch();
	}

	@Test
	public void testPatch_withObjectBody() {
		Launchpad.url("url").use(getTransportFor("PATCH", "10")).patch(10);
	}

	@Test
	public void testPatch_withStringBody() {
		Launchpad.url("url").use(getTransportFor("PATCH", "a")).patch("a");
	}

	@Test
	public void testPatchAsync() {
		Launchpad.url("url")
			.use(getTransportFor("PATCH", null))
			.patchAsync()
			.thenAccept(response -> Assert.assertTrue(response.succeeded()))
			.exceptionally(exception -> {
				Assert.fail(exception.getMessage());
				return null;
			});
	}

	@Test
	public void testPatchAsync_withObjectBody() {
		Launchpad.url("url")
			.use(getTransportFor("PATCH", "10"))
			.patchAsync(10)
			.thenAccept(response -> Assert.assertTrue(response.succeeded()))
			.exceptionally(exception -> {
				Assert.fail(exception.getMessage());
				return null;
			});
	}

	@Test
	public void testPatchAsync_withStringBody() {
		Launchpad.url("url")
			.use(getTransportFor("PATCH", "a"))
			.patchAsync("a")
			.thenAccept(response -> Assert.assertTrue(response.succeeded()))
			.exceptionally(exception -> {
				Assert.fail(exception.getMessage());
				return null;
			});
	}

	@Test
	public void testPath() {
		Launchpad.url("url")
			.use(new TestTransport() {
				@Override
				public Response send(Request request) {
					Assert.assertEquals("url/1/2/3/4", request.url());
					return super.send(request);
				}

			})
			.path("1", "/2", "3/")
			.path("4/")
			.get();
	}

	@Test
	public void testPost() {
		Launchpad.url("url").use(getTransportFor("POST", null)).post();
	}

	@Test
	public void testPost_withObjectBody() {
		Launchpad.url("url").use(getTransportFor("POST", "10")).post(10);
	}

	@Test
	public void testPost_withStringBody() {
		Launchpad.url("url").use(getTransportFor("POST", "a")).post("a");
	}

	@Test
	public void testPostAsync() {
		Launchpad.url("url")
			.use(getTransportFor("POST", null))
			.postAsync()
			.thenAccept(response -> Assert.assertTrue(response.succeeded()))
			.exceptionally(exception -> {
				Assert.fail(exception.getMessage());
				return null;
			});
	}

	@Test
	public void testPostAsync_withObjectBody() {
		Launchpad.url("url")
			.use(getTransportFor("POST", "10"))
			.postAsync(10)
			.thenAccept(response -> Assert.assertTrue(response.succeeded()))
			.exceptionally(exception -> {
				Assert.fail(exception.getMessage());
				return null;
			});
	}

	@Test
	public void testPostAsync_withStringBody() {
		Launchpad.url("url")
			.use(getTransportFor("POST", "a"))
			.postAsync("a")
			.thenAccept(response -> Assert.assertTrue(response.succeeded()))
			.exceptionally(exception -> {
				Assert.fail(exception.getMessage());
				return null;
			});
	}

	@Test
	public void testPut() {
		Launchpad.url("url").use(getTransportFor("PUT", null)).put();
	}

	@Test
	public void testPut_withObjectBody() {
		Launchpad.url("url").use(getTransportFor("PUT", "10")).put(10);
	}

	@Test
	public void testPut_withStringBody() {
		Launchpad.url("url").use(getTransportFor("PUT", "a")).put("a");
	}

	@Test
	public void testPutAsync() {
		Launchpad.url("url")
			.use(getTransportFor("PUT", null))
			.putAsync()
			.thenAccept(response -> Assert.assertTrue(response.succeeded()))
			.exceptionally(exception -> {
				Assert.fail(exception.getMessage());
				return null;
			});
	}

	@Test
	public void testPutAsync_withObjectBody() {
		Launchpad.url("url")
			.use(getTransportFor("PUT", "10"))
			.putAsync(10)
			.thenAccept(response -> Assert.assertTrue(response.succeeded()))
			.exceptionally(exception -> {
				Assert.fail(exception.getMessage());
				return null;
			});
	}

	@Test
	public void testPutAsync_withStringBody() {
		Launchpad.url("url")
			.use(getTransportFor("PUT", "a"))
			.putAsync("a")
			.thenAccept(response -> Assert.assertTrue(response.succeeded()))
			.exceptionally(exception -> {
				Assert.fail(exception.getMessage());
				return null;
			});
	}

	@Test
	public void testRealTimeFactoryDefault_constructorDummyCoverage() {
		new RealTimeFactory.Default();
	}

	@Test
	public void testSearch_withFieldAndText() {
		Launchpad.url("url")
			.use(new TestTransport() {
				@Override
				public Response send(Request request) {
					Assert.assertNotNull(request.body());
					Assert.assertTrue(
						request.body().contains(
							Query.search("field", "query").toString()));
					return super.send(request);
				}

			})
			.search("field", "query")
			.get();
	}

	@Test
	public void testSearch_withFieldOperatorAndText() {
		Launchpad.url("url")
			.use(new TestTransport() {
				@Override
				public Response send(Request request) {
					Assert.assertNotNull(request.body());
					Assert.assertTrue(
						request.body().contains(
							Query.search("field", "op", "query").toString()));
					return super.send(request);
				}

			})
			.search("field", "op", "query")
			.get();
	}

	@Test
	public void testSearch_withFilter() {
		Launchpad.url("url")
			.use(new TestTransport() {
				@Override
				public Response send(Request request) {
					Assert.assertNotNull(request.body());
					Assert.assertTrue(
						request.body().contains(
							Query.search(
								Filter.field("field", "value")).toString()));
					return super.send(request);
				}

			})
			.search(Filter.field("field", "value"))
			.get();
	}

	@Test
	public void testSearch_withText() {
		Launchpad.url("url")
			.use(new TestTransport() {
				@Override
				public Response send(Request request) {
					Assert.assertNotNull(request.body());
					Assert.assertTrue(
						request.body().contains(
							Query.search("query").toString()));
					return super.send(request);
				}

			})
			.search("query")
			.get();
	}

	@Test
	public void testSend_withContentTypeSet() {
		Launchpad.url("url")
			.use(new TestTransport() {
				@Override
				public Response send(Request request) {
					Assert.assertEquals("1", request.body());
					return super.send(request);
				}

			})
			.contentType(ContentType.JSON)
			.send("GET", 1);
	}

	@Test
	public void testSend_withNullBody() {
		Launchpad.url("url")
			.use(new TestTransport() {
				@Override
				public Response send(Request request) {
					Assert.assertNull(request.body());
					return super.send(request);
				}

			})
			.send("GET", (Object)null);
	}

	@Test
	public void testSend_withoutTransport() {
		DefaultTransport.defaultTransport(null);

		try {
			Launchpad.url("url").use(null).send("GET", 1);
			Assert.fail("Exception not thrown.");
		}
		catch (LaunchpadClientException e) {
			Assert.assertEquals("Transport not specified!", e.getMessage());
		}
	}

	@Test
	public void testSort_withField() {
		Launchpad.url("url")
			.use(new TestTransport() {
				@Override
				public Response send(Request request) {
					Assert.assertNotNull(request.body());
					Assert.assertTrue(
						request.body().contains(
							Query.sort("field").toString()));
					return super.send(request);
				}

			})
			.sort("field")
			.get();
	}

	@Test
	public void testSort_withFieldAndDirection() {
		Launchpad.url("url")
			.use(new TestTransport() {
				@Override
				public Response send(Request request) {
					Assert.assertNotNull(request.body());
					Assert.assertTrue(
						request.body().contains(
							Query.sort("field", "asc").toString()));
					return super.send(request);
				}

			})
			.sort("field", "asc")
			.get();
	}

	@Test
	public void testWatch() {
		RealTimeFactory.Default.factory = getRealTimeTestFactory(
			"http://url", true, "", "");

		Launchpad.url("url").watch();
	}

	@Test
	public void testWatch_withBodyObject() {
		RealTimeFactory.Default.factory = getRealTimeTestFactory(
			"http://url", true, "", "?key=%22value%22");

		Map<String, Object> body = new HashMap<>();
		body.put("key", "value");
		Launchpad.url("url").watch(body);
	}

	@Test
	public void testWatch_withBodyObjectAndOptions() {
		RealTimeFactory.Default.factory = getRealTimeTestFactory(
			"http://url", false, "/path", null);

		Map<String, Object> body = new HashMap<>();
		body.put("key", "value");

		Map<String, Object> options = new HashMap<>();
		options.put("forceNew", false);
		options.put("path", "/path");
		options.put("query", Collections.emptyMap());

		Launchpad.url("url").watch(body, options);
	}

	@Test
	public void testWatch_withBodyString() {
		RealTimeFactory.Default.factory = getRealTimeTestFactory(
			"http://url", true, "", "?key=%22value%22");

		Launchpad.url("url")
			.contentType(ContentType.JSON)
			.watch("{\"key\":\"value\"}");
	}

	@Test
	public void testWatch_withBodyStringAndOptions() {
		RealTimeFactory.Default.factory = getRealTimeTestFactory(
			"http://url", false, "/path", null);

		Map<String, Object> options = new HashMap<>();
		options.put("forceNew", false);
		options.put("path", "/path");
		options.put("query", Collections.emptyMap());

		Launchpad.url("url")
			.contentType(ContentType.JSON)
			.watch("{\"key\":\"value\"}", options);
	}

	@Test
	public void testWatch_withFactoryException() {
		RealTimeFactory.Default.factory = (url, options) -> {
			throw new RuntimeException("Error");
		};

		try {
			Launchpad.url("url").watch();
			Assert.fail("Exception not thrown.");
		}
		catch (LaunchpadClientException e) {
			Assert.assertEquals("Error", e.getMessage());
		}
	}

	@Test
	public void testWatch_withFullRequest() {
		RealTimeFactory.Default.factory = getRealTimeTestFactory(
			"http://url", true, "/test/path",
			"/test/path?a=b&c=d&e=%22f%22&g=%22h%22");

		Launchpad.url("url/test/path?a=b")
			.param("c", "d")
			.form("e", "f")
			.contentType(ContentType.JSON)
			.watch("{\"g\":\"h\"}");
	}

	@Test
	public void testWatch_withoutIOFactory() {
		RealTimeFactory.Default.factory = null;

		try {
			Launchpad.url("url").watch();
			Assert.fail("Exception not thrown.");
		}
		catch (LaunchpadClientException e) {
			Assert.assertEquals("Socket.io client not loaded", e.getMessage());
		}
	}

	private RealTimeFactory getRealTimeTestFactory(
		String expectedUrl, boolean expectedForce, String expectedPath,
		String expectedQuery) {

		return (url, options) -> {
			Assert.assertEquals(expectedUrl, url);
			Assert.assertEquals(expectedForce, options.get("forceNew"));
			Assert.assertEquals(expectedPath, options.get("path"));
			Assert.assertEquals(
				expectedQuery, ((Map)options.get("query")).get("url"));

			return new DummyRealTime();
		};
	}

	private Transport getTransportFor(String method, String body) {
		return new TestTransport() {

			@Override
			public Response send(Request request) {
				Assert.assertEquals(method.toUpperCase(), request.method());
				Assert.assertEquals(body, request.body());
				return super.send(request);
			}

		};
	}

	private class DummyRealTime extends RealTime {

		@Override
		public RealTime on(String event, Listener fn) {return null; }

		@Override
		public void close() {
		}

		@Override
		public RealTime emit(String event, Object... args) {return null; }
	}

}