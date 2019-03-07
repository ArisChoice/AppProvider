package com.app.barber.models.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by harish on 5/2/19.
 */

public class GraphResponsewModel {

    /**
     * Message : Success
     * Status : 201
     * data : {"TotalEarninng":"0","DueAmount":null,"List":[{"XAxis":"12:00 PM - 02:00 PM","YAxis":"0"},{"XAxis":"02:00 PM - 04:00 PM","YAxis":"0"},{"XAxis":"04:00 PM - 06:00 PM","YAxis":"0"},{"XAxis":"06:00 PM - 08:00 PM","YAxis":"0"},{"XAxis":"08:00 PM - 10:00 PM","YAxis":"0"}]}
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
         * TotalEarninng : 0
         * DueAmount : null
         * List : [{"XAxis":"12:00 PM - 02:00 PM","YAxis":"0"},{"XAxis":"02:00 PM - 04:00 PM","YAxis":"0"},{"XAxis":"04:00 PM - 06:00 PM","YAxis":"0"},{"XAxis":"06:00 PM - 08:00 PM","YAxis":"0"},{"XAxis":"08:00 PM - 10:00 PM","YAxis":"0"}]
         */

        @SerializedName("TotalEarninng")
        private String TotalEarninng;
        @SerializedName("DueAmount")
        private Object DueAmount;

        public boolean isCanUseReferralCode() {
            return CanUseReferralCode;
        }

        public void setCanUseReferralCode(boolean canUseReferralCode) {
            CanUseReferralCode = canUseReferralCode;
        }

        @SerializedName("CanUseReferralCode")
        private boolean CanUseReferralCode;

        @SerializedName("List")
        private java.util.List<ListBean> List;

        public String getTotalEarninng() {
            return TotalEarninng;
        }

        public void setTotalEarninng(String TotalEarninng) {
            this.TotalEarninng = TotalEarninng;
        }

        public Object getDueAmount() {
            return DueAmount;
        }

        public void setDueAmount(Object DueAmount) {
            this.DueAmount = DueAmount;
        }

        public List<ListBean> getList() {
            return List;
        }

        public void setList(List<ListBean> List) {
            this.List = List;
        }

        public static class ListBean {
            /**
             * XAxis : 12:00 PM - 02:00 PM
             * YAxis : 0
             */

            @SerializedName("XAxis")
            private String XAxis;
            @SerializedName("YAxis")
            private Float YAxis;

            public String getXAxis() {
                return XAxis;
            }

            public void setXAxis(String XAxis) {
                this.XAxis = XAxis;
            }

            public Float getYAxis() {
                return YAxis;
            }

            public void setYAxis(Float YAxis) {
                this.YAxis = YAxis;
            }
        }
    }
}
