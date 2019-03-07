package com.app.barber.models;

import java.util.List;

/**
 * Created by harish on 23/10/18.
 */

public class TimeSlotsModel {

//    public boolean isOpened() {
//        return isOpened;
//    }
//
//    public void setOpened(boolean opened) {
//        isOpened = opened;
//    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public String getClosingHours() {
        return closingHours;
    }

    public void setClosingHours(String closingHours) {
        this.closingHours = closingHours;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    //    boolean isOpened;
    String openingHours;
    String closingHours;
    String day;

    public boolean isClosed() {
        return IsClosed;
    }

    public void setClosed(boolean closed) {
        IsClosed = closed;
    }

    boolean IsClosed;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    boolean isSelected;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    String Id;

    public String getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(String currentDay) {
        this.currentDay = currentDay;
    }

    String currentDay;//temp use


    public List<BreakHoursList> getBreakHours() {
        return breakHours;
    }

    public void setBreakHours(List<BreakHoursList> breakHours) {
        this.breakHours = breakHours;
    }

    List<BreakHoursList> breakHours;

    public static class BreakHoursList {
        public String getBreakStartHours() {
            return BreakStartHours;
        }

        public void setBreakStartHours(String breakStartHours) {
            BreakStartHours = breakStartHours;
        }

        public String getBreakEndHours() {
            return BreakEndHours;
        }

        public void setBreakEndHours(String breakEndHours) {
            BreakEndHours = breakEndHours;
        }

        public String getDay() {
            return Day;
        }

        public void setDay(String day) {
            Day = day;
        }

        String BreakStartHours;
        String BreakEndHours;
        String Day;

        public boolean isClosed() {
            return IsClosed;
        }

        public void setClosed(boolean closed) {
            IsClosed = closed;
        }

        boolean IsClosed;

        public int getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }

        int Id;

    }
}
