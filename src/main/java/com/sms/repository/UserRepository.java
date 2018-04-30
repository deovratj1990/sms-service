package com.sms.repository;

import com.sms.entity.Access;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sms.entity.User;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
	public User findByMobile(String mobile);

	public User findByMobileAndPassword(String mobile, String password);

	@Query("select u from Access a inner join a.user u where u.mobile = :mobile and u.password = :password and a.id = :accessId")
	public User findByMobileAndPasswordAndAccess(String mobile, String password, Long accessId);

	@Query("select u from Access a inner join a.user u where u.mobile = :mobile and u.password = :password and a.id = :accessId and a.otp = :otp")
	public User findByMobileAndPasswordAndAccessAndOtp(String mobile, String password, Long accessId, String otp);
}
