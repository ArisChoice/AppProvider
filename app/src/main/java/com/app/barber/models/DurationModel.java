package com.app.barber.models;

/**
 * Created by harish on 3/12/18.
 */

public class DurationModel {
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    String time;
    boolean isSelected;
}
