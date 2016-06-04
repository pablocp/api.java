package com.wedeploy.api.serializer.impl.model;
public class Numbers {

	public Long getId1() {
		return id1;
	}

	public long getId2() {
		return id2;
	}

	public void setId1(Long id1) {
		this.id1 = id1;
	}

	public void setId2(long id2) {
		this.id2 = id2;
	}

	@Override
	public String toString() {
		return id1 + ":" + id2;
	}

	protected Long id1 = Long.valueOf(1234567890L);
	protected long id2 = 9087654321L;

}