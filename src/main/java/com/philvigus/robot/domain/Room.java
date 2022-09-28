package com.philvigus.robot.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "room", fetch = FetchType.LAZY)
    private Set<Robot> robots;

    private int length;
    private int width;

    public Room(final int length, final int width) {
        this.length = length;
        this.width = width;
        this.robots = new HashSet<>();
    }

    public void addRobot(final Robot robot) {
        robots.add(robot);
        robot.setRoom(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Room room = (Room) o;
        return Objects.equals(id, room.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, length, width);
    }
}
