package com.dabinsystems.pact_app.List.Item;

import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FileListInfoItem {

    private String FileName;
    private String FileSize;
    private String FileType;
    private String Modified;

    private File mFile;

//    public FileListInfoItem(String fileName, String fileType, String fileSize, String modified) {
//        super();
//
//        FileName = fileName;
//        FileSize = fileSize;
//        FileType = fileType;
//        Modified = modified;
//
//    }

    public FileListInfoItem(File file) {

        mFile = file;
        FileName = file.getName();
        FileType = "";
        if(FileName.contains(".") && FileName.lastIndexOf(".")!= 0)
            FileType = FileName.substring(FileName.lastIndexOf(".") + 1).toUpperCase();
        FileSize = file.length() + "";
//                String size = (int)((float)file.length() / (float)Math.pow(2, 10)) + "";
        SimpleDateFormat modifiedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Modified = modifiedDate.format(file.lastModified());

    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getFileSize() {
        return FileSize;
    }

    public void setFileSize(float fileSize) {

        FileSize = fileSize + "";
    }

    public void setFileSize(String fileSize) {
        FileSize = fileSize;
    }

    public String getFileType() {
        return FileType;
    }

    public void setFileType(String fileType) {
        FileType = fileType;
    }

    public String getModified() {
        return Modified;
    }

    public void setModified(String modified) {
        Modified = modified;
    }

    public File getFile() {
        return mFile;
    }
}
