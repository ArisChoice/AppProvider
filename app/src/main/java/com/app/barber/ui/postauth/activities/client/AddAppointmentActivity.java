package com.app.barber.ui.postauth.activities.client;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;

import com.app.barber.R;
import com.app.barber.core.BarberApplication;
import com.app.barber.core.BaseActivity;
import com.app.barber.models.request.AddAppointmentRequest;
import com.app.barber.models.response.BaseResponse;
import com.app.barber.models.response.ClientsListResponseModel;
import com.app.barber.models.response.ServiceListResponseModel;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.ui.postauth.activities.client.addclientmvp.AddClientMVPView;
import com.app.barber.ui.postauth.activities.client.addclientmvp.AddClientPresenter;
import com.app.barber.ui.postauth.activities.client.client_adapter.ChipAdapter;
import com.app.barber.util.CommonUtils;
import com.app.barber.util.CustomDate;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.ImageUtility;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.views.CustomEditText;
import com.app.barber.views.CustomTextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by harish on 9/1/19.
 */

public class AddAppointmentActivity extends BaseActivity implements AddClientMVPView {
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
    @BindView(R.id.customer_image)
    SimpleDraweeView customerImage;
    @BindView(R.id.customer_name)
    CustomTextView customerName;
    @BindView(R.id.customer_number)
    CustomTextView customerNumber;
    @BindView(R.id.selected_services)
    RecyclerView selectedServices;
    @BindView(R.id.service_amount)
    CustomEditText serviceAmount;
    @BindView(R.id.service_date)
    CustomTextView serviceDate;
    @BindView(R.id.service_start_time)
    CustomTextView serviceStartTime;
    @BindView(R.id.service_end_time)
    CustomTextView serviceEndTime;
    @BindView(R.id.service_note)
    CustomEditText serviceNote;
    @BindView(R.id.message_field)
    CustomEditText messageField;
    @BindView(R.id.save_btn)
    CustomTextView saveBtn;
    ChipAdapter cAdapter;
    @BindView(R.id.select_contact)
    ImageView selectContact;
    private ClientsListResponseModel.ResponseBean customerData;
    private AddClientPresenter presenter;

