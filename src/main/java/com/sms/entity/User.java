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
	@OneToMany
	private Set<Access> accesses;
	
	@Column
	private ZonedDateTime residingFrom;

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

	public Set<Access> getAccesses() {
		return accesses;
	}
	
	public void addAccess(Access room) {
		accesses.add(room);
	}

	public void setAccesses(Set<Access> accesses) {
		this.accesses = accesses;
	}

	public ZonedDateTime getResidingFrom() {
		return residingFrom;
	}

	public void setResidingFrom(ZonedDateTime residingFrom) {
		this.residingFrom = residingFrom;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
