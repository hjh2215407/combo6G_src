package com.dabinsystems.pact_app.Util;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;

import org.apache.poi.POIXMLException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ExcelEditor {

    private String PATH = Environment.getExternalStorageDirectory() + File.separator + "PACT/SaveData" + File.separator;
    private String FILE_NAME = "data.xlsx";
    private String FILE_PATH = PATH + FILE_NAME;
    private XSSFWorkbook mWorkbook;

    public ExcelEditor() {

    }

    public ExcelEditor(String name, String path) {
        PATH = path;
        FILE_NAME = name;
        FILE_PATH = PATH + FILE_NAME;
    }

    public ExcelEditor(String name) {
        FILE_NAME = name;
        FILE_PATH = PATH + FILE_NAME;
    }

    private Boolean readExcelFile() {

        try {

            if (mWorkbook != null) {
                return true;
            }

            File xls = new File(FILE_PATH);
            if (xls.exists()) {

                FileInputStream is = new FileInputStream(xls);
                mWorkbook = new XSSFWorkbook(is);
                is.close();
                InitActivity.logMsg("ExcelFile", "get excel file - " + FILE_PATH);

            } else {

                AssetManager asset = MainActivity.getActivity().getAssets();
                InputStream is = asset.open("data.xlsx");
                mWorkbook = new XSSFWorkbook(is);
                File pathFile = new File(PATH);
                if (!pathFile.exists()) pathFile.mkdirs();

                InitActivity.logMsg("ExcelFile", "read excel file from asset folder");

            }

            return true;

        } catch (IOException | POIXMLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getData(int rowIdx, int cellIdx) {

        if (!readExcelFile() || mWorkbook.getNumberOfSheets() == 0) return null;

        try {
            Sheet sheet = mWorkbook.getSheetAt(0);
            Row row = sheet.getRow(rowIdx);
            Cell cell = row.getCell(cellIdx);
            String data = cell.getStringCellValue();
//        writeExcelData();
            InitActivity.logMsg("ExcelEditor", "getData : " + data);
            return data;
        } catch (NullPointerException e) {
            return null;
        }

    }

    public void initExcelFile() {

        try {

            File xls = new File(FILE_PATH);
            if (xls.exists()) {

                boolean isDelete = xls.delete();
                if (isDelete) InitActivity.logMsg("initExcelFile", "delete data.xlsx");
                else InitActivity.logMsg("initExcelFile", "error... delete data.xlsx");

            }

            AssetManager asset = MainActivity.getActivity().getAssets();
            InputStream is = asset.open("data.xlsx");
            mWorkbook = new XSSFWorkbook(is);
            File pathFile = new File(PATH);
            if (!pathFile.exists()) pathFile.mkdirs();

            InitActivity.logMsg("ExcelFile", "read excel file from asset folder");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void removeSheet(int idx) {

        try {
            if (!readExcelFile()) return;
            mWorkbook.removeSheetAt(idx);

        } catch (NullPointerException | IllegalArgumentException e) {
            InitActivity.logMsg("clearSheet", "clear sheet error");
        }

    }

    public void addImage(int rowIdx, int cellIdx, Bitmap bitmap) {

        Sheet sheet;

        if (!readExcelFile()) return;
        if (mWorkbook.getNumberOfSheets() < 2) {
            sheet = mWorkbook.createSheet();
            Row row = sheet.getRow(0);
            if(row == null) row = sheet.createRow(0);
            Cell cell1 = row.getCell(1);
            if(row.getCell(1) == null) cell1 = row.createCell(1);
            Cell cell2 = row.getCell(11);
            if(row.getCell(11) == null) cell2 = row.createCell(11);

            cell1.setCellValue("장치1");
            cell2.setCellValue("장치2");
        }
        else sheet = mWorkbook.getSheetAt(1);

//        try {
//
//            Row row = sheet.getRow(rowIdx);
//            Cell cell = row.getCell(cellIdx);
//            row.removeCell(cell);
//        } catch (NullPointerException e) {
//            InitActivity.logMsg("addImage", "cell clear error ... already deleted");
//        }

        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] bitmapData = bos.toByteArray();
            int screenshot = mWorkbook.addPicture(bitmapData, Workbook.PICTURE_TYPE_JPEG);

            CreationHelper helper = mWorkbook.getCreationHelper();
            Drawing drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();

            anchor.setCol1(cellIdx);
            anchor.setRow1(rowIdx);

            anchor.setCol2(cellIdx + 9);
            anchor.setRow2(rowIdx + 17);
            Picture pict = drawing.createPicture(anchor, screenshot);
//        pict.resize();
            bos.close();
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }

    }

    public void addExcelData(int rowIdx, int cellIdx, String data) {

        if (!readExcelFile()) return;
        if (mWorkbook.getSheetAt(0) == null) mWorkbook.createSheet();

        Sheet sheet;
        Row row;
        Cell cell;
        CellStyle style = mWorkbook.createCellStyle();
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        Font font = mWorkbook.createFont();
        font.setColor(IndexedColors.ROYAL_BLUE.index);
        style.setFont(font);

        try {
            sheet = mWorkbook.getSheetAt(0);
        } catch (NullPointerException e) {
            sheet = mWorkbook.getSheetAt(0);
        }

        try {
            row = sheet.getRow(rowIdx);
        } catch (NullPointerException e) {
            row = sheet.createRow(rowIdx);
        }

        try {

            cell = row.getCell(cellIdx);
            cell.setCellStyle(style);
            cell.setCellValue(data);

        } catch (NullPointerException e) {

            cell = row.createCell(cellIdx);
            cell.setCellStyle(style);
            cell.setCellValue(data);

        }

    }

    public void writeExcelData() {
        writeExcelData(FILE_PATH);
    }

    private void writeExcelData(String path) {

        if (mWorkbook == null) {
            InitActivity.logMsg("writeExcelData", "Workbook is null");
            return;
        }

        new Thread(() -> {

            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(new File(path));
                mWorkbook.write(outputStream);
                mWorkbook.close();
                mWorkbook = null;
                outputStream.close();

                MainActivity.getActivity().runOnUiThread(() -> {
                    Toast.makeText(MainActivity.getContext(), "saved data...", Toast.LENGTH_SHORT).show();
                });

            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
    }

}
