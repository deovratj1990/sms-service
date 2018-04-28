package com.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sms.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public User findByMobile(String mobile);

	public User findByMobileAndPassword(String mobile, String password);

	public User findByMobileAndPasswordAndOtp(String mobile, String password, String otp);
}
