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
        final Room room = new Room(1, 2);

        final Room savedRoom = roomService.save(room);

        final List<Room> rooms = roomService.findAll();

        assertFalse(rooms.isEmpty());
        assertNotNull(savedRoom.getId());
    }

    @Test
    void findAllReturnsAnEmptyListWhenThereAreNoRooms() {
        final List<Room> rooms = roomService.findAll();

        assertTrue(rooms.isEmpty());
    }

    @Test
    void findAllReturnsAListOfExistingRooms() {
        final Room firstSavedRoom = roomService.save(new Room(1, 2));
        final Room secondSavedRoom = roomService.save(new Room(3, 4));

        final List<Room> rooms = roomService.findAll();


        assertEquals(2, rooms.size());
        assertEquals(firstSavedRoom, rooms.get(0));
        assertEquals(secondSavedRoom, rooms.get(1));
    }
}
