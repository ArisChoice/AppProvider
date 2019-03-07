package com.app.barber.util.weekengine;

/**
 * Created by harish on 10/12/18.
 */

public class ModelDay {
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    String day;

    public String getFullDate() {
        return fullDate;
    }

    public void setFullDate(String fullDate) {
        this.fullDate = fullDate;
    }

    String fullDate;
    int date;
    boolean isSelected;

    public boolean isApointmentAvailable() {
        return isApointmentAvailable;
    }

    public void setApointmentAvailable(boolean apointmentAvailable) {
        isApointmentAvailable = apointmentAvailable;
    }

    boolean isApointmentAvailable;//used for future appointments.

    public boolean isBlockedHours() {
        return isBlockedHours;
    }

    public void setBlockedHours(boolean blockedHours) {
        isBlockedHours = blockedHours;
    }

    boolean isBlockedHours;//used for future blocked  hours.
}
