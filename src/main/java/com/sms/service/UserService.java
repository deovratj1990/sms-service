package com.sms.service;

import com.sms.dto.member.RegisterDTO;
import com.sms.dto.member.LoginDTO;
import com.sms.entity.User;

public interface UserService {
	public String generateOTP();

	public User register(RegisterDTO registerDTO) throws Exception;

	public User login(LoginDTO loginDTO) throws Exception;
}
