package com.sms.society.service;

import com.sms.society.entity.Resident;
import com.sms.society.entity.Room;
import com.sms.user.entity.User;

public interface ResidentService {
	public Resident addMember(Room room, User user) throws Exception;
	
	public Resident addTenant(Room room, User user) throws Exception;
}
