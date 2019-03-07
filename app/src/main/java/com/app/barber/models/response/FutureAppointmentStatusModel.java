package com.app.barber.models.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by harish on 4/2/19.
 */

public class FutureAppointmentStatusModel {

    /**
     * Message : Success
     * Status : 201
     * Reponse : [{"Date":"02/14/2019","Status":true}]
     */

    @SerializedName("Message")
    private String Message;
    @SerializedName("Status")
    private int Status;
    @SerializedName("Reponse")
    private List<ReponseBean> Reponse;

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

    public List<ReponseBean> getReponse() {
        return Reponse;
    }

    public void setReponse(List<ReponseBean> Reponse) {
        this.Reponse = Reponse;
    }

    public static class ReponseBean {
        /**
         * Date : 02/14/2019
         * Status : true
         */

        @SerializedName("Date")
        private String Date;
        @SerializedName("Status")
        private boolean Status;

        public String getDate() {
            return Date;
        }

        public void setDate(String Date) {
            this.Date = Date;
        }

        public boolean isStatus() {
            return Status;
        }

        public void setStatus(boolean Status) {
            this.Status = Status;
        }
    }
}
