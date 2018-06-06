package com.lly.test.designModel.singleton;

import java.io.Serializable;

public class InnerClassSingleton3 implements Serializable {
    private static boolean init = false;
    private InnerClassSingleton3() {
        if(init == false){
            init = !init;
        }else{
            throw new RuntimeException("单例被破坏！");
        }
    }
    static class SingleHolder{
        private static InnerClassSingleton3 instance = new InnerClassSingleton3();
    }

    public static InnerClassSingleton3 getInstance(){
        return SingleHolder.instance;
    }

}
