package io.cyberflux.meta;

import java.io.Serializable;

public class ResponseResult implements Serializable {
	private Object header;
	private Object payload;
	private Integer code;

	public ResponseResult() {

	}

	public ResponseResult(Object header, Object payload, Integer code) {
		this.header = header;
		this.payload = payload;
		this.code = code;
	}

	public static ResponseResult codeResult(int code) {
		return new ResponseResult(null, null, code);
	}

	public Object getHeader() {
		return header;
	}

	public void setHeader(Object header) {
		this.header = header;
	}

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

}
