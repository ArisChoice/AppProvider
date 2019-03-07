package com.app.barber.ui.preauth.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.app.barber.R;
import com.app.barber.core.BaseActivity;
import com.app.barber.util.CommonUtils;
import com.app.barber.util.GlobalValues;
import com.app.barber.views.CustomEditText;
import com.app.barber.views.CustomTextView;
import com.hbb20.CountryCodePicker;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by harish on 22/10/18.
 */

public class ForgotPasswordActivity extends BaseActivity {


    private static Activity context;
    @BindView(R.id.submit_btn)
    CustomTextView submitBtn;
    @BindView(R.id.first_header_txt)
    CustomTextView firstHeaderTxt;
    @BindView(R.id.second_header_txt)
    CustomTextView secondHeaderTxt;
    @BindView(R.id.skip_header_txt)
    CustomTextView skipHeaderTxt;
    @BindView(R.id.txt_title_toolbar)
    CustomTextView txtTitleToolbar;
    @BindView(R.id.cc_picker)
    CountryCodePicker ccPicker;
    @BindView(R.id.phone_number_edtxt)
    CustomEditText phoneNumberEdtxt;


    @Override
    public int getLayoutId() {
        return R.layout.layout_forgot_password;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        firstHeaderTxt.setText(R.string.str_forgot_password_);
        secondHeaderTxt.setText(R.string.str_enter_email);
        txtTitleToolbar.setVisibility(View.INVISIBLE);
//        new CommonUtils().ShowToast("Work in progress");
    }

    @OnClick({R.id.submit_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit_btn:
                if (validated()) {
                    Bundle bundle = new Bundle();
                    bundle.putString(GlobalValues.Extras.COUNTRY_CODE, ccPicker.getSelectedCountryCode());
                    bundle.putBoolean(GlobalValues.Extras.FORGOT_PASSWORD, true);
                    bundle.putString(GlobalValues.Extras.ADD_MOBILE, phoneNumberEdtxt.getText().toString());
                    activitySwitcher(ForgotPasswordActivity.this, VerifyMobileActivity.class, bundle);
                }
                break;
        }
    }

    private boolean validated() {
        if (CommonUtils.isEmpty(phoneNumberEdtxt) && phoneNumberEdtxt.getText().toString().length() < 8) {
            CommonUtils.getInstance(this).displayMessage(this, this.getString(R.string.error_phone_number));
            return false;
        }
        return true;
    }

    public static Activity getInstance() {
        return context;
    }
}
