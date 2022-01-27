package com.dabinsystems.pact_app.Data;

public enum EnumOnOff {
    OFF(0, "Off"),
    ON(1, "On");

    private final int Value;
    private final String Name;

    EnumOnOff(int val, String name) {
        Value = val;
        Name = name;
    }

    public int getValue() {
        return Value;
    }

    public String getModeString() {
        return Name;
    }

    public byte getByte() {
        int val = Value & 0xff;
        return (byte) val;
    }
}
