package com.dabinsystems.pact_app.Data;

public class StatusData {

    private boolean isSaOvf = false;
    private boolean isModOvf = false;
    private boolean isSync = false;

    public boolean isSaOvf() {
        return isSaOvf;
    }

    public void setSaOvf(boolean saOvf) {
        isSaOvf = saOvf;
    }

    public boolean isSync() {
        return isSync;
    }

    public void setSync(boolean sync) {
        isSync = sync;
    }

    public boolean isModOvf() {
        return isModOvf;
    }

    public void setModOvf(boolean modOvf) {
        isModOvf = modOvf;
    }
}
