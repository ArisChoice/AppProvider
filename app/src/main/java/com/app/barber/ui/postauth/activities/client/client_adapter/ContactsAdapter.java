package com.app.barber.ui.postauth.activities.client.client_adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import com.app.barber.R;
import com.app.barber.models.ContactModel;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.views.CustomTextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.viethoa.RecyclerViewFastScroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ContactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RecyclerViewFastScroller.BubbleTextGetter, Filterable {

    private List<ContactModel> specList;
    private List<ContactModel> filterList;
    OnItemClickListener listener;
    Activity specialiseActivity;
    ArrayList<String> myDataset;
    HashMap<String, Integer> mapIndex;

    public ContactsAdapter(Activity specialiseActivity, List<ContactModel> feedsList, ArrayList<String> myDataset,
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
        ContactModel positionData = filterList.get(position);
        if (holder instanceof CustomerHolder) {
            ((CustomerHolder) holder).clientName.setText(positionData.getContactName());
            ((CustomerHolder) holder).clientNumver.setText(positionData.getContactNumber());
            if (positionData.isSelected()) {
                ((CustomerHolder) holder).selectedContact.setVisibility(View.VISIBLE);
            } else ((CustomerHolder) holder).selectedContact.setVisibility(View.GONE);

        }
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public void updateAll(Set<ContactModel> posts) {
        this.specList.clear();
        this.specList.addAll(posts);
        notifyDataSetChanged();
    }

    public void addItem(ContactModel posts) {
        this.filterList.add(0, posts);
        notifyDataSetChanged();
    }

    @Override
    public String getTextToShowInBubble(int pos) {
        if (pos < 0 || pos >= filterList.size())
            return null;

        String name = filterList.get(pos).getContactName();
        if (name == null || name.length() < 1)
            return null;

        return filterList.get(pos).getContactName().substring(0, 1);
    }

    public ArrayList<ContactModel> getSelectedContacts() {
        ArrayList<ContactModel> cData = new ArrayList<>();
        for (int i = 0; i < filterList.size(); i++) {
            if (filterList.get(i).isSelected()) {
                cData.add(filterList.get(i));
            }
        }
        return cData;
    }

    public class CustomerHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.client_image)
        SimpleDraweeView clientImage;

        @BindView(R.id.client_name)
        CustomTextView clientName;
        @BindView(R.id.client_numver)
        CustomTextView clientNumver;
        @BindView(R.id.selected_contact)
        ImageView selectedContact;


        public CustomerHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

        @OnClick({R.id.client_image, R.id.client_name})
        public void onLCick(View v) {
            if (filterList.get(getAdapterPosition()).isSelected()) {
                filterList.get(getAdapterPosition()).setSelected(false);
            } else {
                filterList.get(getAdapterPosition()).setSelected(true);
            }
            notifyDataSetChanged();
            new android.os.Handler().postDelayed(() -> listener.onItemClick(v, getAdapterPosition(), GlobalValues.ClickOperations.DETAILS, null), GlobalValues.TIME_DURATIONS.SMALL);

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
                    List<ContactModel> filteredList = new ArrayList<>();
                    for (ContactModel row : specList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getContactName().toLowerCase().contains(charString.toLowerCase()) || row.getContactName().contains(charSequence)) {
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
                filterList = (ArrayList<ContactModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
