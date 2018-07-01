package com.sms.society.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.common.model.StringKeyMap;
import com.sms.society.entity.Room;
import com.sms.society.repository.RoomRepository;
import com.sms.society.service.RoomService;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;

    private StringKeyMap roomToMapDTO(Room room) {
        if(room != null) {
            StringKeyMap map = new StringKeyMap();

            map.put("id", room.getId());
            map.put("name", room.getName());
            map.put("wingId", room.getWing().getId());

            return map;
        } else {
            return null;
        }
    }

    @Override
    public StringKeyMap getRoomByRoomId(Long roomId) throws Exception {
        Room room = roomRepository.findById(roomId).get();

        if(room != null) {
            return roomToMapDTO(room);
        } else {
            throw new EntityNotFoundException("No Data.");
        }
    }

    @Override
    public List<StringKeyMap> getRoomsBySocietyId(Long societyId) throws Exception {
        List<Room> rooms = roomRepository.findBySocietyId(societyId);

        if(rooms.size() != 0) {
            List<StringKeyMap> maps = new ArrayList<StringKeyMap>();

            for(Room room : rooms) {
                maps.add(roomToMapDTO(room));
            }

            return maps;
        } else {
            throw new EntityNotFoundException("No Data.");
        }
    }
}
