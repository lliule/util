package com.lly.pdf;

// 题号
public class QuestionTitle {
    private Position position;
    private String value;

    public QuestionTitle(String value) {
        this.value = value;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "QuestionTitle{" +
                "position=" + position +
                ", value='" + value + '\'' +
                '}';
    }
}
