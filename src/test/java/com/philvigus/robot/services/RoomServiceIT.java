package com.philvigus.robot.services;

import com.philvigus.robot.domain.Room;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class RoomServiceIT {
    @Autowired
    RoomService roomService;

    @Test
    void findAllReturnsAnEmptyListWhenThereAreNoRooms() {
        List<Room> rooms = roomService.findAll();

        assertTrue(rooms.isEmpty());
    }
}
