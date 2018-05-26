package com.sms.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sms.common.converter.ZonedDateTimeConverter;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.HashSet;
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
	@OneToMany(mappedBy = "user")
	private Set<Access> accesses;
	
	@Column
	@Convert(converter = ZonedDateTimeConverter.class)
	private ZonedDateTime residingFrom;

	@Enumerated(EnumType.STRING)
	private Status status;

	@Column
	@Convert(converter = ZonedDateTimeConverter.class)
	private ZonedDateTime createdOn;

	public User() {
		accesses = new HashSet<Access>();
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

	public ZonedDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(ZonedDateTime createdOn) {
		this.createdOn = createdOn;
	}
}
