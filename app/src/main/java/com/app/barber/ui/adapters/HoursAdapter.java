package com.app.barber.ui.adapters;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.barber.R;
import com.app.barber.models.TimeSlotsModel;
import com.app.barber.util.CommonUtils;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.views.CustomTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HoursAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TimeSlotsModel.BreakHoursList> specList;
    OnItemClickListener listener;
    Activity specialiseActivity;
    String day;

    public HoursAdapter(Activity specialiseActivity, List<TimeSlotsModel.BreakHoursList> feedsList, String day, OnItemClickListener listener) {
        this.specList = feedsList;
        this.listener = listener;
        this.specialiseActivity = specialiseActivity;
        this.day = day;
    }

    @Override
    public SlotsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_hours_adapter, parent, false);
        return new SlotsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TimeSlotsModel.BreakHoursList positionData = specList.get(position);
        ((SlotsViewHolder) holder).startHoursTxt.setText(positionData.getBreakStartHours());
        ((SlotsViewHolder) holder).endHoursTxt.setText(positionData.getBreakEndHours());





        /*if (positionData.getId() != 0)//if block hours yet not save don server
            ((SlotsViewHolder) holder).removeBreakhours.setVisibility(View.VISIBLE);
        else ((SlotsViewHolder) holder).removeBreakhours.setVisibility(View.GONE);*/

    }

    @Override
    public int getItemCount() {
        return specList.size();
    }

    public void updateAll(List<TimeSlotsModel.BreakHoursList> posts) {
        this.specList.clear();
        this.specList.addAll(posts);
        notifyDataSetChanged();
    }

    public void addItem(TimeSlotsModel.BreakHoursList posts) {
        this.specList.add(0, posts);
        notifyDataSetChanged();
    }

    public void updateLikeData(int selectedPosition, String status, String postId) {


    }

    public void updatePosition(TimeSlotsModel.BreakHoursList o) {

    }

    public void removePosition(int posit) {
        this.specList.remove(posit);
        notifyDataSetChanged();
    }

    public List<TimeSlotsModel.BreakHoursList> getItems() {

        return specList;
    }


    public class SlotsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.start_hours_txt)
        CustomTextView startHoursTxt;
        @BindView(R.id.end_hours_txt)
        CustomTextView endHoursTxt;
        @BindView(R.id.remove_btn)
        ImageView removeBreakhours;


        public SlotsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            removeBreakhours.setVisibility(View.VISIBLE);
        }

        @OnClick({R.id.start_hours_txt, R.id.end_hours_txt, R.id.remove_btn})
        public void onLCick(View v) {

            switch (v.getId()) {
                case R.id.start_hours_txt:
                    new CommonUtils().showDialog(1111, (CustomTextView) v, specialiseActivity, (OnItemClickListener) (view, position, type, o1) -> {
                        specList.get(getAdapterPosition()).setDay(day);
                        specList.get(getAdapterPosition()).setBreakStartHours(((TextView) view.findViewById(R.id.start_hours_txt)).getText().toString());
                    });
                    break;
                case R.id.end_hours_txt:
                    new CommonUtils().showDialog(1111, (CustomTextView) v, specialiseActivity, (OnItemClickListener) (view, position, type, o1) -> {
                        specList.get(getAdapterPosition()).setDay(day);
                        specList.get(getAdapterPosition()).setBreakEndHours(((TextView) view.findViewById(R.id.end_hours_txt)).getText().toString());
                    });
                    break;
                case R.id.remove_btn:
                    listener.onItemClick(v, getAdapterPosition(), GlobalValues.ClickOperations.REMOVE, specList.get(getAdapterPosition()));
                    break;
            }
            notifyDataSetChanged();
        }
    }


    public void setData(TimeSlotsModel slotData) {
        // User Detail
    }

    private void toggleRefreshing(boolean b) {
    }


}
