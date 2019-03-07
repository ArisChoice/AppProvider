package com.app.barber.ui.adapters;

import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;


import com.app.barber.R;
import com.app.barber.models.TimeSlotsModel;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.views.CustomTextView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


public class TimeSlotsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TimeSlotsModel> slotsList;
    OnItemClickListener listener;
    boolean b;//IsworkingHours

    public TimeSlotsAdapter(List<TimeSlotsModel> feedsList, boolean b, OnItemClickListener listener) {
        this.slotsList = feedsList;
        this.listener = listener;
        this.b = b;
    }

    @Override
    public SlotsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_new_time_slots_adapter, parent, false);
        return new SlotsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((SlotsViewHolder) holder).dayNaame.setText(slotsList.get(position).getDay());
        if (slotsList.get(position).getOpeningHours() != null && !slotsList.get(position).getOpeningHours().equals("")) {
            ((SlotsViewHolder) holder).startHoursTxt.setText(slotsList.get(position).getOpeningHours());
            ((SlotsViewHolder) holder).endHoursTxt.setText(slotsList.get(position).getClosingHours());
            ((SlotsViewHolder) holder).layoutBreakSlots.setVisibility(View.VISIBLE);
            ((SlotsViewHolder) holder).txtClosed.setVisibility(View.GONE);
            ((SlotsViewHolder) holder).onOffbtn.setChecked(true);
            if (slotsList.get(position).getBreakHours() != null && slotsList.get(position).getBreakHours().size() > 0) {
                ((SlotsViewHolder) holder).breakHoursTxt.setText(getMultiLineText(slotsList.get(position).getBreakHours()));
            }
            if (slotsList.get(position).isClosed()) {
                ((SlotsViewHolder) holder).layoutBreakSlots.setVisibility(View.GONE);
                ((SlotsViewHolder) holder).txtClosed.setVisibility(View.VISIBLE);
                ((SlotsViewHolder) holder).onOffbtn.setChecked(false);
            }
        } else {
            ((SlotsViewHolder) holder).layoutBreakSlots.setVisibility(View.GONE);
            ((SlotsViewHolder) holder).txtClosed.setVisibility(View.VISIBLE);
            ((SlotsViewHolder) holder).onOffbtn.setChecked(false);
        }
    }

    private String getMultiLineText(List<TimeSlotsModel.BreakHoursList> breakHours) {
        StringBuilder builder = new StringBuilder();
        if (breakHours != null && breakHours.size() > 0)
            for (int i = 0; i < breakHours.size(); i++) {
                builder.append(breakHours.get(i).getBreakStartHours() + " to "
                        + breakHours.get(i).getBreakEndHours() + '\n');
            }
        else {
            builder.append("00:00" + " to "
                    + "00:00");
        }
        return builder.toString();
    }

    @Override
    public int getItemCount() {
        return slotsList.size();
    }

    public void updateAll(List<TimeSlotsModel> posts) {
        this.slotsList.clear();
        this.slotsList.addAll(posts);
        notifyDataSetChanged();
    }

    public void addItem(TimeSlotsModel posts) {
        this.slotsList.add(0, posts);
        notifyDataSetChanged();
    }

    public void updateLikeData(int selectedPosition, String status, String postId) {

    }

    public void updatePosition(TimeSlotsModel timeModel, int positio) {
        slotsList.set(positio, timeModel);
        notifyDataSetChanged();
    }

    public String getItemName(int positio) {
        return slotsList.get(positio).getDay();
    }

    public List<TimeSlotsModel> getList() {
        return slotsList;
    }

    public void updateItem(TimeSlotsModel t) {
        for (int i = 0; i < slotsList.size(); i++) {
            if (slotsList.get(i).getDay().equalsIgnoreCase(t.getDay())) {
                slotsList.set(i, t);
            }
        }
        notifyDataSetChanged();
    }


    public class SlotsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.on_off_btn)
        Switch onOffbtn;

        @BindView(R.id.day_name)
        CustomTextView dayNaame;
        @BindView(R.id.start_hours_txt)
        CustomTextView startHoursTxt;
        @BindView(R.id.end_hours_txt)
        CustomTextView endHoursTxt;
        @BindView(R.id.txt_closed)
        CustomTextView txtClosed;
        @BindView(R.id.break_hours_txt)
        CustomTextView breakHoursTxt;
        @BindView(R.id.break_hours_holder)
        LinearLayout breakHoursHolder;

        @BindView(R.id.layout_time_slots)
        LinearLayout layoutBreakSlots;


        public SlotsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
           /* if (b) {
                breakHoursHolder.setVisibility(View.VISIBLE);
            } else breakHoursHolder.setVisibility(View.GONE);*/

        }

        @OnClick({R.id.start_hours_txt, R.id.end_hours_txt, R.id.day_name, R.id.on_off_btn})
        public void onLCick(View v) {
            switch (v.getId()) {
                case R.id.end_hours_txt:
                case R.id.start_hours_txt:
                    listener.onItemClick(v, getAdapterPosition(), GlobalValues.ClickOperations.ADD_TIME_CLICK, slotsList.get(getAdapterPosition()));
                    break;
                case R.id.on_off_btn:
                    if (onOffbtn.isChecked()) {
                        layoutBreakSlots.setVisibility(View.VISIBLE);
                        txtClosed.setVisibility(View.GONE);
                    } else {
                        layoutBreakSlots.setVisibility(View.GONE);
                        txtClosed.setVisibility(View.VISIBLE);
                        listener.onItemClick(v, getAdapterPosition(), GlobalValues.ClickOperations.REMOVE, slotsList.get(getAdapterPosition()));
                    }
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