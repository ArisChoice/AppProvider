package com.app.barber.ui.postauth.activities.home.home_adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.barber.R;
import com.app.barber.models.TimeSlotsModel;
import com.app.barber.models.response.OverviewResponseModel;
import com.app.barber.util.CustomDate;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.views.CustomTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class OverviewInsideAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<OverviewResponseModel.DataBean.TimeListBean.DayListBean> durationModelList;
    OnItemClickListener listener;
    Activity specialiseActivity;
    String day;
    int positionn;

    public OverviewInsideAdapter(Activity specialiseActivity, List<OverviewResponseModel.DataBean.TimeListBean.DayListBean> feedsList, int position, OnItemClickListener listener) {
        this.durationModelList = feedsList;
        this.listener = listener;
        this.specialiseActivity = specialiseActivity;
        this.positionn = position;

    }

    @Override
    public SlotsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_bookings_view_adapter, parent, false);
        return new SlotsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SlotsViewHolder) {
            OverviewResponseModel.DataBean.TimeListBean.DayListBean positionData = durationModelList.get(position);
            ((SlotsViewHolder) holder).dayTxt.setText(CustomDate.getCurrentFormat(specialiseActivity, positionData.getDate(), GlobalValues.DateFormats.DEFAULT_FORMAT_DATE, "EEE"));
            ((SlotsViewHolder) holder).dateTxt.setText(CustomDate.getCurrentFormat(specialiseActivity, positionData.getDate(), GlobalValues.DateFormats.DEFAULT_FORMAT_DATE, "dd"));
            if (positionn == 0) {
                ((SlotsViewHolder) holder).calenderViewLay.setVisibility(View.VISIBLE);
                if (CustomDate.getCurrentFormat(specialiseActivity, CustomDate.getCurrentMonth(specialiseActivity, "dd"), "dd", "dd").
                        equals(CustomDate.getCurrentFormat(specialiseActivity, positionData.getDate(), GlobalValues.DateFormats.DEFAULT_FORMAT_DATE, "dd"))) {
                    ((SlotsViewHolder) holder).weekDate.setBackgroundResource(R.drawable.circular_blue_background);
                    ((SlotsViewHolder) holder).weekDate.setTextColor(specialiseActivity.getResources().getColor(R.color.color_white));

                } else {
                    ((SlotsViewHolder) holder).weekDate.setBackgroundResource(R.drawable.circular_white_background);
                    ((SlotsViewHolder) holder).weekDate.setTextColor(specialiseActivity.getResources().getColor(R.color.color_black));
                }
                ((SlotsViewHolder) holder).weekDate.setText("" + CustomDate.getCurrentFormat(specialiseActivity, positionData.getDate(), GlobalValues.DateFormats.DEFAULT_FORMAT_DATE, "dd"));
                ((SlotsViewHolder) holder).weekDay.setText("" + CustomDate.getCurrentFormat(specialiseActivity, positionData.getDate(), GlobalValues.DateFormats.DEFAULT_FORMAT_DATE, "EEE"));
            } else ((SlotsViewHolder) holder).calenderViewLay.setVisibility(View.GONE);

            if (positionData.getAppointmentCount() != 0) {
                ((SlotsViewHolder) holder).appTypeHolder.setVisibility(View.VISIBLE);
                ((SlotsViewHolder) holder).appTypeIcon.setImageResource(R.drawable.scissors);
                ((SlotsViewHolder) holder).appTypeIcon.setBackgroundResource(R.drawable.circular_white_background);
                ((SlotsViewHolder) holder).appCountTxt.setText("" + positionData.getAppointmentCount());
                ((SlotsViewHolder) holder).appTypeHolder.bringToFront();
                ((SlotsViewHolder) holder).appCountTxt.bringToFront();
            } else if (positionData.getAppointmentCount() == 0) {
                ((SlotsViewHolder) holder).appTypeHolder.setVisibility(View.INVISIBLE);
            }
            if (positionData.getCallOutCount() != 0) {
                ((SlotsViewHolder) holder).callTypeHolder.setVisibility(View.VISIBLE);
                ((SlotsViewHolder) holder).callAppTypeIcon.setImageResource(R.drawable.callout_);
                ((SlotsViewHolder) holder).callAppTypeIcon.setBackgroundResource(R.drawable.circular_white_background);
                ((SlotsViewHolder) holder).callCountTxt.setText("" + positionData.getCallOutCount());
                ((SlotsViewHolder) holder).callTypeHolder.bringToFront();
                ((SlotsViewHolder) holder).callCountTxt.bringToFront();
            } else if (positionData.getCallOutCount() == 0) {
                ((SlotsViewHolder) holder).callTypeHolder.setVisibility(View.INVISIBLE);
            }
        }

    }

    @Override
    public int getItemCount() {
        return durationModelList.size();
    }

    public void updateAll(List<OverviewResponseModel.DataBean.TimeListBean.DayListBean> posts) {
        this.durationModelList.clear();
        this.durationModelList.addAll(posts);
        notifyDataSetChanged();
    }

    public void addItem(OverviewResponseModel.DataBean.TimeListBean.DayListBean posts) {
        this.durationModelList.add(0, posts);
        notifyDataSetChanged();
    }

    public void updateLikeData(int selectedPosition, String status, String postId) {


    }

    public void updatePosition(TimeSlotsModel.BreakHoursList o) {

    }

    public List<OverviewResponseModel.DataBean.TimeListBean.DayListBean> getItems() {

        return durationModelList;
    }

    public OverviewResponseModel.DataBean.TimeListBean.DayListBean getPositiondata(int position) {
        return durationModelList.get(position);
    }


    public class SlotsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.apponitment_type_lay)
        RelativeLayout appTypeHolder;
        @BindView(R.id.apointment_type_ico)
        ImageView appTypeIcon;
        @BindView(R.id.app_count_txt)
        CustomTextView appCountTxt;

        @BindView(R.id.calllout_type_lay)
        RelativeLayout callTypeHolder;
        @BindView(R.id.callout_type_ico)
        ImageView callAppTypeIcon;
        @BindView(R.id.calout_count_txt)
        CustomTextView callCountTxt;
        @BindView(R.id.txt_Day)
        CustomTextView dayTxt;
        @BindView(R.id.txt_date)
        CustomTextView dateTxt;

        @BindView(R.id.root_lay)
        RelativeLayout rootLay;

        @BindView(R.id.calender_View_lay)
        LinearLayout calenderViewLay;
        @BindView(R.id.week_day)
        CustomTextView weekDay;
        @BindView(R.id.week_date)
        CustomTextView weekDate;
        @BindView(R.id.appoint_status)
        ImageView appointStatus;


        public SlotsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.apponitment_type_lay, R.id.calllout_type_lay})
        public void onLCick(View v) {

            switch (v.getId()) {
                case R.id.apponitment_type_lay:
                    listener.onItemClick(v, getAdapterPosition(), GlobalValues.ClickOperations.CHECK_APPOINTMENT, null);
                    break;
                case R.id.calllout_type_lay:
                    listener.onItemClick(v, getAdapterPosition(), GlobalValues.ClickOperations.CHECK_APPOINTMENT, null);
                    break;
            }
            notifyDataSetChanged();
        }
    }

    private void refreshSelection(int adapterPosition) {

    }


    public void setData(TimeSlotsModel slotData) {
        // User Detail
    }

    private void toggleRefreshing(boolean b) {
    }


}
