package com.app.barber.ui.preauth.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.barber.R;
import com.app.barber.core.BaseActivity;
import com.app.barber.models.request.RegisterRequestModel;
import com.app.barber.models.request.ValidatePhoneNumberModel;
import com.app.barber.models.response.BaseResponse;
import com.app.barber.models.response.LoginResponseModel;
import com.app.barber.models.response.ValidationResponseModel;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.ui.preauth.authmvp.PreAuthMVPView;
import com.app.barber.ui.preauth.authmvp.PreAuthPresenter;
import com.app.barber.util.CommonUtils;
import com.app.barber.util.GlobalValues;
import com.app.barber.views.CustomEditText;
import com.app.barber.views.CustomTextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by harish on 29/10/18.
 */

public class VerifyMobileActivity extends BaseActivity implements PreAuthMVPView {
    @BindView(R.id.back_toolbar)
    ImageView backToolbar;
    @BindView(R.id.txt_title_toolbar)
    CustomTextView txtTitleToolbar;
    @BindView(R.id.img_edit)
    ImageView imgEdit;
    @BindView(R.id.resend_btn)
    CustomTextView resendBtn;
    @BindView(R.id.verify_Number_btn)
    CustomTextView verifyNumberBtn;
    @BindView(R.id.edtxt_otp)
    CustomEditText edtxtOtp;
    private String enteredNumber;
    private FirebaseAuth mAuth;
    PhoneAuthProvider.ForceResendingToken mResendToken;
    private String countryCode;
    private PreAuthPresenter presenter;
    private boolean isFromForgotPassword;
    private boolean idResend;

    @Override
    public int getLayoutId() {
        return R.layout.layout_verify_mobile;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PreAuthPresenter(this);
        presenter.attachView(this);
        //initializing objects
        getIntentData(getIntent());
    }


    private void getIntentData(Intent intent) {
        enteredNumber = intent.getStringExtra(GlobalValues.Extras.ADD_MOBILE);
        countryCode = intent.getStringExtra(GlobalValues.Extras.COUNTRY_CODE);
        isFromForgotPassword = intent.getBooleanExtra(GlobalValues.Extras.FORGOT_PASSWORD, false);
//        atNumber.setText("at " + countryCode + enteredNumber);
        otprequest();
    }

    private void otprequest() {
        mPref.setPrefrencesBoolean(GlobalValues.Extras.VERIFIED, false);
        checkPhoneNumberValidity();
       /* PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+" + countryCode + enteredNumber,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);*/
        Toast.makeText(VerifyMobileActivity.this, R.string.error_please_wait, Toast.LENGTH_SHORT).show();
    }

    private void checkPhoneNumberValidity() {
        idResend = true;

        ValidatePhoneNumberModel vModel = new ValidatePhoneNumberModel();
        vModel.setPhone(enteredNumber);
        vModel.setUserType(GlobalValues.UserTypes.BARBER);
        presenter.validatePhoneNUmber(NetworkConstatnts.RequestCode.API_VALIDATE_NUMBER, vModel, true);
    }

    private String mVerificationId;
    private String codeReceived;
   /* private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code 
            if (code != null) {
//                editTextCode.setText(code);
                //verifying the code
                codeReceived = code;
//                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(VerifyMobileActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            mVerificationId = s;
            mResendToken = forceResendingToken;
        }
    };*/

   /* private void verifyVerificationCode(String otp) {
        try {
            //creating the credential
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);
            //signing the user
            signInWithPhoneAuthCredential(credential);
        } catch (Exception e) {
            new CommonUtils().displayMessage(VerifyMobileActivity.this, "Error");
        }
    }*/
/*
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(VerifyMobileActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //verification successful we will start the profile activity
//                            Intent intent = new Intent(VerifyMobileActivity.this, ProfileActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            startActivity(intent);
                            new CommonUtils().displayMessage(VerifyMobileActivity.this, "Verified successfully");
                            *//*Intent intent = new Intent();
                            intent.putExtra(GlobalValues.Extras.ADD_MOBILE, enteredNumber);
                            mPref.setPrefrencesBoolean(GlobalValues.Extras.VERIFIED, true);
                            setResult(RESULT_OK, intent);
                            finish();*//*
                            updateMobileNumber();
                        } else {
                            updateMobileNumber();
                            //verification unsuccessful.. display an error message
                            String message = "Somthing is wrong, we will fix it soon...";
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                                new CommonUtils().displayMessage(VerifyMobileActivity.this, message);
                            }
                        }
                    }
                });
    }*/

