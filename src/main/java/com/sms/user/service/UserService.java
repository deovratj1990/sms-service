package com.sms.user.service;

import java.util.Set;

import com.sms.common.model.StringKeyMap;
import com.sms.user.dto.user.LoginDTO;
import com.sms.user.dto.user.RegisterDTO;
import com.sms.user.entity.User;

public interface UserService {
	public User getById(Long id);

	public void register(RegisterDTO registerDTO) throws Exception;

	public Set<StringKeyMap> login(LoginDTO loginDTO) throws Exception;
}
