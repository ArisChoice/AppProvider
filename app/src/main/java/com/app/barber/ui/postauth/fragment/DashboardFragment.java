package com.app.barber.ui.postauth.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.barber.R;
import com.app.barber.core.BarberApplication;
import com.app.barber.core.BaseFragment;
import com.app.barber.models.request.EntoutRequestModel;
import com.app.barber.models.request.UpdateBookingRequestModel;
import com.app.barber.models.request.UpdateEditBookingStatusModel;
import com.app.barber.models.response.AppointmentsResponseModel;
import com.app.barber.models.response.BookingStatusResponse;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.ui.adapters.AppointmentsAdapter;
import com.app.barber.ui.adapters.CalloutAdapter;
import com.app.barber.ui.postauth.activities.HomeActivity;
import com.app.barber.ui.postauth.activities.client.AddAppointmentActivity;
import com.app.barber.ui.postauth.activities.home.BlockHoursActivity;
import com.app.barber.ui.postauth.activities.home.CanceledAppointmentsActivity;
import com.app.barber.ui.postauth.activities.home.homemvp.HomeAuthMVPView;
import com.app.barber.ui.postauth.activities.home.homemvp.HomeAuthPresenter;
import com.app.barber.ui.postauth.activities.settings.ProgressActivity;
import com.app.barber.ui.postauth.activities.socket_work.chat.ChatActivity;
import com.app.barber.util.CommonUtils;
import com.app.barber.util.CustomDate;
import com.app.barber.util.FunctionalDialog;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.ImageUtility;
import com.app.barber.util.iface.OnBottomDialogItemListener;
import com.app.barber.views.CustomTextView;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.app.barber.core.BaseActivity.activitySwitcher;

/**
 * Created by harish on 25/10/18.
 */

public class DashboardFragment extends BaseFragment implements HomeAuthMVPView {
    @BindView(R.id.callout_requests_recyclar)
    RecyclerView calloutRequestsRecyclar;
    @BindView(R.id.appointments_requests_recyclar)
    RecyclerView appointmentsRequestsRecyclar;

    @BindView(R.id.user_name)
    CustomTextView userName;
    @BindView(R.id.total_earnings)
    CustomTextView totalEarnings;
    @BindView(R.id.total_appointments)
    CustomTextView totalAppointments;
    @BindView(R.id.total_callout)
    CustomTextView totalCallout;
    @BindView(R.id.total_canceled)
    CustomTextView totalCanceled;
    @BindView(R.id.txt_callout_requests)
    CustomTextView txtCalloutRequests;
    @BindView(R.id.txt_appointments_requests)
    CustomTextView txtAppointmentsRequests;

    @BindView(R.id.txt_callout_requests_count)
    CustomTextView txtCalloutRequestsCount;
    @BindView(R.id.swipre_refresh)
    SwipeRefreshLayout swipreRefresh;
    @BindView(R.id.callout_holder_lay)
    LinearLayout calloutHolderLay;
    @BindView(R.id.appointment_holder_lay)
    LinearLayout appointmentHolderLay;
    @BindView(R.id.root_layout)
    LinearLayout rootLayout;
    @BindView(R.id.canceled_layout)
    LinearLayout canceledLayout;
    @BindView(R.id.dashboard_floating_btn)
    ImageView dashboardFloatingBtn;
    @BindView(R.id.new_appointment_status)
    ImageView newAppointmentStatus;
    private CalloutAdapter cAdapter;
    private AppointmentsAdapter appointmentsAdapter;
    private HomeAuthPresenter presenter;
    private static DashboardFragment instance;


    @Override
    protected int getFragmentLayout() {
        return R.layout.layout_dashboard_screen;
    }

    @Override
    public void UpdateData(int position, Bundle bundle) {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        instance = this;
        ((BarberApplication) getActivity().getApplication()).getMyComponent(getActivity()).inject(this);
        presenter = new HomeAuthPresenter(getActivity());
        presenter.attachView(this);
        View rootView = inflater.inflate(getFragmentLayout(), container, false);
        ButterKnife.bind(this, rootView);
        initCalloutRecyclar();
        initAppoitmnetsAdapter();
        initSwiperRefresh();
        userName.setText(getActivity().getString(R.string.str_hi) + getUserData().getFullName());
        return rootView;
    }

