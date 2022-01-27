package com.dabinsystems.pact_app.Data.ModAccuracy.NR5GScan;

import java.nio.ByteBuffer;

/**
 * [jigum] 2021-07-22
 *
 * 5G NR Scan 장비로 부터 받은 데이터
 */
public class NrScanRcvData {

    private final String[] Names = { "SKT", "KT", "LGU+", "C4"};

    private NrScanRcvItem[] items;
    private int RcvTimeIndex = 0;

    public NrScanRcvData() {
        this.items = new NrScanRcvItem[NrScanData.NR_SCAN_CARRIER_COUNT];
        for (int i = 0; i < items.length; ++i) {
            items[i] = new NrScanRcvItem();
        }
    }

    public void clear() {
        for (NrScanRcvItem item : items) {
            item.clear();
        }
        RcvTimeIndex = 0;
    }

    public int setRcvData(ByteBuffer bb) {
        //Mode              S32     4 Byte      0x42 : Mod Accuracy
        //Type              S32     4 Byte      0x11 : 5G NR Scan
        //Carrier Index     U8      1 Byte      Range : 0 ~ 3.
        int idx = bb.get(8);
        if (idx < 0 || idx >= NrScanData.NR_SCAN_CARRIER_COUNT)
            return -1;
        items[idx].set(bb);
        items[idx].setTimeIdx(RcvTimeIndex);
        return idx;
    }

    public NrScanRcvItem get(int idx) {
        return items[idx];
    }

    public void setOn(int idx, boolean b) {
        items[idx].setOn(b);
    }

    public boolean isOn(int idx) {
        return items[idx].isOn();
    }

    public String getName(int idx) {
        return Names[idx];
    }

    public int getRcvTimeIndex() {
        return RcvTimeIndex;
    }

    public void setRcvTimeIndex(int rcvTimeIndex) {
        RcvTimeIndex = rcvTimeIndex;
    }

    public void rcvAll() {
        RcvTimeIndex++;
    }
}
