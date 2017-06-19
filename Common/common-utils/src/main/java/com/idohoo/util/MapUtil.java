package com.idohoo.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 和map相关的工具类
 * @author zhangxh
 * @date 2016-8-11 下午9:04:12
 */
public class MapUtil
{

    public static int toInt(Object value)
    {
        if(value == null || "".equals(value)){
            return 0;
        }
        try{
            return Integer.valueOf(value.toString());
        }catch(Exception e){
            return 0;
        }
    }
    
    /**
     * @Description 将list转换为map
     * @author zhangxh
     * @date 2016-8-11 下午9:28:05
     * @param list
     * @param field
     * @return
     * @lastModifier
     */
    public static <T> Map<Integer,T> toMap(List<T> list,String field)
    {
        Map<Integer,T> result = new HashMap<Integer,T>();
        if(list == null || list.isEmpty()){
            return result;
        }
        String methodName = "get"+(field.charAt(0)+"").toUpperCase()+field.substring(1);
        for(T o : list){
        	if( o == null) continue;
            Class<?> clazz = o.getClass();
            try{
                Method method = clazz.getMethod(methodName);
                Object value = method.invoke(o);
                result.put(toInt(value), o);
            }catch(Exception e){
                
            }
        }
        return result;
    }
    
    /**
     * @Description 将List 分组
     * @author xhz
     * @date 2016-9-1 下午2:42:51
     * @param list
     * @param field
     * @return
     * @lastModifier
     */
    public static <T> Map<Integer,List<T>> toMapList(List<T> list,String field)
    {
        Map<Integer,List<T>> result = new HashMap<Integer,List<T>>();
        if(list == null || list.isEmpty()){
            return result;
        }
        String methodName = "get"+(field.charAt(0)+"").toUpperCase()+field.substring(1);
        for(T o : list){
            Class<?> clazz = o.getClass();
            try{
                Method method = clazz.getMethod(methodName);
                Object value = method.invoke(o);
                List<T> listValue = result.get(value);
                if(listValue == null){
                    listValue = new ArrayList<T>();
                    result.put(toInt(value), listValue);
                }
                listValue.add(o);
            }catch(Exception e){
                
            }
        }
        return result;
    }
    
    /**
     * @Description 将list分组
     * @author xhz
     * @date 2016-9-1 下午2:43:08
     * @param list
     * @param field
     * @return
     * @lastModifier
     */
    public static <T> Map<String,List<T>> toMapListByString(List<T> list,String field)
    {
        Map<String,List<T>> result = new HashMap<String,List<T>>();
        if(list == null || list.isEmpty()){
            return result;
        }
        String methodName = "get"+(field.charAt(0)+"").toUpperCase()+field.substring(1);
        for(T o : list){
            Class<?> clazz = o.getClass();
            try{
                Method method = clazz.getMethod(methodName);
                Object value = method.invoke(o);
                List<T> listValue = result.get(value);
                if(listValue == null){
                    listValue = new ArrayList<T>();
                    result.put(value==null?"":value.toString(), listValue);
                }
                listValue.add(o);
            }catch(Exception e){
                
            }
        }
        return result;
    }
    
    /**
     * @Description 
     * @author xhz
     * @date 2016-9-1 下午3:08:25
     * @param map
     * @param key
     * @return
     * @lastModifier
     */
    public static <T1,T2> List<T2> getList(Map<T1,List<T2>> map,T1 key)
    {
        List<T2> result = map.get(key);
        if(result == null){
            result = new ArrayList<T2>();
        }
        return result;
    }
}
