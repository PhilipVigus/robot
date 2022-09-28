package com.philvigus.robot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.philvigus.robot.domain.Orientation;
import com.philvigus.robot.domain.Robot;
import com.philvigus.robot.domain.Room;
import com.philvigus.robot.services.DatabaseResetService;
import com.philvigus.robot.services.RobotService;
import com.philvigus.robot.services.RoomService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@EnableWebMvc
class RobotControllerIT {
    MockMvc mockMvc;

    @Autowired
    DatabaseResetService databaseResetService;

    @Autowired
    RoomService roomService;

    @Autowired
    RobotService robotService;

    @Autowired
    WebApplicationContext context;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach
    void tearDown() {
        databaseResetService.resetDatabase();
    }

    @Test
    void getRobotsShouldGetAllRobotsInASpecifiedRoom() throws Exception {
        final Room room = new Room(1, 2);
        final Room savedRoom = roomService.save(room);

        final Robot robotInRoom = new Robot(Orientation.N, 1, 2);
        final Robot otherRobot = new Robot(Orientation.S, 3, 4);

        roomService.addRobot(savedRoom, robotInRoom);

        robotService.save(robotInRoom);

        final String url = "/rooms/" + savedRoom.getId() + "/robots";

        mockMvc.perform(get(url).contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(robotInRoom.getId())))
                .andExpect(jsonPath("$[0].x", is(1)))
                .andExpect(jsonPath("$[0].y", is(2)))
                .andExpect(jsonPath("[0].roomId", is(savedRoom.getId())));
    }

    @Test
    void createRobotShouldCreateANewRobotInTheSpecifiedRoom() throws Exception {
        final int x = 2;
        final int y = 3;
        final Orientation orientation = Orientation.N;

        final Room room = new Room(1, 2);
        final Room savedRoom = roomService.save(room);

        final ObjectMapper mapper = new ObjectMapper();

        final Robot robot = new Robot(orientation, x, y);
        final String json = mapper.writeValueAsString(robot);

        final String url = "/rooms/" + savedRoom.getId() + "/robots";

        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.x", is(x)))
                .andExpect(jsonPath("$.y", is(y)))
                .andExpect(jsonPath("$.orientation", is(orientation.toString())));
    }
}
