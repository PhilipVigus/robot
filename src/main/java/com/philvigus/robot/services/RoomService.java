package com.philvigus.robot.services;

import com.philvigus.robot.domain.Room;
import com.philvigus.robot.dtos.RoomDto;
import com.philvigus.robot.mappers.RoomMapper;
import com.philvigus.robot.repositories.RoomRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Optional<Room> findById(Long id) {
        return roomRepository.findById(id);
    }

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public Room save(Room room) {
        return roomRepository.save(room);
    }

    public Room update(final RoomDto roomDto) {
        final RoomMapper roomMapper = Mappers.getMapper(RoomMapper.class);

        final Room room = roomRepository.findById(roomDto.getId()).orElseThrow();

        roomMapper.updateRoomFromDto(roomDto, room);
        roomRepository.save(room);

        return room;
    }
}
