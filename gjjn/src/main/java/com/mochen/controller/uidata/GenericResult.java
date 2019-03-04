package com.mochen.controller.uidata;

public class GenericResult<T> {
	private int hr = 0;
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
	
}
