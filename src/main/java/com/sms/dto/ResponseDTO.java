package com.sms.dto;

import java.util.Map;

public class ResponseDTO {
	private String message;
	
	private Map<String, Object> data;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Object> getData() {
		return data;
	}
	
	public void addData(String name, Object value) {
		data.put(name, value);
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
}
