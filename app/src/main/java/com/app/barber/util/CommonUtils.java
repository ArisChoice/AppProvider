package com.app.barber.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.app.barber.R;
import com.app.barber.core.BarberApplication;
import com.app.barber.models.DurationModel;
import com.app.barber.models.TimeSlotsModel;
import com.app.barber.models.request.AddServiceModel;
import com.app.barber.models.response.LoginResponseModel;
import com.app.barber.models.response.ServiceListResponseModel;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.ui.adapters.DaysAdapter;
import com.app.barber.ui.adapters.DurartionAdapter;
import com.app.barber.ui.adapters.HoursAdapter;
import com.app.barber.ui.adapters.OpenBottonDialogAdapter;
import com.app.barber.ui.preauth.activities.SplashActivity;
import com.app.barber.util.iface.OnBottomDialogItemListener;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.views.WheelView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by harish on 23/8/18.
 */

public class CommonUtils {
    private static final String TAG = CommonUtils.class.getSimpleName();
    private static Context context = BarberApplication.getInstance();
    private static CommonUtils ourInstance;


    public static CommonUtils getInstance(Context context) {
        CommonUtils.context = context;
        if (ourInstance == null) {
            ourInstance = new CommonUtils();
        }
        return ourInstance;
    }


    public static void hideView(View v) {
        v.setVisibility(View.GONE);
    }

    public static void showView(View v) {
        v.setVisibility(View.VISIBLE);
    }

    @SuppressLint("all")
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String getTimestamp() {
        return new SimpleDateFormat(GlobalValues.TIMESTAMP_FORMAT, Locale.US).format(new Date());
    }

    public static long getTimestampOther() {
        return System.currentTimeMillis() / 10000;
    }

