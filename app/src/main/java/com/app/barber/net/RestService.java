package com.app.barber.net;

import com.app.barber.models.AvailableSlotsModel;
import com.app.barber.models.ContactModel;
import com.app.barber.models.request.AddAppointmentRequest;
import com.app.barber.models.request.AddBlockHoursRequest;
import com.app.barber.models.request.AddServiceModel;
import com.app.barber.models.request.AppointRequestModel;
import com.app.barber.models.request.BookPaymentRequestModel;
import com.app.barber.models.request.ChangePasswordRequest;
import com.app.barber.models.request.EditBookingRequestModel;
import com.app.barber.models.request.EntoutRequestModel;
import com.app.barber.models.request.GetFutureStatusRequest;
import com.app.barber.models.request.HoursModel;
import com.app.barber.models.request.LoginRequestModel;
import com.app.barber.models.request.PayAmountRequest;
import com.app.barber.models.request.ReferalRequest;
import com.app.barber.models.request.RegisterRequestModel;
import com.app.barber.models.request.SaveDistrictRequest;
import com.app.barber.models.request.SaveQbDialogRequestModel;
import com.app.barber.models.request.SettingChageRequest;
import com.app.barber.models.request.UpdateAddressRequestModel;
import com.app.barber.models.request.UpdateBookingRequestModel;
import com.app.barber.models.request.UpdateChatDialogRequest;
import com.app.barber.models.request.UpdateEditBookingStatusModel;
import com.app.barber.models.request.UpdateRequestStatusModel;
import com.app.barber.models.request.ValidatePhoneNumberModel;
import com.app.barber.models.request.WeekOverViewRequest;
import com.app.barber.models.response.AddressListResponseModel;
import com.app.barber.models.response.AdvanceBookingTimeResponse;
import com.app.barber.models.response.AppointmentsResponseModel;
import com.app.barber.models.response.BarberStatsResponse;
import com.app.barber.models.response.BaseResponse;
import com.app.barber.models.response.BlockHoursResponse;
import com.app.barber.models.response.BlockedDatesResponse;
import com.app.barber.models.response.BookingStatusResponse;
import com.app.barber.models.response.CardListResponse;
import com.app.barber.models.response.ChatUsersResponseModel;
import com.app.barber.models.response.CheckQbIdResponseModel;
import com.app.barber.models.response.ClientsListResponseModel;
import com.app.barber.models.response.ConversationResponseModel;
import com.app.barber.models.response.CustomerBookingResponseModel;
import com.app.barber.models.response.FutureAppointmentStatusModel;
import com.app.barber.models.response.GraphResponsewModel;
import com.app.barber.models.response.LoginResponseModel;
import com.app.barber.models.response.MyImagesResponseModel;
import com.app.barber.models.response.NotificationResponseModel;
import com.app.barber.models.response.OverviewResponseModel;
import com.app.barber.models.response.PaymentHistoryResponse;
import com.app.barber.models.response.ProfileResponseModel;
import com.app.barber.models.response.RecentStatusResponseModel;
import com.app.barber.models.response.ResponseAvailableSlotsModel;
import com.app.barber.models.response.ServiceListResponseModel;
import com.app.barber.models.response.SettingStatusResponse;
import com.app.barber.models.response.SpecialisationResponseModel;
import com.app.barber.models.response.UpdateDataResponse;
import com.app.barber.models.response.ValidationResponseModel;
import com.app.barber.models.response.ZoneDistrictResponseModel;
import com.app.barber.models.response.ZoneResponseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Created by Harish on 2/3/18.
 */
public interface RestService {
    @POST(NetworkConstatnts.API.loginUser)
    Call<LoginResponseModel> loginUser(@Body LoginRequestModel requestData);

    @POST(NetworkConstatnts.API.registerUser)
    Call<BaseResponse> registerUser(@Body RegisterRequestModel registerRequest);

