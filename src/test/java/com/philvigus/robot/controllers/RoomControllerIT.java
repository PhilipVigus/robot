package com.philvigus.robot.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.philvigus.robot.domain.Room;
import com.philvigus.robot.dtos.RoomDto;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Test
    void creatingARoomWithAWidthLessThanOneReturnsAnError() throws Exception {
        final int length = 5;
        final int width = 0;

        final ObjectMapper mapper = new ObjectMapper();

        final Room room = new Room(length, width);
        final String json = mapper.writeValueAsString(room);

        mockMvc.perform(post("/rooms").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.width", is("The width must be between 1 and 1024 inclusive")));

        final List<Room> rooms = roomService.findAll();

        assertEquals(0, rooms.size());
    }

    @Test
    void creatingARoomWithALengthLessThanOneReturnsAnError() throws Exception {
        final int length = 0;
        final int width = 5;

        final ObjectMapper mapper = new ObjectMapper();

        final Room room = new Room(length, width);
        final String json = mapper.writeValueAsString(room);

        mockMvc.perform(post("/rooms").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length", is("The length must be between 1 and 1024 inclusive")));

        final List<Room> rooms = roomService.findAll();

        assertEquals(0, rooms.size());
    }

    @Test
    void creatingARoomWithAWidthGreaterThan1024ReturnsAnError() throws Exception {
        final int length = 5;
        final int width = 1025;

        final ObjectMapper mapper = new ObjectMapper();

        final Room room = new Room(length, width);
        final String json = mapper.writeValueAsString(room);

        mockMvc.perform(post("/rooms").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.width", is("The width must be between 1 and 1024 inclusive")));

        final List<Room> rooms = roomService.findAll();

        assertEquals(0, rooms.size());
    }

    @Test
    void creatingARoomWithALengthGreaterThan1024ReturnsAnError() throws Exception {
        final int length = 1025;
        final int width = 5;

        final ObjectMapper mapper = new ObjectMapper();

        final Room room = new Room(length, width);
        final String json = mapper.writeValueAsString(room);

        mockMvc.perform(post("/rooms").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length", is("The length must be between 1 and 1024 inclusive")));

        final List<Room> rooms = roomService.findAll();

        assertEquals(0, rooms.size());
    }

    @Test
    void updateRoomShouldUpdateAnExistingRoom() throws Exception {
        final int updatedLength = 5;
        final int updatedWidth = 6;

        final Room savedRoom = roomService.save(new Room(1, 2));

        final ObjectMapper mapper = new ObjectMapper();

        final RoomDto roomDto = new RoomDto(updatedLength, updatedWidth);
        final String json = mapper.writeValueAsString(roomDto);

        mockMvc.perform(put("/rooms/" + savedRoom.getId()).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.width", is(updatedWidth)))
                .andExpect(jsonPath("$.length", is(updatedLength)));

        final Room room = roomService.findById(savedRoom.getId()).orElseThrow();

        assertEquals(updatedWidth, room.getWidth());
        assertEquals(updatedLength, room.getLength());
    }

    @Test
    void updatingARoomWithAWidthLessThanOneReturnsAnError() throws Exception {
        final int originalLength = 3;
        final int originalWidth = 4;

        final int updatedLength = 5;
        final int updatedWidth = 0;

        final Room savedRoom = roomService.save(new Room(originalLength, originalWidth));

        final ObjectMapper mapper = new ObjectMapper();

        final RoomDto roomDto = new RoomDto(updatedLength, updatedWidth);
        final String json = mapper.writeValueAsString(roomDto);

        mockMvc.perform(post("/rooms").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.width", is("The width must be between 1 and 1024 inclusive")));

        final Room room = roomService.findById(savedRoom.getId()).orElseThrow();

        assertEquals(originalWidth, room.getWidth());
        assertEquals(originalLength, room.getLength());
    }

    @Test
    void updatingARoomWithALengthLessThanOneReturnsAnError() throws Exception {
        final int originalLength = 3;
        final int originalWidth = 4;

        final int updatedLength = 0;
        final int updatedWidth = 5;

        final Room savedRoom = roomService.save(new Room(originalLength, originalWidth));

        final ObjectMapper mapper = new ObjectMapper();

        final RoomDto roomDto = new RoomDto(updatedLength, updatedWidth);
        final String json = mapper.writeValueAsString(roomDto);

        mockMvc.perform(put("/rooms/" + savedRoom.getId()).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length", is("The length must be between 1 and 1024 inclusive")));

        final Room room = roomService.findById(savedRoom.getId()).orElseThrow();

        assertEquals(originalWidth, room.getWidth());
        assertEquals(originalLength, room.getLength());
    }

    @Test
    void updatingARoomWithAWidthGreaterThan1024ReturnsAnError() throws Exception {
        final int originalLength = 3;
        final int originalWidth = 4;

        final int updatedLength = 5;
        final int updatedWidth = 1025;

        final Room savedRoom = roomService.save(new Room(originalLength, originalWidth));

        final ObjectMapper mapper = new ObjectMapper();

        final RoomDto roomDto = new RoomDto(updatedLength, updatedWidth);
        final String json = mapper.writeValueAsString(roomDto);

        mockMvc.perform(post("/rooms").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.width", is("The width must be between 1 and 1024 inclusive")));

        final Room room = roomService.findById(savedRoom.getId()).orElseThrow();

        assertEquals(originalWidth, room.getWidth());
        assertEquals(originalLength, room.getLength());
    }

    @Test
    void updatingARoomWithALengthGreaterThan1024ReturnsAnError() throws Exception {
        final int originalLength = 3;
        final int originalWidth = 4;

        final int updatedLength = 1025;
        final int updatedWidth = 5;

        final Room savedRoom = roomService.save(new Room(originalLength, originalWidth));

        final ObjectMapper mapper = new ObjectMapper();

        final RoomDto roomDto = new RoomDto(updatedLength, updatedWidth);
        final String json = mapper.writeValueAsString(roomDto);

        mockMvc.perform(post("/rooms").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length", is("The length must be between 1 and 1024 inclusive")));

        final Room room = roomService.findById(savedRoom.getId()).orElseThrow();

        assertEquals(originalWidth, room.getWidth());
        assertEquals(originalLength, room.getLength());
    }

    @Test
    void deleteRoomShouldDeleteTheRoomWithTheSpecifiedId() throws Exception {
        final Room savedRoom = roomService.save(new Room(1, 2));

        mockMvc.perform(delete("/rooms/" + savedRoom.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        assertTrue(roomService.findById(savedRoom.getId()).isEmpty());
    }
}