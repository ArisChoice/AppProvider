package com.app.barber.ui.adapters;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.barber.R;
import com.app.barber.models.DurationModel;
import com.app.barber.models.TimeSlotsModel;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.views.CustomTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class BookingTimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DurationModel> durationModelList;
    OnItemClickListener listener;
    Activity specialiseActivity;
    String day;

    public BookingTimeAdapter(Activity specialiseActivity, List<DurationModel> feedsList, OnItemClickListener listener) {
        this.durationModelList = feedsList;
        this.listener = listener;
        this.specialiseActivity = specialiseActivity;

    }

    @Override
    public SlotsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_time_before_adapter, parent, false);
        return new SlotsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DurationModel positionData = durationModelList.get(position);
        ((SlotsViewHolder) holder).timeText.setText(positionData.getTime());
        if (positionData.isSelected()) {
            ((SlotsViewHolder) holder).timeText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.check, 0);
            ((SlotsViewHolder) holder).timeText.setBackgroundResource(R.drawable.rectangle_white_border);
        } else {
            ((SlotsViewHolder) holder).timeText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
            ((SlotsViewHolder) holder).timeText.setBackgroundResource(R.drawable.rectangle_grey_border);
        }

    }

    @Override
    public int getItemCount() {
        return durationModelList.size();
    }

    public void updateAll(List<DurationModel> posts) {
        this.durationModelList.clear();
        this.durationModelList.addAll(posts);
        notifyDataSetChanged();
    }

    public void addItem(DurationModel posts) {
        this.durationModelList.add(0, posts);
        notifyDataSetChanged();
    }

    public void updateLikeData(int selectedPosition, String status, String postId) {


    }

    public void updatePosition(TimeSlotsModel.BreakHoursList o) {

    }

    public List<DurationModel> getItems() {

        return durationModelList;
    }

    public DurationModel getSelected() {
        for (int i = 0; i < durationModelList.size(); i++) {
            if (durationModelList.get(i).isSelected())
                return durationModelList.get(i);
        }
        return null;
    }

    public void setSelected(int time) {
        for (int i = 0; i < durationModelList.size(); i++) {
            if (time > 30) {
                if (time / 60 == Integer.parseInt(durationModelList.get(i).getTime().split("\\s+")[0])) {
                    durationModelList.get(i).setSelected(true);
                } else {
                    durationModelList.get(i).setSelected(false);
                }
            } else {
                if (time == Integer.parseInt(durationModelList.get(i).getTime().split("\\s+")[0])) {
                    durationModelList.get(i).setSelected(true);
                } else {
                    durationModelList.get(i).setSelected(false);
                }
            }

        }
        notifyDataSetChanged();
    }


    public class SlotsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.time_text)
        CustomTextView timeText;


        public SlotsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.time_text})
        public void onLCick(View v) {

            switch (v.getId()) {
                case R.id.time_text:
                    refreshSelection(getAdapterPosition());

                    new Handler().post(() -> listener.onItemClick(v, getAdapterPosition(), GlobalValues.ClickOperations.DETAILS, null));
                    break;
            }
            notifyDataSetChanged();
        }
    }

    private void refreshSelection(int adapterPosition) {
        for (int i = 0; i < durationModelList.size(); i++) {
            if (i == adapterPosition) {
                durationModelList.get(i).setSelected(true);
            } else durationModelList.get(i).setSelected(false);
        }
        notifyDataSetChanged();
    }


    public void setData(TimeSlotsModel slotData) {
        // User Detail
    }

    private void toggleRefreshing(boolean b) {
    }


}
