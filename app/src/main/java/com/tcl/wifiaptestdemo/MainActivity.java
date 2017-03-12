package com.tcl.wifiaptestdemo;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {
    private WifiManager wifiManager;
    private Button open;
    private boolean flag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        open=(Button)findViewById(R.id.open_hotspot);
        //通过按钮事件设置热点
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果是打开状态就关闭，如果是关闭就打开
                flag=!flag;
                Log.i("YJ--->","out flag:"+flag);
                if (flag){
                    Log.i("YJ--->","setWifiApEnabled state:"+setWifiApEnabled(flag));
                    if (setWifiApEnabled(flag)){
                        Log.i("YJ--->","inner setWifiApEnabled state:"+setWifiApEnabled(flag));
                        open.setText("关闭热点");
                    }
                }
                else {
                    Log.i("YJ--->","setWifiApEnabled state:"+setWifiApEnabled(flag));
                    if (setWifiApEnabled(flag)){
                        Log.i("YJ--->","inner setWifiApEnabled state:"+setWifiApEnabled(flag));
                        open.setText("打开热点");
                    }
                }
            }
        });

    }

    // wifi热点开关
    public boolean setWifiApEnabled(boolean enabled) {
        if (enabled) { // disable WiFi in any case
            //wifi和热点不能同时打开，所以打开热点的时候需要关闭wifi
            wifiManager.setWifiEnabled(false);
        }
        try {
            //热点的配置类
            WifiConfiguration apConfig = new WifiConfiguration();
            //配置热点的名称(可以在名字后面加点随机数什么的)
            apConfig.SSID = "YRCCONNECTION";
            //配置热点的密码
            apConfig.preSharedKey="12122112";
            //通过反射调用设置热点
            Method method = wifiManager.getClass().getMethod(
                    "setWifiApEnabled", WifiConfiguration.class, Boolean.TYPE);
            //返回热点打开状态
            return (Boolean) method.invoke(wifiManager, apConfig, enabled);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
