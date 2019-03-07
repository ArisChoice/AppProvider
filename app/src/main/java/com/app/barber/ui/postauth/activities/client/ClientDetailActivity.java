package com.app.barber.ui.postauth.activities.client;

import android.content.Intent;
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
import com.app.barber.models.response.ClientsListResponseModel;
import com.app.barber.models.response.CustomerBookingResponseModel;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.ui.postauth.activities.client.addclientmvp.AddClientMVPView;
import com.app.barber.ui.postauth.activities.client.addclientmvp.AddClientPresenter;
import com.app.barber.ui.postauth.activities.client.client_adapter.CustomerAppointmentsAdapter;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.ImageUtility;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.views.CustomTextView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by harish on 8/1/19.
 */

public class ClientDetailActivity extends BaseActivity implements AddClientMVPView {
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
    @BindView(R.id.chat_btn)
    ImageView chatBtn;
    @BindView(R.id.call_btn)
    ImageView callBtn;
    @BindView(R.id.recyclar_view_lst)
    RecyclerView bookingsRecyclar;
    @BindView(R.id.contact_number)
    CustomTextView contactNumber;
    @BindView(R.id.client_address)
    CustomTextView clientAddress;
    @BindView(R.id.no_list_data_text)
    CustomTextView noListDataText;
    private ClientsListResponseModel.ResponseBean cData;
    private CustomerAppointmentsAdapter cAppAdapter;
    private AddClientPresenter presenter;
    private int clientId;


    /**
     * @return layout resource id
     */
    @Override
    public int getLayoutId() {
        return R.layout.layout_client_detail_screen;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BarberApplication) getApplication()).getMyComponent(ClientDetailActivity.this).inject(this);
        presenter = new AddClientPresenter(ClientDetailActivity.this);
        presenter.attachView(this);
        getIntentData(getIntent());
        txtTitleToolbar.setVisibility(View.INVISIBLE);
        initAdapter();
        getClientBookings();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    private void getClientBookings() {
        presenter.getBookings(NetworkConstatnts.RequestCode.API_GET_CLIENT_BOOKINGS, clientId, true);

    }

    private void initAdapter() {
        cAppAdapter = new CustomerAppointmentsAdapter(ClientDetailActivity.this, new ArrayList<>(), new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int type, Object o) {

            }
        });

        bookingsRecyclar.setLayoutManager(new LinearLayoutManager(ClientDetailActivity.this));
        bookingsRecyclar.setAdapter(cAppAdapter);
    }

    private void getIntentData(Intent intent) {
        Serializable cDeatil = intent.getSerializableExtra(GlobalValues.KEYS.CUSTOMER_DETAIL);
        if (cDeatil != null) {
            cData = (ClientsListResponseModel.ResponseBean) cDeatil;
            clientId = cData.getId();
            userName.setText(cData.getName());
            if (cData.getImage() != null) {
                imageProfile.setImageURI(ImageUtility.getValidUrl(cData.getImage()));
            }
            if (cData.getContact() != null && !cData.getContact().equals(""))
                contactNumber.setText(cData.getContact());
            else contactNumber.setText(R.string.str_not_available);
            if (cData.getAddress() != null && !cData.getAddress().equals(""))
                clientAddress.setText(cData.getAddress());
            else clientAddress.setText(R.string.str_not_available);
        }
    }

    @OnClick({R.id.back_toolbar, R.id.chat_btn, R.id.call_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_toolbar:
                onBackPressed();
                break;
            case R.id.chat_btn:
                break;
            case R.id.call_btn:
                if (cData != null)
                    callInit(cData.getContact());
                break;
        }
    }

    @Override
    public void onSuccessResponse(int serviceMode, Object o) {
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_GET_CLIENT_BOOKINGS:
                CustomerBookingResponseModel responseData = (CustomerBookingResponseModel) o;
                if (responseData != null && responseData.getData() != null &&
                        responseData.getData().getHistoryList() != null && responseData.getData().getHistoryList().size() > 0) {
                    cAppAdapter.updateAll(responseData.getData().getHistoryList());
                    noListDataText.setVisibility(View.GONE);
                    bookingsRecyclar.setVisibility(View.VISIBLE);
                } else {
                    noListDataText.setVisibility(View.VISIBLE);
                    bookingsRecyclar.setVisibility(View.GONE);
                    noListDataText.setText(R.string.str_no_appointment);
                    noListDataText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
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
