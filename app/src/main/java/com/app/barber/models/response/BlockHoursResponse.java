package com.app.barber.models.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by harish on 28/1/19.
 */

public class BlockHoursResponse {

    /**
     * Message : Success
     * Status : 201
     * BlockHours : [{"Id":1,"StartTime":"12:00 AM","EndTime":"12:15 PM"}]
     */

    @SerializedName("Message")
    private String Message;
    @SerializedName("Status")
    private int Status;
    @SerializedName("BlockHours")
    private List<BlockHoursBean> BlockHours;

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

    public List<BlockHoursBean> getBlockHours() {
        return BlockHours;
    }

    public void setBlockHours(List<BlockHoursBean> BlockHours) {
        this.BlockHours = BlockHours;
    }

    public static class BlockHoursBean {
        /**
         * Id : 1
         * StartTime : 12:00 AM
         * EndTime : 12:15 PM
         */

        @SerializedName("Id")
        private int Id;
        @SerializedName("StartTime")
        private String StartTime;
        @SerializedName("EndTime")
        private String EndTime;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getStartTime() {
            return StartTime;
        }

        public void setStartTime(String StartTime) {
            this.StartTime = StartTime;
        }

        public String getEndTime() {
            return EndTime;
        }

        public void setEndTime(String EndTime) {
            this.EndTime = EndTime;
        }
    }
}
