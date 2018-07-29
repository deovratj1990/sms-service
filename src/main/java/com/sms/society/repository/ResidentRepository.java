package com.sms.society.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sms.society.entity.Resident;

public interface ResidentRepository extends JpaRepository<Resident, Long> {
}
