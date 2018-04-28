package com.sms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Set;

@Entity
public class User {
	public enum Status {
		PENDING_VERIFICATION,
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

	@JsonIgnore
	@Column
	private String password;

	@JsonIgnore
	@Column
	private String otp;

	@JsonIgnore
	@Column
	private String token;

	@JsonIgnore
	@OneToMany
	private Set<Room> rooms;
	
	@Column
	private ZonedDateTime residingFrom;
	
	@Column
	private Role role;

	@Column
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

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Set<Room> getRooms() {
		return rooms;
	}
	
	public void addRoom(Room room) {
		rooms.add(room);
	}

	public void setRooms(Set<Room> rooms) {
		this.rooms = rooms;
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
