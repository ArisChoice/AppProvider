/*
package com.app.barber.ui.preauth.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.barber.R;
import com.app.barber.core.BaseFragment;
import com.app.barber.ui.preauth.activities.RegisterActivity;
import com.app.barber.util.iface.PageChangeCallBack;
import com.app.barber.views.CustomEditText;
import com.app.barber.views.CustomTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

*/
/**
 * Created by harish on 23/10/18.
 *//*


public class RegisterFragmentTwo extends BaseFragment {
    @BindView(R.id.edtxt_code_one)
    CustomEditText edtxtCodeOne;
    @BindView(R.id.edtxt_code_two)
    CustomEditText edtxtCodeTwo;
    @BindView(R.id.edtxt_code_three)
    CustomEditText edtxtCodeThree;
    @BindView(R.id.edtxt_code_four)
    CustomEditText edtxtCodeFour;
    @BindView(R.id.verify_Number_btn)
    CustomTextView verifyNumberBtn;
    @BindView(R.id.resend_btn)
    CustomTextView resendBtn;
    PageChangeCallBack calBackInterface;

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
        return R.layout.layout_register_screen_two;
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

    @OnClick({ R.id.verify_Number_btn, R.id.resend_btn})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.verify_Number_btn:
                ((RegisterActivity) getActivity()).onChangePage(2,null);
                break;
            case R.id.resend_btn:
                break;
        }
    }
}
*/
