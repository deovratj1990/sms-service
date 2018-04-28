package com.sms.service.impl;

import com.sms.DuplicateEntityException;
import com.sms.EntityNotFoundException;
import com.sms.ValidationException;
import com.sms.dto.member.LoginDTO;
import com.sms.dto.member.RegisterDTO;
import com.sms.entity.Room;
import com.sms.entity.User;
import com.sms.repository.RoomRepository;
import com.sms.repository.UserRepository;
import com.sms.service.UserService;
import com.sms.validation.UserControllerValidator;
import com.sms.validation.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
	private static final int OTP_LENGTH = 6;

	private static final String OTP_VALUES = "0123456789";

	@Autowired
	private UserControllerValidator validator;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoomRepository roomRepository;

	@Override
	public String generateOTP() {
		Random random = new Random();

		StringBuilder otp = new StringBuilder();

		for(int index = 0; index < OTP_LENGTH; index++) {
			int nextCharPosition = random.nextInt(OTP_VALUES.length());

			otp.append(OTP_VALUES.charAt(nextCharPosition));
		}

		return otp.toString();
	}
	
	@Override
	public User register(RegisterDTO registerDTO) throws Exception {
		validator.validateRegister(registerDTO);

		User user = userRepository.findByMobile(registerDTO.getMobile());

		if (user == null) {
			Room room = roomRepository.getOne(registerDTO.getRoomId());

			user = new User();

			user.setName(registerDTO.getName());
			user.setMobile(registerDTO.getMobile());
			user.setPassword(registerDTO.getPassword());
			user.setOtp(generateOTP());
			user.setRole(User.Role.MEMBER);
			user.addRoom(room);
			user.setStatus(User.Status.PENDING_VERIFICATION);

			user = userRepository.save(user);

			return user;
		} else {
			throw new DuplicateEntityException("User already exists");
		}
	}

	@Override
	public User login(LoginDTO loginDTO) throws Exception {
		UserControllerValidator validator = new UserControllerValidator();

		validator.validateLogin(loginDTO);

		User user;

		if(loginDTO.getOtp() == null || loginDTO.getOtp().equals("")) {
			user = userRepository.findByMobileAndPassword(loginDTO.getMobile(), loginDTO.getPassword());
		} else {
			user = userRepository.findByMobileAndPasswordAndOtp(loginDTO.getMobile(), loginDTO.getPassword(), loginDTO.getOtp());

			if(user.getStatus()) {}
		}

		if(user != null) {
			return user;
		} else {
			throw new EntityNotFoundException("Invalid mobile and password");
		}
	}
}
