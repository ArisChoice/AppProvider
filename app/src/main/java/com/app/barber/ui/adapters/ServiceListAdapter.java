package com.app.barber.ui.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.app.barber.R;
import com.app.barber.models.TimeSlotsModel;
import com.app.barber.models.response.ServiceListResponseModel;
import com.app.barber.ui.postauth.activities.basic.ServiceListActivity;
import com.app.barber.ui.postauth.activities.client.SelectServiceActivity;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.views.CustomTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ServiceListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity serviceListActivity;
    ArrayList<ServiceListResponseModel.ResponseBean> list;
    OnItemClickListener onItemClickListener;
    String fromView;

    public ServiceListAdapter(Activity serviceListActivity, String fromView, ArrayList<ServiceListResponseModel.ResponseBean> list, OnItemClickListener onItemClickListener) {
        this.serviceListActivity = serviceListActivity;
        this.list = list;
        this.onItemClickListener = onItemClickListener;
        this.fromView = fromView;
    }

    @Override
    public ServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_service_adapter, parent, false);
        return new ServiceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ServiceListResponseModel.ResponseBean positionData = list.get(position);
        if (holder instanceof ServiceViewHolder) {
            ((ServiceViewHolder) holder).serviceName.setText(positionData.getServiceName());
            ((ServiceViewHolder) holder).serviceCost.setText(GlobalValues.Currency.POUNDS + positionData.getPrice());
            ((ServiceViewHolder) holder).serviceTime.setText(positionData.getDuration() + " min");
            if (positionData.isSelected() &&
                    fromView.equals(SelectServiceActivity.class.getSimpleName())) {
                ((ServiceViewHolder) holder).serviceChecked.setVisibility(View.VISIBLE);
            } else ((ServiceViewHolder) holder).serviceChecked.setVisibility(View.GONE);

            if (positionData.getType() == 1) {
                ((ServiceViewHolder) holder).serviceFor.setText(R.string.str_walk_in);
                ((ServiceViewHolder) holder).serviceFor.setBackgroundResource(R.drawable.rectangle_dark_blue_drawable);
            } else {
                ((ServiceViewHolder) holder).serviceFor.setText(R.string.str_callout_);
                ((ServiceViewHolder) holder).serviceFor.setBackgroundResource(R.drawable.rectangle_pink_border);
            }

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateAll(List<ServiceListResponseModel.ResponseBean> posts) {
        this.list.clear();
        this.list.addAll(posts);
        notifyDataSetChanged();
    }

    public void addItem(ServiceListResponseModel.ResponseBean posts) {
        this.list.add(0, posts);
        notifyDataSetChanged();
    }

    public void updateLikeData(int selectedPosition, String status, String postId) {

    }

    public void remove(int positio) {
        list.remove(positio);
        notifyDataSetChanged();
    }

    public ArrayList<ServiceListResponseModel.ResponseBean> getSelected() {
        ArrayList<ServiceListResponseModel.ResponseBean> selectedServices = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isSelected()) {
                selectedServices.add(list.get(i));
            }
        }
        return selectedServices;
    }


    public class ServiceViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.service_name)
        CustomTextView serviceName;
        @BindView(R.id.service_time)
        CustomTextView serviceTime;
        @BindView(R.id.service_cost)
        CustomTextView serviceCost;
        @BindView(R.id.service_checked)
        ImageView serviceChecked;
        @BindView(R.id.service_for)
        CustomTextView serviceFor;


        public ServiceViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

        @OnClick({R.id.service_name, R.id.service_time})
        public void onLCick(View v) {
            if (fromView.equals(SelectServiceActivity.class.getSimpleName())) {
                if (list.get(getAdapterPosition()).isSelected()) {
                    list.get(getAdapterPosition()).setSelected(false);
                } else list.get(getAdapterPosition()).setSelected(true);
                notifyDataSetChanged();
                onItemClickListener.onItemClick(v, getAdapterPosition(), GlobalValues.ClickOperations.SERVICE_DETAIL, null);
            } else
                switch (v.getId()) {
                    case R.id.service_time:
                    case R.id.service_name:
                        onItemClickListener.onItemClick(v, getAdapterPosition(), GlobalValues.ClickOperations.SERVICE_DETAIL, list.get(getAdapterPosition()));
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