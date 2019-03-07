package com.app.barber.models.request;

/**
 * Created by harish on 9/1/19.
 */

public class AddAppointmentRequest {
    int CustomerId;
    String TimingSlot;
    String Note;

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public String getTimingSlot() {
        return TimingSlot;
    }

    public void setTimingSlot(String timingSlot) {
        TimingSlot = timingSlot;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getServiceId() {
        return ServiceId;
    }

    public void setServiceId(String serviceId) {
        ServiceId = serviceId;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    String Message;
    String ServiceId;
    String Date;

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    String Amount;

}
