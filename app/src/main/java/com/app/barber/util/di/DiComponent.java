package com.app.barber.util.di;


import com.app.barber.core.BaseActivity;
import com.app.barber.core.BaseFragment;
import com.app.barber.core.core_mvp.CorePresenter;
import com.app.barber.net.firebase.MyFirebaseInstanceIDService;
import com.app.barber.net.firebase.MyFirebaseMessagingService;
import com.app.barber.ui.postauth.activities.chat.chatmvp.ChatPresenter;
import com.app.barber.ui.postauth.activities.client.addclientmvp.AddClientPresenter;
import com.app.barber.ui.postauth.activities.home.BookingSheetFragment;
import com.app.barber.ui.postauth.activities.settings.SettingActivity;
import com.app.barber.ui.postauth.activities.settings.settingmvp.SettingPresenter;
import com.app.barber.ui.postauth.fragment.AppointsmentsFragment;
import com.app.barber.ui.postauth.fragment.OverviewFragment;
import com.app.barber.ui.preauth.activities.SplashActivity;
import com.app.barber.ui.postauth.activities.basic.basicmvp.BssicAuthPresenter;
import com.app.barber.ui.postauth.activities.basic.CalloutOpeningHoursActivity;
import com.app.barber.ui.postauth.activities.basic.SpecialiseActivity;
import com.app.barber.ui.postauth.activities.home.homemvp.HomeAuthPresenter;
import com.app.barber.ui.postauth.activities.home.ProfileScreenActivity;
import com.app.barber.ui.postauth.fragment.MoreFragment;
import com.app.barber.ui.preauth.authmvp.PreAuthPresenter;
import com.app.barber.ui.preauth.activities.LoginActivity;


import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Harish on 9/11/16.
 */


@Singleton
@Component(modules = {DiModule.class})

public interface DiComponent {
    void inject(SplashActivity splashActivityActivity);

    void inject(LoginActivity signupActivity);

    void inject(MyFirebaseInstanceIDService myFirebaseInstanceIDService);

    void inject(BaseActivity baseActivity);

    void inject(BaseFragment baseFragment);

    void inject(PreAuthPresenter preAuthPresenter);

    void inject(BssicAuthPresenter bssicAuthPresenter);

    void inject(SpecialiseActivity specialisation);

    void inject(CalloutOpeningHoursActivity callOut);


    void inject(HomeAuthPresenter homeAuthPresenter);

    void inject(ProfileScreenActivity profileScreen);

    void inject(MoreFragment profileScreen);

    void inject(AppointsmentsFragment apFrag);

    void inject(OverviewFragment ovrFrag);

    void inject(MyFirebaseMessagingService myFirebaseMessagingService);

    void inject(SettingPresenter settingPresenter);

    void inject(AddClientPresenter addClientPresenter);

    void inject(ChatPresenter chatPresenter);

    void inject(CorePresenter corePresenter);

    void inject(SettingActivity sActivity);

    void inject(BookingSheetFragment bookingSheetFragment);

    // to update the fields in your activities


}