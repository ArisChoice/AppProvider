package com.app.barber.models.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by harish on 18/12/18.
 */

public class AppointmentsResponseModel {

    /**
     * Message : Success
     * Status : 201
     * Response : {"Name":"Test Nai","UserName":"@testnai","ProfileImage":"/Content/images/team-2.jpg","BannerImage":"/Uploads/BannerImages/43986172-efb8-4e64-ae59-8c0cc3dcc9ec.jpg","BookingRequestCount":2,"CallOutRequestCount":0,"CancelledCount":0,"BookingList":[{"UserId":2059,"UserName":"Test Customer ","Name":"Test Customer ","Phone":"7814817584","DateString":"12/18/2018","UserImage":"/Content/images/client.jpg","Address":null,"Id":5,"ServiceId":"Haircut,Hair Colouring ","BarberId":0,"Date":"2018-12-18T00:00:00","TimingSlot":"05:09 PM-06:09 PM","Amount":105,"Note":null},{"UserId":2060,"UserName":"Test User","Name":"Test User","Phone":"789456123","DateString":"12/18/2018","UserImage":"/Content/images/client.jpg","Address":null,"Id":12,"ServiceId":"Haircut","BarberId":0,"Date":"2018-12-18T00:00:00","TimingSlot":"06:27 PM-06:57 PM","Amount":55,"Note":null}],"CallOutRequestList":[]}
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
         * Name : Test Nai
         * UserName : @testnai
         * ProfileImage : /Content/images/team-2.jpg
         * BannerImage : /Uploads/BannerImages/43986172-efb8-4e64-ae59-8c0cc3dcc9ec.jpg
         * BookingRequestCount : 2
         * CallOutRequestCount : 0
         * CancelledCount : 0
         * BookingList : [{"UserId":2059,"UserName":"Test Customer ","Name":"Test Customer ","Phone":"7814817584","DateString":"12/18/2018","UserImage":"/Content/images/client.jpg","Address":null,"Id":5,"ServiceId":"Haircut,Hair Colouring ","BarberId":0,"Date":"2018-12-18T00:00:00","TimingSlot":"05:09 PM-06:09 PM","Amount":105,"Note":null},{"UserId":2060,"UserName":"Test User","Name":"Test User","Phone":"789456123","DateString":"12/18/2018","UserImage":"/Content/images/client.jpg","Address":null,"Id":12,"ServiceId":"Haircut","BarberId":0,"Date":"2018-12-18T00:00:00","TimingSlot":"06:27 PM-06:57 PM","Amount":55,"Note":null}]
         * CallOutRequestList : []
         */

        @SerializedName("Name")
        private String Name;
        @SerializedName("UserName")
        private String UserName;
        @SerializedName("ProfileImage")
        private String ProfileImage;
        @SerializedName("BannerImage")
        private String BannerImage;
        @SerializedName("BookingRequestCount")
        private int BookingRequestCount;
        @SerializedName("CallOutRequestCount")
        private int CallOutRequestCount;
        @SerializedName("CancelledCount")
        private int CancelledCount;


        public int getTotalEarnings() {
            return TotalEarnings;
        }

        public void setTotalEarnings(int totalEarnings) {
            TotalEarnings = totalEarnings;
        }

        @SerializedName("TotalEarnings")
        private int TotalEarnings;

        @SerializedName("BookingList")
        private List<BookingListBean> BookingList;
        @SerializedName("CallOutRequestList")
        private List<BookingListBean> CallOutRequestList;

        public boolean isFutureAppointmentsExist() {
            return IsFutureAppointmentsExist;
        }

        public void setFutureAppointmentsExist(boolean futureAppointmentsExist) {
            IsFutureAppointmentsExist = futureAppointmentsExist;
        }

        private boolean IsFutureAppointmentsExist;
        @SerializedName("IsFutureAppointmentsExist")

        public boolean isFutureAppointments() {
            return IsFutureAppointments;
        }

        public void setFutureAppointments(boolean futureAppointments) {
            IsFutureAppointments = futureAppointments;
        }

        public boolean isFutureCallOuts() {
            return IsFutureCallOuts;
        }

        public void setFutureCallOuts(boolean futureCallOuts) {
            IsFutureCallOuts = futureCallOuts;
        }

        @SerializedName("IsFutureAppointments")
        private boolean IsFutureAppointments;

