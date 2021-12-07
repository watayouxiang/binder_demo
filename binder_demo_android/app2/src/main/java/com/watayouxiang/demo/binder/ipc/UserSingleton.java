package com.watayouxiang.demo.binder.ipc;

import com.watayouxiang.demo.ipclib.ClassId;

// 实现类
@ClassId("com.watayouxiang.demo.binder.ipc.UserSingletonImpl")
public interface UserSingleton {
    UserInfo getUserInfo();
}
