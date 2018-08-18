package com.sms.accounting.service.impl;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.sms.common.model.StringKeyMap;

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
    
    private StringKeyMap generateData(TransactionDefinition transactionDefinition) {
    	StringKeyMap costHeaderData = new StringKeyMap();
        
        costHeaderData.put("id", transactionDefinition.getCostHeader().getId());
        costHeaderData.put("name", transactionDefinition.getCostHeader().getName());
        
        Set<StringKeyMap> particularsData = new HashSet<StringKeyMap>();
        
        for(Particular particular : transactionDefinition.getParticulars()) {
        	StringKeyMap particularCostHeaderData = new StringKeyMap();
        	
        	particularCostHeaderData.put("id", particular.getCostHeader().getId());
        	particularCostHeaderData.put("name", particular.getCostHeader().getName());
        	
        	StringKeyMap particularData = new StringKeyMap();
        	
        	particularData.put("id", particular.getId());
        	particularData.put("costHeader", particularCostHeaderData);
        	particularData.put("amount", particular.getAmount());
        	
        	particularsData.add(particularData);
        }
        
        StringKeyMap data = new StringKeyMap();
        
        data.put("costHeader", costHeaderData);
        data.put("fromAccountType", transactionDefinition.getFromAccountType());
        data.put("toAccountType", transactionDefinition.getToAccountType());
        data.put("interval", transactionDefinition.getInterval());
        data.put("applicableFrom", transactionDefinition.getApplicableFrom());
        data.put("hasParticulars", transactionDefinition.getHasParticulars());
        data.put("particulars", particularsData);
        data.put("amount", transactionDefinition.getAmount());
        
        return data;
    }
    
    @Override
	public StringKeyMap read(Long id) throws Exception {
    	TransactionDefinition transactionDefinition = transactionDefinitionRepository.findById(id).get();
    	
		return generateData(transactionDefinition);
	}
    
    @Override
	public List<StringKeyMap> readAll() throws Exception {
    	List<TransactionDefinition> transactionDefinitions = transactionDefinitionRepository.findAll();
    	
    	List<StringKeyMap> data = new ArrayList<StringKeyMap>();
    	
    	for(TransactionDefinition transactionDefinition : transactionDefinitions) {
    		data.add(generateData(transactionDefinition));
    	}
    	
		return data;
	}

    @Override
    public StringKeyMap create(SaveDTO saveDTO) throws Exception {
        validator.validateSave(saveDTO);

        TransactionDefinition transactionDefinition = new TransactionDefinition();

        CostHeader costHeader = costHeaderRepository.findById(saveDTO.getCostHeaderId()).get();

        if(costHeader != null) {
            transactionDefinition.setCostHeader(costHeader);
            transactionDefinition.setFromAccountType(Account.Type.valueOf(saveDTO.getFromAccountType()));
            transactionDefinition.setToAccountType(Account.Type.valueOf(saveDTO.getToAccountType()));
            transactionDefinition.setInterval(TransactionDefinition.Interval.valueOf(saveDTO.getInterval()));
            transactionDefinition.setApplicableFrom(ZonedDateTime.parse(saveDTO.getApplicableFrom(), dateTimeFormatter));
            transactionDefinition.setHasParticulars(saveDTO.getHasParticulars());

            if(saveDTO.getHasParticulars()) {
                Double totalAmount = 0.0;

                for(SaveDTO.Particular particularDTO : saveDTO.getParticulars()) {
                    Particular particular = new Particular();

                    CostHeader particularCostHeader = costHeaderRepository.findById(particularDTO.getCostHeaderId()).get();

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

            transactionDefinition = transactionDefinitionRepository.save(transactionDefinition);

            if(transactionDefinition.getParticulars().size() > 0) {
                for(Particular particular : transactionDefinition.getParticulars()) {
                    particularRepository.save(particular);
                }
            }
            
            return generateData(transactionDefinition);
        } else {
            throw new EntityNotFoundException("Cost header not found.");
        }
    }

    @Override
    public StringKeyMap update(Long id, SaveDTO saveDTO) throws Exception {
        TransactionDefinition transactionDefinition = transactionDefinitionRepository.findById(id).get();

        if(transactionDefinition != null) {
            validator.validateSave(saveDTO);

            CostHeader costHeader = costHeaderRepository.findById(saveDTO.getCostHeaderId()).get();

            if(costHeader != null) {
                transactionDefinition.setCostHeader(costHeader);
                transactionDefinition.setFromAccountType(Account.Type.valueOf(saveDTO.getFromAccountType()));
                transactionDefinition.setToAccountType(Account.Type.valueOf(saveDTO.getToAccountType()));
                transactionDefinition.setInterval(TransactionDefinition.Interval.valueOf(saveDTO.getInterval()));
                transactionDefinition.setApplicableFrom(ZonedDateTime.parse(saveDTO.getApplicableFrom(), dateTimeFormatter));
                transactionDefinition.setHasParticulars(saveDTO.getHasParticulars());

                if(saveDTO.getHasParticulars()) {
                    Double totalAmount = 0.0;

                    for(SaveDTO.Particular particularDTO : saveDTO.getParticulars()) {
                        Particular particular = new Particular();

                        CostHeader particularCostHeader = costHeaderRepository.findById(particularDTO.getCostHeaderId()).get();

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
                
                return generateData(transactionDefinition);
            } else {
                throw new EntityNotFoundException("Cost header not found.");
            }
        } else {
            throw new EntityNotFoundException("Transaction definition not found.");
        }
    }

	@Override
	public void delete(Long id) throws Exception {
		transactionDefinitionRepository.deleteById(id);
	}
}
