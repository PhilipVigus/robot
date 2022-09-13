package com.philvigus.robot.services;

import com.philvigus.robot.domain.Room;
import com.philvigus.robot.dtos.RoomDto;
import com.philvigus.robot.mappers.RoomMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

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
    void findByIdReturnsTheRoomWithTheGivenId() {
        final Room room = new Room(1, 2);

        final Room savedRoom = roomService.save(room);

        final Room foundRoom = roomService.findById(savedRoom.getId()).orElseThrow();

        assertEquals(savedRoom, foundRoom);
    }

    @Test
    void findByIdReturnsAnEmptyOptionalWhenTheIdIsNotFound() {
        final Optional<Room> room = roomService.findById(1L);

        assertTrue(room.isEmpty());
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

    @Test
    void updateUpdatesARoomInTheDatabase() {
        final int updatedWidth = 4;
        final int updatedLength = 5;

        final RoomMapper roomMapper = Mappers.getMapper(RoomMapper.class);

        final Room room = new Room(1, 2);

        final Room savedRoom = roomService.save(room);

        final RoomDto updatedDto = roomMapper.roomToDto(savedRoom);
        updatedDto.setWidth(updatedWidth);
        updatedDto.setLength(updatedLength);

        roomService.update(updatedDto);

        final Room updatedRoom = roomService.findById(updatedDto.getId()).orElseThrow();

        assertEquals(savedRoom, updatedRoom);
        assertEquals(updatedWidth, updatedRoom.getWidth());
        assertEquals(updatedLength, updatedRoom.getLength());
    }

    @Test
    void deleteDeletesTheRoomWithTheSpecifiedId() {
        final Room roomToKeep = roomService.save(new Room(3, 4));
        final Room roomToDelete = roomService.save(new Room(1, 2));

        roomService.delete(roomToDelete.getId());

        assertTrue(roomService.findById(roomToDelete.getId()).isEmpty());
        assertTrue(roomService.findById(roomToKeep.getId()).isPresent());
    }
}
