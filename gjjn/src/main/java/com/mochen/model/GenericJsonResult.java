package com.mochen.model;

public class GenericJsonResult<T> {
	private Integer hr = 0;
	private T data;

	public Integer getHr() {
		return hr;
	}

	public void setHr(Integer hr) {
		this.hr = hr;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
