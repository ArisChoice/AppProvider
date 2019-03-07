package com.app.barber.ui.postauth.activities.home.home_adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.app.barber.R;
import com.app.barber.models.TimeSlotsModel;
import com.app.barber.models.response.BlockHoursResponse;
import com.app.barber.models.response.OverviewResponseModel;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.views.CustomTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class BlockHoursAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<BlockHoursResponse.BlockHoursBean> durationModelList;
    OnItemClickListener listener;
    Activity specialiseActivity;
    String day;

    public BlockHoursAdapter(Activity specialiseActivity, List<BlockHoursResponse.BlockHoursBean> feedsList, OnItemClickListener listener) {
        this.durationModelList = feedsList;
        this.listener = listener;
        this.specialiseActivity = specialiseActivity;

    }

    @Override
    public SlotsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_block_hours_adapter, parent, false);
        return new SlotsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SlotsViewHolder) {
            ((SlotsViewHolder) holder).startTime.setText(durationModelList.get(position).getStartTime());
            ((SlotsViewHolder) holder).endTime.setText(durationModelList.get(position).getEndTime());

        }

    }

    @Override
    public int getItemCount() {
        return durationModelList.size();
    }

    public void updateAll(List<BlockHoursResponse.BlockHoursBean> posts) {
        this.durationModelList.clear();
        this.durationModelList.addAll(posts);
        notifyDataSetChanged();
    }

    public void addItem(BlockHoursResponse.BlockHoursBean posts) {
        this.durationModelList.add(0, posts);
        notifyDataSetChanged();
    }

    public void updateLikeData(int selectedPosition, String status, String postId) {


    }

    public void clearAll() {
        this.durationModelList.clear();
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        this.durationModelList.remove(position);
        notifyDataSetChanged();
    }


//    public List<OverviewResponseModel.DataBean.TimeListBean.DayListBean> getItems() {

//        return durationModel
//    }


    public class SlotsViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.start_hours_txt)
        CustomTextView startTime;

        @BindView(R.id.end_hours_txt)
        CustomTextView endTime;
        @BindView(R.id.delete_block_hours)
        ImageView deleteBreakHour;


        public SlotsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.delete_block_hours})
        public void onLCick(View v) {

            switch (v.getId()) {
                case R.id.delete_block_hours:
                    listener.onItemClick(v, getAdapterPosition(), GlobalValues.ClickOperations.REMOVE, durationModelList.get(getAdapterPosition()));
                    break;
            }
            notifyDataSetChanged();
        }
    }


}
