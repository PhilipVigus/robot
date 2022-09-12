package com.philvigus.robot.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.philvigus.robot.domain.Room;
import com.philvigus.robot.services.DatabaseResetService;
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

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@EnableWebMvc
class RoomControllerIT {
    MockMvc mockMvc;
    @Autowired
    DatabaseResetService databaseResetService;
    @Autowired
    RoomService roomService;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach
    void tearDown() {
        databaseResetService.resetDatabase();
    }

    @Test
    void getRoomsShouldGetAllRooms() throws Exception {
        final int length = 5;
        final int width = 6;

        final Room room = new Room(length, width);

        roomService.save(room);

        mockMvc.perform(get("/rooms").contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].width", is(width)))
                .andExpect(jsonPath("$[0].length", is(length)));
    }

    @Test
    void createRoomShouldCreateANewRoom() throws Exception {
        final int length = 5;
        final int width = 6;

        final ObjectMapper mapper = new ObjectMapper();

        final Room room = new Room(length, width);
        final String json = mapper.writeValueAsString(room);

        mockMvc.perform(post("/rooms").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.width", is(width)))
                .andExpect(jsonPath("$.length", is(length)));

        final List<Room> rooms = roomService.findAll();

        assertEquals(1, rooms.size());

        final Room createdRoom = rooms.get(0);

        assertNotNull(createdRoom.getId());
        assertEquals(length, createdRoom.getLength());
        assertEquals(width, createdRoom.getWidth());
    }
}