package com.watayouxiang.demo.binder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.watayouxiang.demo.binder.ipc.DownloadSingleton;
import com.watayouxiang.demo.ipclib.core.WtBinderIPC;

public class SecondActivity extends AppCompatActivity {

    private DownloadSingleton downloadSingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void invoke(View view) {
        // 开启服务
        WtBinderIPC.getDefault().open(this);

        // 发现服务
        downloadSingleton = WtBinderIPC.getDefault().getInstance(DownloadSingleton.class);

        // 调用服务
        String result = downloadSingleton.download("https://www.baidu.com");
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();

    }
}