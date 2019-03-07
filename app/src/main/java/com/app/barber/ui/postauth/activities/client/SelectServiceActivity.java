package com.app.barber.ui.postauth.activities.client;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.app.barber.R;
import com.app.barber.core.BarberApplication;
import com.app.barber.core.BaseActivity;
import com.app.barber.models.request.AddServiceModel;
import com.app.barber.models.response.ClientsListResponseModel;
import com.app.barber.models.response.ServiceListResponseModel;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.ui.adapters.ServiceListAdapter;
import com.app.barber.ui.postauth.activities.basic.ServiceListActivity;
import com.app.barber.ui.postauth.activities.client.addclientmvp.AddClientMVPView;
import com.app.barber.ui.postauth.activities.client.addclientmvp.AddClientPresenter;
import com.app.barber.ui.postauth.activities.client.client_adapter.CustomersAdapter;
import com.app.barber.util.CommonUtils;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.iface.OnBottomDialogItemListener;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.views.CustomEditText;
import com.app.barber.views.CustomTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by harish on 9/1/19.
 */

public class SelectServiceActivity extends BaseActivity implements AddClientMVPView {
    @BindView(R.id.back_toolbar)
    ImageView backToolbar;
    @BindView(R.id.txt_title_toolbar)
    CustomTextView txtTitleToolbar;
    @BindView(R.id.img_edit)
    ImageView imgEdit;
    @BindView(R.id.txt_btn)
    CustomTextView txtBtn;
    @BindView(R.id.toolbar_common)
    Toolbar toolbarCommon;
    @BindView(R.id.serach_customer)
    CustomEditText serachCustomer;
    @BindView(R.id.recyclar_view_lst)
    RecyclerView customerRecycler;
    private AddClientPresenter presenter;
    private CustomersAdapter cAdapter;
    private ServiceListAdapter serviceAdapter;
    private ArrayList<ServiceListResponseModel.ResponseBean> selectedServiceList;

    /**
     * @return layout resource id
     */
    @Override
    public int getLayoutId() {
        return R.layout.layout_select_customer_screen;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        txtTitleToolbar.setText(R.string.str_select_service);
        serachCustomer.setVisibility(View.GONE);
        txtBtn.setVisibility(View.VISIBLE);
        ((BarberApplication) getApplication()).getMyComponent(SelectServiceActivity.this).inject(this);
        presenter = new AddClientPresenter(SelectServiceActivity.this);
        presenter.attachView(this);
        initRecyclar();
        getMyServices();
    }

    private void getMyServices() {
        presenter.getUserServices(NetworkConstatnts.RequestCode.API_GET_SERVICE, false);
    }

    private void initRecyclar() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(SelectServiceActivity.this);
        customerRecycler.setLayoutManager(layoutManager);
        serviceAdapter = new ServiceListAdapter(SelectServiceActivity.this, SelectServiceActivity.class.getSimpleName(),
                new ArrayList<ServiceListResponseModel.ResponseBean>(),
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int positio, int type, final Object o) {
                        selectedServiceList = serviceAdapter.getSelected();
                        if (selectedServiceList != null && selectedServiceList.size() > 0) {
                            txtBtn.setText(R.string.str_save);
                        } else txtBtn.setText(R.string.str_cancel);
                    }
                });
        customerRecycler.setAdapter(serviceAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @OnClick({R.id.back_toolbar, R.id.txt_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_toolbar:
                onBackPressed();
                break;
            case R.id.txt_btn:
                if (txtBtn.getText().equals(getString(R.string.str_save))) {
                    if (selectedServiceList != null && selectedServiceList.size() > 0) {
                        Intent intent = new Intent();
                        intent.putExtra(GlobalValues.KEYS.SERVISE_LIST, selectedServiceList);
                        setResult(RESULT_OK, intent);
                    }
                    finish();

                } else
                    onBackPressed();
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
                    }
                }
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
}
