package com.app.barber.ui.preauth.authmvp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.app.barber.core.BarberApplication;
import com.app.barber.models.request.ChangePasswordRequest;
import com.app.barber.models.request.LoginRequestModel;
import com.app.barber.models.request.RegisterRequestModel;
import com.app.barber.models.request.ValidatePhoneNumberModel;
import com.app.barber.models.response.BaseResponse;
import com.app.barber.models.response.LoginResponseModel;
import com.app.barber.models.response.ValidationResponseModel;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.net.RestCallback;
import com.app.barber.net.RestProcess;
import com.app.barber.net.RestService;
import com.app.barber.ui.postauth.activities.HomeActivity;
import com.app.barber.ui.postauth.activities.basic.AddressSelectionActivity;
import com.app.barber.ui.postauth.activities.basic.CalloutOpeningHoursActivity;
import com.app.barber.ui.postauth.activities.basic.ServiceListActivity;
import com.app.barber.ui.postauth.activities.basic.SpecialiseActivity;
import com.app.barber.ui.postauth.activities.basic.WelcomeActivity;
import com.app.barber.ui.preauth.activities.AddMobileNumberActivity;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.PreferenceManager;
import com.app.barber.util.di.DaggerValues;
import com.app.barber.util.mvp.BasePresenter;
import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Named;

import retrofit2.Call;
import retrofit2.Response;

import static com.app.barber.core.BaseActivity.activitySwitcher;

/**
 * Created by harish on 26/10/18.
 */

public class PreAuthPresenter extends BasePresenter<PreAuthMVPView> implements RestCallback {
    @Inject
    @Named(DaggerValues.NON_AUTH)
    RestService appService;
    @Inject
    public PreferenceManager mPref;
    Context context;
    public static final String mypreferenceNew = NetworkConstatnts.TOKEN_PREF;//used only for push token
    SharedPreferences sharedpreferences;

    public PreAuthPresenter(Context context) {
        this.context = context;
        BarberApplication.getMyComponent(context).inject(this);
    }

    @Override
    public void attachView(PreAuthMVPView mvpView) {
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
        Log.e("on Success", " " + new Gson().toJson(model));
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_REGISTER:
                getMvpView().onSuccessResponse(serviceMode, model.body());
                break;
            case NetworkConstatnts.RequestCode.API_LOGIN:
                getMvpView().onSuccessResponse(serviceMode, model.body());
                break;
            case NetworkConstatnts.RequestCode.API_FORGET_PASS:
                getMvpView().onSuccessResponse(serviceMode, model.body());
                break;
            case NetworkConstatnts.RequestCode.API_VALIDATE_NUMBER:
                if (((ValidationResponseModel) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                } else {
                    getMvpView().onfaliurResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_VERIFY_OTP:
                if (((BaseResponse) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                } else {
                    getMvpView().onfaliurResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_VALIDATE_SAVE_NUMBER:
                if (((BaseResponse) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                } else {
                    getMvpView().onfaliurResponse(serviceMode, model.body());
                }
                break;
        }

    }

    @Override
    public void onLogout() {

    }

    public void signInRequest(int apiLogin, LoginRequestModel requestData, boolean b) {
        Call<LoginResponseModel> call = appService.loginUser(requestData);
        call.enqueue(new RestProcess<LoginResponseModel>(apiLogin, this, context, b));
    }

    public void signUpRequest(int apiType, RegisterRequestModel registerRequest, boolean b) {
        Call<BaseResponse> call = appService.registerUser(registerRequest);
        call.enqueue(new RestProcess<BaseResponse>(apiType, this, context, b));
    }

    public void navigateUser(Activity activity, LoginResponseModel.UserBean baseResponse) {
        if (baseResponse.getPhoneNumber() == null || baseResponse.getPhoneNumber().equals(""))
            activitySwitcher(activity, AddMobileNumberActivity.class, null);
        else if (baseResponse.getBarberType() == null || baseResponse.getBarberType().equals(""))
            activitySwitcher(activity, WelcomeActivity.class, null);
        else if (baseResponse.getSpecializaions() == null || baseResponse.getSpecializaions().equals(""))
            activitySwitcher(activity, SpecialiseActivity.class, null);
        else if (baseResponse.getUserAddresses() == null)
            activitySwitcher(activity, AddressSelectionActivity.class, null);
        else if (baseResponse.getOpeningHours() == null || baseResponse.getOpeningHours().size() == 0)
            activitySwitcher(activity, CalloutOpeningHoursActivity.class, null);
        else if (baseResponse.getServices() == null || baseResponse.getServices().size() == 0)
            activitySwitcher(activity, ServiceListActivity.class, null);
        else activitySwitcher(activity, HomeActivity.class, null);
        saveUserData(baseResponse);
    }

    public void saveUserData(LoginResponseModel.UserBean user) {
        mPref.setUserData(new Gson().toJson(user));
        mPref.setPrefrencesBoolean(GlobalValues.KEYS.isLogedIn, true);
        Log.e(" presenter ", "" + new Gson().toJson(user));
    }

    public void validatePhoneNumber(int apiType, RegisterRequestModel validateRequest, boolean b) {
        Call<BaseResponse> call = appService.verifyNumber(validateRequest);
        call.enqueue(new RestProcess<BaseResponse>(apiType, this, context, b));
    }

    public void forgotPassword(int apiType, ChangePasswordRequest registerRequest, boolean b) {
        Call<BaseResponse> call = appService.forgotPass(registerRequest);
        call.enqueue(new RestProcess<BaseResponse>(apiType, this, context, b));
    }

    public String getDeviceIdRegString() {
        //GET THIS TOKEN FROM PREF
        sharedpreferences = context.getSharedPreferences(mypreferenceNew, Context.MODE_PRIVATE);
        String tokedId = sharedpreferences.getString("pushToken", "");
        Log.d("getDeviceIdRegString", "    " + tokedId);
        return tokedId;
    }

    /*public void loginToQb(LoginActivity loginActivity,
                          LoginResponseModel.UserBean usr) {
        if (usr.getQBId() != null) {
            Performer<QBUser> ussr = QBUsers.getUser(Integer.parseInt(usr.getQBId()));
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
        }


    }*/

    public void validatePhoneNUmber(int apiType, ValidatePhoneNumberModel validateRequest, boolean b) {
        Call<ValidationResponseModel> call = appService.validateNumber(validateRequest);
        call.enqueue(new RestProcess<ValidationResponseModel>(apiType, this, context, b));
    }

    public void verifyOtp(int apiVerifyOtp, String phone, String otp, int type, boolean b) {
        Call<BaseResponse> call = appService.verifyOtp(phone, otp, type);
        call.enqueue(new RestProcess<BaseResponse>(apiVerifyOtp, this, context, b));
    }
}
