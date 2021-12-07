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
public class BBBinder {
    public Object onTransact(Method method, Object[] parameters) {
        Object object = null;
        try {
            object = method.invoke(null, parameters);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return object;
    }
}
