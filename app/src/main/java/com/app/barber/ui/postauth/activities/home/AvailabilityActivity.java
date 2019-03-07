package com.app.barber.ui.postauth.activities.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.app.barber.R;
import com.app.barber.core.BaseActivity;
import com.app.barber.ui.postauth.activities.basic.CalloutOpeningHoursActivity;
import com.app.barber.ui.postauth.activities.settings.ClientCanBookActivity;
import com.app.barber.util.CommonUtils;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.iface.OnBottomDialogItemListener;
import com.app.barber.views.CustomTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by harish on 6/11/18.
 */

public class AvailabilityActivity extends BaseActivity {
    @BindView(R.id.back_toolbar)
    ImageView backToolbar;
    @BindView(R.id.txt_title_toolbar)
    CustomTextView txtTitleToolbar;
    @BindView(R.id.img_edit)
    ImageView imgEdit;
    @BindView(R.id.opening_hrs_btn)
    CustomTextView openingHrsBtn;
    @BindView(R.id.callout_hours_btn)
    CustomTextView calloutHoursBtn;
    @BindView(R.id.block_hrs_btn)
    CustomTextView blockHrsBtn;
    @BindView(R.id.how_far_btn)
    CustomTextView howFarBtn;

    @Override
    public int getLayoutId() {
        return R.layout.layout_availability_screen;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        txtTitleToolbar.setText(R.string.str_availabliity);
        txtTitleToolbar.setVisibility(View.VISIBLE);
        if (getUserData() != null && !getUserData().getBarberType().contains("1")) {
            openingHrsBtn.setVisibility(View.GONE);
        } else if (getUserData() != null && !getUserData().getBarberType().contains("2")) {
            calloutHoursBtn.setVisibility(View.GONE);
        } else if (getUserData() != null && !getUserData().getBarberType().contains("1") && !getUserData().getBarberType().contains("2")) {
            openingHrsBtn.setVisibility(View.GONE);
            calloutHoursBtn.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.back_toolbar, R.id.opening_hrs_btn, R.id.callout_hours_btn, R.id.block_hrs_btn, R.id.how_far_btn})
    public void onClick(View view) {
        Bundle bundle;
        switch (view.getId()) {
            case R.id.back_toolbar:
                onBackPressed();
                break;
            case R.id.opening_hrs_btn:
                bundle = new Bundle();
                bundle.putString(GlobalValues.KEYS.FROM, GlobalValues.KEYS.MORE);
                bundle.putString(GlobalValues.KEYS.TYPE, GlobalValues.KEYS.ADD_WORKING_HOURS);
                activitySwitcher(AvailabilityActivity.this, CalloutOpeningHoursActivity.class, bundle);
                break;
            case R.id.callout_hours_btn:
                bundle = new Bundle();
                bundle.putString(GlobalValues.KEYS.FROM, GlobalValues.KEYS.MORE);
                bundle.putString(GlobalValues.KEYS.TYPE, GlobalValues.KEYS.ADD_CALLOUT_HOURS);
//                activitySwitcher(AvailabilityActivity.this, HoursActivity.class, bundle);
                activitySwitcher(AvailabilityActivity.this, CalloutOpeningHoursActivity.class, bundle);
                break;
            case R.id.block_hrs_btn:
                bundle = new Bundle();
                bundle.putString(GlobalValues.KEYS.FROM, GlobalValues.KEYS.ADD_BREAK_HOURS);
                activitySwitcher(AvailabilityActivity.this, BlockHoursActivity.class, null);
                break;
            case R.id.how_far_btn:
                bundle = new Bundle();
                activitySwitcher(AvailabilityActivity.this, ClientCanBookActivity.class, bundle);
                /*CommonUtils.getInstance(AvailabilityActivity.this).openDialogAdvanceHoursPicker(AvailabilityActivity.this, null, new OnBottomDialogItemListener() {
                    @Override
                    public void onItemClick(View view, int position, int type, Object t) {

                    }
                });*/
                break;
        }
    }
}
