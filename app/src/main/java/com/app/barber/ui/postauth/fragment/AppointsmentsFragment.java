package com.app.barber.ui.postauth.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.barber.R;
import com.app.barber.core.BarberApplication;
import com.app.barber.core.BaseFragment;
import com.app.barber.models.request.AppointRequestModel;
import com.app.barber.models.request.EntoutRequestModel;
import com.app.barber.models.request.GetFutureStatusRequest;
import com.app.barber.models.request.UpdateAddressRequestModel;
import com.app.barber.models.request.UpdateBookingRequestModel;
import com.app.barber.models.request.UpdateEditBookingStatusModel;
import com.app.barber.models.request.UpdateRequestStatusModel;
import com.app.barber.models.response.AppointmentsResponseModel;
import com.app.barber.models.response.FutureAppointmentStatusModel;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.ui.adapters.AppointmentsAdapter;
import com.app.barber.ui.postauth.activities.client.SelectCustomerActivity;
import com.app.barber.ui.postauth.activities.home.homemvp.HomeAuthMVPView;
import com.app.barber.ui.postauth.activities.home.homemvp.HomeAuthPresenter;
import com.app.barber.ui.postauth.activities.socket_work.chat.ChatActivity;
import com.app.barber.util.CommonUtils;
import com.app.barber.util.CustomDate;
import com.app.barber.util.FunctionalDialog;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.iface.OnBottomDialogItemListener;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.util.weekengine.ModelDay;
import com.app.barber.util.weekengine.WeekViewAdapter;
import com.app.barber.views.CustomTextView;
import com.crashlytics.android.answers.CustomEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.app.barber.core.BaseActivity.activitySwitcher;

/**
 * Created by harish on 25/10/18.
 */

public class AppointsmentsFragment extends BaseFragment implements HomeAuthMVPView {

    private static AppointsmentsFragment fragment;
    @BindView(R.id.appointments_requests_recyclar)
    RecyclerView appointmentsRequestsRecyclar;
    @BindView(R.id.month_name)
    CustomTextView monthName;
    @BindView(R.id.more_txt)
    CustomTextView moreTxt;
    @BindView(R.id.recyclar_view_lst)
    RecyclerView recyclarViewLst;
    @BindView(R.id.no_list_data_text)
    CustomTextView noListDataText;
    @BindView(R.id.add_appointment_fab)
    ImageView addAppointmentFab;
    @BindView(R.id.more_booking_available_status)
    ImageView moreBookingAvailableStatus;
    private AppointmentsAdapter cAdapter;
    private AppointmentsAdapter appointmentsAdapter;
    private HomeAuthPresenter presenter;
    private WeekViewAdapter weekAdapter;

    private int previousTotal = 0;
    private boolean loading = false;
    private int visibleThreshold = 7;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private int recentPosition;
    private String recentDate;
    private String selectedDate;

    @Override
    protected int getFragmentLayout() {
        return R.layout.layout_appointments_screen;
    }

