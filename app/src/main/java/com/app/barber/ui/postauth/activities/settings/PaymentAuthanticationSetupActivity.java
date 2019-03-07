package com.app.barber.ui.postauth.activities.settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.app.barber.R;
import com.app.barber.core.BaseActivity;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.views.CustomTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by harish on 2/1/19.
 */

public class PaymentAuthanticationSetupActivity extends BaseActivity {
    @BindView(R.id.first_header_txt)
    CustomTextView firstHeaderTxt;
    @BindView(R.id.second_header_txt)
    CustomTextView secondHeaderTxt;
    @BindView(R.id.skip_header_txt)
    CustomTextView skipHeaderTxt;
    @BindView(R.id.stripe_save)
    CustomTextView stripeSave;
    @BindView(R.id.stripe_setup)
    CustomTextView stripeSetup;

    /**
     * @return layout resource id
     */
    @Override
    public int getLayoutId() {
        return R.layout.layout_stripe_setup;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firstHeaderTxt.setText(R.string.str_setup_stripe);
        secondHeaderTxt.setText(R.string.str_add_required_keys);
    }

    @OnClick({R.id.stripe_save, R.id.stripe_setup})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.stripe_save:
                break;
            case R.id.stripe_setup:
                goSetup();
                break;
        }
    }

    private void goSetup() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(NetworkConstatnts.URL.STRIP_SETUP));
        startActivity(browserIntent);
    }
}
