package com.app.barber.models.request;

public class SettingChageRequest {
    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public boolean isChecked() {
        return IsChecked;
    }

    public void setChecked(boolean checked) {
        IsChecked = checked;
    }

    String Type;
    boolean IsChecked;

}
