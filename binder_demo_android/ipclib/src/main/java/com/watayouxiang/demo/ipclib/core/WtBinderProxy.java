package com.watayouxiang.demo.ipclib.core;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.watayouxiang.demo.ipclib.WtServiceManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2021/12/03
 *     desc   : 服务代理
 * </pre>
 */
class WtBinderProxy implements InvocationHandler {
    private static final Gson GSON = new Gson();
    private final Class<?> clazz;

    public WtBinderProxy(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.i("wtBinderIPC", "invoke: " + method.getName());
        // 服务调用
        String data = WtBinderIPC.getDefault().sendRequest(clazz, method, args, WtServiceManager.TYPE_INVOKE);
        if (!TextUtils.isEmpty(data)) {
            return GSON.fromJson(data, method.getReturnType());
        }
        return null;
    }
}
