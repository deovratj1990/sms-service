package com.sms.service;

import com.sms.dto.member.RegisterDTO;
import com.sms.entity.User;

public interface UserService {
	public User register(RegisterDTO registerDTO);
}
