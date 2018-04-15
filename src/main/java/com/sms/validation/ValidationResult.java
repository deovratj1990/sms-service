package com.sms.validation;

import java.util.List;

public class ValidationResult {
	private List<ValidationMessage> messages;

	public List<ValidationMessage> getErrors() {
		return messages;
	}
	
	private void addMessage(String text, ValidationMessageType type) {
		ValidationMessage validationMessage = new ValidationMessage();
		
		validationMessage.setText(text);
		validationMessage.setType(type);
		
		messages.add(validationMessage);
	}
	
	public void addInformation(String text) {
		addMessage(text, ValidationMessageType.INFORMATION);
	}
	
	public void addWarning(String text) {
		addMessage(text, ValidationMessageType.WARNING);
	}
	
	public void addError(String text) {
		addMessage(text, ValidationMessageType.ERROR);
	}

	public void setErrors(List<ValidationMessage> messages) {
		this.messages = messages;
	}
	
	public boolean isValid() {
		return ((messages.size() == 0) ? true : false);
	}
}