    @POST(NetworkConstatnts.API.updateBarberType)
    Call<UpdateDataResponse> updateBarberType(@Query("data") String typs);

    @POST(NetworkConstatnts.API.updateSpecialisationType)
    Call<UpdateDataResponse> updateSpecType(@Query("data") String typs);

    @POST(NetworkConstatnts.API.updateAddress)
    Call<UpdateDataResponse> updateUserAddress(@Body UpdateAddressRequestModel addressModel);

    @POST(NetworkConstatnts.API.updateOpeningTime)
    Call<UpdateDataResponse> updateOpeningTime(@Body HoursModel.WorkingHours addressModel);

    @POST(NetworkConstatnts.API.updtaeCalloutHours)
    Call<UpdateDataResponse> updateCalloutTime(@Body HoursModel.WorkingHours addressModel);

    @POST(NetworkConstatnts.API.updateBreakHours)
    Call<UpdateDataResponse> updateBreakTime(@Body HoursModel.WorkingHours addressModel);

    @POST(NetworkConstatnts.API.addService)
    Call<UpdateDataResponse> addService(@Body AddServiceModel addModel);

    @GET(NetworkConstatnts.API.getServices)
    Call<ServiceListResponseModel> getService();

    @Multipart
    @POST(NetworkConstatnts.API.ADD_WORKPLACE_PHOTOS)
    Call<BaseResponse> uploadImage(@Part List<MultipartBody.Part> list, @PartMap() Map<String, RequestBody> params);

    @Multipart
    @POST(NetworkConstatnts.API.UPDATE_PROFILE)
    Call<LoginResponseModel> uploadProfile(@PartMap() Map<String, RequestBody> params, @Part MultipartBody.Part partData);

    @POST(NetworkConstatnts.API.validateNumber)
    Call<BaseResponse> verifyNumber(@Body RegisterRequestModel validateRequest);

    @GET(NetworkConstatnts.API.getMyImages)
    Call<MyImagesResponseModel> getMyImages();

    @GET(NetworkConstatnts.API.getHours)
    Call<UpdateDataResponse> getHours(@Query("type") String typs);

    @GET(NetworkConstatnts.API.GET_SPECIALISATION)
    Call<SpecialisationResponseModel> getSpecialisationList();

    @POST(NetworkConstatnts.API.updatePaymentType)
    Call<UpdateDataResponse> updatePaymentType(@Query("data") int paymentType);

    @GET(NetworkConstatnts.API.GET_PROFILE)
    Call<ProfileResponseModel> getProfile();

    @POST(NetworkConstatnts.API.GET_ACTIVE_APPOINTMENTS)
    Call<AppointmentsResponseModel> getActiveAppointments();

    @POST(NetworkConstatnts.API.GET_UPCOMING_APPOINTMENTS)
    Call<AppointmentsResponseModel> getUpcomingAppointments(@Body AppointRequestModel fullDate);

    @POST(NetworkConstatnts.API.GET_NOTIFIACTIONS_LIST)
    Call<NotificationResponseModel> getNotificationsList();

    @GET(NetworkConstatnts.API.GET_RECENT_STATUS)
    Call<RecentStatusResponseModel> checkRecentStatus();

    @POST(NetworkConstatnts.API.forgotPassword)
    Call<BaseResponse> forgotPass(@Body ChangePasswordRequest pRequest);

    @POST(NetworkConstatnts.API.UPDATE_BOOKING_STATUS)
    Call<BookingStatusResponse> updateBookingStatus(@Body UpdateBookingRequestModel bStatsu);

    @POST(NetworkConstatnts.API.UPDATE_CALLOUT_STATUS)
    Call<BookingStatusResponse> updateCalloutStatus(@Body UpdateBookingRequestModel bStatsu);

    @POST(NetworkConstatnts.API.GET_CANCELED_APPOINTMENTS)
    Call<AppointmentsResponseModel> getCanceledAppointments(@Body AppointRequestModel requestAppointment);

