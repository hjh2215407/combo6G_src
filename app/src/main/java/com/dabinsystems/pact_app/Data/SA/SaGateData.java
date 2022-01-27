package com.dabinsystems.pact_app.Data.SA;

import android.util.Log;

import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.View.SA.Sweep.GateNumOfGateView;

import java.util.ArrayList;

public class SaGateData {

    ArrayList<GateBoxOffsetData> GateBoxOffsetList;

    public SaGateData() {

        GateBoxOffsetList = new ArrayList<>();
        GateBoxOffsetList.add(new GateBoxOffsetData(0f, 1928.6f));
        GateBoxOffsetList.add(new GateBoxOffsetData(2500f, 4428.6f));
        GateBoxOffsetList.add(new GateBoxOffsetData(5000f, 6928.6f));
        GateBoxOffsetList.add(new GateBoxOffsetData(7500f, 9428.6f));

        //@@ [21.12.22] GateView UpDown Link 관련 수정
        GateBoxOffsetList.add(new GateBoxOffsetData(10000f, 11928.6f));
        GateBoxOffsetList.add(new GateBoxOffsetData(12500f, 14428.6f));
        GateBoxOffsetList.add(new GateBoxOffsetData(15000f, 16928.6f));
        GateBoxOffsetList.add(new GateBoxOffsetData(17500f, 19428.6f));
        ///


        //Number Of Gate을 '1'보다 큰 값을 설정할 때 Gate Delta 메뉴의 기본 값을 Gate View Sweep Time / Number Of Gate 으로 자동 설정
        //- 해당 값을 사용자가 설정할 수 없도록 함.
        GateDelta = GateViewSweepTime / GateNum;
    }

    public enum GATE_MODE {

        OFF(0),
        ON(1);

        private final int value;

        GATE_MODE(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public byte getByte() {
            int val = value & 0xff;
            return (byte) val;
        }
    }

    public enum GATE_SOURCE {

        INTERNAL(0, "Internal"),
        GPS(1, "GPS"),
        SSB(2, "SSB"),
        EXT1PPS(3, "Ext. 1pps");

        private final int value;
        private final String name;

        GATE_SOURCE(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        public byte getByte() {
            int val = value & 0xff;
            return (byte) val;
        }
    }

    public enum GATE_TYPE {
        Slot(1, "Slot"),
        Time(0, "Time");

        private final int value;
        private final String name;

        GATE_TYPE(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        public byte getByte() {
            int val = value & 0xff;
            return (byte) val;
        }
    }


    private GATE_MODE GateMode = GATE_MODE.OFF;
    private GATE_MODE GateView = GATE_MODE.OFF;
    private GATE_SOURCE GateSource = GATE_SOURCE.INTERNAL;

    public final int MAX_GATE_VIEW_SWEEP_TIME = 20000;
    public final int MIN_GATE_VIEW_SWEEP_TIME = 1000;

    public final int MAX_GATE_LENGTH = 10000000;
    public final int MIN_GATE_LENGTH = 5;//60;

    public final int MAX_GATE_DELAY = 10000000;
    public final int MIN_GATE_DELAY = 0;

    private int GateViewSweepTime = 10000;
    private int GateLength = 800;//1857;
    private int GateDelay = 0;

    private int GateNum = 1; // Range : 1, 2, 4, 5, 8 // Default : 1
    private int GateDelta = 0; // Unit : us
    private GATE_TYPE GateType = GATE_TYPE.Time; // 0 : Time, 1 : Slot

    private int GateDelaySlot = 0; // 0 ~ (20 / Gate Number) Gate Type이 ‘1(Slot)’ 인 경우 FW에서 사용되는 변수. ‘20’은 국내 서비스 조건. 해외를 위해 추후 일반화 예정
    //    private int GateDelaySymbol = 0; // 0 ~ 14 Gate Type이 ‘1(Slot)’ 인 경우 FW에서 사용되는 변수
    private int GateLengthSlot = 4; // 0 ~ (20 / Gate Number) Gate Type이 ‘1(Slot)’ 인 경우 FW에서 사용되는 변수
//    private int GateLengthSymbol = 0; // 0 ~ 14 Gate Type이 ‘1(Slot)’ 인 경우 FW에서 사용되는 변수

    private int GateDelaySymbol = 0; // Range : 0 ~ 28 * Gate View Sweep time(ms 제외한 숫자) – 1
    private int GateLengthSymbol = 1; // Fixed 1 symbol


    public GATE_MODE getGateMode() {
        return GateMode;
    }

    public void setGateMode(GATE_MODE gateMode) {
        GateMode = gateMode;
        if (GateMode == GATE_MODE.ON) {
            FunctionHandler.getInstance().getGateLineChart().startGateTimer();
        }
    }

