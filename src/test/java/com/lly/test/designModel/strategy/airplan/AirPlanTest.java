package com.lly.test.designModel.strategy.airplan;

import com.lly.test.designModel.strategy.airplan.fly.Fly;
import com.lly.test.designModel.strategy.airplan.fly.SubSonicFly;
import com.lly.test.designModel.strategy.airplan.taskoff.LongDistanceTakeOff;
import com.lly.test.designModel.strategy.airplan.taskoff.TaskOffStyle;

/**
 * 飞机起飞，飞行
 */
public class AirPlanTest {

    public static void main(String[] args) {
//        XMLParseUtil.getBean("");
        /**
         * 可以通过配置配置飞机类
         */
        Fly fly = new Fly();
        fly.setFly(new SubSonicFly());
        TaskOffStyle style = new TaskOffStyle();
        LongDistanceTakeOff longDistanceTakeOff = new LongDistanceTakeOff();
        style.setTaskOff(longDistanceTakeOff);
        Helicopter helicopter = new Helicopter(fly, style);
        helicopter.taskOff();
        helicopter.fly();

    }

}
