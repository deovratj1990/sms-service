package com.sms.society.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sms.common.model.StringKeyMap;
import com.sms.society.entity.Society;

public interface SocietyRepository extends JpaRepository<Society, Long> {
	@Query("select new Map(r.name as roomName, rr.id as residentRoomId, res.mobile as residentMobile) from ResidentRoom rr inner join rr.resident res inner join rr.room r inner join res.society s where s.id = :societyId and rr.status = com.sms.society.entity.ResidentRoom$Status.UNAPPROVED")
	public List<StringKeyMap> findRegistrationsBySocietyId(@Param("societyId") Long societyId);
}
