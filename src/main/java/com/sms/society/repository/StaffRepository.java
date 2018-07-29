package com.sms.society.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sms.society.entity.Staff;

public interface StaffRepository extends JpaRepository<Staff, Long> {
	@Query("select s from Staff s inner join s.user u where u.id = :userId")
	public List<Staff> findByUserId(@Param("userId") Long userId);
}