    private void updateMobileNumber() {
        if (!isFromForgotPassword) {
            presenter.verifyOtp(NetworkConstatnts.RequestCode.API_VERIFY_OTP, enteredNumber, edtxtOtp.getText().toString(), GlobalValues.UserTypes.BARBER, true);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString(GlobalValues.Extras.COUNTRY_CODE, countryCode);
            bundle.putString(GlobalValues.Extras.ADD_MOBILE, enteredNumber);
            activitySwitcher(VerifyMobileActivity.this, ChangePasswordActivity.class, bundle);
            finish();
        }
    }

    /*@Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(GlobalValues.Extras.ADD_MOBILE, "");
        setResult(RESULT_OK, intent);
        finish();
    }*/

    @OnClick({R.id.back_toolbar, R.id.resend_btn, R.id.verify_Number_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_toolbar:
                onBackPressed();
                break;
            case R.id.resend_btn:
                otprequest();
                break;
            case R.id.verify_Number_btn:
                if (validatefields()) {
                    updateMobileNumber();
                }
//                verifyOtp();
                break;
        }
    }

    /*private void verifyOtp() {
        if (validatefields()) {
            String code = edtxtOtp.getText().toString();
            verifyVerificationCode(code);
        }
    }*/

    private boolean validatefields() {
        if (CommonUtils.isEmpty(edtxtOtp)) {
            CommonUtils.getInstance(VerifyMobileActivity.this).
                    displayMessage(VerifyMobileActivity.this,
                            VerifyMobileActivity.this.getString(R.string.error_otp));
            return false;
        } else
            return true;
    }

    @Override
    public void onSuccessResponse(int serviceMode, Object model) {
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_VALIDATE_NUMBER:
                ValidationResponseModel responseData = (ValidationResponseModel) model;
                if (responseData != null)
                    if (((ValidationResponseModel) model).getStatus() == NetworkConstatnts.ResponseCode.success) {
                        if (!idResend) {
                            new CommonUtils().displayMessage(this, ((ValidationResponseModel) model).getMessage());
                            LoginResponseModel.UserBean userData = getUserData();
                            userData.setPhoneNumber(enteredNumber);
                            presenter.saveUserData(userData);
                            presenter.navigateUser(this, userData);
                        } else {
                            if (responseData != null && responseData.getData() != null && responseData.getData().getOTP() != null)
                                edtxtOtp.setText(responseData.getData().getOTP());
                        }
                    } else
                        new CommonUtils().displayMessage(this, ((ValidationResponseModel) model).getMessage());
                break;
            case NetworkConstatnts.RequestCode.API_VALIDATE_SAVE_NUMBER:
                if (((BaseResponse) model).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    new CommonUtils().displayMessage(this, ((BaseResponse) model).getMessage());
                    LoginResponseModel.UserBean userData = getUserData();
                    userData.setPhoneNumber(enteredNumber);
                    presenter.saveUserData(userData);
                    presenter.navigateUser(this, userData);
                } else
                    new CommonUtils().displayMessage(this, ((ValidationResponseModel) model).getMessage());
                break;
            case NetworkConstatnts.RequestCode.API_VERIFY_OTP:
                if (((BaseResponse) model).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    idResend = false;
                    RegisterRequestModel validateRequest = new RegisterRequestModel();
                    validateRequest.setPhoneNumber(enteredNumber);
                    validateRequest.setUserType(GlobalValues.UserTypes.BARBER);
                    validateRequest.setUserId("" + getUserData().getUserID());
                    presenter.validatePhoneNumber(NetworkConstatnts.RequestCode.API_VALIDATE_SAVE_NUMBER, validateRequest, true);
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
