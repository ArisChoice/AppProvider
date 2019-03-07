package com.app.barber.ui.postauth.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.barber.R;
import com.app.barber.core.BarberApplication;
import com.app.barber.core.BaseActivity;
import com.app.barber.models.request.UpdateEditBookingStatusModel;
import com.app.barber.models.response.AppointmentsResponseModel;
import com.app.barber.models.response.CheckQbIdResponseModel;
import com.app.barber.models.response.LoginResponseModel;
import com.app.barber.models.response.RecentStatusResponseModel;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.ui.adapters.pager.HomePagerAdapter;
import com.app.barber.ui.postauth.activities.home.ProfileScreenActivity;
import com.app.barber.ui.postauth.activities.home.homemvp.HomeAuthMVPView;
import com.app.barber.ui.postauth.activities.home.homemvp.HomeAuthPresenter;
import com.app.barber.ui.postauth.activities.settings.NotificationActivity;
import com.app.barber.ui.postauth.activities.socket_work.chat.ChatActivity;
import com.app.barber.util.CommonUtils;
import com.app.barber.util.FunctionalDialog;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.iface.OnBottomDialogItemListener;
import com.app.barber.views.CustomTextView;
import com.app.barber.views.CustomViewPager;
import com.facebook.drawee.view.SimpleDraweeView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity implements HomeAuthMVPView {

    private static HomeActivity activity;
    @BindView(R.id.home_pager)
    CustomViewPager homePager;
    @BindView(R.id.navigation)
    BottomNavigationViewEx navigation;
    @BindView(R.id.left_img)
    SimpleDraweeView leftImg;
    @BindView(R.id.txt_title_toolbar)
    CustomTextView txtTitleToolbar;
    @BindView(R.id.img_edit)
    ImageView imgEdit;
    @BindView(R.id.drawer_layout)
    LinearLayout drawerLayout;
    @BindView(R.id.toolbar_root)
    Toolbar toolbarRoot;
    @BindView(R.id.notification_ico)
    CustomTextView notificationIco;
    private HomePagerAdapter homePagerAdapter;
    private HomeAuthPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BarberApplication) getApplication()).getMyComponent(this).inject(this);
        activity = this;
        presenter = new HomeAuthPresenter(this);
        presenter.attachView(this);
        initPager();
        initBottomToolbar();
        checkQb();
