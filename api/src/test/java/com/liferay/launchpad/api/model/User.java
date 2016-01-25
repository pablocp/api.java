package com.liferay.launchpad.api.model;
public class User {

	public int getAge() {
		return age;
	}

	public String getName() {
		return name;
	}

	public boolean isValid() {
		return valid;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	private int age = 32;
	private String name = "John";
	private boolean valid = true;

}