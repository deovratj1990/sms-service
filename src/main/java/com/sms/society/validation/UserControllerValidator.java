package com.sms.society.validation;

import com.sms.common.ValidationException;
import com.sms.user.controller.dto.user.LoginDTO;
import com.sms.user.controller.dto.user.RegisterDTO;
import com.sms.society.entity.Room;
import com.sms.society.repository.RoomRepository;
import com.sms.society.repository.SocietyRepository;
import com.sms.common.validation.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserControllerValidator {
	@Autowired
	private SocietyRepository societyRepository;
	
	@Autowired
	private RoomRepository roomRepository;
	
	public void validateRegister(RegisterDTO registerDTO) throws Exception {
		ValidationResult validationResult = new ValidationResult();
		
		if(registerDTO.getSocietyId().equals(null)) {
			validationResult.addError("societyId", "Society is mandatory.");
		}
		
		if(registerDTO.getRoomId().equals(null)) {
			validationResult.addError("roomId", "Room is mandatory.");
		} else {
			Room room = roomRepository.getOne(registerDTO.getRoomId());
			
			if(room != null) {
				if(!room.getWing().getSociety().getId().equals(registerDTO.getSocietyId())) {
					validationResult.addError("roomId", "Society must be valid.");
				}
			} else {
				validationResult.addError("roomId", "Room must be valid.");
			}
		}
		
		if(registerDTO.getMobile().equals(null) || registerDTO.getMobile().equals("")) {
			validationResult.addError("mobile", "Mobile is mandatory.");
		} else if(registerDTO.getMobile().length() != 10) {
			validationResult.addError("mobile", "Mobile must be valid.");
		}
		
		if(registerDTO.getPassword().equals(null) || registerDTO.getPassword().equals("")) {
			validationResult.addError("password", "Password is mandatory");
		}
		
		if(registerDTO.getConfirmPassword().equals(null) || registerDTO.getConfirmPassword().equals("")) {
			validationResult.addError("confirmPassword", "Confirm password is mandatory.");
		} else if(!registerDTO.getConfirmPassword().equals(registerDTO.getPassword())) {
			validationResult.addError("confirmPassword", "Confirm password must match Password.");
		}

		if(validationResult.hasErrors()) {
			throw new ValidationException("Bad Input.", validationResult);
		}
	}

	public void validateLogin(LoginDTO loginDTO) throws Exception {
		ValidationResult validationResult = new ValidationResult();

		if(loginDTO.getMobile() == null) {
			validationResult.addError("mobile", "Mobile is mandatory");
		} else if(loginDTO.getMobile().length() != 10) {
			validationResult.addError("mobile", "Invalid mobile");
		}

		if(loginDTO.getPassword() == null) {
			validationResult.addError("password", "Password is mandatory");
		}

		if(validationResult.hasErrors()) {
			throw new ValidationException("Bad Input.", validationResult);
		}
	}
}
