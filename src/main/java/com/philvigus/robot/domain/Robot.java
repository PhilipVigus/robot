package com.philvigus.robot.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "robots")
public class Robot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Orientation orientation;
    private int x;
    private int y;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    @JsonIgnore
    private Room room;

    public Robot(Orientation orientation, int x, int y) {
        this.orientation = orientation;
        this.x = x;
        this.y = y;
    }

    public void addToRoom(final Room room) {
        this.room = room;
    }

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
