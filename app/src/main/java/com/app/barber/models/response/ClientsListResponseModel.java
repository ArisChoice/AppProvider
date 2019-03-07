package com.app.barber.models.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by harish on 8/1/19.
 */

public class ClientsListResponseModel {


    /**
     * Message : Success
     * Status : 201
     * Response : [{"Id":31,"Name":"Aaaaa","Email":"Aaaaa@g.Com","Image":"/Uploads/CustomerImages/8f94b2f3-6d19-452a-8088-92e3c565e0c8.jpg","Address":"1st and 2nd Floor, Towers A & ","Contact":"3693693690","UserInfo":{"QBId":"77338883","Image":"/Content/images/client.png","DialogId":null}},{"Id":12,"Name":"Akgjv","Email":"Afjvk@gmail.com","Image":"/Uploads/CustomerImages/00fdc1d1-92bc-4ae2-9ab5-321b51a780e6.jpg","Address":"Plot Number 22-23, IT Park, Ph","Contact":"767575757","UserInfo":null},{"Id":15,"Name":"Boendb","Email":"Bo@mailinator.com","Image":"/Uploads/CustomerImages/fc9bd2a2-a8e5-4437-80d7-154ac8a4912b.png","Address":"1st and 2nd Floor, Towers A & ","Contact":"94979979686","UserInfo":null},{"Id":18,"Name":"Chgibk","Email":"Ckgkg@g.com","Image":"/Uploads/CustomerImages/4e3d9768-b389-4d82-8a30-b7a4f77a641a.jpg","Address":"Panchkula, Haryana, India","Contact":"275755882","UserInfo":null},{"Id":28,"Name":"Er Charle. 2","Email":null,"Image":null,"Address":null,"Contact":"+919592476534","UserInfo":null},{"Id":10,"Name":"Nasdff","Email":"Nas@gmail.com","Image":"/Uploads/CustomerImages/eb528c08-c336-4de4-ace4-230bb107e6af.png","Address":"DLF Building, Tower-C, 2nd Flo","Contact":"8588606883","UserInfo":null},{"Id":14,"Name":"Pisj Hs","Email":"Haha@gmail.com","Image":"/Uploads/CustomerImages/907dce7a-0373-4852-be09-2f5775831d6d.png","Address":"Phase - I, Manimajra, Chandiga","Contact":"346499485","UserInfo":null},{"Id":6,"Name":"Rabb","Email":"Rabb@gmail.com","Image":"/Uploads/CustomerImages/28bd54b7-3a5a-497a-a925-f434fc44e1c3.jpg","Address":"Panchkula, Haryana, India","Contact":"1234567890","UserInfo":{"QBId":"76654473","Image":"/Content/images/client.png","DialogId":"5c5180c3a28f9a4611f2c686"}},{"Id":9,"Name":"Sfjgkg","Email":"Dhchfjf@gmail.com","Image":"/Uploads/CustomerImages/2958f55f-6513-4ec6-91a3-b8bd944b88b1.jpg","Address":"Rajiv Gandhi Chandigarh Techno","Contact":"52768060","UserInfo":null},{"Id":11,"Name":"Tuigj","Email":"Tyu@gmail.com","Image":"/Uploads/CustomerImages/d768abec-99d8-4891-bde3-ef8ac7168728.png","Address":"Phase - I, Manimajra, Sukteri,","Contact":"53865758606","UserInfo":null},{"Id":16,"Name":"Ubkkj","Email":"Uxvh@gmail.com","Image":"/Uploads/CustomerImages/7fb88193-30eb-4576-a271-86cee00a8dbd.jpg","Address":"Phase - I, Manimajra, Chandiga","Contact":"3952774","UserInfo":null},{"Id":13,"Name":"Xohbk","Email":"Xgsg@gmail.com","Image":"/Uploads/CustomerImages/0971f833-8310-4ac4-b7db-9553dcc2242d.jpg","Address":"Tower C, Third Floor DLF Build","Contact":"9799499494","UserInfo":null},{"Id":17,"Name":"Yuoooh Vvbi","Email":"Yovb@gil.com","Image":"/Uploads/CustomerImages/66f72e5e-5011-4598-937e-dcbd31a695fd.png","Address":"2nd Floor, Tower B, Phase - I,","Contact":"56585758686","UserInfo":null}]
     */

    @SerializedName("Message")
    private String Message;
    @SerializedName("Status")
    private int Status;
    @SerializedName("Response")
    private List<ResponseBean> Response;

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

    public List<ResponseBean> getResponse() {
        return Response;
    }

    public void setResponse(List<ResponseBean> Response) {
        this.Response = Response;
    }

    public static class ResponseBean implements Serializable {
        /**
         * Id : 31
         * Name : Aaaaa
         * Email : Aaaaa@g.Com
         * Image : /Uploads/CustomerImages/8f94b2f3-6d19-452a-8088-92e3c565e0c8.jpg
         * Address : 1st and 2nd Floor, Towers A &
         * Contact : 3693693690
         * UserInfo : {"QBId":"77338883","Image":"/Content/images/client.png","DialogId":null}
         */

        @SerializedName("Id")
        private int Id;
        @SerializedName("Name")
        private String Name;
        @SerializedName("Email")
        private String Email;
        @SerializedName("Image")
        private String Image;
        @SerializedName("Address")
        private String Address;
        @SerializedName("Contact")
        private String Contact;
        @SerializedName("UserInfo")
        private UserInfoBean UserInfo;

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

        public String getEmail() {
            return Email;
        }

        public void setEmail(String Email) {
            this.Email = Email;
        }

        public String getImage() {
            return Image;
        }

        public void setImage(String Image) {
            this.Image = Image;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getContact() {
            return Contact;
        }

        public void setContact(String Contact) {
            this.Contact = Contact;
        }

        public UserInfoBean getUserInfo() {
            return UserInfo;
        }

        public void setUserInfo(UserInfoBean UserInfo) {
            this.UserInfo = UserInfo;
        }

        public static class UserInfoBean implements Serializable {
            /**
             * QBId : 77338883
             * Image : /Content/images/client.png
             * DialogId : null
             */

            @SerializedName("QBId")
            private String QBId;
            @SerializedName("Image")
            private String Image;
            @SerializedName("DialogId")
            private String DialogId;

            public String getQBId() {
                return QBId;
            }

            public void setQBId(String QBId) {
                this.QBId = QBId;
            }

            public String getImage() {
                return Image;
            }

            public void setImage(String Image) {
                this.Image = Image;
            }

            public String getDialogId() {
                return DialogId;
            }

            public void setDialogId(String DialogId) {
                this.DialogId = DialogId;
            }
        }
    }
}
