package com.watayouxiang.demo.ipclib;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
