package com.sms.accounting.repository;

import com.sms.accounting.entity.Particular;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticularRepository extends JpaRepository<Particular, Long> {
}
