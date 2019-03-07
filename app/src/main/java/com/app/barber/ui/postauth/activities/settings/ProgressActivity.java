package com.app.barber.ui.postauth.activities.settings;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.app.barber.R;
import com.app.barber.core.BarberApplication;
import com.app.barber.core.BaseActivity;
import com.app.barber.models.request.ReferalRequest;
import com.app.barber.models.response.BarberStatsResponse;
import com.app.barber.models.response.BaseResponse;
import com.app.barber.models.response.GraphResponsewModel;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.ui.postauth.activities.settings.settingmvp.SettingMVPView;
import com.app.barber.ui.postauth.activities.settings.settingmvp.SettingPresenter;
import com.app.barber.util.CommonUtils;
import com.app.barber.util.GlobalValues;
import com.app.barber.views.CustomEditText;
import com.app.barber.views.CustomTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by harish on 19/11/18.
 */

public class ProgressActivity extends BaseActivity implements SettingMVPView {
    @BindView(R.id.back_toolbar)
    ImageView backToolbar;
    @BindView(R.id.txt_title_toolbar)
    CustomTextView txtTitleToolbar;
    @BindView(R.id.img_edit)
    ImageView imgEdit;
    @BindView(R.id.toolbar_common)
    Toolbar toolbarCommon;
    @BindView(R.id.txt_btn)
    CustomTextView txtBtn;
    @BindView(R.id.total_appointments_txt_count)
    CustomTextView totalAppointmentsTxtCount;
    @BindView(R.id.total_callouts_txt_count)
    CustomTextView totalCalloutsTxtCount;
    @BindView(R.id.total_ratings_txt_count)
    CustomTextView totalRatingsTxtCount;
    @BindView(R.id.total_canceled_txt_count)
    CustomTextView totalCanceledTxtCount;
    @BindView(R.id.punch_rating_bar)
    RatingBar punchRatingBar;
    @BindView(R.id.avg_rating_bar)
    RatingBar avgRatingBar;
//  @BindView(R.id.progress_chart)
//  com.numetriclabz.numandroidcharts.LineChart chart;
//  LineChart chart;//mp chart
    @BindView(R.id.option_type)
    CustomTextView optionType;
    @BindView(R.id.chart)
    LineChartView chart;
    @BindView(R.id.total_earning)
    CustomTextView totalEarning;
    @BindView(R.id.due_amount)
    CustomTextView dueAmount;
    @BindView(R.id.pay_due_btn)
    CustomTextView payDueBtn;

    @BindView(R.id.apply_referer_btn)
    CustomTextView applyRefererBtn;
    @BindView(R.id.refere_code_field)
    CustomEditText refereCodeField;
    private SettingPresenter presenter;
    String[] type = {"Year", "Month", "Week", "Today"};
    private String selectedType = "Year";//default
    String dueAmounStr = "0";

    @Override
    public int getLayoutId() {
        return R.layout.layout_account_progress_screen;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BarberApplication) getApplication()).getMyComponent(ProgressActivity.this).inject(this);
        presenter = new SettingPresenter(ProgressActivity.this);
        presenter.attachView(this);
        txtTitleToolbar.setText(R.string.str_progress);
        optionType.setVisibility(View.VISIBLE);
        optionType.setText(selectedType);
        initSpinner();
        getBarberStats();
