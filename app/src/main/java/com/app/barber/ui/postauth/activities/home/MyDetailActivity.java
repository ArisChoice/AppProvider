package com.app.barber.ui.postauth.activities.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.app.barber.R;
import com.app.barber.core.BaseActivity;
import com.app.barber.views.CustomEditText;
import com.app.barber.views.CustomTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by harish on 19/11/18.
 */

public class MyDetailActivity extends BaseActivity {
    @BindView(R.id.back_toolbar)
    ImageView backToolbar;
    @BindView(R.id.txt_title_toolbar)
    CustomTextView txtTitleToolbar;
    @BindView(R.id.img_edit)
    ImageView imgEdit;
    @BindView(R.id.toolbar_common)
    Toolbar toolbarCommon;
    @BindView(R.id.edtxt_username)
    CustomEditText edtxtUsername;
    @BindView(R.id.edtxt_email)
    CustomEditText edtxtEmail;
    @BindView(R.id.edtxt_phone_numbere)
    CustomEditText edtxtPhoneNumbere;
    @BindView(R.id.edtxt_password)
    CustomEditText edtxtPassword;
    @BindView(R.id.edtxt_address)
    CustomEditText edtxtAddress;
    @BindView(R.id.edtxt_city)
    CustomEditText edtxtCity;
    @BindView(R.id.edtxt_pin)
    CustomEditText edtxtPin;
    @BindView(R.id.save_Btn_my_detail)
    CustomTextView saveBtnMyDetail;

    @Override
    public int getLayoutId() {
        return R.layout.layout_mydetail_screen;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.back_toolbar, R.id.img_edit, R.id.save_Btn_my_detail})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_toolbar:
                onBackPressed();
                break;
            case R.id.img_edit:
                break;
            case R.id.save_Btn_my_detail:
                break;
        }
    }
}
