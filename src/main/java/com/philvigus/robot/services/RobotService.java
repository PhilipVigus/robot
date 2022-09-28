package com.philvigus.robot.services;

import com.philvigus.robot.domain.Robot;
import com.philvigus.robot.repositories.RobotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RobotService {
    private final RobotRepository robotRepository;

    public RobotService(final RobotRepository robotRepository) {
        this.robotRepository = robotRepository;
    }

    public List<Robot> findAllRobotsByRoomId(final Long roomId) {
        return robotRepository.findAllByRoomId(roomId);
    }

    public Robot save(final Robot robot) {
        return robotRepository.save(robot);
    }
}
