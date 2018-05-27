package com.sms.user.service.impl;

import com.sms.common.DateTimeUtil;
import com.sms.common.InvalidEntityException;
import com.sms.common.dto.MapDTO;
import com.sms.society.entity.Room;
import com.sms.society.repository.RoomRepository;
import com.sms.user.controller.dto.user.LoginDTO;
import com.sms.user.controller.dto.user.RegisterDTO;
import com.sms.user.entity.Access;
import com.sms.user.entity.User;
import com.sms.user.repository.AccessRepository;
import com.sms.user.repository.UserRepository;
import com.sms.user.service.UserService;
import com.sms.user.validation.UserServiceValidator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
	private static final int OTP_LENGTH = 6;

	private static final String OTP_VALUES = "0123456789";

	@Value("${com.sms.jwt.secret}")
	private String jwtSecret;

	@Autowired
	private DateTimeUtil dateTimeUtil;

	@Autowired
	private UserServiceValidator validator;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private AccessRepository accessRepository;

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
	public String generateToken(User user, Access access) {
		Claims claims = Jwts.claims().setSubject(user.getId().toString());

		claims.put("userName", user.getName());
		claims.put("accessId", access.getId());
		claims.put("societyName", access.getRoom().getWing().getSociety().getName());

		return Jwts.builder().signWith(SignatureAlgorithm.HS256, jwtSecret).setClaims(claims).compact();
	}

	@Override
	public User register(RegisterDTO registerDTO) throws Exception {
		validator.validateRegister(registerDTO);

		Room room = roomRepository.findById(registerDTO.getRoomId()).get();

		if(room != null) {
			User user = userRepository.findByMobile(registerDTO.getMobile());

			if (user == null) {
				user = new User();

				user.setName(registerDTO.getName());
				user.setMobile(registerDTO.getMobile());
				user.setCreatedOn(dateTimeUtil.getCurrent());

				user = userRepository.save(user);
			}

			Access access = new Access();

			access.setUser(user);
			access.setRoom(room);
			access.setPassword(generateOTP());
			access.setRole(Access.Role.MEMBER);
			access.setStatus(Access.Status.PENDING_VERIFICATION);

			accessRepository.save(access);

			return user;
		} else {
			throw new EntityNotFoundException("Room not found.");
		}
	}

	@Override
	public MapDTO login(LoginDTO loginDTO) throws Exception {
		UserServiceValidator validator = new UserServiceValidator();

		validator.validateLogin(loginDTO);

		if(loginDTO.getOperation().toUpperCase().equals("ROOM")) {
			User user = userRepository.findByMobile(loginDTO.getMobile());

			if(user != null) {
				MapDTO map = new MapDTO();

				map.put("rooms", new ArrayList<MapDTO>());

				for(Access access : user.getAccesses()) {
					Room room = access.getRoom();

					MapDTO roomMap = new MapDTO();

					roomMap.put("accessId", access.getId());
					roomMap.put("address", room.getName() + ", " + room.getWing().getName() + ", " + room.getWing().getSociety().getName());
					roomMap.put("status", access.getStatus().name());

					if(access.getStatus() == Access.Status.ACTIVE) {
						roomMap.put("statusText", "User is active.");
					} else if(access.getStatus() == Access.Status.PENDING_VERIFICATION) {
						roomMap.put("statusText", "User verification pending.");
					} else if(access.getStatus() == Access.Status.INACTIVE) {
						roomMap.put("statusText", "User is not active.");
					} else if(access.getStatus() == Access.Status.DELETED) {
						roomMap.put("statusText", "User is deleted.");
					} else {
						roomMap.put("statusText", "Invalid user status.");
					}

					map.getList("rooms").add(roomMap);
				}

				return map;
			} else {
				throw new EntityNotFoundException("Invalid mobile.");
			}
		} else {
			Access access = accessRepository.findByIdAndPassword(loginDTO.getAccessId(), loginDTO.getPassword());

			if(access != null) {
				User user = access.getUser();

				if(user.getMobile().equals(loginDTO.getMobile())) {
					MapDTO map = new MapDTO();

					if(access.getStatus() == Access.Status.PENDING_VERIFICATION) {
						access.setStatus(Access.Status.ACTIVE);

						access = accessRepository.save(access);
					}

					map.put("status", access.getStatus().name());

					if(access.getStatus() == Access.Status.ACTIVE) {
						map.put("statusText", "User is active.");
						map.put("token", generateToken(user, user.getAccesses().iterator().next()));
					} else if(access.getStatus() == Access.Status.INACTIVE) {
						map.put("statusText", "User is not active.");
						map.put("token", null);
					} else if(access.getStatus() == Access.Status.DELETED) {
						map.put("statusText", "User is deleted.");
						map.put("token", null);
					} else {
						map.put("statusText", "Invalid user status.");
						map.put("token", null);
					}

					return map;
				} else {
					throw new InvalidEntityException("Invalid mobile and room.");
				}
            } else {
			    throw new EntityNotFoundException("Incorrect password.");
            }
		}
	}
}
