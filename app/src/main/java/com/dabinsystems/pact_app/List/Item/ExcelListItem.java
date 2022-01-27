package com.dabinsystems.pact_app.List.Item;

import java.io.File;

public class ExcelListItem {

    private String Name;
    private String Title;
    private File mFile;

    public ExcelListItem(String name, String title) {
        Title = title;
        Name = name;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
