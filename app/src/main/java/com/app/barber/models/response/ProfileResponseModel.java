package com.app.barber.models.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by harish on 6/12/18.
 */

public class ProfileResponseModel {

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    /**
     * Message : Success
     * Status : 201
     * User : {"UserID":1053,"FullName":"Test","Email":"Testbarber1@gmail.com","UserType":1,"IsDeleted":false,"UserName":"barber1","PhoneNumber":"993939393","Invitationcode":null,"BarberType":"1","Specializaions":"1,2","PaymentType":"3","SessionId":null,"ProfileImage":"/Content/images/team-2.jpg","BannerImage":"/Uploads/BannerImages/02c987d9-80c5-4aa6-9dce-52b18269e2d5.jpg","Description":null,"CallOutHours":null,"OpeningHours":null,"Services":null,"UserAddresses":{"Id":2,"AddressLine1":"Chandigarh International Airport ","AddressLine2":"Chandigarh ","City":"Chandigarh ","Zip":"160001"}}
     */

    @SerializedName("Message")
    private String Message;
    @SerializedName("Status")
    private int Status;

    public LoginResponseModel.UserBean getUser() {
        return User;
    }

    public void setUser(LoginResponseModel.UserBean user) {
        User = user;
    }

    @SerializedName("User")
    private LoginResponseModel.UserBean User;


}
