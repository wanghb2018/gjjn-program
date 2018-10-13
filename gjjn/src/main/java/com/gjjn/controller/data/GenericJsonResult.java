package com.gjjn.controller.data;

public class GenericJsonResult<T> {
	private int hr;
	private T data;

	public int getHr() {
		return hr;
	}

	public void setHr(int hr) {
		this.hr = hr;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public GenericJsonResult(int hr) {
		this.hr = hr;
	}

	public GenericJsonResult(int hr, T data) {
		this.hr = hr;
		this.data = data;
	}

}