package com.sms.society.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sms.society.entity.ResidentRoom;

public interface ResidentRoomRepository extends JpaRepository<ResidentRoom, Long> {
	@Query("select rr from ResidentRoom rr inner join rr.room r inner join rr.resident res inner join res.user u where r.id = :roomId and u.mobile = :mobile")
	public List<ResidentRoom> findByRoomIdAndMobile(@Param("roomId") Long roomId, @Param("mobile") String mobile);
}
