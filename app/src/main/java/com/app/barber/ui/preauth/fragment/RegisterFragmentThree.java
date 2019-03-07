/*
package com.app.barber.ui.preauth.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.app.barber.R;
import com.app.barber.core.BaseFragment;
import com.app.barber.models.request.RegisterRequestModel;
import com.app.barber.ui.preauth.activities.RegisterActivity;
import com.app.barber.util.CommonUtils;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.iface.PageChangeCallBack;
import com.app.barber.views.CustomEditText;
import com.app.barber.views.CustomTextView;
import com.google.gson.Gson;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

*/
/**
 * Created by harish on 23/10/18.
 *//*


public class RegisterFragmentThree extends BaseFragment {
    private static final String TAG = RegisterFragmentThree.class.getSimpleName();
    @BindView(R.id.edtxt_username)
    CustomEditText edtxtUsername;
    @BindView(R.id.edtxt_email)
    CustomEditText edtxtEmail;
    @BindView(R.id.edtxt_password)
    CustomEditText edtxtPassword;
    @BindView(R.id.edtxt_cnfrmPassword)
    CustomEditText edtxtCnfrmPassword;
    @BindView(R.id.continue_btn)
    ImageView continueBtn;
    PageChangeCallBack calBackInterface;
    @BindView(R.id.terms_check_box)
    CheckBox termsCheckBox;
    @BindView(R.id.terms_text)
    CustomTextView termsText;
    private RegisterRequestModel registerRequestModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getFragmentLayout(), container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    protected int getFragmentLayout() {
        return R.layout.layout_register_screen_three;
    }

    @Override
    public void UpdateData(int position, Bundle bundle) {
        Serializable registerData = bundle.getSerializable(GlobalValues.Extras.REGISTER_REQUEST_DATA);
        if (registerData != null) {
            registerRequestModel = (RegisterRequestModel) registerData;
            edtxtUsername.setText(registerRequestModel.getFirstName() + " " + registerRequestModel.getLastName());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            calBackInterface = (PageChangeCallBack) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement MyInterface ");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @OnClick(R.id.continue_btn)
    public void onClick() {
        if (validated()) {
//            activitySwitcher(getActivity(), SpecialiseActivity.class, null);
//            getActivity().finish();
            RegisterRequestModel dataModel = new RegisterRequestModel();
            dataModel.setUserName(edtxtUsername.getText().toString());
            dataModel.setEmail(edtxtEmail.getText().toString());
            dataModel.setPassword(edtxtCnfrmPassword.getText().toString());
            Log.e(TAG, " " + new Gson().toJson(dataModel));

            ((RegisterActivity) getActivity()).onChangePage(2, dataModel);
        }
    }

    private boolean validated() {
        if (CommonUtils.isEmpty(edtxtUsername)) {
            CommonUtils.getInstance(getActivity()).displayMessage(getActivity(), getActivity().getString(R.string.error_user_name));
            return false;
        } else if (CommonUtils.isEmpty(edtxtEmail)) {
            CommonUtils.getInstance(getActivity()).displayMessage(getActivity(), getActivity().getString(R.string.error_email));
            return false;
        } else if (!CommonUtils.isEmailValid(edtxtEmail.getText().toString())) {
            CommonUtils.getInstance(getActivity()).displayMessage(getActivity(), getActivity().getString(R.string.error_email));
            return false;
        } else if (edtxtPassword.getText().toString().length() <= 5) {
            CommonUtils.getInstance(getActivity()).displayMessage(getActivity(), getActivity().getString(R.string.error_password));
            return false;
        } else if (!edtxtPassword.getText().toString().equals(edtxtCnfrmPassword.getText().toString())) {
            CommonUtils.getInstance(getActivity()).displayMessage(getActivity(), getActivity().getString(R.string.error_password_not_matched));
            return false;
        } else if (!termsCheckBox.isChecked()) {
            CommonUtils.getInstance(getActivity()).displayMessage(getActivity(), getActivity().getString(R.string.error_accept_terms));
            return false;
        } else
            return true;
    }
}
*/
