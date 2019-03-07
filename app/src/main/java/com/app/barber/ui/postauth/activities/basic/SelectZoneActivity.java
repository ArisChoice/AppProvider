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
import com.app.barber.core.BaseActivity;
import com.app.barber.models.ZoneModel;
import com.app.barber.models.request.SaveDistrictRequest;
import com.app.barber.models.response.BaseResponse;
import com.app.barber.models.response.SpecialisationResponseModel;
import com.app.barber.models.response.ZoneDistrictResponseModel;
import com.app.barber.models.response.ZoneResponseModel;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.ui.adapters.SpecialisationAdapter;
import com.app.barber.ui.postauth.activities.basic.basic_adapter.ZoneAdapter;
import com.app.barber.ui.postauth.activities.basic.basic_adapter.ZoneDistrictsAdapter;
import com.app.barber.ui.postauth.activities.basic.basicmvp.BasicAuthMVPView;
import com.app.barber.ui.postauth.activities.basic.basicmvp.BssicAuthPresenter;
import com.app.barber.ui.postauth.activities.settings.SettingActivity;
import com.app.barber.util.CommonUtils;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.views.CustomTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by harish on 6/12/18.
 */

public class SelectZoneActivity extends BaseActivity implements BasicAuthMVPView {
    @BindView(R.id.first_header_txt)
    CustomTextView firstHeaderTxt;
    @BindView(R.id.second_header_txt)
    CustomTextView secondHeaderTxt;
    @BindView(R.id.skip_header_txt)
    CustomTextView skipHeaderTxt;
    @BindView(R.id.zone_recyclar)
    RecyclerView zoneRecyclar;
    @BindView(R.id.recyclar_view_lst)
    RecyclerView recyclarViewLst;
    @BindView(R.id.no_list_data_text)
    CustomTextView noListDataText;
    @BindView(R.id.continue_btn)
    CustomTextView continueBtn;
    private LinearLayoutManager layoutManager;
    private ZoneDistrictsAdapter distAdapter;
    private BssicAuthPresenter presenter;
    private ZoneAdapter zoneAdapter;
    private String from;

    @Override
    public int getLayoutId() {
        return R.layout.layout_serving_zone;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firstHeaderTxt.setText(R.string.str_distric_you_serve);
        secondHeaderTxt.setText(R.string.str_as_a_callout);
        skipHeaderTxt.setVisibility(View.VISIBLE);
        presenter = new BssicAuthPresenter(this);
        presenter.attachView(this);
        getIntentData(getIntent());
        initAdapter();
        getZoneList();
    }

    private void getIntentData(Intent intent) {
        from = intent.getStringExtra(GlobalValues.KEYS.FROM);
        if (from != null && from.equals(SettingActivity.class.getName())) {
            skipHeaderTxt.setVisibility(View.GONE);
            continueBtn.setVisibility(View.VISIBLE);
            continueBtn.setText(R.string.str_update);
        }

    }

    private void getZoneList() {
        presenter.getZoneList(NetworkConstatnts.RequestCode.API_GET_ZONES, "", true);
    }

    private void initAdapter() {
        layoutManager = new LinearLayoutManager(SelectZoneActivity.this);
        recyclarViewLst.setLayoutManager(layoutManager);
        distAdapter = new ZoneDistrictsAdapter(SelectZoneActivity.this, new ArrayList<ZoneDistrictResponseModel.ListBean>(),
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position, int type, Object o) {
                        Log.e("Onitem click ", " " + distAdapter.getselected());
                        /*if (distAdapter.getselected() != null && !distAdapter.getselected().equals("")) {
                            continueBtn.setVisibility(View.VISIBLE);
                        } else continueBtn.setVisibility(View.GONE);*/
                        presenter.saveDistricts(NetworkConstatnts.RequestCode.API_SAVE_SERVING_DISTRICTS,
                                new SaveDistrictRequest(((ZoneDistrictResponseModel.ListBean) o).getMItem1(), ((ZoneDistrictResponseModel.ListBean) o).isM_Item3()), false);
                    }
                });
        recyclarViewLst.setAdapter(distAdapter);


        layoutManager = new LinearLayoutManager(SelectZoneActivity.this, LinearLayoutManager.HORIZONTAL, false);
        zoneRecyclar.setLayoutManager(layoutManager);
        zoneAdapter = new ZoneAdapter(SelectZoneActivity.this, new ArrayList<ZoneResponseModel.ListBean>(),
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position, int type, Object o) {
                        Log.d("On item click ", " " + ((ZoneResponseModel.ListBean) o).getMItem2());
                        continueBtn.setVisibility(View.GONE);
                        zoneAdapter.setSelected(position);
                        getRequestedDistricList(((ZoneResponseModel.ListBean) o).getMItem1());
                    }
                });
        zoneRecyclar.setAdapter(zoneAdapter);

    }

    private void getRequestedDistricList(int i) {
        presenter.getZoneDistricList(NetworkConstatnts.RequestCode.API_GET_DISTRIC_LIST, i, true);
    }


    @Override
    public void onSuccessResponse(int serviceMode, Object model) {
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_GET_ZONES:
                if (((ZoneResponseModel) model).getList() != null && ((ZoneResponseModel) model).getList().size() > 0) {
                    recyclarViewLst.setVisibility(View.VISIBLE);
                    noListDataText.setVisibility(View.GONE);
                    zoneAdapter.updateAll(((ZoneResponseModel) model).getList());
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            zoneAdapter.setSelected(0);//default zone
                            getRequestedDistricList(1);//zone 1
                        }
                    }, GlobalValues.TIME_DURATIONS.SMALL);
                } else {
                    recyclarViewLst.setVisibility(View.GONE);
                    noListDataText.setVisibility(View.VISIBLE);
                }

                break;
            case NetworkConstatnts.RequestCode.API_GET_DISTRIC_LIST:
                if (((ZoneDistrictResponseModel) model).getList() != null && ((ZoneDistrictResponseModel) model).getList().size() > 0) {
                    distAdapter.updateAll(((ZoneDistrictResponseModel) model).getList());
                }

                break;
            case NetworkConstatnts.RequestCode.API_SAVE_SERVING_DISTRICTS:
//                new CommonUtils().ShowToast(((BaseResponse) model).getMessage());
//                activitySwitcher(SelectZoneActivity.this, ServiceListActivity.class, null);
                continueBtn.setVisibility(View.VISIBLE);
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


    @OnClick({R.id.skip_header_txt, R.id.continue_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.skip_header_txt:
                activitySwitcher(SelectZoneActivity.this, ServiceListActivity.class, null);
                break;
            case R.id.continue_btn:
                if (from != null && from.equals(SettingActivity.class.getName())) {
                    finish();
                } else activitySwitcher(SelectZoneActivity.this, ServiceListActivity.class, null);
//                presenter.saveDistricts(NetworkConstatnts.RequestCode.API_SAVE_SERVING_DISTRICTS, new SaveDistrictRequest(distAdapter.getselected()), true);
                break;
        }
    }
}
