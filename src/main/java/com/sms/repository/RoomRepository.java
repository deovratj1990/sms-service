package com.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sms.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
