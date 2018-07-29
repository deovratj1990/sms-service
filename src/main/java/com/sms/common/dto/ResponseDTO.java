package com.sms.common.dto;

import java.util.Map;

import com.sms.common.model.StringKeyMap;

public class ResponseDTO extends StringKeyMap {
	public ResponseDTO() {
		setCode(0);
		setMessage("");
		setData(new StringKeyMap());
	}
	
	public Integer getCode() {
		return getInteger("code");
	}

	public void setCode(Integer code) {
		put("code", code);
	}

	public String getMessage() {
		return getString("message");
	}

	public void setMessage(String message) {
		put("message", message);
	}

	public Map<String, ?> getData() {
		return getMap("data");
	}
	
	public void addData(String key, Object value) {
		getMap("data").put(key, value);
	}

	public void setData(Map<String, ?> data) {
		put("data", data);
	}
}
