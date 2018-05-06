package com.sms;

import com.sms.validation.ValidationResult;

public class ValidationException extends Exception {
    private ValidationResult validationResult;

    public ValidationException(String message, ValidationResult validationResult) {
        super(message);

        this.validationResult = validationResult;
    }

    public ValidationResult getValidationResult() {
        return validationResult;
    }
}
