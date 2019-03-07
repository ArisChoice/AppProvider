package com.app.barber.ui.postauth.activities.basic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.app.barber.R;
import com.app.barber.core.BarberApplication;
import com.app.barber.core.BaseActivity;
import com.app.barber.models.TimeSlotsModel;
import com.app.barber.models.request.HoursModel;
import com.app.barber.models.response.LoginResponseModel;
import com.app.barber.models.response.UpdateDataResponse;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.ui.adapters.TimeSlotsAdapter;
import com.app.barber.ui.postauth.activities.basic.basicmvp.BasicAuthMVPView;
import com.app.barber.ui.postauth.activities.basic.basicmvp.BssicAuthPresenter;
import com.app.barber.ui.postauth.activities.home.AvailabilityActivity;
import com.app.barber.util.CommonUtils;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.iface.OnBottomDialogItemListener;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.views.CustomTextView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by harish on 23/10/18.
 */

public class CalloutOpeningHoursActivity extends BaseActivity implements BasicAuthMVPView {
    @BindView(R.id.recyclar_view_lst)
    RecyclerView recyclarViewLst;
    @BindView(R.id.no_list_data_text)
    CustomTextView noListDataText;
    @BindView(R.id.continue_btn)
    CustomTextView continueBtn;
    @BindView(R.id.first_header_txt)
    CustomTextView firstHeaderTxt;
    @BindView(R.id.second_header_txt)
    CustomTextView secondHeaderTxt;
    @BindView(R.id.skip_header_txt)
    CustomTextView skipHeaderTxt;
    @BindView(R.id.add_break_hours)
    CustomTextView addBreakHours;
    @BindView(R.id.add_callout_hours)
    CustomTextView addCalloutHours;
    private LinearLayoutManager layoutManager;
    private TimeSlotsAdapter timeAdapter;
    private BssicAuthPresenter presenter;
    private List<UpdateDataResponse.OpeningHoursBean> savedTimeSlots;
    private boolean isRegistration = true;
    private boolean isWorkingHours;
    private boolean isEdit;//is time editing

