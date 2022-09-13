package com.philvigus.robot.controllers;

import com.philvigus.robot.domain.Room;
import com.philvigus.robot.dtos.RoomDto;
import com.philvigus.robot.mappers.RoomMapper;
import com.philvigus.robot.services.RoomService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    private final RoomService roomService;

    @Autowired
    public RoomController(final RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<List<Room>> getRooms() {
        return new ResponseEntity<>(roomService.findAll(), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Room> createRoom(final @RequestBody RoomDto roomDto) {
        final RoomMapper roomMapper = Mappers.getMapper(RoomMapper.class);

        final Room room = roomMapper.dtoToRoom(roomDto);

        return new ResponseEntity<>(roomService.save(room), new HttpHeaders(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(final @PathVariable Long id, @RequestBody RoomDto roomDto) {
        roomDto.setId(id);
        return new ResponseEntity<>(roomService.update(roomDto), new HttpHeaders(), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(final @PathVariable Long id) {
        roomService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
