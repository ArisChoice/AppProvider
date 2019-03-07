package com.app.barber.ui.postauth.activities.basic.basic_adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.barber.R;
import com.app.barber.models.TimeSlotsModel;
import com.app.barber.models.response.ZoneDistrictResponseModel;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.views.CustomTextView;

import java.util.List;
import java.util.logging.Handler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ZoneDistrictsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ZoneDistrictResponseModel.ListBean> specList;
    OnItemClickListener listener;
    Activity specialiseActivity;

    public ZoneDistrictsAdapter(Activity specialiseActivity, List<ZoneDistrictResponseModel.ListBean> feedsList, OnItemClickListener listener) {
        this.specList = feedsList;
        this.listener = listener;
        this.specialiseActivity = specialiseActivity;
    }

    @Override
    public SlotsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_zone_districts_adapter, parent, false);
        return new SlotsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ZoneDistrictResponseModel.ListBean positionData = specList.get(position);
        ((SlotsViewHolder) holder).districtText.setText(positionData.getMItem2());
        if (positionData.isM_Item3()) {
            ((SlotsViewHolder) holder).districtText.setBackgroundResource(R.drawable.rectangle_white_border);
            ((SlotsViewHolder) holder).districtText.setTextColor(specialiseActivity.getResources().getColor(R.color.color_sky_blue));
            ((SlotsViewHolder) holder).districtText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.check, 0);
        } else {
            ((SlotsViewHolder) holder).districtText.setBackgroundResource(R.drawable.rectangle_grey_border);
            ((SlotsViewHolder) holder).districtText.setTextColor(specialiseActivity.getResources().getColor(R.color.color_black));
            ((SlotsViewHolder) holder).districtText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
        }

    }

    @Override
    public int getItemCount() {
        return specList.size();
    }

    public void updateAll(List<ZoneDistrictResponseModel.ListBean> posts) {
        this.specList.clear();
        this.specList.addAll(posts);
        notifyDataSetChanged();
    }

    public void addItem(ZoneDistrictResponseModel.ListBean posts) {
        this.specList.add(0, posts);
        notifyDataSetChanged();
    }

    public void updateLikeData(int selectedPosition, String status, String postId) {


    }

   /* public void setSelected(int selected) {
        for (int i = 0; i < specList.size(); i++) {
            if (selected == i) {
                specList.get(i).setSelected(true);
            } else specList.get(i).setSelected(false);
        }
        notifyDataSetChanged();
    }*/

    public String getselected() {
        String selectedType = null;
        try {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < specList.size(); i++) {
                if (specList.get(i).isSelected()) {
                    builder.append(specList.get(i).getMItem1() + ",");
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

    public ZoneDistrictResponseModel.ListBean getItem(int position) {
        return specList.get(position);
    }


    public class SlotsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.district_text_text)
        CustomTextView districtText;


        public SlotsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

        @OnClick({R.id.district_text_text})
        public void onLCick(View v) {
            if (specList.get(getAdapterPosition()).isM_Item3()) {
                specList.get(getAdapterPosition()).setM_Item3(false);
            } else {
                specList.get(getAdapterPosition()).setM_Item3(true);
            }

            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    listener.onItemClick(v, getAdapterPosition(), GlobalValues.ClickOperations.DETAILS, specList.get(getAdapterPosition()));
                    notifyDataSetChanged();
                }
            }, GlobalValues.TIME_DURATIONS.SMALL);
        }
    }


    public void setData(TimeSlotsModel slotData) {
        // User Detail
    }

    private void toggleRefreshing(boolean b) {
    }


}
