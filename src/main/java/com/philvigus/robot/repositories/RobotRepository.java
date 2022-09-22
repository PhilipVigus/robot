package com.philvigus.robot.repositories;

import com.philvigus.robot.domain.Robot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RobotRepository extends JpaRepository<Robot, Long> {
    List<Robot> findAllByRoomId(Long roomId);
}
