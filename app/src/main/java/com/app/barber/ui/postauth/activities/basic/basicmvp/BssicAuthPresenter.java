package com.app.barber.ui.postauth.activities.basic.basicmvp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;

import com.app.barber.BuildConfig;
import com.app.barber.R;
import com.app.barber.core.BarberApplication;
import com.app.barber.models.SpecialisationModel;
import com.app.barber.models.request.AddServiceModel;
import com.app.barber.models.request.HoursModel;
import com.app.barber.models.request.SaveDistrictRequest;
import com.app.barber.models.request.UpdateAddressRequestModel;
import com.app.barber.models.response.BaseResponse;
import com.app.barber.models.response.LoginResponseModel;
import com.app.barber.models.response.MyImagesResponseModel;
import com.app.barber.models.response.ServiceListResponseModel;
import com.app.barber.models.response.SpecialisationResponseModel;
import com.app.barber.models.response.UpdateDataResponse;
import com.app.barber.models.response.ZoneDistrictResponseModel;
import com.app.barber.models.response.ZoneResponseModel;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.net.RestCallback;
import com.app.barber.net.RestProcess;
import com.app.barber.net.RestService;
import com.app.barber.ui.postauth.activities.basic.AddWorkspacePhotos;
import com.app.barber.ui.postauth.activities.basic.DisplayImageActivity;
import com.app.barber.util.CommonUtils;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.ImageUtility;
import com.app.barber.util.PreferenceManager;
import com.app.barber.util.di.DaggerValues;
import com.app.barber.util.iface.OnBottomDialogItemListener;
import com.app.barber.util.mvp.BasePresenter;
import com.google.gson.Gson;

import java.io.File;
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

public class BssicAuthPresenter extends BasePresenter<BasicAuthMVPView> implements RestCallback {
    @Inject
    @Named(DaggerValues.AUTH)
    RestService appService;
    @Inject
    public PreferenceManager mPref;
    Context context;


    public BssicAuthPresenter(Context context) {
        this.context = context;
        BarberApplication.getMyComponent(context).inject(this);
    }

