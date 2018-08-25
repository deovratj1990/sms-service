package com.sms.common.service;

import com.sms.common.model.Token;
import com.sms.user.entity.User;

public interface SecurityService {
	public String generateHash(String text) throws Exception;
	
	public boolean validateHash(String hash, String text) throws Exception;
	
	public String generateOTP();
	
	public String generateToken(Token token);
	
	public boolean isAuthorizationRequired();
	
	public Token parseToken() throws Exception;
	
	public void sendTokenErrorResponse(String message) throws Exception;
	
	public boolean authorizeRequest() throws Exception;
	
	public Token getRequestToken();
	
	public User getRequestUser();
}
