package com.sms.user.controller;

import com.sms.common.InvalidEntityException;
import com.sms.common.dto.MapDTO;
import com.sms.common.dto.ResponseDTO;
import com.sms.common.validation.ValidationException;
import com.sms.user.dto.user.LoginDTO;
import com.sms.user.dto.user.RegisterDTO;
import com.sms.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

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

			MapDTO map = new MapDTO();

			map.put("validationError", ex.getValidationResult().getMap());

			responseDTO.setData(map);
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
			MapDTO mapDTO = userService.login(loginDTO);

			responseDTO.setCode(HttpStatus.OK.value());

			responseDTO.setMessage("Success");

			responseDTO.setData(mapDTO);
		} catch(ValidationException ex) {
			responseDTO.setCode(HttpStatus.BAD_REQUEST.value());

			responseDTO.setMessage(ex.getMessage());

			responseDTO.setData(ex.getValidationResult().getMap());
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
