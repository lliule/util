package com.lly.test.designModel.singleton;

import java.io.Serializable;

/**
 * 内部类单例终极版
 * 不能被反射破坏，不能被序列化破坏
 */
public class InnerClassSingletonFinal implements Serializable {
    private static boolean init = false;
    private InnerClassSingletonFinal(){
        synchronized (InnerClassSingletonFinal.class){
            if(init == false){
                init = !init;
            }else{
                throw new RuntimeException("单例被破坏");
            }
        }
    }

    static class SingleHolder{
        private static InnerClassSingletonFinal instance = new InnerClassSingletonFinal();
    }

    public static InnerClassSingletonFinal getInstance(){
        return SingleHolder.instance;
    }

    /**
     * 通过提供 readResolve()方法代替了从流中读取
     * 对象。确保了在序列化和反序列化过程中没人可以创建
     * 新的实例
     * @return
     */
    private Object readResolve(){
        return getInstance();
    }

}
