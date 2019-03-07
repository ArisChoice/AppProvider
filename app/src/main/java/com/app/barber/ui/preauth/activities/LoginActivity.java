package com.app.barber.ui.preauth.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.app.barber.R;
import com.app.barber.core.BarberApplication;
import com.app.barber.core.BaseActivity;
import com.app.barber.models.request.LoginRequestModel;
import com.app.barber.models.response.LoginResponseModel;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.ui.postauth.activities.HomeActivity;
import com.app.barber.ui.preauth.authmvp.PreAuthMVPView;
import com.app.barber.ui.preauth.authmvp.PreAuthPresenter;
import com.app.barber.util.CommonUtils;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.PreferenceManager;
import com.app.barber.views.CustomEditText;
import com.app.barber.views.CustomTextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by harish on 16/10/18.
 */

public class LoginActivity extends BaseActivity implements PreAuthMVPView {
    String TAG = LoginActivity.class.getSimpleName();
    @Inject
    public PreferenceManager mPref;

    @BindView(R.id.edtxt_user_name)
    CustomEditText edtxtUserName;
    @BindView(R.id.edtxt_password)
    CustomEditText edtxtPassword;
    @BindView(R.id.txt_forget_password)
    TextView txtForgetPassword;

    @BindView(R.id.sign_in_btn)
    TextView signInBtn;
    private PreAuthPresenter presenter;
    private boolean isNumber;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_screen;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BarberApplication) getApplication()).getMyComponent(this).inject(this);

        presenter = new PreAuthPresenter(this);
        presenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @OnClick({R.id.txt_forget_password, R.id.sign_in_btn, R.id.sign_up_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_forget_password:
                activitySwitcher(LoginActivity.this, ForgotPasswordActivity.class, null);
                break;
            case R.id.sign_in_btn:
//                presenter.signInRequest(NetworkConstatnts.RequestCode.API_LOGIN, getRequestData());
                if (validated()) {
                    LoginRequestModel loginRequestModel = new LoginRequestModel();
                    if (isNumber)
                        loginRequestModel.setUserName(edtxtUserName.getText().toString());
                    else loginRequestModel.setUserName(edtxtUserName.getText().toString());
                    loginRequestModel.setPassword(edtxtPassword.getText().toString());
                    loginRequestModel.setUserType(GlobalValues.UserTypes.BARBER);
                    loginRequestModel.setDeviceId(presenter.getDeviceIdRegString());
                    loginRequestModel.setDeviceType(NetworkConstatnts.DeviceType.Android);
                    presenter.signInRequest(NetworkConstatnts.RequestCode.API_LOGIN, loginRequestModel, true);
                }
                break;
            case R.id.sign_up_btn:
                activitySwitcher(LoginActivity.this, RegisterActivity.class, null);
                break;

        }
    }

    private boolean validated() {
        if (CommonUtils.isNumber(edtxtUserName.getText().toString())) {
            isNumber = true;
        } else isNumber = false;

        if (CommonUtils.isEmpty(edtxtUserName) && !isNumber) {
            CommonUtils.getInstance(this).displayMessage(this, getString(R.string.error_user_name));
            return false;
        } else if (!CommonUtils.isEmpty(edtxtUserName) && isNumber && edtxtUserName.getText().toString().length() < 8) {
            CommonUtils.getInstance(this).displayMessage(this, this.getString(R.string.error_phone_number));
            return false;
        } else if (edtxtPassword.getText().toString().length() <= 5) {
            CommonUtils.getInstance(this).displayMessage(this, getString(R.string.error_password));
            return false;
        } else
            return true;
    }

    private Object getRequestData() {
        return new Object();
    }


    @Override
    public void showProgres() {

    }

    @Override
    public void hidePreogress() {

    }

    @Override
    public void onSuccess(Object o, int type) {

    }

    @Override
    public void onError(String localizedMessage) {

    }

    @Override
    public void onException(Exception e) {

    }

    @Override
    public void onSuccessResponse(int serviceMode, Object o) {
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_LOGIN:
                LoginResponseModel baseResponse = (LoginResponseModel) o;
                if (baseResponse != null)
                    if (baseResponse.getStatus() == NetworkConstatnts.ResponseCode.success) {
                        new CommonUtils().displayMessage(LoginActivity.this, baseResponse.getMessage());
//                        presenter.loginToQb(this, baseResponse.getUser());
                        if (baseResponse.getUser().getPhoneNumber() == null || baseResponse.getUser().getPhoneNumber().equals(""))
                            activitySwitcher(LoginActivity.this, AddMobileNumberActivity.class, null);
                        else activitySwitcher(LoginActivity.this, HomeActivity.class, null);
                        presenter.saveUserData(baseResponse.getUser());

//                        presenter.navigateUser(this, baseResponse.getUser());
                        finish();
                    } else
                        new CommonUtils().displayMessage(LoginActivity.this, baseResponse.getMessage());
                break;
        }
    }

    @Override
    public void onfaliurResponse(int serviceMode, Object o) {

    }
}
