package com.sms.user.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.accounting.entity.Account;
import com.sms.common.DateTimeUtil;
import com.sms.common.model.StringKeyMap;
import com.sms.common.model.Token;
import com.sms.common.service.SecurityService;
import com.sms.society.entity.Resident;
import com.sms.society.entity.ResidentRoom;
import com.sms.society.entity.Room;
import com.sms.society.entity.Society;
import com.sms.society.entity.Staff;
import com.sms.society.repository.ResidentRepository;
import com.sms.society.repository.RoomRepository;
import com.sms.society.repository.StaffRepository;
import com.sms.society.service.ResidentService;
import com.sms.user.dto.user.LoginDTO;
import com.sms.user.dto.user.RegisterDTO;
import com.sms.user.entity.User;
import com.sms.user.repository.UserRepository;
import com.sms.user.service.UserService;
import com.sms.user.validation.UserServiceValidator;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private DateTimeUtil dateTimeUtil;

	@Autowired
	private UserServiceValidator validator;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private ResidentRepository residentRepository;
	
	@Autowired
	private StaffRepository staffRepository;
	
	@Autowired
	private ResidentService residentService;

	@Autowired
	private SecurityService securityService;

	@Override
	public User getById(Long id) {
		return userRepository.findById(id).get();
	}
	
	private String generatePassword() {
		String allowedChars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		Random random = new Random();
		
		StringBuffer passwordBuffer = new StringBuffer();
		
		for(int index = 0; index < 10; index++) {
			passwordBuffer.append(allowedChars.charAt(random.nextInt(allowedChars.length())));
		}
		
		return passwordBuffer.toString();
	}

	@Override
	public void register(RegisterDTO registerDTO) throws Exception {
		validator.validateRegister(registerDTO);
		
		Room room = roomRepository.getOne(registerDTO.getRoomId());
		
		if(room != null) {
			User user = userRepository.findByMobile(registerDTO.getMobile());

			if(user == null) {
				String passwordHash = securityService.generateHash(generatePassword());
				
				user = new User();

				user.setMobile(registerDTO.getMobile());
				user.setPassword(passwordHash);
				user.setStatus(User.Status.ACTIVE);
				user.setCreatedOn(dateTimeUtil.getCurrent());

				user = userRepository.save(user);
			}
			
			if(user != null) {
				residentService.addMember(room, user);
			} else {
				throw new PersistenceException("Error while saving user.");
			}
		} else {
			throw new EntityNotFoundException("Room does not exist.");
		}
	}

	@Override
	public Set<StringKeyMap> login(LoginDTO loginDTO) throws Exception {
		UserServiceValidator validator = new UserServiceValidator();

		validator.validateLogin(loginDTO);
		
		String passwordHash = securityService.generateHash(loginDTO.getPassword());
		
		User user = userRepository.findByMobileAndPassword(loginDTO.getMobile(), passwordHash);
		
		if(user != null) {
			Set<StringKeyMap> options = new HashSet<StringKeyMap>();
			
			List<Resident> residents = residentRepository.findByUserId(user.getId());

			for(Resident resident : residents) {
				Society society = resident.getSociety();
				
				for(ResidentRoom residentRoom : resident.getResidentRooms()) {
					Room room = residentRoom.getRoom();
					
					Token token = new Token();
					
					token.setUserId(user.getId());
					token.setUserName(resident.getName());
					token.setUserRole(resident.getRole().name());
					token.setCreatedOn(dateTimeUtil.getCurrent());
					token.set("userType", residentRoom.getResidentType().name());
					token.set("societyId", society.getId());
					token.set("societyName", society.getName());
					token.set("roomId", room.getId());
					token.set("roomName", room.getName());
					
					StringKeyMap option = new StringKeyMap();
					
					option.put("accountType", Account.Type.ROOM.name());
					option.put("roomName", room.getName());
					option.put("societyName", society.getName());
					option.put("token", securityService.generateToken(token));
					
					options.add(option);
				}
			}
			
			List<Staff> staffs = staffRepository.findByUserId(user.getId());
			
			for(Staff staff : staffs) {
				Society society = staff.getSociety();
				
				Token token = new Token();
				
				token.setUserId(user.getId());
				token.setUserName("");
				token.setUserRole(staff.getRole().name());
				token.setCreatedOn(dateTimeUtil.getCurrent());
				token.set("societyId", society.getId());
				token.set("societyName", society.getName());
				
				StringKeyMap option = new StringKeyMap();
				
				option.put("accountType", Account.Type.STAFF.name());
				option.put("societyName", society.getName());
				option.put("token", securityService.generateToken(token));
				
				options.add(option);
			}

			return options;
		} else {
			throw new EntityNotFoundException("Invalid mobile and password.");
		}
	}
}
