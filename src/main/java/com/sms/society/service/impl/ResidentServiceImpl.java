package com.sms.society.service.impl;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.common.service.SecurityService;
import com.sms.society.entity.Resident;
import com.sms.society.entity.ResidentRoom;
import com.sms.society.entity.Room;
import com.sms.society.repository.ResidentRepository;
import com.sms.society.repository.ResidentRoomRepository;
import com.sms.society.service.ResidentService;
import com.sms.user.entity.User;

@Service
public class ResidentServiceImpl implements ResidentService {
	@Autowired
	private ResidentRepository residentRepository;
	
	@Autowired
	private ResidentRoomRepository residentRoomRepository;
	
	@Autowired
	private SecurityService securityService;
	
	public boolean hasMember(Room room) {
		Set<ResidentRoom> residentRooms = room.getResidentRooms();
		
		for(ResidentRoom residentRoom : residentRooms) {
			if(residentRoom.getResidentType() == Resident.Type.MEMBER && residentRoom.getStatus() == ResidentRoom.Status.ACTIVE) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean hasTenant(Room room) {
		Set<ResidentRoom> residentRooms = room.getResidentRooms();
		
		for(ResidentRoom residentRoom : residentRooms) {
			if(residentRoom.getResidentType() == Resident.Type.TENANT && residentRoom.getStatus() == ResidentRoom.Status.ACTIVE) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public Resident addMember(Room room, User user) throws Exception {
		List<ResidentRoom> residentRooms = residentRoomRepository.findByRoomIdAndMobile(room.getId(), user.getMobile());
		
		if(residentRooms.size() == 0 && !hasMember(room)) {
			Resident resident = new Resident();
			
			resident.setRole(Resident.Role.NA);
			resident.setSociety(room.getWing().getSociety());
			resident.setOtp(securityService.generateOTP());
			resident.setUser(user);
			
			resident = residentRepository.save(resident);
			
			ResidentRoom residentRoom = new ResidentRoom();
			
			residentRoom.setResident(resident);
			residentRoom.setRoom(room);
			residentRoom.setResidentType(Resident.Type.MEMBER);
			residentRoom.setStatus(ResidentRoom.Status.UNAPPROVED);
			
			residentRoomRepository.save(residentRoom);
			
			return resident;
		} else {
			throw new EntityExistsException("Registration for the requested room and mobile already exists!");
		}
	}

	@Override
	public Resident addTenant(Room room, User user) throws Exception {
		List<ResidentRoom> residentRooms = residentRoomRepository.findByRoomIdAndMobile(room.getId(), user.getMobile());
		
		if(residentRooms.size() == 0 && !hasTenant(room)) {
			Resident resident = new Resident();
			
			resident.setRole(Resident.Role.NA);
			resident.setSociety(room.getWing().getSociety());
			resident.setOtp(securityService.generateOTP());
			resident.setUser(user);
			
			resident = residentRepository.save(resident);
			
			ResidentRoom residentRoom = new ResidentRoom();
			
			residentRoom.setResident(resident);
			residentRoom.setRoom(room);
			residentRoom.setResidentType(Resident.Type.TENANT);
			residentRoom.setStatus(ResidentRoom.Status.UNAPPROVED);
			
			residentRoomRepository.save(residentRoom);
			
			return resident;
		} else {
			throw new EntityExistsException("Room already has tenant.");
		}
	}
}
