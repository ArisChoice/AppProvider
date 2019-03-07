package com.app.barber.models.request;

import com.app.barber.models.TimeSlotsModel;

import java.util.List;

/**
 * Created by harish on 1/11/18.
 */

public class HoursModel {

    public HoursModel.WorkingHours getWorkingHours() {
        return WorkingHours;
    }

    public void setWorkingHours(HoursModel.WorkingHours workingHours) {
        WorkingHours = workingHours;
    }

    WorkingHours WorkingHours;

    public class WorkingHours {
        int Id;

        public boolean isClosed() {
            return IsClosed;
        }

        public void setClosed(boolean closed) {
            IsClosed = closed;
        }

        boolean IsClosed;
        String Day;
        String OpeningHours;
        String ClosingHours;
        List<TimeSlotsModel.BreakHoursList> BreakHoursList;

        public List<TimeSlotsModel.BreakHoursList> getBreakHours() {
            return BreakHoursList;
        }

        public void setBreakHours(List<TimeSlotsModel.BreakHoursList> breakHours) {
            this.BreakHoursList = breakHours;
        }

        public int getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }


        public String getDay() {
            return Day;
        }

        public void setDay(String day) {
            Day = day;
        }

        public String getOpeningHours() {
            return OpeningHours;
        }

        public void setOpeningHours(String openingHours) {
            OpeningHours = openingHours;
        }

        public String getClosingHours() {
            return ClosingHours;
        }

        public void setClosingHours(String closingHours) {
            ClosingHours = closingHours;
        }

    }
}

