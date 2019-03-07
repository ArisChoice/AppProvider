package com.app.barber.ui.postauth.activities.basic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.barber.R;
import com.app.barber.core.BarberApplication;
import com.app.barber.core.BaseActivity;
import com.app.barber.models.response.LoginResponseModel;
import com.app.barber.models.response.UpdateDataResponse;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.ui.postauth.activities.basic.basicmvp.BasicAuthMVPView;
import com.app.barber.ui.postauth.activities.basic.basicmvp.BssicAuthPresenter;
import com.app.barber.util.GlobalValues;
import com.app.barber.views.CustomTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by harish on 23/10/18.
 */

public class WelcomeActivity extends BaseActivity implements BasicAuthMVPView {

    @BindView(R.id.text_type_one)
    CustomTextView textTypeOne;
    @BindView(R.id.img_check_type_one)
    ImageView imgCheckTypeOne;
    @BindView(R.id.type_barber_layout)
    LinearLayout typeBarberLayout;
    @BindView(R.id.text_type_two)
    CustomTextView textTypeTwo;
    @BindView(R.id.img_check_type_two)
    ImageView imgCheckTypeTwo;
    @BindView(R.id.type_callout_barber_layout)
    LinearLayout typeCalloutBarberLayout;
    @BindView(R.id.text_type_three)
    CustomTextView textTypeThree;
    @BindView(R.id.img_check_type_three)
    ImageView imgCheckTypeThree;
    @BindView(R.id.type_tranee_barber_layout)
    LinearLayout typeTraneeBarberLayout;
    @BindView(R.id.continue_btn)
    CustomTextView continueBtn;
    @BindView(R.id.img_type_one)
    ImageView imgTypeOne;
    @BindView(R.id.img_type_two)
    ImageView imgTypeTwo;
    @BindView(R.id.img_type_three)
    ImageView imgTypeThree;
    @BindView(R.id.first_header_txt)
    CustomTextView firstHeaderTxt;
    @BindView(R.id.second_header_txt)
    CustomTextView secondHeaderTxt;
    private BssicAuthPresenter presenter;
    private String selectedType;
    private boolean isRegistration = true;
    private String savedSelectedType;

