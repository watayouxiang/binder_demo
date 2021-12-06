package com.watayouxiang.demo.binder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.watayouxiang.demo.binder.ipc.DownloadSingleton;
import com.watayouxiang.demo.binder.ipc.UserInfo;
import com.watayouxiang.demo.binder.ipc.UserSingleton;
import com.watayouxiang.demo.binder.ipc.UserSingletonImpl;
import com.watayouxiang.demo.ipclib.core.WtBinderIPC;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 开启服务
        WtBinderIPC.getDefault().open(this);
        // 注册服务
        WtBinderIPC.getDefault().register(DownloadSingleton.class);
        WtBinderIPC.getDefault().register(UserSingleton.class);
        UserSingletonImpl.getInstance().setUserInfo(new UserInfo("123", "watayouxiang"));
    }

    public void goOtherProcess(View view) {
        startActivity(new Intent(this, SecondActivity.class));
    }
}