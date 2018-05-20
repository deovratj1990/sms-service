package com.sms.accounting.service;

import com.sms.accounting.controller.dto.transactiondefinition.SaveDTO;

public interface TransactionDefinitionService {
    public void createTransactionDefinition(SaveDTO saveDTO) throws Exception;

    public void updateTransactionDefinition(Long id, SaveDTO saveDTO) throws Exception;
}
