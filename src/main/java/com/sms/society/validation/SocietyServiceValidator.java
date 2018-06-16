package com.sms.society.validation;

import com.sms.common.validation.ValidationException;
import com.sms.society.dto.society.RegisterDTO;
import com.sms.common.validation.ValidationResult;
import org.springframework.stereotype.Component;

@Component
public class SocietyServiceValidator {
    public void validateRegister(RegisterDTO registerDTO) throws ValidationException {
        ValidationResult validationResult = new ValidationResult();

        if(registerDTO.getName() == null || registerDTO.getName().equals("")) {
            validationResult.addError("name", "Name is mandatory.");
        }

        if(registerDTO.getWings() == null || registerDTO.getWings().size() == 0) {
            validationResult.addError("wings", "Wings are mandatory.");
        }

        if(registerDTO.getSecretary().getWing() == null || registerDTO.getSecretary().getWing().equals("")) {
            validationResult.addError("secretaryWing", "Secretary Wing is mandatory.");
        }

        if(registerDTO.getSecretary().getRoom() == null || registerDTO.getSecretary().getRoom().equals("")) {
            validationResult.addError("secretaryRoom", "Secretary Room is mandatory.");
        }

        if(registerDTO.getSecretary().getMobile() == null || registerDTO.getSecretary().getMobile().equals("")) {
            validationResult.addError("secretaryMobile", "Secretary Mobile is mandatory.");
        }

        if(validationResult.hasErrors()) {
            throw new ValidationException("Bad Input.", validationResult);
        }
    }
}
