package com.sms.accounting.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Account {
    public enum Type {
        SOCIETY,
        ROOM,
        STAFF,
        VENDOR,
        CUSTOMER;
    	
    	public static boolean contains(String name) {
    		for(Type type : values()) {
    			if(type.name().equals(name)) {
    				return true;
    			}
    		}
    		
    		return false;
    	}
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Type type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
