package com.liferay.launchpad.api;

import static org.junit.Assert.assertEquals;

import com.liferay.launchpad.api.model.User;
import com.liferay.launchpad.sdk.ContentType;
import com.liferay.launchpad.sdk.Request;
import com.liferay.launchpad.sdk.Response;

import jodd.json.JsonParser;

import org.junit.Test;
public class JsonTest {

	@Test
	public void testBodyObject() throws Exception {
		User user = new User();
		TestTransport tt = new TestTransport();

		LaunchpadClient
			.url("http://foo.com")
			.use(tt)
			.model(User.class)
			.post(user)
			.join();

		Response respone = tt.getResponse();

		assertUser(user, (User)respone.bodyObject());
	}

	@Test
	public void testSerializeBody_delete() throws Exception {
		User user = new User();
		TestTransport tt = new TestTransport();

		LaunchpadClient
			.url("http://foo.com")
			.use(tt)
			.delete(user)
			.join();

		Request request = tt.getRequest();
		String body = request.body();

		assertEquals(ContentType.JSON.contentType(), request.contentType());
		assertUser(user, deserialize(body, User.class));
	}

	@Test
	public void testSerializeBody_patch() throws Exception {
		User user = new User();
		TestTransport tt = new TestTransport();

		LaunchpadClient
			.url("http://foo.com")
			.use(tt)
			.patch(user)
			.join();

		Request request = tt.getRequest();
		String body = request.body();

		assertEquals(ContentType.JSON.contentType(), request.contentType());
		assertUser(user, deserialize(body, User.class));
	}

	@Test
	public void testSerializeBody_post() throws Exception {
		User user = new User();
		TestTransport tt = new TestTransport();

		LaunchpadClient
			.url("http://foo.com")
			.use(tt)
			.post(user)
			.join();

		Request request = tt.getRequest();
		String body = request.body();

		assertEquals(ContentType.JSON.contentType(), request.contentType());
		assertUser(user, deserialize(body, User.class));
	}

	@Test
	public void testSerializeBody_put() throws Exception {
		User user = new User();
		TestTransport tt = new TestTransport();

		LaunchpadClient
			.url("http://foo.com")
			.use(tt)
			.put(user)
			.join();

		Request request = tt.getRequest();
		String body = request.body();

		assertEquals(ContentType.JSON.contentType(), request.contentType());
		assertUser(user, deserialize(body, User.class));
	}

	@Test
	public void testSerializeParams() throws Exception {
		User user = new User();
		TestTransport tt = new TestTransport();

		LaunchpadClient
			.url("http://foo.com")
			.use(tt)
			.param("user", user)
			.get()
			.join();

		Request request = tt.getRequest();
		String param = request.params().get("user");

		assertUser(user, deserialize(param, User.class));
	}

	private void assertUser(User expected, User actual) {
		assertEquals(expected.getAge(), actual.getAge());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.isValid(), actual.isValid());
	}

	private <T> T deserialize(String json, Class<T> target) {
		return new JsonParser().parse(json, target);
	}

}