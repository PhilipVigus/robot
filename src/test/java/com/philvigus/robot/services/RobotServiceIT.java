package com.philvigus.robot.services;

import com.philvigus.robot.domain.Orientation;
import com.philvigus.robot.domain.Robot;
import com.philvigus.robot.domain.Room;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class RobotServiceIT {
    @Autowired
    RobotService robotService;

    @Autowired
    RoomService roomService;

    @Autowired
    DatabaseResetService databaseResetService;

    @AfterEach
    void tearDown() {
        databaseResetService.resetDatabase();
    }

    @Test
    void findAllRobotsByRoomIdFindsAllRobotsForTheSpecifiedRoomId() {
        final Room room = new Room(3, 4);
        final Room otherRoom = new Room(5, 6);

        final Robot firstRobotInRoom = new Robot(Orientation.N, 1, 2);
        final Robot secondRobotInRoom = new Robot(Orientation.E, 2, 3);

        final Robot otherRobot = new Robot(Orientation.S, 3, 4);

        room.addRobot(firstRobotInRoom);
        room.addRobot(secondRobotInRoom);
        otherRoom.addRobot(otherRobot);

        final Room savedRoom = roomService.save(room);
        roomService.save(otherRoom);

        List<Robot> robots = robotService.findAllRobotsByRoomId(savedRoom.getId());

        assertEquals(2, robots.size());
        assertEquals(savedRoom.getId(), robots.get(0).getRoom().getId());
        assertEquals(savedRoom.getId(), robots.get(1).getRoom().getId());
    }

    @Test
    void savePersistsARobotToTheDatabase() {
        final Room room = new Room(3, 4);
        final Room savedRoom = roomService.save(room);

        final Robot robot = new Robot(Orientation.N, 1, 2);
        savedRoom.addRobot(robot);

        final Robot savedRobot = robotService.save(robot);

        assertNotNull(savedRobot);
    }
}
