package com.app.barber.ui.postauth.activities.settings.settingmvp;

import android.content.Context;
import android.util.Log;

import com.app.barber.R;
import com.app.barber.core.BarberApplication;
import com.app.barber.models.SpecialisationModel;
import com.app.barber.models.request.AddServiceModel;
import com.app.barber.models.request.AppointRequestModel;
import com.app.barber.models.request.BookPaymentRequestModel;
import com.app.barber.models.request.PayAmountRequest;
import com.app.barber.models.request.ReferalRequest;
import com.app.barber.models.request.SettingChageRequest;
import com.app.barber.models.response.AddressListResponseModel;
import com.app.barber.models.response.AdvanceBookingTimeResponse;
import com.app.barber.models.response.AppointmentsResponseModel;
import com.app.barber.models.response.BarberStatsResponse;
import com.app.barber.models.response.BaseResponse;
import com.app.barber.models.response.CardListResponse;
import com.app.barber.models.response.GraphResponsewModel;
import com.app.barber.models.response.LoginResponseModel;
import com.app.barber.models.response.MyImagesResponseModel;
import com.app.barber.models.response.NotificationResponseModel;
import com.app.barber.models.response.PaymentHistoryResponse;
import com.app.barber.models.response.ProfileResponseModel;
import com.app.barber.models.response.SettingStatusResponse;
import com.app.barber.models.response.UpdateDataResponse;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.net.RestCallback;
import com.app.barber.net.RestProcess;
import com.app.barber.net.RestService;
import com.app.barber.ui.postauth.activities.home.homemvp.HomeAuthMVPView;
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

public class SettingPresenter extends BasePresenter<SettingMVPView> implements RestCallback {
    @Inject
    @Named(DaggerValues.AUTH)
    RestService appService;
    @Inject
    public PreferenceManager mPref;
    Context context;

    public SettingPresenter(Context context) {
        this.context = context;
        BarberApplication.getMyComponent(context).inject(this);
    }

