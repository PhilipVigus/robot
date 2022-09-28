package com.philvigus.robot.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RobotTest {
    Robot robot;

    @Test
    void getOrientationReturnsTheRobotsOrientation() {
        final Orientation orientation = Orientation.N;

        robot = new Robot(orientation, 1, 2);

        assertEquals(orientation, robot.getOrientation());
    }

    @Test
    void turnLeftTurnsTheRobotLeft() {
        robot = new Robot(Orientation.N, 1, 2);
        robot.turnLeft();

        assertEquals(Orientation.W, robot.getOrientation());
    }

    @Test
    void turnRightTurnsTheRobotRight() {
        robot = new Robot(Orientation.N, 1, 2);
        robot.turnRight();

        assertEquals(Orientation.E, robot.getOrientation());
    }

    @Test
    void moveForwardWhenFacingNorthMovesTheRobotOneStepNorth() {
        final int initialY = 2;
        robot = new Robot(Orientation.N, 1, initialY);
        robot.moveForward();

        assertEquals(initialY + 1, robot.getY());
    }

    @Test
    void moveForwardWhenFacingEastMovesTheRobotOneStepEast() {
        final int initialX = 2;
        robot = new Robot(Orientation.E, initialX, 1);
        robot.moveForward();

        assertEquals(initialX + 1, robot.getX());
    }

    @Test
    void moveForwardWhenFacingSouthMovesTheRobotOneStepSouth() {
        final int initialY = 2;
        robot = new Robot(Orientation.S, 1, initialY);
        robot.moveForward();

        assertEquals(initialY - 1, robot.getY());
    }

    @Test
    void moveForwardWhenFacingWestMovesTheRobotOneStepWest() {
        final int initialX = 2;
        robot = new Robot(Orientation.W, initialX, 1);
        robot.moveForward();

        assertEquals(initialX - 1, robot.getX());
    }
}