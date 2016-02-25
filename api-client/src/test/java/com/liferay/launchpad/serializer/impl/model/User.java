package com.liferay.launchpad.serializer.impl.model;

import com.liferay.launchpad.serializer.Serialize;
public class User {

	public String getName() {
		return name;
	}

	public String getUserId() {
		return userId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	private String name = "John Doe";

	@Serialize(name = "id")
	private String userId = "1Q2W";

	@Override
	public String toString() {
		return userId + ":" + name;
	}
}
