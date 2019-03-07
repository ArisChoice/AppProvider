package com.app.barber.ui.postauth.activities.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.app.barber.R;
import com.app.barber.core.BaseActivity;
import com.app.barber.models.response.ServiceListResponseModel;
import com.app.barber.ui.adapters.BarberReviewsListAdapter;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.views.CustomTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by harish on 19/11/18.
 */

public class ReviewRatingActivity extends BaseActivity {
    @BindView(R.id.back_toolbar)
    ImageView backToolbar;
    @BindView(R.id.txt_title_toolbar)
    CustomTextView txtTitleToolbar;
    @BindView(R.id.img_edit)
    ImageView imgEdit;
    @BindView(R.id.toolbar_common)
    Toolbar toolbarCommon;
    @BindView(R.id.recyclar_view_lst)
    RecyclerView recyclarViewLst;
    @BindView(R.id.no_list_data_text)
    CustomTextView noListDataText;
    private LinearLayoutManager layoutManager;
    private BarberReviewsListAdapter barberReviewAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.layout_review_rating_screen;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        txtTitleToolbar.setText(R.string.str_rating_review);
        initRecyclar();
    }

    private void initRecyclar() {
        layoutManager = new LinearLayoutManager(ReviewRatingActivity.this);
        recyclarViewLst.setLayoutManager(layoutManager);
        barberReviewAdapter = new BarberReviewsListAdapter(ReviewRatingActivity.this, new ArrayList<ServiceListResponseModel.ResponseBean>(10),
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int positio, int type, Object o) {
                        switch (type) {

                        }

                    }
                });
        recyclarViewLst.setAdapter(barberReviewAdapter);
    }

    @OnClick({R.id.back_toolbar, R.id.img_edit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_toolbar:
                onBackPressed();
                break;
            case R.id.img_edit:
                break;
        }
    }
}
