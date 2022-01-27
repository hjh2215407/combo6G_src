package com.dabinsystems.pact_app.List.Item;

import android.net.wifi.ScanResult;

public class AccessPointItem {

    private String ssid;
    private String bssid;
    private String rssi;
    private ScanResult result;

    public AccessPointItem(String ssid, String bssid, String rssi, ScanResult result) {
        this.ssid = ssid;
        this.bssid = bssid;
        this.rssi = rssi;
        this.result = result;
    }

    public String getSsid() {
        return ssid;
    }

    public String getBssid() {
        return bssid;
    }

    public String getRssi() {
        return rssi;
    }

    public ScanResult getScanResult() {
        return result;
    }
}