//        chart.isActivated();
        initChart();
    }

    private void initSpinner() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getGraphData();
    }

    private void getGraphData() {
        presenter.getGraphData(NetworkConstatnts.RequestCode.API_GET_GRAPH_DATA, selectedType, true);
    }

    private void initChart() {//default
       /* List<ChartData> values = new ArrayList<>();
        values.add(new ChartData(4f, "2001")); //values.add(new ChartData(y,&quot;Labels&quot;));<br />
        values.add(new ChartData(9f, "2002"));
        values.add(new ChartData(18f, "2003"));
        values.add(new ChartData(2f, "2004"));
        values.add(new ChartData(12f, "2005"));
        values.add(new ChartData(9f, "2006"));
        chart.setData(values);
        chart.setDescription("My chart");
        chart.setGesture(false);
        chart.setDrawCubic();*/

        /////----------------------------------------------------
/*
        // creating list of entry<br />
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0f, 0));
        entries.add(new Entry(0f, 1));
        entries.add(new Entry(0f, 2));
        entries.add(new Entry(0f, 3));
        entries.add(new Entry(0f, 4));
        entries.add(new Entry(0f, 5));
        entries.add(new Entry(0f, 6));
        entries.add(new Entry(0f, 7));
        LineDataSet dataset = new LineDataSet(entries, "Earning/" + selectedType);

        // creating labels<br />
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Jan");
        labels.add("Feb");
        labels.add("Mar");
        labels.add("Apr");
        labels.add("May");
        labels.add("Jun");
        labels.add("Jul");
        labels.add("Aug");
        LineData data = new LineData(labels, dataset);
        chart.setData(data);
        chart.setDescription("");
        dataset.setDrawCubic(true);
        dataset.setDrawFilled(true);*/

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    private void getBarberStats() {
        presenter.getBarberStats(NetworkConstatnts.RequestCode.API_GET_BARBER_STATS, selectedType, false);
    }

    @OnClick({R.id.back_toolbar, R.id.img_edit, R.id.option_type, R.id.pay_due_btn, R.id.apply_referer_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_toolbar:
                onBackPressed();
                break;
            case R.id.img_edit:
                break;
            case R.id.apply_referer_btn:
                if (isValidated()) {
                    applyRefere();
                }
                break;
            case R.id.option_type:
                showPopup();
                break;
            case R.id.pay_due_btn:
                if (!dueAmounStr.equals("0") && dueAmounStr != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString(GlobalValues.KEYS.AMOUNT, dueAmounStr);
                    activitySwitcher(ProgressActivity.this, PayDueAmountActivity.class, bundle);
                } else new CommonUtils().ShowToast(getString(R.string.str_no_due_amount));
                break;
        }
    }

    private boolean isValidated() {
        if (CommonUtils.isEmpty(refereCodeField)) {
            CommonUtils.getInstance(this).displayMessage(this, getString(R.string.error_valid_code));
            return false;
        } else
            return true;
    }

    private void applyRefere() {
        ReferalRequest rRequest = new ReferalRequest();
        rRequest.setCode(refereCodeField.getText().toString());
        presenter.appReferer(NetworkConstatnts.RequestCode.API_APPLY_REFERAL, rRequest, false);

    }

    private void showPopup() {
        PopupMenu popup = new PopupMenu(this, optionType);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.actions, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.year:
                        optionType.setText(type[0]);
                        selectedType = type[0];
                        getGraphData();
                        getBarberStats();
                        break;
                    case R.id.month:
                        optionType.setText(type[1]);
                        selectedType = type[1];
                        getGraphData();
                        getBarberStats();
                        break;
                    case R.id.week:
                        optionType.setText(type[2]);
                        selectedType = type[2];
                        getGraphData();
                        getBarberStats();
                        break;
                    case R.id.today:
                        optionType.setText(type[3]);
                        selectedType = type[3];
                        getGraphData();
                        getBarberStats();
                        break;
                }
                return false;
            }
        });
        popup.show();
    }

    @Override
    public void onSuccessResponse(int serviceMode, Object o) {
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_GET_BARBER_STATS:
                BarberStatsResponse responseData = (BarberStatsResponse) o;
                if (responseData.getData() != null)
                    updateView(responseData.getData());
                break;
            case NetworkConstatnts.RequestCode.API_GET_GRAPH_DATA:
                GraphResponsewModel rData = (GraphResponsewModel) o;
                if (rData.getData() != null) {
                    updateGraphView(rData.getData().getList());
                    totalEarning.setText(GlobalValues.Currency.POUNDS + "" + rData.getData().getTotalEarninng());
                    if (rData.getData().getDueAmount() != null) {
                        dueAmount.setText(GlobalValues.Currency.POUNDS + rData.getData().getDueAmount());
                        dueAmounStr = rData.getData().getDueAmount().toString();
                    } else {
                        dueAmounStr = "0";
                    }
                    if (rData.getData().isCanUseReferralCode()) {
                        enableButtns();
                    } else {
                        disableButtons();
                    }
                }
                break;
            case NetworkConstatnts.RequestCode.API_APPLY_REFERAL:
                BaseResponse resData = (BaseResponse) o;
                new CommonUtils().ShowToast(resData.getMessage());
                disableButtons();
                break;
        }
    }

    private void disableButtons() {
        applyRefererBtn.setEnabled(false);
        refereCodeField.setEnabled(false);
        applyRefererBtn.setBackgroundResource(R.drawable.rectangle_light_grey_drawable);
    }

    private void enableButtns() {
        refereCodeField.setEnabled(true);
        applyRefererBtn.setEnabled(true);
        applyRefererBtn.setBackgroundResource(R.drawable.rectangle_blue_drawable);
    }


    private void updateGraphView(List<GraphResponsewModel.DataBean.ListBean> list) {

        List yAxisValues = new ArrayList();
        List axisValues = new ArrayList();
        Line line = new Line(yAxisValues);
        line.setColor(Color.parseColor("#2AB3FF"));

        for (int i = 0; i < list.size(); i++) {
//            axisValues.add(i, new AxisValue(i).setLabel(axisData[i]));
            if (selectedType.equals(type[3]))
                axisValues.add(i, new AxisValue(i).setLabel(list.get(i).getXAxis().split(":")[0] + " " + list.get(i).getXAxis().split("\\s")[1]));
            if (selectedType.equals(type[2]))
                axisValues.add(i, new AxisValue(i).setLabel(list.get(i).getXAxis()));
            if (selectedType.equals(type[0]))
                axisValues.add(i, new AxisValue(i).setLabel(list.get(i).getXAxis()));
            if (selectedType.equals(type[1]))
                axisValues.add(i, new AxisValue(i).setLabel(list.get(i).getXAxis()));
        }

        for (int i = 0; i < list.size(); i++) {
//            yAxisValues.add(new PointValue(i, yAxisData[i]));
            yAxisValues.add(new PointValue(i, list.get(i).getYAxis()));
        }
        List lines = new ArrayList();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        Axis axis = new Axis();
        axis.setName("Earning/" + selectedType);
        axis.setValues(axisValues);
        axis.setTextSize(10);
        axis.setHasLines(true);
        axis.setLineColor(Color.parseColor("#B3B7BB"));
        axis.setTextColor(Color.parseColor("#B3B7BB"));
        data.setAxisXBottom(axis);

        Axis yAxis = new Axis();
//      yAxis.setName("Earning in pounds");
        yAxis.setTextColor(Color.parseColor("#03A9F4"));
        yAxis.setTextSize(10);
//      yAxis.setHasLines(true);
        data.setAxisYLeft(yAxis);


        chart.setLineChartData(data);
        Viewport viewport = new Viewport(chart.getMaximumViewport());
        viewport.top = 1000;
        chart.setMaximumViewport(viewport);
        chart.setCurrentViewport(viewport);


    }

    private void updateView(BarberStatsResponse.DataBean data) {
        totalAppointmentsTxtCount.setText(data.getAppointmentsCount());
        totalCalloutsTxtCount.setText(data.getCallOutCount());
        avgRatingBar.setRating(Float.parseFloat(data.getAvgRating()));
        punchRatingBar.setRating(Float.parseFloat(data.getAvgPuchRating()));
        totalCanceledTxtCount.setText(data.getCancelledCount());
    }

    @Override
    public void onfaliurResponse(int serviceMode, Object o) {
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_APPLY_REFERAL:
                BaseResponse resData = (BaseResponse) o;
                new CommonUtils().ShowToast(resData.getMessage());
                break;
        }
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
}
