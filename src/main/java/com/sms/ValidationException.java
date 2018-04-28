package com.sms;

import com.sms.validation.ValidationResult;

public class ValidationException extends Exception {
    private ValidationResult validationResult;

    public ValidationException(ValidationResult validationResult) {
        super("Validation exception");

        this.validationResult = validationResult;
    }

    public ValidationResult getValidationResult() {
        return validationResult;
    }
}
