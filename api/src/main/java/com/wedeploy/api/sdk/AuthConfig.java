package com.wedeploy.api.sdk;

import java.util.List;
import java.util.Map;
public interface AuthConfig {

	/**
	 * Returns the id of the container that will provide users
	 * for authentication.
	 */
	public String containerAuthenticationProvider();

	/**
	 * Returns the hash algorithm for passwords in login requests.
	 * Users in {@link AuthConfig#containerAuthenticationProvider}
	 * must have passwords hashed by this same algorithm.
	 */
	public String hashAlgorithm();

	/**
	 * Returns the login url.
	 * The server will expect post requests with parameters
	 * {@link AuthConfig#userParam} and
	 * {@link AuthConfig#passwordParam}.
	 */
	public String loginAuthenticationUrl();

	/**
	 * Returns the login page url.
	 * All unauthenticated requests to authenticated paths will be
	 * redirected to this page, if value set.
	 */
	public String loginPageUrl();

	/**
	 * Returns the login redirect url.
	 * All login requests without return page will be redirected to this
	 * page, if value set.
	 */
	public String loginRedirectPageUrl();

	/**
	 * Returns the logout url.
	 * Any request to this url will destroy current session.
	 */
	public String logoutAuthenticationUrl();

	/**
	 * Returns the logout redirect url.
	 * All logout requests will be redirected to this page, if value set.
	 */
	public String logoutRedirectPageUrl();

	/**
	 * Returns the password parameter expected in
	 * {@link AuthConfig#loginAuthenticationUrl} and
	 * {@link AuthConfig#containerAuthenticationProvider}.
	 */
	public String passwordParam();

	/**
	 * Returns the permissions field expected in
	 * {@link AuthConfig#containerAuthenticationProvider}.
	 */
	public String permissionsParam();

	/**
	 * Returns the realm name.
	 */
	public String realm();

	/**
	 * Returns the roles for this application.
	 * Each role is composed by a list of permissions.
	 */
	public Map<String, List> roles();

	/**
	 * Returns the roles field expected in
	 * {@link AuthConfig#containerAuthenticationProvider}.
	 */
	public String rolesParam();

	/**
	 * Returns the username parameter expected in
	 * {@link AuthConfig#loginAuthenticationUrl} and
	 * {@link AuthConfig#containerAuthenticationProvider}.
	 */
	public String userParam();

}