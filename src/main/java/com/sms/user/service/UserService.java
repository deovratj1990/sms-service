package com.sms.user.service;

import com.sms.common.dto.MapDTO;
import com.sms.user.controller.dto.user.RegisterDTO;
import com.sms.user.controller.dto.user.LoginDTO;
import com.sms.user.entity.Access;
import com.sms.user.entity.User;

public interface UserService {
	public String generateOTP();

	public String generateToken(User user, Access access);

	public User register(RegisterDTO registerDTO) throws Exception;

	public MapDTO login(LoginDTO loginDTO) throws Exception;
}
