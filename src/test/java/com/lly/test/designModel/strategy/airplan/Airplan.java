package com.lly.test.designModel.strategy.airplan;

import com.lly.test.designModel.strategy.airplan.fly.Fly;
import com.lly.test.designModel.strategy.airplan.taskoff.TaskOff;
import com.lly.test.designModel.strategy.airplan.taskoff.TaskOffStyle;

/**
 * context类
 * 飞机接口
 */
public interface Airplan {

    /**
     * 定义起飞接口
     */
    void taskOff();

    /**
     * 定义飞行接口
     */
    void fly();

}
