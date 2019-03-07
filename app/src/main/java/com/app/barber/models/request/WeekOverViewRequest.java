package com.app.barber.models.request;

/**
 * Created by harish on 18/1/19.
 */

public class WeekOverViewRequest {
    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    String StartDate;
    String EndDate;

}
