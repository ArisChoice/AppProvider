package com.app.barber.ui.postauth.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.barber.R;
import com.app.barber.core.BarberApplication;
import com.app.barber.core.BaseFragment;
import com.app.barber.models.request.AppointRequestModel;
import com.app.barber.models.request.WeekOverViewRequest;
import com.app.barber.models.response.OverviewResponseModel;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.ui.adapters.AppointmentsAdapter;
import com.app.barber.ui.adapters.OverviewAdapter;
import com.app.barber.ui.postauth.activities.home.home_adapter.OverviewInsideAdapter;
import com.app.barber.ui.postauth.activities.home.homemvp.HomeAuthMVPView;
import com.app.barber.ui.postauth.activities.home.homemvp.HomeAuthPresenter;
import com.app.barber.util.CustomDate;
import com.app.barber.util.FunctionalDialog;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.iface.OnBottomDialogItemListener;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.util.weekengine.ModelDay;
import com.app.barber.util.weekengine.WeekViewAdapter;
import com.app.barber.views.CustomTextView;
import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import aligningrecyclerview.AligningRecyclerView;
import aligningrecyclerview.AlignmentManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by harish on 25/10/18.
 */

public class OverviewFragment extends BaseFragment implements HomeAuthMVPView {

    //    @BindView(R.id.appointments_requests_recyclar)
//    RecyclerView appointmentsRequestsRecyclar;
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
    @BindView(R.id.go_past_btn)
    ImageView goPastBtn;
    @BindView(R.id.week_highlight_txt)
    CustomTextView weekHighlightTxt;
    @BindView(R.id.go_next_btn)
    ImageView goNextBtn;
    @BindView(R.id.calender_cntroll_)
    LinearLayout calenderCntroll;
    @BindView(R.id.unused_View)
    CustomTextView unusedView;
    @BindView(R.id.week_view_lay)
    LinearLayout weekViewLay;
    @BindView(R.id.lay_recyclar)
    LinearLayout layRecyclar;
    @BindView(R.id.more_booking_available_status)
    ImageView moreBookingAvailableStatus;

    @BindView(R.id.overview_lay)
    LinearLayout overviewLay;
    @BindView(R.id.timeSlots_recyclar)
    RecyclerView timeSlotsRecyclar;
    private AppointmentsAdapter cAdapter;
    private OverviewAdapter overviewAdapter;
    private HomeAuthPresenter presenter;
    private WeekViewAdapter weekAdapter;

    private int previousTotal = 0;
    private boolean loading = false;
    private int visibleThreshold = 7;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private int recentPosition;
    public static OverviewFragment instance;

    public static OverviewFragment getInstance() {
        return instance;
    }


    @Override
    protected int getFragmentLayout() {
        return R.layout.layout_overview_screen;
    }

