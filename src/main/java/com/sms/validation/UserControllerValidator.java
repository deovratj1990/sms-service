package com.sms.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sms.dto.member.RegisterDTO;
import com.sms.entity.Room;
import com.sms.repository.RoomRepository;
import com.sms.repository.SocietyRepository;

@Component
public class UserControllerValidator {
	@Autowired
	private SocietyRepository societyRepository;
	
	@Autowired
	private RoomRepository roomRepository;
	
	public ValidationResult validateRegister(RegisterDTO registerDTO) {
		ValidationResult validationResult = new ValidationResult();
		
		if(registerDTO.getSocietyId().equals(null)) {
			validationResult.addError("Society is mandatory");
		}
		
		if(registerDTO.getRoomId().equals(null)) {
			validationResult.addError("Room is mandatory");
		} else {
			Room room = roomRepository.getOne(registerDTO.getRoomId());
			
			if(room != null) {
				if(!room.getWing().getSociety().getId().equals(registerDTO.getSocietyId())) {
					validationResult.addError("Invalid society");
				}
			} else {
				validationResult.addError("Invalid room");
			}
		}
		
		if(registerDTO.getMobile().equals(null) || registerDTO.getMobile().equals("")) {
			validationResult.addError("Mobile is mandatory");
		} else if(registerDTO.getMobile().length() == 10) {
			validationResult.addError("Invalid mobile");
		}
		
		if(registerDTO.getPassword().equals(null) || registerDTO.getPassword().equals("")) {
			validationResult.addError("Password is mandatory");
		}
		
		if(registerDTO.getConfirmPassword().equals(null) || registerDTO.getConfirmPassword().equals("")) {
			validationResult.addError("Confirm password is mandatory");
		} else if(!registerDTO.getConfirmPassword().equals(registerDTO.getPassword())) {
			validationResult.addError("Invalid confirm password");
		}
		
		return validationResult;
	}
}
