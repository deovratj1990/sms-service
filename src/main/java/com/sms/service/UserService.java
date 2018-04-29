package com.sms.service;

import com.sms.dto.MapDTO;
import com.sms.dto.member.RegisterDTO;
import com.sms.dto.member.LoginDTO;
import com.sms.entity.Access;
import com.sms.entity.Room;
import com.sms.entity.User;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

public interface UserService {
	public String generateOTP();

	public String generateToken(User user, Access access);

	public User register(RegisterDTO registerDTO) throws Exception;

	public MapDTO login(LoginDTO loginDTO) throws Exception;
}
