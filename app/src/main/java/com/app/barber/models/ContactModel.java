package com.app.barber.models;

/**
 * Created by harish on 28/1/19.
 */

public class ContactModel {
    public String getContactName() {
        return Name;
    }

    public void setContactName(String contactName) {
        this.Name = contactName;
    }

    public String getContactNumber() {
        return Contact;
    }

    public void setContactNumber(String contactNumber) {
        this.Contact = contactNumber;
    }

    public String getContactImage() {
        return contactImage;
    }

    public void setContactImage(String contactImage) {
        this.contactImage = contactImage;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    String Name;
    String Contact;
    String contactImage;
    boolean isSelected;

}
