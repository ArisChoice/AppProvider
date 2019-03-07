package com.app.barber.models.request;

/**
 * Created by harish on 1/2/19.
 */

public class GetFutureStatusRequest {
    public GetFutureStatusRequest(String date, String endDate) {
        Date = date;
        EndDate = endDate;
    }

    String Date;
    String EndDate;

}
