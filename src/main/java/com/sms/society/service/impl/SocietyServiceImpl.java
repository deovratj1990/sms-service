package com.sms.society.service.impl;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.common.model.StringKeyMap;
import com.sms.society.dto.society.RegisterDTO;
import com.sms.society.entity.Room;
import com.sms.society.entity.Society;
import com.sms.society.entity.Wing;
import com.sms.society.repository.RoomRepository;
import com.sms.society.repository.SocietyRepository;
import com.sms.society.repository.WingRepository;
import com.sms.society.service.SocietyService;
import com.sms.society.validation.SocietyServiceValidator;

@Service
public class SocietyServiceImpl implements SocietyService {
    @Autowired
    private SocietyServiceValidator validator;

    @Autowired
    private SocietyRepository societyRepository;

    @Autowired
    private WingRepository wingRepository;

    @Autowired
    private RoomRepository roomRepository;

    private StringKeyMap societyToMapDTO(Society society) {
        if(society != null) {
            StringKeyMap map = new StringKeyMap();

            map.put("id", society.getId());
            map.put("name", society.getName());
            map.put("wingCount", society.getWingCount());
            map.put("roomCount", society.getRoomCount());
            map.put("status", society.getStatus());

            return map;
        } else {
            return null;
        }
    }

    @Override
    public StringKeyMap getById(Long id) throws Exception {
        Society society = societyRepository.findById(id).get();

        if(society != null) {
            return societyToMapDTO(society);
        } else {
            throw new EntityNotFoundException("No Data.");
        }
    }

    @Override
    public List<StringKeyMap> getAll() throws Exception {
        List<Society> societies = societyRepository.findAll();

        if(societies.size() != 0) {
            List<StringKeyMap> maps = new ArrayList<StringKeyMap>();

            for(Society society : societies) {
                maps.add(societyToMapDTO(society));
            }

            return maps;
        } else {
            throw new EntityNotFoundException("No Data.");
        }
    }

    @Override
    public void register(RegisterDTO registerDTO) throws Exception {
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
