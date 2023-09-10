package io.cyberflux.cloud.core.utils;

import java.io.Serializable;

public class HttpResult implements Serializable{
	private Integer code;
	private Object header;
	private Object payload;

	public HttpResult() {}

	public Integer getCode() {
		return code;
	}
	public HttpResult setCode(Integer code) {
		this.code = code;
		return this;
	}
	public Object getHeader() {
		return header;
	}
	public HttpResult setHeader(Object header) {
		this.header = header;
		return this;
	}
	public Object getPayload() {
		return payload;
	}
	public HttpResult setPayload(Object payload) {
		this.payload = payload;
		return this;
	}
}
