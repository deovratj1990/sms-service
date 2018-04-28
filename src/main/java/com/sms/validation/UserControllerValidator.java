package com.sms.validation;

import com.sms.ValidationException;
import com.sms.dto.member.LoginDTO;
import com.sms.dto.member.RegisterDTO;
import com.sms.entity.Room;
import com.sms.repository.RoomRepository;
import com.sms.repository.SocietyRepository;
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
			validationResult.addError("societyId", "Society is mandatory");
		}
		
		if(registerDTO.getRoomId().equals(null)) {
			validationResult.addError("roomId", "Room is mandatory");
		} else {
			Room room = roomRepository.getOne(registerDTO.getRoomId());
			
			if(room != null) {
				if(!room.getWing().getSociety().getId().equals(registerDTO.getSocietyId())) {
					validationResult.addError("roomId", "Invalid society");
				}
			} else {
				validationResult.addError("roomId", "Invalid room");
			}
		}
		
		if(registerDTO.getMobile().equals(null) || registerDTO.getMobile().equals("")) {
			validationResult.addError("mobile", "Mobile is mandatory");
		} else if(registerDTO.getMobile().length() == 10) {
			validationResult.addError("mobile", "Invalid mobile");
		}
		
		if(registerDTO.getPassword().equals(null) || registerDTO.getPassword().equals("")) {
			validationResult.addError("password", "Password is mandatory");
		}
		
		if(registerDTO.getConfirmPassword().equals(null) || registerDTO.getConfirmPassword().equals("")) {
			validationResult.addError("confirmPassword", "Confirm password is mandatory");
		} else if(!registerDTO.getConfirmPassword().equals(registerDTO.getPassword())) {
			validationResult.addError("confirmPassword", "Invalid confirm password");
		}

		if(validationResult.hasErrors()) {
			throw new ValidationException(validationResult);
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
			throw new ValidationException(validationResult);
		}
	}
}
