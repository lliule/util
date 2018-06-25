package com.lly.test.designModel.strategy;

import lombok.Getter;
import lombok.Setter;

/**
 * 环境类： 电影票
 */
@Setter
public class MovieTicket {

    private double price;
    private AbstractDiscount discount;

    public double getPrice(){
        return discount.discount(price);
    }

}
