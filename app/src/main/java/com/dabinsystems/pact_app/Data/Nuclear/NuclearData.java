package com.dabinsystems.pact_app.Data.Nuclear;

public class NuclearData {

    public enum CONNECTION {
        LOCAL(0, "LOCAL"),
        REMOTE(1, "REMOTE");

        int val;
        String str;

        CONNECTION(int connection, String name) {
            val = connection;
            str = name;
        }

        public int getVal() { return val; }
        public String getName() { return str; }
    }

    CONNECTION connection = CONNECTION.LOCAL;

    public CONNECTION getConnection() { return connection; }

    public void setConnection(CONNECTION connection) { this.connection = connection; }

    private int MAX_RF_NUM = 4;

    private int selectedRf = 0;

    public int getSelectedRf() { return selectedRf; }

    public void setSelectedRf(int rf) { selectedRf = rf; }

}
