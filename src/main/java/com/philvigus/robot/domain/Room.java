package com.philvigus.robot.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int length;
    private int width;

    public Room(int length, int width) {
        this.length = length;
        this.width = width;
    }
}
