package com.app.barber.ui.postauth.activities.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.barber.R;
import com.app.barber.core.BarberApplication;
import com.app.barber.core.BaseActivity;
import com.app.barber.models.TimeSlotsModel;
import com.app.barber.models.request.AddBlockHoursRequest;
import com.app.barber.models.request.AppointRequestModel;
import com.app.barber.models.request.GetFutureStatusRequest;
import com.app.barber.models.response.AppointmentsResponseModel;
import com.app.barber.models.response.BaseResponse;
import com.app.barber.models.response.BlockHoursResponse;
import com.app.barber.models.response.BlockedDatesResponse;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.ui.adapters.AppointmentsAdapter;
import com.app.barber.ui.postauth.activities.basic.CalloutOpeningHoursActivity;
import com.app.barber.ui.postauth.activities.home.home_adapter.BlockHoursAdapter;
import com.app.barber.ui.postauth.activities.home.homemvp.HomeAuthMVPView;
import com.app.barber.ui.postauth.activities.home.homemvp.HomeAuthPresenter;
import com.app.barber.util.CommonUtils;
import com.app.barber.util.CustomDate;
import com.app.barber.util.FunctionalDialog;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.iface.OnBottomDialogItemListener;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.util.weekengine.ModelDay;
import com.app.barber.util.weekengine.WeekViewAdapter;
import com.app.barber.views.CustomTextView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by harish on 7/1/19.
 */

