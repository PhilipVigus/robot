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
    RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<List<Room>> getRooms() {
        return new ResponseEntity<>(roomService.findAll(), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody RoomDto roomDto) {
        RoomMapper roomMapper = Mappers.getMapper(RoomMapper.class);

        Room room = roomMapper.dtoToRoom(roomDto);

        return new ResponseEntity<>(roomService.save(room), new HttpHeaders(), HttpStatus.CREATED);
    }
}
