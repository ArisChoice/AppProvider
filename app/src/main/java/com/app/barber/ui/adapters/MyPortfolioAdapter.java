package com.app.barber.ui.adapters;

import android.content.res.TypedArray;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.barber.R;
import com.app.barber.models.TimeSlotsModel;
import com.app.barber.models.response.MyImagesResponseModel;
import com.app.barber.ui.postauth.activities.home.EditProfileActivity;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.ImageUtility;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.views.CustomTextView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MyPortfolioAdapter extends RecyclerView.Adapter<MyPortfolioAdapter.OptionViewHolder> {

    FragmentActivity activity;
    List<MyImagesResponseModel.ImagesBean> imagesList;
    OnItemClickListener listener;
    TypedArray icons;
    String from;

    public MyPortfolioAdapter(FragmentActivity activity, String from, List<MyImagesResponseModel.ImagesBean> imagesList, OnItemClickListener listener) {
        this.activity = activity;
        this.listener = listener;
        this.imagesList = imagesList;
        this.from = from;
    }

    @Override
    public OptionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_portfolio_adapter, parent, false);
        return new OptionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OptionViewHolder holder, int position) {
        MyImagesResponseModel.ImagesBean positionData = imagesList.get(position);
        holder.imageStyle.setImageURI(ImageUtility.getValidUrl(positionData.getMItem1()));
        if (position == 0 && from.equals(EditProfileActivity.class.getName())) {
            holder.addLay.setVisibility(View.VISIBLE);
        } else {
            holder.addLay.setVisibility(View.GONE);
            holder.removeImage.setVisibility(View.GONE);
        }
        if (from.equals(EditProfileActivity.class.getName())) {
            holder.removeImage.setVisibility(View.VISIBLE);
        } else holder.removeImage.setVisibility(View.GONE);


    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    public void updateAll(List<MyImagesResponseModel.ImagesBean> imagesList) {
        this.imagesList.clear();
        this.imagesList.addAll(imagesList);
        notifyDataSetChanged();
    }

    public void addItem(TimeSlotsModel posts) {
//        this.slotsList.add(0, posts);
//        notifyDataSetChanged();
    }

    public List<MyImagesResponseModel.ImagesBean> getAll() {
        return imagesList;
    }

    public MyImagesResponseModel.ImagesBean getpositionData(int position) {
        return imagesList.get(position);
    }

    public void remove(int position) {
        this.imagesList.remove(position);
        notifyDataSetChanged();
    }


    public class OptionViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.my_style_image)
        SimpleDraweeView imageStyle;
        @BindView(R.id.add_image_btn_lay)
        LinearLayout addLay;
        @BindView(R.id.remove_image_btn)
        CustomTextView removeImage;

        public OptionViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

        @OnClick({R.id.add_image_btn_lay, R.id.my_style_image, R.id.remove_image_btn})
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.add_image_btn_lay:
                    listener.onItemClick(v, getAdapterPosition(), GlobalValues.ClickOperations.ADD_IMAGE, null);
                    break;
                case R.id.my_style_image:
                    listener.onItemClick(v, getAdapterPosition(), GlobalValues.ClickOperations.FULL_IMAGE, null);
                    break;
                case R.id.remove_image_btn:
                    listener.onItemClick(v, getAdapterPosition(), GlobalValues.ClickOperations.REMOVE, null);
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