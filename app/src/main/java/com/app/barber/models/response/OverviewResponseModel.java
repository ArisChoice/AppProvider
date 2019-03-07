package com.app.barber.models.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by harish on 18/1/19.
 */

public class OverviewResponseModel {

    /**
     * Message : Success
     * Status : 201
     * data : {"TimeList":[{"TimeSlot":"08:00 AM-09:00 AM","DayList":[{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0}]},{"TimeSlot":"09:00 AM-10:00 AM","DayList":[{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0}]},{"TimeSlot":"10:00 AM-11:00 AM","DayList":[{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0}]},{"TimeSlot":"11:00 AM-12:00 PM","DayList":[{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0}]},{"TimeSlot":"12:00 PM-01:00 PM","DayList":[{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0}]},{"TimeSlot":"01:00 PM-02:00 PM","DayList":[{"AppointmentCount":0,"CallOutCount":1},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0}]},{"TimeSlot":"02:00 PM-03:00 PM","DayList":[{"AppointmentCount":0,"CallOutCount":2},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0}]},{"TimeSlot":"03:00 PM-04:00 PM","DayList":[{"AppointmentCount":0,"CallOutCount":1},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0}]},{"TimeSlot":"04:00 PM-05:00 PM","DayList":[{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0}]}]}
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
        @SerializedName("TimeList")
        private List<TimeListBean> TimeList;

        public List<TimeListBean> getTimeList() {
            return TimeList;
        }

        public void setTimeList(List<TimeListBean> TimeList) {
            this.TimeList = TimeList;
        }

        public static class TimeListBean {
            /**
             * TimeSlot : 08:00 AM-09:00 AM
             * DayList : [{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0},{"AppointmentCount":0,"CallOutCount":0}]
             */

            @SerializedName("TimeSlot")
            private String TimeSlot;
            @SerializedName("DayList")
            private List<DayListBean> DayList;

            public String getTimeSlot() {
                return TimeSlot;
            }

            public void setTimeSlot(String TimeSlot) {
                this.TimeSlot = TimeSlot;
            }

            public List<DayListBean> getDayList() {
                return DayList;
            }

            public void setDayList(List<DayListBean> DayList) {
                this.DayList = DayList;
            }

            public static class DayListBean {
                /**
                 * AppointmentCount : 0
                 * CallOutCount : 0
                 */

                @SerializedName("AppointmentCount")
                private int AppointmentCount;
                @SerializedName("CallOutCount")
                private int CallOutCount;

                public String getDate() {
                    return Date;
                }

                public void setDate(String date) {
                    Date = date;
                }

                @SerializedName("Date")
                private String Date;

                public int getAppointmentCount() {
                    return AppointmentCount;
                }

                public void setAppointmentCount(int AppointmentCount) {
                    this.AppointmentCount = AppointmentCount;
                }

                public int getCallOutCount() {
                    return CallOutCount;
                }

                public void setCallOutCount(int CallOutCount) {
                    this.CallOutCount = CallOutCount;
                }
            }
        }
    }
}
