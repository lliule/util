package com.lly.test.designModel.innerclass;

/**
 * 说明静态内部类加载
 * 当且仅当其某个静态成员（静态域、构造器、静态方法等）被调用时发生。。
 */
public class Outer {
    static{
        System.out.println("outer static code ");
    }

    static class Inner{
        static {
            System.out.println("inner static code ");
        }

        static void innerMethod(){
            System.out.println("inner menthod ");
        }
    }

    public static void main(String[] args) {
        Outer outer = new Outer();
        System.out.println("starting line=====");
        Outer.Inner.innerMethod();
    }

}