    @GET(NetworkConstatnts.API.UPDATE_NOTIFICATION_STATUS)
    Call<BaseResponse> updateNotificationStatus(@Query("Id") String o, @Query("Type") String s);

    @Multipart
    @POST(NetworkConstatnts.API.ADD_CLIENT)
    Call<BaseResponse> addClient(@PartMap() Map<String, RequestBody> params, @Part MultipartBody.Part partData);

    @POST(NetworkConstatnts.API.GET_CLIENTS)
    Call<ClientsListResponseModel> getClients();

    @POST(NetworkConstatnts.API.ADD_CLIENT_APPOINTMENT)
    Call<BaseResponse> addAppointment(@Body AddAppointmentRequest aRequest);

    @GET(NetworkConstatnts.API.GET_CHAT_USERS)
    Call<ChatUsersResponseModel> getChatUsers(@Query("Type") int barber);

    @DELETE(NetworkConstatnts.API.DELETE_SERVICE)
    Call<BaseResponse> removeService(@Query("Id") int id);

    @POST(NetworkConstatnts.API.validateMobileNumber)
    Call<ValidationResponseModel> validateNumber(@Body ValidatePhoneNumberModel validateRequest);

    @GET(NetworkConstatnts.API.CHECK_QB_ID)
    Call<CheckQbIdResponseModel> chcekQbId();

    @POST(NetworkConstatnts.API.UPDATE_CHAT_DIALOG_ID)
    Call<BaseResponse> updateChatDialog(@Body UpdateChatDialogRequest cRequest);

    @DELETE(NetworkConstatnts.API.REMOVE_BREAK_HOUR)
    Call<BaseResponse> removeBreakHour(@Query("Id") String s);

    @POST(NetworkConstatnts.API.GET_WEEK_OVERVIEW)
    Call<OverviewResponseModel> getOverView(@Body WeekOverViewRequest wekRequest);

    @GET(NetworkConstatnts.API.GET_ZONES)
    Call<ZoneResponseModel> getZoneList();

    @GET(NetworkConstatnts.API.GET_ZONES_DISTRICTS)
    Call<ZoneDistrictResponseModel> getZoneDistricList(@Query("zoneId") int i);

    @POST(NetworkConstatnts.API.SAVE_SERVING_DISTRIC)
    Call<BaseResponse> saveServingDistrict(@Body SaveDistrictRequest saveDistrictRequest);

    @POST(NetworkConstatnts.API.SAVE_ADVANCE_BOOKING_TIME)
    Call<BaseResponse> saveAdavanceBookingTime(@Query("time") String time);

    @GET(NetworkConstatnts.API.GET_SAVED_ADVANCE_TIME)
    Call<AdvanceBookingTimeResponse> getAdavanceBookingTime();

    @GET(NetworkConstatnts.API.GET_BARBER_STATS)
    Call<BarberStatsResponse> getBarberStats(@Query("Type") String type);

    @POST(NetworkConstatnts.API.ADD_BLOCK_HOURS)
    Call<BaseResponse> addBlockHours(@Body AddBlockHoursRequest adRequest);

    @POST(NetworkConstatnts.API.GET_BLOCK_HOURS)
    Call<BlockHoursResponse> getBlockHours(@Body AppointRequestModel requestAppointment);

    @DELETE(NetworkConstatnts.API.DELETE_BREAK_HOUR)
    Call<BaseResponse> deleteBlockHour(@Query("Id") int id);

    @POST(NetworkConstatnts.API.ADD_MOBILE_CONTACTS)
    Call<BaseResponse> addMobileContacts(@Body ArrayList<ContactModel> selectedContacts);

    @GET(NetworkConstatnts.API.GET_PAYMENT_HISTORY_IN)
    Call<PaymentHistoryResponse> getPayments();

    @POST(NetworkConstatnts.API.UPDATE_TODAYS_BOOKING_STATUS)
    Call<BaseResponse> updateTodaysAppointmentStatus();

