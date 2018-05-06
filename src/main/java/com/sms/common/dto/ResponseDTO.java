package com.sms.common.dto;

public class ResponseDTO extends MapDTO {
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

	public MapDTO getData() {
		return getMap("data");
	}

	public void setData(MapDTO data) {
		put("data", data);
	}
}
