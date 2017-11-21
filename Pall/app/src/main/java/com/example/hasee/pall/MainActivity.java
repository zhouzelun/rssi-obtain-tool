package com.example.hasee.pall;

import android.Manifest;
import android.net.wifi.ScanResult;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView list;
    String[] arry ={"asd","asda"};
    WifiAdmin mWifiAdmin;
    Button btn;
    private Thread locate_thread;
    private List<ScanResult> mScanResult_List = new ArrayList<>();
    ArrayAdapter<String> mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView)findViewById(R.id.list);
        btn = (Button) findViewById(R.id.btn);
        mWifiAdmin = new WifiAdmin(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startScan();
            }
        });
    }

    private void startScan() {
        locate_thread = new Thread() {
            @Override
            public void run() {
                while(true) {

                    doActionHandler.sendEmptyMessage(0);
                    try {
                        sleep(1000);
                    }catch (Exception e){}

                }
            }
        };
        locate_thread.start();
    }
    private Handler doActionHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case 0:
                    //mScanResult_List.clear();
                    mWifiAdmin.startScan();
                    mScanResult_List = mWifiAdmin.getWifiList();
                    arry = new String[mScanResult_List.size()];
                    int count = 0;
                    for(ScanResult s : mScanResult_List){
                        arry[count++] = s.SSID+"--------"+s.level;
                    }
                    mAdapter = new ArrayAdapter<String>(MainActivity.this,R.layout.list_item,arry);
                    list.setAdapter(mAdapter);

                    break;
            }
        }
    };
}
