package com.app.barber.ui.adapters;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.barber.R;
import com.app.barber.models.TimeSlotsModel;
import com.app.barber.models.response.AppointmentsResponseModel;
import com.app.barber.models.response.ServiceListResponseModel;
import com.app.barber.util.CommonUtils;
import com.app.barber.util.CustomDate;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.views.CustomTextView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CalloutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity serviceListActivity;
    ArrayList<AppointmentsResponseModel.ResponseBean.BookingListBean> list;
    OnItemClickListener onItemClickListener;


    public CalloutAdapter(FragmentActivity serviceListActivity, ArrayList<AppointmentsResponseModel.ResponseBean.BookingListBean> list, OnItemClickListener onItemClickListener) {
        this.serviceListActivity = serviceListActivity;
        this.list = list;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_callout_adapter, parent, false);
        return new ServiceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AppointmentsResponseModel.ResponseBean.BookingListBean positionData = list.get(position);
        if (holder instanceof CalloutAdapter.ServiceViewHolder) {
            ((CalloutAdapter.ServiceViewHolder) holder).userName.setText(positionData.getName());
            ((CalloutAdapter.ServiceViewHolder) holder).userImageVw.setImageURI(CommonUtils.getValidUrl(positionData.getUserImage()));
            ((CalloutAdapter.ServiceViewHolder) holder).userService.setText(positionData.getServiceNames());
            ((CalloutAdapter.ServiceViewHolder) holder).timeSlotText.setText(positionData.getTimingSlot());
            ((CalloutAdapter.ServiceViewHolder) holder).payableApountTxt.setText(GlobalValues.Currency.POUNDS + positionData.getAmount());
            if (positionData.isCompleted()) {
                ((ServiceViewHolder) holder).timeRemainingTxt.setText(R.string.str_completed);
                ((ServiceViewHolder) holder).timeRemainingTxt.setBackgroundResource(R.drawable.rectangle_green_border);
                ((ServiceViewHolder) holder).timeRemainingTxt.setTextColor(serviceListActivity.getResources().getColor(R.color.color_white));
            } else {
                ((CalloutAdapter.ServiceViewHolder) holder).timeRemainingTxt.setText(CustomDate.formatTimeRemainig(serviceListActivity, positionData.getTimeRemaining()));
                ((CalloutAdapter.ServiceViewHolder) holder).timeRemainingTxt.setTextColor(serviceListActivity.getResources().getColor(R.color.color_white));
                ((ServiceViewHolder) holder).timeRemainingTxt.setBackgroundResource(R.drawable.rectangle_grey_background);

            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateAll(List<AppointmentsResponseModel.ResponseBean.BookingListBean> posts) {
        this.list.clear();
        this.list.addAll(posts);
        notifyDataSetChanged();
    }

    public void addItem(AppointmentsResponseModel.ResponseBean.BookingListBean posts) {
        this.list.add(0, posts);
        notifyDataSetChanged();
    }

    public void updateLikeData(int selectedPosition, String status, String postId) {

    }

    public void remove(int positio) {
        list.remove(positio);
        notifyDataSetChanged();
    }

    public void notifyScreen(int id, boolean getstatus) {

    }

    public void updatePositionData(int id, boolean status) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == id) {
                list.get(i).setConfirmed(status);
            }
        }
        notifyDataSetChanged();
    }

    public class ServiceViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.user_name)
        CustomTextView userName;
        @BindView(R.id.user_service)
        CustomTextView userService;
        @BindView(R.id.time_slot_txt)
        CustomTextView timeSlotText;
        @BindView(R.id.payable_apount_txt)
        CustomTextView payableApountTxt;
        @BindView(R.id.time_remaining_txt)
        CustomTextView timeRemainingTxt;
        @BindView(R.id.user_image_vw)
        SimpleDraweeView userImageVw;
        @BindView(R.id.booking_view)
        LinearLayout bookingView;

        public ServiceViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            timeRemainingTxt.setVisibility(View.VISIBLE);
        }

        @OnClick({R.id.user_image_vw, R.id.booking_view})
        public void onLCick(View v) {
            switch (v.getId()) {
                case R.id.user_image_vw:
                    onItemClickListener.onItemClick(v, getAdapterPosition(), GlobalValues.ClickOperations.USER_PROFILE, list.get(getAdapterPosition()));
                    break;
                case R.id.booking_view:
                    onItemClickListener.onItemClick(v, getAdapterPosition(), GlobalValues.ClickOperations.CALLOUT_REQUEST, list.get(getAdapterPosition()));
                    break;
            }
        }

        public void setData(TimeSlotsModel slotData) {
            // User Detail
        }

        private void toggleRefreshing(boolean b) {
        }


    }
}