    @Override
    public void attachView(BasicAuthMVPView mvpView) {
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
            case NetworkConstatnts.RequestCode.API_UPDATE_BARBER_TYPE:
                if (((UpdateDataResponse) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_UPDATE_SPEC_TYPE:
                if (((UpdateDataResponse) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_UPDATE_ADDRESS:
                if (((UpdateDataResponse) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_UPDATE_BREAK_TIME:
            case NetworkConstatnts.RequestCode.API_UPDATE_CALLOUT_TIME:
            case NetworkConstatnts.RequestCode.API_UPDATE_OPENING_TIME:
                if (((UpdateDataResponse) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                } else getMvpView().onfaliurResponse(serviceMode, model.body());
                break;
            case NetworkConstatnts.RequestCode.API_ADD_SERVICE:
                if (((UpdateDataResponse) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_GET_SERVICE:
                if (((ServiceListResponseModel) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_POST_WORKSPACE_IMAGES:
                if (((BaseResponse) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_GET_IMAGES:
                if (((MyImagesResponseModel) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_GET_HOURS:
                if (((UpdateDataResponse) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_GET_SPECIALISATION:
                if (((SpecialisationResponseModel) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_UPDATE_PAYMENT_TYPE:
                if (((UpdateDataResponse) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_REMOVE_SERVICE:
                if (((BaseResponse) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_DELETE_BREAK_HOUR:
                if (((BaseResponse) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_GET_ZONES:
                if (((ZoneResponseModel) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_GET_DISTRIC_LIST:
                if (((ZoneDistrictResponseModel) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getMvpView().onSuccessResponse(serviceMode, model.body());
                }
                break;
            case NetworkConstatnts.RequestCode.API_SAVE_SERVING_DISTRICTS:
                if (((BaseResponse) model.body()).getStatus() == NetworkConstatnts.ResponseCode.success) {
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

    public void uppdateUserAddress(int apiUpdateAddress, UpdateAddressRequestModel addressModel, boolean b) {
        Call<UpdateDataResponse> call = appService.updateUserAddress(addressModel);
        call.enqueue(new RestProcess<UpdateDataResponse>(apiUpdateAddress, this, context, b));
    }

    public void updateOpeningTime(int apiUpdateOpeningTime, HoursModel.WorkingHours updateTime, boolean b) {
        Log.e("updateOpening ", " " + new Gson().toJson(updateTime).toString());
        Call<UpdateDataResponse> call = null;
        switch (apiUpdateOpeningTime) {
            case NetworkConstatnts.RequestCode.API_UPDATE_CALLOUT_TIME:
                call = appService.updateCalloutTime(updateTime);
                break;
            case NetworkConstatnts.RequestCode.API_UPDATE_OPENING_TIME:
                call = appService.updateOpeningTime(updateTime);
                break;
            case NetworkConstatnts.RequestCode.API_UPDATE_BREAK_TIME:
                call = appService.updateBreakTime(updateTime);
                break;
        }

        call.enqueue(new RestProcess<UpdateDataResponse>(apiUpdateOpeningTime, this, context, b));
    }

    public void addService(int apiAddService, AddServiceModel addModel, boolean b) {
        Call<UpdateDataResponse> call = appService.addService(addModel);
        call.enqueue(new RestProcess<UpdateDataResponse>(apiAddService, this, context, b));
    }

    public void getUserServices(int apiGetService, boolean b) {
        Call<ServiceListResponseModel> call = appService.getService();
        call.enqueue(new RestProcess<ServiceListResponseModel>(apiGetService, this, context, b));
    }

    public void postImages(int addWorkplacePhotos, Map<String, RequestBody> params, List<MultipartBody.Part> list, boolean b) {
        Call<BaseResponse> call = appService.uploadImage(list, params);
        call.enqueue(new RestProcess<BaseResponse>(addWorkplacePhotos, this, context, b));
    }

    public void getMyPortfolioList(int apiGetImages, Object o, boolean b) {
        Call<MyImagesResponseModel> call = appService.getMyImages();
        call.enqueue(new RestProcess<MyImagesResponseModel>(apiGetImages, this, context, b));
    }

    public void getSavedHours(int apiGetHours, String s, boolean b) {
        Call<UpdateDataResponse> call = appService.getHours(s);
        call.enqueue(new RestProcess<UpdateDataResponse>(apiGetHours, this, context, b));
    }

    public void getSpecialisationList(int getSpecialisation, String s, boolean b) {
        Call<SpecialisationResponseModel> call = appService.getSpecialisationList();
        call.enqueue(new RestProcess<SpecialisationResponseModel>(getSpecialisation, this, context, b));
    }

    public void updatePaymentType(int apiUpdatePaymentType, int paymentType, boolean b) {
        Call<UpdateDataResponse> call = appService.updatePaymentType(paymentType);
        call.enqueue(new RestProcess<UpdateDataResponse>(apiUpdatePaymentType, this, context, b));
    }

    /**
     * Add default service.
     */
    public List<ServiceListResponseModel.ResponseBean> adddfaultService() {
        List<ServiceListResponseModel.ResponseBean> defaultService = new ArrayList<>();
        ServiceListResponseModel mdl = new ServiceListResponseModel();
        ServiceListResponseModel.ResponseBean responseModel = mdl.new ResponseBean();
        responseModel.setDuration("15");
        responseModel.setPrice(0);
        responseModel.setPriceType("fixed");
        responseModel.setServiceName("Hair Cut");
        defaultService.add(responseModel);
        responseModel = mdl.new ResponseBean();
        responseModel.setDuration("5");
        responseModel.setPrice(0);
        responseModel.setPriceType("fixed");
        responseModel.setServiceName("Hair Trim");
        defaultService.add(responseModel);
        return defaultService;

    }

    public boolean imagePickerCall(DisplayImageActivity displayImageActivity) {
        if (Build.VERSION.SDK_INT < 23) {
            //Do not need to check the permission
            return true;
        } else {
            if (checkAndRequestPermissions(displayImageActivity)) {
                //If you have already permitted the permission
                return true;
            }
        }
        return false;
    }

    private boolean checkAndRequestPermissions(DisplayImageActivity displayImageActivity) {
        int camPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        int readstoragePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        int writestoragePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (readstoragePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (writestoragePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (camPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(displayImageActivity,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), GlobalValues.RequestCodes.PERMISSIONS_REQUEST_CAMERA);
            return false;
        }

        return true;
    }


    public void removeService(int apiRemoveService, ServiceListResponseModel.ResponseBean t, boolean b) {
        Call<BaseResponse> call = appService.removeService(t.getId());
        call.enqueue(new RestProcess(apiRemoveService, this, context, b));
    }

    public void removeBrakeHour(int apiDeleteBreakHour, String s, boolean b) {
        Call<BaseResponse> call = appService.removeBreakHour(s);
        call.enqueue(new RestProcess(apiDeleteBreakHour, this, context, b));
    }

    public void getZoneList(int apiGetZones, String s, boolean b) {
        Call<ZoneResponseModel> call = appService.getZoneList();
        call.enqueue(new RestProcess(apiGetZones, this, context, b));
    }

    public void getZoneDistricList(int apiGetDistricList, int i, boolean b) {
        Call<ZoneDistrictResponseModel> call = appService.getZoneDistricList(i);
        call.enqueue(new RestProcess(apiGetDistricList, this, context, b));
    }

    public void saveDistricts(int apiSaveServingDistricts, SaveDistrictRequest saveDistrictRequest, boolean b) {
        Call<BaseResponse> call = appService.saveServingDistrict(saveDistrictRequest);
        call.enqueue(new RestProcess(apiSaveServingDistricts, this, context, b));
    }
}
