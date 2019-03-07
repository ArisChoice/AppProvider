package com.app.barber.core;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.app.barber.R;
import com.app.barber.core.core_mvp.CorePresenter;
import com.app.barber.models.response.LoginResponseModel;
import com.app.barber.util.CommonUtils;
import com.app.barber.util.Dialogs;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.PreferenceManager;
import com.google.gson.Gson;


import javax.inject.Inject;

import butterknife.ButterKnife;


/**
 * Created by harish on 16/10/18.
 */

public abstract class BaseActivity extends AppCompatActivity {
    // since its going to be common for all the activities
    private ProgressDialog mProgressDialog;
    private Dialogs dialogs;
    @Inject
    public PreferenceManager mPref;
    @Inject
    public CommonUtils cUtils;
    private CorePresenter cPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performDataBinding();
        dialogs = Dialogs.getInstance(this);
        ((BarberApplication) getApplication()).getMyComponent(this).inject(this);
        cPresenter=new CorePresenter(this);
    }

    /**
     * @return layout resource id
     */
    public abstract
    @LayoutRes
    int getLayoutId();

    private void performDataBinding() {
        setContentView(getLayoutId());
        ButterKnife.bind(this);
    }

    public void showLoading() {
        hideLoading();
        mProgressDialog = dialogs.showLoadingDialog(this);
        mProgressDialog.setCancelable(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.setCancelable(true);
            }
        }, GlobalValues.TIME_DURATIONS.LARGE);

    }

    public void hideLoading() {
        dialogs.DismissDialog(mProgressDialog);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    public static void activitySwitcher(Activity from, Class<?> to, Bundle bundle) {

        Intent intent = new Intent(from, to);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        from.startActivity(intent);
        from.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    public static void activitySwitcherResult(Activity from, Class<?> to, Bundle bundle, int requestCode) {

        Intent intent = new Intent(from, to);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        from.startActivityForResult(intent, requestCode);
        from.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    public LoginResponseModel.UserBean getUserData() {
        return new Gson().fromJson(mPref.getUserData(), LoginResponseModel.UserBean.class);
    }

    public void callInit(String s) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + s));
        if (ContextCompat.checkSelfPermission(BaseActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(BaseActivity.this, new String[]{Manifest.permission.CALL_PHONE}, GlobalValues.RequestCodes.REQUEST_PHONE_CALL);
        } else {
            startActivity(intent);
        }

    }
    /*private QBUser getUserFromSession() {
        QBUser user = mPref.getQbUser();
        QBSessionManager qbSessionManager = QBSessionManager.getInstance();
        if (qbSessionManager.getSessionParameters() == null) {
            ChatHelper.getInstance().destroy();
            return null;
        }
        Integer userId = qbSessionManager.getSessionParameters().getUserId();
        user.setId(userId);
        return user;
    }*/
  /*  public void restoreChatSession() {
        if (ChatHelper.getInstance().isLogged()) {
            QBUser currentUser = getUserFromSession();
            mPref.saveQbUser(currentUser);

            ArrayList<QBUser> qbUsers=new ArrayList<>();
            QBUser qbUser=new QBUser();
            qbUser.setId(Integer.parseInt(getUserData().getQBId()));

            qbUsers.add(qbUser);
            createDialog(qbUsers);
//            ProgressDialogFragment.hide(getFragmentManager());
        } else {
            QBUser currentUser = getUserFromSession();
            if (currentUser == null) {
                cPresenter.login(getUserData());
            } else {
                cPresenter.loginToChat(currentUser);
            }
        }
    }*/
}
