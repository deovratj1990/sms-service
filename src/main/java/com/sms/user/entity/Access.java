package com.sms.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sms.society.entity.Room;

import javax.persistence.*;

@Entity
public class Access {
    public enum Role {
        CHAIRMAN,
        SECRETARY,
        TREASURER,
        MEMBER,
        TENANT,
        STAFF
    }

    public enum Status {
        PENDING_VERIFICATION,
        ACTIVE,
        INACTIVE,
        DELETED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Room room;

    @JsonIgnore
    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