    @GET(NetworkConstatnts.API.GET_PAYMENT_HISTORY_OUT)
    Call<PaymentHistoryResponse> getPaymentsOut();

    @POST(NetworkConstatnts.API.SAVE_QB_DIALOG)
    Call<BaseResponse> saveDialogDetails(@Body SaveQbDialogRequestModel dialogId);

    @POST(NetworkConstatnts.API.GET_FUTURRE_BOOKING_STATUS)
    Call<FutureAppointmentStatusModel> getFutureStatus(@Body GetFutureStatusRequest sRequest);

    @POST(NetworkConstatnts.API.UPDTAE_FUTUTE_DATE_STATUS)
    Call<BaseResponse> updateFutureStatus(@Body UpdateRequestStatusModel fullDate);

    @POST(NetworkConstatnts.API.getTimeSlots)
    Call<ResponseAvailableSlotsModel> getAvailableSlots(@Body AvailableSlotsModel avaialbeModel);

    @POST(NetworkConstatnts.API.EDIT_BOOKING_REQUEST)
    Call<BaseResponse> editBookingRequest(@Body EditBookingRequestModel editRequestDataModel);

    @POST(NetworkConstatnts.API.UPDATE_EDIT_REQUEST_STATUS)
    Call<BaseResponse> editBookingStatus(@Body UpdateEditBookingStatusModel updateEditBookingStatusModel);

    @GET(NetworkConstatnts.API.GET_GRAPH_DATA)
    Call<GraphResponsewModel> getGraphData(@Query("type") String today);


    @GET(NetworkConstatnts.API.GET_MY_ADDRESS)
    Call<AddressListResponseModel> getMyAddress();

    @GET(NetworkConstatnts.API.GET_CARD_LIST)
    Call<CardListResponse> getSavedCards();

    @DELETE(NetworkConstatnts.API.DELETE_CARD)
    Call<BaseResponse> deleteCard(@Query("cardId") String id);

    @POST(NetworkConstatnts.API.SAVE_CARD)
    Call<BaseResponse> saveCard(@Body BookPaymentRequestModel bRequest);

    @POST(NetworkConstatnts.API.PAY_DUE_AMOUNT)
    Call<BaseResponse> payDueAmount(@Body PayAmountRequest pRequest);

    @POST(NetworkConstatnts.API.GET_REFERER_REWARD)
    Call<BaseResponse> applyRefere(@Query("code") String rRequest);

    @GET(NetworkConstatnts.API.GET_CLIENT_BOOKINGS)
    Call<CustomerBookingResponseModel> getClientBookings(@Query("Id") int clientId);

    @DELETE(NetworkConstatnts.API.DELETE_IMAGE)
    Call<BaseResponse> deleteUserImage(@Query("Id") String mItem1);

    @POST(NetworkConstatnts.API.NOTIFY_ENTOUT)
    Call<BaseResponse> notifyEnrout(@Body EntoutRequestModel eModel);

    @POST(NetworkConstatnts.API.GET_BLOCK_DATES)
    Call<BlockedDatesResponse> getBlockDates(@Body GetFutureStatusRequest sRequest);

    @POST(NetworkConstatnts.API.CHANGE_SETTINGS)
    Call<BaseResponse> changeSettings(@Body SettingChageRequest cRequest);

    @GET(NetworkConstatnts.API.GET_SETTING_STATUS)
    Call<SettingStatusResponse> getSettingStatus();

    @GET(NetworkConstatnts.API.GET_CONVERSATION_LIST)
    Call<ConversationResponseModel> getConversationList(@Query("DialogId") String o, @Query("UserId") String userID, @Query("UserToDeliver") String otherUserId);

    @GET(NetworkConstatnts.API.VERIFY_OTP)
    Call<BaseResponse> verifyOtp(@Query("Phone") String phone, @Query("OTP") String otp, @Query("UserType") int type);
}