package com.dabinsystems.pact_app.Util;

import com.dabinsystems.pact_app.Data.FWUpdateData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import static com.dabinsystems.pact_app.Activity.InitActivity.getActivity;

public class UpdateFileParser {

    FWUpdateData data = new FWUpdateData();

    private String getJsonString(String assetPath) {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open(assetPath);
            int fileSize = is.available();
            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
            return json;

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public FWUpdateData parsing() {

        try {

            JSONObject jsonObject = new JSONObject(getJsonString("fw_update_folder/update.json"));
            JSONArray jsonArrForFW = jsonObject.getJSONArray("firmware");
            String fwVer = jsonArrForFW.getJSONObject(0).getString(
                    data.getFwName()
            );


            String vswrVer = jsonArrForFW.getJSONObject(1).getString(
                    data.getFpgaVswrName()
            );

            String saVer = jsonArrForFW.getJSONObject(2).getString(
                    data.getFpgaSaName()
            );

            String lteMd5Ver = jsonArrForFW.getJSONObject(3).getString(
                    data.getLteFileName()
            );



            JSONArray jsonArrForMd5 = jsonObject.getJSONArray("md5");

            String elfMd5FileName = jsonArrForMd5.getString(0);
            String vswrMd5FileName = jsonArrForMd5.getString(1);
            String saMd5FileName = jsonArrForMd5.getString(2);
            String lteMd5FileName = jsonArrForMd5.getString(3);

            String path = jsonObject.getString("path");

            data.setDescription(jsonObject.getString("description"));
            data.setFwVersion(fwVer);
            data.setFpgaVswrVersion(vswrVer);
            data.setFpgaSaVersion(saVer);
            data.setMd5ElfName(elfMd5FileName);
            data.setMd5VswrName(vswrMd5FileName);
            data.setMd5SaName(saMd5FileName);
            data.setMd5LteName(lteMd5FileName);
            data.setPath(path);

            return data;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

}
