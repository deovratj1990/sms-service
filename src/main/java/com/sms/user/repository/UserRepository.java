package com.sms.user.repository;

import com.sms.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	public User findByMobile(String mobile);
}
