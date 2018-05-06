package com.sms.accounting.service;

import com.sms.accounting.controller.dto.costheader.SaveDTO;

public interface CostHeaderService {
    public void saveCostHeader(SaveDTO saveDTO) throws Exception;
}
