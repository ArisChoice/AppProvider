/*
package com.app.barber.ui.preauth.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.barber.R;
import com.app.barber.core.BarberApplication;
import com.app.barber.core.BaseFragment;
import com.app.barber.models.request.RegisterRequestModel;
import com.app.barber.models.response.BaseResponse;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.ui.preauth.PreAuthMVPView;
import com.app.barber.ui.preauth.PreAuthPresenter;
import com.app.barber.ui.preauth.activities.AddMobileNumberActivity;
import com.app.barber.ui.preauth.activities.RegisterActivity;
import com.app.barber.ui.preauth.activities.VerifyMobileActivity;
import com.app.barber.util.CommonUtils;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.iface.PageChangeCallBack;
import com.app.barber.views.CustomEditText;
import com.hbb20.CountryCodePicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.app.barber.core.BaseActivity.activitySwitcherResult;

*/
/**
 * Created by harish on 23/10/18.
 *//*


public class RegisterFragmentOne extends BaseFragment implements PreAuthMVPView {
    String TAG = RegisterFragmentOne.class.getSimpleName();
    @BindView(R.id.edtxt_first_name)
    CustomEditText edtxtFirstName;
    @BindView(R.id.edtxt_last_name)
    CustomEditText edtxtLastName;
    @BindView(R.id.edtxt_phone_number)
    CustomEditText edtxtPhoneNumber;
    @BindView(R.id.edtxt_invitation_code)
    CustomEditText edtxtInvitationCode;
    @BindView(R.id.continue_btn)
    ImageView continueBtn;
    PageChangeCallBack calBackInterface;
    @BindView(R.id.ccp)
    CountryCodePicker ccp;
    private PreAuthPresenter presenter;
    private RegisterRequestModel dataModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((BarberApplication) getActivity().getApplication()).getMyComponent(getActivity()).inject(this);
        presenter = new PreAuthPresenter(getActivity());
        presenter.attachView(this);
        View rootView = inflater.inflate(getFragmentLayout(), container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.layout_register_screen_one;
    }

    @Override
    public void UpdateData(int position, Bundle bundle) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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

    @OnClick({R.id.continue_btn, R.id.edtxt_phone_number})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.continue_btn:
                if (validate()) {
                    dataModel = new RegisterRequestModel();
                    dataModel.setFirstName(edtxtFirstName.getText().toString());
                    dataModel.setLastName(edtxtLastName.getText().toString());
                    dataModel.setPhoneNumber(edtxtPhoneNumber.getText().toString());
                    if (mPref.getPrefrencesBoolean(GlobalValues.Extras.VERIFIED)) {
                        RegisterRequestModel validateRequest = new RegisterRequestModel();
                        validateRequest.setPhoneNumber(edtxtPhoneNumber.getText().toString());
                        validateRequest.setUserType(GlobalValues.UserTypes.BARBER);
                        presenter.validatePhoneNUmber(NetworkConstatnts.RequestCode.API_VALIDATE_NUMBER, validateRequest, true);
//                        ((RegisterActivity) getActivity()).onChangePage(1, dataModel);
                    } else {
                        CommonUtils.getInstance(getActivity()).displayMessage(getActivity(), getActivity().getString(R.string.error_phone_number_verify));
                        Bundle bundle = new Bundle();
                        bundle.putString(GlobalValues.Extras.COUNTRY_CODE, ccp.getSelectedCountryCode());
                        bundle.putString(GlobalValues.Extras.ADD_MOBILE, edtxtPhoneNumber.getText().toString());
                        activitySwitcherResult(getActivity(), VerifyMobileActivity.class, bundle, GlobalValues.RequestCodes.ADD_MOBILE);
                    }
                }
                break;
            case R.id.edtxt_phone_number:
//                Intent intent = new Intent();
//                intent.putExtra(GlobalValues.Extras.ADD_MOBILE, edtextMobileNumber.getText().toString());
//                intent.putExtra(GlobalValues.Extras.COUNTRY_CODE, ccp.getSelectedCountryCode());
//                intent.putExtra(GlobalValues.Extras.VERIFIED, false);
//                setResult(RESULT_OK, intent);

//                activitySwitcherResult(getActivity(), AddMobileNumberActivity.class, null, GlobalValues.RequestCodes.ADD_MOBILE);
                break;
        }

    }

    private boolean validate() {
        if (CommonUtils.isEmpty(edtxtFirstName)) {
            CommonUtils.getInstance(getActivity()).displayMessage(getActivity(), getActivity().getString(R.string.error_firstName));
            return false;
        } else if (CommonUtils.isEmpty(edtxtLastName)) {
            CommonUtils.getInstance(getActivity()).displayMessage(getActivity(), getActivity().getString(R.string.error_lastName));
            return false;
        } else if (CommonUtils.isEmpty(edtxtPhoneNumber)) {
            CommonUtils.getInstance(getActivity()).displayMessage(getActivity(), getActivity().getString(R.string.error_phone_number));
            return false;
        } else if (edtxtPhoneNumber.getText().toString().length() < 8) {
            CommonUtils.getInstance(getActivity()).displayMessage(getActivity(), getActivity().getString(R.string.error_phone_number));
            return false;
        } else
            return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, " " + requestCode);
        try {
            Log.e(TAG, " ADD_MOBILE  " + data.getStringExtra(GlobalValues.Extras.ADD_MOBILE));
            Log.e(TAG, " COUNTRY_CODE " + data.getStringExtra(GlobalValues.Extras.COUNTRY_CODE));
            Log.e(TAG, " Verified " + data.getBooleanExtra(GlobalValues.Extras.VERIFIED, false));
            edtxtPhoneNumber.setText(data.getStringExtra(GlobalValues.Extras.ADD_MOBILE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccessResponse(int serviceMode, Object model) {
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_VALIDATE_NUMBER:
                if (model != null)
                    if (((BaseResponse) model).getStatus() == NetworkConstatnts.ResponseCode.success) {
//                        new CommonUtils().displayMessage(getActivity(), ((BaseResponse) model).getMessage());
                        ((RegisterActivity) getActivity()).onChangePage(1, dataModel);
                    } else
                        new CommonUtils().displayMessage(getActivity(), ((BaseResponse) model).getMessage());
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
*/
