package com.app.barber.ui.postauth.activities.client.client_adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;

import com.app.barber.R;
import com.app.barber.models.TimeSlotsModel;
import com.app.barber.models.response.ClientsListResponseModel;
import com.app.barber.models.response.SpecialisationResponseModel;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.ImageUtility;
import com.app.barber.util.iface.FastScrollRecyclerViewInterface;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.views.CustomTextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.viethoa.RecyclerViewFastScroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CustomersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RecyclerViewFastScroller.BubbleTextGetter, Filterable {

    private List<ClientsListResponseModel.ResponseBean> specList;
    private List<ClientsListResponseModel.ResponseBean> filterList;
    OnItemClickListener listener;
    Activity specialiseActivity;
    ArrayList<String> myDataset;
    HashMap<String, Integer> mapIndex;

    public CustomersAdapter(Activity specialiseActivity, List<ClientsListResponseModel.ResponseBean> feedsList, ArrayList<String> myDataset,
                            HashMap<String, Integer> mapIndex,
                            OnItemClickListener listener) {
        this.specList = feedsList;
        this.listener = listener;
        this.specialiseActivity = specialiseActivity;
        this.myDataset = myDataset;
        this.mapIndex = mapIndex;
        filterList = feedsList;
    }

    @Override
    public CustomerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_customer_adapter, parent, false);
        return new CustomerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ClientsListResponseModel.ResponseBean positionData = filterList.get(position);
        if (holder instanceof CustomerHolder) {
            ((CustomerHolder) holder).clientName.setText(positionData.getName());

            if (positionData.getImage() != null && !positionData.getImage().equals("")) {
                ((CustomerHolder) holder).clientImage.setImageURI(ImageUtility.getValidUrl(positionData.getImage()));
            }
            if (positionData.getContact() != null && !positionData.getContact().equals("")) {
                ((CustomerHolder) holder).clientNumver.setText(positionData.getContact());
            } else {
                ((CustomerHolder) holder).clientNumver.setText(R.string.str_not_available);
            }
        }
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public void updateAll(List<ClientsListResponseModel.ResponseBean> posts) {
        this.specList.clear();
        this.specList.addAll(posts);
        notifyDataSetChanged();
    }

    public void addItem(ClientsListResponseModel.ResponseBean posts) {
        this.filterList.add(0, posts);
        notifyDataSetChanged();
    }

    @Override
    public String getTextToShowInBubble(int pos) {
        if (pos < 0 || pos >= filterList.size())
            return null;

        String name = filterList.get(pos).getName();
        if (name == null || name.length() < 1)
            return null;

        return filterList.get(pos).getName().substring(0, 1);
    }

    public void setQBDialogId(String dialogId, int userId) {
        for (int i = 0; i < filterList.size(); i++) {
            if (filterList.get(i).getId() == userId) {
                filterList.get(i).getUserInfo().setDialogId(dialogId);
            }
        }
        notifyDataSetChanged();
    }

    public class CustomerHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.client_image)
        SimpleDraweeView clientImage;

        @BindView(R.id.client_name)
        CustomTextView clientName;
        @BindView(R.id.client_numver)
        CustomTextView clientNumver;


        public CustomerHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

        @OnClick({R.id.client_image, R.id.client_name})
        public void onLCick(View v) {
            listener.onItemClick(v, getAdapterPosition(), GlobalValues.ClickOperations.DETAILS, filterList.get(getAdapterPosition()));

        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filterList = specList;
                } else {
                    List<ClientsListResponseModel.ResponseBean> filteredList = new ArrayList<>();
                    for (ClientsListResponseModel.ResponseBean row : specList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getContact().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    filterList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filterList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filterList = (ArrayList<ClientsListResponseModel.ResponseBean>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
