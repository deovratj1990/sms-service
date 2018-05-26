package com.sms.society.entity;

import com.sms.user.entity.Admin;
import com.sms.address.entity.Locality;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
public class Society {
	public enum Status {
		PENDING_VERIFICATION,
		ACTIVE,
		INACTIVE,
		DELETED
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private Locality locality;
	
	@Column
	private String name;
	
	@Column
	private Integer wingCount;
	
	@Column
	private Integer roomCount;

	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Column
	private ZonedDateTime createdOn;
	
	@OneToOne
	private Admin createdBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Locality getLocality() {
		return locality;
	}

	public void setLocality(Locality locality) {
		this.locality = locality;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getWingCount() {
		return wingCount;
	}

	public void setWingCount(Integer wingCount) {
		this.wingCount = wingCount;
	}

	public Integer getRoomCount() {
		return roomCount;
	}

	public void setRoomCount(Integer roomCount) {
		this.roomCount = roomCount;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public ZonedDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(ZonedDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public Admin getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Admin createdBy) {
		this.createdBy = createdBy;
	}
}
