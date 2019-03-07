package com.app.barber.models.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by harish on 23/1/19.
 */

public class BarberStatsResponse {

    /**
     * Message : Success
     * Status : 201
     * data : {"TotalAppointmentsCount":"5","AppointmentsCount":"5","CallOutCount":"0","CancelledCount":"0","PageViews":"41","AvgRating":"4.29","AvgRatingCount":"3","AvgPuchRating":"4.00","AvgPuchRatingCount":null,"AvgValueRating":"4.67","AvgHygieneRating":"4.33","AvgExpertiseRating":"4.17","FiveStarRatingCount":"2","FourStarRatingCount":"1","ThreeStarRatingCount":"0","TwoStarRatingCount":"0","OneStarRatingCount":"0","ReviewCount":"3"}
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
         * TotalAppointmentsCount : 5
         * AppointmentsCount : 5
         * CallOutCount : 0
         * CancelledCount : 0
         * PageViews : 41
         * AvgRating : 4.29
         * AvgRatingCount : 3
         * AvgPuchRating : 4.00
         * AvgPuchRatingCount : null
         * AvgValueRating : 4.67
         * AvgHygieneRating : 4.33
         * AvgExpertiseRating : 4.17
         * FiveStarRatingCount : 2
         * FourStarRatingCount : 1
         * ThreeStarRatingCount : 0
         * TwoStarRatingCount : 0
         * OneStarRatingCount : 0
         * ReviewCount : 3
         */

        @SerializedName("TotalAppointmentsCount")
        private String TotalAppointmentsCount;
        @SerializedName("AppointmentsCount")
        private String AppointmentsCount;
        @SerializedName("CallOutCount")
        private String CallOutCount;
        @SerializedName("CancelledCount")
        private String CancelledCount;
        @SerializedName("PageViews")
        private String PageViews;
        @SerializedName("AvgRating")
        private String AvgRating;
        @SerializedName("AvgRatingCount")
        private String AvgRatingCount;
        @SerializedName("AvgPuchRating")
        private String AvgPuchRating;
        @SerializedName("AvgPuchRatingCount")
        private Object AvgPuchRatingCount;
        @SerializedName("AvgValueRating")
        private String AvgValueRating;
        @SerializedName("AvgHygieneRating")
        private String AvgHygieneRating;
        @SerializedName("AvgExpertiseRating")
        private String AvgExpertiseRating;
        @SerializedName("FiveStarRatingCount")
        private int FiveStarRatingCount;
        @SerializedName("FourStarRatingCount")
        private int FourStarRatingCount;
        @SerializedName("ThreeStarRatingCount")
        private int ThreeStarRatingCount;
        @SerializedName("TwoStarRatingCount")
        private int TwoStarRatingCount;
        @SerializedName("OneStarRatingCount")
        private int OneStarRatingCount;
        @SerializedName("ReviewCount")
        private int ReviewCount;

        public String getTotalAppointmentsCount() {
            return TotalAppointmentsCount;
        }

        public void setTotalAppointmentsCount(String TotalAppointmentsCount) {
            this.TotalAppointmentsCount = TotalAppointmentsCount;
        }

        public String getAppointmentsCount() {
            return AppointmentsCount;
        }

        public void setAppointmentsCount(String AppointmentsCount) {
            this.AppointmentsCount = AppointmentsCount;
        }

        public String getCallOutCount() {
            return CallOutCount;
        }

        public void setCallOutCount(String CallOutCount) {
            this.CallOutCount = CallOutCount;
        }

        public String getCancelledCount() {
            return CancelledCount;
        }

        public void setCancelledCount(String CancelledCount) {
            this.CancelledCount = CancelledCount;
        }

        public String getPageViews() {
            return PageViews;
        }

        public void setPageViews(String PageViews) {
            this.PageViews = PageViews;
        }

        public String getAvgRating() {
            return AvgRating;
        }

        public void setAvgRating(String AvgRating) {
            this.AvgRating = AvgRating;
        }

        public String getAvgRatingCount() {
            return AvgRatingCount;
        }

        public void setAvgRatingCount(String AvgRatingCount) {
            this.AvgRatingCount = AvgRatingCount;
        }

        public String getAvgPuchRating() {
            return AvgPuchRating;
        }

        public void setAvgPuchRating(String AvgPuchRating) {
            this.AvgPuchRating = AvgPuchRating;
        }

        public Object getAvgPuchRatingCount() {
            return AvgPuchRatingCount;
        }

        public void setAvgPuchRatingCount(Object AvgPuchRatingCount) {
            this.AvgPuchRatingCount = AvgPuchRatingCount;
        }

        public String getAvgValueRating() {
            return AvgValueRating;
        }

        public void setAvgValueRating(String AvgValueRating) {
            this.AvgValueRating = AvgValueRating;
        }

        public String getAvgHygieneRating() {
            return AvgHygieneRating;
        }

        public void setAvgHygieneRating(String AvgHygieneRating) {
            this.AvgHygieneRating = AvgHygieneRating;
        }

        public String getAvgExpertiseRating() {
            return AvgExpertiseRating;
        }

        public void setAvgExpertiseRating(String AvgExpertiseRating) {
            this.AvgExpertiseRating = AvgExpertiseRating;
        }

        public int getFiveStarRatingCount() {
            return FiveStarRatingCount;
        }

        public void setFiveStarRatingCount(int FiveStarRatingCount) {
            this.FiveStarRatingCount = FiveStarRatingCount;
        }

        public int getFourStarRatingCount() {
            return FourStarRatingCount;
        }

        public void setFourStarRatingCount(int FourStarRatingCount) {
            this.FourStarRatingCount = FourStarRatingCount;
        }

        public int getThreeStarRatingCount() {
            return ThreeStarRatingCount;
        }

        public void setThreeStarRatingCount(int ThreeStarRatingCount) {
            this.ThreeStarRatingCount = ThreeStarRatingCount;
        }

        public int getTwoStarRatingCount() {
            return TwoStarRatingCount;
        }

        public void setTwoStarRatingCount(int TwoStarRatingCount) {
            this.TwoStarRatingCount = TwoStarRatingCount;
        }

        public int getOneStarRatingCount() {
            return OneStarRatingCount;
        }

        public void setOneStarRatingCount(int OneStarRatingCount) {
            this.OneStarRatingCount = OneStarRatingCount;
        }

        public int getReviewCount() {
            return ReviewCount;
        }

        public void setReviewCount(int ReviewCount) {
            this.ReviewCount = ReviewCount;
        }
    }
}
