package com.app.barber.util;

/**
 * Created by harish on 16/10/18.
 */

public class GlobalValues {
    public static final String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";
    public static final String WEB_URL = "http://barber.xicom.info/Provider/home/Login";
    public static final String SUPPORT_ADMIN_ID = "159";
    public static String APPLICATION_PLAYSTORE_URL_CUSTOMER = "https://play.google.com/store/apps/details?id=" + "com.app.trimcheck.customer";
    public static String APPLICATION_PLAYSTORE_URL_BARBER = "https://play.google.com/store/apps/details?id=" + "com.app.trimcheck.barber";

    public enum DATE_FORMAT {
        ;
        public static final String STANDARD = "MM/dd/YYYY";
    }

    public interface RequestCodes {
        int TIME_SELECTION = 921;
        int ADD_MOBILE = 922;
        int ADDRESS_SEARCH = 923;
        int PERMISSIONS_REQUEST_CAMERA = 924;
        int REQUEST_TAKE_IMAGE = 925;
        int GALLERY_REQUEST = 926;
        int LOCATION_PICKER = 213;
        int PERMISSIONS_REQUEST_ = 927;
        int SELECT_SERVICES = 928;
        int REMOVE = 929;
        int MESSAGE = 930;
        int CALL = 931;
        int REQUEST_PHONE_CALL = 932;
        int REQUEST_PERMISSION_FOR_LOCATION = 931;
        int SELECT_CUSTOMER = 932;
        int UPDATE = 933;
        int REJECT_REQUEST = 934;
        int CANCED_REQUEST = 935;
        int ACCEPT_REQUEST = 936;
        int SELECTED_DAYS = 937;
        int CANCEL = 938;
        int ENROUTE = 939;

        int SKIP_IT = 940;
        int SEND = 941;
    }

    public interface EVENTS {

        int LOCATION_USER = 120;
        int EDIT_CALLBACK = 121;
    }

    public interface DateFormats {
        final String APPOINTMENT_FORMAT = "EEEE,MMMM YY";
        String DEFAULT_FORMAT_DATE = "MM/dd/yyyy";
        String DEFAULT_FORMAT_DATE_TIME = "MM/dd/yyyy hh:mm:ss";
        String FULL_DATE_TIME = "MM/dd/yyyy hh:mm a";
        String TIME_FORMAT = "hh:mm a";
    }

    public interface NotificationType {
        int AppointmentConfirmation = 1;
        int CallOutConfirmation = 2;
        int UserRating = 9;
        int CHAT_MESSAGE = 10;
    }

    public interface PaymentModes {

        String CARD = "Card";
        String CASH = "Cash";
        String FREE = "Free";
    }

    public interface SettingTypes {
        String CANCELLATION ="cancellation" ;
    }

    public static class Font {
        public static final String COMFORTAA_BOLD = "fonts/Inter-UI-Medium.ttf";
        public static final String COMFORTAA_LIGHT = "fonts/Inter-UI-Regular.ttf";
        public static final String COMFORTAA_REGULAR = "fonts/Inter-UI-Regular.ttf";
    }

    public static class CONSTANTS {
        public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";
        public static final String CLIENT_ID = "2";
        public static final String CLIENT_SECRET_KEY = "HzMMk2fbmxUx8nCEsrTawxHCHXGfdIHmMubq6QyI";
        public static final int RADIO_CHECKED = 1;
    }

    public interface KEYS {
        String isLogedIn = "isUserLogedin";
        String USER_ID = "userId";
        String USER_NAME = "userName";
        String IS_EDIT = "isEdit";
        String SELECTED_DAY = "selectedDay";
        String PLACE_DETAIL = "";
        String SERVICE_DETAIL = "serviceDetail";
        String TITLE = "title";
        String HELP = "Help";
        String PRIVACY = "Privacy and terms";
        String FROM = "from";

        String ADD_BREAK_HOURS = "break hours";
        String ADD_CALLOUT_HOURS = "callout hours";

        String MORE = "moreScreen";
        String ADD_WORKING_HOURS = "workingHours";

        String TYPE = "type";

        String PORTFOLIO_IMAGES = "imageList";
        String LATITUDE = "latitude";
        String LONGITUDE = "longitude";
        String CURRENT_ADDRESS = "isCurrentAddress";
        String CUSTOMER_DETAIL = "cDetail";
        String SERVISE_LIST = "serviceList";
        String EXTRA_DIALOG_ID = "dialogId";
        String DETAIL = "detail";
        String OTHER_IMAGE = "userImage";
        String isAddressFromSetting = "fromSettings_provider";
        String IS_NOTIFICATION_ACTIVE = "isNotificationActive";
        String SHOW_NOTIFICATION_RED_DOT = "showNotificationDot";
        String BOOKING_DATA = "BookingData";
        String BOOKING_TYPE = "bookingType";
        String APPOINTMENT_ID = "bookingId";
        String EDIT_REQUEST_DATA = "editRequestData";
        String ADDRESS = "address";
        String AMOUNT = "amount";
        String BLOCK_NOTIFICATION_APPOINTMENTS_CALLOUOTS = "blockNotifications";//for appointment and callout.
    }

    public interface TIME_DURATIONS {
        int SMALL = 100;
        int MEDIUS = 300;
        int LARGE = 500;
        int EXTRA_LARGE = 3000;
    }

    public interface ClickOperations {
        int MORE_OPRION_CLICK = 1101;
        int ADD_TIME_CLICK = 1102;
        int SERVICE_DETAIL = 1103;
        int SERVICE_DELETE = 1104;
        int APAPTER_BOTTOM_DIALOG_CLICK = 1105;
        int ADD_IMAGE = 1106;

        int ADD_START_TIME = 1107;
        int ADD_END_TIME = 1108;
        int CHECK_APPOINTMENT = 1109;
        int DATE_CLICKED = 1110;
        int CALLOUT_REQUEST = 1111;
        int USER_PROFILE = 1112;
        int DETAILS = 1113;
        int REMOVE = 1114;
        int FULL_IMAGE = 1115;
    }

    public interface Extras {
        String ADD_MOBILE = "addMobile";
        String REGISTER_REQUEST_DATA = "requestModal";
        String COUNTRY_CODE = "countryCode";
        String VERIFIED = "verified_barber";
        String FORGOT_PASSWORD = "fromForgotPassword";
    }

    public interface UserTypes {
        int BARBER = 1;
        int CUSTOMER = 2;
    }

    public interface BookingTypes {
        int APPOINTMENT = 1;
        int CALLOUT = 2;
    }

    public interface BARBER_TYPES {
        int BARBER = 1;
        int CALLOUT_BARBER = 2;
    }

    public interface HOURS {
        String OPENING = "1";
        String CALLOUT = "2";
        String BREAK = "3";
    }

    public interface Currency {
        String EURO = "€";
        String POUNDS = "£";
    }

    public interface ImageTypeKey {
        String PROFILE_IMAGE = "profile";
        String BANNER_IMAGE = "banner";
        String PORTFOLIO_IMAGE = "portfolio";
    }
}