    /**
     * @return layout resource id
     */
    @Override
    public int getLayoutId() {
        return R.layout.layout_add_appointment_screen;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BarberApplication) getApplication()).getMyComponent(AddAppointmentActivity.this).inject(this);
        presenter = new AddClientPresenter(AddAppointmentActivity.this);
        presenter.attachView(this);
        txtTitleToolbar.setText(R.string.str_add_appointment);
        txtBtn.setVisibility(View.VISIBLE);
        getIntentData(getIntent());
        initAdapter();
    }

    private void getIntentData(Intent intent) {
        Serializable cDeatil = intent.getSerializableExtra(GlobalValues.KEYS.CUSTOMER_DETAIL);
        if (cDeatil != null) {
            customerData = (ClientsListResponseModel.ResponseBean) cDeatil;
            customerName.setText(customerData.getName());
            if (customerData.getImage() != null) {
                customerImage.setImageURI(ImageUtility.getValidUrl(customerData.getImage()));
            }
            customerNumber.setText(customerData.getContact());
        }
    }

    private void initAdapter() {
        GridLayoutManager layoutManager = new GridLayoutManager(AddAppointmentActivity.this, 2);
        selectedServices.setLayoutManager(layoutManager);
        cAdapter = new ChipAdapter(AddAppointmentActivity.this, new ArrayList<ServiceListResponseModel.ResponseBean>(),
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int positio, int type, final Object o) {
                        switch (type) {
                            case GlobalValues.ClickOperations.REMOVE:
                                cAdapter.remove(positio);
                                break;
                        }
                    }
                });
        selectedServices.setAdapter(cAdapter);
    }

    @OnClick({R.id.back_toolbar, R.id.txt_btn, R.id.service_date, R.id.service_start_time, R.id.service_end_time, R.id.save_btn, R.id.service_btn, R.id.select_contact})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_toolbar:
                onBackPressed();
                break;
            case R.id.txt_btn:
                onBackPressed();
                break;
            case R.id.service_date:
                openDatePicker();
                break;
            case R.id.service_start_time:
                new CommonUtils().showDialog(1111, serviceStartTime, this, null);
                break;
            case R.id.service_end_time:
                new CommonUtils().showDialog(1111, serviceEndTime, this, null);
                break;
            case R.id.save_btn:
                if (validate()) {
                    saveAppointmentRequest();
                }
                break;
            case R.id.service_btn:
                activitySwitcherResult(AddAppointmentActivity.this, SelectServiceActivity.class, null, GlobalValues.RequestCodes.SELECT_SERVICES);
                break;
            case R.id.select_contact:
                activitySwitcher(AddAppointmentActivity.this, SelectCustomerActivity.class, null/*, GlobalValues.RequestCodes.SELECT_CUSTOMER*/);
                finish();
                break;
        }
    }

    private boolean validate() {
        if (cAdapter == null && cAdapter.getItemCount() == 0) {
            CommonUtils.getInstance(this).displayMessage(this, getString(R.string.err_no_service_selected));
            return false;
        } else if (CommonUtils.isEmpty(serviceAmount)) {
            CommonUtils.getInstance(this).displayMessage(this, getString(R.string.error_enter_amount));
            return false;
        } else if (serviceStartTime.getText().toString() == null || serviceStartTime.getText().toString().contains("00:00")) {
            CommonUtils.getInstance(this).displayMessage(this, getString(R.string.error_enter_valid_time));
            return false;
        } else if (serviceEndTime.getText().toString() == null || serviceEndTime.getText().toString().contains("00:00")) {
            CommonUtils.getInstance(this).displayMessage(this, getString(R.string.error_enter_valid_time));
            return false;
        } else
            return true;
    }

    private void saveAppointmentRequest() {
        AddAppointmentRequest aRequest = new AddAppointmentRequest();
        aRequest.setCustomerId(customerData.getId());
        aRequest.setDate(CustomDate.getCurrentFormat(AddAppointmentActivity.this, serviceDate.getText().toString(), "dd MMM yyyy", "MM/dd/yyyy"));
        aRequest.setMessage(messageField.getText().toString());
        aRequest.setNote(serviceNote.getText().toString());
        aRequest.setServiceId(cAdapter.getIds());
        aRequest.setTimingSlot(serviceStartTime.getText().toString() + "-" + serviceEndTime.getText().toString());
        aRequest.setAmount(serviceAmount.getText().toString());
        presenter.addAppointment(NetworkConstatnts.RequestCode.API_ADD_CLIENT_APPOINTMENT, aRequest, true);

    }

    private void openDatePicker() {
        // To show current date in the datepicker
        final Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker = new DatePickerDialog(
                AddAppointmentActivity.this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker,
                                  int selectedyear, int selectedmonth,
                                  int selectedday) {

                mcurrentDate.set(Calendar.YEAR, selectedyear);
                mcurrentDate.set(Calendar.MONTH, selectedmonth);
                mcurrentDate.set(Calendar.DAY_OF_MONTH,
                        selectedday);
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.US);

                serviceDate.setText(sdf.format(mcurrentDate.getTime()));
            }
        }, mYear, mMonth, mDay);
        // Set the Calendar new date as minimum date of date picker
        mDatePicker.getDatePicker().setMinDate(mcurrentDate.getTimeInMillis());
        mDatePicker.setTitle(getResources().getString(R.string.alert_date_select));
        mDatePicker.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GlobalValues.RequestCodes.SELECT_SERVICES:
                if (resultCode == RESULT_OK) {
                    ArrayList<ServiceListResponseModel.ResponseBean> serviceList;
                    serviceList = (ArrayList<ServiceListResponseModel.ResponseBean>) data.getSerializableExtra(GlobalValues.KEYS.SERVISE_LIST);
                    Log.e(" onActivityResult ", "  " + new Gson().toJson(serviceList));
                    if (serviceList != null && serviceList.size() > 0) {
//                      Chip chip
                        cAdapter.updateAll(serviceList);
                    }
                }
        }
    }

    @Override
    public void onSuccessResponse(int serviceMode, Object o) {
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_ADD_CLIENT_APPOINTMENT:
                new CommonUtils().displayMessage(AddAppointmentActivity.this, ((BaseResponse) o).getMessage());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onBackPressed();
                    }
                }, GlobalValues.TIME_DURATIONS.MEDIUS);
                break;
        }
    }

    @Override
    public void onfaliurResponse(int serviceMode, Object o) {
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_ADD_CLIENT_APPOINTMENT:
                new CommonUtils().displayMessage(AddAppointmentActivity.this, ((BaseResponse) o).getMessage());
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
