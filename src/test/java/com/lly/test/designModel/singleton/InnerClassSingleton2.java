package com.lly.test.designModel.singleton;

import java.io.Serializable;

/**
 * 优化一下，让反射无法破坏
 * 还有缺陷：当被序列化存储后取出来，是否还是同一个实例
 *
 * @code{
 *  public class InnerClassSingleton2 implements Serializable{
 *      ...
 * }
 */
public class InnerClassSingleton2 implements Serializable {
    private static boolean init = false;
    private InnerClassSingleton2() {
        if(init == false){
            init = !init;
        }else{
            throw new RuntimeException("单例被破坏！");
        }
    }
    static class SingleHolder{
        private static InnerClassSingleton2 instance = new InnerClassSingleton2();
    }

    public static InnerClassSingleton2 getInstance(){
        return SingleHolder.instance;
    }

}
