package com.app.barber.ui.adapters.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.app.barber.BuildConfig;
import com.app.barber.ui.postauth.fragment.AppointsmentsFragment;
import com.app.barber.ui.postauth.fragment.CallOutFragment;
import com.app.barber.ui.postauth.fragment.ClientsFragment;
import com.app.barber.ui.postauth.fragment.MessageFragment;
import com.app.barber.ui.postauth.fragment.MoreFragment;
import com.app.barber.ui.postauth.fragment.OverviewFragment;


/**
 * Created by xicom on 9/5/16.
 */
public class CalloutPagerAdapter extends FragmentPagerAdapter {

    public static int COUNT_FRAGMENTS = 2;
    Context applicationContext;
    FragmentManager fragmentManager;

    public CalloutPagerAdapter(Context applicationContext, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.fragmentManager = fragmentManager;
        this.applicationContext = applicationContext;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (BuildConfig.DEBUG) {
            Log.d("CURRENT POSITION", "==>" + position);
        }
        switch (position) {
            case 0:
                fragment = new AppointsmentsFragment();
                break;
            case 1:
                fragment = new OverviewFragment();
                break;

           /* default:
                BaseActivity.menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                fragment = new LandingFragment();
                break;*/
        }
        return fragment;
    }

    public static void setCountFragments(int countFragments) {
        COUNT_FRAGMENTS = countFragments;
    }

    @Override
    public int getCount() {
        return COUNT_FRAGMENTS;
    }
}
