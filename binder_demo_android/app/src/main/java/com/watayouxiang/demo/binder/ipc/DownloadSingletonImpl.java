package com.watayouxiang.demo.binder.ipc;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2021/12/06
 *     desc   :
 * </pre>
 */
public class DownloadSingletonImpl implements DownloadSingleton {
    private static DownloadSingletonImpl sInstance = null;
    private DownloadSingletonImpl() {
    }
    public static synchronized DownloadSingletonImpl getInstance(  ) {
        if (sInstance == null) {
            sInstance = new DownloadSingletonImpl();
        }
        return sInstance;
    }

    @Override
    public String download(String url) {
        return "下载成功";
    }
}
