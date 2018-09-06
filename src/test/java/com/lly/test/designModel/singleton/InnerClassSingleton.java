package com.lly.test.designModel.singleton;

/**
 * 由于静态内部类只有他的静态成员被调用时加载，
 * 所以只有在调用 InnerClassSingleton.getInstance()
 * 时 InnerClassSingletonHolder会被加载，且只加载一次。
 * 所以是线程安全且是懒加载的。
 * 缺点：反射破坏
 */
public class InnerClassSingleton {
    private InnerClassSingleton (){}

    static class InnerClassSingletonHolder{
        private static InnerClassSingleton instance = new InnerClassSingleton();
    }

    public static InnerClassSingleton getInstance(){
        return InnerClassSingletonHolder.instance;
    }

}