        @SerializedName("IsFutureCallOuts")
        private boolean IsFutureCallOuts;

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public String getProfileImage() {
            return ProfileImage;
        }

        public void setProfileImage(String ProfileImage) {
            this.ProfileImage = ProfileImage;
        }

        public String getBannerImage() {
            return BannerImage;
        }

        public void setBannerImage(String BannerImage) {
            this.BannerImage = BannerImage;
        }

        public int getBookingRequestCount() {
            return BookingRequestCount;
        }

        public void setBookingRequestCount(int BookingRequestCount) {
            this.BookingRequestCount = BookingRequestCount;
        }

        public int getCallOutRequestCount() {
            return CallOutRequestCount;
        }

        public void setCallOutRequestCount(int CallOutRequestCount) {
            this.CallOutRequestCount = CallOutRequestCount;
        }

        public int getCancelledCount() {
            return CancelledCount;
        }

        public void setCancelledCount(int CancelledCount) {
            this.CancelledCount = CancelledCount;
        }

        public List<BookingListBean> getBookingList() {
            return BookingList;
        }

        public void setBookingList(List<BookingListBean> BookingList) {
            this.BookingList = BookingList;
        }

        public List<BookingListBean> getCallOutRequestList() {
            return CallOutRequestList;
        }

        public void setCallOutRequestList(List<BookingListBean> CallOutRequestList) {
            this.CallOutRequestList = CallOutRequestList;
        }

        public static class Address {

            @SerializedName("AddressLine1")
            private String AddressLine1;
            @SerializedName("AddressLine2")
            private String AddressLine2;
            @SerializedName("City")
            private String City;

            public String getAddressLine1() {
                return AddressLine1;
            }

            public void setAddressLine1(String addressLine1) {
                AddressLine1 = addressLine1;
            }

            public String getAddressLine2() {
                return AddressLine2;
            }

            public void setAddressLine2(String addressLine2) {
                AddressLine2 = addressLine2;
            }

            public String getCity() {
                return City;
            }

            public void setCity(String city) {
                City = city;
            }

            public String getLat() {
                return Lat;
            }

            public void setLat(String lat) {
                Lat = lat;
            }

            public String getLong() {
                return Long;
            }

            public void setLong(String aLong) {
                Long = aLong;
            }

            @SerializedName("Lat")
            private String Lat;
            @SerializedName("Long")
            private String Long;
        }

        public static class BookingListBean {
            /**
             * UserId : 2059
             * UserName : Test Customer
             * Name : Test Customer
             * Phone : 7814817584
             * DateString : 12/18/2018
             * UserImage : /Content/images/client.jpg
             * Address : null
             * Id : 5
             * ServiceId : Haircut,Hair Colouring
             * BarberId : 0
             * Date : 2018-12-18T00:00:00
             * TimingSlot : 05:09 PM-06:09 PM
             * Amount : 105
             * Note : null
             */

            @SerializedName("UserId")
            private int UserId;
            @SerializedName("UserName")
            private String UserName;
            @SerializedName("Name")
            private String Name;
            @SerializedName("Phone")
            private String Phone;
            @SerializedName("DateString")
            private String DateString;
            @SerializedName("UserImage")
            private String UserImage;
            @SerializedName("Address")
            private Address Address;
            @SerializedName("Id")
            private int Id;
            @SerializedName("ServiceId")
            private String ServiceId;
            public String getServiceNames() {
                return ServiceNames;
            }

            public void setServiceNames(String serviceNames) {
                ServiceNames = serviceNames;
            }

            @SerializedName("ServiceNames")
            private String ServiceNames;

            @SerializedName("BarberId")
            private int BarberId;
            @SerializedName("Date")
            private String Date;
            @SerializedName("TimingSlot")
            private String TimingSlot;
            @SerializedName("Amount")
            private int Amount;
            @SerializedName("Note")
            private Object Note;
            @SerializedName("IsCompleted")
            private boolean IsCompleted;

            public boolean isEnrouteShow() {
                return IsEnrouteShow;
            }

            public void setEnrouteShow(boolean enrouteShow) {
                IsEnrouteShow = enrouteShow;
            }

            @SerializedName("IsEnrouteShow")
            private boolean IsEnrouteShow;

            public int getCancellationCharge() {
                return CancellationCharge;
            }

            public void setCancellationCharge(int cancellationCharge) {
                CancellationCharge = cancellationCharge;
            }