    private void initSwiperRefresh() {
        swipreRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipreRefresh.setRefreshing(true);
                getActiveAppointments();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getActiveAppointments();
    }

    private void getActiveAppointments() {
        presenter.getActiveAppointments(NetworkConstatnts.RequestCode.API_DASHBOARD_APPOINTMENTS, null, true);
    }

    private void initAppoitmnetsAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        appointmentsAdapter = new AppointmentsAdapter(getActivity(), DashboardFragment.class.getSimpleName(), new ArrayList<>(), (view, position, type, o) -> {
            switch (type) {
                case GlobalValues.ClickOperations.CHECK_APPOINTMENT:
                    new FunctionalDialog().openDialogAppointmentReceivedRequest(getActivity(), o, (view1, position1, type1, t) -> {
                        AppointmentsResponseModel.ResponseBean.BookingListBean positionData = (AppointmentsResponseModel.ResponseBean.BookingListBean) o;
                        switch (type1) {
                            case 0:
                                presenter.updateBookingStatus(NetworkConstatnts.RequestCode.API_UPDATE_BOOKING_STATUS,
                                        new UpdateBookingRequestModel(positionData.getId(), false, positionData.getBookingType(), getUserData().getUserID()), false);
                                appointmentsAdapter.remove(position1);
                                break;
                            case 1:
//                                presenter.updateBookingStatus(NetworkConstatnts.RequestCode.API_UPDATE_BOOKING_STATUS,
//                                        new UpdateBookingRequestModel(positionData.getId(), true, positionData.getBookingType(), getUserData().getUserID()), false);
                                if (CustomDate.isValidTime(getActivity(), positionData.getTimingSlot())) {
                                    presenter.updateBookingStatus(NetworkConstatnts.RequestCode.API_UPDATE_BOOKING_STATUS,
                                            new UpdateBookingRequestModel(positionData.getId(), true, positionData.getBookingType(), getUserData().getUserID()), false);
                                } else
                                    new CommonUtils().ShowToast(getString(R.string.no_service_timenot_completed_yet));
                                break;
                            case GlobalValues.RequestCodes.MESSAGE:
                                if (positionData.getChatDialog() != null) {
                                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                                    intent.putExtra(GlobalValues.KEYS.EXTRA_DIALOG_ID, positionData.getChatDialog());
                                    intent.putExtra(GlobalValues.KEYS.OTHER_IMAGE, positionData.getUserImage());
                                    intent.putExtra(GlobalValues.KEYS.USER_ID, "" + positionData.getUserId());
                                    startActivity(intent);
                                }
                                break;
                            case GlobalValues.RequestCodes.ENROUTE:
                                new FunctionalDialog().openEnrouteDialog(getActivity(), positionData, new OnBottomDialogItemListener() {
                                    @Override
                                    public void onItemClick(View view, int position, int type, Object t) {
                                        EntoutRequestModel eModel = new EntoutRequestModel();
                                        eModel.setTime(CustomDate.getCurrentMonth(getActivity(), "hh:ss a"));
                                        eModel.setBookingId(String.valueOf(positionData.getId()));
                                        eModel.setStartTime(CustomDate.getCurrentMonth(getActivity(), GlobalValues.DateFormats.TIME_FORMAT));
                                        switch (type) {
                                            case GlobalValues.RequestCodes.SEND:
                                                eModel.setTime("" + (int) t);
                                                presenter.notiFyEnrout(NetworkConstatnts.RequestCode.API_NOTIFY_ENROUT, eModel, true);
                                                break;
                                            case GlobalValues.RequestCodes.SKIP_IT:
                                                eModel.setTime(null);
                                                presenter.notiFyEnrout(NetworkConstatnts.RequestCode.API_NOTIFY_ENROUT, eModel, true);
                                                break;
                                        }
                                    }
                                });
                                break;
                            case GlobalValues.RequestCodes.CANCEL:
                                new FunctionalDialog().openDialogCancelAppointment(getActivity(), positionData, new OnBottomDialogItemListener() {
                                    @Override
                                    public void onItemClick(View view, int position, int type, Object t) {
                                        switch (type) {
                                            case GlobalValues.RequestCodes.CANCEL:
                                                presenter.updateBookingStatus(NetworkConstatnts.RequestCode.API_UPDATE_BOOKING_STATUS,
                                                        new UpdateBookingRequestModel(positionData.getId(), false, positionData.getBookingType(), getUserData().getUserID()), false);
                                                appointmentsAdapter.remove(position1);
                                                break;
                                        }
                                    }
                                });
                                break;
                            case GlobalValues.RequestCodes.CALL:
                                if (positionData.getPhone() != null)
                                    callInit(positionData.getPhone());
                                break;
                            case GlobalValues.RequestCodes.REJECT_REQUEST:
                                UpdateEditBookingStatusModel uRequest = new UpdateEditBookingStatusModel();
                                uRequest.setBarberId(positionData.getBarberId());
                                uRequest.setBookingId(positionData.getId());
                                uRequest.setBookingType(positionData.getBookingType());
                                uRequest.setDate(positionData.getEditData().split("@")[0]);
                                uRequest.setTimingSlot(positionData.getEditData().split("@")[1]);
                                uRequest.setUserId(getUserData().getUserID());
                                uRequest.setEditedBy(GlobalValues.UserTypes.BARBER);
                                uRequest.setStatus(false);
                                presenter.updateEditStatus(NetworkConstatnts.RequestCode.API_EDIT_BOOKING_STATUS, uRequest, true);
                                break;
                            case GlobalValues.RequestCodes.ACCEPT_REQUEST:
                                uRequest = new UpdateEditBookingStatusModel();
                                uRequest.setBarberId(positionData.getBarberId());
                                uRequest.setBookingId(positionData.getId());
                                uRequest.setBookingType(positionData.getBookingType());
                                uRequest.setDate(positionData.getEditData().split("@")[0]);
                                uRequest.setTimingSlot(positionData.getEditData().split("@")[1]);
                                uRequest.setUserId(getUserData().getUserID());
                                uRequest.setStatus(true);
                                uRequest.setEditedBy(GlobalValues.UserTypes.BARBER);
                                presenter.updateEditStatus(NetworkConstatnts.RequestCode.API_EDIT_BOOKING_STATUS, uRequest, true);
                                break;
                            case GlobalValues.RequestCodes.CANCED_REQUEST:
                                uRequest = new UpdateEditBookingStatusModel();
                                uRequest.setBarberId(positionData.getBarberId());
                                uRequest.setBookingId(positionData.getId());
                                uRequest.setBookingType(positionData.getBookingType());
                                uRequest.setDate(positionData.getEditData().split("@")[0]);
                                uRequest.setTimingSlot(positionData.getEditData().split("@")[1]);
                                uRequest.setUserId(getUserData().getUserID());
                                uRequest.setStatus(false);
                                uRequest.setEditedBy(GlobalValues.UserTypes.BARBER);
                                presenter.updateEditStatus(NetworkConstatnts.RequestCode.API_EDIT_BOOKING_STATUS, uRequest, true);
                                break;
                        }
                    });
                    break;
            }

        });
        appointmentsRequestsRecyclar.setLayoutManager(layoutManager);
        appointmentsRequestsRecyclar.setAdapter(appointmentsAdapter);
        appointmentsRequestsRecyclar.setNestedScrollingEnabled(false);
    }

    private void initCalloutRecyclar() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        cAdapter = new CalloutAdapter(getActivity(), new ArrayList<>(), (view, position, type, o) -> {
            switch (type) {
                case GlobalValues.ClickOperations.CALLOUT_REQUEST:
                    new FunctionalDialog().openDialogCalloutReceivedRequest(getActivity(), o, (view1, position1, type1, t) -> {
                        AppointmentsResponseModel.ResponseBean.BookingListBean positionData = (AppointmentsResponseModel.ResponseBean.BookingListBean) o;
                        switch (type1) {
                            case 0:
                                if (!positionData.isConfirmed()) {
                                    presenter.updateBookingStatus(NetworkConstatnts.RequestCode.API_UPDATE_CALLOUT_STATUS,
                                            new UpdateBookingRequestModel(positionData.getId(), false, positionData.getBookingType(), getUserData().getUserID()), false);
                                    cAdapter.remove(position1);
                                } else {
                                    presenter.updateBookingStatus(NetworkConstatnts.RequestCode.API_UPDATE_BOOKING_STATUS,
                                            new UpdateBookingRequestModel(positionData.getId(), false, positionData.getBookingType(), getUserData().getUserID()), false);
                                }
                                break;
                            case 1:
                                if (!positionData.isConfirmed()) {
                                    presenter.updateBookingStatus(NetworkConstatnts.RequestCode.API_UPDATE_CALLOUT_STATUS,
                                            new UpdateBookingRequestModel(positionData.getId(), true, positionData.getBookingType(), getUserData().getUserID()), false);
                                } else {
                                    presenter.updateBookingStatus(NetworkConstatnts.RequestCode.API_UPDATE_BOOKING_STATUS,
                                            new UpdateBookingRequestModel(positionData.getId(), true, positionData.getBookingType(), getUserData().getUserID()), false);
                                }
                                break;
                            case GlobalValues.RequestCodes.MESSAGE:
                                if (positionData.getChatDialog() != null) {
                                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                                    intent.putExtra(GlobalValues.KEYS.EXTRA_DIALOG_ID, positionData.getChatDialog());
                                    intent.putExtra(GlobalValues.KEYS.OTHER_IMAGE, positionData.getUserImage());
                                    intent.putExtra(GlobalValues.KEYS.USER_ID, ""+positionData.getUserId());
                                    startActivity(intent);
                                }
                                break;
                            case GlobalValues.RequestCodes.CALL:
                                if (positionData.getPhone() != null)
                                    callInit(positionData.getPhone());
                                break;
                        }
                    });
                    break;
            }
        });
        calloutRequestsRecyclar.setLayoutManager(layoutManager);
        calloutRequestsRecyclar.setAdapter(cAdapter);
    }


    /**
     * Set up a date time interpreter which will show short date values when in week view and long
     * date values otherwise.
     *
     * @param shortDate True if the date values should be short.
     */


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onSuccessResponse(int serviceMode, Object o) {
        swipreRefresh.setRefreshing(false);
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_DASHBOARD_APPOINTMENTS:
                AppointmentsResponseModel appointmentsData = (AppointmentsResponseModel) o;
                updateViewsData(appointmentsData.getResponse());
                break;
            case NetworkConstatnts.RequestCode.API_UPDATE_BOOKING_STATUS:
                BookingStatusResponse bData = (BookingStatusResponse) o;
                new CommonUtils().displayMessage(getActivity(), bData.getMessage());
                updateBookingView(bData.getstatus(), bData.getId());
                break;
            case NetworkConstatnts.RequestCode.API_UPDATE_CALLOUT_STATUS:
                bData = (BookingStatusResponse) o;
                new CommonUtils().displayMessage(getActivity(), bData.getMessage());
                cAdapter.notifyScreen(bData.getId(), bData.getstatus());
//                updateCalloutView(bData.getstatus(), bData.getId());
                getActiveAppointments();
                break;
            case NetworkConstatnts.RequestCode.API_EDIT_BOOKING_STATUS:
                getActiveAppointments();
                break;
            case NetworkConstatnts.RequestCode.API_NOTIFY_ENROUT:
                getActiveAppointments();
                break;
        }
    }

    private void updateCalloutView(boolean status, int id) {
        cAdapter.updatePositionData(id, status);
    }

    private void updateBookingView(boolean status, int id) {
        appointmentsAdapter.updatePositionData(id, status);
    }

    private void updateViewsData(AppointmentsResponseModel.ResponseBean response) {
        userName.setText(getActivity().getString(R.string.str_hi) + response.getName());
        totalCallout.setText("" + response.getCallOutRequestCount());
        totalAppointments.setText("" + response.getBookingRequestCount());
        totalEarnings.setText(GlobalValues.Currency.POUNDS + response.getTotalEarnings());
        txtCalloutRequestsCount.setText("" + response.getCallOutRequestCount());

        if (response.getBookingList() != null && response.getBookingList().size() > 0) {
            appointmentsAdapter.updateAll(response.getBookingList());
            appointmentsRequestsRecyclar.setVisibility(View.VISIBLE);
        } else {
            appointmentsRequestsRecyclar.setVisibility(View.GONE);
            addCustomView(appointmentHolderLay);
        }
        if (response.getCallOutRequestList() != null && response.getCallOutRequestList().size() > 0) {
            cAdapter.updateAll(response.getCallOutRequestList());
            calloutRequestsRecyclar.setVisibility(View.VISIBLE);
        } else {
            calloutRequestsRecyclar.setVisibility(View.GONE);

        }
        totalCanceled.setText("" + response.getCancelledCount());
        try {
            if (response.getBookingList().size() == 0 &&
                    response.getCallOutRequestList().size() == 0) {
                addCustomView(rootLayout);
            }
        } catch (Exception e) {
            addCustomView(rootLayout);
        }
        if (response.isFutureAppointments()) {
            newAppointmentStatus.setVisibility(View.VISIBLE);
        } else newAppointmentStatus.setVisibility(View.GONE);
    }

    private void addCustomView(LinearLayout appointmentHolderLay) {
        View layoutView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_no_appointments, appointmentHolderLay, false);
        appointmentHolderLay.removeAllViews();
        appointmentHolderLay.addView(layoutView);
        SimpleDraweeView imageVw = layoutView.findViewById(R.id.img_no_appointment);
        CustomTextView userName = layoutView.findViewById(R.id.user_name_no_appointment);
        if (getUserData() != null) {
            imageVw.setImageURI(ImageUtility.getValidUrl(getUserData().getProfileImage()));
            userName.setText(getUserData().getUserName());
        }
    }

    @Override
    public void onfaliurResponse(int serviceMode, Object o) {
        swipreRefresh.setRefreshing(false);
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_UPDATE_BOOKING_STATUS:
                BookingStatusResponse bData = (BookingStatusResponse) o;
                new CommonUtils().displayMessage(getActivity(), bData.getMessage());
                break;
            case NetworkConstatnts.RequestCode.API_UPDATE_CALLOUT_STATUS:
                bData = (BookingStatusResponse) o;
                new CommonUtils().displayMessage(getActivity(), bData.getMessage());
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

    @OnClick({R.id.total_earnings, R.id.canceled_layout, R.id.appointment_layout, R.id.callout_layout, R.id.dashboard_floating_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.total_earnings:
                activitySwitcher(getActivity(), ProgressActivity.class, null);
                break;
            case R.id.canceled_layout:
                activitySwitcher(getActivity(), CanceledAppointmentsActivity.class, null);
                break;
            case R.id.appointment_layout:
                HomeActivity.getInstance().navigateView(1);
                newAppointmentStatus.setVisibility(View.GONE);
                presenter.updateBlueDotStatus(NetworkConstatnts.RequestCode.API_UPDATE_TODAY_APPOINTMENT_STATUS, false);
                break;
            case R.id.callout_layout:
                HomeActivity.getInstance().navigateView(1);
                break;
            case R.id.dashboard_floating_btn:
                openPopup();
                break;
        }
    }

    private void openPopup() {

        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dashboard_dialog_layout);
        dialog.setTitle(null);
        dialog.setCanceledOnTouchOutside(true);

        /*
         * Set the access window object Christmas box and parameter object to modify the layout of the dialog,
         * Can directly call getWindow (), said the Activity Window
         * Object, attribute that can change the Activity in the same way
         */
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.RIGHT | Gravity.BOTTOM);

        lp.x = 100; // The new position of the X coordinates
        lp.y = 380; // The new position of the Y coordinates
//        lp.width = 300; // Width
//        lp.height = 300; // Height
//        lp.alpha = 0.7f; // Transparency


        dialogWindow.setAttributes(lp);

        CustomTextView creatBtn = dialog.findViewById(R.id.create_appointment_btn);
        CustomTextView bloclHrs = dialog.findViewById(R.id.block_hours_btn);
        creatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activitySwitcher(getActivity(), AddAppointmentActivity.class, null);
            }
        });
        bloclHrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activitySwitcher(getActivity(), BlockHoursActivity.class, null);
            }
        });

        dialog.show();


    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessage(com.app.barber.util.bus_event.CustomEvent event) {
        Log.d("onMessage", " " + event.getType());
        switch (event.getType()) {
            case GlobalValues.EVENTS.EDIT_CALLBACK:
                getActiveAppointments();
                break;
        }
        EventBus.getDefault().removeStickyEvent(event); // don't forget to remove the sticky event if youre done with it

    }

    public static DashboardFragment getInstance() {
        return instance;
    }


}
