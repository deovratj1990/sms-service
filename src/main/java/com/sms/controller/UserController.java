package com.sms.controller;

import com.sms.DuplicateEntityException;
import com.sms.EntityNotFoundException;
import com.sms.InvalidEntityException;
import com.sms.ValidationException;
import com.sms.dto.MapDTO;
import com.sms.dto.ResponseDTO;
import com.sms.dto.member.LoginDTO;
import com.sms.dto.member.RegisterDTO;
import com.sms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
@CrossOrigin(origins = "*")
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping(path = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> register(@RequestBody RegisterDTO registerDTO) {
		ResponseDTO responseDTO = new ResponseDTO();

		try {
			userService.register(registerDTO);

			responseDTO.setCode(HttpStatus.CREATED.value());

			responseDTO.setMessage("Registration successful. Please collect otp from your Society Secretary and Login.");
		} catch(ValidationException ex) {
			responseDTO.setCode(HttpStatus.BAD_REQUEST.value());

			responseDTO.setMessage(ex.getMessage());

			MapDTO map = new MapDTO();

			map.put("validationError", ex.getValidationResult().getMap());

			responseDTO.setData(map);
		} catch(DuplicateEntityException ex) {
			responseDTO.setCode(HttpStatus.CONFLICT.value());

			responseDTO.setMessage(ex.getMessage());
		} catch(Exception ex) {
			responseDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

			responseDTO.setMessage(ex.getMessage());
		}

		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}

	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public ResponseEntity<ResponseDTO> login(@RequestBody LoginDTO loginDTO) {
		ResponseDTO responseDTO = new ResponseDTO();

		try {
			MapDTO mapDTO = userService.login(loginDTO);

			responseDTO.setCode(HttpStatus.OK.value());

			responseDTO.setMessage("Success");

			responseDTO.setData(mapDTO);
		} catch(ValidationException ex) {
			responseDTO.setCode(HttpStatus.BAD_REQUEST.value());

			responseDTO.setMessage(ex.getMessage());

			responseDTO.setData(ex.getValidationResult().getMap());
		} catch(EntityNotFoundException ex) {
			responseDTO.setCode(HttpStatus.NOT_FOUND.value());

			responseDTO.setMessage(ex.getMessage());
		} catch(InvalidEntityException ex) {
			responseDTO.setCode(HttpStatus.BAD_REQUEST.value());

			responseDTO.setMessage(ex.getMessage());
		} catch(Exception ex) {
			responseDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

			responseDTO.setMessage(ex.getMessage());
		}

		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
}
