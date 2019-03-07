package com.app.barber.models.response;

import android.app.Service;

import com.app.barber.models.TimeSlotsModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by harish on 31/10/18.
 */

public class UpdateDataResponse {

    /**
     * Message : Success
     * Status : 201
     * BarberType : 1,2
     */

    @SerializedName("Message")
    private String Message;
    @SerializedName("Status")
    private int Status;
    @SerializedName("BarberType")
    private String BarberType;
    @SerializedName("SpecType")
    private String SpecType;

    public String getPaymentType() {
        return PaymentType;
    }

    public void setPaymentType(String paymentType) {
        PaymentType = paymentType;
    }

    @SerializedName("PaymentType")
    private String PaymentType;
    @SerializedName("OpeningHours")
    @Expose
    private List<OpeningHoursBean> OpeningHours = null;

    public List<OpeningHoursBean> getCallOutHours() {
        return CallOutHours;
    }

    public void setCallOutHours(List<OpeningHoursBean> callOutHours) {
        CallOutHours = callOutHours;
    }

    @SerializedName("CallOutHours")
    @Expose
    private List<OpeningHoursBean> CallOutHours = null;

    public Address getAddress() {
        return address;
    }

    @SerializedName("Services")
    @Expose
    private List<Service> services = null;

    public void setAddress(Address address) {
        this.address = address;
    }

    @SerializedName("Address")
    private Address address;

    class Address {
        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

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

        public String getZip() {
            return Zip;
        }

        public void setZip(String zip) {
            Zip = zip;
        }

        String Id;
        String AddressLine1;
        String AddressLine2;
        String City;
        String Zip;

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

        String Lat;
        String Long;

    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public List<OpeningHoursBean> getOpeningHours() {
        return OpeningHours;
    }

    public void setOpeningHours(List<OpeningHoursBean> OpeningHours) {
        this.OpeningHours = OpeningHours;
    }

    public String getSpecType() {
        return SpecType;
    }

    public void setSpecType(String specType) {
        SpecType = specType;
    }


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

    public String getBarberType() {
        return BarberType;
    }

    public void setBarberType(String BarberType) {
        this.BarberType = BarberType;
    }

    public static class OpeningHoursBean {
        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        /**
         * Day : Monday
         * OpeningHours : 01:25 AM
         * ClosingHours : 01:25 AM
         */
        @SerializedName("Id")
        private String Id;
        @SerializedName("Day")
        private String Day;
        @SerializedName("OpeningHours")
        private String OpeningHours;
        @SerializedName("ClosingHours")
        private String ClosingHours;
        @SerializedName("BreakHoursList")
        @Expose
        private List<TimeSlotsModel.BreakHoursList> breakHoursList = null;

        public Boolean getClosed() {
            return isClosed;
        }

        public void setClosed(Boolean closed) {
            isClosed = closed;
        }

        @SerializedName("IsClosed")
        @Expose
        private Boolean isClosed;


        public List<TimeSlotsModel.BreakHoursList> getBreakHoursList() {
            return breakHoursList;
        }

        public void setBreakHoursList(List<TimeSlotsModel.BreakHoursList> breakHoursList) {
            this.breakHoursList = breakHoursList;
        }

        public String getDay() {
            return Day;
        }

        public void setDay(String Day) {
            this.Day = Day;
        }

        public String getOpeningHours() {
            return OpeningHours;
        }

        public void setOpeningHours(String OpeningHours) {
            this.OpeningHours = OpeningHours;
        }

        public String getClosingHours() {
            return ClosingHours;
        }

        public void setClosingHours(String ClosingHours) {
            this.ClosingHours = ClosingHours;
        }
    }

    public class Service {

        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("ServiceName")
        @Expose
        private String serviceName;
        @SerializedName("Duration")
        @Expose
        private String duration;
        @SerializedName("Price")
        @Expose
        private Integer price;
        @SerializedName("PriceType")
        @Expose
        private String priceType;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public String getPriceType() {
            return priceType;
        }

        public void setPriceType(String priceType) {
            this.priceType = priceType;
        }

    }
}