    @Override
    public void attachView(SettingMVPView mvpView) {
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
                }
                break;
            case NetworkConstatnts.RequestCode.API_GET_IMAGES:
                if (((MyImagesResponseModel) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_NOTIFICATION_LIST:
                if (((NotificationResponseModel) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_UPDATE_NOTIFICATION_STATUS:
                if (((BaseResponse) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_ADVANCE_BOOKING_TIME:
                if (((BaseResponse) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_GET_ADVANCE_BOOKING_TIME:
                if (((AdvanceBookingTimeResponse) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_GET_BARBER_STATS:
                if (((BarberStatsResponse) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_GET_PAYMENT_HISTORY_IN:
                if (((PaymentHistoryResponse) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_GET_PAYMENT_HISTORY_OUT:
                if (((PaymentHistoryResponse) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_GET_GRAPH_DATA:
                if (((GraphResponsewModel) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_GET_USER_ADDRESS:
                if (((AddressListResponseModel) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_GET_SAVED_CARDS:
                if (((CardListResponse) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                } else {
                    getMvpView().onfaliurResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_SAVE_CARD:
                if (((BaseResponse) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_PAY_DUE_AMOUNT:
                if (((BaseResponse) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                } else getMvpView().onfaliurResponse(serviceMode, model.body());
                break;
            case NetworkConstatnts.RequestCode.API_APPLY_REFERAL:
                if (((BaseResponse) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                } else getMvpView().onfaliurResponse(serviceMode, model.body());
                break;
            case NetworkConstatnts.RequestCode.API_CHANGE_SETTING:
                if (((BaseResponse) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                } else getMvpView().onfaliurResponse(serviceMode, model.body());
                break;
            case NetworkConstatnts.RequestCode.API_GET_SETTINGS_STATUS:
                if (((SettingStatusResponse) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                } else getMvpView().onfaliurResponse(serviceMode, model.body());
                break;

        }
    }

    @Override
    public void onLogout() {

    }

    public void getAllNotifications(int apiNotificationList, Object o, boolean b) {
        Call<NotificationResponseModel> call = appService.getNotificationsList();
        call.enqueue(new RestProcess(apiNotificationList, this, context, b));
    }

    public void updateNoificationStatus(int apiUpdateNotificationStatus, String o, boolean b) {
        Call<BaseResponse> call = appService.updateNotificationStatus(o, ""+GlobalValues.UserTypes.BARBER);
        call.enqueue(new RestProcess(apiUpdateNotificationStatus, this, context, b));
    }

    public void saveAdvanceBookingTime(int apiAdvanceBookingTime, String time, boolean b) {
        Call<BaseResponse> call = appService.saveAdavanceBookingTime(time);
        call.enqueue(new RestProcess(apiAdvanceBookingTime, this, context, b));
    }

    public String getValidMinutes(String tim) {
        Log.d("getValidMinutes ", " " + tim.split("\\s+")[1]);
        if (tim.split("\\s+")[1].contains("Minutes"))
            return tim.split("\\s+")[0];
        else {
            String hrsTxt = tim.split("\\s+")[0];
            int totalMin = Integer.parseInt(hrsTxt) * 60;
            return String.valueOf(totalMin);
        }
    }

    public void getAdvanceBookingTime(int apiGetAdvanceBookingTime, Object o, boolean b) {
        Call<AdvanceBookingTimeResponse> call = appService.getAdavanceBookingTime();
        call.enqueue(new RestProcess(apiGetAdvanceBookingTime, this, context, b));
    }

    public void getBarberStats(int apiGetBarberStats, String oType, boolean b) {
        Call<BarberStatsResponse> call = appService.getBarberStats(oType);
        call.enqueue(new RestProcess(apiGetBarberStats, this, context, b));
    }

    public void getPaymentHistory(int apiGetPaymentHistory, Object o, boolean b) {
        switch (apiGetPaymentHistory) {
            case NetworkConstatnts.RequestCode.API_GET_PAYMENT_HISTORY_IN:
                Call<PaymentHistoryResponse> call = appService.getPayments();
                call.enqueue(new RestProcess(apiGetPaymentHistory, this, context, b));
                break;
            case NetworkConstatnts.RequestCode.API_GET_PAYMENT_HISTORY_OUT:
                Call<PaymentHistoryResponse> call1 = appService.getPaymentsOut();
                call1.enqueue(new RestProcess(apiGetPaymentHistory, this, context, b));
                break;
        }
    }

    public void getGraphData(int apiGetGraphData, String today, boolean b) {
        Call<GraphResponsewModel> call = appService.getGraphData(today);
        call.enqueue(new RestProcess(apiGetGraphData, this, context, b));
    }

    public void getMyAddress(int apiGetUserAddress, Object o, boolean b) {
        Call<AddressListResponseModel> call = appService.getMyAddress();
        call.enqueue(new RestProcess<>(apiGetUserAddress, this, context, b));
    }

    public void getSavedCard(int apiGetSavedCards, Object o, boolean b) {
        Call<CardListResponse> call = appService.getSavedCards();
        call.enqueue(new RestProcess(apiGetSavedCards, this, context, b));
    }

    public void removeCard(int apiRemoveSavedCard, String id, boolean b) {
        Call<BaseResponse> call = appService.deleteCard(id);
        call.enqueue(new RestProcess(apiRemoveSavedCard, this, context, b));
    }

    public void saveCardDetail(int apiSaveCard, BookPaymentRequestModel bRequest, boolean b) {
        Call<BaseResponse> call = appService.saveCard(bRequest);
        call.enqueue(new RestProcess(apiSaveCard, this, context, b));
    }

    public void pauDueAmount(int apiPayDueAmount, PayAmountRequest pRequest, boolean b) {
        Call<BaseResponse> call = appService.payDueAmount(pRequest);
        call.enqueue(new RestProcess(apiPayDueAmount, this, context, b));
    }

    public void appReferer(int apiApplyReferal, ReferalRequest rRequest, boolean b) {
        Call<BaseResponse> call = appService.applyRefere(rRequest.getCode());
        call.enqueue(new RestProcess(apiApplyReferal, this, context, b));
    }

    public void updateSettingStatus(int apiChangeSetting, SettingChageRequest cRequest, boolean b) {
        Call<BaseResponse> call = appService.changeSettings(cRequest);
        call.enqueue(new RestProcess(apiChangeSetting, this, context, b));
    }

    public void getSettingStatus(int apiGetSettingsStatus, Object o, boolean b) {
        Call<SettingStatusResponse> call = appService.getSettingStatus();
        call.enqueue(new RestProcess(apiGetSettingsStatus, this, context, b));
    }
}
