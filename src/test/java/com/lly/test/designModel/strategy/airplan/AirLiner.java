package com.lly.test.designModel.strategy.airplan;

import com.lly.test.designModel.strategy.airplan.fly.Fly;
import com.lly.test.designModel.strategy.airplan.taskoff.TaskOffStyle;

/**
 * 客机
 */
public class AirLiner implements Airplan{
    private Fly fly;
    private TaskOffStyle taskOffStyle;

    public AirLiner(Fly fly, TaskOffStyle taskOffStyle) {
        this.fly = fly;
        this.taskOffStyle = taskOffStyle;
    }

    @Override
    public void taskOff() {
        taskOffStyle.taskOff();
    }

    @Override
    public void fly() {
        fly.fly();
    }
}
