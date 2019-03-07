package com.app.barber.ui.preauth.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import com.app.barber.R;
import com.app.barber.core.BaseActivity;
import com.app.barber.models.request.LoginRequestModel;
import com.app.barber.models.request.RegisterRequestModel;
import com.app.barber.models.response.BaseResponse;
import com.app.barber.models.response.LoginResponseModel;
import com.app.barber.net.NetworkConstatnts;

import com.app.barber.ui.preauth.authmvp.PreAuthMVPView;
import com.app.barber.ui.preauth.authmvp.PreAuthPresenter;

import com.app.barber.util.CommonUtils;
import com.app.barber.util.GlobalValues;
import com.app.barber.views.CustomEditText;
import com.app.barber.views.CustomTextView;
import com.app.barber.views.CustomViewPager;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by harish on 22/10/18.
 */

public class RegisterActivity extends BaseActivity implements PreAuthMVPView {
    @BindView(R.id.registration_pager)
    CustomViewPager registrationPager;
    @BindView(R.id.edtxt_full_name)
    CustomEditText edtxtFullName;
    @BindView(R.id.edtxt_user_name)
    CustomEditText edtxtUserName;
    @BindView(R.id.edtxt_emsil)
    CustomEditText edtxtEmsil;
    @BindView(R.id.edtxt_password)
    CustomEditText edtxtPassword;
    @BindView(R.id.continue_btn)
    CustomTextView continueBtn;
    @BindView(R.id.sign_in_btn)
    TextView signInBtn;
    private static Activity aContext;
    private int currentPage = 0;
    RegisterRequestModel registerRequest;
    private List<Fragment> fragments;
    private PreAuthPresenter presenter;
    private boolean registered;

    @Override
    public int getLayoutId() {
        return R.layout.layout_register_screen;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aContext = this;
        presenter = new PreAuthPresenter(this);
        presenter.attachView(this);
        registerRequest = new RegisterRequestModel();
        edtxtUserName.setFilters(new InputFilter[]{filter});
//        initPager();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    public static Activity getInstance() {
        return aContext;
    }

    InputFilter filter = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            String filtered = "";
            for (int i = start; i < end; i++) {
                char character = source.charAt(i);
                if (!Character.isWhitespace(character)) {
                    filtered += character;
                }
            }
            return filtered;
        }

    };

    @Override
    public void onSuccessResponse(int serviceMode, Object model) {
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_REGISTER:
                registered = false;
                if (model != null)
                    if (((BaseResponse) model).getStatus() == NetworkConstatnts.ResponseCode.success) {
                        BaseResponse baseResponse = ((BaseResponse) model);
                        new CommonUtils().displayMessage(RegisterActivity.this, baseResponse.getMessage());
                        LoginRequestModel loginRequestModel = new LoginRequestModel();
                        loginRequestModel.setUserName(registerRequest.getUserName());
                        loginRequestModel.setPassword(registerRequest.getPassword());
                        loginRequestModel.setUserType(GlobalValues.UserTypes.BARBER);
                        loginRequestModel.setDeviceId(presenter.getDeviceIdRegString());
                        loginRequestModel.setDeviceType(NetworkConstatnts.DeviceType.Android);
                        presenter.signInRequest(NetworkConstatnts.RequestCode.API_LOGIN, loginRequestModel, true);
                    } else
                        new CommonUtils().displayMessage(RegisterActivity.this, ((BaseResponse) model).getMessage());
                else
                    new CommonUtils().displayMessage(RegisterActivity.this, "");
                break;
            case NetworkConstatnts.RequestCode.API_LOGIN:
                LoginResponseModel baResponse = (LoginResponseModel) model;
                if (baResponse.getStatus() == NetworkConstatnts.ResponseCode.success) {
                    new CommonUtils().displayMessage(RegisterActivity.this, baResponse.getMessage());
                    presenter.navigateUser(RegisterActivity.this, baResponse.getUser());
                    finish();
                } else
                    new CommonUtils().displayMessage(RegisterActivity.this, baResponse.getMessage());
                break;
        }
//        activitySwitcher(RegisterActivity.this, WelcomeActivity.class, null);

    }

    @Override
    public void onfaliurResponse(int serviceMode, Object o) {

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

    @OnClick({R.id.continue_btn, R.id.sign_in_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.continue_btn:
                if (validated()) {
                    registerRequest.setUserType(GlobalValues.UserTypes.BARBER);
                    registerRequest.setFullName(edtxtFullName.getText().toString());
                    registerRequest.setUserName( edtxtUserName.getText().toString());
                    registerRequest.setEmail(edtxtEmsil.getText().toString());
                    registerRequest.setPassword(edtxtPassword.getText().toString());
                    registerRequest.setConfirmPassword(edtxtPassword.getText().toString());
//                    activitySwitcher(RegisterActivity.this, AddMobileNumberActivity.class, null);
                    presenter.signUpRequest(NetworkConstatnts.RequestCode.API_REGISTER, registerRequest, true);
                }
                break;
            case R.id.sign_in_btn:
                activitySwitcher(RegisterActivity.this, LoginActivity.class, null);
                break;
        }
    }

    private boolean validated() {
        if (CommonUtils.isEmpty(edtxtFullName)) {
            CommonUtils.getInstance(this).displayMessage(this, getString(R.string.error_fullName));
            return false;
        } else if (CommonUtils.isEmpty(edtxtUserName)) {
            CommonUtils.getInstance(this).displayMessage(this, getString(R.string.error_user_name));
            return false;
        } else if (!CommonUtils.isEmpty(edtxtEmsil) && !CommonUtils.isEmailValid(edtxtEmsil.getText().toString())) {
            CommonUtils.getInstance(this).displayMessage(this, getString(R.string.error_email));
            return false;
        } else if (edtxtPassword.getText().toString().length() <= 5) {
            CommonUtils.getInstance(this).displayMessage(this, getString(R.string.error_password));
            return false;
        } /*else if (!edtxtPassword.getText().toString().equals(edtxtCnfrmPassword.getText().toString())) {
            CommonUtils.getInstance(this).displayMessage(getActivity(), getActivity().getString(R.string.error_password_not_matched));
            return false;
        }*/ /*else if (!termsCheckBox.isChecked()) {
            CommonUtils.getInstance(this).displayMessage(getActivity(), getActivity().getString(R.string.error_accept_terms));
            return false;
        }*/ else
            return true;
    }
}
