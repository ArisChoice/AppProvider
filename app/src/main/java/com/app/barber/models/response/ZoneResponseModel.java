package com.app.barber.models.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by harish on 21/1/19.
 */

public class ZoneResponseModel {

    /**
     * Message : Success
     * Status : 201
     * List : [{"m_Item1":"1","m_Item2":"E"},{"m_Item1":"2","m_Item2":"EC"},{"m_Item1":"3","m_Item2":"N"},{"m_Item1":"4","m_Item2":"NW"},{"m_Item1":"5","m_Item2":"SE"},{"m_Item1":"6","m_Item2":"SW"},{"m_Item1":"7","m_Item2":"W"},{"m_Item1":"8","m_Item2":"WC"}]
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
         * m_Item1 : 1
         * m_Item2 : E
         */

        @SerializedName("m_Item1")
        private int mItem1;
        @SerializedName("m_Item2")
        private String mItem2;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        boolean isSelected;

        public int getMItem1() {
            return mItem1;
        }

        public void setMItem1(int mItem1) {
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
