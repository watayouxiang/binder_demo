package com.watayouxiang.demo.binder2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.watayouxiang.demo.binder.ipc.UserInfo;
import com.watayouxiang.demo.binder.ipc.UserSingleton;
import com.watayouxiang.demo.ipclib.core.WtBinderIPC;

public class MainActivity extends AppCompatActivity {

    private UserSingleton userSingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 开启服务
        WtBinderIPC.getDefault().open(this, "com.watayouxiang.demo.binder");
    }

    public void findService(View view) {
        // 发现服务
        userSingleton = WtBinderIPC.getDefault().getInstance(UserSingleton.class);
    }

    public void callService(View view) {
        // 调用服务
        UserInfo userInfo = userSingleton.getUserInfo();
        Toast.makeText(this, userInfo.toString(), Toast.LENGTH_SHORT).show();
    }
}