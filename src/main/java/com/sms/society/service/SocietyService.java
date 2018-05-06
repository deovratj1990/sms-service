package com.sms.society.service;

import com.sms.common.dto.MapDTO;
import com.sms.society.controller.dto.society.RegisterDTO;

import java.util.List;

public interface SocietyService {
    public MapDTO getSocietyById(Long id) throws Exception;

    public List<MapDTO> getAllSocieties() throws Exception;

    public void registerSociety(RegisterDTO registerDTO) throws Exception;
}
