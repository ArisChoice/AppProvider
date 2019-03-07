package com.app.barber.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by harish on 2/1/19.
 */

public class RecentStatusResponseModel {

    /**
     * Message : Success
     * Status : 201
     * Response : {"IsNotificationPending":false,"BookingList":[]}
     */

    @SerializedName("Message")
    private String Message;
    @SerializedName("Status")
    private int Status;
    @SerializedName("Response")
    private ResponseBean Response;

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

    public ResponseBean getResponse() {
        return Response;
    }

    public void setResponse(ResponseBean Response) {
        this.Response = Response;
    }

    public static class ResponseBean {
        /**
         * IsNotificationPending : false
         * BookingList : []
         */

        @SerializedName("IsNotificationPending")
        private boolean IsNotificationPending;

        public boolean isBlockNotification() {
            return BlockNotification;
        }

        public void setBlockNotification(boolean blockNotification) {
            BlockNotification = blockNotification;
        }

        @SerializedName("BlockNotification")
        private boolean BlockNotification;
        @SerializedName("BookingList")
        private List<?> BookingList;


        public List<AppointmentsResponseModel.ResponseBean.BookingListBean> getBarberEditList() {
            return barberEditList;
        }

        public void setBarberEditList(List<AppointmentsResponseModel.ResponseBean.BookingListBean> barberEditList) {
            this.barberEditList = barberEditList;
        }

        @SerializedName("BarberEditList")
        @Expose
        private List<AppointmentsResponseModel.ResponseBean.BookingListBean> barberEditList = null;


        public Validations getValidations() {
            return Validations;
        }

        public void setValidations(Validations validations) {
            this.Validations = validations;
        }

        private Validations Validations;

        public boolean isIsNotificationPending() {
            return IsNotificationPending;
        }

        public void setIsNotificationPending(boolean IsNotificationPending) {
            this.IsNotificationPending = IsNotificationPending;
        }

        public List<?> getBookingList() {
            return BookingList;
        }

        public void setBookingList(List<?> BookingList) {
            this.BookingList = BookingList;
        }
    }

    public class Validations {
        public boolean isAddressAdded() {
            return IsAddressAdded;
        }

        public void setAddressAdded(boolean addressAdded) {
            IsAddressAdded = addressAdded;
        }

        public boolean isCallOutAdded() {
            return IsCallOutAdded;
        }

        public void setCallOutAdded(boolean callOutAdded) {
            IsCallOutAdded = callOutAdded;
        }

        public boolean isOpeningHoursAdded() {
            return IsOpeningHoursAdded;
        }

        public void setOpeningHoursAdded(boolean openingHoursAdded) {
            IsOpeningHoursAdded = openingHoursAdded;
        }

        public boolean isDistrictAdded() {
            return IsDistrictAdded;
        }

        public void setDistrictAdded(boolean districtAdded) {
            IsDistrictAdded = districtAdded;
        }

        public boolean isBarberTypeAdded() {
            return IsBarberTypeAdded;
        }

        public void setBarberTypeAdded(boolean barberTypeAdded) {
            IsBarberTypeAdded = barberTypeAdded;
        }

        public boolean isPaymentTypeAdded() {
            return IsPaymentTypeAdded;
        }

        public void setPaymentTypeAdded(boolean paymentTypeAdded) {
            IsPaymentTypeAdded = paymentTypeAdded;
        }

        public boolean isStripeConnected() {
            return IsStripeConnected;
        }

        public void setStripeConnected(boolean stripeConnected) {
            IsStripeConnected = stripeConnected;
        }

        public boolean isServiceAdded() {
            return IsServiceAdded;
        }

        public void setServiceAdded(boolean serviceAdded) {
            IsServiceAdded = serviceAdded;
        }

        public boolean isZeroAmountServiceForNonTrainee() {
            return IsZeroAmountServiceValidation;
        }

        public void setZeroAmountServiceForNonTrainee(boolean zeroAmountServiceForNonTrainee) {
            IsZeroAmountServiceValidation = zeroAmountServiceForNonTrainee;
        }

        boolean IsAddressAdded;
        boolean IsCallOutAdded;
        boolean IsOpeningHoursAdded;
        boolean IsDistrictAdded;
        boolean IsBarberTypeAdded;
        boolean IsPaymentTypeAdded;
        boolean IsStripeConnected;
        boolean IsServiceAdded;
        boolean IsZeroAmountServiceValidation;


    }
}
