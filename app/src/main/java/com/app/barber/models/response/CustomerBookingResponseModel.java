package com.app.barber.models.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerBookingResponseModel {

    /**
     * Message : List
     * Status : 201
     * data : {"Id":36,"Name":"Cardlis Test3","Image":"/Content/images/client.png","Phone":"9988556555","HistoryList":[{"Services":"Shave","Amount":"30","Date":"18 Jan","TimeSlot":"4:00 PM-5:00 PM"}]}
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
         * Id : 36
         * Name : Cardlis Test3
         * Image : /Content/images/client.png
         * Phone : 9988556555
         * HistoryList : [{"Services":"Shave","Amount":"30","Date":"18 Jan","TimeSlot":"4:00 PM-5:00 PM"}]
         */

        @SerializedName("Id")
        private int Id;
        @SerializedName("Name")
        private String Name;
        @SerializedName("Image")
        private String Image;
        @SerializedName("Phone")
        private String Phone;
        @SerializedName("HistoryList")
        private List<HistoryListBean> HistoryList;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getImage() {
            return Image;
        }

        public void setImage(String Image) {
            this.Image = Image;
        }

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String Phone) {
            this.Phone = Phone;
        }

        public List<HistoryListBean> getHistoryList() {
            return HistoryList;
        }

        public void setHistoryList(List<HistoryListBean> HistoryList) {
            this.HistoryList = HistoryList;
        }

        public static class HistoryListBean {
            /**
             * Services : Shave
             * Amount : 30
             * Date : 18 Jan
             * TimeSlot : 4:00 PM-5:00 PM
             */

            @SerializedName("Services")
            private String Services;
            @SerializedName("Amount")
            private String Amount;
            @SerializedName("Date")
            private String Date;
            @SerializedName("TimeSlot")
            private String TimeSlot;

            public String getServices() {
                return Services;
            }

            public void setServices(String Services) {
                this.Services = Services;
            }

            public String getAmount() {
                return Amount;
            }

            public void setAmount(String Amount) {
                this.Amount = Amount;
            }

            public String getDate() {
                return Date;
            }

            public void setDate(String Date) {
                this.Date = Date;
            }

            public String getTimeSlot() {
                return TimeSlot;
            }

            public void setTimeSlot(String TimeSlot) {
                this.TimeSlot = TimeSlot;
            }
        }
    }
}
