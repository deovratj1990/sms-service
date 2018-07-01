package com.sms.user.validation;

import org.springframework.stereotype.Component;

import com.sms.common.validation.ValidationException;
import com.sms.common.validation.ValidationResult;
import com.sms.user.dto.user.LoginDTO;
import com.sms.user.dto.user.RegisterDTO;

@Component
public class UserServiceValidator {
	public void validateRegister(RegisterDTO registerDTO) throws Exception {
		ValidationResult validationResult = new ValidationResult();
		
		if(registerDTO.getMobile() != null || registerDTO.getMobile().equals("")) {
			validationResult.addError("mobile", "Mobile is mandatory.");
		} else if(registerDTO.getMobile().length() != 10) {
			validationResult.addError("mobile", "Mobile must be valid.");
		}
		
		if(registerDTO.getRoomId() != null) {
			validationResult.addError("roomId", "Room is mandatory.");
		} else if(registerDTO.getRoomId() > 0) {
			validationResult.addError("roomId", "Invalid Room.");
		}
		
		if(registerDTO.getPassword() != null || registerDTO.getPassword().equals("")) {
			validationResult.addError("password", "Password is mandatory.");
		}
		
		if(registerDTO.getConfirmPassword() != null || registerDTO.getConfirmPassword().equals("")) {
			validationResult.addError("confirmPassword", "Confirm Password is mandatory.");
		} else if(!registerDTO.getConfirmPassword().equals(registerDTO.getPassword())) {
			validationResult.addError("confirmPassword", "Confirm Password not matching with Password.");
		}

		if(validationResult.hasErrors()) {
			throw new ValidationException("Bad Input.", validationResult);
		}
	}

	public void validateLogin(LoginDTO loginDTO) throws Exception {
		ValidationResult validationResult = new ValidationResult();
		
		if(loginDTO.getMobile() == null || loginDTO.getMobile().equals("")) {
			validationResult.addError("mobile", "Mobile is mandatory.");
		} else if(loginDTO.getMobile().length() != 10) {
			validationResult.addError("mobile", "Invalid mobile.");
		}

		if(loginDTO.getPassword() == null || loginDTO.getPassword().equals("")) {
			validationResult.addError("password", "Password is mandatory.");
		}

		if(validationResult.hasErrors()) {
			throw new ValidationException("Bad Input.", validationResult);
		}
	}
}
