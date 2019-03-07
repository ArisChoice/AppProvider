package com.app.barber.models.response;

import com.google.gson.annotations.SerializedName;

public class ValidationResponseModel {

    /**
     * Message : Success
     * Status : 201
     * data : {"Phone":"7814817171","UserType":1,"OTP":"310554"}
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
         * Phone : 7814817171
         * UserType : 1
         * OTP : 310554
         */

        @SerializedName("Phone")
        private String Phone;
        @SerializedName("UserType")
        private int UserType;
        @SerializedName("OTP")
        private String OTP;

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String Phone) {
            this.Phone = Phone;
        }

        public int getUserType() {
            return UserType;
        }

        public void setUserType(int UserType) {
            this.UserType = UserType;
        }

        public String getOTP() {
            return OTP;
        }

        public void setOTP(String OTP) {
            this.OTP = OTP;
        }
    }
}
