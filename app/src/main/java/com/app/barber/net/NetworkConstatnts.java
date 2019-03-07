package com.app.barber.net;

/**
 * Created by harish on 24/8/18.
 */

public interface NetworkConstatnts {

    String TOKEN_PREF = "mypref_Token_Barber";
    String STRIPE_TEST_KEY = "pk_test_bCRYp7Tm1SKmfdKToNO0z8GB" /*"pk_test_u8fIUtKG1iv0vNwPVi6CTEXY"*/;

    interface ResponseCode {
        int success = 201;
        int sessionExpred = 401;
    }

    interface URL {
        //        String BASE_URL = ""; //LIVE
        String BASE_URL = "http://barber.xicom.info/";//DEMO
        String STRIP_SETUP = "https://dashboard.stripe.com/register";
    }

    interface KEYS {
        String secretKey = "J3H7F9J6FG";
        String deviceType = "DeviceType";
        String uniqueDeviceId = "UniqueDeviceId";
        // String deviceId = "DeviceID";
        String TimeStamp = "TimeStamp";
        String sessionToken = "SessionToken";
        String deviceToken = "DeviceToken";
        String userId = "userId";
        String sessionId = "SessionId";
        String ClientHash = "ClientHash";


        String userType = "UserType";
        String deviceId = "DeviceId";
        String timezone = "TimeZone";
    }

    interface API {
        String ABOUT_US = URL.BASE_URL + "Page/AboutUs";
        String FAQ = URL.BASE_URL + "Page/FAQ";
        String TERMS = URL.BASE_URL + "Page/TermsAndConditions";


        String loginUser = "api/account/Login";
        String registerUser = "api/account/Register";
        String updateBarberType = "api/barber/UpdateBarberType";
        String updateSpecialisationType = "api/barber/UpdateSpecType";
        String updateAddress = "api/barber/AddAddress";
        String updateOpeningTime = "api/barber/AddOpeningHours";
        String addService = "api/barber/AddServices";
        String getServices = "api/barber/GetServices";
        String ADD_WORKPLACE_PHOTOS = "api/account/PostWorkSpaceImages";
        String UPDATE_PROFILE = "api/account/UpdateProfile";
        String validateNumber = "api/account/UpdatePhoneNumber";
        String getMyImages = "api/account/PortfolioImages ";
        String updtaeCalloutHours = "api/barber/AddCallOutHours";
        String getHours = "api/barber/GetHours";
        String updateBreakHours = "api/barber/AddBreakHours";
        String GET_SPECIALISATION = "api/account/GetSpecializations";
        String updatePaymentType = "api/barber/UpdatePaymentType";
        String GET_PROFILE = "api/account/GetProfile";
        String GET_ACTIVE_APPOINTMENTS = "api/barber/Dashboard";
        String GET_UPCOMING_APPOINTMENTS = "api/barber/BookingListing";
        String GET_NOTIFIACTIONS_LIST = "api/barber/NotificationList";
        String GET_RECENT_STATUS = "api/account/GetStatus";
        String forgotPassword = "api/account/ForgotPassword";

        String UPDATE_BOOKING_STATUS = "api/barber/ChangeBookingStatus";
        String UPDATE_CALLOUT_STATUS = "api/barber/ChangeCallOutStatus";
        String GET_CANCELED_APPOINTMENTS = "api/barber/CanceledBookingList";
        String UPDATE_NOTIFICATION_STATUS = "api/account/MakeNotificationSeen";

        String ADD_CLIENT = "api/barber/ManageCustomer";

        String GET_CLIENTS = "api/barber/ListCustomer";
        String ADD_CLIENT_APPOINTMENT = "api/barber/AddCustomerBookings";
        String GET_CHAT_USERS = "api/account/GetChatParticipants";
        String DELETE_SERVICE = "api/barber/DeleteService";
        String validateMobileNumber = "api/account/ValidatePhoneNumber";
        String CHECK_QB_ID = "api/account/GetQBId";
        String UPDATE_CHAT_DIALOG_ID = "api/account/UpdateChatDialog";//UpdateQuickBloxDialog
        String REMOVE_BREAK_HOUR = "api/barber/DeleteBreakHour";
        String GET_WEEK_OVERVIEW = "api/barber/CalenderData";
        String GET_ZONES = "api/account/GetZones";
        String GET_ZONES_DISTRICTS = "api/account/GetDistricts";

