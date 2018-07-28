package com.sms.user.dto.user;

public class RegisterDTO {
	private String mobile;
	
	private Long societyId;
	
	private Long roomId;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getSocietyId() {
		return societyId;
	}

	public void setSocietyId(Long societyId) {
		this.societyId = societyId;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}
}
