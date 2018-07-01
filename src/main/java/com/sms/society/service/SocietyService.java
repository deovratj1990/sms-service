package com.sms.society.service;

import com.sms.common.model.StringKeyMap;
import com.sms.society.dto.society.RegisterDTO;

import java.util.List;

public interface SocietyService {
    public StringKeyMap getSocietyById(Long id) throws Exception;

    public List<StringKeyMap> getAllSocieties() throws Exception;

    public void registerSociety(RegisterDTO registerDTO) throws Exception;
}
