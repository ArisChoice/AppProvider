package com.app.barber.ui.postauth.activities.basic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.barber.R;
import com.app.barber.core.BaseActivity;
import com.app.barber.models.response.LoginResponseModel;
import com.app.barber.models.response.UpdateDataResponse;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.ui.postauth.activities.HomeActivity;
import com.app.barber.ui.postauth.activities.basic.basicmvp.BasicAuthMVPView;
import com.app.barber.ui.postauth.activities.basic.basicmvp.BssicAuthPresenter;
import com.app.barber.util.GlobalValues;
import com.app.barber.views.CustomTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by harish on 2/11/18.
 */

public class PaymentTypeActivity extends BaseActivity implements BasicAuthMVPView {
    @BindView(R.id.text_type_card)
    CustomTextView textTypeCard;
    @BindView(R.id.type_card_layout)
    LinearLayout typeCardLayout;
    @BindView(R.id.text_type_cash)
    CustomTextView textTypeCash;
    @BindView(R.id.img_check_type_two)
    ImageView imgCheckTypeTwo;
    @BindView(R.id.type_cash_layout)
    LinearLayout typeCashLayout;
    @BindView(R.id.text_type_both)
    CustomTextView textTypeBoth;
    @BindView(R.id.img_check_type_three)
    ImageView imgCheckTypeThree;
    @BindView(R.id.type_both_layout)
    LinearLayout typeBothLayout;
    @BindView(R.id.continue_btn)
    CustomTextView continueBtn;
    boolean isCard, isCash, isBoth;
    @BindView(R.id.first_header_txt)
    CustomTextView firstHeaderTxt;
    @BindView(R.id.second_header_txt)
    CustomTextView secondHeaderTxt;
    @BindView(R.id.skip_header_txt)
    CustomTextView skipHeaderTxt;
    private boolean isRegistration = true;
    private BssicAuthPresenter presenter;
    private int paymentType;

    @Override
    public int getLayoutId() {
        return R.layout.layout_payment_screen;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firstHeaderTxt.setText(R.string.str_payment_method);
        secondHeaderTxt.setText(R.string.str_accept_payment);
        presenter = new BssicAuthPresenter(this);
        presenter.attachView(this);
        getIntentData(getIntent());

    }

    private void getIntentData(Intent intent) {
        if (intent.getStringExtra(GlobalValues.KEYS.FROM) != null) {
            if (intent.getStringExtra(GlobalValues.KEYS.FROM).equals(GlobalValues.KEYS.MORE)) {
                isRegistration = false;
                continueBtn.setText(R.string.str_update);
                getSavedPaymentMethod();
            }
        }
    }

    private void getSavedPaymentMethod() {
        if (getUserData() != null && getUserData().getPaymentType() != null) {
            switch (getUserData().getPaymentType()) {
                case "1":
                    new Handler().postDelayed(() -> setSelection(R.id.type_card_layout), GlobalValues.TIME_DURATIONS.SMALL);
                    break;
                case "2":
                    new Handler().postDelayed(() -> setSelection(R.id.type_cash_layout), GlobalValues.TIME_DURATIONS.SMALL);
                    break;
                case "3":
                    new Handler().postDelayed(() -> setSelection(R.id.type_both_layout), GlobalValues.TIME_DURATIONS.SMALL);
                    break;
            }
        }
    }

    @OnClick({R.id.type_card_layout, R.id.type_cash_layout, R.id.type_both_layout, R.id.continue_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.type_card_layout:
                setSelection(R.id.type_card_layout);
                break;
            case R.id.type_cash_layout:
                setSelection(R.id.type_cash_layout);
                break;
            case R.id.type_both_layout:
                setSelection(R.id.type_both_layout);
                break;
            case R.id.continue_btn:
                if (paymentType != 0)
                    presenter.updatePaymentType(NetworkConstatnts.RequestCode.API_UPDATE_PAYMENT_TYPE, paymentType, true);
//                    activitySwitcher(PaymentTypeActivity.this, HomeActivity.class, null);
                break;
        }
    }

    private void setSelection(int intType) {
        switch (intType) {
            case R.id.type_card_layout:
                typeCardLayout.setBackgroundResource(R.drawable.rectangle_white_border);
                typeCashLayout.setBackgroundResource(R.drawable.rectangle_grey_border);
                typeBothLayout.setBackgroundResource(R.drawable.rectangle_grey_border);
                textTypeCard.setTextColor(getResources().getColor(R.color.colorPrimary));
                textTypeCash.setTextColor(getResources().getColor(R.color.color_black));
                textTypeBoth.setTextColor(getResources().getColor(R.color.color_black));
                paymentType = 1;//card
                break;
            case R.id.type_cash_layout:

                typeCardLayout.setBackgroundResource(R.drawable.rectangle_grey_border);
                typeCashLayout.setBackgroundResource(R.drawable.rectangle_white_border);
                typeBothLayout.setBackgroundResource(R.drawable.rectangle_grey_border);
                textTypeCard.setTextColor(getResources().getColor(R.color.color_black));
                textTypeCash.setTextColor(getResources().getColor(R.color.colorPrimary));
                textTypeBoth.setTextColor(getResources().getColor(R.color.color_black));
                paymentType = 2;//cash
                break;
            case R.id.type_both_layout:
                typeCardLayout.setBackgroundResource(R.drawable.rectangle_grey_border);
                typeCashLayout.setBackgroundResource(R.drawable.rectangle_grey_border);
                typeBothLayout.setBackgroundResource(R.drawable.rectangle_white_border);
                textTypeCard.setTextColor(getResources().getColor(R.color.color_black));
                textTypeCash.setTextColor(getResources().getColor(R.color.color_black));
                textTypeBoth.setTextColor(getResources().getColor(R.color.colorPrimary));
                paymentType = 3;//both
                break;
        }
    }

    @Override
    public void onSuccessResponse(int serviceMode, Object model) {
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_UPDATE_PAYMENT_TYPE:
                if (((UpdateDataResponse) model).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    LoginResponseModel.UserBean userData = getUserData();
                    userData.setPaymentType(((UpdateDataResponse) model).getPaymentType());
                    presenter.saveUserData(userData);
                    if (isRegistration) {
                        activitySwitcher(PaymentTypeActivity.this, HomeActivity.class, null);
                    } else finish();
                }
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
}
