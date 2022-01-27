package com.dabinsystems.pact_app.Data.SA.MeasSetupData.InterferenceHunting;

public class SpurTableData {

    private String Time;
    private InterferenceMeasSetupData.SpurType Type;
    private Double Power;

    public SpurTableData(String timeStamp, InterferenceMeasSetupData.SpurType type, Double power) {
        Time = timeStamp;
        Type = type;
        Power = power;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public InterferenceMeasSetupData.SpurType getType() {
        return Type;
    }

    public void setType(InterferenceMeasSetupData.SpurType type) {
        Type = type;
    }

    public Double getPower() {
        return Power;
    }

    public void setPower(Double power) {
        Power = power;
    }
}
