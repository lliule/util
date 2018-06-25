package com.lly.test.designModel.strategy.airplan.taskoff;

public class LongDistanceTakeOff implements TaskOff{
    @Override
    public void taskOff() {
        System.out.println("长距离起飞");
    }
}
