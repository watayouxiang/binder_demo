package com.watayouxiang.demo.binder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.watayouxiang.demo.binder.ipc.DownloadSingleton;
import com.watayouxiang.demo.binder.ipc.UserInfo;
import com.watayouxiang.demo.binder.ipc.UserSingleton;
import com.watayouxiang.demo.ipclib.core.WtBinderIPC;

public class SecondActivity extends AppCompatActivity {

    private DownloadSingleton downloadSingleton;
    private UserSingleton userSingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        // 开启服务
        WtBinderIPC.getDefault().open(this);
    }

    public void findService(View view) {
        // 发现服务
        downloadSingleton = WtBinderIPC.getDefault().getInstance(DownloadSingleton.class);
        userSingleton = WtBinderIPC.getDefault().getInstance(UserSingleton.class);
    }

    public void callService(View view) {
        // 调用服务
        String result = downloadSingleton.download("https://www.baidu.com");
        UserInfo userInfo = userSingleton.getUserInfo();
        Toast.makeText(this, result + "; " + userInfo.toString(), Toast.LENGTH_SHORT).show();
    }
}