package com.sms.society.service.impl;

import com.sms.address.repository.LocalityRepository;
import com.sms.common.dto.MapDTO;
import com.sms.society.controller.dto.society.RegisterDTO;
import com.sms.society.entity.Room;
import com.sms.society.entity.Society;
import com.sms.society.entity.Wing;
import com.sms.society.repository.RoomRepository;
import com.sms.society.repository.SocietyRepository;
import com.sms.society.repository.WingRepository;
import com.sms.society.service.SocietyService;
import com.sms.society.validation.SocietyServiceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SocietyServiceImpl implements SocietyService {
    @Autowired
    private SocietyServiceValidator validator;

    @Autowired
    private LocalityRepository localityRepository;

    @Autowired
    private SocietyRepository societyRepository;

    @Autowired
    private WingRepository wingRepository;

    @Autowired
    private RoomRepository roomRepository;

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

    @Override
    public void registerSociety(RegisterDTO registerDTO) throws Exception {
        validator.validateRegister(registerDTO);

        Society society = new Society();

        society.setName(registerDTO.getName());
        society.setWingCount(registerDTO.getWings().size());
        society.setRoomCount(0);
        society.setCreatedOn(ZonedDateTime.now());
        society.setStatus(Society.Status.PENDING_VERIFICATION);

        society = societyRepository.save(society);

        for(RegisterDTO.Wing wingDTO : registerDTO.getWings()) {
            Wing wing = new Wing();

            wing.setName(wingDTO.getName());
            wing.setSociety(society);

            wing = wingRepository.save(wing);

            List<Room> rooms = new ArrayList<Room>();

            for(String roomName : wingDTO.getRooms()) {
                Room room = new Room();

                room.setName(roomName);
                room.setWing(wing);

                rooms.add(room);
            }

            roomRepository.saveAll(rooms);

            society.setRoomCount(rooms.size());

            society = societyRepository.save(society);
        }
    }
}
