package com.lly.test.designModel.strategy.airplan.taskoff;

/**
 * 垂直起飞
 */
public class VerticalTaskOff implements TaskOff {
    @Override
    public void taskOff() {
        System.out.println("垂直起飞");
    }
}
