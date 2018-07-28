package com.sms.society.service;

import java.util.List;

import com.sms.common.model.StringKeyMap;
import com.sms.society.dto.society.RegisterDTO;

public interface SocietyService {
    public StringKeyMap getById(Long id) throws Exception;

    public List<StringKeyMap> getAll() throws Exception;

    public void register(RegisterDTO registerDTO) throws Exception;
}
