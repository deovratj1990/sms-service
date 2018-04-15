package com.sms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sms.dto.ResponseDTO;
import com.sms.dto.member.RegisterDTO;
import com.sms.service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
	
	public ResponseEntity<ResponseDTO> register(@RequestBody RegisterDTO registerDTO) {
		return new ResponseEntity<ResponseDTO>(HttpStatus.NO_CONTENT);
	}
}
