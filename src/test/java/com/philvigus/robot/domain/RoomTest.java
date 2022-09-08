package com.philvigus.robot.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoomTest {
    @Test
    void getWidthShouldReturnTheWidth() {
        int width = 1;

        Room room = new Room(width, 2);

        assertEquals(width, room.width());
    }

    @Test
    void getLengthShouldReturnTheLength() {
        int length = 1;

        Room room = new Room(2, length);

        assertEquals(length, room.length());
    }
}