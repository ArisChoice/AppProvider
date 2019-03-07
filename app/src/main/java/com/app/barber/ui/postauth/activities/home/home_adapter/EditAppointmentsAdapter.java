package com.app.barber.ui.postauth.activities.home.home_adapter;

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


public class EditAppointmentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity serviceListActivity;
    ArrayList<AppointmentsResponseModel.ResponseBean.BookingListBean> list;
    OnItemClickListener onItemClickListener;
    String nFrom;

    public EditAppointmentsAdapter(FragmentActivity serviceListActivity, String nFrom, ArrayList<AppointmentsResponseModel.ResponseBean.BookingListBean> list, OnItemClickListener onItemClickListener) {
        this.serviceListActivity = serviceListActivity;
        this.list = list;
        this.onItemClickListener = onItemClickListener;
        this.nFrom = nFrom;
    }

    @Override
    public AppointmentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_edit_appointments_adapter, parent, false);
        return new AppointmentsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AppointmentsResponseModel.ResponseBean.BookingListBean positionData = list.get(position);
        if (holder instanceof AppointmentsViewHolder) {
            ((AppointmentsViewHolder) holder).userName.setText(positionData.getName());
            ((AppointmentsViewHolder) holder).userImageVw.setImageURI(CommonUtils.getValidUrl(positionData.getUserImage()));
            ((AppointmentsViewHolder) holder).userService.setText(positionData.getServiceNames());
            ((AppointmentsViewHolder) holder).currenTimeSlotText.setText(positionData.getTimingSlot());
            ((AppointmentsViewHolder) holder).requestedTimeSlotText.setText(positionData.getEditData().split("@")[1]);
            ((AppointmentsViewHolder) holder).payableApountTxt.setText(GlobalValues.Currency.POUNDS + positionData.getAmount());
            ((AppointmentsViewHolder) holder).currentDateTxt.setText(CustomDate.formatThis(GlobalValues.DateFormats.DEFAULT_FORMAT_DATE, positionData.getDateString(), "dd MMM yyyy"));
            try {
                ((AppointmentsViewHolder) holder).requestedDateTxt.setText(CustomDate.formatThis(GlobalValues.DateFormats.DEFAULT_FORMAT_DATE, positionData.getEditData().split("@")[0], "dd MMM yyyy"));
            } catch (Exception e) {

            }
            /* if (positionData.getBookingType() == GlobalValues.BookingTypes.CALLOUT) {
                    ((AppointmentsViewHolder) holder).timeRemainingTxt.setVisibility(View.VISIBLE);
                    ((AppointmentsViewHolder) holder).timeRemainingTxt.setText(serviceListActivity.getString(R.string.str_callout_));
                    ((AppointmentsViewHolder) holder).timeRemainingTxt.setBackgroundResource(R.drawable.rectangle_pink_border);
                } else {
                    ((AppointmentsViewHolder) holder).timeRemainingTxt.setVisibility(View.GONE);
                }*/

            if (positionData.getPaymentMode() != null && !positionData.getPaymentMode().equals("")) {
                ((AppointmentsViewHolder) holder).paymentType.setVisibility(View.VISIBLE);
                if (positionData.getPaymentMode().equals(GlobalValues.PaymentModes.CARD)) {
                    ((AppointmentsViewHolder) holder).paymentType.setText(serviceListActivity.getString(R.string.str_paid));
                    ((AppointmentsViewHolder) holder).paymentType.setBackgroundResource(R.drawable.rectangle_green_border);
                } else if (positionData.getPaymentMode().equals(GlobalValues.PaymentModes.CASH)) {
                    ((AppointmentsViewHolder) holder).paymentType.setText(serviceListActivity.getString(R.string.str_cash));
                    ((AppointmentsViewHolder) holder).paymentType.setBackgroundResource(R.drawable.rectangle_green_border);
                } else {
                    ((AppointmentsViewHolder) holder).paymentType.setVisibility(View.GONE);
                }
            } else {
                ((AppointmentsViewHolder) holder).paymentType.setVisibility(View.GONE);
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

    public void clearAll() {
        list.clear();
        notifyDataSetChanged();
    }

    public void updatePositionData(int id, boolean status) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == id) {
                list.get(i).setCompleted(status);
            }
        }
        notifyDataSetChanged();
    }


    public class AppointmentsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.user_name)
        CustomTextView userName;
        @BindView(R.id.user_service)
        CustomTextView userService;
        @BindView(R.id.current_time_slot_txt)
        CustomTextView currenTimeSlotText;
        @BindView(R.id.requested_time_slot_txt)
        CustomTextView requestedTimeSlotText;

        @BindView(R.id.payable_apount_txt)
        CustomTextView payableApountTxt;

        @BindView(R.id.current_date_txt)
        CustomTextView currentDateTxt;

        @BindView(R.id.requested_date_txt)
        CustomTextView requestedDateTxt;

        @BindView(R.id.user_image_vw)
        SimpleDraweeView userImageVw;

        @BindView(R.id.booking_view)
        LinearLayout bookingView;

        @BindView(R.id.payment_type)
        CustomTextView paymentType;

        public AppointmentsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.booking_view, R.id.user_image_vw, R.id.user_service})
        public void onLCick(View v) {
            switch (v.getId()) {
                case R.id.booking_view:
                case R.id.user_image_vw:
                case R.id.user_service:
                    onItemClickListener.onItemClick(v, getAdapterPosition(), GlobalValues.ClickOperations.CHECK_APPOINTMENT, list.get(getAdapterPosition()));
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