    public static boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isEmpty(EditText editText) {
        if (editText.getText().toString().trim().contentEquals("") || editText.getText().toString().trim() == null) {
            editText.requestFocus();
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNumber(String trim) {
        boolean isPhoneNumber = false;
        try {
            Long.parseLong(trim);
            isPhoneNumber = true;
            return true;
        } catch (Exception e) {
//            e.printStackTrace();
            isPhoneNumber = false;
            return false;
        }
    }

    /**
     * Description : Toast with Message String as input
     */
    Toast toast;

    public void ShowToast(String msg) {
        try {
            if (toast != null)
                toast.cancel();
            if (msg != null && !msg.trim().equalsIgnoreCase("")) {
                toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
                toast.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Description : Validates the email
     *
     * @param editText
     * @return true : if email is valid
     */
    public boolean isValidEmail(EditText editText) {
        Pattern pattern;
        Matcher matcher;
        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(editText.getText().toString());
        if (matcher.matches()) {
            return true;
        } else {
            ShowToast(context.getString(R.string.error_toast_invalid_email));
            return false;
        }
    }

    /**
     * @param context
     * @desc to check weather app is in
     * background or not.
     * *
     */
    public static boolean isAppInBackground(Context context) {

        boolean isInBackground = true;
        try {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
                List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
                for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                    if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        for (String activeProcess : processInfo.pkgList) {
                            if (activeProcess.equals(context.getPackageName())) {
                                isInBackground = false;
                            }
                        }
                    }
                }
            } else {
                List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
                ComponentName componentInfo = taskInfo.get(0).topActivity;
                if (componentInfo.getPackageName().equals(context.getPackageName())) {
                    isInBackground = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isInBackground;
    }

    public static String getText(EditText editText) {
        return editText.getText().toString();
    }

    /**
     * get unique device id
     */

    @SuppressLint("MissingPermission")
    public static String getUniqueDeviceId(Context context) {

        String deviceId;
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            final String tmDevice, tmSerial, androidId;
            tmDevice = "" + tm.getDeviceId();
            tmSerial = "" + tm.getSimSerialNumber();
            androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

            UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
            deviceId = deviceUuid.toString();
        } catch (Exception e) {
            deviceId = tm.getDeviceId();
        }
        return deviceId;
    }

    public void openDialog(Activity activity, final String[] name, int[] icons, final OnBottomDialogItemListener listener) {
        final Dialog mBottomSheetDialog = new Dialog(activity, R.style.MaterialDialogSheet);
        View child = activity.getLayoutInflater().inflate(R.layout.view_image_pick_dialog, null);
        mBottomSheetDialog.setContentView(child);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();
        RecyclerView list_view = child.findViewById(R.id.list_view);
        list_view.setLayoutManager(new LinearLayoutManager(activity));
        list_view.setAdapter(new OpenBottonDialogAdapter(name, icons, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int type, Object o) {
                switch (type) {
                    case GlobalValues.ClickOperations.APAPTER_BOTTOM_DIALOG_CLICK:
                        String s = (String) o;
                        Log.e("Data ", s);
                        listener.onItemClick(view, position, type, o);
                        mBottomSheetDialog.dismiss();
                        break;
                }
            }
        }));
    }

    private String[] TIME;

    public void openDialogTimePicker(Activity activity, String icons, final OnBottomDialogItemListener listener) {
        final Dialog mBottomSheetDialog = new Dialog(activity, R.style.MaterialDialogSheet);
        View child = activity.getLayoutInflater().inflate(R.layout.view_time_pick_dialog, null);
        mBottomSheetDialog.setContentView(child);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();
//        TIME = getList();
        final WheelView wheelOne = child.findViewById(R.id.wheel_view_one);
        final WheelView wheelTwo = child.findViewById(R.id.wheel_view_two);
        final TextView saveBtn = child.findViewById(R.id.save_Btn);
        final TextView cancelBtn = child.findViewById(R.id.cancel_Btn);
        wheelOne.setOffset(1);
        wheelOne.setItems(getList());
        wheelOne.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d(TAG, "selectedIndex: " + selectedIndex + ", item: " + item);
            }
        });
        wheelTwo.setOffset(1);
        wheelTwo.setItems(getList());
        wheelTwo.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d(TAG, "selectedIndex: " + selectedIndex + ", item: " + item);

            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeSlotsModel timeModel = new TimeSlotsModel();
                timeModel.setOpeningHours(wheelOne.getSeletedItem());
                timeModel.setClosingHours(wheelTwo.getSeletedItem());
                mBottomSheetDialog.dismiss();
                listener.onItemClick(wheelTwo, 0, 1, timeModel);
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                listener.onItemClick(wheelTwo, 0, 2, null);
            }
        });
    }


    public void openDialogAdvanceHoursPicker(Activity activity, String icons, final OnBottomDialogItemListener listener) {
        final Dialog mBottomSheetDialog = new Dialog(activity, R.style.MaterialDialogSheet);
        View child = activity.getLayoutInflater().inflate(R.layout.view_hours_pick_dialog, null);
        mBottomSheetDialog.setContentView(child);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();
        final TextView saveBtn = child.findViewById(R.id.dialog_save_btn);
//        TIME = getList();
        final WheelView wheelOne = child.findViewById(R.id.wheel_view_one);
        wheelOne.setOffset(1);
        wheelOne.setItems(getListhours(activity));
        wheelOne.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d(TAG, "selectedIndex: " + selectedIndex + ", item: " + item);
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                //  listener.onItemClick(wheelTwo, 0, 1, timeModel);
            }
        });
    }

    int serviceType;

    public void openAddUpdateServiceDialog(Activity activity, LoginResponseModel.UserBean userData, final ServiceListResponseModel.ResponseBean adMdl, final OnBottomDialogItemListener listener) {
        final Dialog mBottomSheetDialog = new Dialog(activity, R.style.MaterialDialogSheet);
        View child = activity.getLayoutInflater().inflate(R.layout.view_addservice_dialog, null);
        mBottomSheetDialog.setContentView(child);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();

        final TextView cancelBtn = child.findViewById(R.id.cancel_btn_dialog);
        final EditText serviceName = child.findViewById(R.id.service_name);
        final EditText serviceCost = child.findViewById(R.id.service_cost);
        final TextView updateBtn = child.findViewById(R.id.update_btn_dialog);
        final TextView removeBtn = child.findViewById(R.id.service_remove);
        final TextView calloutServiceBtn = child.findViewById(R.id.callout_service_btn);
        final TextView walkinServiceBtn = child.findViewById(R.id.walkin_service_btn);

        final RecyclerView recyclarViewLst = child.findViewById(R.id.recyclar_view_lst);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        recyclarViewLst.setLayoutManager(layoutManager);
        final DurartionAdapter durationAdapter = new DurartionAdapter(activity, (List<DurationModel>) getDurations(), new OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int positio, int type, Object o) {
                switch (type) {

                }
            }
        });
        recyclarViewLst.setAdapter(durationAdapter);
        if (adMdl != null) {
            serviceName.setText(adMdl.getServiceName());
            serviceCost.setText("" + adMdl.getPrice());
            durationAdapter.setSelected(adMdl.getDuration());
            removeBtn.setVisibility(View.VISIBLE);
            serviceType = adMdl.getType();
            if (adMdl.getType() == 2) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        calloutServiceBtn.performClick();
                    }
                }, GlobalValues.TIME_DURATIONS.SMALL);
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        walkinServiceBtn.performClick();
                    }
                }, GlobalValues.TIME_DURATIONS.SMALL);
            }
        }

        if (new Gson().fromJson(new PreferenceManager().getUserData(), LoginResponseModel.UserBean.class).getBarberType()
                != null && new Gson().fromJson(new PreferenceManager().getUserData(), LoginResponseModel.UserBean.class).getBarberType().contains("3")) {
            serviceCost.setText("0");
            serviceCost.setFocusable(false);
            serviceCost.setFocusableInTouchMode(false);
        }
        calloutServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceType = 2;
                calloutServiceBtn.setTextColor(activity.getResources().getColor(R.color.color_white));
                walkinServiceBtn.setTextColor(activity.getResources().getColor(R.color.color_grey));

                calloutServiceBtn.setBackgroundResource(R.drawable.rectangle_blue_drawable);
                walkinServiceBtn.setBackgroundResource(R.drawable.rectangle_white_background);
                Log.e("onClick", " " + serviceType);

            }
        });
        walkinServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceType = 1;
                walkinServiceBtn.setTextColor(activity.getResources().getColor(R.color.color_white));
                calloutServiceBtn.setTextColor(activity.getResources().getColor(R.color.color_grey));

                walkinServiceBtn.setBackgroundResource(R.drawable.rectangle_blue_drawable);
                calloutServiceBtn.setBackgroundResource(R.drawable.rectangle_white_background);
                Log.e("onClick", " " + serviceType);
            }
        });
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                listener.onItemClick(null, 0, GlobalValues.RequestCodes.REMOVE, adMdl);
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                //  listener.onItemClick(wheelTwo, 0, 1, timeModel);
            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (serviceName.getText().toString().trim().length() > 0 && serviceCost.getText().toString().trim().length() > 0 && durationAdapter.isSelected()) {
                    AddServiceModel addMdl = new AddServiceModel();
                    addMdl.setServiceName(serviceName.getText().toString());
                    addMdl.setDuration(Integer.parseInt(durationAdapter.getSelectedSlot().replace("min", "")));
                    addMdl.setPrice(serviceCost.getText().toString());
                    addMdl.setPriceType("fixed");
                    addMdl.setType(serviceType);
                    try {
                        if (adMdl != null && adMdl.getId() != 0) {
                            addMdl.setId(String.valueOf(adMdl.getId()));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (userData != null && userData.getBarberType() != null && !userData.getBarberType().contains("3")
                            && Integer.parseInt(serviceCost.getText().toString()) <= 0) {
                        ShowToast(activity.getString(R.string.str_error_service_amount));
                        return;
                    } else {
                        mBottomSheetDialog.dismiss();
                        listener.onItemClick(null, 0, 1, addMdl);
                    }

                } else ShowToast(activity.getString(R.string.str_error_required_field));
            }
        });
    }

    int TIME_DIALOG_ID = 1111;
    final Calendar c = Calendar.getInstance();
    int hour;
    int minute;
    private HoursAdapter hoursAdapter;

    public void openHoursSelectionsDialog(final Activity activity, boolean isWorkingHours, boolean isEdit, final TimeSlotsModel o, final OnBottomDialogItemListener listener) {
        // Current Hour
        hour = c.get(Calendar.HOUR_OF_DAY);
        // Current Minute
        minute = c.get(Calendar.MINUTE);
        final Dialog mBottomSheetDialog = new Dialog(activity, R.style.MaterialDialogSheet);
        View child = activity.getLayoutInflater().inflate(R.layout.view_hours_settings_dialog, null);
        mBottomSheetDialog.setContentView(child);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();
        final TextView updateBtn = child.findViewById(R.id.update_btn);
        final TextView openingTime = child.findViewById(R.id.start_hours_txt);
        final TextView closingTime = child.findViewById(R.id.end_hours_txt);
        final Switch workingHours = child.findViewById(R.id.switch_working_hours);
//        final Switch breakHours = child.findViewById(R.id.switch_breakhour);
        final RecyclerView recyclarViewLst = child.findViewById(R.id.recyclar_view_lst);
        final LinearLayout breakHOursContainer = child.findViewById(R.id.break_hours_container);
        final LinearLayout workingHrsLay = child.findViewById(R.id.slot_View_Lay);
        /*if (isWorkingHours) {//disable break hours for callout timings.
            breakHOursContainer.setVisibility(View.VISIBLE);
        } else breakHOursContainer.setVisibility(View.GONE);
*/
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        recyclarViewLst.setLayoutManager(layoutManager);
        DaysAdapter dayAdapter = new DaysAdapter(activity, isEdit, (List<TimeSlotsModel>) getDays(), new OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int positio, int type, Object o) {
                switch (type) {
                }
            }
        });
        recyclarViewLst.setAdapter(dayAdapter);
        dayAdapter.updatePosition(o);
        openingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID, openingTime, activity, null);
            }
        });
        closingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID, closingTime, activity, null);
            }
        });
