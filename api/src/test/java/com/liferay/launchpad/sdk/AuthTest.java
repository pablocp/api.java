package com.liferay.launchpad.sdk;

import com.liferay.launchpad.api.Launchpad;
import org.junit.Assert;
import org.junit.Test;
public class AuthTest {

	@Test(expected = UnsupportedOperationException.class)
	public void testConfiguration_throwsUnsupportedOperation() {
		Auth.create("token").config();
	}

	@Test
	public void testGetters_withTokenAuth() {
		Auth auth = Auth.create("token");
		Assert.assertEquals("token", auth.token());
		Assert.assertNull(auth.data());
		Assert.assertNull(auth.id());
		Assert.assertNull(auth.password());
		Assert.assertNull(auth.permissions());
		Assert.assertNull(auth.roles());
		Assert.assertNull(auth.username());
		Assert.assertTrue(auth.hasToken());
		Assert.assertFalse(auth.hasId());
		Assert.assertFalse(auth.hasPassword());
		Assert.assertFalse(auth.hasUsername());
	}

	@Test
	public void testGetters_withUsernamePasswordAuth() {
		Auth auth = Auth.create("user", "password");
		Assert.assertEquals("user", auth.username());
		Assert.assertEquals("password", auth.password());
		Assert.assertNull(auth.data());
		Assert.assertNull(auth.id());
		Assert.assertNull(auth.permissions());
		Assert.assertNull(auth.roles());
		Assert.assertNull(auth.token());
		Assert.assertTrue(auth.hasUsername());
		Assert.assertTrue(auth.hasPassword());
		Assert.assertFalse(auth.hasId());
		Assert.assertFalse(auth.hasToken());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testHasRole_throwsUnsupportedOperation() {
		Auth.create("user", "password").hasRole("role");
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testIsAuthenticated_throwsUnsupportedOperation() {
		Auth.create("user", "password").isAuthenticated();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testIsPermitted_throwsUnsupportedOperation() {
		Auth.create("token").isPermitted("permission");
	}

	@Test
	public void testMaster_withMasterToken() {
		Launchpad.MASTER_TOKEN = "master-token";
		Auth auth = Auth.master();
		Assert.assertEquals("master-token", auth.token());
	}

	@Test(expected = PodException.class)
	public void testMaster_withoutMasterToken() {
		Auth.master();
	}

}