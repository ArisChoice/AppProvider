package com.app.barber.ui.adapters;

import android.app.Activity;
import android.content.res.TypedArray;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.barber.R;
import com.app.barber.models.TimeSlotsModel;
import com.app.barber.models.response.NotificationResponseModel;
import com.app.barber.util.CustomDate;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.views.CustomTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.NOtificationViewHolder> {

    Activity activity;
    ArrayList<NotificationResponseModel.ResponseBean> nList;
    OnItemClickListener listener;
    TypedArray icons;

    public NotificationListAdapter(Activity activity, ArrayList<NotificationResponseModel.ResponseBean> nList, OnItemClickListener listener) {
        this.activity = activity;
        this.listener = listener;
        this.nList = nList;
    }

    @Override
    public NOtificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_notification_adapter, parent, false);
        return new NOtificationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NOtificationViewHolder holder, int position) {
        NotificationResponseModel.ResponseBean positionData = nList.get(position);
        holder.notificationText.setText(positionData.getMessage());
        switch (positionData.getNotificationType()) {
            case GlobalValues.NotificationType.AppointmentConfirmation:
                holder.notificationImage.setImageResource(R.drawable.scissors);
                holder.notificationImage.setBackgroundResource(R.drawable.circular_notifiaction_blue_background);
                break;
            case GlobalValues.NotificationType.CallOutConfirmation:
                holder.notificationImage.setImageResource(R.drawable.callout_);
                holder.notificationImage.setBackgroundResource(R.drawable.circular_notifiaction_pink_background);
                break;
            case GlobalValues.NotificationType.UserRating:
                holder.notificationImage.setImageResource(R.drawable.star_copy);
                holder.notificationImage.setBackgroundResource(R.drawable.circular_notifiaction_yellow_background);
                break;
        }
        holder.notificationTime.setText(CustomDate.getDateFromUTCTimestamp(activity, Long.parseLong(positionData.getDateProcessed()), "MMM dd hh :ss a"));
       if(positionData.isSeen())
       {
           holder.notificationStatsu.setVisibility(View.GONE);
       }else  holder.notificationStatsu.setVisibility(View.VISIBLE);


        /*if (positionData.isToday()) {
            holder.notificationTime.setText(CustomDate.getCurrentFormat(activity, positionData.getDateProcessed(), "hh:mm:ss", "hh"));
        } else
            holder.notificationTime.setText(CustomDate.getCurrentFormat(activity, positionData.getDateProcessed(), "MM/dd/yyyy", "dd,MM"));*/

    }

    @Override
    public int getItemCount() {
        return nList.size();
    }

    public void updateAll(List<NotificationResponseModel.ResponseBean> posts) {
        this.nList.clear();
        this.nList.addAll(posts);
        notifyDataSetChanged();
    }

    public void addItem(NotificationResponseModel.ResponseBean posts) {
//        this.slotsList.add(0, posts);
//        notifyDataSetChanged();
    }

    public NotificationResponseModel.ResponseBean getpositionData(int position) {
        return nList.get(position);
    }


    public class NOtificationViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.notification_img)
        ImageView notificationImage;

        @BindView(R.id.notification_text)
        CustomTextView notificationText;
        @BindView(R.id.notification_time)
        CustomTextView notificationTime;
        @BindView(R.id.notification_status)
        CustomTextView notificationStatsu;

        public NOtificationViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

        @OnClick({R.id.notification_text})
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.notification_text:
                    listener.onItemClick(v, getAdapterPosition(), GlobalValues.ClickOperations.MORE_OPRION_CLICK, null);
                    break;

            }
        }


    }
}