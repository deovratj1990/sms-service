package com.sms.common.service.impl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sms.common.dto.ResponseDTO;
import com.sms.common.model.Token;
import com.sms.common.service.SecurityService;
import com.sms.user.entity.User;
import com.sms.user.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class SecurityServiceImpl implements SecurityService {
	@Value("${com.sms.otp.values}")
	private String OTP_VALUES;
	
	@Value("${com.sms.otp.length}")
	private int OTP_LENGTH;
	
	@Value("${com.sms.token.secret}")
	private String TOKEN_SECRET;
	
	@Value("${com.sms.user.public-uris}")
	private String PUBLIC_URIS;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private HttpServletResponse response;
	
	@Autowired
    private UserService userService;
	
	@Override
	public String generateHash(String text) throws Exception {
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		
		byte[] hashBytes = messageDigest.digest(text.getBytes(StandardCharsets.UTF_8));
		
		StringBuffer hashStringBuffer = new StringBuffer();
		
		for(byte hashByte : hashBytes) {
			hashStringBuffer.append(hashByte);
		}
		
		return hashStringBuffer.toString();
	}
	
	@Override
	public boolean validateHash(String hash, String text) throws Exception {
		String textHash = generateHash(text);
		
		return textHash.equals(hash);
	}
	
	@Override
	public String generateOTP() {
		Random random = new Random();

		StringBuilder otp = new StringBuilder();

		for(int index = 0; index < OTP_LENGTH; index++) {
			int nextCharPosition = random.nextInt(OTP_VALUES.length());

			otp.append(OTP_VALUES.charAt(nextCharPosition));
		}

		return otp.toString();
	}
	
	@Override
	public String generateToken(Token token) {
		Claims claims = Jwts.claims();

		claims.putAll(token);

		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, TOKEN_SECRET).compact();
	}
	
	@Override
	public boolean isAuthorizationRequired() {
		if(PUBLIC_URIS != null) {
			String[] publicUris = PUBLIC_URIS.split("\\|");
			
			for(String publicUri : publicUris) {
				if(request.getRequestURI().equals(publicUri)) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	@Override
	public Token parseToken() throws Exception {
		String authorization = request.getHeader("Authorization");

        if(authorization != null) {
            String[] authorizationComponents = authorization.trim().split(" ");

            if(authorizationComponents.length == 2) {
                String authorizationType = authorizationComponents[0];
                String authorizationToken = authorizationComponents[1];

                if(authorizationType.toUpperCase().equals("BEARER")) {
                	Claims claims = Jwts.parser().setSigningKey(TOKEN_SECRET).parseClaimsJws(authorizationToken).getBody();
                	
                	Token token = new Token();
                    
                	token.setAuthorizationType(authorizationType);
                	token.setText(authorizationToken);
                	token.putAll(claims);
                    
                    return token;
                } else {
                	throw new Exception("Invalid authorization type.");
                }
            } else {
                throw new Exception("Invalid authorization token.");
            }
        } else {
            throw new Exception("Missing authorization token.");
        }
	}
	
	@Override
	public void sendTokenErrorResponse(String message) throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();

        responseDTO.setCode(HttpStatus.UNAUTHORIZED.value());
        responseDTO.setMessage(message);

        ObjectMapper objectMapper = new ObjectMapper();

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(objectMapper.writeValueAsString(responseDTO));
        response.getWriter().close();
	}
	
	@Override
	public void authorizeRequest() throws Exception {
		if(isAuthorizationRequired()) {
			try {
				Token token = parseToken();
				
				User user = userService.getUserById(token.getUserId());
				
				request.setAttribute("token", token);
				request.setAttribute("user", user);
			} catch(Exception ex) {
				sendTokenErrorResponse(ex.getMessage());
			}
		}
	}
	
	@Override
	public Token getRequestToken() {
		return (Token) request.getAttribute("token");
	}
	
	@Override
	public User getRequestUser() {
		return (User) request.getAttribute("user");
	}
}
