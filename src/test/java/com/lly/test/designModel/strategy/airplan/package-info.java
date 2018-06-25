package com.lly.test.designModel.strategy.airplan;

/**
 * 使用策略模式实现飞机模拟系统
 *
 *
 * 飞机种类                   起飞特征                     飞行特征
 * 直升机(Helicopter)   垂直起飞(VerticalTakeOff)       亚音速飞行(SubSonicFly)
 * 客机(AirPlane)      长距离起飞(LongDistanceTakeOff)  亚音速飞行(SubSonicFly)
 * 歼击机(Fighter)     长距离起飞(LongDistanceTakeOff)  超音速飞行(SuperSonicFly)
 * 鹞式战斗机(Harrier)  垂直起飞(VerticalTakeOff)       超音速飞行(SuperSonicFly)
 */