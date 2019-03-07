package com.app.barber.models.response;

import com.google.gson.annotations.SerializedName;

public class SettingStatusResponse {

    /**
     * Message : Success
     * Status : 201
     * data : {"IsPromotionNotification":false,"IsAppointmentNotification":false,"IsCancellationChargeNotification":false}
     */

    @SerializedName("Message")
    private String Message;
    @SerializedName("Status")
    private int Status;
    @SerializedName("data")
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * IsPromotionNotification : false
         * IsAppointmentNotification : false
         * IsCancellationChargeNotification : false
         */

        @SerializedName("IsPromotionNotification")
        private boolean IsPromotionNotification;
        @SerializedName("IsAppointmentNotification")
        private boolean IsAppointmentNotification;
        @SerializedName("IsCancellationChargeNotification")
        private boolean IsCancellationChargeNotification;

        public boolean isIsPromotionNotification() {
            return IsPromotionNotification;
        }

        public void setIsPromotionNotification(boolean IsPromotionNotification) {
            this.IsPromotionNotification = IsPromotionNotification;
        }

        public boolean isIsAppointmentNotification() {
            return IsAppointmentNotification;
        }

        public void setIsAppointmentNotification(boolean IsAppointmentNotification) {
            this.IsAppointmentNotification = IsAppointmentNotification;
        }

        public boolean isIsCancellationChargeNotification() {
            return IsCancellationChargeNotification;
        }

        public void setIsCancellationChargeNotification(boolean IsCancellationChargeNotification) {
            this.IsCancellationChargeNotification = IsCancellationChargeNotification;
        }
    }
}
