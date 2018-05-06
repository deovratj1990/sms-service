package com.sms.society.service;

import com.sms.common.dto.MapDTO;

import java.util.List;

public interface RoomService {
    public MapDTO getRoomByRoomId(Long roomId) throws Exception;

    public List<MapDTO> getRoomsBySocietyId(Long societyId) throws Exception;
}