public class BlockHoursActivity extends BaseActivity implements HomeAuthMVPView {
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
    @BindView(R.id.txt_btn)
    CustomTextView txtBtn;
    @BindView(R.id.go_past_btn)
    ImageView goPastBtn;
    @BindView(R.id.week_highlight_txt)
    CustomTextView weekHighlightTxt;
    @BindView(R.id.go_next_btn)
    ImageView goNextBtn;
    @BindView(R.id.calender_cntroll_)
    LinearLayout calenderCntroll;
    @BindView(R.id.block_floating_btn)
    ImageView blockFloatingBtn;
    private HomeAuthPresenter presenter;
    private WeekViewAdapter weekAdapter;
    private AddBlockHoursRequest adRequest;
    private AppointRequestModel requestBreakHours;
    private BlockHoursAdapter blockHoursAdapter;

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
        ((BarberApplication) getApplication()).getMyComponent(BlockHoursActivity.this).inject(this);
        presenter = new HomeAuthPresenter(BlockHoursActivity.this);
        presenter.attachView(this);
        txtTitleToolbar.setText(R.string.str_block_hours);
        moreTxt.setVisibility(View.GONE);
        initWeekView();
        initAppoitmnetsAdapter();
        adRequest = new AddBlockHoursRequest();
        requestBreakHours = new AppointRequestModel();
        adRequest.setDate(weekAdapter.getSelectedDate());
        requestBreakHours.setDate(weekAdapter.getSelectedDate());
        getBlockedHours();
    }

    private void initWeekView() {
        weekAdapter = new WeekViewAdapter(BlockHoursActivity.this, new ArrayList<ModelDay>(), new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int type, Object o) {
                switch (type) {
                    case GlobalValues.ClickOperations.DATE_CLICKED:
                        ModelDay selectedDateModel = (ModelDay) o;
                        adRequest.setDate(selectedDateModel.getFullDate());
                        requestBreakHours.setDate(selectedDateModel.getFullDate());
                        getBlockedHours();
                        break;
                }
            }
        });
        LinearLayoutManager lManager = new LinearLayoutManager(BlockHoursActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclarViewLst.setLayoutManager(lManager);
        recyclarViewLst.setAdapter(weekAdapter);
        weekAdapter.setCurrentWeek(35);
        monthName.setText(CustomDate.getCurrentMonth(BlockHoursActivity.this, "MMMM yyyy"));
        getSelectedDates(weekAdapter.getFirstDay(), weekAdapter.getEndDay());//get current day appointments

    }

    private void getSelectedDates(String firstDay, String endDay) {
        GetFutureStatusRequest sRequest = new GetFutureStatusRequest(firstDay, endDay);
        presenter.getFutureDatesBlockHoursStatus(NetworkConstatnts.RequestCode.API_GET_FUTURE_BLOCK_STATUS, sRequest, false);
    }

    private void initAppoitmnetsAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(BlockHoursActivity.this, LinearLayoutManager.VERTICAL, false);
        blockHoursAdapter = new BlockHoursAdapter(BlockHoursActivity.this, new ArrayList<BlockHoursResponse.BlockHoursBean>(), new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int type, Object o) {
                switch (type) {
                    case GlobalValues.ClickOperations.REMOVE:
                        presenter.deleteBlockHours(NetworkConstatnts.RequestCode.API_DELETE_BLOCK_HOURS, ((BlockHoursResponse.BlockHoursBean) o).getId(), true);
                        blockHoursAdapter.removeItem(position);
                        break;

                }
            }
        });
        canceledList.setLayoutManager(layoutManager);
        canceledList.setAdapter(blockHoursAdapter);
        canceledList.setNestedScrollingEnabled(false);
    }

    private void getBlockedHours() {
        presenter.getBlockedHours(NetworkConstatnts.RequestCode.API_BLOCKED_HOURS, requestBreakHours, false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onSuccessResponse(int serviceMode, Object o) {
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_BLOCKED_HOURS:
                BlockHoursResponse appointmentsData = (BlockHoursResponse) o;
                if (appointmentsData.getBlockHours() != null && appointmentsData.getBlockHours().size() > 0) {
                    updateViewsData(appointmentsData.getBlockHours());
                    noListDataText.setVisibility(View.GONE);
                    canceledList.setVisibility(View.VISIBLE);
                } else {
                    noListDataText.setVisibility(View.VISIBLE);
                    noListDataText.setText(R.string.str_no_block_hours);
                    noListDataText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                    canceledList.setVisibility(View.GONE);
                }
                break;
            case NetworkConstatnts.RequestCode.API_ADD_BLOCK_HOURS:
                getBlockedHours();
                break;
            case NetworkConstatnts.RequestCode.API_GET_FUTURE_BLOCK_STATUS:
                BlockedDatesResponse rModel = (BlockedDatesResponse) o;
                if (rModel != null && rModel.getList() != null && rModel.getList().size() > 0) {
                    weekAdapter.notifyDateStatus(rModel.getList(),null);
                }
                break;
        }
    }

    private void updateViewsData(List<BlockHoursResponse.BlockHoursBean> response) {
        if (response != null && response.size() > 0) {
            blockHoursAdapter.updateAll(response);
            canceledList.setVisibility(View.VISIBLE);
            noListDataText.setVisibility(View.GONE);
        } else {
            blockHoursAdapter.clearAll();
            canceledList.setVisibility(View.GONE);
            noListDataText.setVisibility(View.VISIBLE);
            noListDataText.setText(R.string.str_no_appointments);
            noListDataText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
        }
    }

    @Override
    public void onfaliurResponse(int serviceMode, Object o) {
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_ADD_BLOCK_HOURS:
                new CommonUtils().ShowToast(((BaseResponse) o).getMessage());
                break;
        }
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

    @OnClick({R.id.back_toolbar, R.id.more_txt, R.id.block_floating_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_toolbar:
                onBackPressed();
                break;
            case R.id.more_txt:
                break;
            case R.id.block_floating_btn:

                new FunctionalDialog().openBlockHoursSelectionsDialog(BlockHoursActivity.this, false, false, adRequest, (view1, position, type, t) -> {
//                        List<AddBlockHoursRequest.BlockHourListData> bList = new ArrayList<>();
//                        bList.add(blockHours);
//                        adRequest.setList(bList);
                    presenter.saveBlockHours(NetworkConstatnts.RequestCode.API_ADD_BLOCK_HOURS, (AddBlockHoursRequest) t, true);

                });
                break;
        }
    }
}
