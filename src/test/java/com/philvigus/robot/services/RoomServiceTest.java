package com.philvigus.robot.services;

import com.philvigus.robot.repositories.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
    void findAllCallsFindAllOnTheRoomRepository() {
        roomService.findAll();

        verify(roomRepository, times(1)).findAll();
    }
}