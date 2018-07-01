package com.sms.society.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sms.society.entity.Resident;

public interface ResidentRepository extends JpaRepository<Resident, Long> {
	@Query("select r Resident r inner join User u where u.id = :userId")
    public List<Resident> findByUserId(@Param("userId") Long userId);
}
