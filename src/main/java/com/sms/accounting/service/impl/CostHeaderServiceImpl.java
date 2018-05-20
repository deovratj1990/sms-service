package com.sms.accounting.service.impl;

import com.sms.accounting.controller.dto.costheader.SaveDTO;
import com.sms.accounting.entity.CostHeader;
import com.sms.accounting.repository.CostHeaderRepository;
import com.sms.accounting.service.CostHeaderService;
import com.sms.accounting.validation.CostHeaderServiceValidator;
import com.sms.common.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class CostHeaderServiceImpl implements CostHeaderService {
    @Autowired
    private CostHeaderServiceValidator validator;

    @Autowired
    private CostHeaderRepository costHeaderRepository;

    @Override
    public void createCostHeader(SaveDTO saveDTO) throws Exception {
        validator.validateSave(saveDTO);

        CostHeader costHeader = new CostHeader();

        costHeader.setName(saveDTO.getName());
        costHeader.setStatus(CostHeader.Status.ACTIVE);
        costHeader.setCreatedOn(ZonedDateTime.now());
        costHeader.setCreatedBy(null);

        costHeaderRepository.save(costHeader);
    }

    @Override
    public void updateCostHeader(Long id, SaveDTO saveDTO) throws Exception {
        validator.validateSave(saveDTO);

        CostHeader costHeader = costHeaderRepository.findById(id).get();

        if(costHeader != null) {
            costHeader.setName(saveDTO.getName());

            costHeaderRepository.save(costHeader);
        } else {
            throw new EntityNotFoundException("Cost header not found.");
        }
    }
}