    @Override
    public int getLayoutId() {
        return R.layout.welcome_screen_activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BarberApplication) getApplication()).getMyComponent(this).inject(this);
        firstHeaderTxt.setText(R.string.str_your_type);
        secondHeaderTxt.setText(R.string.str_please_select_type);
        presenter = new BssicAuthPresenter(this);
        presenter.attachView(this);
        getIntentData(getIntent());
        getsavedTypes();
    }

    private void getsavedTypes() {
        if (getUserData() != null && getUserData().getBarberType() != null) {
            savedSelectedType = getUserData().getBarberType();
            String[] barberTypes = getUserData().getBarberType().split(",");
            for (int i = 0; i < barberTypes.length; i++) {
                if (barberTypes[i].equals("1")) {
                    setTypeSelection(R.id.type_barber_layout);
                } else if (barberTypes[i].equals("2"))
                    setTypeSelection(R.id.type_callout_barber_layout);
                else if (barberTypes[i].equals("3"))
                    setTypeSelection(R.id.type_tranee_barber_layout);
            }
        } else getSelection();
    }

    private void getIntentData(Intent intent) {
        if (intent.getStringExtra(GlobalValues.KEYS.FROM) != null) {
            if (intent.getStringExtra(GlobalValues.KEYS.FROM).equals(GlobalValues.KEYS.MORE)) {
                isRegistration = false;
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @OnClick({R.id.type_barber_layout, R.id.type_callout_barber_layout, R.id.type_tranee_barber_layout, R.id.continue_btn})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.type_barber_layout:
                setTypeSelection(view.getId());
                break;
            case R.id.type_callout_barber_layout:
                setTypeSelection(view.getId());
                break;
            case R.id.type_tranee_barber_layout:
                setTypeSelection(view.getId());
                break;
            case R.id.continue_btn:
                if (selectedType != null)
                    presenter.updateBarberType(NetworkConstatnts.RequestCode.API_UPDATE_BARBER_TYPE, selectedType, true);
//
                break;
        }
    }


    private void setTypeSelection(int id) {
        switch (id) {
            case R.id.type_barber_layout://1
                if (imgCheckTypeOne.getVisibility() == View.GONE) {
                    typeBarberLayout.setBackgroundResource(R.drawable.rectangle_white_border);
                    textTypeOne.setTextColor(getResources().getColor(R.color.color_sky_blue));
                    imgCheckTypeOne.setVisibility(View.VISIBLE);
                    imgTypeOne.setImageResource(R.drawable.barber_icon_active);
                } else {
                    typeBarberLayout.setBackgroundResource(R.drawable.rectangle_grey_border);
                    textTypeOne.setTextColor(getResources().getColor(R.color.color_black));
                    imgTypeOne.setImageResource(R.drawable.barber_icon_normal);
                    imgCheckTypeOne.setVisibility(View.GONE);
                    refreshSelection();
                }
                getSelection();
                break;
            case R.id.type_callout_barber_layout://2
                if (imgCheckTypeTwo.getVisibility() == View.GONE) {
                    typeCalloutBarberLayout.setBackgroundResource(R.drawable.rectangle_white_border);
                    textTypeTwo.setTextColor(getResources().getColor(R.color.color_sky_blue));
                    imgCheckTypeTwo.setVisibility(View.VISIBLE);
                    imgTypeTwo.setImageResource(R.drawable.callout_barber_icon_active);

                } else {
                    typeCalloutBarberLayout.setBackgroundResource(R.drawable.rectangle_grey_border);
                    textTypeTwo.setTextColor(getResources().getColor(R.color.color_black));
                    imgCheckTypeTwo.setVisibility(View.GONE);
                    imgTypeTwo.setImageResource(R.drawable.callout_barber_icon_normal);
                    refreshSelection();
                }
                getSelection();
                break;
            case R.id.type_tranee_barber_layout://3
                if (imgCheckTypeOne.getVisibility() == View.VISIBLE
                        || imgCheckTypeTwo.getVisibility() == View.VISIBLE) {
                    if (imgCheckTypeThree.getVisibility() == View.VISIBLE) {
                        typeTraneeBarberLayout.setBackgroundResource(R.drawable.rectangle_grey_border);
                        textTypeThree.setTextColor(getResources().getColor(R.color.color_black));
                        imgCheckTypeThree.setVisibility(View.GONE);
                        imgTypeThree.setImageResource(R.drawable.trainee_normal);
                    } else {
                        typeTraneeBarberLayout.setBackgroundResource(R.drawable.rectangle_white_border);
                        textTypeThree.setTextColor(getResources().getColor(R.color.color_sky_blue));
                        imgCheckTypeThree.setVisibility(View.VISIBLE);
                        imgTypeThree.setImageResource(R.drawable.trainee_active);
                    }
                    refreshSelection();
                } else {
                    typeTraneeBarberLayout.setBackgroundResource(R.drawable.rectangle_grey_border);
                    textTypeThree.setTextColor(getResources().getColor(R.color.color_black));
                    imgCheckTypeThree.setVisibility(View.GONE);
                    imgTypeThree.setImageResource(R.drawable.trainee_normal);
                }
                getSelection();
                break;
        }
    }

    //selection probabilities
    private void getSelection() {
        if (imgCheckTypeOne.getVisibility() == View.VISIBLE) {
            selectedType = "1";
        }
        if (imgCheckTypeTwo.getVisibility() == View.VISIBLE) {
            selectedType = "2";
        }
        if (imgCheckTypeOne.getVisibility() == View.VISIBLE && imgCheckTypeTwo.getVisibility() == View.VISIBLE) {
            selectedType = "1,2";
        }
        if (imgCheckTypeOne.getVisibility() == View.VISIBLE && imgCheckTypeThree.getVisibility() == View.VISIBLE) {
            selectedType = "1,3";
        }
        if (imgCheckTypeTwo.getVisibility() == View.VISIBLE && imgCheckTypeThree.getVisibility() == View.VISIBLE) {
            selectedType = "2,3";
        }
        if (imgCheckTypeOne.getVisibility() == View.VISIBLE && imgCheckTypeTwo.getVisibility() == View.VISIBLE && imgCheckTypeThree.getVisibility() == View.VISIBLE) {
            selectedType = "1,2,3";
        }
    }

    private void refreshSelection() {
        if (imgCheckTypeOne.getVisibility() == View.GONE && imgCheckTypeTwo.getVisibility() == View.GONE) {
            typeTraneeBarberLayout.setBackgroundResource(R.drawable.rectangle_grey_border);
            textTypeThree.setTextColor(getResources().getColor(R.color.color_black));
            imgCheckTypeThree.setVisibility(View.GONE);
            imgTypeThree.setImageResource(R.drawable.trainee_normal);
            selectedType = null;
        }

    }

    @Override
    public void onSuccessResponse(int serviceMode, Object model) {
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_UPDATE_BARBER_TYPE:
                if (((UpdateDataResponse) model).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    LoginResponseModel.UserBean userData = getUserData();
                    userData.setBarberType(((UpdateDataResponse) model).getBarberType());
                    presenter.saveUserData(userData);
                    if (isRegistration)
                        activitySwitcher(WelcomeActivity.this, SpecialiseActivity.class, null);
                    else {
                        if (userData.getBarberType() != null && userData.getBarberType().contains("1") && !savedSelectedType.contains("1")) {
                            Bundle bundle = new Bundle();
                            bundle.putString(GlobalValues.KEYS.FROM, GlobalValues.KEYS.MORE);
                            bundle.putString(GlobalValues.KEYS.TYPE, GlobalValues.KEYS.ADD_WORKING_HOURS);
                            activitySwitcher(WelcomeActivity.this, CalloutOpeningHoursActivity.class, bundle);
                        } else if (userData.getBarberType() != null && userData.getBarberType().contains("2") && !savedSelectedType.contains("2")) {
                            Bundle bundle = new Bundle();
                            bundle.putString(GlobalValues.KEYS.FROM, GlobalValues.KEYS.MORE);
                            bundle.putString(GlobalValues.KEYS.TYPE, GlobalValues.KEYS.ADD_CALLOUT_HOURS);
                            activitySwitcher(WelcomeActivity.this, CalloutOpeningHoursActivity.class, bundle);
                        }else if(userData.getBarberType() != null && userData.getBarberType().contains("3") && !savedSelectedType.contains("3")){

                        }
                    }
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
