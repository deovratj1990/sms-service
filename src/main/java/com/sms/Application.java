package com.sms;

import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan
public class Application implements ApplicationContextAware {
	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		Application.applicationContext = applicationContext;
	}
	
	public static ApplicationContext getApplicationContext() {
		return Application.applicationContext;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
