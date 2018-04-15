package com.sms.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sms.validation.ValidationMessage;
import com.sms.validation.ValidationResult;

@Configuration
public class ValidationConfiguration {
	@Bean
	public ValidationMessage validationMessage() {
		return new ValidationMessage();
	}
	
	@Bean
	public ValidationResult validationResult() {
		return new ValidationResult();
	}
}
