package com.app.barber.ui.postauth.activities.basic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.barber.R;
import com.app.barber.core.BarberApplication;
import com.app.barber.core.BaseActivity;
import com.app.barber.models.response.LoginResponseModel;
import com.app.barber.models.response.SpecialisationResponseModel;
import com.app.barber.models.response.UpdateDataResponse;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.ui.adapters.SpecialisationAdapter;
import com.app.barber.ui.postauth.activities.basic.basicmvp.BasicAuthMVPView;
import com.app.barber.ui.postauth.activities.basic.basicmvp.BssicAuthPresenter;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.PreferenceManager;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.views.CustomTextView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by harish on 23/10/18.
 */

public class SpecialiseActivity extends BaseActivity implements BasicAuthMVPView {
    @Inject
    PreferenceManager mPref;
    @BindView(R.id.text_type_one)
    CustomTextView textTypeOne;
    @BindView(R.id.img_check_type_one)
    ImageView imgCheckTypeOne;
    @BindView(R.id.type_barber_layout)
    LinearLayout typeBarberLayout;
    @BindView(R.id.text_type_two)
    CustomTextView textTypeTwo;
    @BindView(R.id.img_check_type_two)
    ImageView imgCheckTypeTwo;
    @BindView(R.id.type_callout_barber_layout)
    LinearLayout typeCalloutBarberLayout;
    @BindView(R.id.text_type_three)
    CustomTextView textTypeThree;
    @BindView(R.id.img_check_type_three)
    ImageView imgCheckTypeThree;
    @BindView(R.id.type_tranee_barber_layout)
    LinearLayout typeTraneeBarberLayout;
    @BindView(R.id.continue_btn)
    CustomTextView continueBtn;
    @BindView(R.id.recyclar_view_lst)
    RecyclerView recyclarViewLst;
    @BindView(R.id.no_list_data_text)
    CustomTextView noListDataText;
    @BindView(R.id.skip_btn)
    CustomTextView skipBtn;
    @BindView(R.id.first_header_txt)
    CustomTextView firstHeaderTxt;
    @BindView(R.id.second_header_txt)
    CustomTextView secondHeaderTxt;
    @BindView(R.id.skip_header_txt)
    CustomTextView skipHeaderTxt;
    private BssicAuthPresenter presenter;
    private String selectedType = null;

    private LinearLayoutManager layoutManager;
    private SpecialisationAdapter specAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.specialise_screen_activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BarberApplication) getApplication()).getMyComponent(this).inject(this);
        firstHeaderTxt.setText(R.string.str_specialest);
        secondHeaderTxt.setText(R.string.str_please_select_type);
        skipHeaderTxt.setVisibility(View.VISIBLE);
        presenter = new BssicAuthPresenter(this);
        presenter.attachView(this);
//        specList = presenter.getSpecialisationList();

        initAdapter();
        getSpecialisation();
    }

    private void getSpecialisation() {
        presenter.getSpecialisationList(NetworkConstatnts.RequestCode.API_GET_SPECIALISATION, "", true);
    }

    private void initAdapter() {
        layoutManager = new LinearLayoutManager(SpecialiseActivity.this);
        recyclarViewLst.setLayoutManager(layoutManager);
        specAdapter = new SpecialisationAdapter(SpecialiseActivity.this, new ArrayList<SpecialisationResponseModel.ResponseBean>(),
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position, int type, Object o) {
                        Log.e("Onitem click ", " " + specAdapter.getselected());
                        if (specAdapter.getselected() != null && !specAdapter.getselected().equals("")) {
                            continueBtn.setVisibility(View.VISIBLE);
                        } else continueBtn.setVisibility(View.GONE);
                    }
                });
        recyclarViewLst.setAdapter(specAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @OnClick({R.id.skip_header_txt, R.id.type_barber_layout, R.id.type_callout_barber_layout, R.id.type_tranee_barber_layout, R.id.continue_btn, R.id.skip_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.skip_header_txt:
//                setTypeSelection(view.getId());
                activitySwitcher(SpecialiseActivity.this, AddressSelectionActivity.class, null);
                break;
            case R.id.continue_btn:
                if (specAdapter.getselected() != null && !specAdapter.getselected().equals(""))
                    presenter.updateSpecType(NetworkConstatnts.RequestCode.API_UPDATE_SPEC_TYPE, specAdapter.getselected(), true);
                else
                    activitySwitcher(SpecialiseActivity.this, AddressSelectionActivity.class, null);
                break;
            case R.id.skip_btn:
                activitySwitcher(SpecialiseActivity.this, AddressActivity.class, null);
                break;
        }
    }

    @Override
    public void onSuccessResponse(int serviceMode, Object model) {
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_UPDATE_SPEC_TYPE:
                if (((UpdateDataResponse) model).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    LoginResponseModel.UserBean userData = getUserData();
                    userData.setSpecializaions(((UpdateDataResponse) model).getSpecType());
                    presenter.saveUserData(userData);
                    mPref.setPrefrencesBoolean(GlobalValues.KEYS.isAddressFromSetting, false);
                    activitySwitcher(SpecialiseActivity.this, AddressSelectionActivity.class, null);
                }
                break;
            case NetworkConstatnts.RequestCode.API_GET_SPECIALISATION:
                if (((SpecialisationResponseModel) model).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    if (((SpecialisationResponseModel) model).getResponse() != null && ((SpecialisationResponseModel) model).getResponse().size() > 0) {
                        specAdapter.updateAll(((SpecialisationResponseModel) model).getResponse());
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
