package com.liferay.launchpad.sdk;

import java.util.List;
import java.util.Map;
public interface AuthConfig {

	public String getHashAlgorithm();

	public String getLoginPageUrl();

	public String getLoginRedirectUrl();

	public String getLoginUrl();

	public String getLogoutRedirectUrl();

	public String getLogoutUrl();

	public String getPasswordParam();

	public String getPermissionsParam();

	public String getRealm();

	public String getRealmRestUrl();

	public Map<String, List> getRoles();

	public String getRolesParam();

	public String getUserParam();

}