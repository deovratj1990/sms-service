package com.sms.common.dto;

import java.util.Map;

import com.sms.common.model.StringKeyMap;

public class ResponseDTO extends StringKeyMap {
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

	public Map<String, Object> getData() {
		return getMap("data");
	}
	
	public void addData(String key, Object value) {
		getMap("data").put(key, value);
	}

	public void setData(Map<String, ? extends Object> data) {
		put("data", data);
	}
}
