package com.watayouxiang.demo.binder.ipc;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2021/12/06
 *     desc   :
 * </pre>
 */
public class UserSingletonImpl implements UserSingleton {
    private static UserSingletonImpl sInstance = null;
    private UserSingletonImpl() {
    }
    public static synchronized UserSingletonImpl getInstance() {
        if (sInstance == null) {
            sInstance = new UserSingletonImpl();
        }
        return sInstance;
    }

    UserInfo userInfo;

    @Override
    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
