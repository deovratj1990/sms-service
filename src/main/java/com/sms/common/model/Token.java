package com.sms.common.model;

import java.time.ZonedDateTime;

public class Token extends StringKeyMap {
	private String authorizationType;
	
	private String text;
	
	public String getAuthorizationType() {
		return authorizationType;
	}

	public void setAuthorizationType(String authorizationType) {
		this.authorizationType = authorizationType;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Long getUserId() {
		return getLong("userId");
	}

	public void setUserId(Long userId) {
		put("userId", userId);
	}
	
	public String getUserName() {
		return getString("userName");
	}

	public void setUserName(String userName) {
		put("userName", userName);
	}
	
	public String getUserRole() {
		return getString("userRole");
	}

	public void setUserRole(String userRole) {
		put("userRole", userRole);
	}
	
	public ZonedDateTime getCreatedOn() {
		return (ZonedDateTime) get("createdOn");
	}

	public void setCreatedOn(ZonedDateTime createdOn) {
		put("createdOn", createdOn);
	}
	
	public void set(String key, Object value) {
		put(key, value);
	}
}
