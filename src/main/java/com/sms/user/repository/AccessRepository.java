package com.sms.user.repository;

import com.sms.society.entity.Room;
import com.sms.user.entity.Access;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccessRepository extends JpaRepository<Access, Long> {
    public List<Access> findByRoom(Room room);

    public Access findByIdAndPassword(Long id, String password);
}
