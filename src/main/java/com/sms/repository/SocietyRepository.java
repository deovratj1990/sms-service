package com.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sms.entity.Society;

public interface SocietyRepository extends JpaRepository<Society, Long> {
}
