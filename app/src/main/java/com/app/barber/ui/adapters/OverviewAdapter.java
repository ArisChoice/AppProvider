package com.app.barber.ui.adapters;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.barber.R;
import com.app.barber.models.TimeSlotsModel;
import com.app.barber.models.response.OverviewResponseModel;
import com.app.barber.ui.postauth.activities.home.home_adapter.OverviewInsideAdapter;
import com.app.barber.util.CustomDate;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.views.CustomTextView;

import java.util.ArrayList;
import java.util.List;

import aligningrecyclerview.AligningRecyclerView;
import aligningrecyclerview.AlignmentManager;
import butterknife.BindView;
import butterknife.ButterKnife;


public class OverviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    RecyclerView.OnScrollListener[] scrollListeners;
    Activity serviceListActivity;
    ArrayList<OverviewResponseModel.DataBean.TimeListBean> list;
    OnItemClickListener onItemClickListener;
    private OverviewInsideAdapter oAdapter;
    @AligningRecyclerView.AlignOrientation
    int orientation = 2;
    AlignmentManager aM;

    public OverviewAdapter(FragmentActivity activity, ArrayList<OverviewResponseModel.DataBean.TimeListBean> list, OnItemClickListener onItemClickListener) {
        this.serviceListActivity = activity;
        this.list = list;
        this.onItemClickListener = onItemClickListener;
        scrollListeners = new RecyclerView.OnScrollListener[list.size()];
    }

    @Override
    public ServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_overview_adapter, parent, false);
        return new ServiceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        OverviewResponseModel.DataBean.TimeListBean positionData = list.get(position);
        if (holder instanceof ServiceViewHolder) {
            ((ServiceViewHolder) holder).timeSlot.setText(getRequiredText(positionData.getTimeSlot()));
//            setData(positionData, ((ServiceViewHolder) holder).bookingsRecyclar, position);
//            adView(position, ((ServiceViewHolder) holder).recyclerContainer, positionData);
            if (position == 0) {
                ((ServiceViewHolder) holder).defaultView.setVisibility(View.VISIBLE);
            } else {
                ((ServiceViewHolder) holder).defaultView.setVisibility(View.GONE);
            }
        }
    }



    private String getRequiredText(String timeSlot) {
        Log.e("1", "  " + timeSlot);
        String fText = null;
        try {
            String txt = timeSlot.split("-")[0];
//            String txtN = txt.split("\\n")[1];
            fText = txt.replace(":00", "");
            Log.e("getRequiredText  2", "  " + txt);
        } catch (Exception e) {
            e.printStackTrace();
            return timeSlot.split("-")[0];

        }
        Log.e("getRequiredText  3", "  " + fText);
        return fText;
    }

    private void setData(OverviewResponseModel.DataBean.TimeListBean positionData, AligningRecyclerView bookingsRecyclar, int position) {


        lManager = new LinearLayoutManager(serviceListActivity, LinearLayoutManager.HORIZONTAL, false);
//        lManager = new FlexboxLayoutManager(serviceListActivity);
//        lManager.setFlexDirection(FlexDirection.ROW);
//        lManager.setFlexWrap(FlexWrap.WRAP);
//        bookingsRecyclar.setLayoutManager(lManager);
        oAdapter = new OverviewInsideAdapter(serviceListActivity, positionData.getDayList(), position, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int type, Object o) {

            }
        });
        bookingsRecyclar.setLayoutManager(lManager);
        bookingsRecyclar.setAdapter(oAdapter);
        bookingsRecyclar.setTag(position);
        bookingsRecyclar.setItemAnimator(new DefaultItemAnimator());
//      orientation = AligningRecyclerView.ALIGN_ORIENTATION_HORIZONTAL;
//        aM.join(AligningRecyclerView.ALIGN_ORIENTATION_HORIZONTAL, bookingsRecyclar);



       /* scrollListeners[position] = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                try {
                    bookingsRecyclar.removeOnScrollListener(scrollListeners[position + 1]);
                    bookingsRecyclar.scrollBy(dx, dy);
                    bookingsRecyclar.addOnScrollListener(scrollListeners[position + 1]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        try {
            bookingsRecyclar.addOnScrollListener(scrollListeners[position]);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateAll(List<OverviewResponseModel.DataBean.TimeListBean> posts) {
        this.list.clear();
        this.list.addAll(posts);
        notifyDataSetChanged();
    }

    public void addItem(OverviewResponseModel.DataBean.TimeListBean posts) {
        this.list.add(0, posts);
        notifyDataSetChanged();
    }

    public void updateLikeData(int selectedPosition, String status, String postId) {

    }

    public void remove(int positio) {
        list.remove(positio);
        notifyDataSetChanged();
    }

    int firstVisibleItem, visibleItemCount, totalItemCount, lastVisibleItem;
    private int recentPosition;
    LinearLayoutManager lManager;

    public class ServiceViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.time_slot)
        CustomTextView timeSlot;
        @BindView(R.id.default_view)
        CustomTextView defaultView;
        @BindView(R.id.bookings_recyclar)
        AligningRecyclerView bookingsRecyclar;

        @BindView(R.id.recycler_view_container)
        LinearLayout recyclerContainer;

       /* @BindView(R.id.first_ico)
        ImageView firstDay;

        @BindView(R.id.second_ico)
        ImageView secondDay;

        @BindView(R.id.third_ico)
        ImageView thirdDay;

        @BindView(R.id.fourth_ico)
        ImageView fourthDay;

        @BindView(R.id.five_ico)
        ImageView fifthDay;

        @BindView(R.id.six_ico)
        ImageView sixthDay;

        @BindView(R.id.seven_ico)
        ImageView sevenDay;*/


        public ServiceViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            scrollListeners = new RecyclerView.OnScrollListener[list.size()];
//            bookingsRecyclar.addOnScrollListener(new RecyclerView.OnScrollListener() {
//
//                @Override
//                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                    super.onScrolled(recyclerView, dx, dy);
//                    visibleItemCount = lManager.getChildCount();
//                    totalItemCount = lManager.getItemCount();
//                    firstVisibleItem = lManager.findFirstVisibleItemPosition();
//                    lastVisibleItem = lManager.findLastVisibleItemPosition();
//                    Log.i("Scroll", "========================= " + lastVisibleItem + "  " + firstVisibleItem);
////                    OverviewFragment.getInstance().getLoadMore();
//
//                }
//            });


        }

        public void setData(TimeSlotsModel slotData) {
            // User Detail
        }

        private void toggleRefreshing(boolean b) {
        }


    }
}