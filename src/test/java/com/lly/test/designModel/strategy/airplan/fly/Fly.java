package com.lly.test.designModel.strategy.airplan.fly;

import lombok.Setter;

@Setter
public class Fly {
    private FlyType fly;

    public void fly() {
        fly.fly();
    }
}
