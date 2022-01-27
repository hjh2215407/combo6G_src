package com.dabinsystems.pact_app.Data.SA;

import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.TraceEnumData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.TraceEnumData.DETECTOR;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.TraceEnumData.TRACE_MODE;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.TraceEnumData.TRACE_TYPE;

import org.apache.poi.hssf.util.HSSFColor;

import java.util.Arrays;

public class TraceData {

    public static final int MAX_TRACE_NUM = 4;

    private int mSelectedTrace = 0;

    private TRACE_MODE[] mSelectedMode;
    private TRACE_TYPE[] mSelectedType;
    private DETECTOR[] mSelectedDetector;

    private TRACE_MODE SemTraceMode = TRACE_MODE.AVERAGE;
    private TRACE_TYPE SemTraceType = TRACE_TYPE.UPDATE;
    private DETECTOR SemChannelDetector = DETECTOR.RMS;
    private DETECTOR SemOffsetDetector = DETECTOR.PEAK;

    public TraceData() {

        mSelectedMode = new TRACE_MODE[4];
        mSelectedType = new TRACE_TYPE[]{TRACE_TYPE.UPDATE, TRACE_TYPE.BLANK, TRACE_TYPE.BLANK, TRACE_TYPE.BLANK};
        mSelectedDetector = new DETECTOR[4];

        Arrays.fill(mSelectedMode, TRACE_MODE.CLEAR_WRITE);
        Arrays.fill(mSelectedDetector, DETECTOR.NORMAL);

    }

    public void setTrace(int idx) {

        if(idx < 0 || idx >= 4) return;

        mSelectedTrace = idx;

    }

    public void setAllMode(TRACE_MODE mode) {

        for(int i=0; i<MAX_TRACE_NUM; i++) {

            setMode(mode, i);

        }

    }

    public int getTraceIndex() {
        return mSelectedTrace;
    }


    /*set trace type*/

    public void setType(TRACE_TYPE type) {

        if (mSelectedTrace == -1) return;
        mSelectedType[mSelectedTrace] = type;

    }

    public void setType(TRACE_TYPE type, int idx) {

        if (idx < 0 || idx >= 4) return;
        mSelectedType[idx] = type;

    }

    public void setAllType(TRACE_TYPE type) {

        for(int i=0; i<MAX_TRACE_NUM; i++) {

            setType(type, i);

        }

    }


    public TRACE_TYPE getType() {
        if (mSelectedTrace == -1) return null;
        return mSelectedType[mSelectedTrace];
    }

    public TRACE_TYPE getType(int idx) {

        if(idx < 0 || idx >= mSelectedType.length) return null;

        return mSelectedType[idx];

    }



    /*set trace mode*/


    public void setMode(TRACE_MODE mode) {

        if (mSelectedTrace == -1) return;
        mSelectedMode[mSelectedTrace] = mode;

    }

    public void setMode(TRACE_MODE mode, int idx) {

        if (idx < 0 || idx >= 4) return;
        mSelectedMode[idx] = mode;
    }

    public TRACE_MODE getMode() {

        if(mSelectedTrace == -1) return null;
        return mSelectedMode[mSelectedTrace];

    }

    public TRACE_MODE getMode(int idx) {

        if(idx < 0 || idx >= mSelectedMode.length) return null;

        return mSelectedMode[idx];

    }



    /*set detector*/

    public void setDetector(DETECTOR detector) {

        if (mSelectedTrace == -1) return;

        mSelectedDetector[mSelectedTrace] = detector;

    }

    public void setDetector(DETECTOR detector, int idx) {

        if (idx < 0 || idx >= 4) return;

        mSelectedDetector[idx] = detector;

    }

    public void setAllDetector(DETECTOR detector) {

        for(int i=0; i<MAX_TRACE_NUM; i++) {

            setDetector(detector, i);

        }

    }

    public DETECTOR getDetector() {
        if(mSelectedTrace == -1) return null;

        return mSelectedDetector[mSelectedTrace];
    }

    public DETECTOR getDetector(int idx) {

        if(idx < 0 || idx >= mSelectedDetector.length) return null;

        return mSelectedDetector[idx];

    }

    public int getMaxTraceNum() {

        return MAX_TRACE_NUM;
    }

    public TRACE_MODE getSemTraceMode() {
        return SemTraceMode;
    }

    public void setSemTraceMode(TRACE_MODE semTraceMode) {
        SemTraceMode = semTraceMode;
    }

    public DETECTOR getSemChannelDetector() {
        return SemChannelDetector;
    }

    public void setSemChannelDetector(DETECTOR semChannelDetector) {
        SemChannelDetector = semChannelDetector;
    }

    public DETECTOR getSemOffsetDetector() {
        return SemOffsetDetector;
    }

    public void setSemOffsetDetector(DETECTOR semOffsetDetector) {
        SemOffsetDetector = semOffsetDetector;
    }

    public TRACE_TYPE getSemTraceType() {
        return SemTraceType;
    }
}
