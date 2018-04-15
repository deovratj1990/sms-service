package com.sms.entity;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {
	public enum Status {
		REGISTERED,
		ACTIVE,
		INACTIVE,
		DELETED
	}
	
	public enum Role {
		CHAIRMAN,
		SECRETARY,
		TREASURER,
		MEMBER,
		TENANT,
		STAFF
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private String mobile;
	
	@Column
	private String password;
	
	@OneToMany
	private Room room;
	
	@Column
	private ZonedDateTime residingFrom;
	
	@Column
	private Role role;
	
	private Status status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public ZonedDateTime getResidingFrom() {
		return residingFrom;
	}

	public void setResidingFrom(ZonedDateTime residingFrom) {
		this.residingFrom = residingFrom;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
