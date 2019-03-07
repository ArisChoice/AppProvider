package com.app.barber.ui.postauth.activities.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;

import com.app.barber.R;
import com.app.barber.core.BarberApplication;
import com.app.barber.core.BaseActivity;
import com.app.barber.models.response.BarberStatsResponse;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.ui.postauth.activities.settings.settingmvp.SettingMVPView;
import com.app.barber.ui.postauth.activities.settings.settingmvp.SettingPresenter;
import com.app.barber.util.CommonUtils;
import com.app.barber.util.ImageUtility;
import com.app.barber.views.CustomTextView;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by harish on 7/1/19.
 */

public class BusinessStatActivity extends BaseActivity implements SettingMVPView {
    @BindView(R.id.back_toolbar)
    ImageView backToolbar;
    @BindView(R.id.txt_title_toolbar)
    CustomTextView txtTitleToolbar;
    @BindView(R.id.img_edit)
    ImageView imgEdit;
    @BindView(R.id.toolbar_common)
    Toolbar toolbarCommon;
    @BindView(R.id.banner_image_profile)
    SimpleDraweeView bannerImageProfile;
    @BindView(R.id.image_profile)
    SimpleDraweeView imageProfile;
    @BindView(R.id.user_name)
    CustomTextView userName;
    @BindView(R.id.user_profile)
    CustomTextView userProfile;
    @BindView(R.id.barber_ratings)
    RatingBar barberRatings;
    @BindView(R.id.barber_status)
    ImageView barberStatus;
    @BindView(R.id.total_appointments)
    CustomTextView totalAppointments;
    @BindView(R.id.total_views)
    CustomTextView totalViews;
    @BindView(R.id.total_conversion_rate)
    CustomTextView totalConversionRate;
    @BindView(R.id.total_response_rate)
    CustomTextView totalResponseRate;
    @BindView(R.id.total_response_time)
    CustomTextView totalResponseTime;
    @BindView(R.id.image_expertise)
    ImageView imageExpertise;
    @BindView(R.id.skills_count_txt_main)
    CustomTextView skillCountTxtMain;
    @BindView(R.id.service_count_txt_main)
    CustomTextView serviceCountTxtMain;
    @BindView(R.id.cleaness_count_txt_main)
    CustomTextView cleanCountTxtMain;
    @BindView(R.id.txt_btn)
    CustomTextView txtBtn;
    @BindView(R.id.ayg_rating)
    CustomTextView aygRating;
    @BindView(R.id.ratingbar_avg_rate)
    RatingBar ratingbarAvgRate;
    @BindView(R.id.avg_rate_count)
    CustomTextView avgRateCount;
    @BindView(R.id.five_star_progress)
    ProgressBar fiveStarProgress;
    @BindView(R.id.five_star_count)
    CustomTextView fiveStarCount;
    @BindView(R.id.four_star_progress)
    ProgressBar fourStarProgress;
    @BindView(R.id.four_star_count)
    CustomTextView fourStarCount;
    @BindView(R.id.three_star_progress)
    ProgressBar threeStarProgress;
    @BindView(R.id.three_star_count)
    CustomTextView threeStarCount;
    @BindView(R.id.two_star_progress)
    ProgressBar twoStarProgress;
    @BindView(R.id.two_star_count)
    CustomTextView twoStarCount;
    @BindView(R.id.one_star_progress)
    ProgressBar oneStarProgress;
    @BindView(R.id.one_star_count)
    CustomTextView oneStarCount;
    @BindView(R.id.punctuality_rating)
    CustomTextView punctualityRating;
    @BindView(R.id.punctuality_ratingbar)
    RatingBar punctualityRatingbar;
    @BindView(R.id.punctuality_rating_cunt)
    CustomTextView punctualityRatingCunt;
    @BindView(R.id.status_super_barber_bookings)
    CustomTextView statusSuperBarberBookings;
    @BindView(R.id.status_super_barber_punch_rating)
    CustomTextView statusSuperBarberPunchRating;
    @BindView(R.id.status_super_barber_avg_rating)
    CustomTextView statusSuperBarberAvgRating;
    private SettingPresenter presenter;

