package com.dabinsystems.pact_app.Data;

public class FWUpdateData {

    private String Description;
    private String FwName = "pact-linux-app.elf";

    private String FpgaVswrName = "vswr.bit.bin";
    private String FpgaSaName = "sa.bit.bin";

    private String LteFileName = "lte5g.bin";
    private String UpdateShellFileName = "update.sh";

    private String FwVersion;
    private String FpgaVswrVersion;
    private String FpgaSaVersion;
    private String Md5ElfName;
    private String Md5VswrName;
    private String Md5SaName;
    private String Md5LteName = "lte5g.md5";
    private String Path;

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getFwName() {
        return FwName;
    }

    public void setFwName(String fwName) {
        FwName = fwName;
    }

    public String getFpgaVswrName() {
        return FpgaVswrName;
    }

    public void setFpgaVswrName(String fpgaVswrName) {
        FpgaVswrName = fpgaVswrName;
    }

    public String getFpgaSaName() {
        return FpgaSaName;
    }

    public void setFpgaSaName(String fpgaSaName) {
        FpgaSaName = fpgaSaName;
    }

    public String getFwVersion() {
        return FwVersion;
    }

    public void setFwVersion(String fwVersion) {
        FwVersion = fwVersion;
    }

    public String getFpgaVswrVersion() {
        return FpgaVswrVersion;
    }

    public void setFpgaVswrVersion(String fpgaVswrVersion) {
        FpgaVswrVersion = fpgaVswrVersion;
    }

    public String getFpgaSaVersion() {
        return FpgaSaVersion;
    }

    public void setFpgaSaVersion(String fpgaSaVersion) {
        FpgaSaVersion = fpgaSaVersion;
    }

    public String getMd5ElfName() {
        return Md5ElfName;
    }

    public void setMd5ElfName(String md5ElfName) {
        Md5ElfName = md5ElfName;
    }

    public String getMd5VswrName() {
        return Md5VswrName;
    }

    public void setMd5VswrName(String md5VswrName) {
        Md5VswrName = md5VswrName;
    }

    public String getMd5SaName() {
        return Md5SaName;
    }

    public void setMd5SaName(String md5SaName) {
        Md5SaName = md5SaName;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public String getLteFileName() {
        return LteFileName;
    }

    public void setLteFileName(String lteFileName) {
        LteFileName = lteFileName;
    }

    public String getMd5LteName() {
        return Md5LteName;
    }

    public void setMd5LteName(String md5LteName) {
        Md5LteName = md5LteName;
    }

    public String getUpdateShellFileName() {
        return UpdateShellFileName;
    }

    public void setUpdateShellFileName(String updateShellFileName) {
        UpdateShellFileName = updateShellFileName;
    }
}
