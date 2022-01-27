package com.dabinsystems.pact_app.Data.SA;

public class GateBoxOffsetData {

    float mBlueBoxOffset;
    float mRedBoxOffset;

    float mBlueBoxLength = 1857.3f;
    float mRedBoxLength = 571.4f;

    public GateBoxOffsetData(float blue, float red) {

        mBlueBoxOffset = blue;
        mRedBoxOffset = red;

    }

    public float getBlueBoxOffset() {
        return mBlueBoxOffset;
    }

    public void setBlueBoxOffset(float mBlueBoxOffset) {
        this.mBlueBoxOffset = mBlueBoxOffset;
    }

    public float getRedBoxOffset() {
        return mRedBoxOffset;
    }

    public void setRedBoxOffset(float mRedBoxOffset) {
        this.mRedBoxOffset = mRedBoxOffset;
    }

    public float getBlueBoxLength() {
        return mBlueBoxLength;
    }

    public void setBlueBoxLength(float mBlueBoxLength) {
        this.mBlueBoxLength = mBlueBoxLength;
    }

    public float getRedBoxLength() {
        return mRedBoxLength;
    }

    public void setRedBoxLength(float mRedBoxLength) {
        this.mRedBoxLength = mRedBoxLength;
    }
}
