package com.sms.society.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.sms.accounting.entity.Account;

@Entity
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String name;
	
	@ManyToOne
	private Wing wing;
	
	@OneToMany(mappedBy = "room")
	private Set<ResidentRoom> residentRooms;
	
	@OneToOne
	private Account account;
	
	public Room() {
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

	public Wing getWing() {
		return wing;
	}

	public void setWing(Wing wing) {
		this.wing = wing;
	}
	
	public Set<ResidentRoom> getResidentRooms() {
		return residentRooms;
	}

	public void setResidentRooms(Set<ResidentRoom> residentRooms) {
		this.residentRooms = residentRooms;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}
