package com.sms.society.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sms.society.entity.Society;

public interface SocietyRepository extends JpaRepository<Society, Long> {
}
