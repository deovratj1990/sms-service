package com.sms.society.service.impl;

import java.util.Set;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.common.service.SecurityService;
import com.sms.society.entity.Resident;
import com.sms.society.entity.Room;
import com.sms.society.repository.ResidentRepository;
import com.sms.society.service.ResidentService;
import com.sms.user.entity.User;

@Service
public class ResidentServiceImpl implements ResidentService {
	@Autowired
	private ResidentRepository residentRepository;
	
	@Autowired
	private SecurityService securityService;
	
	@Override
	public Resident addMember(Room room, User user) throws Exception {
		Set<Resident> roomResidents = room.getResidents();
		
		boolean hasMember = false;
		
		for(Resident roomResident : roomResidents) {
			if(roomResident.getType() == Resident.Type.MEMBER) {
				hasMember = true;
				
				break;
			}
		}
		
		if(!hasMember) {
			Resident resident = new Resident();
			
			resident.setType(Resident.Type.MEMBER);
			resident.setRole(Resident.Role.NA);
			resident.setRoom(room);
			resident.setOtp(securityService.generateOTP());
			resident.setUser(user);
			resident.setStatus(Resident.Status.UNAPPROVED);
			
			resident = residentRepository.save(resident);
			
			return resident;
		} else {
			throw new EntityExistsException("Room already has member.");
		}
	}

	@Override
	public Resident addTenant(Room room, User user) throws Exception {
		Resident resident = new Resident();
		
		resident.setType(Resident.Type.TENANT);
		resident.setRole(Resident.Role.NA);
		resident.setRoom(room);
		resident.setOtp(securityService.generateOTP());
		resident.setUser(user);
		resident.setStatus(Resident.Status.UNAPPROVED);
		
		resident = residentRepository.save(resident);
		
		return resident;
	}
}
