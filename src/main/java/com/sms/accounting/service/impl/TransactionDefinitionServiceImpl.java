package com.sms.accounting.service.impl;

import com.sms.accounting.controller.dto.transactiondefinition.SaveDTO;
import com.sms.accounting.entity.CostHeader;
import com.sms.accounting.entity.Particular;
import com.sms.accounting.entity.TransactionDefinition;
import com.sms.accounting.repository.CostHeaderRepository;
import com.sms.accounting.repository.ParticularRepository;
import com.sms.accounting.repository.TransactionDefinitionRepository;
import com.sms.accounting.service.TransactionDefinitionService;
import com.sms.accounting.validation.TransactionDefinitionServiceValidator;
import com.sms.common.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TransactionDefinitionServiceImpl implements TransactionDefinitionService {
    @Autowired
    private DateTimeFormatter dateTimeFormatter;

    @Autowired
    private TransactionDefinitionServiceValidator validator;

    @Autowired
    private CostHeaderRepository costHeaderRepository;

    @Autowired
    private ParticularRepository particularRepository;

    @Autowired
    private TransactionDefinitionRepository transactionDefinitionRepository;

    @Override
    public void createTransactionDefinition(SaveDTO saveDTO) throws Exception {
        validator.validateSave(saveDTO);

        TransactionDefinition transactionDefinition = new TransactionDefinition();

        CostHeader costHeader = costHeaderRepository.findById(saveDTO.getCostHeaderId()).get();

        if(costHeader != null) {
            transactionDefinition.setCostHeader(costHeader);
            transactionDefinition.setTransactionType(TransactionDefinition.TransactionType.valueOf(saveDTO.getTransactionType()));
            transactionDefinition.setApplicableTo(TransactionDefinition.ApplicableTo.valueOf(saveDTO.getApplicableTo()));
            transactionDefinition.setInterval(TransactionDefinition.Interval.valueOf(saveDTO.getInterval()));
            transactionDefinition.setFrom(ZonedDateTime.parse(saveDTO.getFrom(), dateTimeFormatter));

            if(saveDTO.getHasParticulars()) {
                Double totalAmount = 0.0;

                for(SaveDTO.Particular particularDTO : saveDTO.getParticulars()) {
                    Particular particular = new Particular();

                    CostHeader particularCostHeader = costHeaderRepository.findByName(particularDTO.getName());

                    if(particularCostHeader != null) {
                        particular.setCostHeader(particularCostHeader);
                        particular.setAmount(particularDTO.getAmount());

                        transactionDefinition.addParticular(particular);

                        totalAmount += particular.getAmount();
                    } else {
                        throw new EntityNotFoundException("Cost header not found for particular name.");
                    }
                }

                transactionDefinition.setAmount(totalAmount);
            } else {
                transactionDefinition.setAmount(saveDTO.getAmount());
            }

            transactionDefinitionRepository.save(transactionDefinition);

            if(transactionDefinition.getParticulars().size() > 0) {
                for(Particular particular : transactionDefinition.getParticulars()) {
                    particularRepository.save(particular);
                }
            }
        } else {
            throw new EntityNotFoundException("Cost header not found.");
        }
    }

    @Override
    public void updateTransactionDefinition(Long id, SaveDTO saveDTO) throws Exception {
        TransactionDefinition transactionDefinition = transactionDefinitionRepository.findById(id).get();

        if(transactionDefinition != null) {
            validator.validateSave(saveDTO);

            CostHeader costHeader = costHeaderRepository.findById(saveDTO.getCostHeaderId()).get();

            if(costHeader != null) {
                transactionDefinition.setCostHeader(costHeader);
                transactionDefinition.setTransactionType(TransactionDefinition.TransactionType.valueOf(saveDTO.getTransactionType()));
                transactionDefinition.setApplicableTo(TransactionDefinition.ApplicableTo.valueOf(saveDTO.getApplicableTo()));
                transactionDefinition.setInterval(TransactionDefinition.Interval.valueOf(saveDTO.getInterval()));
                transactionDefinition.setFrom(ZonedDateTime.parse(saveDTO.getFrom(), dateTimeFormatter));

                if(saveDTO.getHasParticulars()) {
                    Double totalAmount = 0.0;

                    for(SaveDTO.Particular particularDTO : saveDTO.getParticulars()) {
                        Particular particular = new Particular();

                        CostHeader particularCostHeader = costHeaderRepository.findByName(particularDTO.getName());

                        if(particularCostHeader != null) {
                            particular.setCostHeader(particularCostHeader);
                            particular.setAmount(particularDTO.getAmount());

                            transactionDefinition.addParticular(particular);

                            totalAmount += particular.getAmount();
                        } else {
                            throw new EntityNotFoundException("Cost header not found for particular name.");
                        }
                    }

                    transactionDefinition.setAmount(totalAmount);
                } else {
                    transactionDefinition.setAmount(saveDTO.getAmount());
                }

                transactionDefinitionRepository.save(transactionDefinition);

                if(transactionDefinition.getParticulars().size() > 0) {
                    for(Particular particular : transactionDefinition.getParticulars()) {
                        particularRepository.save(particular);
                    }
                }
            } else {
                throw new EntityNotFoundException("Cost header not found.");
            }
        } else {
            throw new EntityNotFoundException("Transaction definition not found.");
        }
    }
}
