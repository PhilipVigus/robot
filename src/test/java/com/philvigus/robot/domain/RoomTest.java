package com.philvigus.robot.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoomTest {
    @Test
    void getWidthShouldReturnTheWidth() {
        final int width = 1;

        final Room room = new Room(2, width);

        assertEquals(width, room.getWidth());
    }

    @Test
    void getLengthShouldReturnTheLength() {
        final int length = 1;

        final Room room = new Room(length, 2);

        assertEquals(length, room.getLength());
    }
}