package com.app.barber.models.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by harish on 21/1/19.
 */

public class ZoneDistrictResponseModel {

    /**
     * Message : Success
     * Status : 201
     * List : [{"m_Item1":"7","m_Item2":"NW1 Head district"},{"m_Item1":"8","m_Item2":"NW2 Cricklewood"},{"m_Item1":"9","m_Item2":"NW3 Hampstead"},{"m_Item1":"10","m_Item2":"NW4 Hendon"},{"m_Item1":"11","m_Item2":"NW5 Kentish Town"},{"m_Item1":"12","m_Item2":"NW6 Kilburn"},{"m_Item1":"13","m_Item2":"NW6 Kilburn"},{"m_Item1":"14","m_Item2":"NW7 Mill Hill"},{"m_Item1":"15","m_Item2":"NW8 St John's Wood"},{"m_Item1":"16","m_Item2":"NW9 The Hyde"},{"m_Item1":"17","m_Item2":"NW10 Willesden"},{"m_Item1":"18","m_Item2":"NW11 Golders Green"}]
     */

    @SerializedName("Message")
    private String Message;
    @SerializedName("Status")
    private int Status;
    @SerializedName("List")
    private java.util.List<ListBean> List;

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

    public List<ListBean> getList() {
        return List;
    }

    public void setList(List<ListBean> List) {
        this.List = List;
    }

    public static class ListBean {
        /**
         * m_Item1 : 7
         * m_Item2 : NW1 Head district
         */

        @SerializedName("m_Item1")
        private String mItem1;
        @SerializedName("m_Item2")
        private String mItem2;

        public boolean isM_Item3() {
            return m_Item3;
        }

        public void setM_Item3(boolean m_Item3) {
            this.m_Item3 = m_Item3;
        }

        @SerializedName("m_Item3")
        private boolean m_Item3;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        boolean isSelected;

        public String getMItem1() {
            return mItem1;
        }

        public void setMItem1(String mItem1) {
            this.mItem1 = mItem1;
        }

        public String getMItem2() {
            return mItem2;
        }

        public void setMItem2(String mItem2) {
            this.mItem2 = mItem2;
        }
    }
}
