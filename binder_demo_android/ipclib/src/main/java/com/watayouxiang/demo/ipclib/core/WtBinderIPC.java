package com.watayouxiang.demo.ipclib.core;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.watayouxiang.demo.ipclib.ClassId;
import com.watayouxiang.demo.ipclib.WtBinderInterface;
import com.watayouxiang.demo.ipclib.WtServiceManager;
import com.watayouxiang.demo.ipclib.bean.RequestBean;
import com.watayouxiang.demo.ipclib.bean.RequestParameter;
import com.watayouxiang.demo.ipclib.cache.CacheCenter;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2021/12/03
 *     desc   : 1、服务注册
 *              2、服务发现
 *              3、服务调用
 * </pre>
 */
public class WtBinderIPC {

    private Context sContext;
    private WtBinderInterface wtBinderInterface;
    private CacheCenter cacheCenter = CacheCenter.getInstance();
    private static final Gson GSON = new Gson();

    // ====================================================================================
    // 单例
    // ====================================================================================

    private static final WtBinderIPC ourInstance = new WtBinderIPC();

    public static WtBinderIPC getDefault() {
        return ourInstance;
    }

    // ====================================================================================
    // 开启服务
    // ====================================================================================

    public void open(Context context) {
        open(context, null);
    }

    public void open(Context context, String packageName) {
        init(context);
        bind(context.getApplicationContext(), packageName, WtServiceManager.class);
    }

    private void init(Context context) {
        sContext = context.getApplicationContext();
    }

    private void bind(Context context, String packageName, Class<? extends WtServiceManager> service) {
        Intent intent;
        if (TextUtils.isEmpty(packageName)) {
            intent = new Intent(context, service);
        } else {
            ComponentName component = new ComponentName(packageName, service.getName());
            intent = new Intent();
            intent.setComponent(component);
            intent.setAction(service.getName());
        }
        WtServiceConnection wtServiceConnection = new WtServiceConnection();
        context.bindService(intent, wtServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private class WtServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            wtBinderInterface = WtBinderInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    // ====================================================================================
    // 服务注册
    // ====================================================================================

    public void register(Class<?> clazz) {
        cacheCenter.register(clazz);
    }

    // ====================================================================================
    // 服务发现 / 服务调用
    // ====================================================================================

    public <T> T getInstance(Class<T> clazz, Object... parameters) {
        // 服务获取
        sendRequest(clazz, null, parameters, WtServiceManager.TYPE_GET);
        return getProxy(clazz);
    }

    private <T> T getProxy(Class<T> clazz) {
        ClassLoader classLoader = sContext.getClassLoader();
        return (T) Proxy.newProxyInstance(classLoader, new Class[]{clazz}, new BpBinder(clazz));
    }

    /**
     * 服务获取 / 服务调用
     *
     * @param clazz      类名
     * @param method     方法
     * @param parameters 方法参数
     * @param type       类型（1服务获取；2服务调用）
     */
    public <T> String sendRequest(Class<T> clazz, Method method, Object[] parameters, int type) {
        // 类名
        String className = clazz.getAnnotation(ClassId.class).value();
        // 方法名
        String methodName = method == null ? "getInstance" : method.getName();

        // 封装请求参数
        RequestParameter[] requestParameters = null;
        if (parameters != null && parameters.length > 0) {
            requestParameters = new RequestParameter[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                Object parameter = parameters[i];
                String parameterClassName = parameter.getClass().getName();
                String parameterValue = GSON.toJson(parameter);
                RequestParameter requestParameter = new RequestParameter(parameterClassName, parameterValue);
                requestParameters[i] = requestParameter;
            }
        }

        // 请求模型
        RequestBean requestBean = new RequestBean(type, className, methodName, requestParameters);
        String msg = GSON.toJson(requestBean);

        // 做真正的请求
        String resp = null;
        try {
            resp = wtBinderInterface.request(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return resp;
    }

}
