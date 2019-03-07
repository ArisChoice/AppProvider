package com.app.barber.ui.preauth.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.app.barber.R;
import com.app.barber.core.BaseActivity;
import com.app.barber.models.request.ValidatePhoneNumberModel;
import com.app.barber.models.response.BaseResponse;
import com.app.barber.models.response.ValidationResponseModel;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.ui.preauth.authmvp.PreAuthMVPView;
import com.app.barber.ui.preauth.authmvp.PreAuthPresenter;
import com.app.barber.util.CommonUtils;
import com.app.barber.util.GlobalValues;
import com.app.barber.views.CustomEditText;
import com.app.barber.views.CustomTextView;
import com.google.gson.Gson;
import com.hbb20.CountryCodePicker;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by harish on 29/10/18.
 */

public class AddMobileNumberActivity extends BaseActivity implements PreAuthMVPView {

    @BindView(R.id.ccp)
    CountryCodePicker ccp;
    @BindView(R.id.edtext_mobile_number)
    CustomEditText edtextMobileNumber;
    @BindView(R.id.next_btn)
    ImageView nextBtn;
    @BindView(R.id.back_toolbar)
    ImageView backToolbar;
    @BindView(R.id.txt_title_toolbar)
    CustomTextView txtTitleToolbar;
    @BindView(R.id.img_edit)
    ImageView imgEdit;
    private PreAuthPresenter presenter;

    @Override
    public int getLayoutId() {
        return R.layout.layout_add_mobile;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        txtTitleToolbar.setVisibility(View.INVISIBLE);
        presenter = new PreAuthPresenter(this);
        presenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    /* @Override
         public void onBackPressed() {
             Intent intent = new Intent();
             intent.putExtra(GlobalValues.Extras.ADD_MOBILE, ccp.getSelectedCountryCode() + edtextMobileNumber.getText().toString());
             setResult(RESULT_OK, intent);
             finish();
         }*/
    @OnClick({R.id.ccp, R.id.next_btn, R.id.back_toolbar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_toolbar:
                onBackPressed();
                break;
            case R.id.ccp:
                break;
            case R.id.next_btn:
                if (Validated()) {
                    checkPhoneNumberValidity();
                }
                break;
        }
    }

    private void checkPhoneNumberValidity() {
        ValidatePhoneNumberModel vModel = new ValidatePhoneNumberModel();
        vModel.setPhone(edtextMobileNumber.getText().toString());
        vModel.setUserType(GlobalValues.UserTypes.BARBER);
        presenter.validatePhoneNUmber(NetworkConstatnts.RequestCode.API_VALIDATE_NUMBER, vModel, true);
    }

    private boolean Validated() {
        if (CommonUtils.isEmpty(edtextMobileNumber)) {
            CommonUtils.getInstance(AddMobileNumberActivity.this).displayMessage(AddMobileNumberActivity.this, getString(R.string.error_phone_number));
            return false;
        } else if (edtextMobileNumber.getText().length() < 8) {
            CommonUtils.getInstance(AddMobileNumberActivity.this).displayMessage(AddMobileNumberActivity.this, getString(R.string.error_phone_number));
            return false;
        } else
            return true;
    }

    @Override
    public void onSuccessResponse(int serviceMode, Object o) {
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_VALIDATE_NUMBER:
                ValidationResponseModel responseData = (ValidationResponseModel) o;
                new CommonUtils().ShowToast(responseData.getMessage());
                Intent intent = new Intent();
                intent.putExtra(GlobalValues.Extras.ADD_MOBILE, edtextMobileNumber.getText().toString());
                intent.putExtra(GlobalValues.Extras.COUNTRY_CODE, ccp.getSelectedCountryCode());
                intent.putExtra(GlobalValues.Extras.VERIFIED, false);
                setResult(RESULT_OK, intent);
                Bundle bundle = new Bundle();
                bundle.putString(GlobalValues.Extras.COUNTRY_CODE, ccp.getSelectedCountryCode());
                bundle.putString(GlobalValues.Extras.ADD_MOBILE, edtextMobileNumber.getText().toString());
                activitySwitcherResult(AddMobileNumberActivity.this, VerifyMobileActivity.class, bundle, GlobalValues.RequestCodes.ADD_MOBILE);
                break;
        }
    }

    @Override
    public void onfaliurResponse(int serviceMode, Object o) {
        new CommonUtils().ShowToast(((BaseResponse) o).getMessage());
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
}
