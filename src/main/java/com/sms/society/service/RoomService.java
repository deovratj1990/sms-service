package com.sms.society.service;

import java.util.List;

import com.sms.common.model.StringKeyMap;

public interface RoomService {
    public StringKeyMap getById(Long roomId) throws Exception;

    public List<StringKeyMap> getBySocietyId(Long societyId) throws Exception;
    
    public List<StringKeyMap> getAll() throws Exception;
}
