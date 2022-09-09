package com.philvigus.robot.services;

import com.philvigus.robot.domain.Room;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoomServiceIT {
    @Autowired
    RoomService roomService;

    @Autowired
    DatabaseResetService databaseResetService;

    @AfterEach
    void tearDown() {
        databaseResetService.resetDatabase();
    }

    @Test
    void savePersistsARoomToTheDatabase() {
        Room room = new Room(1, 2);

        Room savedRoom = roomService.save(room);

        List<Room> rooms = roomService.findAll();

        assertFalse(rooms.isEmpty());
        assertNotNull(savedRoom.getId());
    }

    @Test
    void findAllReturnsAnEmptyListWhenThereAreNoRooms() {
        List<Room> rooms = roomService.findAll();

        assertTrue(rooms.isEmpty());
    }

    @Test
    void findAllReturnsAListOfExistingRooms() {
        Room firstSavedRoom = roomService.save(new Room(1, 2));
        Room secondSavedRoom = roomService.save(new Room(3, 4));

        List<Room> rooms = roomService.findAll();


        assertEquals(2, rooms.size());
        assertEquals(firstSavedRoom, rooms.get(0));
        assertEquals(secondSavedRoom, rooms.get(1));
    }
}
