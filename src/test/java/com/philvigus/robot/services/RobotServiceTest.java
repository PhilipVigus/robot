package com.philvigus.robot.services;

import com.philvigus.robot.repositories.RobotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class RobotServiceTest {
    RobotService robotService;

    @Mock
    RobotRepository robotRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        robotService = new RobotService(robotRepository);
    }

    @Test
    void findAllRobotsByRoomIdCallsFindAllByRoomIdOnTheRepository() {
        final Long id = 1L;

        robotService.findAllRobotsByRoomId(id);

        verify(robotRepository, times(1)).findAllByRoomId(id);
    }
}