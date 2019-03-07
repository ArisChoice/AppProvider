package com.app.barber.ui.preauth.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.app.barber.R;
import com.app.barber.core.BaseActivity;
import com.app.barber.ui.adapters.SlidingImage_Adapter;
import com.app.barber.views.CustomTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by harish on 22/10/18.
 */

public class LandingActivity extends BaseActivity {
    @BindView(R.id.sign_in_btn)
    CustomTextView signInBtn;
    @BindView(R.id.register_btn_btn)
    CustomTextView registerBtnBtn;
    @BindView(R.id.welcome_pager)
    ViewPager welcomePager;
    private static final Integer[] IMAGES = {R.drawable.welcom1_and, R.drawable.welcom2_and, R.drawable.welcom3_and};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();

    @Override
    public int getLayoutId() {
        return R.layout.layout_landing_screen;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPager();
    }

    private void initPager() {
        for (int i = 0; i < IMAGES.length; i++)
            ImagesArray.add(IMAGES[i]);
        welcomePager.setAdapter(new SlidingImage_Adapter(LandingActivity.this, ImagesArray));
    }

    @OnClick({R.id.sign_in_btn, R.id.register_btn_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_btn:
                activitySwitcher(LandingActivity.this, LoginActivity.class, null);
                break;
            case R.id.register_btn_btn:
                activitySwitcher(LandingActivity.this, RegisterActivity.class, null);
                break;
        }
    }
}
