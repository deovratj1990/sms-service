package com.sms.dto.member;

public class LoginDTO {
    private String mobile;

    private String password;

    private Long accessId;

    private String otp;

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

    public Long getAccessId() {
        return accessId;
    }

    public void setAccessId(Long accessId) {
        this.accessId = accessId;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
