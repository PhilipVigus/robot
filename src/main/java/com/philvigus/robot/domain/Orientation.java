package com.philvigus.robot.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum Orientation {
    N(0),
    E(1),
    S(2),
    W(3);

    private static final Map<Integer, Orientation> BY_POSITION = new ConcurrentHashMap<>();

    static {
        for (final Orientation o : values()) {
            BY_POSITION.put(o.position, o);
        }
    }

    private final Integer position;

    Orientation(final Integer position) {
        this.position = position;
    }

    public Orientation turnRight() {
        if (position == 3) {
            return Orientation.N;
        }

        return BY_POSITION.get(position + 1);
    }

    public Orientation turnLeft() {
        if (position == 0) {
            return Orientation.W;
        }

        return BY_POSITION.get(position - 1);
    }
}