    public GATE_MODE getGateView() {
        return GateView;
    }

    public void setGateView(GATE_MODE gateView) {
        GateView = gateView;
    }


    public GATE_SOURCE getGateSource() {
        return GateSource;
    }

    public void setGateSource(GATE_SOURCE gateSource) {
        GateSource = gateSource;
        FunctionHandler.getInstance().getGateLineChart().updateGateOffsetBox();
    }


    public int getGateLengthMin() {
        return MIN_GATE_LENGTH;
    }

    public int getGateLengthMax() {
        return (GateViewSweepTime == 0) ? 0 : GateViewSweepTime / GateNum - GateDelay;
    }

    public int getGateLength() {
        int val;
        if (GateType == GATE_TYPE.Time)
            val = GateLength;
        else {
            val = GateLengthSymbol;
//            // slot length = 0.5ms = 14 symbols
//            // symbol length in 1 Slot : 1st symbol = 36.198 us, 2nd ~ 14th symbol = 35.677 us
//            int sl = GateLengthSlot * 500;
//            int sy = 0;
//            if (GateLengthSymbol > 0) {
//                sy = (int)(36.198f + ((GateLengthSymbol - 1) * 35.677f));
//            }
//
//            val = sl + sy;
        }
        return val;
    }

    public void setGateLength(int gateLength) {
        //Range : 5 us ~ Gate View Sweep Time / Number of Gate – Gate Delay
        if (gateLength > getGateLengthMax() || gateLength < getGateLengthMin()) return;
        GateLength = gateLength;
    }


    public int getGateDelayMin() {
        return MIN_GATE_DELAY;
    }

    public int getGateDelayMax() {
        return (GateViewSweepTime == 0) ? 0 : GateViewSweepTime / GateNum - GateLength;
    }

    public int getGateDelay() {
        int val;
        if (GateType == GATE_TYPE.Time)
            val = GateDelay;
        else {
            if (GateDelaySymbol > 0) {
                //Gate View Sweep Time 이 ’10’ ms 인 경우 Gate Box가 움직이는 단위는 다음과 같음
                //Gate Delay Range : 0 ~ 279
                //N = Gate Delay / 14
                //Position of Gate Delay = N * 36.199 us + (Gate Delay – N) * 35.677 us + 17.839 us
                //Ex) Gate Delay 가 28 Symbol인 경우의 Gate 위치
                //    N = 28 / 14 = 2
                //    Position of Gate Delay = 2 * 36.199 us + (28 – 2) * 35.677 us + 17.839 = 1017.839 us
                double N = (double) GateDelaySymbol / 14;
                val = (int) (N * 36.199 + ((double) GateDelaySymbol - N) * 35.677 + 17.839);
            } else {
                val = 0;
            }

//            // slot length = 0.5ms = 14 symbols
//            // symbol length in 1 Slot : 1st symbol = 36.198 us, 2nd ~ 14th symbol = 35.677 us
//            int sl = GateDelaySlot * 500;
//            int sy = 0;
//            if (GateDelaySymbol > 0) {
//                sy = (int)(36.198f + ((GateDelaySymbol - 1) * 35.677f));
//            }
//            val = sl + sy;
        }
        return val;
    }

    public void setGateDelay(int gateDelay) {
        //Range : 0 ~ Gate View Sweep Time / Number of Gate – Gate Length
        if (gateDelay > getGateDelayMax() || gateDelay < getGateDelayMin()) return;
        GateDelay = gateDelay;
    }


    public int getGateViewSweepTime() {
        return GateViewSweepTime;
    }

    public void setGateViewSweepTime(int gateViewSweepTime) {
        //Fixed 10 ms
        if (gateViewSweepTime > MAX_GATE_VIEW_SWEEP_TIME || gateViewSweepTime < MIN_GATE_VIEW_SWEEP_TIME)
            return;

        //Log.e("GateViewSweep", "GateSweepTime Setted ("+GateViewSweepTime + " ---> " + gateViewSweepTime + ")");

        GateViewSweepTime = gateViewSweepTime;

        // TODO 해당 값 변경에 따른 영향 받는 값 계산 필요
        setGateNum(GateNum);

    }


    public ArrayList<GateBoxOffsetData> getGateBoxOffsetList() {
        return GateBoxOffsetList;
    }


    public int getGateNum() {
        return GateNum;
    }

