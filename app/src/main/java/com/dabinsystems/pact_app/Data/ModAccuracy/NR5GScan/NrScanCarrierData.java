package com.dabinsystems.pact_app.Data.ModAccuracy.NR5GScan;

import com.dabinsystems.pact_app.Data.EnumOnOff;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5G.NrDuplexData;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5G.NrSCSData;

import java.text.NumberFormat;

/**
 * [jigum] 2021-07-22
 * <p>
 * 5G NR Scan Carrier 설정 값
 */
public class NrScanCarrierData {

    private final int NumOfCarrier = NrScanData.NR_SCAN_CARRIER_COUNT;
    private int CurrentCarrierIdx = 0;
    private int SelectedCarrierIdx = 0;

    private final EnumOnOff[] CarrierSetting = {EnumOnOff.ON, EnumOnOff.ON, EnumOnOff.ON, EnumOnOff.OFF};
    private final long[] FreqOfCarrier = {3650010000L, 3549990000L, 3459990000L, 3000000000L};
    private final NR_SCAN_PROFILE[] Profile = {NR_SCAN_PROFILE.MHZ100, NR_SCAN_PROFILE.MHZ100, NR_SCAN_PROFILE.MHZ80, NR_SCAN_PROFILE.MHZ100};

    //@@ [22.01.03] Carrier Duplex, SCS
    private NrDuplexData[] DuplexData;
    private NrSCSData[] ScsData;

    public NrScanCarrierData() {
        DuplexData = new NrDuplexData[NumOfCarrier];
        ScsData = new NrSCSData[NumOfCarrier];

        for (int i = 0; i < NumOfCarrier; i++) {
            DuplexData[i] = new NrDuplexData();
            ScsData[i] = new NrSCSData();
        }
    }

    //@@

    public int getNumOfCarrier() {
        return NumOfCarrier;
    }

    public int getCurrentCarrierIdx() {
        return CurrentCarrierIdx;
    }

    public boolean setCurrentCarrierIdx(int currentCarrierIdx) {
        if (currentCarrierIdx < NumOfCarrier) {
            CurrentCarrierIdx = currentCarrierIdx;
            return true;
        }
        return false;
    }

    public int getSelectedCarrierIdx() {
        return SelectedCarrierIdx;
    }

    public boolean setSelectedCarrierIdx(int selectedCarrierIdx) {
        if (selectedCarrierIdx < NumOfCarrier) {
            SelectedCarrierIdx = selectedCarrierIdx;
            return true;
        }
        return false;
    }

    public EnumOnOff getCarrierSetting(int idx) {
        return CarrierSetting[idx];
    }

    public void setCarrierSetting(int idx, EnumOnOff carrierSetting) {
        CarrierSetting[idx] = carrierSetting;
    }

    public boolean isOn(int idx) {
        return CarrierSetting[idx] == EnumOnOff.ON;
    }

    public void setOn(int idx, boolean b) {
        CarrierSetting[idx] = b ? EnumOnOff.ON : EnumOnOff.OFF;
    }

    // 70 ~ 6000 MHz
    public long getFreqOfCarrierMin() {
        return 70000000L;
    }

    public long getFreqOfCarrierMax() {
        return 6000000000L;
    }

    public String getFreqOfCarrierMinMaxToString() {
        return "70 ~ 6000 MHz";
    }

    public long getFreqOfCarrier(int idx) {
        return FreqOfCarrier[idx];
    }

    public void setFreqOfCarrier(int idx, long freqOfCarrier) {
        FreqOfCarrier[idx] = freqOfCarrier;
    }

    public NR_SCAN_PROFILE getProfile(int idx) {
        return Profile[idx];
    }

    public void setProfile(int idx, NR_SCAN_PROFILE profile) {
        Profile[idx] = profile;
    }


    public String getFreqToMHzString(int idx, boolean isUnit) {
        NumberFormat f = NumberFormat.getInstance();
        f.setGroupingUsed(false);
        return f.format((double) FreqOfCarrier[idx] / 1000000) + (isUnit ? " MHz" : "");
    }

    public String getProfileToMHzString(int idx) {
        return Profile[idx].getString();
    }

    // Duplex Data

    public NrDuplexData getDuplexData() {
        return DuplexData[SelectedCarrierIdx];
    }

    public NrDuplexData getDuplexData(int carrierIdx) {
        return DuplexData[carrierIdx];
    }

    public void setDuplexData(NrDuplexData duplexData) { DuplexData[SelectedCarrierIdx] = duplexData; }

    public void setDuplexData(NrDuplexData duplexData, int carrierIdx) { DuplexData[carrierIdx] = duplexData; }

    // SCS Data

    public NrSCSData getScsData() { return ScsData[SelectedCarrierIdx]; }

    public NrSCSData getScsData(int carrierIdx) { return ScsData[carrierIdx]; }

    public void setScsData(NrSCSData scsData) { ScsData[SelectedCarrierIdx] = scsData; }

    public void setScsData(NrSCSData scsData, int carrierIdx) { ScsData[carrierIdx] = scsData; }
}
