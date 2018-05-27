package com.sms.user.validation;

import com.sms.common.validation.ValidationException;
import com.sms.common.validation.ValidationResult;
import com.sms.society.entity.Room;
import com.sms.society.repository.RoomRepository;
import com.sms.society.repository.SocietyRepository;
import com.sms.user.controller.dto.user.LoginDTO;
import com.sms.user.controller.dto.user.RegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceValidator {
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

		if(validationResult.hasErrors()) {
			throw new ValidationException("Bad Input.", validationResult);
		}
	}

	public void validateLogin(LoginDTO loginDTO) throws Exception {
		ValidationResult validationResult = new ValidationResult();

		if(loginDTO.getOperation() == null) {
			validationResult.addError("operation", "Operation is mandatory.");
		} else if(!loginDTO.getOperation().toUpperCase().matches("ROOM|PASSWORD")) {
			validationResult.addError("operation", "Invalid operation.");
		} else {
			if(loginDTO.getMobile() == null) {
				validationResult.addError("mobile", "Mobile is mandatory.");
			} else if(loginDTO.getMobile().length() != 10) {
				validationResult.addError("mobile", "Invalid mobile.");
			}

			if(loginDTO.getOperation().toUpperCase().equals("PASSWORD") && loginDTO.getPassword() == null) {
				validationResult.addError("password", "Password is mandatory.");
			}

			if(loginDTO.getOperation().toUpperCase().equals("PASSWORD") && loginDTO.getAccessId() == null) {
				validationResult.addError("accessId", "Access is mandatory.");
			}
		}

		if(validationResult.hasErrors()) {
			throw new ValidationException("Bad Input.", validationResult);
		}
	}
}
