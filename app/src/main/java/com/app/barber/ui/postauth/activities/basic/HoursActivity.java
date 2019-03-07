/*
package com.app.barber.ui.postauth.activities.basic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.app.barber.R;
import com.app.barber.core.BarberApplication;
import com.app.barber.core.BaseActivity;
import com.app.barber.models.TimeSlotsModel;
import com.app.barber.models.request.HoursModel;
import com.app.barber.models.response.LoginResponseModel;
import com.app.barber.models.response.UpdateDataResponse;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.ui.adapters.TimeSlotsAdapter;
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

*/
/**
 * Created by harish on 23/10/18.
 *//*


public class HoursActivity extends BaseActivity implements BasicAuthMVPView {
    @BindView(R.id.back_toolbar)
    ImageView backToolbar;
    @BindView(R.id.txt_title_toolbar)
    CustomTextView txtTitleToolbar;
    @BindView(R.id.img_edit)
    ImageView imgEdit;
    @BindView(R.id.recyclar_view_lst)
    RecyclerView recyclarViewLst;
    @BindView(R.id.no_list_data_text)
    CustomTextView noListDataText;
    @BindView(R.id.continue_btn)
    ImageView continueBtn;
    @BindView(R.id.screen_Top_text)
    CustomTextView screenTopText;
    @BindView(R.id.screen_message_text)
    CustomTextView screenMessageText;
    private LinearLayoutManager layoutManager;
    private TimeSlotsAdapter timeAdapter;
    private BssicAuthPresenter presenter;
    private boolean isBreakHours;
    private List<UpdateDataResponse.OpeningHoursBean> savedTimeSlots;

    @Override
    public int getLayoutId() {
        return R.layout.layout_hours_screen;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BarberApplication) getApplication()).getMyComponent(this).inject(this);
        presenter = new BssicAuthPresenter(this);
        presenter.attachView(this);
        getIntentData(getIntent());
        getSavedTime();
        initRecyclarView();

    }

    private void getIntentData(Intent intent) {
        if (intent.getStringExtra(GlobalValues.KEYS.FROM) != null)
            if (intent.getStringExtra(GlobalValues.KEYS.FROM).equals(GlobalValues.KEYS.ADD_BREAK_HOURS)) {
                isBreakHours = true;
                screenTopText.setText(R.string.str_your_break_hours);
            } else {
                screenTopText.setText(R.string.str_your_call_out_hours);
                isBreakHours = false;
            }
    }

    private void getSavedTime() {
        if (getUserData().getOpeningHours() != null && getUserData().getOpeningHours().size() > 0) {
            if (isBreakHours)
                savedTimeSlots = getUserData().getBreakhours();
            else savedTimeSlots = getUserData().getCallOutHours();
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    private void initRecyclarView() {
        layoutManager = new LinearLayoutManager(HoursActivity.this);
        recyclarViewLst.setLayoutManager(layoutManager);
        timeAdapter = new TimeSlotsAdapter(getTimeSlots(), new OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int positio, int type, Object o) {
                switch (type) {
                    case GlobalValues.ClickOperations.ADD_TIME_CLICK:
                        CommonUtils.getInstance(HoursActivity.this).
                                openDialogTimePicker(HoursActivity.this, "", new OnBottomDialogItemListener() {
                                    TimeSlotsModel timeModel = new TimeSlotsModel();

                                    @Override
                                    public void onItemClick(View view, int position, int type, Object t) {
                                        switch (type) {
                                            case 1:
                                                timeModel = ((TimeSlotsModel) t);
                                                timeModel.setDay(timeAdapter.getItemName(positio));
                                                timeAdapter.updatePosition(timeModel, positio);
                                                break;
                                            case 2:
                                                break;
                                        }

                                    }
                                });
                        break;
                }

            }
        });
        recyclarViewLst.setAdapter(timeAdapter);

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
                arrayList.add(model);
            }
        }
        return arrayList;
    }

    @OnClick({R.id.back_toolbar, R.id.continue_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_toolbar:
                onBackPressed();
                break;
            case R.id.continue_btn:
                Log.e("continue_btn ", " " + new Gson().toJson(timeAdapter.getList()));
//                UpdateTimeModel updateTime = new UpdateTimeModel();
//                updateTime.setListTime(getValidList());
                if (isBreakHours)
                    presenter.updateOpeningTime(NetworkConstatnts.RequestCode.API_UPDATE_BREAK_TIME, getValidList(), true);
                else
                    presenter.updateOpeningTime(NetworkConstatnts.RequestCode.API_UPDATE_CALLOUT_TIME, getValidList(), true);
//              activitySwitcher(CalloutOpeningHoursActivity.this, ServiceListActivity.class, null);
                break;
        }
    }

    private List<HoursModel> getValidList() {
        List<HoursModel> list = new ArrayList<>();
      */
/*  for (int i = 0; i < timeAdapter.getList().size(); i++) {
            HoursModel timModel = new HoursModel();
            timModel.setOpeningHours(timeAdapter.getList().get(i).getOpeningHours());
            timModel.setClosingHours(timeAdapter.getList().get(i).getClosingHours());
            timModel.setDay(timeAdapter.getList().get(i).getDay());
            list.add(timModel);

        }*//*

        return list;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccessResponse(int serviceMode, Object model) {
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_UPDATE_BREAK_TIME:
                if (((UpdateDataResponse) model).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getSavedHours();
                }
                break;
            case NetworkConstatnts.RequestCode.API_UPDATE_CALLOUT_TIME:
                if (((UpdateDataResponse) model).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getSavedHours();
                }
                break;
            case NetworkConstatnts.RequestCode.API_GET_HOURS:
                if (((UpdateDataResponse) model).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    LoginResponseModel.UserBean userData = getUserData();
                    if (isBreakHours)
                        userData.setBreakhours(((UpdateDataResponse) model).getOpeningHours());
                    else userData.setCallOutHours(((UpdateDataResponse) model).getOpeningHours());
                    presenter.saveUserData(userData);
                    finish();
                }
                break;
        }
    }

    private void getSavedHours() {
        if (isBreakHours)
            presenter.getSavedHours(NetworkConstatnts.RequestCode.API_GET_HOURS, GlobalValues.HOURS.BREAK, true);
        else
            presenter.getSavedHours(NetworkConstatnts.RequestCode.API_GET_HOURS, GlobalValues.HOURS.CALLOUT, true);
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
*/
