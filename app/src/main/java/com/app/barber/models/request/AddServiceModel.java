package com.app.barber.models.request;

/**
 * Created by harish on 1/11/18.
 */

public class AddServiceModel {
    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String serviceName) {
        ServiceName = serviceName;
    }

    public int getDuration() {
        return Duration;
    }

    public void setDuration(int duration) {
        Duration = duration;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getPriceType() {
        return PriceType;
    }

    public void setPriceType(String priceType) {
        PriceType = priceType;
    }

    String ServiceName;
    int Duration;
    String Price;
    String PriceType;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    String Id;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    int type;
}
