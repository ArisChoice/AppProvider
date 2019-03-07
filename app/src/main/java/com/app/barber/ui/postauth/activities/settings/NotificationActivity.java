package com.app.barber.ui.postauth.activities.settings;

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
import com.app.barber.models.response.NotificationResponseModel;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.ui.adapters.NotificationListAdapter;
import com.app.barber.ui.postauth.activities.settings.settingmvp.SettingMVPView;
import com.app.barber.ui.postauth.activities.settings.settingmvp.SettingPresenter;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.views.CustomTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by harish on 21/12/18.
 */

public class NotificationActivity extends BaseActivity implements SettingMVPView {
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
    private SettingPresenter presenter;
    private NotificationListAdapter notificationAdapter;

    /**
     * @return layout resource id
     */
    @Override
    public int getLayoutId() {
        return R.layout.layout_barber_notification;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BarberApplication) getApplication()).getMyComponent(this).inject(this);
        presenter = new SettingPresenter(this);
        presenter.attachView(this);
        txtTitleToolbar.setText(R.string.str_notification);
        initAdapter();
        getNotifications();
    }

    private void initAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(NotificationActivity.this, LinearLayoutManager.VERTICAL, false);
        notificationAdapter = new NotificationListAdapter(NotificationActivity.this, new ArrayList(), new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int type, Object o) {
//                presenter.updateNoificationStatus(NetworkConstatnts.RequestCode.API_UPDATE_NOTIFICATION_STATUS, "" + notificationAdapter.getpositionData(position).getId(), false);
                presenter.updateNoificationStatus(NetworkConstatnts.RequestCode.API_UPDATE_NOTIFICATION_STATUS, "" + getUserData().getUserID(), false);

            }
        });
        recyclarViewLst.setLayoutManager(layoutManager);
        recyclarViewLst.setAdapter(notificationAdapter);
    }

    private void getNotifications() {
        presenter.getAllNotifications(NetworkConstatnts.RequestCode.API_NOTIFICATION_LIST, null, false);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
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

    @Override
    public void onSuccessResponse(int serviceMode, Object o) {
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_NOTIFICATION_LIST:
                NotificationResponseModel responseData = (NotificationResponseModel) o;
                if (responseData.getResponse() != null && responseData.getResponse().size() > 0) {
                    notificationAdapter.updateAll(responseData.getResponse());
                    noListDataText.setVisibility(View.GONE);
                    recyclarViewLst.setVisibility(View.VISIBLE);
                } else {
                    noListDataText.setVisibility(View.VISIBLE);
                    recyclarViewLst.setVisibility(View.GONE);
                    noListDataText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);

                }
                break;
            case NetworkConstatnts.RequestCode.API_UPDATE_NOTIFICATION_STATUS:
                getNotifications();
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
