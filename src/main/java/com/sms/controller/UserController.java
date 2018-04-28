package com.sms.controller;

import com.sms.ValidationException;
import com.sms.dto.ResponseDTO;
import com.sms.dto.member.RegisterDTO;
import com.sms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
	
	public ResponseEntity<ResponseDTO> register(@RequestBody RegisterDTO registerDTO) {
		ResponseDTO responseDTO = new ResponseDTO();

		try {
			userService.register(registerDTO);

			responseDTO.setMessage("User registered successfully. Kindly collect login otp from secretary");

			return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.NO_CONTENT);
		} catch(ValidationException ex) {
			responseDTO.setMessage(ex.getMessage());

			responseDTO.setData(ex.getValidationResult().getMessages());

			return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST);
		} catch(Exception ex) {
			responseDTO.setMessage(ex.getMessage());

			return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