    public void setGateNum(int gateNum) {
        //Range : 1, 2, 4, 5, 8
        GateNum = gateNum;

        //TODO 영향 받는 애들 전부 재 계산

        //Number Of Gate을 '1'보다 큰 값을 설정할 때 Gate Delta 메뉴의 기본 값을 Gate View Sweep Time / Number Of Gate 으로 자동 설정
        //- 해당 값을 사용자가 설정할 수 없도록 함.
        GateDelta = GateViewSweepTime / GateNum;

        if (GateLength > getGateLengthMax())
            GateLength = getGateLengthMax();
        if (GateLength < getGateLengthMin())
            GateLength = getGateLengthMin();

        if (GateDelay > getGateDelayMax())
            GateDelay = getGateDelayMax();
        if (GateDelay < getGateDelayMin())
            GateDelay = getGateDelayMin();

//        if (GateDelaySlot > getGateDelaySlotMax())
//            GateDelaySlot = getGateDelaySlotMax();
//        if (GateLengthSlot > getGateLengthSlotMax())
//            GateLengthSlot = getGateLengthSlotMax();

        if (GateLengthSymbol > getGateLengthSymbolMax())
            GateLengthSymbol = getGateLengthSymbolMax();
        if (GateLengthSymbol < getGateLengthSymbolMin())
            GateLengthSymbol = getGateLengthSymbolMin();

        if (GateDelaySymbol > getGateDelaySymbolMax())
            GateDelaySymbol = getGateDelaySymbolMax();
        if (GateDelaySymbol < getGateDelaySymbolMin())
            GateDelaySymbol = getGateDelaySymbolMin();
    }


    public int getGateDeltaMin() {
        return 0;
    }

    public int getGateDeltaMax() {
        return (GateViewSweepTime == 0) ? 0 : (GateViewSweepTime / GateNum);
    }

    public int getGateDelta() {
        return GateDelta;
    }

    public boolean setGateDelta(int gateDelta) {
        //Range : 0 ~ (Gate View Sweep Time / Number of Gate)
        if (gateDelta < getGateDeltaMin() || gateDelta > getGateDeltaMax()) return false;
        this.GateDelta = gateDelta;
        return true;
    }


    public GATE_TYPE getGateType() {
        return GateType;
    }

    public void setGateType(GATE_TYPE gateType) {
        this.GateType = gateType;
    }


    public int getGateDelaySlotMin() {
        return 0;
    }

    public int getGateDelaySlotMax() {
        return 20 / GateNum;
    }

    public int getGateDelaySlot() {
        return GateDelaySlot;
    }

    public boolean setGateDelaySlot(int gateDelaySlot) {
        //Number of Slot : 0 ~ (20 / Number of Gate)
        if (gateDelaySlot < getGateDelaySlotMin() || gateDelaySlot > getGateDelaySlotMax())
            return false;
        this.GateDelaySlot = gateDelaySlot;
        return true;
    }


    public int getGateDelaySymbolMin() {
        return 0;
    }

    public int getGateDelaySymbolMax() {
        // Range : 0 ~ 28 * Gate View Sweep time(ms 제외한 숫자) – 1
        int sweepTimeMs = (int) (GateViewSweepTime / 1000);
        int rtn = (28 * sweepTimeMs) - 1;
        rtn /= GateNum;
        return rtn;
        //return 14;
    }

    public int getGateDelaySymbol() {
        return GateDelaySymbol;
    }

    public boolean setGateDelaySymbol(int gateDelaySymbol) {
        //Number of Symbol : 0 ~ 14
        if (gateDelaySymbol < getGateDelaySymbolMin() || gateDelaySymbol > getGateDelaySymbolMax())
            return false;
        this.GateDelaySymbol = gateDelaySymbol;
        return true;
    }


    public int getGateLengthSlotMin() {
        return 0;
    }

    public int getGateLengthSlotMax() {
        return 20 / GateNum;
    }

    public int getGateLengthSlot() {
        return GateLengthSlot;
    }

    public boolean setGateLengthSlot(int gateLengthSlot) {
        //Number of Slot : 0 ~ (20 / Number of Gate)
        if (gateLengthSlot < getGateLengthSlotMin() || gateLengthSlot > getGateLengthSlotMax())
            return false;
        this.GateLengthSlot = gateLengthSlot;
        return true;
    }


    public int getGateLengthSymbolMin() {
        return 0;
    }

    public int getGateLengthSymbolMax() {
        return 14;
    }

    public int getGateLengthSymbol() {
        return GateLengthSymbol;
    }

    public boolean setGateLengthSymbol(int gateLengthSymbol) {
        //Number of Symbol : 0 ~ 14
        if (gateLengthSymbol < getGateLengthSymbolMin() || gateLengthSymbol > getGateLengthSymbolMax())
            return false;
        this.GateLengthSymbol = gateLengthSymbol;
        return true;
    }

}