//        Log.e("openHoursSelectionsDialog  ", "  " + new Gson().toJson(o));
        final RecyclerView breakRecuclar = child.findViewById(R.id.break_hours_recyclar);
        layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        breakRecuclar.setLayoutManager(layoutManager);
        hoursAdapter = new HoursAdapter(activity, (List<TimeSlotsModel.BreakHoursList>) getHours(), o.getDay(), new OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int positio, int type, Object o1) {
                switch (type) {
                    case GlobalValues.ClickOperations.ADD_START_TIME:
                        break;
                    case GlobalValues.ClickOperations.ADD_END_TIME:
                        break;
                    case GlobalValues.ClickOperations.REMOVE:
                        if (((TimeSlotsModel.BreakHoursList) o1).getId() != 0) {
                            listener.onItemClick(view, positio, type, ((TimeSlotsModel.BreakHoursList) o1).getId());
                            hoursAdapter.removePosition(positio);
                        } else {
                            hoursAdapter.removePosition(positio);
                        }
                        break;
                }
            }
        });
        breakRecuclar.setAdapter(hoursAdapter);
        if (o != null && o.getOpeningHours() != null) {
            openingTime.setText(o.getOpeningHours());
            closingTime.setText(o.getClosingHours());
            hoursAdapter.updateAll(o.getBreakHours());
            if (o.isClosed())
                workingHours.setChecked(true);

        }
        final LinearLayout breakLayout = child.findViewById(R.id.add_break_layout);
        breakLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hoursAdapter.getItemCount() < 2)
                    hoursAdapter.addItem(new TimeSlotsModel.BreakHoursList());
                else breakLayout.setVisibility(View.GONE);
            }
        });
        workingHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isWorkingHours) {//working hours
                    if (workingHours.isChecked()) {
                        workingHrsLay.setVisibility(View.VISIBLE);
                        breakHOursContainer.setVisibility(View.VISIBLE);
                    } else {
                        workingHrsLay.setVisibility(View.GONE);
                        breakHOursContainer.setVisibility(View.GONE);
                    }
                } else {//callout hours
                    if (workingHours.isChecked()) {
                        workingHrsLay.setVisibility(View.VISIBLE);

                    } else {
                        workingHrsLay.setVisibility(View.GONE);
                    }
                }

            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!openingTime.equals("00:00")) {
                    if (timeValidation(openingTime.getText().toString(), closingTime.getText().toString())) {
                        mBottomSheetDialog.dismiss();
                        o.setOpeningHours(openingTime.getText().toString());
                        o.setClosingHours(closingTime.getText().toString());
                        o.setId(o.getId());
                        o.setBreakHours(hoursAdapter.getItems());
                        o.setDay(checkMultiple(dayAdapter));
                        o.setClosed(workingHours.isChecked());
                        Log.e("  hoursAdapter  ", "  " + new Gson().toJson(o));
                        listener.onItemClick(null, 0, 1, o);
                    } else {
                        ShowToast(activity.getString(R.string.error_invalid_time));
                    }
                }
            }
        });
    }

    private String checkMultiple(DaysAdapter dayAdapter) {
        Log.e("  checkMultiple  ", "  " + dayAdapter.getSelectedDays());
        return dayAdapter.getSelectedDays();
    }

    private boolean timeValidation(String time, String endtime) {

        String pattern = "h:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date date1 = sdf.parse(time);
            Date date2 = sdf.parse(endtime);

            if (date1.before(date2)) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void showDialog(int time_dialog_id, final TextView vTxt, Activity activity, final Object o) {

        // set time picker as current time
        final TimePickerDialog dialog = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
                hour = hourOfDay;
                minute = minutes;
                vTxt.setText(updateTime(hour, minute));
                if (o != null) {
                    ((OnItemClickListener) o).onItemClick(vTxt, 0, GlobalValues.ClickOperations.ADD_START_TIME, (String) vTxt.getText().toString());
                }
            }
        }, hour, minute,
                false);
        dialog.show();

    }

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            // TODO Auto-generated method stub
            hour = hourOfDay;
            minute = minutes;

            updateTime(hour, minute);

        }

    };

    // Used to convert 24hr format to 12hr format with AM/PM values
    private String updateTime(int hours, int mins) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";


        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();

        return aTime;
    }

    private ArrayList<TimeSlotsModel.BreakHoursList> getHours() {
        ArrayList<TimeSlotsModel.BreakHoursList> arrayList = new ArrayList<>();

        return arrayList;
    }

    private ArrayList<TimeSlotsModel> getDays() {
        ArrayList<TimeSlotsModel> arrayList = new ArrayList<>();
        String[] name = context.getResources().getStringArray(R.array.days_names);
        for (int i = 0; i < name.length; i++) {
            TimeSlotsModel model = new TimeSlotsModel();
            model.setDay(name[i]);
            model.setOpeningHours(null);
            model.setClosingHours(null);
            arrayList.add(model);
        }

        return arrayList;
    }

    private Collection<? extends DurationModel> getDurations() {
        ArrayList<DurationModel> durationData = new ArrayList<>();
        DurationModel model = new DurationModel();

        model = new DurationModel();
        model.setSelected(false);
        model.setTime("5min");
        durationData.add(model);

        model = new DurationModel();
        model.setSelected(false);
        model.setTime("10min");
        durationData.add(model);

        model = new DurationModel();
        model.setSelected(false);
        model.setTime("15min");
        durationData.add(model);

        model = new DurationModel();
        model.setSelected(false);
        model.setTime("20min");
        durationData.add(model);

        model = new DurationModel();
        model.setSelected(false);
        model.setTime("25min");
        durationData.add(model);

        model = new DurationModel();
        model.setSelected(false);
        model.setTime("30min");
        durationData.add(model);

        model = new DurationModel();
        model.setSelected(false);
        model.setTime("40min");
        durationData.add(model);

        model = new DurationModel();
        model.setSelected(false);
        model.setTime("50min");
        durationData.add(model);

        model = new DurationModel();
        model.setSelected(false);
        model.setTime("60min");
        durationData.add(model);

        model = new DurationModel();
        model.setSelected(false);
        model.setTime("90min");
        durationData.add(model);

        model = new DurationModel();
        model.setSelected(false);
        model.setTime("120min");
        durationData.add(model);

        return durationData;
    }

    private List<String> getListhours(Activity activity) {
        ArrayList<String> time = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            time.add(activity.getString(R.string.str_up_to) + " " + i + " " + activity.getString(R.string.str_hour_in_adv));
        }
        return time;
    }

    private ArrayList<String> getList() {
        ArrayList<String> time = new ArrayList<>();
        String timeValue = "2018-10-28T00:37:04.899+05:30";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        try {
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(sdf.parse(timeValue));

            if (startCalendar.get(Calendar.MINUTE) < 30) {
                startCalendar.set(Calendar.MINUTE, 30);
            } else {
                startCalendar.add(Calendar.MINUTE, 30); // overstep hour and clear minutes
                startCalendar.clear(Calendar.MINUTE);
            }

            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(startCalendar.getTime());

            // if you want dates for whole next day, uncomment next line
            //endCalendar.add(Calendar.DAY_OF_YEAR, 1);
            endCalendar.add(Calendar.HOUR_OF_DAY, 24 - startCalendar.get(Calendar.HOUR_OF_DAY));

            endCalendar.clear(Calendar.MINUTE);
            endCalendar.clear(Calendar.SECOND);
            endCalendar.clear(Calendar.MILLISECOND);

            SimpleDateFormat slotTime = new SimpleDateFormat("hh:mm a");
//            SimpleDateFormat slotDate = new SimpleDateFormat(", dd/MM/yy");
            while (endCalendar.after(startCalendar)) {
                String slotStartTime = slotTime.format(startCalendar.getTime());
//                String slotStartDate = slotDate.format(startCalendar.getTime());

                startCalendar.add(Calendar.MINUTE, 5);
                String slotEndTime = slotTime.format(startCalendar.getTime());
                time.add(slotStartTime);
                Log.d("DATE", slotStartTime + " - " + slotEndTime);
            }

        } catch (ParseException e) {
            // date in wrong format
        }

        return time;
    }


    public void getKeyHash() {//3pWvky8jAayZrBrP7iZm/US+hck=(debug)
        // add_ code to print out the key hash
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    "com.app.rum_a",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    public void LogoutUser() {
        CommonUtils.getInstance(context).ShowToast(context.getString(R.string.str_session_expired));
        /*Thread thread = new Thread(() -> {
            try {
                try {
                    QBChatService.getInstance().logout();
                    new PreferenceManager().removeQbUser();
                } catch (SmackException.NotConnectedException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();*/
        new PreferenceManager().setPrefrencesBoolean(GlobalValues.KEYS.isLogedIn, false);
        new PreferenceManager().clearUserData();
        Intent intent = new Intent(context, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static String getValidUrl(String imageURL) {
        return NetworkConstatnts.URL.BASE_URL + imageURL;
    }


    public static Point getDisplaySize(WindowManager windowManager) {
        try {
            if (Build.VERSION.SDK_INT > 16) {
                Display display = windowManager.getDefaultDisplay();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                display.getMetrics(displayMetrics);
                return new Point(displayMetrics.widthPixels, displayMetrics.heightPixels);
            } else {
                return new Point(0, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Point(0, 0);
        }
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }


    public String getDevideGCMid() {
        String mypreferenceNew = "mypref_Token";
        //GET THIS TOKEN FROM PREF
        SharedPreferences sharedpreferences = context.getSharedPreferences(mypreferenceNew, Context.MODE_PRIVATE);
        String deviceId = sharedpreferences.getString("pushToken", "");
        Log.e(TAG, " Refreshed token  ::: sendRegistrationToServer  =====> " + sharedpreferences.getString("pushToken", ""));
        return deviceId;
    }

    //  Used in Message screen activity & Chat screen Activity.
    public static boolean IsChatActive = false;

    public static boolean getIsChatActive() {
        return IsChatActive;
    }

    public boolean setIsChatActive(boolean IsChatActive) {
        CommonUtils.IsChatActive = IsChatActive;
        return IsChatActive;
    }

    public void displayMessage(Activity aCtivty, String toastString) {
        Log.e("displayMessage", "" + toastString);
        try {
        /*Snackbar.make(getCurrentFocus(), toastString, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();*/
            try {
                if (toastString == null || toastString.equals("")) {
                    toastString = "Some error occured";
                }
            } catch (Exception e) {
                toastString = "Some error occured";
            }
            Snackbar snackbar = Snackbar.make(aCtivty.getCurrentFocus(), toastString, Snackbar.LENGTH_SHORT);
            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(context.getResources().getColor(R.color.color_white));
            snackbar.show();
        } catch (Exception e) {

        }
    }

    public String getStartHours(String timingSlot) {
        Log.e(" getStartHours ", "" + timingSlot.split("-")[0].split(":")[0]);
        timingSlot = timingSlot.split("-")[0].split(":")[0] + " " + timingSlot.split("-")[0].split(":")[1].split(" ")[1];
        return timingSlot;
    }

    public static String formatDecimalPoint(String avgRating, int i) {
        String valueA = avgRating;
        Log.d("formatDecimalPoint", "  " + Float.valueOf(avgRating));
        try {
            switch (i) {
                case 1:
                    valueA = String.format("%.1f", Float.valueOf(avgRating));
                    break;
                case 2:
                    valueA = String.format("%.2f", Float.valueOf(avgRating));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "0.0";
        }

        return valueA;
    }

    public void navigateUsertoMap(Activity activity, String startlat, String startlong, String endlat, String endlong) {
//        Intent navigation = new Intent(
//                Intent.ACTION_VIEW,
//                Uri.parse("http://maps.google.com/maps?saddr=" + startlat + "," + startlong + "&daddr=+" + endlat + "," + endlong+ " (" + "Client Address" + ")"));
//        activity.startActivity(navigation);
        String strUri = "http://maps.google.com/maps?q=loc:" + startlat + "," + startlong + "&daddr=+" + endlat + "," + endlong;
//        String strUri = "http://maps.google.com/maps?q=loc:" + location.getLatitude() + "," + location.getLongitude() + " (" + responseModel.getFullName() + ")";
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        activity.startActivity(intent);


    }

    public String getCurrentDate() {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String date = df.format(Calendar.getInstance().getTime());
        return date;
    }

    public String getNewDate(int position) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");// HH:mm:ss");
        String reg_date = df.format(c.getTime());
//        showtoast("Currrent Date Time : "+reg_date);
        Log.d("getNewDate", "C " + reg_date + " " + position);
        c.add(Calendar.DATE, position);  // number of days to add
        String end_date = df.format(c.getTime());
        Log.d("getNewDate", "E " + end_date);
//        showtoast("end Time : "+end_date);
        return end_date;
    }


    public Dialog FullImageScreem(Activity activity, String imageUrl) {
        final Dialog dialog = new Dialog(activity, android.R.style.Theme_Holo_NoActionBar_Fullscreen);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme; //style idsetIsChatActive
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.full_image_view_layout);
        dialog.setCancelable(true);
        SimpleDraweeView fullImage = (SimpleDraweeView) dialog.findViewById(R.id._full_image);
        try {
//            new ImageUtility(activity).LoadImage(CommonUtils.getValidUrl(imageUrl), fullImage);
            fullImage.setImageURI(Uri.parse(imageUrl));
        } catch (Exception e) {
//            e.printStackTrace();
        }
        ImageView backImage = dialog.findViewById(R.id.back_toolbar);
        backImage.bringToFront();
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        return dialog;
    }

    public String getSupportChatDialog(LoginResponseModel.UserBean userData) {
        return userData.getUserID() + "" + GlobalValues.SUPPORT_ADMIN_ID;
    }

    //  Used in Message screen activity & Chat screen Activity.
    public static boolean IsSupportChatActive = false;

    public static boolean getIsSupportChatActive() {
        return IsSupportChatActive;
    }

    public boolean setIsSupportChatActive(boolean IsChatActive) {
        CommonUtils.IsSupportChatActive = IsChatActive;
        return IsChatActive;
    }
}
