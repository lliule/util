package com.lly.test.designModel.singleton;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 验证反射破坏静态内部类的单例模式
 */
public class DestorySingletonTest {

    public static void main(String[] args) throws Exception {
//        singleClassDestoryByReflect();
//            singleClassCannotDestoryByReflect();
//            singleClassDestoryBySerializable();
        signleClassCannotDestoryBySerializable();
    }

    /**
     * 测试静态内部类单例模式被反射破坏
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private static void singleClassDestoryByReflect() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        //先获取一个单例
        InnerClassSingleton instance = InnerClassSingleton.getInstance();
        InnerClassSingleton instance2 = InnerClassSingleton.getInstance();
        //反射再获取一个
        Class<InnerClassSingleton> singletonClass = InnerClassSingleton.class;
        Constructor<InnerClassSingleton> constructor = singletonClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        InnerClassSingleton newInstance = constructor.newInstance();
        /**
         * first instance: 640070680
         * second instance: 640070680
         * third instance: 1510467688
         * 可见，反射获取到的对象和单例模式的对象不是一个。
         */
        System.out.println("first instance: "+instance.hashCode());
        System.out.println("second instance: "+instance2.hashCode());
        System.out.println("third instance: "+newInstance.hashCode());
    }

    /**
     * 测试优化后的单例模式不能被反射破坏
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    private static void singleClassCannotDestoryByReflect() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        InnerClassSingleton2 instance = InnerClassSingleton2.getInstance();
        InnerClassSingleton2 instance2 = InnerClassSingleton2.getInstance();
        Class<InnerClassSingleton2> aClass = InnerClassSingleton2.class;
        Constructor<InnerClassSingleton2> constructor = aClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        //无法获取到实例对象。直接抛异常
        InnerClassSingleton2 instance3 = constructor.newInstance();
        System.out.println("first instance: "+instance.hashCode());
        System.out.println("second instance: "+instance2.hashCode());
        System.out.println("third instance: "+instance3.hashCode());
    }

    /**
     * 测试不能被反射破坏的单例能被序列化破坏
     */
    private static void singleClassDestoryBySerializable(){
        InnerClassSingleton3 instance = InnerClassSingleton3.getInstance();
        ObjectOutput out = null;
        try {
            //序列化实例
            out = new ObjectOutputStream(new FileOutputStream("fileName.ser"));
            out.writeObject(instance);
            out.close();
            // 反序列化
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("fileName.ser"));
            InnerClassSingleton3 instance2 = (InnerClassSingleton3) in.readObject();
            /**
             * first instance : 1523554304
             * second instance : 2094548358
             * 发现并不是同一个对象，单例被破坏
             */
            System.out.println("first instance : "+ instance.hashCode());
            System.out.println("second instance : "+ instance2.hashCode());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * 测试序列化不能破坏单例
     */
    private static void signleClassCannotDestoryBySerializable(){
        InnerClassSingletonFinal instance = InnerClassSingletonFinal.getInstance();
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("file.ser"));
            out.writeObject(instance);
            out.close();
            //反序列化
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("file.ser"));
            InnerClassSingletonFinal instance2 = (InnerClassSingletonFinal)in.readObject();
            /**
             * first instance : 1523554304
             * second instance : 1523554304
             * 单例不能被破坏
             */
            System.out.println("first instance : " +instance.hashCode());
            System.out.println("second instance : " +instance2.hashCode());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
