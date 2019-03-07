package com.app.barber.ui.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.barber.R;
import com.app.barber.models.DurationModel;
import com.app.barber.models.SpecialisationModel;
import com.app.barber.models.TimeSlotsModel;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.views.CustomTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DurartionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DurationModel> specList;
    OnItemClickListener listener;
    Activity specialiseActivity;

    public DurartionAdapter(Activity specialiseActivity, List<DurationModel> feedsList, OnItemClickListener listener) {
        this.specList = feedsList;
        this.listener = listener;
        this.specialiseActivity = specialiseActivity;
    }

    @Override
    public SlotsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_duration_adapter, parent, false);
        return new SlotsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DurationModel positionData = specList.get(position);
        ((SlotsViewHolder) holder).durationText.setText(positionData.getTime());
        if (positionData.isSelected()) {
            ((SlotsViewHolder) holder).durationText.setBackgroundResource(R.drawable.rectangle_blue_drawable);
            ((SlotsViewHolder) holder).durationText.setTextColor(specialiseActivity.getResources().getColor(R.color.color_white));
        } else {
            ((SlotsViewHolder) holder).durationText.setBackgroundResource(R.drawable.rectangle_grey_border);
            ((SlotsViewHolder) holder).durationText.setTextColor(specialiseActivity.getResources().getColor(R.color.color_black));
        }

    }

    @Override
    public int getItemCount() {
        return specList.size();
    }

    public void updateAll(List<DurationModel> posts) {
        this.specList.clear();
        this.specList.addAll(posts);
        notifyDataSetChanged();
    }

    public void addItem(DurationModel posts) {
        this.specList.add(0, posts);
        notifyDataSetChanged();
    }

    public void updateLikeData(int selectedPosition, String status, String postId) {


    }

    public String getSelectedSlot() {
        for (int i = 0; i < specList.size(); i++) {
            if (specList.get(i).isSelected()) {
                return specList.get(i).getTime();
            }
        }
        return "20min";
    }

    public void setSelected(String duration) {
        for (int i = 0; i < specList.size(); i++) {
            if (specList.get(i).getTime().contains("" + duration)) {
                specList.get(i).setSelected(true);
            }
        }
        notifyDataSetChanged();
    }

    public boolean isSelected() {
        boolean isSelected = false;
        for (int i = 0; i < specList.size(); i++) {
            if (specList.get(i).isSelected()) {
                isSelected = true;
            }
        }
        return isSelected;
    }


    public class SlotsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.duration_text)
        CustomTextView durationText;


        public SlotsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

        @OnClick({R.id.duration_text})
        public void onLCick(View v) {
            if (specList.get(getAdapterPosition()).isSelected()) {
                specList.get(getAdapterPosition()).setSelected(false);
            } else {
                specList.get(getAdapterPosition()).setSelected(true);
                resetOthers(getAdapterPosition());
            }
            notifyDataSetChanged();
//            listener.onItemClick(v, getAdapterPosition(), GlobalValues.ClickOperations.ADD_TIME_CLICK, specList.get(getAdapterPosition()));

        }
    }

    private void resetOthers(int adapterPosition) {
        for (int i = 0; i < specList.size(); i++) {
            if (i != adapterPosition)
                specList.get(i).setSelected(false);
        }

    }

    public void setData(TimeSlotsModel slotData) {
        // User Detail
    }

    private void toggleRefreshing(boolean b) {
    }


}
