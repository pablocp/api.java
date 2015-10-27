package com.liferay.launchpad.api;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;

/**
 * Created by Pablo on 10/23/15.
 */
public class LaunchpadRpcController implements RpcController {
	@Override
	public void reset() {
		failureReason = null;
	}

	@Override
	public boolean failed() {
		return failureReason != null;
	}

	@Override
	public String errorText() {
		return failureReason;
	}

	@Override
	public void startCancel() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setFailed(String reason) {
		this.failureReason = reason;
	}

	@Override
	public boolean isCanceled() {
		return false;
	}

	@Override
	public void notifyOnCancel(RpcCallback<Object> callback) {
		throw new UnsupportedOperationException();
	}

	private String failureReason;
}
