package com.farmer.utils;


import java.lang.reflect.Field;
import java.sql.ResultSet;

/**
 * 反射工具类
 * @author farmer
 * @version 1.0
 * @data 2019/4/25 21:47
 **/
public class ReflectUtils {


    public static  <T> T resultSetToObject(ResultSet resultSet, Class<T> classParam) {

        T newInstance = null;

        try{

            newInstance = classParam.newInstance();

            Field[] declaredFields = classParam.getDeclaredFields();

            for (int i = 0; i < declaredFields.length; i++) {


                Field declaredField = declaredFields[i];

                declaredField.setAccessible(true);

                String name = declaredField.getName();

                declaredField.set(newInstance, resultSet.getObject(name));

            }

        }catch (Exception e){

            e.printStackTrace();
        }

        return newInstance;
    }

}
