package com.sms.accounting.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.accounting.dto.costheader.SaveDTO;
import com.sms.accounting.entity.CostHeader;
import com.sms.accounting.repository.CostHeaderRepository;
import com.sms.accounting.service.CostHeaderService;
import com.sms.accounting.validation.CostHeaderServiceValidator;
import com.sms.common.model.StringKeyMap;
import com.sms.common.service.SecurityService;
import com.sms.society.entity.Society;
import com.sms.society.repository.SocietyRepository;

@Service
public class CostHeaderServiceImpl implements CostHeaderService {
    @Autowired
    private CostHeaderServiceValidator validator;
    
    @Autowired
    private SocietyRepository societyRepository;

    @Autowired
    private CostHeaderRepository costHeaderRepository;
    
    @Autowired
    private SecurityService securityService;
    
    @Override
	public List<StringKeyMap> readAll() throws Exception {
    	List<CostHeader> costHeaders = costHeaderRepository.findAll();
    	
    	List<StringKeyMap> data = new ArrayList<StringKeyMap>();
    	
    	for(CostHeader costHeader : costHeaders) {
    		StringKeyMap datum = new StringKeyMap();
    		
    		datum.put("id", costHeader.getId());
    		datum.put("name", costHeader.getName());
    		
    		data.add(datum);
    	}
    	
    	return data;
	}
    
    @Override
	public StringKeyMap read(Long id) throws Exception {
    	CostHeader costHeader = costHeaderRepository.findById(id).get();
    	
    	StringKeyMap data = new StringKeyMap();
    	
    	data.put("id", costHeader.getId());
    	data.put("name", costHeader.getName());
    	
		return data;
	}

    @Override
    public StringKeyMap create(SaveDTO saveDTO) throws Exception {
        validator.validateSave(saveDTO);
        
        Long societyId = securityService.getRequestToken().getSocietyId();
        
        Society society = societyRepository.findById(societyId).get();

        CostHeader costHeader = new CostHeader();

        costHeader.setName(saveDTO.getName());
        costHeader.setStatus(CostHeader.Status.ACTIVE);
        costHeader.setSociety(society);

        costHeader = costHeaderRepository.save(costHeader);
        
        StringKeyMap data = new StringKeyMap();
        
        data.put("id", costHeader.getId());
        data.put("name", costHeader.getName());
        
        return data;
    }

    @Override
    public StringKeyMap update(Long id, SaveDTO saveDTO) throws Exception {
        validator.validateSave(saveDTO);

        CostHeader costHeader = costHeaderRepository.findById(id).get();

        if(costHeader != null) {
            costHeader.setName(saveDTO.getName());

            costHeader = costHeaderRepository.save(costHeader);
            
            StringKeyMap data = new StringKeyMap();
            
            data.put("id", costHeader.getId());
            data.put("name", costHeader.getName());
            
            return data;
        } else {
            throw new EntityNotFoundException("Cost header not found.");
        }
    }

	@Override
	public void delete(Long id) throws Exception {
		costHeaderRepository.deleteById(id);
	}
}
