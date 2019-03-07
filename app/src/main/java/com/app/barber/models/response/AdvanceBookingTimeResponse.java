package com.app.barber.models.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by harish on 22/1/19.
 */

public class AdvanceBookingTimeResponse {

    /**
     * Message : Success
     * Status : 201
     * Time : 120
     */

    @SerializedName("Message")
    private String Message;
    @SerializedName("Status")
    private int Status;
    @SerializedName("Time")
    private int Time;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public int getTime() {
        return Time;
    }

    public void setTime(int Time) {
        this.Time = Time;
    }
}
