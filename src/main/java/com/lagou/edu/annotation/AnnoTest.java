package com.lagou.edu.annotation;

import java.lang.reflect.Field;

public class AnnoTest {


    @MyAutowired(num=45)
    private static int a;//使用注解
    private static int b = 9; //未使用注解的变量，主要是用于做对比

    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {

        set(AnnoTest.class);
        System.out.println(a);
        System.out.println(b);
    }

    public static void set(Class c) throws IllegalArgumentException, IllegalAccessException{
        Field[] fields = c.getDeclaredFields();//获取所有变量
        for(int i=0; i<fields.length;i++){
            fields[i].setAccessible(true);//获取访问私有变量的权限
            if(fields[i].isAnnotationPresent(MyAutowired.class)){//判断是否是使用注解的参数
                MyAutowired t = fields[i].getAnnotation(MyAutowired.class);
                System.out.println("注解中配置的num参数是："+t.num());
                fields[i].set(t, 18);//给变量赋值
            }
        }
    }

}
