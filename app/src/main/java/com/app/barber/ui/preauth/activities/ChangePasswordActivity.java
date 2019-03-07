package com.app.barber.ui.preauth.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.app.barber.R;
import com.app.barber.core.BaseActivity;
import com.app.barber.models.request.ChangePasswordRequest;
import com.app.barber.models.response.BaseResponse;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.ui.preauth.authmvp.PreAuthMVPView;
import com.app.barber.ui.preauth.authmvp.PreAuthPresenter;
import com.app.barber.util.CommonUtils;
import com.app.barber.util.GlobalValues;
import com.app.barber.views.CustomEditText;
import com.app.barber.views.CustomTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by harish on 2/1/19.
 */

public class ChangePasswordActivity extends BaseActivity implements PreAuthMVPView {
    @BindView(R.id.back_toolbar)
    ImageView backToolbar;
    @BindView(R.id.txt_title_toolbar)
    CustomTextView txtTitleToolbar;
    @BindView(R.id.img_edit)
    ImageView imgEdit;
    @BindView(R.id.toolbar_common)
    Toolbar toolbarCommon;
    @BindView(R.id.first_header_txt)
    CustomTextView firstHeaderTxt;
    @BindView(R.id.second_header_txt)
    CustomTextView secondHeaderTxt;
    @BindView(R.id.skip_header_txt)
    CustomTextView skipHeaderTxt;
    @BindView(R.id.new_password_field)
    CustomEditText newPasswordField;
    @BindView(R.id.cnfrm_password_field)
    CustomEditText cnfrmPasswordField;
    @BindView(R.id.submit_btn)
    CustomTextView submitBtn;
    private PreAuthPresenter presenter;
    private String enteredNumber;

    /**
     * @return layout resource id
     */
    @Override
    public int getLayoutId() {
        return R.layout.layout_change_password_screen;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PreAuthPresenter(this);
        presenter.attachView(this);
        getintentData(getIntent());
        firstHeaderTxt.setText(R.string.str_change_password);
        secondHeaderTxt.setText(R.string.str_enter_new_password);
        txtTitleToolbar.setVisibility(View.GONE);
    }

    private void getintentData(Intent intent) {
        enteredNumber = intent.getStringExtra(GlobalValues.Extras.ADD_MOBILE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onSuccessResponse(int serviceMode, Object o) {
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_FORGET_PASS:
                BaseResponse baseResponse = (BaseResponse) o;
                if (baseResponse != null)
                    if (baseResponse.getStatus() == NetworkConstatnts.ResponseCode.success) {
                        new CommonUtils().displayMessage(ChangePasswordActivity.this, baseResponse.getMessage());
                        if (ForgotPasswordActivity.getInstance() != null) {
                            ForgotPasswordActivity.getInstance().finish();
                            finish();
                        }
                    } else
                        new CommonUtils().displayMessage(ChangePasswordActivity.this, "");
                break;
        }
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

    @OnClick({R.id.back_toolbar, R.id.submit_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_toolbar:
                onBackPressed();
                break;
            case R.id.submit_btn:
                if (validated()) {
                    ChangePasswordRequest cRequest = new ChangePasswordRequest();
                    cRequest.setPassword(cnfrmPasswordField.getText().toString());
                    cRequest.setPhoneNumber(enteredNumber);
                    cRequest.setUserType(GlobalValues.UserTypes.BARBER);
                    presenter.forgotPassword(NetworkConstatnts.RequestCode.API_FORGET_PASS, cRequest, true);
                }
                break;
        }
    }

    private boolean validated() {
        if (CommonUtils.isEmpty(newPasswordField)) {
            CommonUtils.getInstance(ChangePasswordActivity.this).displayMessage(ChangePasswordActivity.this, getString(R.string.error_password));
            return false;
        } else if (!newPasswordField.getText().toString().equals(cnfrmPasswordField.getText().toString())) {
            CommonUtils.getInstance(ChangePasswordActivity.this).displayMessage(ChangePasswordActivity.this, getString(R.string.error_password_not_matched));
            return false;
        } else
            return true;
    }
}
