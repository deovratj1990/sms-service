package com.sms.accounting.service.impl;

import com.sms.accounting.controller.dto.costheader.SaveDTO;
import com.sms.accounting.entity.CostHeader;
import com.sms.accounting.repository.CostHeaderRepository;
import com.sms.accounting.service.CostHeaderService;
import com.sms.accounting.validation.CostHeaderControllerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class CostHeaderImpl implements CostHeaderService {
    @Autowired
    private CostHeaderControllerValidator validator;

    @Autowired
    private CostHeaderRepository costHeaderRepository;

    @Override
    public void saveCostHeader(SaveDTO saveDTO) throws Exception {
        validator.validateSave(saveDTO);

        CostHeader costHeader = new CostHeader();

        costHeader.setName(saveDTO.getName());
        costHeader.setStatus(CostHeader.Status.ACTIVE);
        costHeader.setCreatedOn(ZonedDateTime.now());
        costHeader.setCreatedBy(null);

        costHeaderRepository.save(costHeader);
    }
}
