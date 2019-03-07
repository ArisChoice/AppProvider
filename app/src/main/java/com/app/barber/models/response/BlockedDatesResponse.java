package com.app.barber.models.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BlockedDatesResponse {

    /**
     * Message : Success
     * Status : 201
     * List : [{"Date":"03/01/2019","IsBlockHourExist":true},{"Date":"03/02/2019","IsBlockHourExist":true},{"Date":"03/03/2019","IsBlockHourExist":false},{"Date":"03/04/2019","IsBlockHourExist":false},{"Date":"03/05/2019","IsBlockHourExist":true},{"Date":"03/06/2019","IsBlockHourExist":false},{"Date":"03/07/2019","IsBlockHourExist":false},{"Date":"03/08/2019","IsBlockHourExist":false},{"Date":"03/09/2019","IsBlockHourExist":false},{"Date":"03/10/2019","IsBlockHourExist":false},{"Date":"03/11/2019","IsBlockHourExist":false},{"Date":"03/12/2019","IsBlockHourExist":false},{"Date":"03/13/2019","IsBlockHourExist":false},{"Date":"03/14/2019","IsBlockHourExist":false},{"Date":"03/15/2019","IsBlockHourExist":false},{"Date":"03/16/2019","IsBlockHourExist":false},{"Date":"03/17/2019","IsBlockHourExist":false},{"Date":"03/18/2019","IsBlockHourExist":false},{"Date":"03/19/2019","IsBlockHourExist":false},{"Date":"03/20/2019","IsBlockHourExist":false},{"Date":"03/21/2019","IsBlockHourExist":false},{"Date":"03/22/2019","IsBlockHourExist":false},{"Date":"03/23/2019","IsBlockHourExist":false},{"Date":"03/24/2019","IsBlockHourExist":false},{"Date":"03/25/2019","IsBlockHourExist":false},{"Date":"03/26/2019","IsBlockHourExist":false},{"Date":"03/27/2019","IsBlockHourExist":false},{"Date":"03/28/2019","IsBlockHourExist":false},{"Date":"03/29/2019","IsBlockHourExist":false},{"Date":"03/30/2019","IsBlockHourExist":false},{"Date":"03/31/2019","IsBlockHourExist":false},{"Date":"04/01/2019","IsBlockHourExist":false},{"Date":"04/02/2019","IsBlockHourExist":false},{"Date":"04/03/2019","IsBlockHourExist":false},{"Date":"04/04/2019","IsBlockHourExist":false}]
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
         * Date : 03/01/2019
         * IsBlockHourExist : true
         */

        @SerializedName("Date")
        private String Date;
        @SerializedName("IsBlockHourExist")
        private boolean IsBlockHourExist;

        public String getDate() {
            return Date;
        }

        public void setDate(String Date) {
            this.Date = Date;
        }

        public boolean isIsBlockHourExist() {
            return IsBlockHourExist;
        }

        public void setIsBlockHourExist(boolean IsBlockHourExist) {
            this.IsBlockHourExist = IsBlockHourExist;
        }
    }
}
