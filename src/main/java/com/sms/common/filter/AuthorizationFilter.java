package com.sms.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sms.common.service.SecurityService;

@Component
public class AuthorizationFilter implements Filter {
	@Autowired
	private SecurityService securityService;
	
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    	try {
    		if(securityService.authorizeRequest()) {
    			filterChain.doFilter(servletRequest, servletResponse);
    		}
    	} catch(Exception ex) {
    		throw new ServletException(ex.getMessage());
    	}
    }

    @Override
    public void destroy() {
    }
}
