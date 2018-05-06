package com.sms.common.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sms.common.validation.ValidationMessage;
import com.sms.common.validation.ValidationResult;

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
