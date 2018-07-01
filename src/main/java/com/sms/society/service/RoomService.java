package com.sms.society.service;

import java.util.List;

import com.sms.common.model.StringKeyMap;

public interface RoomService {
    public StringKeyMap getRoomByRoomId(Long roomId) throws Exception;

    public List<StringKeyMap> getRoomsBySocietyId(Long societyId) throws Exception;
}
