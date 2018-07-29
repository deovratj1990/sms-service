package com.sms.society.service;

import java.util.List;
import java.util.Set;

import com.sms.common.model.StringKeyMap;
import com.sms.society.dto.society.RegisterDTO;
import com.sms.society.entity.Resident;
import com.sms.society.entity.Room;
import com.sms.user.entity.User;

public interface SocietyService {
    public StringKeyMap getById(Long id) throws Exception;

    public List<StringKeyMap> getAll() throws Exception;

    public void register(RegisterDTO registerDTO) throws Exception;
    
    public Resident addMember(Room room, User user) throws Exception;
	
	public Resident addTenant(Room room, User user) throws Exception;
    
    public Set<StringKeyMap> getRegistrationsBySocietyId(Long societyId) throws Exception;
    
    public void activateRegistration(Long residentRoomId) throws Exception;
    
    public void deleteRegistration(Long residentRoomId) throws Exception;
}
