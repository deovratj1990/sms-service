package com.sms.service;

import com.sms.dto.MapDTO;

import java.util.List;

public interface RoomService {
    public MapDTO getRoomByRoomId(Long roomId) throws Exception;

    public List<MapDTO> getRoomsBySocietyId(Long societyId) throws Exception;
}
