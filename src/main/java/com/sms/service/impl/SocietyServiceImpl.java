package com.sms.service.impl;

import com.sms.EntityNotFoundException;
import com.sms.dto.MapDTO;
import com.sms.entity.Society;
import com.sms.repository.SocietyRepository;
import com.sms.service.SocietyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SocietyServiceImpl implements SocietyService {
    @Autowired
    private SocietyRepository societyRepository;

    private MapDTO societyToMapDTO(Society society) {
        if(society != null) {
            MapDTO map = new MapDTO();

            map.put("id", society.getId());
            map.put("name", society.getName());
            map.put("wingCount", society.getWingCount());
            map.put("roomCount", society.getRoomCount());
            map.put("localityId", society.getLocality().getId());
            map.put("status", society.getStatus());

            return map;
        } else {
            return null;
        }
    }

    @Override
    public MapDTO getSocietyById(Long id) throws Exception {
        Society society = societyRepository.findById(id).get();

        if(society != null) {
            return societyToMapDTO(society);
        } else {
            throw new EntityNotFoundException("No Data.");
        }
    }

    @Override
    public List<MapDTO> getAllSocieties() throws Exception {
        List<Society> societies = societyRepository.findAll();

        if(societies.size() != 0) {
            List<MapDTO> maps = new ArrayList<MapDTO>();

            for(Society society : societies) {
                maps.add(societyToMapDTO(society));
            }

            return maps;
        } else {
            throw new EntityNotFoundException("No Data.");
        }
    }
}
