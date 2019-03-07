package com.app.barber.ui.postauth.activities.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.app.barber.R;
import com.app.barber.core.BarberApplication;
import com.app.barber.core.BaseActivity;
import com.app.barber.models.request.SaveQbDialogRequestModel;
import com.app.barber.models.response.ClientsListResponseModel;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.ui.postauth.activities.client.addclientmvp.AddClientMVPView;
import com.app.barber.ui.postauth.activities.client.addclientmvp.AddClientPresenter;
import com.app.barber.ui.postauth.activities.client.client_adapter.CustomersAdapter;
import com.app.barber.ui.postauth.activities.socket_work.chat.ChatActivity;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.views.CustomEditText;
import com.app.barber.views.CustomTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by harish on 9/1/19.
 */

public class SelectCustomerActivity extends BaseActivity implements AddClientMVPView {
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
    @BindView(R.id.no_list_data_text)
    CustomTextView noListDataText;
    @BindView(R.id.add_client_fab)
    ImageView addClientFab;
    private AddClientPresenter presenter;
    private CustomersAdapter cAdapter;
    private boolean fromMessage;

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
        txtTitleToolbar.setText(R.string.str_select_customer);
        txtBtn.setVisibility(View.VISIBLE);
        ((BarberApplication) getApplication()).getMyComponent(SelectCustomerActivity.this).inject(this);
        presenter = new AddClientPresenter(SelectCustomerActivity.this);
        presenter.attachView(this);
        initRecyclar();
        initSearchFilter();
        addClientFab.setVisibility(View.VISIBLE);
        getIntentData();
    }

    private void getIntentData() {
        fromMessage = getIntent().getBooleanExtra(GlobalValues.KEYS.FROM, false);
        if (fromMessage)
            addClientFab.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMyCustomers();
    }

    private void initSearchFilter() {
        // listening to search query text change
        serachCustomer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getMyCustomers() {
        presenter.getMyCustomers(NetworkConstatnts.RequestCode.API_LIST_CUSTOMERS, null, false);
    }

    private void initRecyclar() {
        cAdapter = new CustomersAdapter(SelectCustomerActivity.this, new ArrayList<ClientsListResponseModel.ResponseBean>(), null, null, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int type, Object o) {
                ClientsListResponseModel.ResponseBean pData = (ClientsListResponseModel.ResponseBean) o;
                switch (type) {
                    case GlobalValues.ClickOperations.DETAILS:
                        if (fromMessage) {
                            if (pData.getUserInfo().getDialogId() != null) {
                                Intent intent = new Intent(SelectCustomerActivity.this, ChatActivity.class);
                                intent.putExtra(GlobalValues.KEYS.EXTRA_DIALOG_ID, pData.getUserInfo().getDialogId());
                                intent.putExtra(GlobalValues.KEYS.OTHER_IMAGE, pData.getUserInfo().getImage());
                                intent.putExtra(GlobalValues.KEYS.USER_ID, ""+pData.getId());
                                startActivity(intent);
                            } else {
//                                initChat(getRequiredVales(pData.getId()), pData.getUserInfo().getQBId(), getUserData().getQBId());
                            }

                        } else {

                            Bundle bndl = new Bundle();
                            bndl.putSerializable(GlobalValues.KEYS.CUSTOMER_DETAIL, pData);
                            activitySwitcher(SelectCustomerActivity.this, AddAppointmentActivity.class, bndl);
                        }
                        break;
                }
            }
        });
        customerRecycler.setLayoutManager(new LinearLayoutManager(SelectCustomerActivity.this, LinearLayoutManager.VERTICAL, false));
        customerRecycler.setAdapter(cAdapter);
    }

   /* public void initChat(SaveQbDialogRequestModel requiredVales, String qbId, String id) {
        showLoading();
        try {
            QBChatDialog dialog = DialogUtils.buildPrivateDialog(Integer.parseInt(qbId));
            QBRestChatService.createChatDialog(dialog).performAsync(new QBEntityCallback<QBChatDialog>() {
                @Override
                public void onSuccess(QBChatDialog result, Bundle params) {
                    Log.d("onSuccess", "    " + result.getDialogId());
                    requiredVales.setDialogId(result.getDialogId());
                    cAdapter.setQBDialogId(result.getDialogId(), requiredVales.getUserId());
                    hideLoading();
                    presenter.saveQbDialog(NetworkConstatnts.RequestCode.API_SAVE_QB_DIALOG, requiredVales, true);
                }

                @Override
                public void onError(QBResponseException responseException) {

                }
            });
        } catch (Exception e) {
//            cUtils.ShowToast(getString(R.string.msg_error));
        }
    }*/

    private SaveQbDialogRequestModel getRequiredVales(int id) {
        SaveQbDialogRequestModel sModel = new SaveQbDialogRequestModel();
        sModel.setBarberId(getUserData().getUserID());
        sModel.setUserId(id);
        return sModel;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @OnClick({R.id.back_toolbar, R.id.txt_btn, R.id.add_client_fab})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_toolbar:
                onBackPressed();
                break;
            case R.id.txt_btn:
                onBackPressed();
                break;
            case R.id.add_client_fab:
                activitySwitcher(SelectCustomerActivity.this, AddClientActivity.class, null);
                break;
        }


    }

    @Override
    public void onSuccessResponse(int serviceMode, Object o) {
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_LIST_CUSTOMERS:
                if (((ClientsListResponseModel) o).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    if (((ClientsListResponseModel) o).getResponse() != null && ((ClientsListResponseModel) o).getResponse().size() > 0) {
                        if (!fromMessage)//if user is here from other screen not message.
                            cAdapter.updateAll(((ClientsListResponseModel) o).getResponse());
                        else
                            cAdapter.updateAll(presenter.getAvailableChatUsers(((ClientsListResponseModel) o).getResponse()));
                        noListDataText.setVisibility(View.GONE);
                        customerRecycler.setVisibility(View.VISIBLE);
                    } else {
                        noListDataText.setText(R.string.str_no_customers);
                        noListDataText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                        noListDataText.setVisibility(View.VISIBLE);
                        customerRecycler.setVisibility(View.GONE);
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
