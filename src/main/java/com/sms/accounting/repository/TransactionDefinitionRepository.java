package com.sms.accounting.repository;

import com.sms.accounting.entity.TransactionDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionDefinitionRepository extends JpaRepository<TransactionDefinition, Long> {
}