//        updateMenuFonts();
    }

    private void checkQb() {
        if (getUserData() != null && getUserData().getQBId() == null || getUserData().getQBId().equals(""))
            checkQuickBloxid();
    }

    private void checkQuickBloxid() {
        presenter.checkQbId(NetworkConstatnts.RequestCode.API_CHECK_QB_ID, null, false);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }


    private void initBottomToolbar() {
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.enableAnimation(false);
        navigation.enableShiftingMode(false);
        navigation.enableItemShiftingMode(false);
//      navigation.setTextSize(0f);
        navigation.setIconSize(20, 20);
        navigation.setTextVisibility(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getUserData() != null && getUserData().getProfileImage() != null && !getUserData().getProfileImage().equals(""))
            leftImg.setImageURI(CommonUtils.getValidUrl(getUserData().getProfileImage()));
        checkRecentStatus();

        Log.e(" onResume  ", "  " + navigation.getCurrentItem());
    }

    private void checkRecentStatus() {
        presenter.checkRecentStatus(NetworkConstatnts.RequestCode.API_CHECK_RECENT_STATUS, null, false);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //reference to the item of the menu

            switch (item.getItemId()) {
                case R.id.navigation_appointments:
                    homePager.setCurrentItem(0);
//                    backToolbar.setVisibility(View.INVISIBLE);
                    txtTitleToolbar.setText(R.string.str_today);
                    toolbarRoot.setVisibility(View.VISIBLE);
                    showCommonViews();
                    refreshNotificationAlert();
//                    changeThisView();
                    return true;
                case R.id.navigation_Callouts:
                    homePager.setCurrentItem(1);
                    txtTitleToolbar.setText(R.string.title_Callouts);
                    toolbarRoot.setVisibility(View.GONE);
                    hideCommonViews();
                    refreshNotificationAlert();
                    return true;
                case R.id.navigation_clients:
                    homePager.setCurrentItem(2);
//                    backToolbar.setVisibility(View.INVISIBLE);
                    txtTitleToolbar.setText(R.string.str_clients);
                    toolbarRoot.setVisibility(View.VISIBLE);
                    hideCommonViews();
                    refreshNotificationAlert();
                    return true;
                case R.id.navigation_Message:
                    homePager.setCurrentItem(3);
                    txtTitleToolbar.setText(R.string.title_Message);
                    toolbarRoot.setVisibility(View.VISIBLE);
                    hideCommonViews();
                    refreshNotificationAlert();
//                  new CommonUtils().ShowToast("Work in progress");
                    return true;
                case R.id.navigation_more:
                    homePager.setCurrentItem(4);
                    toolbarRoot.setVisibility(View.GONE);
                    hideCommonViews();
                    refreshNotificationAlert();
                    return true;
            }
            return false;
        }
    };

    /*private void changeThisView() {
        BottomNavigationMenuView mbottomNavigationMenuView =
                (BottomNavigationMenuView) navigation.getChildAt(0);
        View view = mbottomNavigationMenuView.getChildAt(0);

        BottomNavigationItemView itemView = (BottomNavigationItemView) view;

        View cart_badge = LayoutInflater.from(this)
                .inflate(R.layout.layout_custom_tab, mbottomNavigationMenuView, false);
        itemView.removeAllViews();
        itemView.addView(cart_badge);
    }*/

    private void hideCommonViews() {
        leftImg.setVisibility(View.INVISIBLE);
        imgEdit.setVisibility(View.GONE);
        notificationIco.setVisibility(View.GONE);
    }

    private void showCommonViews() {
        leftImg.setVisibility(View.VISIBLE);
        imgEdit.setVisibility(View.VISIBLE);
    }

    /**
     * initialize pager
     */
    private void initPager() {
        homePagerAdapter = new HomePagerAdapter(getApplicationContext(), getSupportFragmentManager());
        homePager.setAdapter(homePagerAdapter);
        homePager.setPagingEnabled(true);
        homePager.setOffscreenPageLimit(0);
        homePager.swipeable = false;
        homePager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    //double tap to close app
    boolean doubleBackToExitPressedOnce;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(HomeActivity.this, R.string.back_alert_message, Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 3500);
    }

    @OnClick({R.id.left_img, R.id.img_edit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_img:
                activitySwitcher(HomeActivity.this, ProfileScreenActivity.class, null);
                break;
            case R.id.img_edit:
                activitySwitcher(HomeActivity.this, NotificationActivity.class, null);
                break;
        }
    }

    Dialog dialog, dialog1 = null;

    @Override
    public void onSuccessResponse(int serviceMode, Object o) {
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_CHECK_RECENT_STATUS:
                RecentStatusResponseModel responseData = (RecentStatusResponseModel) o;
                if (responseData != null) {
                    mPref.setPrefrencesBoolean(GlobalValues.KEYS.SHOW_NOTIFICATION_RED_DOT, responseData.getResponse().isIsNotificationPending());
                    mPref.setPrefrencesBoolean(GlobalValues.KEYS.BLOCK_NOTIFICATION_APPOINTMENTS_CALLOUOTS, responseData.getResponse().isBlockNotification());
                }
                refreshNotificationAlert();

                if (dialog == null) {
                    openValidationAlert(responseData);
                } else {
                    dialog.dismiss();
                    openValidationAlert(responseData);
                }
                if (responseData.getResponse().getBarberEditList() != null && responseData.getResponse().getBarberEditList().size() > 0) {
                    if (dialog1 == null)
                        dialog1 = new FunctionalDialog().openEditRequestsDialog(HomeActivity.this, responseData.getResponse().getBarberEditList(), getUserData(), new OnBottomDialogItemListener() {
                            @Override
                            public void onItemClick(View view, int position, int type, Object t) {
                                UpdateEditBookingStatusModel requestData;
                                AppointmentsResponseModel.ResponseBean.BookingListBean positionData;
                                switch (type) {
                                    case GlobalValues.RequestCodes.CALL:
                                        positionData = (AppointmentsResponseModel.ResponseBean.BookingListBean) t;
                                        if (positionData.getPhone() != null)
                                            callInit(positionData.getPhone());
                                        break;
                                    case GlobalValues.RequestCodes.MESSAGE:
                                        positionData = (AppointmentsResponseModel.ResponseBean.BookingListBean) t;
                                        if (positionData.getChatDialog() != null) {
                                            Intent intent = new Intent(activity, ChatActivity.class);
                                            intent.putExtra(GlobalValues.KEYS.EXTRA_DIALOG_ID, positionData.getChatDialog());
                                            intent.putExtra(GlobalValues.KEYS.OTHER_IMAGE, positionData.getUserImage());
                                            intent.putExtra(GlobalValues.KEYS.OTHER_IMAGE, "" + positionData.getUserId());
                                            startActivity(intent);
                                        }
                                        break;
                                    case GlobalValues.RequestCodes.REJECT_REQUEST:
                                        requestData = (UpdateEditBookingStatusModel) t;
                                        presenter.updateEditStatus(NetworkConstatnts.RequestCode.API_EDIT_BOOKING_STATUS, requestData, true);
                                        break;
                                    case GlobalValues.RequestCodes.ACCEPT_REQUEST:
                                        requestData = (UpdateEditBookingStatusModel) t;
                                        presenter.updateEditStatus(NetworkConstatnts.RequestCode.API_EDIT_BOOKING_STATUS, requestData, true);
                                        break;
                                    case GlobalValues.RequestCodes.CANCED_REQUEST:
                                        requestData = (UpdateEditBookingStatusModel) t;
                                        presenter.updateEditStatus(NetworkConstatnts.RequestCode.API_EDIT_BOOKING_STATUS, requestData, true);
                                        break;
                                }
                            }
                        });
                }
                break;
            case NetworkConstatnts.RequestCode.API_CHECK_QB_ID:
                CheckQbIdResponseModel rData = (CheckQbIdResponseModel) o;
                LoginResponseModel.UserBean userData = getUserData();
                userData.setQBId(String.valueOf(rData.getId()));
                presenter.saveUserData(userData);
//                presenter.initQbUser(userData);
                break;
        }
    }

    private void refreshNotificationAlert() {
        if (mPref.getPrefrencesBoolean(GlobalValues.KEYS.SHOW_NOTIFICATION_RED_DOT) && homePager.getCurrentItem() == 0) {
            notificationIco.setVisibility(View.VISIBLE);
        } else {
            notificationIco.setVisibility(View.GONE);
        }
    }

    private void openValidationAlert(RecentStatusResponseModel responseData) {
        if (presenter.isAnyThingRequired(responseData.getResponse(), getUserData()))
            dialog = new FunctionalDialog().validationRequirementsDialog(HomeActivity.this, responseData, getUserData(), new OnBottomDialogItemListener() {
                @Override
                public void onItemClick(View view, int position, int type, Object t) {

                }
            });
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

    public static HomeActivity getInstance() {
        return activity;
    }

    public void navigateView(int i) {
        switch (i) {
            case 1:
                new Handler().post(() -> {
                    homePager.setCurrentItem(1);
                    txtTitleToolbar.setText(R.string.title_Callouts);
                    toolbarRoot.setVisibility(View.GONE);
                    hideCommonViews();
                    navigation.setCurrentItem(1);
                });
                break;
        }

    }
}
