package com.philvigus.robot.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrientationTest {
    @Test
    void turnRightFromNorthShouldReturnEast() {
        final Orientation orientation = Orientation.N;

        assertEquals(Orientation.E, orientation.turnRight());
    }

    @Test
    void turnRightFromEastShouldReturnSouth() {
        final Orientation orientation = Orientation.E;

        assertEquals(Orientation.S, orientation.turnRight());
    }

    @Test
    void turnRightFromSouthShouldReturnWest() {
        final Orientation orientation = Orientation.S;

        assertEquals(Orientation.W, orientation.turnRight());
    }

    @Test
    void turnRightFromWestShouldReturnNorth() {
        final Orientation orientation = Orientation.W;

        assertEquals(Orientation.N, orientation.turnRight());
    }

    @Test
    void turnLeftFromWestShouldReturnSouth() {
        final Orientation orientation = Orientation.W;

        assertEquals(Orientation.S, orientation.turnLeft());
    }

    @Test
    void turnLeftFromSouthShouldReturnEast() {
        final Orientation orientation = Orientation.S;

        assertEquals(Orientation.E, orientation.turnLeft());
    }

    @Test
    void turnLeftFromEastShouldReturnNorth() {
        final Orientation orientation = Orientation.E;

        assertEquals(Orientation.N, orientation.turnLeft());
    }

    @Test
    void turnLeftFromNorthShouldReturnWest() {
        final Orientation orientation = Orientation.N;

        assertEquals(Orientation.W, orientation.turnLeft());
    }
}