package com.sms.service.impl;

import com.sms.EntityNotFoundException;
import com.sms.dto.MapDTO;
import com.sms.entity.Room;
import com.sms.repository.RoomRepository;
import com.sms.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;

    private MapDTO roomToMapDTO(Room room) {
        if(room != null) {
            MapDTO map = new MapDTO();

            map.put("id", room.getId());
            map.put("name", room.getName());
            map.put("wingId", room.getWing().getId());

            return map;
        } else {
            return null;
        }
    }

    @Override
    public MapDTO getRoomByRoomId(Long roomId) throws Exception {
        Room room = roomRepository.findById(roomId).get();

        if(room != null) {
            return roomToMapDTO(room);
        } else {
            throw new EntityNotFoundException("No Data.");
        }
    }

    @Override
    public List<MapDTO> getRoomsBySocietyId(Long societyId) throws Exception {
        List<Room> rooms = roomRepository.findBySocietyId(societyId);

        if(rooms.size() != 0) {
            List<MapDTO> maps = new ArrayList<MapDTO>();

            for(Room room : rooms) {
                maps.add(roomToMapDTO(room));
            }

            return maps;
        } else {
            throw new EntityNotFoundException("No Data.");
        }
    }
}
