package com.app.barber.ui.postauth.activities.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;

import com.app.barber.R;
import com.app.barber.core.BarberApplication;
import com.app.barber.core.BaseActivity;
import com.app.barber.models.request.SettingChageRequest;
import com.app.barber.models.response.SettingStatusResponse;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.ui.postauth.activities.basic.PaymentTypeActivity;
import com.app.barber.ui.postauth.activities.basic.SelectZoneActivity;
import com.app.barber.ui.postauth.activities.basic.ServiceListActivity;
import com.app.barber.ui.postauth.activities.basic.WelcomeActivity;
import com.app.barber.ui.postauth.activities.settings.settingmvp.SettingMVPView;
import com.app.barber.ui.postauth.activities.settings.settingmvp.SettingPresenter;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.PreferenceManager;
import com.app.barber.views.CustomTextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by harish on 12/11/18.
 */

public class SettingActivity extends BaseActivity implements SettingMVPView {
    @BindView(R.id.back_toolbar)
    ImageView backToolbar;
    @BindView(R.id.txt_title_toolbar)
    CustomTextView txtTitleToolbar;
    @BindView(R.id.img_edit)
    ImageView imgEdit;
    @BindView(R.id.toolbar_common)
    Toolbar toolbarCommon;
    @BindView(R.id.barber_modes_btn)
    CustomTextView barberModesBtn;
    @BindView(R.id.payment_mode_btn)
    CustomTextView paymentModeBtn;
    @BindView(R.id.notification_btn)
    CustomTextView notificationBtn;
    @BindView(R.id.txt_btn)
    CustomTextView txtBtn;
    @BindView(R.id.distric_mode_btn)
    CustomTextView districModeBtn;
    @BindView(R.id.address_btn)
    CustomTextView addressBtn;
    @BindView(R.id.notification_switch)
    Switch notificationSwitch;
    @BindView(R.id.services_btn)
    LinearLayout servicesBtn;
    @BindView(R.id.currency_text)
    CustomTextView currencyText;
    @BindView(R.id.currency)
    Spinner currency;
    @BindView(R.id.cancellation_switch)
    Switch cancellationSwitch;
    private SettingPresenter presenter;
    @Inject
    PreferenceManager mPref;

    @Override
    public int getLayoutId() {
        return R.layout.layout_setting_screen;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BarberApplication) getApplication()).getMyComponent(SettingActivity.this).inject(this);
        presenter = new SettingPresenter(SettingActivity.this);
        presenter.attachView(this);
        txtTitleToolbar.setText(R.string.str_setting);
        notificationSwitch.setChecked(mPref.getPrefrencesBoolean(GlobalValues.KEYS.IS_NOTIFICATION_ACTIVE));
        notificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> mPref.setPrefrencesBoolean(GlobalValues.KEYS.IS_NOTIFICATION_ACTIVE, notificationSwitch.isChecked()));
        presenter.getSettingStatus(NetworkConstatnts.RequestCode.API_GET_SETTINGS_STATUS, null, false);
    }

    @OnClick({R.id.back_toolbar, R.id.barber_modes_btn, R.id.payment_mode_btn, R.id.notification_btn,
            R.id.services_btn, R.id.distric_mode_btn, R.id.address_btn, R.id.notification_switch, R.id.cancellation_switch})
    public void onClick(View view) {
        Bundle bundle;
        switch (view.getId()) {
            case R.id.back_toolbar:
                onBackPressed();
                break;
            case R.id.barber_modes_btn:
                bundle = new Bundle();
                bundle.putString(GlobalValues.KEYS.FROM, GlobalValues.KEYS.MORE);
                activitySwitcher(SettingActivity.this, WelcomeActivity.class, bundle);
                break;
            case R.id.payment_mode_btn:
                bundle = new Bundle();
                bundle.putString(GlobalValues.KEYS.FROM, GlobalValues.KEYS.MORE);
                activitySwitcher(SettingActivity.this, PaymentTypeActivity.class, bundle);
                break;
            case R.id.address_btn:

                activitySwitcher(SettingActivity.this, AddressScreenActivity.class, null);
                break;
            case R.id.services_btn:
                bundle = new Bundle();
                bundle.putString(GlobalValues.KEYS.FROM, SettingActivity.class.getName());
                activitySwitcher(SettingActivity.this, ServiceListActivity.class, bundle);
                break;
            case R.id.distric_mode_btn:
                bundle = new Bundle();
                bundle.putString(GlobalValues.KEYS.FROM, SettingActivity.class.getName());
                activitySwitcher(SettingActivity.this, SelectZoneActivity.class, bundle);
                break;
            case R.id.notification_switch:

                break;
            case R.id.cancellation_switch:
                updateStatus();

                break;
        }
    }

    private void updateStatus() {

        SettingChageRequest cRequest = new SettingChageRequest();
        cRequest.setType(GlobalValues.SettingTypes.CANCELLATION);
        cRequest.setChecked(cancellationSwitch.isChecked());
        presenter.updateSettingStatus(NetworkConstatnts.RequestCode.API_CHANGE_SETTING, cRequest, false);

    }

    @Override
    public void onSuccessResponse(int serviceMode, Object o) {
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_GET_SETTINGS_STATUS:
                SettingStatusResponse responseData = (SettingStatusResponse) o;
                if (responseData != null) {
                    cancellationSwitch.setChecked(responseData.getData().isIsCancellationChargeNotification());
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
