package com.watayouxiang.demo.ipclib.cache;

import com.watayouxiang.demo.ipclib.bean.RequestBean;
import com.watayouxiang.demo.ipclib.bean.RequestParameter;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2021/12/03
 *     desc   :
 * </pre>
 */
public class CacheCenter {
    // key = className; value = class
    private final ConcurrentHashMap<String, Class<?>> mClassMap;
    // key = className; value = [方法签名 : method]
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, Method>> mAllMethodMap;
    // key = 类名; value = 实例化对象
    private final ConcurrentHashMap<String, Object> mInstanceObjectMap;

    // ====================================================================================
    // 单例
    // ====================================================================================

    private static final CacheCenter ourInstance = new CacheCenter();

    public static CacheCenter getInstance() {
        return ourInstance;
    }

    private CacheCenter() {
        mClassMap = new ConcurrentHashMap<>();
        mAllMethodMap = new ConcurrentHashMap<>();
        mInstanceObjectMap = new ConcurrentHashMap<>();
    }

    // ====================================================================================
    // 实例化对象的存取
    // ====================================================================================

    public void putObject(String className, Object instance) {
        mInstanceObjectMap.put(className, instance);
    }

    public Object getObject(String className) {
        return mInstanceObjectMap.get(className);
    }

    // ====================================================================================
    // 注册
    // ====================================================================================

    public void register(Class<?> clazz) {
        registerClass(clazz);
        registerMethod(clazz);
    }

    private void registerClass(Class<?> clazz) {
        String className = clazz.getName();
        mClassMap.put(className, clazz);
    }

    private void registerMethod(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            ConcurrentHashMap<String, Method> map = mAllMethodMap.get(clazz.getName());
            if (map == null) {
                map = new ConcurrentHashMap<>();
                mAllMethodMap.put(clazz.getName(), map);
            }
            String key = getMethodParameters(method);
            map.put(key, method);
        }
    }

    /**
     * 获取方法签名
     * <p>
     * ex: 方法名-参数1类型-参数2类型
     */
    private String getMethodParameters(Method method) {
        // 方法名-参数1类型-参数2类型
        StringBuilder builder = new StringBuilder();
        builder.append(method.getName());
        Class<?>[] classes = method.getParameterTypes();
        int length = classes.length;
        if (length == 0) {
            return builder.toString();
        }
        for (int i = 0; i < length; ++i) {
            builder.append("-").append(classes[i].getName());
        }
        return builder.toString();
    }

    // ====================================================================================
    // 查找方法
    // ====================================================================================

    public Method getMethod(RequestBean requestBean) {
        ConcurrentHashMap<String, Method> map = mAllMethodMap.get(requestBean.getClassName());
        if (map != null) {
            String key = getMethodParameters(requestBean);
            return map.get(key);
        }
        return null;
    }

    private String getMethodParameters(RequestBean requestBean) {
        StringBuilder result = new StringBuilder();
        result.append(requestBean.getMethodName());
        RequestParameter[] requestParameters = requestBean.getRequestParameters();
        if (requestParameters == null || requestParameters.length == 0) {
            return result.toString();
        }
        for (int i = 0; i < requestParameters.length; ++i) {
            result.append("-").append(requestParameters[i].getParameterClassName());
        }
        return result.toString();
    }

    // ====================================================================================
    // 根据类名反射出类
    // ====================================================================================

    public Class<?> getClassType(String parameterClassName) {
        try {
            return Class.forName(parameterClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
