package com.sms.accounting.service.impl;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.accounting.dto.transactiondefinition.SaveDTO;
import com.sms.accounting.entity.Account;
import com.sms.accounting.entity.CostHeader;
import com.sms.accounting.entity.Particular;
import com.sms.accounting.entity.TransactionDefinition;
import com.sms.accounting.repository.CostHeaderRepository;
import com.sms.accounting.repository.ParticularRepository;
import com.sms.accounting.repository.TransactionDefinitionRepository;
import com.sms.accounting.service.TransactionDefinitionService;
import com.sms.accounting.validation.TransactionDefinitionServiceValidator;

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
            transactionDefinition.setTransactionFrom(Account.Type.valueOf(saveDTO.getTransactionFrom()));
            transactionDefinition.setTransactionTo(Account.Type.valueOf(saveDTO.getTransactionTo()));
            transactionDefinition.setInterval(TransactionDefinition.Interval.valueOf(saveDTO.getInterval()));
            transactionDefinition.setFrom(ZonedDateTime.parse(saveDTO.getFrom(), dateTimeFormatter));

            if(saveDTO.getHasParticulars()) {
                Double totalAmount = 0.0;

                for(SaveDTO.Particular particularDTO : saveDTO.getParticulars()) {
                    Particular particular = new Particular();

                    CostHeader particularCostHeader = costHeaderRepository.findByName(particularDTO.getCostHeader().getName());

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
                transactionDefinition.setTransactionFrom(Account.Type.valueOf(saveDTO.getTransactionFrom()));
                transactionDefinition.setTransactionTo(Account.Type.valueOf(saveDTO.getTransactionTo()));
                transactionDefinition.setInterval(TransactionDefinition.Interval.valueOf(saveDTO.getInterval()));
                transactionDefinition.setFrom(ZonedDateTime.parse(saveDTO.getFrom(), dateTimeFormatter));

                if(saveDTO.getHasParticulars()) {
                    Double totalAmount = 0.0;

                    for(SaveDTO.Particular particularDTO : saveDTO.getParticulars()) {
                        Particular particular = new Particular();

                        CostHeader particularCostHeader = costHeaderRepository.findByName(particularDTO.getCostHeader().getName());

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
