package com.sms.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sms.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public User findByMobile(String mobile);
	
	public User findByMobileAndPassword(String mobile, String password);
}
