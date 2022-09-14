package com.philvigus.robot.services;

import com.philvigus.robot.domain.Room;
import com.philvigus.robot.dtos.RoomDto;
import com.philvigus.robot.mappers.RoomMapper;
import com.philvigus.robot.repositories.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RoomServiceTest {
    RoomService roomService;

    @Mock
    RoomRepository roomRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        roomService = new RoomService(roomRepository);
    }

    @Test
    void findByIdCallsFindByIdOnTheRoomRepository() {
        final Long id = 1L;

        roomService.findById(id);

        verify(roomRepository, times(1)).findById(id);
    }

    @Test
    void findAllCallsFindAllOnTheRoomRepository() {
        roomService.findAll();

        verify(roomRepository, times(1)).findAll();
    }

    @Test
    void saveCallsSaveOnTheRoomRepository() {
        final Room room = new Room(1, 2);

        roomService.save(room);

        verify(roomRepository, times(1)).save(room);
    }

    @Test
    void updateCallsFindByIdOnTheRoomRepository() {
        final Long roomId = 1L;

        final RoomMapper roomMapper = Mappers.getMapper(RoomMapper.class);

        final RoomDto roomDto = new RoomDto(2, 3);
        final Room room = roomMapper.dtoToRoom(roomDto);

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));

        roomService.update(roomId, roomDto);

        verify(roomRepository, times(1)).findById(roomId);
    }

    @Test
    void updateCallsSaveOnTheRoomRepositoryWhenARoomWithTheDtoIdExists() {
        final Long roomId = 1L;

        final RoomMapper roomMapper = Mappers.getMapper(RoomMapper.class);

        final RoomDto roomDto = new RoomDto(2, 3);
        final Room room = roomMapper.dtoToRoom(roomDto);

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));

        roomService.update(roomId, roomDto);

        verify(roomRepository, times(1)).save(room);
    }

    @Test
    void updateThrowsAnExceptionWhenARoomWithTheDtoIdDoesNotExist() {
        final Long roomId = 1L;

        final RoomDto roomDto = new RoomDto(2, 3);

        when(roomRepository.findById(roomId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> roomService.update(roomId, roomDto));

    }

    @Test
    void deleteCallsDeleteOnTheRepository() {
        final long id = 1L;

        roomService.delete(id);

        verify(roomRepository, times(1)).deleteById(id);
    }
}