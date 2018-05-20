package com.sms.common.validation;

import com.sms.common.validation.ValidationResult;

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
