package com.sms.society.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ResidentRoom {
	public enum Status {
		UNAPPROVED,
		ACTIVE,
		INACTIVE,
		DELETED
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private Resident resident;
	
	@ManyToOne
	private Room room;
	
	@Enumerated(EnumType.STRING)
	private Resident.Type residentType;
	
	@Column
	private Date residingFrom;
	
	@Enumerated(EnumType.STRING)
	private Status status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Resident getResident() {
		return resident;
	}

	public void setResident(Resident resident) {
		this.resident = resident;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Resident.Type getResidentType() {
		return residentType;
	}

	public void setResidentType(Resident.Type residentType) {
		this.residentType = residentType;
	}
	
	public Date getResidingFrom() {
		return residingFrom;
	}

	public void setResidingFrom(Date residingFrom) {
		this.residingFrom = residingFrom;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
