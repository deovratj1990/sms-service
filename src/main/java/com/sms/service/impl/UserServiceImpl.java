package com.sms.service.impl;

import com.sms.DuplicateEntityException;
import com.sms.EntityNotFoundException;
import com.sms.InvalidEntity;
import com.sms.dto.MapDTO;
import com.sms.dto.member.LoginDTO;
import com.sms.dto.member.RegisterDTO;
import com.sms.entity.Access;
import com.sms.entity.Room;
import com.sms.entity.User;
import com.sms.repository.AccessRepository;
import com.sms.repository.RoomRepository;
import com.sms.repository.UserRepository;
import com.sms.service.UserService;
import com.sms.validation.UserControllerValidator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
	private static final int OTP_LENGTH = 6;

	private static final String OTP_VALUES = "0123456789";

	@Value("${com.sms.jwt.secret}")
	private String jwtSecret;

	@Autowired
	private UserControllerValidator validator;
	
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

		User user = userRepository.findByMobile(registerDTO.getMobile());

		if (user == null) {
			Room room = roomRepository.getOne(registerDTO.getRoomId());

			user = new User();

			user.setName(registerDTO.getName());
			user.setMobile(registerDTO.getMobile());
			user.setPassword(registerDTO.getPassword());
			user.setOtp(generateOTP());

			Access access = new Access();

			access.setUser(user);
			access.setRoom(room);
			access.setRole(Access.Role.MEMBER);

			accessRepository.save(access);

			user.addAccess(access);
			user.setStatus(User.Status.PENDING_VERIFICATION);

			user = userRepository.save(user);

			return user;
		} else {
			throw new DuplicateEntityException("User already exists");
		}
	}

	@Override
	public MapDTO login(LoginDTO loginDTO) throws Exception {
		UserControllerValidator validator = new UserControllerValidator();

		validator.validateLogin(loginDTO);

		User user;

		if(loginDTO.getOtp() == null && loginDTO.getAccessId() == null) {
			user = userRepository.findByMobileAndPassword(loginDTO.getMobile(), loginDTO.getPassword());

			if(user != null) {
				if(user.getStatus() == User.Status.ACTIVE) {
					MapDTO map = new MapDTO();

					if(user.getAccesses().size() > 1) {
                        map.put("rooms", new ArrayList<MapDTO>());

                        for(Access access : user.getAccesses()) {
                            Room room = access.getRoom();

                            MapDTO roomMap = new MapDTO();

                            roomMap.put("accessId", access.getId());
                            roomMap.put("address", room.getNumber() + ", " + room.getWing().getName() + ", " + room.getWing().getSociety().getName());

                            map.getList("rooms").add(roomMap);
                        }

                        map.put("status", "MULTIPLE_ROOMS");
                    } else {
					    map.put("status", "TOKEN");
                        map.put("token", generateToken(user, user.getAccesses().iterator().next()));
                    }

					return map;
				} else if(user.getStatus() == User.Status.PENDING_VERIFICATION) {
					throw new InvalidEntity("User verification pending");
				} else if(user.getStatus() == User.Status.INACTIVE) {
					throw new InvalidEntity("User is not active");
				} else if(user.getStatus() == User.Status.DELETED) {
					throw new InvalidEntity("User is deleted");
				} else {
					throw new InvalidEntity("Invalid state");
				}
			} else {
				throw new EntityNotFoundException("Invalid mobile and password");
			}
		} else if(loginDTO.getOtp() == null) {
        } else {
			user = userRepository.findByMobileAndPasswordAndOtp(loginDTO.getMobile(), loginDTO.getPassword(), loginDTO.getOtp());

			if(user != null) {
                if(user.getStatus() == User.Status.PENDING_VERIFICATION) {
                    user.setStatus(User.Status.ACTIVE);

                    user = userRepository.save(user);
                }

                MapDTO map = new MapDTO();

                if(user.getAccesses().size() > 1) {
                    map.put("rooms", new ArrayList<MapDTO>());

                    for(Access access : user.getAccesses()) {
                        Room room = access.getRoom();

                        MapDTO roomMap = new MapDTO();

                        roomMap.put("accessId", access.getId());
                        roomMap.put("address", room.getNumber() + ", " + room.getWing().getName() + ", " + room.getWing().getSociety().getName());

                        map.getList("rooms").add(roomMap);
                    }

                    map.put("status", "MULTIPLE_ROOMS");
                } else {
                    map.put("status", "TOKEN");
                    map.put("token", generateToken(user, user.getAccesses().iterator().next()));
                }
            } else {
			    throw new EntityNotFoundException("Invalid otp");
            }
		}
	}
}