        String SAVE_SERVING_DISTRIC = "api/barber/SaveDistricts";

        String SAVE_ADVANCE_BOOKING_TIME = "api/barber/SaveAdvanceBookingTime";
        String GET_SAVED_ADVANCE_TIME = "api/barber/GetAdvanceBookingTime";
        String GET_BARBER_STATS = "api/barber/GetBarberAnalytics";

        String ADD_BLOCK_HOURS = "api/barber/AddBlockHours";

        String GET_BLOCK_HOURS = "api/barber/BlockHours";
        String DELETE_BREAK_HOUR = "api/barber/DeleteBlockHour";
        String ADD_MOBILE_CONTACTS = "api/barber/ImportContacts";
        String GET_PAYMENT_HISTORY_IN = "api/account/GetPaymentsIn";
        String UPDATE_TODAYS_BOOKING_STATUS = "api/barber/TodayAppointmentStatus";
        String GET_PAYMENT_HISTORY_OUT = "api/account/GetPaymentsOut";
        String SAVE_QB_DIALOG = "api/account/SaveQbDialog";
        String GET_FUTURRE_BOOKING_STATUS = "api/barber/FutureAppointmentStatus";
        String UPDTAE_FUTUTE_DATE_STATUS = "api/barber/ChangeFutureAppointmentStatus";
        String getTimeSlots = "/api/user/TimeSlots";

        String EDIT_BOOKING_REQUEST = "api/account/EditBooking";

        String UPDATE_EDIT_REQUEST_STATUS = "api/account/ChageEditStatus";

        String GET_GRAPH_DATA = "api/barber/GetGraphData";
        String GET_MY_ADDRESS = "api/account/GetAddress";
        String GET_CARD_LIST = "api/payment/GetCardList";
        String DELETE_CARD = "api/payment/DeleteCard";
        String SAVE_CARD = "api/payment/SaveCard";
        String PAY_DUE_AMOUNT = "api/barber/PayDueAmount";
        String GET_REFERER_REWARD = "api/barber/UseReferralCode";
        String GET_CLIENT_BOOKINGS = "api/barber/GetCustomerHistory";
        String DELETE_IMAGE = "api/account/DeletePortFolioImages";
        String NOTIFY_ENTOUT = "api/barber/ConfirmEnroute";
        String GET_BLOCK_DATES = "api/barber/DateWiseBlockHour";
        String CHANGE_SETTINGS = "api/barber/ChangeSettings";
        String GET_SETTING_STATUS = "api/barber/GetNotificationSettings";
        String GET_CONVERSATION_LIST = "api/account/GetChat";
        String VERIFY_OTP ="api/account/VerifyOTP" ;
    }

    interface Params {
        String firstName = "FirstName";
        String email = "Email";
        String lastName = "LastName";
        String desc = "Description";

        String value = "type";//value=”portfolio” || “banner”

        String fullname = "FullName";
        String shopName = "ShopName";
        String facebook = "FbUrl";
        String twitter = "TwtUrl";
        String insta = "InstaUrl";
        String other = "OtherUrl";
        String userType = "UserType";
        String contact = "Contact";
        String address = "Address";
    }

