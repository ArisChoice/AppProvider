package com.app.barber.ui.postauth.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.barber.R;
import com.app.barber.core.BarberApplication;
import com.app.barber.core.BaseFragment;
import com.app.barber.models.response.ClientsListResponseModel;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.ui.postauth.activities.client.AddClientActivity;
import com.app.barber.ui.postauth.activities.client.ClientDetailActivity;
import com.app.barber.ui.postauth.activities.client.MobileContactActivity;
import com.app.barber.ui.postauth.activities.client.addclientmvp.AddClientMVPView;
import com.app.barber.ui.postauth.activities.client.addclientmvp.AddClientPresenter;
import com.app.barber.ui.postauth.activities.client.client_adapter.CustomersAdapter;
import com.app.barber.util.CommonUtils;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.util.iface.StatusCallback;
import com.app.barber.views.CustomEditText;
import com.app.barber.views.CustomTextView;
import com.app.barber.views.FastScrollRecyclerView;
import com.viethoa.RecyclerViewFastScroller;
import com.viethoa.models.AlphabetItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.services.concurrency.AsyncTask;

import static com.app.barber.core.BaseActivity.activitySwitcher;

/**
 * Created by harish on 25/10/18.
 */

public class ClientsFragment extends BaseFragment implements AddClientMVPView {
    @BindView(R.id.user_phone_contact_btn)
    CustomTextView userPhoneContactBtn;
    @BindView(R.id.create_btn)
    CustomTextView createBtn;
    @BindView(R.id.default_layout)
    LinearLayout defaultLayout;
    @BindView(R.id.fast_scroller_recycler)
    RecyclerViewFastScroller fastrecyclarViewLst;


    @BindView(R.id.list_holder_lay)
    RelativeLayout listHolderLay;

    @BindView(R.id.add_client_fab)
    ImageView addClientFab;
    @BindView(R.id.serach_customer)
    CustomEditText serachCustomer;
    @BindView(R.id.client_recycler)
    RecyclerView clientRecycler;
    private AddClientPresenter presenter;
    private CustomersAdapter cAdapter;
    private ArrayList<String> myDataset;
    private HashMap<String, Integer> mapIndex;
    public static NotifyCall notifyCall;

    public interface NotifyCall {
        void refreshView();
    }


    @Override
    protected int getFragmentLayout() {
        return R.layout.layout_clients_screen;
    }

    @Override
    public void UpdateData(int position, Bundle bundle) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getFragmentLayout(), container, false);
        ButterKnife.bind(this, rootView);

        ((BarberApplication) getActivity().getApplication()).getMyComponent(getActivity()).inject(this);
        presenter = new AddClientPresenter(getActivity());
        presenter.attachView(this);
//        initialiseData();
        initRecyclar();
        getMyCustomers();
        notifyCall = () -> {
            getMyCustomers();
        };
        initSearchFilter();
        return rootView;
    }

    private void initSearchFilter() {
        // listening to search query text change
        serachCustomer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initRecyclar() {
        cAdapter = new CustomersAdapter(getActivity(), new ArrayList<ClientsListResponseModel.ResponseBean>(), myDataset, mapIndex, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int type, Object o) {
                switch (type) {
                    case GlobalValues.ClickOperations.DETAILS:
                        ClientsListResponseModel.ResponseBean pData = (ClientsListResponseModel.ResponseBean) o;
                        Bundle bndl = new Bundle();
                        bndl.putSerializable(GlobalValues.KEYS.CUSTOMER_DETAIL, pData);
                        activitySwitcher(getActivity(), ClientDetailActivity.class, bndl);
                        break;
                }
            }
        });
        clientRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        clientRecycler.setAdapter(cAdapter);

    }

    //    private List<String> mDataArray;
    private List<AlphabetItem> mAlphabetItems;

    private void initialiseData(List<ClientsListResponseModel.ResponseBean> response) {
        // specify an adapter (see also next example)
        mAlphabetItems = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            mAlphabetItems.add(new AlphabetItem(i, Character.toString((char) (65 + i)), false));
            Log.e("initialiseData", "   " + Character.toString((char) (65 + i)));
        }

//        //Alphabet fast scroller data
//        mAlphabetItems = new ArrayList<>();
//        List<String> strAlphabets = new ArrayList<>();
//        for (int i = 0; i < response.size(); i++) {
//            String name = response.get(i).getName();
//            if (name == null || name.trim().isEmpty())
//                continue;
//            String word = name.substring(0, 1);
//            if (!strAlphabets.contains(word)) {
//                strAlphabets.add(word);
//                mAlphabetItems.add(new AlphabetItem(i, word, false));
//            }
//        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();

    }
    @Override
    public void onResume() {
        super.onResume();
//        refreshView();
    }

    private void refreshView() {
        if (cAdapter.getItemCount() > 0) {
            defaultLayout.setVisibility(View.GONE);
            listHolderLay.setVisibility(View.VISIBLE);
        } else {
            defaultLayout.setVisibility(View.VISIBLE);
            listHolderLay.setVisibility(View.GONE);
        }
    }

    private void getMyCustomers() {
        presenter.getMyCustomers(NetworkConstatnts.RequestCode.API_LIST_CUSTOMERS, null, false);
    }

    @OnClick({R.id.user_phone_contact_btn, R.id.create_btn, R.id.add_client_fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_phone_contact_btn:
                fetchContactLst();
                break;
            case R.id.create_btn:
                activitySwitcher(getActivity(), AddClientActivity.class, null);
                break;
            case R.id.add_client_fab:
//                activitySwitcher(getActivity(), AddClientActivity.class, null);
                defaultLayout.setVisibility(View.VISIBLE);
                listHolderLay.setVisibility(View.GONE);

                break;
        }
    }


    private void fetchContactLst() {
        activitySwitcher(getActivity(), MobileContactActivity.class, null);
    }

    @Override
    public void onSuccessResponse(int serviceMode, Object o) {
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_LIST_CUSTOMERS:
                if (((ClientsListResponseModel) o).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    if (((ClientsListResponseModel) o).getResponse() != null && ((ClientsListResponseModel) o).getResponse().size() > 0) {
                        initialiseData(((ClientsListResponseModel) o).getResponse());
                        cAdapter.updateAll(((ClientsListResponseModel) o).getResponse());
                        fastrecyclarViewLst.setRecyclerView(clientRecycler);
                        fastrecyclarViewLst.setUpAlphabet(mAlphabetItems);
                        defaultLayout.setVisibility(View.GONE);
                        listHolderLay.setVisibility(View.VISIBLE);
                    } else {
                        defaultLayout.setVisibility(View.VISIBLE);
                        listHolderLay.setVisibility(View.GONE);
                    }
                }
                break;
        }
    }

    @Override
    public void onfaliurResponse(int serviceMode, Object o) {

    }

    @Override
    public void showProgres() {

    }

    @Override
    public void hidePreogress() {

    }

    @Override
    public void onSuccess(Object o, int type) {

    }

    @Override
    public void onError(String localizedMessage) {

    }

    @Override
    public void onException(Exception e) {

    }

    private HashMap<String, Integer> calculateIndexesForName(ArrayList<String> items) {
        HashMap<String, Integer> mapIndex = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < items.size(); i++) {
            String name = items.get(i);
            String index = name.substring(0, 1);
            index = index.toUpperCase();
            if (!mapIndex.containsKey(index)) {
                mapIndex.put(index, i);
            }
        }
        return mapIndex;
    }


}
