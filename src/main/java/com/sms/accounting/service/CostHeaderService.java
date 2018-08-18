package com.sms.accounting.service;

import java.util.List;

import com.sms.accounting.dto.costheader.SaveDTO;
import com.sms.common.model.StringKeyMap;

public interface CostHeaderService {
	public List<StringKeyMap> readAll() throws Exception;
	
	public StringKeyMap read(Long id) throws Exception;
	
    public StringKeyMap create(SaveDTO saveDTO) throws Exception;

    public StringKeyMap update(Long id, SaveDTO saveDTO) throws Exception;
    
    public void delete(Long id) throws Exception;
}
