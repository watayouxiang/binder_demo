package com.watayouxiang.demo.ipclib.core;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.text.TextUtils;

import com.watayouxiang.demo.ipclib.WtBinderInterface;
import com.watayouxiang.demo.ipclib.WtServiceManager;

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

    // ====================================================================================
    // 单例
    // ====================================================================================

    private static final WtBinderIPC ourInstance = new WtBinderIPC();

    public static WtBinderIPC getDefault() {
        return ourInstance;
    }

    // ====================================================================================
    // 服务注册
    // ====================================================================================

    public void register(Class<?> clazz) {

    }

    // ====================================================================================
    // 开启服务
    // ====================================================================================

    private void init(Context context) {
        sContext = context.getApplicationContext();
    }

    public void open(Context context) {
        open(context, null);
    }

    public void open(Context context, String packageName) {
        init(context);
        bind(context.getApplicationContext(), packageName, WtServiceManager.class);
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
}
