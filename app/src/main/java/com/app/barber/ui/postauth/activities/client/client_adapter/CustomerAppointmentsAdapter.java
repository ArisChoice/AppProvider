package com.app.barber.ui.postauth.activities.client.client_adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.barber.R;
import com.app.barber.models.TimeSlotsModel;
import com.app.barber.models.response.CustomerBookingResponseModel;
import com.app.barber.models.response.PaymentHistoryResponse;
import com.app.barber.models.response.ServiceListResponseModel;
import com.app.barber.ui.postauth.activities.client.SelectServiceActivity;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.views.CustomTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CustomerAppointmentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity serviceListActivity;
    ArrayList<CustomerBookingResponseModel.DataBean.HistoryListBean> list;
    OnItemClickListener onItemClickListener;
    int fromView;

    public CustomerAppointmentsAdapter(Activity serviceListActivity, ArrayList<CustomerBookingResponseModel.DataBean.HistoryListBean> list, OnItemClickListener onItemClickListener) {
        this.serviceListActivity = serviceListActivity;
        this.list = list;
        this.onItemClickListener = onItemClickListener;
        this.fromView = fromView;
    }

    @Override
    public PaymentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_customer_appointmetns_adapter, parent, false);
        return new PaymentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CustomerBookingResponseModel.DataBean.HistoryListBean positionData = list.get(position);
        if (holder instanceof PaymentViewHolder) {
            ((PaymentViewHolder) holder).serviceAmount.setText(GlobalValues.Currency.POUNDS + " " + positionData.getAmount());
            ((PaymentViewHolder) holder).serviceName.setText(positionData.getServices());
            ((PaymentViewHolder) holder).serviceDate.setText(positionData.getDate() + " " + positionData.getTimeSlot());
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateAll(List<CustomerBookingResponseModel.DataBean.HistoryListBean> posts) {
        this.list.clear();
        this.list.addAll(posts);
        notifyDataSetChanged();
    }

    public void addItem(CustomerBookingResponseModel.DataBean.HistoryListBean posts) {
        this.list.add(0, posts);
        notifyDataSetChanged();
    }

    public void updateLikeData(int selectedPosition, String status, String postId) {

    }

    public void remove(int positio) {
        list.remove(positio);
        notifyDataSetChanged();
    }


    public class PaymentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.service_name)
        CustomTextView serviceName;
        @BindView(R.id.service_date)
        CustomTextView serviceDate;
        @BindView(R.id.service_amount)
        CustomTextView serviceAmount;

        public PaymentViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

        @OnClick({})
        public void onLCick(View v) {

        }

        public void setData(TimeSlotsModel slotData) {
            // User Detail
        }

        private void toggleRefreshing(boolean b) {
        }


    }
}