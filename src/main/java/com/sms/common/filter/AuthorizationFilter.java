package com.sms.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sms.common.dto.ResponseDTO;
import com.sms.user.entity.User;
import com.sms.user.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthorizationFilter implements Filter {
    @Value("${com.sms.jwt.secret}")
    private String jwtSecret;

    @Value("${com.sms.user.register-uri}")
    private String registerUri;

    @Value("${com.sms.user.login-uri}")
    private String loginUri;

    @Autowired
    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        System.out.println(httpServletRequest.getRequestURI());

        if(!httpServletRequest.getRequestURI().equals(loginUri) && !httpServletRequest.getRequestURI().equals(registerUri)) {
            String authorization = httpServletRequest.getHeader("Authorization");

            try {
                if(authorization != null) {
                    String[] authorizationComponents = authorization.trim().split(" ");

                    if(authorizationComponents.length == 2) {
                        String authorizationType = authorizationComponents[0];

                        String authorizationToken = authorizationComponents[1];

                        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authorizationToken).getBody();

                        User user = userService.getUserById(Long.valueOf(claims.getSubject()));

                        httpServletRequest.setAttribute("authorizationType", authorizationType);
                        httpServletRequest.setAttribute("authorizationToken", authorizationToken);
                        httpServletRequest.setAttribute("user", user);
                    } else {
                        throw new Exception("Invalid authorization token.");
                    }
                } else {
                    throw new Exception("Missing authorization token.");
                }
            } catch(Exception ex) {
                HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

                ResponseDTO responseDTO = new ResponseDTO();

                responseDTO.setCode(HttpStatus.UNAUTHORIZED.value());
                responseDTO.setMessage(ex.getMessage());

                ObjectMapper objectMapper = new ObjectMapper();

                httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                httpServletResponse.getWriter().print(objectMapper.writeValueAsString(responseDTO));
                httpServletResponse.getWriter().close();

                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
