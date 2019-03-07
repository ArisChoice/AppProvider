package com.app.barber.ui.postauth.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.barber.R;
import com.app.barber.core.BarberApplication;
import com.app.barber.core.BaseFragment;
import com.app.barber.models.response.ChatUsersResponseModel;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.ui.postauth.activities.chat.chat_adapter.MessageListAdapter;
import com.app.barber.ui.postauth.activities.chat.chatmvp.ChatMVPView;
import com.app.barber.ui.postauth.activities.chat.chatmvp.ChatPresenter;
import com.app.barber.ui.postauth.activities.client.SelectCustomerActivity;
import com.app.barber.ui.postauth.activities.socket_work.chat.ChatActivity;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.PreferenceManager;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.views.CustomTextView;


import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.app.barber.core.BaseActivity.activitySwitcher;

/**
 * Created by harish on 25/10/18.
 */

public class MessageFragment extends BaseFragment implements ChatMVPView {
    @BindView(R.id.recyclar_view_lst)
    RecyclerView recyclarViewLst;
    @BindView(R.id.no_list_data_text)
    CustomTextView noListDataText;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.add_fab)
    ImageView addFab;
    private MessageListAdapter messageAdapter;
    private ChatPresenter presenter;

    @Override
    protected int getFragmentLayout() {
        return R.layout.layout_nessage_screen;
    }

    @Override
    public void UpdateData(int position, Bundle bundle) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getFragmentLayout(), container, false);
        ButterKnife.bind(this, rootView);
        ((BarberApplication) getActivity().getApplication()).getMyComponent(getActivity()).inject(this);
        presenter = new ChatPresenter(getActivity());
        presenter.attachView(this);
        initRecyclarView();
//        initQbuser();

        initSwipeRefresh();
        return rootView;
    }

    private void initSwipeRefresh() {
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(true);
                retriveChatList();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        retriveChatList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    /*private void initQbuser() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    try {
                        QBChatService.getInstance().login(PreferenceManager.getInstance(getActivity()).getQbUser());
                    } catch (XMPPException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SmackException e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }*/

    private void retriveChatList() {
       /* QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();
        requestBuilder.setLimit(100);
        requestBuilder.sortAsc("last_message_date_sent");

        QBRestChatService.getChatDialogs(QBDialogType.GROUP, requestBuilder).performAsync(
                new QBEntityCallback<ArrayList<QBChatDialog>>() {
                    @Override
                    public void onSuccess(ArrayList<QBChatDialog> result, Bundle params) {
                        int totalEntries = params.getInt("total_entries");
                    }

                    @Override
                    public void onError(QBResponseException responseException) {

                    }
                });*/

        presenter.getChatParticipent(NetworkConstatnts.RequestCode.API_GET_CHAT_PARTICIPENT, false);

    }

    private void initRecyclarView() {
        messageAdapter = new MessageListAdapter(getActivity(), new ArrayList<ChatUsersResponseModel.ResponseBean>(),
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position, int type, Object t) {
                        ChatUsersResponseModel.ResponseBean positionData = (ChatUsersResponseModel.ResponseBean) t;
                        Intent intent = new Intent(getActivity(), ChatActivity.class);
                        intent.putExtra(GlobalValues.KEYS.EXTRA_DIALOG_ID, positionData.getDialogId());
                        intent.putExtra(GlobalValues.KEYS.OTHER_IMAGE, positionData.getImage());
                        intent.putExtra(GlobalValues.KEYS.USER_ID, "" + positionData.getId());
                        startActivity(intent);
                    }
                });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclarViewLst.setLayoutManager(layoutManager);
        recyclarViewLst.setAdapter(messageAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onSuccessResponse(int serviceMode, Object model) {
        swipeRefresh.setRefreshing(false);
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_GET_CHAT_PARTICIPENT:
                ChatUsersResponseModel responseData = ((ChatUsersResponseModel) model);
                if (responseData != null && responseData.getResponse() != null && responseData.getResponse().size() > 0) {
                    messageAdapter.updateAll(responseData.getResponse());
                    noListDataText.setVisibility(View.GONE);
                    recyclarViewLst.setVisibility(View.VISIBLE);
                } else {
                    noListDataText.setVisibility(View.VISIBLE);
                    recyclarViewLst.setVisibility(View.GONE);
                }
                break;

        }
    }

    @Override
    public void onfaliurResponse(int serviceMode, Object o) {
        swipeRefresh.setRefreshing(false);
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

    @OnClick(R.id.add_fab)
    public void onClick() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(GlobalValues.KEYS.FROM, true);
        activitySwitcher(getActivity(), SelectCustomerActivity.class, bundle);
    }
}
