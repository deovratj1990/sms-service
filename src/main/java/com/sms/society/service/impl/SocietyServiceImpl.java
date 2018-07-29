package com.sms.society.service.impl;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.common.model.StringKeyMap;
import com.sms.common.service.SecurityService;
import com.sms.society.dto.society.RegisterDTO;
import com.sms.society.entity.Resident;
import com.sms.society.entity.ResidentRoom;
import com.sms.society.entity.Room;
import com.sms.society.entity.Society;
import com.sms.society.entity.Wing;
import com.sms.society.repository.ResidentRepository;
import com.sms.society.repository.ResidentRoomRepository;
import com.sms.society.repository.RoomRepository;
import com.sms.society.repository.SocietyRepository;
import com.sms.society.repository.WingRepository;
import com.sms.society.service.SocietyService;
import com.sms.society.validation.SocietyServiceValidator;
import com.sms.user.entity.User;

@Service
public class SocietyServiceImpl implements SocietyService {
    @Autowired
    private SocietyServiceValidator validator;

    @Autowired
    private SocietyRepository societyRepository;

    @Autowired
    private WingRepository wingRepository;

    @Autowired
    private RoomRepository roomRepository;
    
    @Autowired
	private ResidentRepository residentRepository;
	
	@Autowired
	private ResidentRoomRepository residentRoomRepository;
	
	@Autowired
	private SecurityService securityService;

    private StringKeyMap societyToMapDTO(Society society) {
        if(society != null) {
            StringKeyMap map = new StringKeyMap();

            map.put("id", society.getId());
            map.put("name", society.getName());
            map.put("wingCount", society.getWingCount());
            map.put("roomCount", society.getRoomCount());
            map.put("status", society.getStatus());

            return map;
        } else {
            return null;
        }
    }

    @Override
    public StringKeyMap getById(Long id) throws Exception {
        Society society = societyRepository.findById(id).get();

        if(society != null) {
            return societyToMapDTO(society);
        } else {
            throw new EntityNotFoundException("No Data.");
        }
    }

    @Override
    public List<StringKeyMap> getAll() throws Exception {
        List<Society> societies = societyRepository.findAll();

        if(societies.size() != 0) {
            List<StringKeyMap> maps = new ArrayList<StringKeyMap>();

            for(Society society : societies) {
                maps.add(societyToMapDTO(society));
            }

            return maps;
        } else {
            throw new EntityNotFoundException("No Data.");
        }
    }

    @Override
    public void register(RegisterDTO registerDTO) throws Exception {
        validator.validateRegister(registerDTO);

        Society society = new Society();

        society.setName(registerDTO.getName());
        society.setWingCount(registerDTO.getWings().size());
        society.setRoomCount(0);
        society.setCreatedOn(ZonedDateTime.now());
        society.setStatus(Society.Status.PENDING_VERIFICATION);

        society = societyRepository.save(society);

        for(RegisterDTO.Wing wingDTO : registerDTO.getWings()) {
            Wing wing = new Wing();

            wing.setName(wingDTO.getName());
            wing.setSociety(society);

            wing = wingRepository.save(wing);

            List<Room> rooms = new ArrayList<Room>();

            for(String roomName : wingDTO.getRooms()) {
                Room room = new Room();

                room.setName(roomName);
                room.setWing(wing);

                rooms.add(room);
            }

            roomRepository.saveAll(rooms);

            society.setRoomCount(rooms.size());

            society = societyRepository.save(society);
        }
    }
    
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
			
			resident.setMobile(user.getMobile());
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
			
			resident.setMobile(user.getMobile());
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
    
    @Override
	public Set<StringKeyMap> getRegistrationsBySocietyId(Long societyId) throws Exception {
    	List<StringKeyMap> registrationList = societyRepository.findRegistrationsBySocietyId(societyId);
    	
    	StringKeyMap roomsRegistrations = new StringKeyMap();
    	
    	for(StringKeyMap registration : registrationList) {
    		Set<StringKeyMap> roomRegistrations = roomsRegistrations.getSet(registration.getString("roomName"));
    		
    		if(roomRegistrations == null) {
    			roomRegistrations = new HashSet<StringKeyMap>();
    			
    			roomsRegistrations.put(registration.getString("roomName"), roomRegistrations);
    		}
    		
    		StringKeyMap roomRegistration = new StringKeyMap();
    		
    		roomRegistration.put("residentRoomId", registration.getLong("residentRoomId"));
    		roomRegistration.put("residentMobile", registration.getString("residentMobile"));
    		
    		roomRegistrations.add(roomRegistration);
    	}
    	
    	Set<StringKeyMap> registrations = new HashSet<StringKeyMap>();
    	
    	for(String key : roomsRegistrations.keySet()) {
    		StringKeyMap registration = new StringKeyMap();
    		
    		registration.put("roomName", key);
    		registration.put("registrations", roomsRegistrations.get(key));
    		
    		registrations.add(registration);
    	}
    	
		return registrations;
	}

	@Override
	public void activateRegistration(Long residentRoomId) throws Exception {
		ResidentRoom residentRoom = residentRoomRepository.getOne(residentRoomId);
		
		if(residentRoom != null) {
			if(!hasMember(residentRoom.getRoom())) {
				residentRoom.setStatus(ResidentRoom.Status.ACTIVE);
				
				residentRoomRepository.save(residentRoom);
			} else {
				throw new EntityExistsException("Requested room already has active member!");
			}
		} else {
			throw new EntityNotFoundException("Registration not found!");
		}
	}

	@Override
	public void deleteRegistration(Long residentRoomId) throws Exception {
		ResidentRoom residentRoom = residentRoomRepository.getOne(residentRoomId);
		
		if(residentRoom != null) {
			if(residentRoom.getStatus() == ResidentRoom.Status.UNAPPROVED) {
				residentRoomRepository.deleteById(residentRoomId);
			} else {
				throw new Exception("Only unapproved resident can be deleted!");
			}
		} else {
			throw new EntityNotFoundException("Registration not found!");
		}
	}
}
