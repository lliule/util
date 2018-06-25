package com.lly.test.designModel.strategy;

/**
 * 通过工厂模式来创建折扣策略实现
 */
public class MovivTicketFactory {

    public static AbstractDiscount createBean(String type){
        AbstractDiscount discount = null;
        switch (type){
            case "student": discount =new StudentDiscount();break;
            case "vip":discount = new VipDiscount();break;
        }
        return discount;
    }

}
