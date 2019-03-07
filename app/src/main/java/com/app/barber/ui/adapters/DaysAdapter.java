package com.app.barber.ui.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.barber.R;
import com.app.barber.models.DurationModel;
import com.app.barber.models.TimeSlotsModel;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.views.CustomTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DaysAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TimeSlotsModel> specList;
    OnItemClickListener listener;
    Activity specialiseActivity;
    boolean isEdit;

    public DaysAdapter(Activity specialiseActivity, boolean isEdit, List<TimeSlotsModel> feedsList, OnItemClickListener listener) {
        this.specList = feedsList;
        this.listener = listener;
        this.specialiseActivity = specialiseActivity;
        this.isEdit = isEdit;
    }

    @Override
    public SlotsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_days_adapter, parent, false);
        return new SlotsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TimeSlotsModel positionData = specList.get(position);
        ((SlotsViewHolder) holder).dayTxt.setText("" + positionData.getDay().charAt(0));
        if (positionData.getCurrentDay() != null)
            ((SlotsViewHolder) holder).dayTxt.setBackgroundResource(R.drawable.circular_blue_background);
        else {
            if (positionData.isSelected()) {
                ((SlotsViewHolder) holder).dayTxt.setBackgroundResource(R.drawable.circular_blue_background);
            } else
                ((SlotsViewHolder) holder).dayTxt.setBackgroundResource(R.drawable.circular_light_blue_background);
        }

    }

    @Override
    public int getItemCount() {
        return specList.size();
    }

    public void updateAll(List<TimeSlotsModel> posts) {
        this.specList.clear();
        this.specList.addAll(posts);
        notifyDataSetChanged();
    }

    public void addItem(TimeSlotsModel posts) {
        this.specList.add(0, posts);
        notifyDataSetChanged();
    }

    public void updateLikeData(int selectedPosition, String status, String postId) {


    }

    /**
     * Get current day of the week.
     */
    public void updatePosition(TimeSlotsModel o) {
        for (int i = 0; i < specList.size(); i++) {
            if (specList.get(i).getDay().toString().equalsIgnoreCase(o.getDay())) {
                specList.get(i).setCurrentDay(o.getDay());
                specList.get(i).setSelected(true);
            } else specList.get(i).setCurrentDay(null);
        }
    }

    /**
     * Get get selected day of the week.
     */
    public String getSelectedDays() {
        String currentday = null;

        StringBuilder days = new StringBuilder();
        try {
            for (int i = 0; i < specList.size(); i++) {
                if (specList.get(i).isSelected())
                    days.append(specList.get(i).getDay() + ",");

                if (specList.get(i).getCurrentDay() != null) {
                    currentday = specList.get(i).getDay();
                }
            }
            if (days != null && days.toString().length() > 0 && days.toString().charAt(days.toString().length() - 1) == ',') {
                return days.toString().substring(0, days.toString().length() - 1);
            }
        } catch (Exception e) {

        }
        return currentday;
    }


    public class SlotsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.day_txt)
        CustomTextView dayTxt;


        public SlotsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

        @OnClick({R.id.day_txt})
        public void onLCick(View v) {
//            if (!isEdit)
                if (specList.get(getAdapterPosition()).getCurrentDay() != null) {
                    specList.get(getAdapterPosition()).setSelected(true);
                } else {
                    if (specList.get(getAdapterPosition()).isSelected()) {
                        specList.get(getAdapterPosition()).setSelected(false);
                    } else
                        specList.get(getAdapterPosition()).setSelected(true);
                }
            notifyDataSetChanged();
//          listener.onItemClick(v, getAdapterPosition(), GlobalValues.ClickOperations.ADD_TIME_CLICK, specList.get(getAdapterPosition()));

        }

    }


    public void setData(TimeSlotsModel slotData) {
        // User Detail
    }

    private void toggleRefreshing(boolean b) {
    }


}
