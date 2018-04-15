package com.sms.validation;

public class ValidationMessage {
	private String text;
	
	private ValidationMessageType type;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public ValidationMessageType getType() {
		return type;
	}

	public void setType(ValidationMessageType type) {
		this.type = type;
	}
}
