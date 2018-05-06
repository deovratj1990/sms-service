package com.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sms.entity.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("select r from Room r inner join r.wing w inner join w.society s where s.id = :societyId")
    public List<Room> findBySocietyId(@Param("societyId") Long societyId);
}
