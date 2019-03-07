package com.app.barber.ui.postauth.activities.client.client_adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.barber.R;
import com.app.barber.models.TimeSlotsModel;
import com.app.barber.models.response.ClientsListResponseModel;
import com.app.barber.models.response.ServiceListResponseModel;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.ImageUtility;
import com.app.barber.util.iface.FastScrollRecyclerViewInterface;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.views.CustomTextView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ChipAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements FastScrollRecyclerViewInterface {

    private List<ServiceListResponseModel.ResponseBean> specList;
    OnItemClickListener listener;
    Activity specialiseActivity;
    HashMap<String, Integer> mapIndex;

    public ChipAdapter(Activity specialiseActivity, List<ServiceListResponseModel.ResponseBean> feedsList,
                       OnItemClickListener listener) {
        this.specList = feedsList;
        this.listener = listener;
        this.specialiseActivity = specialiseActivity;
        this.mapIndex = mapIndex;
    }

    @Override
    public CustomerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_chip_adapter, parent, false);
        return new CustomerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ServiceListResponseModel.ResponseBean positionData = specList.get(position);
        if (holder instanceof CustomerHolder) {
            ((CustomerHolder) holder).serviceName.setText(positionData.getServiceName());
        }
    }

    @Override
    public int getItemCount() {
        return specList.size();
    }

    public void updateAll(List<ServiceListResponseModel.ResponseBean> posts) {
        this.specList.clear();
        this.specList.addAll(posts);
        notifyDataSetChanged();
    }

    public void addItem(ServiceListResponseModel.ResponseBean posts) {
        this.specList.add(0, posts);
        notifyDataSetChanged();
    }

    @Override
    public HashMap<String, Integer> getMapIndex() {
        return this.mapIndex;
    }

    public void remove(int positio) {
        specList.remove(positio);
        notifyDataSetChanged();
    }

    public String getIds() {
        String selectedType = null;
        try {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < specList.size(); i++) {
                if (specList.get(i).isSelected()) {
                    builder.append(specList.get(i).getId() + ",");
                }
            }
            selectedType = builder.toString();
            if (selectedType != null && selectedType.length() > 0 && selectedType.charAt(selectedType.length() - 1) == ',') {
                selectedType = selectedType.substring(0, selectedType.length() - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return selectedType;
    }

    public class CustomerHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.service_name)
        CustomTextView serviceName;
        @BindView(R.id.remove_icon)
        ImageView removeIcon;


        public CustomerHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.remove_icon})
        public void onLCick(View v) {
            listener.onItemClick(v, getAdapterPosition(), GlobalValues.ClickOperations.REMOVE, specList.get(getAdapterPosition()));

        }
    }

    public void setData(TimeSlotsModel slotData) {
        // User Detail
    }

    private void toggleRefreshing(boolean b) {
    }


}
