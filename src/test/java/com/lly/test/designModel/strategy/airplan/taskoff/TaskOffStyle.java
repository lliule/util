package com.lly.test.designModel.strategy.airplan.taskoff;

import lombok.Getter;
import lombok.Setter;

/**
 * 起飞特征 Context类
 */
@Setter
public class TaskOffStyle {
    private TaskOff taskOff;

    public void taskOff(){
        taskOff.taskOff();
    }
}