    /**
     * @return layout resource id
     */
    @Override
    public int getLayoutId() {
        return R.layout.layout_business_stats_screen;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BarberApplication) getApplication()).getMyComponent(BusinessStatActivity.this).inject(this);
        presenter = new SettingPresenter(BusinessStatActivity.this);
        presenter.attachView(this);
        txtTitleToolbar.setText(R.string.str_business_stats);
        bringToFront();
        if (getUserData() != null) {
            userName.setText(getUserData().getFullName());
            if (getUserData().getProfileImage() != null && !getUserData().getProfileImage().equals("")) {
                imageProfile.setImageURI(ImageUtility.getValidUrl(getUserData().getProfileImage()));
            }
        }
        getBarberStats();
    }

    private void getBarberStats() {
        presenter.getBarberStats(NetworkConstatnts.RequestCode.API_GET_BARBER_STATS, "Year", true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    private void bringToFront() {
        skillCountTxtMain.bringToFront();
        serviceCountTxtMain.bringToFront();
        cleanCountTxtMain.bringToFront();
        skillCountTxtMain.invalidate();
    }

    @OnClick(R.id.back_toolbar)
    public void onClick() {
        onBackPressed();
    }

    @Override
    public void onSuccessResponse(int serviceMode, Object o) {
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_GET_BARBER_STATS:
                BarberStatsResponse responseData = (BarberStatsResponse) o;
                if (responseData.getData() != null)
                    updateView(responseData.getData());
                break;
        }
    }

    private void updateView(BarberStatsResponse.DataBean data) {
        totalAppointments.setText(data.getTotalAppointmentsCount());
        totalViews.setText(data.getPageViews());
        ratingbarAvgRate.setRating(Float.parseFloat(data.getAvgRating()));
        aygRating.setText(data.getAvgRating());
        barberRatings.setRating(Float.parseFloat(data.getAvgRating()));


        punctualityRating.setText(data.getAvgPuchRating());
        punctualityRatingbar.setRating(Float.parseFloat(data.getAvgPuchRating()));
        punctualityRatingCunt.setText(data.getAvgRatingCount() + " Ratings");

        skillCountTxtMain.setText(CommonUtils.formatDecimalPoint(data.getAvgRating(), 1));
        serviceCountTxtMain.setText(CommonUtils.formatDecimalPoint(data.getAvgPuchRating(), 1));
        cleanCountTxtMain.setText(CommonUtils.formatDecimalPoint(data.getAvgRating(), 1));
        avgRateCount.setText(data.getAvgRatingCount() + " Ratings");
        if (Integer.parseInt(data.getTotalAppointmentsCount()) >= 300) {
            statusSuperBarberBookings.setText(R.string.str_completed);
            statusSuperBarberBookings.setTextColor(getResources().getColor(R.color.color_green));
        }

        if (Double.parseDouble(data.getAvgRating()) >= 4) {
            statusSuperBarberAvgRating.setText(R.string.str_completed);
            statusSuperBarberAvgRating.setTextColor(getResources().getColor(R.color.color_green));
        }
        if (Double.parseDouble(data.getAvgPuchRating()) >= 4) {
            statusSuperBarberPunchRating.setText(R.string.str_completed);
            statusSuperBarberPunchRating.setTextColor(getResources().getColor(R.color.color_green));
        }


        try {
            if (data.getReviewCount() > 0) {
                oneStarCount.setText("" + data.getOneStarRatingCount());
                oneStarProgress.setProgress((int) (((float) data.getOneStarRatingCount() / (float) data.getReviewCount()) * 100));

                twoStarCount.setText("" + data.getTwoStarRatingCount());
                twoStarProgress.setProgress((int) (((float) data.getTwoStarRatingCount() / (float) data.getReviewCount()) * 100));

                threeStarCount.setText("" + data.getThreeStarRatingCount());
                threeStarProgress.setProgress((int) (((float) data.getThreeStarRatingCount() / (float) data.getReviewCount()) * 100));

                fourStarCount.setText("" + data.getFourStarRatingCount());
                fourStarProgress.setProgress((int) (((float) data.getFourStarRatingCount() / (float) data.getReviewCount()) * 100));

                fiveStarCount.setText("" + data.getFiveStarRatingCount());
                fiveStarProgress.setProgress((int) (((float) data.getFiveStarRatingCount() / (float) data.getReviewCount()) * 100));
            }
        } catch (Exception e) {
            e.printStackTrace();
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
