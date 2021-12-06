package com.watayouxiang.demo.ipclib;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.watayouxiang.demo.ipclib.bean.RequestBean;
import com.watayouxiang.demo.ipclib.bean.RequestParameter;
import com.watayouxiang.demo.ipclib.cache.CacheCenter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2021/12/03
 *     desc   :
 * </pre>
 */
public class WtServiceManager extends Service {
    // 服务获取（实例化对象）
    public static final int TYPE_GET = 1;
    // 服务调用
    public static final int TYPE_INVOKE = 2;

    private static final Gson gson = new Gson();
    private static final CacheCenter cacheCenter = CacheCenter.getInstance();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new WtBinderInterface.Stub() {
            @Override
            public String request(String request) throws RemoteException {
                RequestBean requestBean = gson.fromJson(request, RequestBean.class);
                int type = requestBean.getType();
                switch (type) {
                    case TYPE_GET:
                        Method method = cacheCenter.getMethod(requestBean);
                        if (method != null) {
                            Object[] parameters = makeParameterObject(requestBean);
                            try {
                                Object object = method.invoke(null, parameters);
                                if (object != null) {
                                    cacheCenter.putObject(requestBean.getClassName(), object);
                                }
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case TYPE_INVOKE:
                        Method method1 = cacheCenter.getMethod(requestBean);
                        if (method1 != null) {
                            Object instance = cacheCenter.getObject(requestBean.getClassName());
                            Object[] parameters1 = makeParameterObject(requestBean);
                            try {
                                Object result = method1.invoke(instance, parameters1);
                                return gson.toJson(result);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                }
                return null;
            }
        };
    }

    // 构造方法参数数组
    private Object[] makeParameterObject(RequestBean requestBean) {
        Object[] parameters;
        RequestParameter[] requestParameters = requestBean.getRequestParameters();
        if (requestParameters != null && requestParameters.length > 0) {
            parameters = new Object[requestParameters.length];
            for (int i = 0; i < requestParameters.length; i++) {
                RequestParameter requestParameter = requestParameters[i];
                Class<?> clazz = cacheCenter.getClassType(requestParameter.getParameterClassName());
                parameters[i] = gson.fromJson(requestParameter.getParameterValue(), clazz);
            }
        } else {
            parameters = new Object[0];
        }
        return parameters;
    }

}
