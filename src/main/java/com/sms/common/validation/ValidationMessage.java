package com.sms.common.validation;

public class ValidationMessage {
	enum Type {
		INFORMATION,
		WARNING,
		ERROR
	}

	private String text;
	
	private Type type;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
}
