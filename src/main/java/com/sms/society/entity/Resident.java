package com.sms.society.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.sms.user.entity.User;

@Entity
public class Resident {
	public enum Type {
		MEMBER,
		TENANT
	}
	
	public enum Role {
		NA,
		CHAIRMAN,
		SECRETARY,
		TRASURER
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String name;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@ManyToOne
	private Society society;
	
	@OneToMany(mappedBy = "resident")
	private Set<ResidentRoom> residentRooms;
	
	@Column
	private String otp;
	
	@OneToOne
	private User user;
	
	public Resident() {
		residentRooms = new HashSet<ResidentRoom>();
	}

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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Society getSociety() {
		return society;
	}

	public void setSociety(Society society) {
		this.society = society;
	}

	public Set<ResidentRoom> getResidentRooms() {
		return residentRooms;
	}
	
	public void addResidentRoom(ResidentRoom residentRoom) {
		residentRooms.add(residentRoom);
	}

	public void setResidentRooms(Set<ResidentRoom> residentRooms) {
		this.residentRooms = residentRooms;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
