package com.jietu.admin.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 自定义断言类
 *
 * @author 印修河
 * @date 2017 /12/22 9:10
 */
public class Assert {

    /**
     * 断言为空
     *
     * @param obj     the obj
     * @param message the message
     */
    public static void isNull(Object obj, String message){
        if(obj != null){
            //如果为字符串,并且不为空
            if(obj instanceof String && StringUtils.isBlank((String)obj)){
                return ;
            }
            throw  new MyException(message);
        }
    }

    /**
     * 断言不为空
     *
     * @param obj     the obj
     * @param message the message
     */
    public static void notNull(Object obj, String message){
        if(obj == null){
            throw  new MyException(message);
        }
        //如果为字符串,并且不为空
        if(obj instanceof String && StringUtils.isBlank((String)obj)){
            throw  new MyException(message);
        }
    }

    /**
     * Is true.
     *
     * @param b       the b
     * @param message the message
     */
    public static void isTrue(boolean b, String message){
        if(!b){
            throw new MyException(message);
        }
    }

    /**
     * Is false.
     *
     * @param b       the b
     * @param message the message
     */
    public static void isFalse(boolean b, String message){
        if(b){
            throw new MyException(message);
        }
    }

    /**
     * 断言两个变量相等
     * @param o1 变量1
     * @param o2 变量2
     * @param message 异常信息
     */
    public static void isEquals(Object o1, Object o2, String message){
        if(o1 != null && o2 == null){
            throw new MyException(message);
        }
        if(o1 == null && o2 != null){
            throw new MyException(message);
        }
        if(o1 == null && o2 == null){
            return ;
        }
        //不相等则抛出异常
        if(!o1.equals(o2)){
            throw new MyException(message);
        }
    }

    /**
     * 断言两个变量不相等
     * @param o1 变量1
     * @param o2 变量2
     * @param message 异常信息
     */
    public static void notEquals(Object o1, Object o2, String message){
        if(o1 != null && o2 == null){
            return ;
        }
        if(o1 == null && o2 != null){
            return ;
        }
        if(o1 == null && o2 == null){
            throw new MyException(message);
        }
        //相等则抛出异常
        if(o1.equals(o2)){
            throw new MyException(message);
        }
    }

}
