package com.example.hasee.pall;

import java.util.List;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;

/**
 * Created by zzl on 2015/11/26.
 */
public class WifiAdmin {
    //����һ��WifiManager����
    private WifiManager mWifiManager;
    //����һ��WifiInfo����
    private WifiInfo mWifiInfo;
    //ɨ��������������б�
    private List<ScanResult> mWifiList;
    //���������б�
    private List<WifiConfiguration> mWifiConfigurations;

    WifiLock mWifiLock;

    public WifiAdmin(Context context){
        //ȡ��WifiManager����
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        //ȡ��wifiInfo����
        mWifiInfo = mWifiManager.getConnectionInfo();
        if (mWifiManager.getWifiState() != WifiManager.WIFI_STATE_ENABLED) {
            if (mWifiManager == null) {
                mWifiManager = (WifiManager) context
                        .getSystemService(Context.WIFI_SERVICE);
            }
            if (!mWifiManager.isWifiEnabled()) {
                mWifiManager.setWifiEnabled(true);
            }
        }
    }


    //开启wifi
    public void openWifi(){
        if(!mWifiManager.isWifiEnabled())
            mWifiManager.setWifiEnabled(true);
    }

    //关闭wifi
    public void closeWifi(){
        if(!mWifiManager.isWifiEnabled())
            mWifiManager.setWifiEnabled(false);
    }

    //查看wifi是否打开
    public int checkState(){
        return mWifiManager.getWifiState();
    }

    //挂起wifi radio
    public void acquireWifiLock(){
        mWifiLock.acquire();
    }

    //允许wifi空闲的时候关闭
    public void releaseWifiLock(){
        //�ж��Ƿ�����
        if(mWifiLock.isHeld())
            mWifiLock.release();
    }

    //创建wifiLock
    public void createWifiLock(){
        mWifiLock = mWifiManager.createWifiLock("test");
    }

    //�õ����úõ�����
    public List<WifiConfiguration> getConfiguration(){
        return mWifiConfigurations;
    }

    //ָ�����úõ������������
    public void connectionConfiguration(int index){
        if(index > mWifiConfigurations.size()){
            return ;
        }
        //�������ú�ָ��ID������
        mWifiManager.enableNetwork(mWifiConfigurations.get(index).networkId, false);
    }

    public void startScan(){
        mWifiManager.startScan();
        mWifiList = mWifiManager.getScanResults();

        mWifiConfigurations = mWifiManager.getConfiguredNetworks();
    }

    //�õ������б�
    public List<ScanResult> getWifiList(){
        return mWifiList;
    }

    //�鿴ɨ����
    public StringBuffer lookUpScan(){
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i < mWifiList.size();i++){
            sb.append("Index_"+new Integer(i+1).toString()+":");
            //��ScanResult��Ϣת����һ���ַ�����
            //���а�����BSSID,SSID,capability,frequency,level
            sb.append((mWifiList.get(i)).toString()).append("\n");
        }
        return sb;
    }

    public String getMacAddress(){
        return (mWifiInfo == null)?"NULL":mWifiInfo.getMacAddress();
    }
    /*BSSID:一群计算机设定相同的BSS名称，即可自成一个group。每个BSS都会被赋予一个BSSID，
    *它是一个长度为48位的二进制标识符，用来识别不同的BSS。其的主要优点是它可以作为过滤之用。
    */
    public String getBSSID(){
        return (mWifiInfo == null)?"NULL":mWifiInfo.getBSSID();
    }

    public int getIpAddress(){
        return (mWifiInfo == null)?0:mWifiInfo.getIpAddress();
    }

    //�õ����ӵ�ID
    public int getNetWordId(){
        return (mWifiInfo == null)?0:mWifiInfo.getNetworkId();
    }

    //�õ�wifiInfo��������Ϣ
    public String getWifiInfo(){
        return (mWifiInfo == null)?"NULL":mWifiInfo.toString();
    }
    public String getSSID(){return (mWifiInfo == null)?"NULL":mWifiInfo.getSSID(); }
    //获取RSSIֵ
    public int getRSS(){
        return mWifiInfo.getRssi();
    }

    //获取连接速率
    public int get_link_speed(){
        return mWifiInfo.getLinkSpeed();
    }

    //���һ�����粢����
    public void addNetWork(WifiConfiguration configuration){
        int wcgId = mWifiManager.addNetwork(configuration);
        mWifiManager.enableNetwork(wcgId, false);
    }

    //�Ͽ�ָ��ID������
    public void disConnectionWifi(int netId){
        mWifiManager.disableNetwork(netId);
        mWifiManager.disconnect();
    }
}
