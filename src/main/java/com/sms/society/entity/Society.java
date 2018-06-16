package com.sms.society.entity;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.sms.accounting.entity.Account;
import com.sms.address.entity.Locality;
import com.sms.common.converter.ZonedDateTimeConverter;
import com.sms.user.entity.Admin;

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
	
	@Column
	private String name;
	
	@ManyToOne
	private Locality locality;
	
	@Column
	private Integer wingCount;
	
	@Column
	private Integer roomCount;
	
	@OneToOne
	private Account account;

	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Column
	@Convert(converter = ZonedDateTimeConverter.class)
	private ZonedDateTime createdOn;
	
	@OneToOne
	private Admin createdBy;

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
	
	public Locality getLocality() {
		return locality;
	}

	public void setLocality(Locality locality) {
		this.locality = locality;
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

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
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