    @Override
    public void UpdateData(int position, Bundle bundle) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragment = this;
        ((BarberApplication) getActivity().getApplication()).getMyComponent(getActivity()).inject(this);
        presenter = new HomeAuthPresenter(getActivity());
        presenter.attachView(this);
        View rootView = inflater.inflate(getFragmentLayout(), container, false);
        ButterKnife.bind(this, rootView);
        initAppoitmnetsAdapter();
        initWeekView();
        return rootView;
    }

    private void initWeekView() {
        weekAdapter = new WeekViewAdapter(getActivity(), new ArrayList<ModelDay>(), new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int type, Object o) {
                switch (type) {
                    case GlobalValues.ClickOperations.DATE_CLICKED:
                        ModelDay selectedDateModel = (ModelDay) o;
                        selectedDate = selectedDateModel.getFullDate();
                        if (selectedDateModel.isApointmentAvailable()) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    weekAdapter.updateAppointmentStatus(position);
                                    presenter.updateDataStatus(NetworkConstatnts.RequestCode.API_UPDATE_FUTURE_STATUS,
                                            new UpdateRequestStatusModel(selectedDateModel.getFullDate()), false);
                                }
                            }, 10);

                        }
                        appointmentsAdapter.clearAll();
                        getUpComingAppoinments(selectedDateModel.getFullDate());
                        break;
                }
            }
        });
        LinearLayoutManager lManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclarViewLst.setLayoutManager(lManager);
        recyclarViewLst.setAdapter(weekAdapter);
        weekAdapter.setCurrentWeek(7);//by default 7 days
        monthName.setText(CustomDate.getCurrentMonth(getActivity(), "MMMM yyyy"));
        getUpComingAppoinments(CustomDate.getCurrentMonth(getActivity(), "MM/dd/yyyy"));//get current day appointments
        getFutureWeekStatus(weekAdapter.getpostionData(0), weekAdapter.getEndDay());

        recyclarViewLst.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = lManager.getChildCount();
                totalItemCount = lManager.getItemCount();
                firstVisibleItem = lManager.findFirstVisibleItemPosition();
                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }

                if (presenter.check(firstVisibleItem) && (totalItemCount <= 42) && recentPosition != firstVisibleItem) {
                    Log.i("scroll ", " appointmet fragment " + firstVisibleItem + "   " + presenter.check(firstVisibleItem));
                    // End has been reached
                    recentPosition = firstVisibleItem;
                    getFutureWeekStatus(weekAdapter.getpostionData(firstVisibleItem), weekAdapter.getEndDay());
                    Log.i("getFutureWeekStatus ", "request ==== " + weekAdapter.getpostionData(firstVisibleItem));
                    loading = true;
                    monthName.setText(new CustomDate().formatThis("MM/dd/yyyy", weekAdapter.getpostionData(firstVisibleItem), "MMMM yyyy"));
                }
            }
        });

    }

    private void getFutureWeekStatus(String s, String endDay) {
        GetFutureStatusRequest sRequest = new GetFutureStatusRequest(s, endDay);
        presenter.getFutureDatesStatus(NetworkConstatnts.RequestCode.API_GET_FUTURE_STATUS, sRequest, false);
    }


    private void getUpComingAppoinments(String fullDate) {
        recentDate = fullDate;
        AppointRequestModel requestAppointment = new AppointRequestModel();
        requestAppointment.setDate(fullDate);
        presenter.getUpcomingAppointments(NetworkConstatnts.RequestCode.API_UPCOMMING_APPOINTMENTS, requestAppointment, false);
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
        Log.e("onMessage", " " + event.getType());
        switch (event.getType()) {
            case GlobalValues.EVENTS.EDIT_CALLBACK:
                getUpComingAppoinments(selectedDate);
                break;
        }
        EventBus.getDefault().removeStickyEvent(event); // don't forget to remove the sticky event if youre done with it
    }

    private void initAppoitmnetsAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        appointmentsAdapter = new AppointmentsAdapter(getActivity(), AppointsmentsFragment.class.getSimpleName(), new ArrayList<>(), new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int type, Object o) {
                switch (type) {
                    case GlobalValues.ClickOperations.CHECK_APPOINTMENT:
                        new FunctionalDialog().openDialogAppointmentReceivedRequest(getActivity(), o, (view1, position1, type1, t) -> {
                            AppointmentsResponseModel.ResponseBean.BookingListBean positionData = (AppointmentsResponseModel.ResponseBean.BookingListBean) o;
                            switch (type1) {
                               /* case 0:
                                    presenter.updateBookingStatus(NetworkConstatnts.RequestCode.API_UPDATE_BOOKING_STATUS,
                                            new UpdateBookingRequestModel(positionData.getId(), false, positionData.getBookingType(), getUserData().getUserID()), false);
                                    appointmentsAdapter.remove(position1);
                                    break;*/
                                case 1:
//                                    presenter.updateBookingStatus(NetworkConstatnts.RequestCode.API_UPDATE_BOOKING_STATUS,
//                                            new UpdateBookingRequestModel(positionData.getId(), true, positionData.getBookingType()), false);
                                    if (CustomDate.isValidTime(getActivity(), positionData.getTimingSlot())) {
                                        presenter.updateBookingStatus(NetworkConstatnts.RequestCode.API_UPDATE_BOOKING_STATUS,
                                                new UpdateBookingRequestModel(positionData.getId(), true, positionData.getBookingType(), getUserData().getUserID()), false);
                                    } else
                                        new CommonUtils().ShowToast(getString(R.string.no_service_timenot_completed_yet));
                                    break;
                                case GlobalValues.RequestCodes.ENROUTE:
                                    new FunctionalDialog().openEnrouteDialog(getActivity(), positionData, new OnBottomDialogItemListener() {
                                        @Override
                                        public void onItemClick(View view, int position, int type, Object t) {
                                            EntoutRequestModel eModel = new EntoutRequestModel();
                                            eModel.setTime(CustomDate.getCurrentMonth(getActivity(), "hh:ss a"));
                                            eModel.setBookingId(String.valueOf(positionData.getId()));
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
                                case GlobalValues.RequestCodes.MESSAGE:
                                    if (positionData.getChatDialog() != null) {
                                        Intent intent = new Intent(getActivity(), ChatActivity.class);
                                        intent.putExtra(GlobalValues.KEYS.EXTRA_DIALOG_ID, positionData.getChatDialog());
                                        intent.putExtra(GlobalValues.KEYS.OTHER_IMAGE, positionData.getUserImage());
                                        intent.putExtra(GlobalValues.KEYS.USER_ID, "" + positionData.getUserId());
                                        startActivity(intent);
                                    }
                                    break;
                                case GlobalValues.RequestCodes.CALL:
                                    if (positionData.getPhone() != null)
                                        callInit(positionData.getPhone());
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

            }
        });
        appointmentsRequestsRecyclar.setLayoutManager(layoutManager);
        appointmentsRequestsRecyclar.setAdapter(appointmentsAdapter);
        appointmentsRequestsRecyclar.setNestedScrollingEnabled(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @OnClick({R.id.month_name, R.id.more_txt, R.id.add_appointment_fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.month_name:
                break;
            case R.id.more_txt:
                if (weekAdapter.getItemCount() <= 7) {
                    weekAdapter.setCurrentWeek(weekAdapter.getItemCount() + 28);//add more days upto 35 days
                    weekAdapter.notifyDataSetChanged();
                    moreTxt.setVisibility(View.GONE);
                    moreBookingAvailableStatus.setVisibility(View.GONE);
                    getFutureWeekStatus(weekAdapter.getpostionData(0), weekAdapter.getEndDay());
                }

                break;
            case R.id.add_appointment_fab:
                activitySwitcher(getActivity(), SelectCustomerActivity.class, null);
                break;
        }
    }


    @Override
    public void onSuccessResponse(int serviceMode, Object o) {
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_UPCOMMING_APPOINTMENTS:
                AppointmentsResponseModel appointmentsData = (AppointmentsResponseModel) o;
                updateViewsData(appointmentsData.getResponse());
                break;
            case NetworkConstatnts.RequestCode.API_GET_FUTURE_STATUS:
                FutureAppointmentStatusModel fStatus = (FutureAppointmentStatusModel) o;
                if (fStatus != null && fStatus.getReponse() != null && fStatus.getReponse().size() > 0)
                    updateWeekStatus(fStatus.getReponse());
                break;
            case NetworkConstatnts.RequestCode.API_EDIT_BOOKING_STATUS:
                getUpComingAppoinments(recentDate);
                break;
            case NetworkConstatnts.RequestCode.API_NOTIFY_ENROUT:
                getUpComingAppoinments(recentDate);
                break;
        }
    }

    private void updateWeekStatus(List<FutureAppointmentStatusModel.ReponseBean> reponse) {
        new Handler().post(() -> {
                    weekAdapter.notifyDateStatus(reponse);
                    weekAdapter.notifyDataSetChanged();
                }
        );
    }

    private void updateViewsData(AppointmentsResponseModel.ResponseBean response) {
        if (response.getBookingList() != null && response.getBookingList().size() > 0) {
            appointmentsAdapter.updateAll(response.getBookingList());
            appointmentsRequestsRecyclar.setVisibility(View.VISIBLE);
            noListDataText.setVisibility(View.GONE);
        } else {
            appointmentsAdapter.clearAll();
            appointmentsRequestsRecyclar.setVisibility(View.GONE);
            noListDataText.setVisibility(View.VISIBLE);
            noListDataText.setText(R.string.str_no_appointments);
            noListDataText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
        }
        if (response.isFutureAppointmentsExist() && moreTxt.getVisibility() == View.VISIBLE) {
            moreBookingAvailableStatus.setVisibility(View.VISIBLE);
        } else moreBookingAvailableStatus.setVisibility(View.GONE);
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

    public void setToDefault() {
        if (weekAdapter != null && weekAdapter.getItemCount() > 0)
            recyclarViewLst.scrollToPosition(0);
    }

    public static AppointsmentsFragment getInstance() {
        return fragment;
    }
}
