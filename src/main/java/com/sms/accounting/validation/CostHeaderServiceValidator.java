package com.sms.accounting.validation;

import com.sms.accounting.controller.dto.costheader.SaveDTO;
import com.sms.common.validation.ValidationException;
import com.sms.common.validation.ValidationResult;
import org.springframework.stereotype.Component;

@Component
public class CostHeaderServiceValidator {
    public void validateSave(SaveDTO saveDTO) throws ValidationException {
        ValidationResult validationResult = new ValidationResult();

        if(saveDTO.getName() == null || saveDTO.getName().equals("")) {
            validationResult.addError("name", "Name is mandatory.");
        }

        if(validationResult.hasErrors()) {
            throw new ValidationException("Bad Input.", validationResult);
        }
    }
}
