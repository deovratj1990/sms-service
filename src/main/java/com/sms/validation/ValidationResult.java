package com.sms.validation;

import com.sms.dto.MapDTO;

import java.util.HashMap;
import java.util.Map;

public class ValidationResult {
	private Map<String, ValidationMessage> messages;

	public ValidationResult() {
		messages = new HashMap<String, ValidationMessage>();
	}

	public Map<String, ValidationMessage> getMessages() {
		return messages;
	}
	
	private void addMessage(String target, String text, ValidationMessage.Type type) {
		ValidationMessage validationMessage = new ValidationMessage();
		
		validationMessage.setText(text);
		validationMessage.setType(type);
		
		messages.put(target, validationMessage);
	}

	public void setMessages(Map<String, ValidationMessage> messages) {
		this.messages = messages;
	}

	private boolean hasMessages(ValidationMessage.Type type) {
		for(ValidationMessage message : messages.values()) {
			if(message.getType() == type) {
				return true;
			}
		}

		return false;
	}

	public boolean hasInformations() {
		return hasMessages(ValidationMessage.Type.INFORMATION);
	}
	
	public void addInformation(String target, String text) {
		addMessage(target, text, ValidationMessage.Type.INFORMATION);
	}

	public boolean hasWarnings() {
		return hasMessages(ValidationMessage.Type.INFORMATION);
	}
	
	public void addWarning(String target, String text) {
		addMessage(target, text, ValidationMessage.Type.WARNING);
	}

	public boolean hasErrors() {
		return hasMessages(ValidationMessage.Type.ERROR);
	}
	
	public void addError(String target, String text) {
		addMessage(target, text, ValidationMessage.Type.ERROR);
	}

	public MapDTO getMap() {
		MapDTO map = new MapDTO();

		for(Map.Entry<String, ValidationMessage> entry : messages.entrySet()) {
			map.put(entry.getKey(), entry.getValue());
		}

		return map;
	}
}
