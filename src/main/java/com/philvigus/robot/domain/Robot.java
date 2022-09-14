package com.philvigus.robot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Robot {
    private Orientation orientation;
    private int x;
    private int y;

    public void turnLeft() {
        orientation = orientation.turnLeft();
    }

    public void turnRight() {
        orientation = orientation.turnRight();
    }

    public void moveForward() {
        switch (orientation) {
            case N -> moveNorth();
            case E -> moveEast();
            case S -> moveSouth();
            case W -> moveWest();
        }
    }

    private void moveNorth() {
        y++;
    }

    private void moveEast() {
        x++;
    }

    private void moveSouth() {
        y--;
    }

    private void moveWest() {
        x--;
    }
}
