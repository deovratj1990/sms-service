package com.sms.user.controller;

import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sms.common.InvalidEntityException;
import com.sms.common.dto.ResponseDTO;
import com.sms.common.model.StringKeyMap;
import com.sms.common.validation.ValidationException;
import com.sms.user.dto.user.LoginDTO;
import com.sms.user.dto.user.RegisterDTO;
import com.sms.user.service.UserService;

@RestController
@RequestMapping(path = "/user")
@CrossOrigin(origins = "*")
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping(path = "/register", method = RequestMethod.POST)
	public ResponseEntity<ResponseDTO> registerUser(@RequestBody RegisterDTO registerDTO) {
		ResponseDTO responseDTO = new ResponseDTO();

		try {
			userService.register(registerDTO);

			responseDTO.setCode(HttpStatus.CREATED.value());

			responseDTO.setMessage("Registration successful. Please collect Password from your Society Secretary and Login.");
		} catch(ValidationException ex) {
			responseDTO.setCode(HttpStatus.BAD_REQUEST.value());

			responseDTO.setMessage(ex.getMessage());

			responseDTO.setData(ex.getValidationResult().getMessages());
		} catch(EntityNotFoundException ex) {
			responseDTO.setCode(HttpStatus.NOT_FOUND.value());

			responseDTO.setMessage(ex.getMessage());
		} catch(Exception ex) {
			responseDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

			responseDTO.setMessage(ex.getMessage());
		}

		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}

	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public ResponseEntity<ResponseDTO> loginUser(@RequestBody LoginDTO loginDTO) {
		ResponseDTO responseDTO = new ResponseDTO();

		try {
			Set<StringKeyMap> options = userService.login(loginDTO);

			responseDTO.setCode(HttpStatus.OK.value());

			responseDTO.setMessage("Success");

			responseDTO.addData("options", options);
		} catch(ValidationException ex) {
			responseDTO.setCode(HttpStatus.BAD_REQUEST.value());

			responseDTO.setMessage(ex.getMessage());

			responseDTO.setData(ex.getValidationResult().getMessages());
		} catch(EntityNotFoundException ex) {
			responseDTO.setCode(HttpStatus.UNAUTHORIZED.value());

			responseDTO.setMessage(ex.getMessage());
		} catch(InvalidEntityException ex) {
			responseDTO.setCode(HttpStatus.FORBIDDEN.value());

			responseDTO.setMessage(ex.getMessage());
		} catch(Exception ex) {
			responseDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

			responseDTO.setMessage(ex.getMessage());
		}

		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
}
