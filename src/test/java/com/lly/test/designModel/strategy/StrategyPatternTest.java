package com.lly.test.designModel.strategy;

import com.lly.test.util.XMLParseUtil;

public class StrategyPatternTest {

    public static void main(String[] args) {
//        testByConfig();
        testByFactory();
    }

    /**
     * 我们可以只通过修改配置信息而无需修改代码
     */
    private static void testByConfig() {
        MovieTicket ticket = new MovieTicket();
        Object o = XMLParseUtil.getBean("D:\\devlopment\\workspace\\util\\src\\test\\java\\config.xml");
        AbstractDiscount bean =
                (AbstractDiscount) o;
        ticket.setDiscount(bean);
        ticket.setPrice(90);

        double price = ticket.getPrice();
        System.out.println("折后价为: " + price);
    }

    /**
     * 工厂模式实现
     */
    private static void testByFactory(){
        MovieTicket ticket = new MovieTicket();
        AbstractDiscount vip = MovivTicketFactory.createBean("vip");
        ticket.setDiscount(vip);
        ticket.setPrice(85);
        System.out.println(ticket.getPrice());
    }


}
