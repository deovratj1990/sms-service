package com.sms.user.service.impl;

import java.util.Date;
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
import com.sms.society.repository.ResidentRoomRepository;
import com.sms.society.repository.RoomRepository;
import com.sms.society.repository.StaffRepository;
import com.sms.society.service.SocietyService;
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
	private ResidentRoomRepository residentRoomRepository;
	
	@Autowired
	private StaffRepository staffRepository;
	
	@Autowired
	private SocietyService societyService;

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
				societyService.addMember(room, user);
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
		
		String passwordHash = loginDTO.getPassword();//securityService.generateHash(loginDTO.getPassword());
		
		User user = userRepository.findByMobileAndPassword(loginDTO.getMobile(), passwordHash);
		
		if(user != null) {
			Set<StringKeyMap> options = new HashSet<StringKeyMap>();
			
			List<ResidentRoom> residentRooms = residentRoomRepository.findByUserId(user.getId());
			
			for(ResidentRoom residentRoom : residentRooms) {
				Resident resident = residentRoom.getResident();
				
				Society society = resident.getSociety();
				
				Room room = residentRoom.getRoom();
				
				Token token = new Token();
				
				token.setUserId(user.getId());
				token.setUserName(resident.getName());
				token.setUserType(residentRoom.getResidentType().name());
				token.setUserRole(resident.getRole().name());
				token.setSocietyId(society.getId());
				token.setSocietyName(society.getName());
				token.setRoomId(room.getId());
				token.setRoomName(room.getName());
				token.setIssuedAt(new Date());
				
				StringKeyMap option = new StringKeyMap();
				
				option.put("accountType", residentRoom.getResidentType().name());
				option.put("roomName", room.getName());
				option.put("societyName", society.getName());
				option.put("token", securityService.generateToken(token));
				
				options.add(option);
			}
			
			List<Staff> staffs = staffRepository.findByUserId(user.getId());
			
			for(Staff staff : staffs) {
				Society society = staff.getSociety();
				
				Token token = new Token();
				
				token.setUserId(user.getId());
				token.setUserName("");
				token.setUserType("NA");
				token.setUserRole(staff.getRole().name());
				token.setSocietyId(society.getId());
				token.setSocietyName(society.getName());
				token.setIssuedAt(new Date());
				
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
