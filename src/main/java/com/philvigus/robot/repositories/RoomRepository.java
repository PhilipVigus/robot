package com.philvigus.robot.repositories;

import com.philvigus.robot.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