    @Override
    public int getLayoutId() {
        return R.layout.layout_callout_opening_hours_screen;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BarberApplication) getApplication()).getMyComponent(this).inject(this);
        presenter = new BssicAuthPresenter(this);
        presenter.attachView(this);
        firstHeaderTxt.setText(R.string.str_working_hours_break_hours);
        firstHeaderTxt.setText(R.string.str_when_can);
        getIntentData(getIntent());
        getSavedTime();
        initRecyclarView();

    }

    private void getIntentData(Intent intent) {
        if (intent.getStringExtra(GlobalValues.KEYS.FROM) != null) {
            if (intent.getStringExtra(GlobalValues.KEYS.FROM).equals(GlobalValues.KEYS.MORE)) {
                isRegistration = false;
                continueBtn.setVisibility(View.GONE);
            }
        }
        if (intent.getStringExtra(GlobalValues.KEYS.TYPE) != null)
            if (intent.getStringExtra(GlobalValues.KEYS.TYPE).equals(GlobalValues.KEYS.ADD_WORKING_HOURS)) {
                isWorkingHours = true;
                firstHeaderTxt.setText(R.string.str_working_hours_break_hours);
                secondHeaderTxt.setText(R.string.str_when_can);
            } else {
                isWorkingHours = false;
                firstHeaderTxt.setText(R.string.str_callout_hours);
                secondHeaderTxt.setText(R.string.str_when_can);
            }

        if (isRegistration) {
            if (!isWorkingHours)
                skipHeaderTxt.setVisibility(View.VISIBLE);
        }

    }

    private void getSavedTime() {
        try {
            if (getUserData().getOpeningHours() != null && getUserData().getOpeningHours().size() > 0) {
                if (isWorkingHours)
                    savedTimeSlots = getUserData().getOpeningHours();
                else savedTimeSlots = getUserData().getCallOutHours();
            }
        } catch (Exception e) {

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    private void initRecyclarView() {
        layoutManager = new LinearLayoutManager(CalloutOpeningHoursActivity.this);
        recyclarViewLst.setLayoutManager(layoutManager);
        timeAdapter = new TimeSlotsAdapter(getTimeSlots(), isWorkingHours, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int positio, int type, Object o) {
                switch (type) {
                    case GlobalValues.ClickOperations.ADD_TIME_CLICK:
                        if (timeAdapter.getList().get(positio).getId() == null || timeAdapter.getList().get(positio).getId().equals("")) {
                            isEdit = false;
                        } else isEdit = true;
                        Log.e("onItemClick ", " " + isEdit);
                        CommonUtils.getInstance(CalloutOpeningHoursActivity.this).
                                openHoursSelectionsDialog(CalloutOpeningHoursActivity.this, isWorkingHours, isEdit, (TimeSlotsModel) o, new OnBottomDialogItemListener() {
                                    @Override
                                    public void onItemClick(View view, int position, int type, Object t) {
                                        switch (type) {
                                            case GlobalValues.ClickOperations.REMOVE:
                                                Log.e("onItemClick ", "   remove " + new Gson().toJson((Integer) t));
                                                presenter.removeBrakeHour(NetworkConstatnts.RequestCode.API_DELETE_BREAK_HOUR, new Gson().toJson((Integer) t), false);
                                                break;
                                            default:
                                                timeAdapter.updateItem((TimeSlotsModel) t);
                                                if (isWorkingHours)
                                                    presenter.updateOpeningTime(NetworkConstatnts.RequestCode.API_UPDATE_OPENING_TIME, getValidData((TimeSlotsModel) t), true);
                                                else
                                                    presenter.updateOpeningTime(NetworkConstatnts.RequestCode.API_UPDATE_CALLOUT_TIME, getValidData((TimeSlotsModel) t), true);
                                                break;
                                        }
                                    }
                                });
                        break;
                    case GlobalValues.ClickOperations.REMOVE:
                        HoursModel.WorkingHours positionData = getValidData((TimeSlotsModel) o);
                        positionData.setClosed(true);
                        if (isWorkingHours)
                            presenter.updateOpeningTime(NetworkConstatnts.RequestCode.API_UPDATE_OPENING_TIME, positionData, true);
                        else
                            presenter.updateOpeningTime(NetworkConstatnts.RequestCode.API_UPDATE_CALLOUT_TIME, positionData, true);
                        break;
                }
            }
        });
        recyclarViewLst.setAdapter(timeAdapter);

    }

    private HoursModel.WorkingHours getValidData(TimeSlotsModel t) {
        HoursModel timModel = new HoursModel();
        HoursModel.WorkingHours workingHours = timModel.new WorkingHours();
        workingHours.setOpeningHours(t.getOpeningHours());
        workingHours.setClosingHours(t.getClosingHours());
        workingHours.setDay(t.getDay());
        try {
            workingHours.setId(Integer.parseInt(t.getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (t.getBreakHours() != null && t.getBreakHours().size() > 0) {
            workingHours.setBreakHours(t.getBreakHours());
        }
        return workingHours;
    }

    private ArrayList<TimeSlotsModel> getTimeSlots() {
        ArrayList<TimeSlotsModel> arrayList = new ArrayList<>();
        if (savedTimeSlots == null || savedTimeSlots.size() == 0) {
            String[] name = getResources().getStringArray(R.array.days_names);
            for (int i = 0; i < name.length; i++) {
                TimeSlotsModel model = new TimeSlotsModel();
                model.setDay(name[i]);
                model.setOpeningHours(null);
                model.setClosingHours(null);
                arrayList.add(model);
            }
        } else {
            for (int i = 0; i < savedTimeSlots.size(); i++) {
                TimeSlotsModel model = new TimeSlotsModel();
                model.setDay(savedTimeSlots.get(i).getDay());
                model.setId(savedTimeSlots.get(i).getId());
                model.setOpeningHours(savedTimeSlots.get(i).getOpeningHours());
                model.setClosingHours(savedTimeSlots.get(i).getClosingHours());
                model.setBreakHours(savedTimeSlots.get(i).getBreakHoursList());
                model.setClosed(savedTimeSlots.get(i).getClosed());
                arrayList.add(model);
            }
        }
        return arrayList;
    }

    @OnClick({R.id.continue_btn, R.id.skip_header_txt})
    public void onClick(View view) {
        Bundle bundle;

        switch (view.getId()) {
            case R.id.continue_btn:
                Log.d("continue_btn ", " " + new Gson().toJson(timeAdapter.getList()));
                if (isWorkingHours) {
                    bundle = new Bundle();
                    if (getUserData().getBarberType() != null && getUserData().getBarberType().contains("2")) {
                        bundle.putString(GlobalValues.KEYS.TYPE, GlobalValues.KEYS.ADD_CALLOUT_HOURS);
                        activitySwitcher(CalloutOpeningHoursActivity.this, CalloutOpeningHoursActivity.class, bundle);
                    } else
                        activitySwitcher(CalloutOpeningHoursActivity.this, ServiceListActivity.class, bundle);
                } else {
                    activitySwitcher(CalloutOpeningHoursActivity.this, SelectZoneActivity.class, null);
                }
                break;
            case R.id.skip_header_txt:
                activitySwitcher(CalloutOpeningHoursActivity.this, ServiceListActivity.class, null);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccessResponse(int serviceMode, Object model) {
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_UPDATE_OPENING_TIME:
                if (((UpdateDataResponse) model).getStatus() == NetworkConstatnts.ResponseCode.success) {
//                    getSavedHours();
                    LoginResponseModel.UserBean userData = getUserData();
                    userData.setOpeningHours(((UpdateDataResponse) model).getOpeningHours());
                    userData.setOpeningHoursExist(true);
                    presenter.saveUserData(userData);
                    new CommonUtils().displayMessage(CalloutOpeningHoursActivity.this, ((UpdateDataResponse) model).getMessage());
                    refreshView();
                }
                break;
            case NetworkConstatnts.RequestCode.API_UPDATE_CALLOUT_TIME:
                if (((UpdateDataResponse) model).getStatus() == NetworkConstatnts.ResponseCode.success) {
//                    getSavedHours();
                    LoginResponseModel.UserBean userData = getUserData();
                    userData.setCallOutHours(((UpdateDataResponse) model).getCallOutHours());
                    userData.setCallOutHoursExist(true);
                    presenter.saveUserData(userData);
                    new CommonUtils().displayMessage(CalloutOpeningHoursActivity.this, ((UpdateDataResponse) model).getMessage());
                    refreshView();
                }
                break;
            case NetworkConstatnts.RequestCode.API_GET_HOURS:
                if (((UpdateDataResponse) model).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    LoginResponseModel.UserBean userData = getUserData();
                    userData.setOpeningHours(((UpdateDataResponse) model).getOpeningHours());
                    presenter.saveUserData(userData);
                    if (isRegistration)
                        activitySwitcher(CalloutOpeningHoursActivity.this, ServiceListActivity.class, null);
                    else finish();
                }
                break;
        }
    }

    private void refreshView() {
        new Handler().postDelayed(() -> {
            getSavedTime();
            initRecyclarView();
        }, GlobalValues.TIME_DURATIONS.SMALL);
    }

    private void getSavedHours() {
        presenter.getSavedHours(NetworkConstatnts.RequestCode.API_GET_HOURS, GlobalValues.HOURS.OPENING, true);
    }

    @Override
    public void onfaliurResponse(int serviceMode, Object model) {
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_UPDATE_OPENING_TIME:
                new CommonUtils().displayMessage(CalloutOpeningHoursActivity.this, ((UpdateDataResponse) model).getMessage());
                refreshView();
                break;
            case NetworkConstatnts.RequestCode.API_UPDATE_CALLOUT_TIME:
                new CommonUtils().displayMessage(CalloutOpeningHoursActivity.this, ((UpdateDataResponse) model).getMessage());
                refreshView();
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
}
