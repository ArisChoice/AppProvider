package com.app.barber.ui.postauth.activities.basic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.app.barber.R;
import com.app.barber.core.BarberApplication;
import com.app.barber.core.BaseActivity;
import com.app.barber.models.request.AddServiceModel;
import com.app.barber.models.response.ServiceListResponseModel;
import com.app.barber.models.response.UpdateDataResponse;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.ui.adapters.ServiceListAdapter;
import com.app.barber.ui.postauth.activities.basic.basicmvp.BasicAuthMVPView;
import com.app.barber.ui.postauth.activities.basic.basicmvp.BssicAuthPresenter;
import com.app.barber.ui.postauth.activities.settings.SettingActivity;
import com.app.barber.util.CommonUtils;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.iface.OnBottomDialogItemListener;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.views.CustomTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by harish on 1/11/18.
 */

public class ServiceListActivity extends BaseActivity implements BasicAuthMVPView {

    @BindView(R.id.recyclar_view_lst)
    RecyclerView recyclarViewLst;
    @BindView(R.id.no_list_data_text)
    CustomTextView noListDataText;
    @BindView(R.id.add_service_btn)
    LinearLayout addServiceBtn;
    @BindView(R.id.continue_btn)
    CustomTextView continueBtn;
    @BindView(R.id.first_header_txt)
    CustomTextView firstHeaderTxt;
    @BindView(R.id.second_header_txt)
    CustomTextView secondHeaderTxt;
    private LinearLayoutManager layoutManager;
    private ServiceListAdapter serviceAdapter;
    private BssicAuthPresenter presenter;
    private String from;

    @Override
    public int getLayoutId() {
        return R.layout.service_list_screen;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BarberApplication) getApplication()).getMyComponent(this).inject(this);
        firstHeaderTxt.setText(R.string.str_your_service);
        secondHeaderTxt.setText(R.string.str_you_can_change);
        presenter = new BssicAuthPresenter(this);
        presenter.attachView(this);
        initRecyclar();
        getIntentData(getIntent());

    }

    private void getIntentData(Intent intent) {
        from = intent.getStringExtra(GlobalValues.KEYS.FROM);
        /*if (from != null) {
            if (from.equals(SettingActivity.class.getName())) {
                continueBtn.setVisibility(View.GONE);
            }
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        getServiceList();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    private void getServiceList() {
        presenter.getUserServices(NetworkConstatnts.RequestCode.API_GET_SERVICE, false);

    }

    private void initRecyclar() {
        layoutManager = new LinearLayoutManager(ServiceListActivity.this);
        recyclarViewLst.setLayoutManager(layoutManager);
        serviceAdapter = new ServiceListAdapter(ServiceListActivity.this, ServiceListActivity.class.getSimpleName(), new ArrayList<ServiceListResponseModel.ResponseBean>(),
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int positio, int type, final Object o) {
                        CommonUtils.getInstance(ServiceListActivity.this).openAddUpdateServiceDialog(ServiceListActivity.this, getUserData(),
                                (ServiceListResponseModel.ResponseBean) o, new OnBottomDialogItemListener() {
                                    @Override
                                    public void onItemClick(View view, int position, int type, Object t) {
                                        switch (type) {
                                            case GlobalValues.RequestCodes.REMOVE:
                                                presenter.removeService(NetworkConstatnts.RequestCode.API_REMOVE_SERVICE, (ServiceListResponseModel.ResponseBean) t, true);
                                                break;
                                            default:
                                                presenter.addService(NetworkConstatnts.RequestCode.API_ADD_SERVICE, (AddServiceModel) t, true);
                                                break;
                                        }
                                    }
                                });


                      /*  switch (type) {
                            case GlobalValues.ClickOperations.SERVICE_DELETE:
                                serviceAdapter.remove(positio);
                                break;
                            case GlobalValues.ClickOperations.SERVICE_DETAIL:
                                Bundle bundle = new Bundle();
                                bundle.putSerializable(GlobalValues.KEYS.SERVICE_DETAIL, (ServiceListResponseModel.ResponseBean) o);
                                activitySwitcher(ServiceListActivity.this, AddServiceActivity.class, bundle);
                                break;
                        }*/

                    }
                });
        recyclarViewLst.setAdapter(serviceAdapter);
    }

    @OnClick({R.id.add_service_btn, R.id.continue_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_service_btn:
                CommonUtils.getInstance(ServiceListActivity.this).openAddUpdateServiceDialog(ServiceListActivity.this, getUserData(),
                        null, new OnBottomDialogItemListener() {
                            @Override
                            public void onItemClick(View view, int position, int type, Object t) {
                                switch (type) {
                                    default:
                                        presenter.addService(NetworkConstatnts.RequestCode.API_ADD_SERVICE, (AddServiceModel) t, true);
                                }

                            }
                        });

//                activitySwitcher(ServiceListActivity.this, AddServiceActivity.class, null);
                break;
            case R.id.continue_btn:
                if (from != null) {
                    if (from.equals(SettingActivity.class.getName())) {
                        onBackPressed();
                    }
                } else
                    activitySwitcher(ServiceListActivity.this, AddWorkspacePhotos.class, null);
                break;
        }
    }

    @Override
    public void onSuccessResponse(int serviceMode, Object model) {
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_GET_SERVICE:
                if (((ServiceListResponseModel) model).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    ServiceListResponseModel responseModel = ((ServiceListResponseModel) model);
                    if (responseModel.getResponse() != null && responseModel.getResponse().size() > 0) {
                        serviceAdapter.updateAll(responseModel.getResponse());
                        recyclarViewLst.setVisibility(View.VISIBLE);
//                        noListDataText.setVisibility(View.GONE);
                    } else {
                        recyclarViewLst.setVisibility(View.VISIBLE);
//                        noListDataText.setVisibility(View.VISIBLE);
                        serviceAdapter.updateAll(presenter.adddfaultService());
                    }

                }
                break;
            case NetworkConstatnts.RequestCode.API_ADD_SERVICE:
                if (((UpdateDataResponse) model).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    getServiceList();
                }
                break;
            case NetworkConstatnts.RequestCode.API_REMOVE_SERVICE:
                getServiceList();
                break;
        }
    }

    private void addSampleServices() {

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
