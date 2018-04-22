package com.sms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.dto.member.RegisterDTO;
import com.sms.entity.Room;
import com.sms.entity.User;
import com.sms.repository.RoomRepository;
import com.sms.repository.UserRepository;
import com.sms.service.UserService;
import com.sms.validation.UserControllerValidator;
import com.sms.validation.ValidationResult;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserControllerValidator validator;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Override
	public User register(RegisterDTO registerDTO) {
		ValidationResult validationResult = validator.validateRegister(registerDTO);
		
		if(validationResult.isValid()) {
			User user = userRepository.findByMobile(registerDTO.getMobile());
			
			if(user == null) {
				Room room = roomRepository.getOne(registerDTO.getRoomId());
				
				user = new User();
				
				user.setName(registerDTO.getName());
				user.setMobile(registerDTO.getMobile());
				user.setPassword(registerDTO.getPassword());
				user.setRole(User.Role.MEMBER);
				user.addRoom(room);
				user.setStatus(User.Status.REGISTERED);
				
				userRepository.save(user);
			}
		}
		
		return null;
	}
}
