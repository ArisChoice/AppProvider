package com.app.barber.ui.postauth.activities.settings;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.app.barber.R;
import com.app.barber.core.BarberApplication;
import com.app.barber.core.BaseActivity;
import com.app.barber.models.DurationModel;
import com.app.barber.models.response.AdvanceBookingTimeResponse;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.ui.adapters.BookingTimeAdapter;
import com.app.barber.ui.adapters.OptionListAdapter;
import com.app.barber.ui.postauth.activities.basic.ServiceListActivity;
import com.app.barber.ui.postauth.activities.chat.chatmvp.ChatPresenter;
import com.app.barber.ui.postauth.activities.home.AvailabilityActivity;
import com.app.barber.ui.postauth.activities.home.ProfileScreenActivity;
import com.app.barber.ui.postauth.activities.settings.settingmvp.SettingMVPView;
import com.app.barber.ui.postauth.activities.settings.settingmvp.SettingPresenter;
import com.app.barber.util.CommonUtils;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.views.CustomTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by harish on 27/12/18.
 */

public class ClientCanBookActivity extends BaseActivity implements SettingMVPView {
    @BindView(R.id.back_toolbar)
    ImageView backToolbar;
    @BindView(R.id.txt_title_toolbar)
    CustomTextView txtTitleToolbar;
    @BindView(R.id.img_edit)
    ImageView imgEdit;
    @BindView(R.id.toolbar_common)
    Toolbar toolbarCommon;
    @BindView(R.id.recyclar_view_lst)
    RecyclerView recyclarViewLst;
    @BindView(R.id.no_list_data_text)
    CustomTextView noListDataText;
    private BookingTimeAdapter beforeAdapter;
    private SettingPresenter presenter;

    /**
     * @return layout resource id
     */
    @Override
    public int getLayoutId() {
        return R.layout.layout_how_far_book;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BarberApplication) getApplication()).getMyComponent(ClientCanBookActivity.this).inject(this);
        presenter = new SettingPresenter(ClientCanBookActivity.this);
        presenter.attachView(this);
        initAdapter();
        txtTitleToolbar.setText(R.string.str_client_can_book);
        getSelectedAdvanceTime();
    }

    private void getSelectedAdvanceTime() {
        presenter.getAdvanceBookingTime(NetworkConstatnts.RequestCode.API_GET_ADVANCE_BOOKING_TIME, null, true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    private void initAdapter() {
        beforeAdapter = new BookingTimeAdapter(ClientCanBookActivity.this, getTimeListhours(),
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position, int type, Object t) {

                        DurationModel selectedData = beforeAdapter.getSelected();
                        Log.e(" onItemClick ", " " + selectedData.getTime());

                        presenter.saveAdvanceBookingTime(NetworkConstatnts.RequestCode.API_ADVANCE_BOOKING_TIME, presenter.getValidMinutes(selectedData.getTime()), true);

                    }
                });
        LinearLayoutManager layoutManager = new LinearLayoutManager(ClientCanBookActivity.this);
        recyclarViewLst.setLayoutManager(layoutManager);
        recyclarViewLst.setAdapter(beforeAdapter);
    }

    private List<DurationModel> getTimeListhours() {

        ArrayList<DurationModel> time = new ArrayList<>();
        DurationModel dModel;
        for (int i = 1; i < 6; i++) {
            dModel = new DurationModel();
            dModel.setTime(i + " Hour");
            time.add(dModel);
        }
        dModel = new DurationModel();
        dModel.setTime("0 Minutes");
        time.add(0, dModel);
        dModel = new DurationModel();
        dModel.setTime("15 Minutes");
        time.add(1, dModel);
        dModel = new DurationModel();
        dModel.setTime("30 Minutes");
        time.add(2, dModel);
        return time;
    }

    @Override
    public void onSuccessResponse(int serviceMode, Object o) {
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_GET_ADVANCE_BOOKING_TIME:
                AdvanceBookingTimeResponse responseMdl = (AdvanceBookingTimeResponse) o;
                beforeAdapter.setSelected(responseMdl.getTime());
                break;
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

    @OnClick({R.id.back_toolbar, R.id.img_edit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_toolbar:
                onBackPressed();
                break;
            case R.id.img_edit:
                break;
        }
    }
}
