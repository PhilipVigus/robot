package com.philvigus.robot.controllers;

import com.philvigus.robot.domain.Robot;
import com.philvigus.robot.domain.Room;
import com.philvigus.robot.dtos.RobotDto;
import com.philvigus.robot.mappers.RobotMapper;
import com.philvigus.robot.services.RobotService;
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
public class RobotController {
    private final RobotService robotService;

    private final RoomService roomService;

    @Autowired
    public RobotController(RobotService robotService, RoomService roomService) {
        this.robotService = robotService;
        this.roomService = roomService;
    }

    @GetMapping("/{roomId}/robots")
    public ResponseEntity<List<Robot>> getRobots(final @PathVariable Long roomId) {
        List<Robot> robots = robotService.findAllRobotsByRoomId(roomId);
        
        return new ResponseEntity<>(robotService.findAllRobotsByRoomId(roomId), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/{roomId}/robots")
    public ResponseEntity<Robot> createRobot(final @PathVariable Long roomId, final @RequestBody RobotDto robotDto) {
        final Room room = roomService.findById(roomId).orElseThrow();

        final RobotMapper robotMapper = Mappers.getMapper(RobotMapper.class);

        final Robot robot = robotMapper.dtoToRobot(robotDto);

        roomService.addRobot(room, robot);
        roomService.save(room);

        return new ResponseEntity<>(robotService.save(robot), new HttpHeaders(), HttpStatus.CREATED);
    }
}
