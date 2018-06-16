package com.sms.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sms.common.converter.ZonedDateTimeConverter;
import com.sms.society.entity.Room;

import javax.persistence.*;
import java.time.ZonedDateTime;

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

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column
    @Convert(converter = ZonedDateTimeConverter.class)
    private ZonedDateTime residingFrom;

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public ZonedDateTime getResidingFrom() {
        return residingFrom;
    }

    public void setResidingFrom(ZonedDateTime residingFrom) {
        this.residingFrom = residingFrom;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