    @Override
    public void UpdateData(int position, Bundle bundle) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((BarberApplication) getActivity().getApplication()).getMyComponent(getActivity()).inject(this);
        instance = this;
        presenter = new HomeAuthPresenter(getActivity());
        presenter.attachView(this);
        View rootView = inflater.inflate(getFragmentLayout(), container, false);
        ButterKnife.bind(this, rootView);
        initOverviewAdapter();
        initWeekView();
        addAppointmentFab.setVisibility(View.GONE);
        moreTxt.setVisibility(View.VISIBLE);
        unusedView.setVisibility(View.VISIBLE);
        overviewLay.setVisibility(View.VISIBLE);
        return rootView;
    }

    private void getOverViewData(String firstDay, String lastDay) {
        WeekOverViewRequest wekRequest = new WeekOverViewRequest();
        wekRequest.setStartDate(firstDay);
        wekRequest.setEndDate(lastDay);
        presenter.getWeekOverView(NetworkConstatnts.RequestCode.API_GET_WEEK_OVERVIEW, wekRequest, false);
//        calenderCntroll.setVisibility(View.VISIBLE);
    }

    private void initWeekView() {
        weekAdapter = new WeekViewAdapter(getActivity(), new ArrayList<ModelDay>(), new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int type, Object o) {
                switch (type) {
                    case GlobalValues.ClickOperations.DATE_CLICKED:
                        ModelDay selectedDateModel = (ModelDay) o;
                        getUpComingAppoinments(selectedDateModel.getFullDate());
                        break;
                }
            }
        });
        LinearLayoutManager lManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclarViewLst.setLayoutManager(lManager);
        recyclarViewLst.setAdapter(weekAdapter);
        weekViewLay.setVisibility(View.GONE);
        weekAdapter.setCurrentWeek(35);
        weekAdapter.noDeafultselection();
        monthName.setText(CustomDate.getCurrentMonth(getActivity(), "MMMM yyyy"));
        getOverViewData(weekAdapter.getpostionData(0), weekAdapter.getpostionData(0 + 7));
        weekAdapter.getPastWeek(8);
        recyclarViewLst.scrollToPosition(7);
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
                if (presenter.checkNew(firstVisibleItem) && (totalItemCount <= 42) && recentPosition != firstVisibleItem) {
                    Log.i("Scroll", "========================= " + recentPosition + "  " + firstVisibleItem + "   " + presenter.check(firstVisibleItem));
                    // End has been reached
                    recentPosition = firstVisibleItem;
                    getOverViewData(weekAdapter.getpostionData(firstVisibleItem), weekAdapter.getpostionData(firstVisibleItem + 10));
                    Log.i("getOverViewData ", " request" + weekAdapter.getpostionData(firstVisibleItem) + "   " + weekAdapter.getpostionData(firstVisibleItem + 7));
                    loading = true;
                    monthName.setText(new CustomDate().formatThis("MM/dd/yyyy", weekAdapter.getpostionData(firstVisibleItem), "MMMM yyyy"));
                }
            }
        });
    }


    private void getUpComingAppoinments(String fullDate) {
        AppointRequestModel requestAppointment = new AppointRequestModel();
        requestAppointment.setDate(fullDate);
        presenter.getUpcomingAppointments(NetworkConstatnts.RequestCode.API_UPCOMMING_APPOINTMENTS, requestAppointment, false);

    }

    private void initOverviewAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        overviewAdapter = new OverviewAdapter(getActivity(), new ArrayList<>(), new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int type, Object o) {

            }
        });
        timeSlotsRecyclar.setLayoutManager(layoutManager);
        timeSlotsRecyclar.setAdapter(overviewAdapter);
        timeSlotsRecyclar.setNestedScrollingEnabled(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @OnClick({R.id.month_name, R.id.more_txt, R.id.go_past_btn, R.id.go_next_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.month_name:
                break;
            case R.id.more_txt:

                showDatePicker();
                break;
            /*case R.id.go_past_btn:
                weekHighlightTxt.setText(R.string.str_past_week);
                weekAdapter.getPastWeek(7);
                getOverViewData();
                goPastBtn.setEnabled(false);
                goPastBtn.setBackgroundResource(R.drawable.rectangle_light_grey_drawable);
                goNextBtn.setEnabled(true);
                goNextBtn.setBackgroundResource(R.drawable.rectangle_blue_drawable);
                refreshScreen();
                break;
            case R.id.go_next_btn:
                weekHighlightTxt.setText(R.string.str_current_week);
                weekAdapter.getNextWeek(7);
                getOverViewData();
                goPastBtn.setEnabled(true);
//                goNextBtn.setEnabled(false);
                goPastBtn.setBackgroundResource(R.drawable.rectangle_blue_drawable);
//                goNextBtn.setBackgroundResource(R.drawable.rectangle_light_grey_drawable);
                refreshScreen();
                break;*/
        }
    }

    private void showDatePicker() {
        new FunctionalDialog().openDateSelectionsDialog(getActivity(), new OnBottomDialogItemListener() {
            @Override
            public void onItemClick(View view, int position, int type, Object t) {
                switch (type) {
                    case GlobalValues.RequestCodes.SELECTED_DAYS:
                        String date = (String) t;
                        getOverViewData(date.split("@")[0], date.split("@")[1]);
                        monthName.setText(new CustomDate().formatThis("MM/dd/yyyy", date.split("@")[0], "MMMM yyyy"));
                        break;
                }
            }
        });
    }

    private void refreshScreen() {
        monthName.setText(CustomDate.formatThis("MM/dd/yyyy", weekAdapter.getFirstDay(), "MMMM yyyy"));
        weekHighlightTxt.setText(CustomDate.formatThis("MM/dd/yyyy", weekAdapter.getFirstDay(), "dd MMM") + " - " +
                CustomDate.formatThis("MM/dd/yyyy", weekAdapter.getEndDay(), "dd MMM"));
    }

    @Override
    public void onSuccessResponse(int serviceMode, Object o) {
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_GET_WEEK_OVERVIEW:
                OverviewResponseModel respData = ((OverviewResponseModel) o);
                if (respData.getStatus() == NetworkConstatnts.ResponseCode.success) {
                    if (respData.getData() != null && respData.getData().getTimeList() != null && respData.getData().getTimeList().size() > 0) {
                        overviewAdapter.updateAll(respData.getData().getTimeList());
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                setCalenderData(respData.getData().getTimeList());
                            }
                        });
                    } else {

                    }

                }
                break;
        }
    }

    AlignmentManager am;
    ArrayList<AligningRecyclerView> aRList;

    OverviewInsideAdapter oAdapter;

    private void setCalenderData(List<OverviewResponseModel.DataBean.TimeListBean> timeList) {
//        appointmentsRequestsRecyclar.setVisibility(View.GONE);
        layRecyclar.setVisibility(View.VISIBLE);
        aRList = new ArrayList<AligningRecyclerView>();
        layRecyclar.removeAllViews();
        for (int i = 0; i < timeList.size(); i++) {

            View layoutView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_common_recyclay_view, layRecyclar, false);
            AligningRecyclerView rView = layoutView.findViewById(R.id.recyclar_view_lst);
            LinearLayoutManager lManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//         lManager = new FlexboxLayoutManager(serviceListActivity);
//         lManager.setFlexDirection(FlexDirection.ROW);
//         lManager.setFlexWrap(FlexWrap.WRAP);
//         bookingsRecyclar.setLayoutManager(lManager);
            oAdapter = new OverviewInsideAdapter(getActivity(), timeList.get(i).getDayList(), i, new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position, int type, Object o) {
                    Log.i("onItemClick", "   ===========   " + oAdapter.getPositiondata(position).getDate());
                }
            });
            rView.setLayoutManager(lManager);
            rView.setAdapter(oAdapter);
            rView.setTag(i);
            rView.setItemAnimator(new DefaultItemAnimator());
            layRecyclar.addView(layoutView);
            rView.setNestedScrollingEnabled(false);
            aRList.add(rView);
        }
        Log.e(" setCalenderData ", "  " + aRList.size());

        am.join(AligningRecyclerView.ALIGN_ORIENTATION_HORIZONTAL, getvalidList(aRList));

    }

    private AligningRecyclerView[] getvalidList(ArrayList<AligningRecyclerView> aRList) {
        AligningRecyclerView[] alist = new AligningRecyclerView[aRList.size()];
        for (int i = 0; i < aRList.size(); i++) {
            alist[i] = aRList.get(i);
        }
        return alist;
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
            recyclarViewLst.scrollToPosition(7);
    }
}
