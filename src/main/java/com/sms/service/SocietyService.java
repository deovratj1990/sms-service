package com.sms.service;

import com.sms.dto.MapDTO;

import java.util.List;

public interface SocietyService {
    public MapDTO getSocietyById(Long id) throws Exception;

    public List<MapDTO> getAllSocieties() throws Exception;
}
