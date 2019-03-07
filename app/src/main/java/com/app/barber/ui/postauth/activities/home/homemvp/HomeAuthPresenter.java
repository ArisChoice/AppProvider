package com.app.barber.ui.postauth.activities.home.homemvp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.app.barber.R;
import com.app.barber.core.BarberApplication;
import com.app.barber.models.AvailableSlotsModel;
import com.app.barber.models.SpecialisationModel;
import com.app.barber.models.request.AddBlockHoursRequest;
import com.app.barber.models.request.AddServiceModel;
import com.app.barber.models.request.AppointRequestModel;
import com.app.barber.models.request.EditBookingRequestModel;
import com.app.barber.models.request.EntoutRequestModel;
import com.app.barber.models.request.GetFutureStatusRequest;
import com.app.barber.models.request.UpdateBookingRequestModel;
import com.app.barber.models.request.UpdateEditBookingStatusModel;
import com.app.barber.models.request.UpdateRequestStatusModel;
import com.app.barber.models.request.WeekOverViewRequest;
import com.app.barber.models.response.AppointmentsResponseModel;
import com.app.barber.models.response.BaseResponse;
import com.app.barber.models.response.BlockHoursResponse;
import com.app.barber.models.response.BlockedDatesResponse;
import com.app.barber.models.response.BookingStatusResponse;
import com.app.barber.models.response.CheckQbIdResponseModel;
import com.app.barber.models.response.FutureAppointmentStatusModel;
import com.app.barber.models.response.LoginResponseModel;
import com.app.barber.models.response.MyImagesResponseModel;
import com.app.barber.models.response.OverviewResponseModel;
import com.app.barber.models.response.ProfileResponseModel;
import com.app.barber.models.response.RecentStatusResponseModel;
import com.app.barber.models.response.ResponseAvailableSlotsModel;
import com.app.barber.models.response.UpdateDataResponse;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.net.RestCallback;
import com.app.barber.net.RestProcess;
import com.app.barber.net.RestService;
import com.app.barber.util.CommonUtils;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.PreferenceManager;
import com.app.barber.util.di.DaggerValues;
import com.app.barber.util.mvp.BasePresenter;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by harish on 26/10/18.
 */

public class HomeAuthPresenter extends BasePresenter<HomeAuthMVPView> implements RestCallback {
    @Inject
    @Named(DaggerValues.AUTH)
    RestService appService;
    @Inject
    public PreferenceManager mPref;
    Context context;

    public HomeAuthPresenter(Context context) {
        this.context = context;
        BarberApplication.getMyComponent(context).inject(this);
    }

