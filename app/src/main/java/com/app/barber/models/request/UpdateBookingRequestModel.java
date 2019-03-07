package com.app.barber.models.request;

import com.app.barber.models.response.AppointmentsResponseModel;

/**
 * Created by harish on 2/1/19.
 */

public class UpdateBookingRequestModel {
    int bookingId;
    boolean Status;
    int BookingType;
    int BarberId;

    public UpdateBookingRequestModel(int bookingId, boolean Status, int BookingType, int BarberId) {
        this.bookingId = bookingId;
        this.Status = Status;
        this.BookingType = BookingType;
        this.BarberId = BarberId;

    }
}
