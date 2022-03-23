package com.watayouxiang.demo.binder;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.watayouxiang.demo.binder.ipc.UserInfo;
import com.watayouxiang.demo.binder.ipc.UserSingletonImpl;
import com.watayouxiang.demo.ipclib.core.WtBinderIPC;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void registerService(View view) {
        // 开启服务
        WtBinderIPC.getDefault().open(this);
        // 注册服务
        WtBinderIPC.getDefault().register(UserSingletonImpl.class);
        UserSingletonImpl.getInstance().setUserInfo(new UserInfo("123", "watayouxiang"));
    }
}