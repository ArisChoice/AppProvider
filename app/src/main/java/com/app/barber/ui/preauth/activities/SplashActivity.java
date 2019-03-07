package com.app.barber.ui.preauth.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.app.barber.R;
import com.app.barber.core.BarberApplication;
import com.app.barber.core.BaseActivity;
import com.app.barber.net.firebase.MyFirebaseInstanceIDService;
import com.app.barber.ui.postauth.activities.HomeActivity;
import com.app.barber.ui.postauth.activities.basic.AddWorkspacePhotos;
import com.app.barber.ui.postauth.activities.basic.AddressActivity;
import com.app.barber.ui.postauth.activities.basic.AddressSelectionActivity;
import com.app.barber.ui.postauth.activities.basic.CalloutOpeningHoursActivity;
import com.app.barber.ui.postauth.activities.basic.DisplayImageActivity;
import com.app.barber.ui.postauth.activities.basic.PaymentTypeActivity;
import com.app.barber.ui.postauth.activities.basic.SearchAddressActivity;
import com.app.barber.ui.postauth.activities.basic.SelectZoneActivity;
import com.app.barber.ui.postauth.activities.basic.ServiceListActivity;
import com.app.barber.ui.postauth.activities.basic.SpecialiseActivity;
import com.app.barber.ui.postauth.activities.basic.WelcomeActivity;
import com.app.barber.ui.postauth.activities.settings.PaymentAuthanticationSetupActivity;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.PreferenceManager;
import com.google.firebase.crash.FirebaseCrash;

import javax.inject.Inject;

/**
 * Created by harish on 16/10/18.
 */

public class SplashActivity extends BaseActivity {
    @Inject
    PreferenceManager mPref;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash_screen;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BarberApplication) getApplication()).getMyComponent(this).inject(this);
//        activitySwitcher(SplashActivity.this, AddressSelectionActivity.class, null);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mPref.getPrefrencesBoolean(GlobalValues.KEYS.isLogedIn) && getUserData() != null) {
                    if (getUserData().getPhoneNumber() == null || getUserData().getPhoneNumber().equals(""))
                        activitySwitcher(SplashActivity.this, AddMobileNumberActivity.class, null);
                    else activitySwitcher(SplashActivity.this, HomeActivity.class, null);
                } else activitySwitcher(SplashActivity.this, LandingActivity.class, null);
                finish();
            }
        }, GlobalValues.TIME_DURATIONS.EXTRA_LARGE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // GET GOOGLE  TOKEN FOR PUSH NOTIFICATION....................
        new StartRegisterationService().execute();
    }

    private class StartRegisterationService extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(SplashActivity.this, MyFirebaseInstanceIDService.class);
            startService(intent);
            return null;
        }
    }
}
