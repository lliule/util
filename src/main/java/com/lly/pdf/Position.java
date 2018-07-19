package com.lly.pdf;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Position {
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private int x;
    private int y;

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
