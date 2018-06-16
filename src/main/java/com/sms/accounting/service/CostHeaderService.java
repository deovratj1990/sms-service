package com.sms.accounting.service;

import com.sms.accounting.dto.costheader.SaveDTO;

public interface CostHeaderService {
    public void createCostHeader(SaveDTO saveDTO) throws Exception;

    public void updateCostHeader(Long id, SaveDTO saveDTO) throws Exception;
}
