package com.app.barber.models.request;

import java.util.List;

/**
 * Created by harish on 28/1/19.
 */

public class AddBlockHoursRequest {
    String Date;
    List<BlockHourListData> BlockHourList;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public List<BlockHourListData> getList() {
        return BlockHourList;
    }

    public void setList(List<BlockHourListData> list) {
        this.BlockHourList = list;
    }

    public class BlockHourListData {
        int Id;
        String StartTime;

        public int getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }

        public String getStartTime() {
            return StartTime;
        }

        public void setStartTime(String startTime) {
            StartTime = startTime;
        }

        public String getEndTime() {
            return EndTime;
        }

        public void setEndTime(String endTime) {
            EndTime = endTime;
        }

        String EndTime;

    }

}
