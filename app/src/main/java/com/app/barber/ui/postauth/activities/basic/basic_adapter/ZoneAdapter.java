package com.app.barber.ui.postauth.activities.basic.basic_adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.barber.R;
import com.app.barber.models.DurationModel;
import com.app.barber.models.TimeSlotsModel;
import com.app.barber.models.ZoneModel;
import com.app.barber.models.response.ZoneResponseModel;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.views.CustomTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ZoneAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ZoneResponseModel.ListBean> specList;
    OnItemClickListener listener;
    Activity specialiseActivity;

    public ZoneAdapter(Activity specialiseActivity, List<ZoneResponseModel.ListBean> feedsList, OnItemClickListener listener) {
        this.specList = feedsList;
        this.listener = listener;
        this.specialiseActivity = specialiseActivity;
    }

    @Override
    public SlotsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_zone_adapter, parent, false);
        return new SlotsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ZoneResponseModel.ListBean positionData = specList.get(position);
        ((SlotsViewHolder) holder).zoneText.setText(positionData.getMItem2());
        if (positionData.isSelected()) {
            ((SlotsViewHolder) holder).zoneText.setBackgroundResource(R.drawable.rectangle_blue_drawable);
            ((SlotsViewHolder) holder).zoneText.setTextColor(specialiseActivity.getResources().getColor(R.color.color_white));
        } else {
            ((SlotsViewHolder) holder).zoneText.setBackgroundResource(R.drawable.rectangle_grey_border);
            ((SlotsViewHolder) holder).zoneText.setTextColor(specialiseActivity.getResources().getColor(R.color.color_black));
        }

    }

    @Override
    public int getItemCount() {
        return specList.size();
    }

    public void updateAll(List<ZoneResponseModel.ListBean> posts) {
        this.specList.clear();
        this.specList.addAll(posts);
        notifyDataSetChanged();
    }

    public void addItem(ZoneResponseModel.ListBean posts) {
        this.specList.add(0, posts);
        notifyDataSetChanged();
    }

    public void updateLikeData(int selectedPosition, String status, String postId) {


    }

    public void setSelected(int selected) {
        for (int i = 0; i < specList.size(); i++) {
            if (selected == i) {
                specList.get(i).setSelected(true);
            } else specList.get(i).setSelected(false);
        }
        notifyDataSetChanged();
    }

    public class SlotsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.zone_text)
        CustomTextView zoneText;


        public SlotsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

        @OnClick({R.id.zone_text})
        public void onLCick(View v) {
            listener.onItemClick(v, getAdapterPosition(), GlobalValues.ClickOperations.DETAILS, specList.get(getAdapterPosition()));

        }
    }


    public void setData(TimeSlotsModel slotData) {
        // User Detail
    }

    private void toggleRefreshing(boolean b) {
    }


}
