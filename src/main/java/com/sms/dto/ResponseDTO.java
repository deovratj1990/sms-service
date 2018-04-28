package com.sms.dto;

import java.util.Map;

public class ResponseDTO {
	private String message;
	
	private Map<String, ?> data;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, ?> getData() {
		return data;
	}

	public void setData(Map<String, ?> data) {
		this.data = data;
	}
}
