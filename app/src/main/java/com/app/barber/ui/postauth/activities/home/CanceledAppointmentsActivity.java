package com.app.barber.ui.postauth.activities.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.app.barber.R;
import com.app.barber.core.BarberApplication;
import com.app.barber.core.BaseActivity;
import com.app.barber.models.request.AppointRequestModel;
import com.app.barber.models.response.AppointmentsResponseModel;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.ui.adapters.AppointmentsAdapter;
import com.app.barber.ui.postauth.activities.home.homemvp.HomeAuthMVPView;
import com.app.barber.ui.postauth.activities.home.homemvp.HomeAuthPresenter;
import com.app.barber.util.CustomDate;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.util.weekengine.ModelDay;
import com.app.barber.util.weekengine.WeekViewAdapter;
import com.app.barber.views.CustomTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by harish on 7/1/19.
 */

public class CanceledAppointmentsActivity extends BaseActivity implements HomeAuthMVPView {
    @BindView(R.id.back_toolbar)
    ImageView backToolbar;
    @BindView(R.id.txt_title_toolbar)
    CustomTextView txtTitleToolbar;
    @BindView(R.id.img_edit)
    ImageView imgEdit;
    @BindView(R.id.toolbar_common)
    Toolbar toolbarCommon;
    @BindView(R.id.month_name)
    CustomTextView monthName;
    @BindView(R.id.more_txt)
    CustomTextView moreTxt;
    @BindView(R.id.recyclar_view_lst)
    RecyclerView recyclarViewLst;
    @BindView(R.id.no_list_data_text)
    CustomTextView noListDataText;
    @BindView(R.id.canceled_list)
    RecyclerView canceledList;
    @BindView(R.id.block_floating_btn)
    ImageView blockFloatingBtn;
    private HomeAuthPresenter presenter;
    private AppointmentsAdapter appointmentsAdapter;
    private WeekViewAdapter weekAdapter;

    /**
     * @return layout resource id
     */
    @Override
    public int getLayoutId() {
        return R.layout.layout_canceled_screen;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BarberApplication) getApplication()).getMyComponent(CanceledAppointmentsActivity.this).inject(this);
        presenter = new HomeAuthPresenter(CanceledAppointmentsActivity.this);
        presenter.attachView(this);
        txtTitleToolbar.setText(R.string.str_cancelled_appointments);
        blockFloatingBtn.setVisibility(View.GONE);
        initWeekView();
        initAppoitmnetsAdapter();
    }

    private void initWeekView() {
        weekAdapter = new WeekViewAdapter(CanceledAppointmentsActivity.this, new ArrayList<ModelDay>(), new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int type, Object o) {
                switch (type) {
                    case GlobalValues.ClickOperations.DATE_CLICKED:
                        ModelDay selectedDateModel = (ModelDay) o;
                        getCanceledAppoinments(selectedDateModel.getFullDate());
                        break;
                }
            }
        });
        LinearLayoutManager lManager = new LinearLayoutManager(CanceledAppointmentsActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclarViewLst.setLayoutManager(lManager);
        recyclarViewLst.setAdapter(weekAdapter);
        weekAdapter.setCurrentWeek(7);
//        weekAdapter.getPastWeek(7);
        monthName.setText(CustomDate.getCurrentMonth(CanceledAppointmentsActivity.this, "MMMM yyyy"));
        getCanceledAppoinments(CustomDate.getCurrentMonth(CanceledAppointmentsActivity.this, "MM/dd/yyyy"));//get current day appointments
    }

    private void initAppoitmnetsAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(CanceledAppointmentsActivity.this, LinearLayoutManager.VERTICAL, false);
        appointmentsAdapter = new AppointmentsAdapter(CanceledAppointmentsActivity.this, CanceledAppointmentsActivity.class.getSimpleName(), new ArrayList<>(), new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int type, Object o) {
                switch (type) {

                }

            }
        });
        canceledList.setLayoutManager(layoutManager);
        canceledList.setAdapter(appointmentsAdapter);
        canceledList.setNestedScrollingEnabled(false);

    }

    private void getCanceledAppoinments(String fullDate) {
        AppointRequestModel requestAppointment = new AppointRequestModel();
        requestAppointment.setDate(fullDate);
        presenter.getCanceledAppointments(NetworkConstatnts.RequestCode.API_CANCELED_APPOINTMENTS, requestAppointment, false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onSuccessResponse(int serviceMode, Object o) {
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_CANCELED_APPOINTMENTS:
                AppointmentsResponseModel appointmentsData = (AppointmentsResponseModel) o;
                updateViewsData(appointmentsData.getResponse());
                break;
        }
    }

    private void updateViewsData(AppointmentsResponseModel.ResponseBean response) {
        if (response.getBookingList() != null && response.getBookingList().size() > 0) {
            appointmentsAdapter.updateAll(response.getBookingList());
            canceledList.setVisibility(View.VISIBLE);
            noListDataText.setVisibility(View.GONE);
        } else {
            appointmentsAdapter.clearAll();
            canceledList.setVisibility(View.GONE);
            noListDataText.setVisibility(View.VISIBLE);
            noListDataText.setText(R.string.str_no_appointments);
            noListDataText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
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

    @OnClick({R.id.back_toolbar, R.id.more_txt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_toolbar:
                onBackPressed();
                break;
            case R.id.more_txt:
                if (weekAdapter.getItemCount() <= 7) {
                    weekAdapter.setCurrentWeek(weekAdapter.getItemCount() + 35);//add more days upto 35 days
                    weekAdapter.notifyDataSetChanged();
                    moreTxt.setVisibility(View.GONE);
                }
                break;
        }
    }
}