            @SerializedName("CancellationCharge")
            private int CancellationCharge;

            public String getChatDialog() {
                return ChatDialog;
            }

            public void setChatDialog(String chatDialog) {
                ChatDialog = chatDialog;
            }

            @SerializedName("ChatDialog")
            private String ChatDialog;

            public String getEditData() {
                return EditData;
            }

            public void setEditData(String editData) {
                EditData = editData;
            }

            @SerializedName("EditData")
            private String EditData;

            public String getPaymentMode() {
                return PaymentMode;
            }

            public void setPaymentMode(String paymentMode) {
                PaymentMode = paymentMode;
            }

            @SerializedName("PaymentMode")
            private String PaymentMode;

            public String getDistance() {
                return Distance;
            }

            public void setDistance(String distance) {
                Distance = distance;
            }

            @SerializedName("Distance")
            private String Distance;

            public boolean isCompleted() {
                return IsCompleted;
            }

            public void setCompleted(boolean completed) {
                IsCompleted = completed;
            }

            public boolean isCanceled() {
                return IsCanceled;
            }

            public void setCanceled(boolean canceled) {
                IsCanceled = canceled;
            }

            public boolean isConfirmed() {
                return IsConfirmed;
            }

            public void setConfirmed(boolean confirmed) {
                IsConfirmed = confirmed;
            }

            @SerializedName("IsCanceled")
            private boolean IsCanceled;
            @SerializedName("IsConfirmed")
            private boolean IsConfirmed;

            public int getBookingType() {
                return BookingType;
            }

            public void setBookingType(int bookingType) {
                BookingType = bookingType;
            }

            @SerializedName("BookingType")
            private int BookingType;

            public String getTotalDuration() {
                return TotalDuration;
            }

            public void setTotalDuration(String totalDuration) {
                TotalDuration = totalDuration;
            }

            @SerializedName("TotalDuration")
            private String TotalDuration;

            public String getCard() {
                return Card;
            }

            public void setCard(String card) {
                Card = card;
            }

            public String getReceivedAmount() {
                return ReceivedAmount;
            }

            public void setReceivedAmount(String receivedAmount) {
                ReceivedAmount = receivedAmount;
            }

            @SerializedName("Card")
            private String Card;
            @SerializedName("ReceivedAmount")
            private String ReceivedAmount;

            public String getTimeRemaining() {
                return TimeRemaining;
            }

            public void setTimeRemaining(String timeRemaining) {
                TimeRemaining = timeRemaining;
            }

            @SerializedName("TimeRemaining")
            private String TimeRemaining;

            public int getUserId() {
                return UserId;
            }

            public void setUserId(int UserId) {
                this.UserId = UserId;
            }

            public String getUserName() {
                return UserName;
            }

            public void setUserName(String UserName) {
                this.UserName = UserName;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public String getPhone() {
                return Phone;
            }

            public void setPhone(String Phone) {
                this.Phone = Phone;
            }

            public String getDateString() {
                return DateString;
            }

            public void setDateString(String DateString) {
                this.DateString = DateString;
            }

            public String getUserImage() {
                return UserImage;
            }

            public void setUserImage(String UserImage) {
                this.UserImage = UserImage;
            }

            public Address getAddress() {
                return Address;
            }

            public void setAddress(Address Address) {
                this.Address = Address;
            }

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public String getServiceId() {
                return ServiceId;
            }

            public void setServiceId(String ServiceId) {
                this.ServiceId = ServiceId;
            }

            public int getBarberId() {
                return BarberId;
            }

            public void setBarberId(int BarberId) {
                this.BarberId = BarberId;
            }

            public String getDate() {
                return Date;
            }

            public void setDate(String Date) {
                this.Date = Date;
            }

            public String getTimingSlot() {
                return TimingSlot;
            }

            public void setTimingSlot(String TimingSlot) {
                this.TimingSlot = TimingSlot;
            }

            public int getAmount() {
                return Amount;
            }

            public void setAmount(int Amount) {
                this.Amount = Amount;
            }

            public Object getNote() {
                return Note;
            }

            public void setNote(Object Note) {
                this.Note = Note;
            }

            public String getQBdialogId() {
                return QBdialogId;
            }

            public void setQBdialogId(String QBdialogId) {
                this.QBdialogId = QBdialogId;
            }

            @SerializedName("QbDailogId")
            private String QBdialogId;
        }
    }
}