    @Override
    public void attachView(HomeAuthMVPView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public void onFailure(Call call, Throwable t, int serviceMode) {

    }

    @Override
    public void onSuccess(Call call, Response model, int serviceMode) {
        Log.e("on Success", " " + model.body());
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_UPDATE_PROFILE:
                if (((LoginResponseModel) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                } else getMvpView().onSuccessResponse(serviceMode, model.body());
                break;
            case NetworkConstatnts.RequestCode.API_GET_IMAGES:
                if (((MyImagesResponseModel) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_POST_WORKSPACE_IMAGES:
                if (((BaseResponse) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                } else getMvpView().onSuccessResponse(serviceMode, model.body());
                break;
            case NetworkConstatnts.RequestCode.API_GET_PROFILE:
                if (((ProfileResponseModel) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_DASHBOARD_APPOINTMENTS:
                if (((AppointmentsResponseModel) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                } else getMvpView().onfaliurResponse(serviceMode, model.body());
                break;
            case NetworkConstatnts.RequestCode.API_UPCOMMING_APPOINTMENTS:
                if (((AppointmentsResponseModel) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_CHECK_RECENT_STATUS:
                if (((RecentStatusResponseModel) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                } else if (((RecentStatusResponseModel) model.body()).getStatus() == 0) {//when session get expired
                    new CommonUtils().ShowToast(context.getString(R.string.str_session_expired_plesae_relogin));
                    onLogout();
                }
                break;
            case NetworkConstatnts.RequestCode.API_UPDATE_BOOKING_STATUS:
                if (((BookingStatusResponse) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                } else getMvpView().onfaliurResponse(serviceMode, model.body());
                break;
            case NetworkConstatnts.RequestCode.API_UPDATE_CALLOUT_STATUS:
                if (((BookingStatusResponse) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                } else getMvpView().onfaliurResponse(serviceMode, model.body());
                break;
            case NetworkConstatnts.RequestCode.API_CANCELED_APPOINTMENTS:
                if (((AppointmentsResponseModel) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                } else getMvpView().onfaliurResponse(serviceMode, model.body());
                break;
            case NetworkConstatnts.RequestCode.API_CHECK_QB_ID:
                if (((CheckQbIdResponseModel) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_GET_WEEK_OVERVIEW:
                if (((OverviewResponseModel) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_ADD_BLOCK_HOURS:
                if (((BaseResponse) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                } else {
                    getMvpView().onfaliurResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_BLOCKED_HOURS:
                if (((BlockHoursResponse) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_GET_FUTURE_STATUS:
                if (((FutureAppointmentStatusModel) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_UPDATE_FUTURE_STATUS:
                if (((BaseResponse) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_AVAILABLE_SLOTS:
                if (((ResponseAvailableSlotsModel) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                } else {
                    getMvpView().onfaliurResponse(serviceMode, null);
                    new CommonUtils().ShowToast(((ResponseAvailableSlotsModel) model.body()).getMessage());
                }
                break;
            case NetworkConstatnts.RequestCode.API_EDIT_BOOKING_REQUEST:
                if (((BaseResponse) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_EDIT_BOOKING_STATUS:
                if (((BaseResponse) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_DELETE_IMAGE:
                if (((BaseResponse) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_NOTIFY_ENROUT:
                if (((BaseResponse) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_GET_FUTURE_BLOCK_STATUS:
                if (((BlockedDatesResponse) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
        }

    }

    @Override
    public void onLogout() {
        new CommonUtils().LogoutUser();

    }


    public void saveUserData(LoginResponseModel.UserBean user) {
        mPref.setUserData(new Gson().toJson(user));
        mPref.setPrefrencesBoolean(GlobalValues.KEYS.isLogedIn, true);
        Log.e(" presenter ", "" + new Gson().toJson(user));
    }

    public void updateBarberType(int apiUpdateBarberType, String oType, boolean b) {
        Call<UpdateDataResponse> call = appService.updateBarberType(oType);
        call.enqueue(new RestProcess<UpdateDataResponse>(apiUpdateBarberType, this, context, b));
    }

    public void updateSpecType(int apiUpdateBarberType, String oType, boolean b) {
        Call<UpdateDataResponse> call = appService.updateSpecType(oType);
        call.enqueue(new RestProcess<UpdateDataResponse>(apiUpdateBarberType, this, context, b));
    }

    public ArrayList<SpecialisationModel> getSpecialisationList() {
        ArrayList<SpecialisationModel> list = new ArrayList<>();
        SpecialisationModel model = new SpecialisationModel();
        model.setName(context.getString(R.string.str_afro_caribbean));
        model.setId("1");
        model.setSelected(false);
        list.add(model);
        model = new SpecialisationModel();
        model.setName(context.getString(R.string.str_asian_hair));
        model.setId("2");
        model.setSelected(false);
        list.add(model);
        model = new SpecialisationModel();
        model.setName(context.getString(R.string.str_caucasian));
        model.setId("3");
        model.setSelected(false);
        list.add(model);
        return list;
    }

    public void addService(int apiAddService, AddServiceModel addModel, boolean b) {
        Call<UpdateDataResponse> call = appService.addService(addModel);
        call.enqueue(new RestProcess<UpdateDataResponse>(apiAddService, this, context, b));
    }


    public void updateProfileRequest(int updateProfile, Map<String, RequestBody> params, MultipartBody.Part partData, boolean b) {
        Call<LoginResponseModel> call = appService.uploadProfile(params, partData);
        call.enqueue(new RestProcess<LoginResponseModel>(updateProfile, this, context, b));
    }

    public void getMyStyleList(int apiGetImages, Object o, boolean b) {
        Call<MyImagesResponseModel> call = appService.getMyImages();
        call.enqueue(new RestProcess<MyImagesResponseModel>(apiGetImages, this, context, b));
    }

    public void postImages(int apiPostWorkspaceImages, Map<String, RequestBody> params, List<MultipartBody.Part> list, boolean b) {
        Call<BaseResponse> call = appService.uploadImage(list, params);
        call.enqueue(new RestProcess<BaseResponse>(apiPostWorkspaceImages, this, context, b));
    }

    public void getUserProfile(int apiGetProfile, boolean b) {
        Call<ProfileResponseModel> call = appService.getProfile();
        call.enqueue(new RestProcess<ProfileResponseModel>(apiGetProfile, this, context, b));
    }

    public void getActiveAppointments(int apiDashboardAppointments, Object o, boolean b) {
        Call<AppointmentsResponseModel> call = appService.getActiveAppointments();
        call.enqueue(new RestProcess<AppointmentsResponseModel>(apiDashboardAppointments, this, context, b));
    }

    public void getUpcomingAppointments(int apiUpcommingAppointments, AppointRequestModel fullDate, boolean b) {
        Call<AppointmentsResponseModel> call = appService.getUpcomingAppointments(fullDate);
        call.enqueue(new RestProcess<AppointmentsResponseModel>(apiUpcommingAppointments, this, context, b));
    }

    public void checkRecentStatus(int apiCheckRecentStatus, Object o, boolean b) {
        Call<RecentStatusResponseModel> call = appService.checkRecentStatus();
        call.enqueue(new RestProcess(apiCheckRecentStatus, this, context, b));
    }

    public void updateBookingStatus(int apiUpdateBookingStatus, UpdateBookingRequestModel bStatsu, boolean b) {
        Call<BookingStatusResponse> call = null;
        switch (apiUpdateBookingStatus) {
            case NetworkConstatnts.RequestCode.API_UPDATE_BOOKING_STATUS:
                call = appService.updateBookingStatus(bStatsu);
                break;
            case NetworkConstatnts.RequestCode.API_UPDATE_CALLOUT_STATUS:
                call = appService.updateCalloutStatus(bStatsu);
                break;
        }

        call.enqueue(new RestProcess(apiUpdateBookingStatus, this, context, b));
    }

    public void getCanceledAppointments(int apiCanceledAppointments, AppointRequestModel requestAppointment, boolean b) {
        Call<AppointmentsResponseModel> call = appService.getCanceledAppointments(requestAppointment);
        call.enqueue(new RestProcess<>(apiCanceledAppointments, this, context, b));
    }

    public void checkQbId(int apiCheckQbId, Object o, boolean b) {
        Call<CheckQbIdResponseModel> call = appService.chcekQbId();
        call.enqueue(new RestProcess<>(apiCheckQbId, this, context, b));
    }

  /*  public void initQbUser(LoginResponseModel.UserBean userData) {
        Performer<QBUser> ussr = QBUsers.getUser(Integer.parseInt(userData.getQBId()));
        ussr.performAsync(new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser qbUser, Bundle bundle) {
                final QBUser user = qbUser;
                user.setPassword(NetworkConstatnts.QB.GLOBAL_PASSWORD);
                QBUsers.signIn(user).performAsync(new QBEntityCallback<QBUser>() {
                    @Override
                    public void onSuccess(QBUser qbUser, Bundle bundle) {
//                callback.onSuccess(qbUser, bundle);
                        Log.d(" loginToQb ", "  onSuccess  " + qbUser.getId());

                        qbUser.setPassword(NetworkConstatnts.QB.GLOBAL_PASSWORD);
                        Log.d(" loginToQb ", "  onSuccess  " + qbUser.getPassword());
                        SubscribeToNotification.getInstance().setSubscribeToNotification(context);
                        PreferenceManager.getInstance(context).saveQbUser(qbUser);

                    }

                    @Override
                    public void onError(QBResponseException e) {
                        Log.d(" loginToQb ", "  onError " + e.getLocalizedMessage());
                    }
                });
            }

            @Override
            public void onError(QBResponseException e) {
                Log.d(" loginToQb ", "  onError " + e.getLocalizedMessage());
            }
        });
    }*/

    public void getWeekOverView(int apiGetWeekOverview, WeekOverViewRequest wekRequest, boolean b) {
        Call<OverviewResponseModel> call = appService.getOverView(wekRequest);
        call.enqueue(new RestProcess<>(apiGetWeekOverview, this, context, b));
    }

    public boolean isAnyThingRequired(RecentStatusResponseModel.ResponseBean responseData, LoginResponseModel.UserBean userData) {
        if (!responseData.getValidations().isAddressAdded()) {
            return true;
        }
        if (!responseData.getValidations().isServiceAdded()) {
            return true;
        }
        if (!responseData.getValidations().isPaymentTypeAdded()) {
            return true;
        }
        if (!responseData.getValidations().isBarberTypeAdded()) {
            return true;
        }
        if (!responseData.getValidations().isBarberTypeAdded()) {
            return true;
        }
        /*if (!responseData.getValidations().isStripeConnected()) {
//            if (userData.getPaymentType() != null && userData.getPaymentType().contains("1") && userData.getPaymentType().contains("3"))
            return true;
        }*/
        if (!responseData.getValidations().isOpeningHoursAdded()) {
            if (userData.getBarberType() != null && userData.getBarberType().contains("1"))
                return true;
        }
        if (!responseData.getValidations().isCallOutAdded()) {
            if (userData.getBarberType() != null && userData.getBarberType().contains("2"))
                return true;
        }
        if (!responseData.getValidations().isDistrictAdded()) {
            if (userData.getBarberType() != null && userData.getBarberType().contains("2"))
                return true;
        }
        if (!responseData.getValidations().isServiceAdded()) {
            return true;
        }
        if (!responseData.getValidations().isZeroAmountServiceForNonTrainee()) {
            if (userData.getBarberType() != null && !userData.getBarberType().contains("3"))
                return true;
        }
        return false;
    }

    public void getBlockedHours(int apiCanceledAppointments, AppointRequestModel requestAppointment, boolean b) {
        Call<BlockHoursResponse> call = appService.getBlockHours(requestAppointment);
        call.enqueue(new RestProcess<>(apiCanceledAppointments, this, context, b));
    }

    public void saveBlockHours(int apiAddBlockHours, AddBlockHoursRequest adRequest, boolean b) {
        Call<BaseResponse> call = appService.addBlockHours(adRequest);
        call.enqueue(new RestProcess<>(apiAddBlockHours, this, context, b));
    }

    public void deleteBlockHours(int apiDeleteBlockHours, int id, boolean b) {
        Call<BaseResponse> call = appService.deleteBlockHour(id);
        call.enqueue(new RestProcess<>(apiDeleteBlockHours, this, context, b));
    }

    public void updateBlueDotStatus(int apiUpdateTodayAppointmentStatus, boolean b) {
        Call<BaseResponse> call = appService.updateTodaysAppointmentStatus();
        call.enqueue(new RestProcess<>(apiUpdateTodayAppointmentStatus, this, context, b));
    }

    public static boolean check(final int n) {
        int m = Math.abs(n);
        while (m > 0) {
            if (m % 10 == 7)
                return true;
            m /= 10;
        }
        return n % 7 == 0;
    }

    public static boolean checkNew(final int n) {
        int m = Math.abs(n);
        while (m > 0) {
            if (m % 10 == 10)
                return true;
            m /= 10;
        }
        return n % 10 == 0;
    }

    public void getFutureDatesStatus(int apiGetFutureStatus, GetFutureStatusRequest sRequest, boolean b) {
        Call<FutureAppointmentStatusModel> call = appService.getFutureStatus(sRequest);
        call.enqueue(new RestProcess<>(apiGetFutureStatus, this, context, b));
    }

    public void updateDataStatus(int apiUpdateFutureStatus, UpdateRequestStatusModel fullDate, boolean b) {
        Call<BaseResponse> call = appService.updateFutureStatus(fullDate);
        call.enqueue(new RestProcess<>(apiUpdateFutureStatus, this, context, b));
    }

    public void getAvailableSlots(int apiAvailableSlots, AvailableSlotsModel avaialbeModel, boolean b) {
        Call<ResponseAvailableSlotsModel> call = appService.getAvailableSlots(avaialbeModel);
        call.enqueue(new RestProcess(apiAvailableSlots, this, context, b));
    }

    public void editBookingRequest(int apiEditBookingRequest, EditBookingRequestModel editRequestDataModel, boolean b) {
        Call<BaseResponse> call = appService.editBookingRequest(editRequestDataModel);
        call.enqueue(new RestProcess(apiEditBookingRequest, this, context, b));
    }

    public void updateEditStatus(int apiEditBookingStatus, UpdateEditBookingStatusModel updateEditBookingStatusModel, boolean b) {
        Call<BaseResponse> call = appService.editBookingStatus(updateEditBookingStatusModel);
        call.enqueue(new RestProcess<>(apiEditBookingStatus, this, context, b));
    }

    public void deleteImage(int apiDeleteImage, String mItem1, boolean b) {
        Call<BaseResponse> call = appService.deleteUserImage(mItem1);
        call.enqueue(new RestProcess<>(apiDeleteImage, this, context, b));
    }

    public void notiFyEnrout(int apiNotifyEnrout, EntoutRequestModel eModel, boolean b) {
        Call<BaseResponse> call = appService.notifyEnrout(eModel);
        call.enqueue(new RestProcess<>(apiNotifyEnrout, this, context, b));
    }

    public void getFutureDatesBlockHoursStatus(int apiGetFutureBlockStatus, GetFutureStatusRequest sRequest, boolean b) {
        Call<BlockedDatesResponse> call = appService.getBlockDates(sRequest);
        call.enqueue(new RestProcess<>(apiGetFutureBlockStatus, this, context, b));
    }
}
