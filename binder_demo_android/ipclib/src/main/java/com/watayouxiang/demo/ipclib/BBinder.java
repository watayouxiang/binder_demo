package com.watayouxiang.demo.ipclib;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2021/12/07
 *     desc   :
 * </pre>
 */
public class BBinder {
    public Object onTransact(Object obj, Method method, Object[] parameters) {
        Object object = null;
        try {
            object = method.invoke(obj, parameters);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return object;
    }
}
