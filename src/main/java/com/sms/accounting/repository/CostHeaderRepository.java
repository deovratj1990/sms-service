package com.sms.accounting.repository;

import com.sms.accounting.entity.CostHeader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CostHeaderRepository extends JpaRepository<CostHeader, Long> {
    public CostHeader findByName(String name);
}
