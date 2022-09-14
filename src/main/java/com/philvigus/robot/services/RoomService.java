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

    public RoomService(final RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Optional<Room> findById(final Long id) {
        return roomRepository.findById(id);
    }

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public Room save(final Room room) {
        return roomRepository.save(room);
    }

    public Room update(final Long id, final RoomDto roomDto) {
        final RoomMapper roomMapper = Mappers.getMapper(RoomMapper.class);

        final Room room = roomRepository.findById(id).orElseThrow();

        roomMapper.updateRoomFromDto(roomDto, room);
        roomRepository.save(room);

        return room;
    }

    public void delete(final Long id) {
        roomRepository.deleteById(id);
    }
}