    interface RequestCode {
        int API_LOGIN = 1;
        int API_REGISTER = 2;
        int API_FORGET_PASSWORD = 3;
        int API_UPDATE_BARBER_TYPE = 4;
        int API_UPDATE_SPEC_TYPE = 5;
        int API_UPDATE_ADDRESS = 6;
        int API_UPDATE_SERVICE = 7;
        int API_UPDATE_OPENING_TIME = 8;
        int API_ADD_SERVICE = 9;
        int API_GET_SERVICE = 10;
        int API_POST_WORKSPACE_IMAGES = 11;
        int API_UPDATE_PROFILE = 12;
        int API_VALIDATE_NUMBER = 13;
        int API_GET_IMAGES = 14;
        int API_UPDATE_CALLOUT_TIME = 15;
        int API_UPDATE_BREAK_TIME = 16;
        int API_GET_HOURS = 17;
        int API_GET_SPECIALISATION = 18;
        int API_UPDATE_PAYMENT_TYPE = 19;
        int API_GET_PROFILE = 20;
        int API_DASHBOARD_APPOINTMENTS = 21;
        int API_UPCOMMING_APPOINTMENTS = 22;
        int API_NOTIFICATION_LIST = 23;

        int API_CHECK_RECENT_STATUS = 24;

        int API_FORGET_PASS = 25;
        int API_UPDATE_BOOKING_STATUS = 26;
        int API_UPDATE_CALLOUT_STATUS = 27;
        int API_CANCELED_APPOINTMENTS = 28;
        int API_UPDATE_NOTIFICATION_STATUS = 29;
        int API_ADD_CLIENT = 30;
        int API_LIST_CUSTOMERS = 31;
        int API_ADD_CLIENT_APPOINTMENT = 32;
        int API_GET_CHAT_PARTICIPENT = 33;
        int API_REMOVE_SERVICE = 34;
        int API_CHECK_QB_ID = 35;
        int API_UPDTAE_CHAT_DIALOG = 36;
        int API_DELETE_BREAK_HOUR = 37;
        int API_GET_WEEK_OVERVIEW = 38;
        int API_GET_ZONES = 39;
        int API_GET_DISTRIC_LIST = 40;
        int API_SAVE_SERVING_DISTRICTS = 41;
        int API_ADVANCE_BOOKING_TIME = 42;
        int API_GET_ADVANCE_BOOKING_TIME = 43;
        int API_GET_BARBER_STATS = 44;
        int API_ADD_BLOCK_HOURS = 45;
        int API_BLOCKED_HOURS = 46;
        int API_DELETE_BLOCK_HOURS = 47;
        int API_ADD_MOBILE_CONTACTS = 48;
        int API_GET_PAYMENT_HISTORY_IN = 49;
        int API_GET_PAYMENT_HISTORY_OUT = 50;
        int API_UPDATE_TODAY_APPOINTMENT_STATUS = 50;
        int API_SAVE_QB_DIALOG = 51;

        int API_GET_FUTURE_STATUS = 52;

        int API_UPDATE_FUTURE_STATUS = 53;

        int API_AVAILABLE_SLOTS = 54;

        int API_EDIT_BOOKING_REQUEST = 55;

        int API_EDIT_BOOKING_STATUS = 56;
        int API_GET_GRAPH_DATA = 57;
        int API_GET_USER_ADDRESS = 58;

        int API_GET_SAVED_CARDS = 59;
        int API_REMOVE_SAVED_CARD = 60;
        int API_SAVE_CARD = 61;
        int API_PAY_DUE_AMOUNT = 62;
        int API_APPLY_REFERAL = 63;
        int API_GET_CLIENT_BOOKINGS = 64;
        int API_DELETE_IMAGE = 65;
        int API_NOTIFY_ENROUT = 66;
        int API_GET_FUTURE_BLOCK_STATUS = 67;
        int API_CHANGE_SETTING = 68;
        int API_GET_SETTINGS_STATUS = 69;
        int API_CONVERSATION_LIST = 70;
        int API_VERIFY_OTP =71;
        int API_VALIDATE_SAVE_NUMBER =72 ;
    }

    interface DeviceType {
        String Android = "1";
    }

    interface QB {
        String APP_ID = "75356";
        String ACCOUNT_ID = "102713";
        String AUTH_KEY = "cjjqmG272xj5XYE";
        String AUTH_SECRET = "yfQ-BFEFhCy9Gwf";
        String ACCOUNT_KEY = "_3bTSswpeMf9H4KcwN9S";
        String GLOBAL_PASSWORD = "trimuser";
    }
}
