package com.app.barber.util.weekengine;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.barber.R;
import com.app.barber.models.TimeSlotsModel;
import com.app.barber.models.response.BlockedDatesResponse;
import com.app.barber.models.response.FutureAppointmentStatusModel;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.views.CustomTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class WeekViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ModelDay> specList;
    OnItemClickListener listener;
    Activity specialiseActivity;
    private boolean isClicable = true;
    private boolean isMultiselection;//is multiselection enabled

    public WeekViewAdapter(Activity specialiseActivity, List<ModelDay> feedsList, OnItemClickListener listener) {
        this.specList = feedsList;
        this.listener = listener;
        this.specialiseActivity = specialiseActivity;
    }

    @Override
    public SlotsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_week_days_adapter, parent, false);
        return new SlotsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ModelDay positionData = specList.get(position);
        ((SlotsViewHolder) holder).weekDate.setText("" + positionData.getDate());
        ((SlotsViewHolder) holder).weekDay.setText("" + positionData.getDay());
        if (positionData.isSelected()) {
            ((SlotsViewHolder) holder).weekDate.setText("" + positionData.getDate());
            ((SlotsViewHolder) holder).weekDate.setTextColor(specialiseActivity.getResources().getColor(R.color.color_white));
            ((SlotsViewHolder) holder).weekDate.setBackgroundResource(R.drawable.circular_blue_background);
        } else {
            ((SlotsViewHolder) holder).weekDate.setText("" + positionData.getDate());
            ((SlotsViewHolder) holder).weekDate.setTextColor(specialiseActivity.getResources().getColor(R.color.color_grey));
            ((SlotsViewHolder) holder).weekDate.setBackgroundResource(R.color.color_white);
        }
        if (positionData.isApointmentAvailable()) {
            ((SlotsViewHolder) holder).appointStatus.setVisibility(View.VISIBLE);
        } else ((SlotsViewHolder) holder).appointStatus.setVisibility(View.GONE);

        if (positionData.isBlockedHours()) {
            ((SlotsViewHolder) holder).appointStatus.setVisibility(View.VISIBLE);
        } else ((SlotsViewHolder) holder).appointStatus.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return specList.size();
    }

    public void updateAll(List<ModelDay> posts) {
        this.specList.clear();
        this.specList.addAll(posts);
        notifyDataSetChanged();
    }

    public void addItem(ModelDay posts) {
        this.specList.add(0, posts);
        notifyDataSetChanged();
    }

    public String getSelectedDate() {
        return specList.get(0).getFullDate();//default selected date
    }

    /**
     * setCurrent days.
     */
    public void setCurrentWeek(int iDay) {
        ArrayList<ModelDay> modelList = new ArrayList<>();
        ModelDay mDay = new ModelDay();
        SimpleDateFormat sdfFull = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("EE");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd");
        for (int i = 0; i < iDay; i++) {
            Calendar calendar = new GregorianCalendar();
            calendar.add(Calendar.DATE, i);
            String day = sdf.format(calendar.getTime());
            String date = sdf1.format(calendar.getTime());
            String fullDate = sdfFull.format(calendar.getTime());
            Log.i("TAG", day + " " + date);
            mDay = new ModelDay();
            mDay.setDate(Integer.parseInt(date));
            mDay.setDay(day.substring(0, 2));
            mDay.setFullDate(fullDate);
            if (i == 0)
                mDay.setSelected(true);
            modelList.add(mDay);
        }
        specList.clear();
        specList.addAll(modelList);
        notifyDataSetChanged();
    }

    /**
     * Get FirstDay
     */
    public String getFirstDay() {
        return specList.get(0).fullDate;
    }

    /**
     * Get LastDay
     */
    public String getEndDay() {
        return specList.get(specList.size() - 1).fullDate;
    }

    public void noDeafultselection() {
        isClicable = false;
//        for (int i = 0; i < specList.size(); i++) {
//            specList.get(i).setSelected(false);
//        }
        notifyDataSetChanged();
    }

    /**
     * Get Previous Week
     */
    public void getPastWeek(int iDay) {
        ArrayList<ModelDay> modelList = new ArrayList<>();
        ModelDay mDay = new ModelDay();
        SimpleDateFormat sdfFull = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("EE");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd");
        for (int i = 1; i < iDay; i++) {
            Calendar calendar = new GregorianCalendar();
            calendar.add(Calendar.DATE, -i);
            String day = sdf.format(calendar.getTime());
            String date = sdf1.format(calendar.getTime());
            String fullDate = sdfFull.format(calendar.getTime());
            Log.i("TAG", day + " " + date);
            mDay = new ModelDay();
            mDay.setDate(Integer.parseInt(date));
            mDay.setDay(day.substring(0, 2));
            mDay.setFullDate(fullDate);
//            if (i == 0)
//                mDay.setSelected(true);
            modelList.add(0, mDay);
        }
//        specList.clear();
        specList.addAll(0, modelList);
        noDeafultselection();
        notifyDataSetChanged();
    }

    /**
     * Get next week
     */
    public void getNextWeek(int iDay) {
        Log.i("getNextWeek ", "  " + specList.get(specList.size() - 1).getFullDate());
        SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");
        Calendar calendar = Calendar.getInstance();
        try {
            Date d = f.parse(specList.get(specList.size() - 1).getFullDate());
            long milliseconds = d.getTime();

            calendar.setTimeInMillis(milliseconds);
//            calendar.add(Calendar.DATE, iDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ArrayList<ModelDay> modelList = new ArrayList<>();
        ModelDay mDay = new ModelDay();
        SimpleDateFormat sdfFull = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("EE");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd");
        for (int i = 0; i < iDay; i++) {
            Log.i("TAG", " --------------------------- " + i);
            calendar.add(Calendar.DATE, 1);
            String day = sdf.format(calendar.getTime());
            String date = sdf1.format(calendar.getTime());
            String fullDate = sdfFull.format(calendar.getTime());
            Log.i("TAG", day + " " + date);
            mDay = new ModelDay();
            mDay.setDate(Integer.parseInt(date));
            mDay.setDay(day.substring(0, 2));
            mDay.setFullDate(fullDate);
            if (i == 0)
                mDay.setSelected(true);
            modelList.add(mDay);
        }
        specList.clear();
        specList.addAll(modelList);
        noDeafultselection();
        notifyDataSetChanged();
    }

    /**
     * Get Particular data.
     */
    public String getpostionData(int firstVisibleItem) {
        return specList.get(firstVisibleItem).getFullDate();
    }

    /**
     * update  status for future appointments.
     */
    public void notifyDateStatus(List<FutureAppointmentStatusModel.ReponseBean> reponse) {
        for (int i = 0; i < specList.size(); i++) {
            for (int j = 0; j < reponse.size(); j++) {
                if (this.specList.get(i).getFullDate().equals(reponse.get(j).getDate())) {
//                    Log.e("loop ", " notifyDateStatus  " + specList.get(i).getFullDate() + "    " + reponse.get(j).getDate());
                    this.specList.get(i).setApointmentAvailable(reponse.get(j).isStatus());
                    notifyDataSetChanged();
                }
            }
        }
        notifyDataSetChanged();
    }

    /**
     * update  status for future blockhours.
     */
    public void notifyDateStatus(List<BlockedDatesResponse.ListBean> reponse, Object oj) {
        for (int i = 0; i < specList.size(); i++) {
            for (int j = 0; j < reponse.size(); j++) {
                if (this.specList.get(i).getFullDate().equals(reponse.get(j).getDate())) {
//                    Log.e("loop ", " notifyDateStatus  " + specList.get(i).getFullDate() + "    " + reponse.get(j).getDate());
                    this.specList.get(i).setBlockedHours(reponse.get(j).isIsBlockHourExist());
                    notifyDataSetChanged();
                }
            }
        }
        notifyDataSetChanged();
    }

    public void updateAppointmentStatus(int position) {
        for (int i = 0; i < specList.size(); i++) {
            if (i == position) {
                specList.get(i).setApointmentAvailable(false);
            }
        }
        notifyDataSetChanged();
    }

    /**
     * Set particular position selected
     */
    public void setselected(int position) {
        if (specList.get(position).isSelected()) {
            this.specList.get(position).setSelected(false);
        } else {
            this.specList.get(position).setSelected(true);
        }
        notifyItemChanged(position);
    }

    /**
     * Allow multi selection
     */
    public void allowMultipleSelection() {
        this.isMultiselection = true;
        notifyDataSetChanged();
    }

    /**
     * Get selectedItems List;
     */
    public ArrayList<String> getAllSelectedDates() {//Get comma selcted values string array.
        ArrayList<String> selectedDates = new ArrayList<>();

        for (int i = 0; i < specList.size(); i++) {
            if (specList.get(i).isSelected()) {
                selectedDates.add(specList.get(i).getFullDate());
            }
        }


        return selectedDates;
    }

    /**
     * Get selected items string comma separated.
     */
    public String getAllSelectedDate() {//Get comma saperated values
        String selectedType = null;
        try {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < specList.size(); i++) {
                if (specList.get(i).isSelected()) {
                    builder.append(specList.get(i).getFullDate() + ",");
                }
            }
            selectedType = builder.toString();
            if (selectedType != null && selectedType.length() > 0 && selectedType.charAt(selectedType.length() - 1) == ',') {
                selectedType = selectedType.substring(0, selectedType.length() - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return selectedType;
    }

    /**
     * Set selected date.
     */
    public void setselectedDate(String date) {
        for (int i = 0; i < specList.size(); i++) {
            if (date.equals(specList.get(i).getFullDate())) {
                specList.get(i).setSelected(true);
            } else {
                specList.get(i).setSelected(false);
            }
        }
        notifyDataSetChanged();
    }

    public class SlotsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.week_day)
        CustomTextView weekDay;
        @BindView(R.id.week_date)
        CustomTextView weekDate;
        @BindView(R.id.appoint_status)
        ImageView appointStatus;

        public SlotsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

        @OnClick({R.id.week_date})
        public void onLCick(View v) {
            switch (v.getId()) {
                case R.id.week_date:
                    if (isClicable) {
                        if (isMultiselection) {
                            listener.onItemClick(v, getAdapterPosition(), GlobalValues.ClickOperations.DATE_CLICKED, null);
                            setselected(getAdapterPosition());
                        } else {
                            listener.onItemClick(v, getAdapterPosition(), GlobalValues.ClickOperations.DATE_CLICKED, specList.get(getAdapterPosition()));
                            updateSelection(getAdapterPosition());
                        }
                    }
                    break;
            }
        }
    }

    private void updateSelection(int adapterPosition) {
        for (int i = 0; i < specList.size(); i++) {
            if (i == adapterPosition)
                specList.get(i).setSelected(true);
            else specList.get(i).setSelected(false);
        }
        notifyDataSetChanged();
    }

    public void setData(TimeSlotsModel slotData) {
        // User Detail
    }

    private void toggleRefreshing(boolean b) {

    }
}
