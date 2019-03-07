package com.app.barber.ui.postauth.activities.client.addclientmvp;

import android.content.Context;
import android.util.Log;

import com.app.barber.R;
import com.app.barber.core.BarberApplication;
import com.app.barber.models.ContactModel;
import com.app.barber.models.SpecialisationModel;
import com.app.barber.models.request.AddAppointmentRequest;
import com.app.barber.models.request.AddServiceModel;
import com.app.barber.models.request.AppointRequestModel;
import com.app.barber.models.request.SaveQbDialogRequestModel;
import com.app.barber.models.request.UpdateBookingRequestModel;
import com.app.barber.models.response.AppointmentsResponseModel;
import com.app.barber.models.response.BaseResponse;
import com.app.barber.models.response.BookingStatusResponse;
import com.app.barber.models.response.ClientsListResponseModel;
import com.app.barber.models.response.CustomerBookingResponseModel;
import com.app.barber.models.response.LoginResponseModel;
import com.app.barber.models.response.MyImagesResponseModel;
import com.app.barber.models.response.ProfileResponseModel;
import com.app.barber.models.response.RecentStatusResponseModel;
import com.app.barber.models.response.ServiceListResponseModel;
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

public class AddClientPresenter extends BasePresenter<AddClientMVPView> implements RestCallback {
    @Inject
    @Named(DaggerValues.AUTH)
    RestService appService;
    @Inject
    public PreferenceManager mPref;
    Context context;

    public AddClientPresenter(Context context) {
        this.context = context;
        BarberApplication.getMyComponent(context).inject(this);
    }

    @Override
    public void attachView(AddClientMVPView mvpView) {
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
        Log.d("on Success", " " + model.body());
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_ADD_CLIENT:
                if (((BaseResponse) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_LIST_CUSTOMERS:
                if (((ClientsListResponseModel) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_GET_SERVICE:
                if (((ServiceListResponseModel) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_ADD_CLIENT_APPOINTMENT:
                if (((BaseResponse) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                } else getMvpView().onfaliurResponse(serviceMode, model.body());
                break;
            case NetworkConstatnts.RequestCode.API_ADD_MOBILE_CONTACTS:
                if (((BaseResponse) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_GET_CLIENT_BOOKINGS:
                if (((CustomerBookingResponseModel) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;

        }

    }

    @Override
    public void onLogout() {

    }


    public void saveUserData(LoginResponseModel.UserBean user) {
        mPref.setUserData(new Gson().toJson(user));
        mPref.setPrefrencesBoolean(GlobalValues.KEYS.isLogedIn, true);
        Log.d(" presenter ", "" + new Gson().toJson(user));
    }


    public void getCanceledAppointments(int apiCanceledAppointments, AppointRequestModel requestAppointment, boolean b) {
        Call<AppointmentsResponseModel> call = appService.getCanceledAppointments(requestAppointment);
        call.enqueue(new RestProcess<>(apiCanceledAppointments, this, context, b));
    }

    public void addClientRequest(int apiAddClient, Map<String, RequestBody> params, MultipartBody.Part partData, boolean b) {
        Call<BaseResponse> call = appService.addClient(params, partData);
        call.enqueue(new RestProcess<BaseResponse>(apiAddClient, this, context, b));
    }

    public void getMyCustomers(int apiListCustomers, Object o, boolean b) {
        Call<ClientsListResponseModel> call = appService.getClients();
        call.enqueue(new RestProcess<ClientsListResponseModel>(apiListCustomers, this, context, b));
    }

    public void getUserServices(int apiGetService, boolean b) {
        Call<ServiceListResponseModel> call = appService.getService();
        call.enqueue(new RestProcess<ServiceListResponseModel>(apiGetService, this, context, b));
    }

    public void addAppointment(int apiAddClientAppointment, AddAppointmentRequest aRequest, boolean b) {
        Call<BaseResponse> call = appService.addAppointment(aRequest);
        call.enqueue(new RestProcess<BaseResponse>(apiAddClientAppointment, this, context, b));
    }

    public void addContactCustomers(int apiAddMobileContacts, ArrayList<ContactModel> selectedContacts, boolean b) {
        Call<BaseResponse> call = appService.addMobileContacts(selectedContacts);
        call.enqueue(new RestProcess<BaseResponse>(apiAddMobileContacts, this, context, b));
    }

    public List<ClientsListResponseModel.ResponseBean> getAvailableChatUsers(List<ClientsListResponseModel.ResponseBean> response) {
        ArrayList<ClientsListResponseModel.ResponseBean> cList = new ArrayList<>();
        for (int i = 0; i < response.size(); i++) {
            if (response.get(i).getUserInfo() != null && response.get(i).getUserInfo().getQBId() != null) {
                cList.add(response.get(i));
            }
        }
        Log.e("getAvailableChatUsers", "   -----   " + new Gson().toJson(cList));
        return cList;
    }

    public void saveQbDialog(int apiSaveQbDialog, SaveQbDialogRequestModel dialogId, boolean b) {
        Call<BaseResponse> call = appService.saveDialogDetails(dialogId);
        call.enqueue(new RestProcess(apiSaveQbDialog, this, context, b));
    }

    public void getBookings(int apiGetClientBookings, int clientId, boolean b) {
        Call<CustomerBookingResponseModel> call = appService.getClientBookings(clientId);
        call.enqueue(new RestProcess<>(apiGetClientBookings, this, context, b));
    }
}
