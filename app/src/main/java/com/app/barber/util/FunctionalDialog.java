package com.app.barber.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.barber.R;
import com.app.barber.models.BookingData;
import com.app.barber.models.request.AddBlockHoursRequest;
import com.app.barber.models.request.EditBookingRequestModel;
import com.app.barber.models.request.UpdateEditBookingStatusModel;
import com.app.barber.models.response.AppointmentsResponseModel;
import com.app.barber.models.response.LoginResponseModel;
import com.app.barber.models.response.RecentStatusResponseModel;
import com.app.barber.ui.postauth.activities.basic.AddressSelectionActivity;
import com.app.barber.ui.postauth.activities.basic.CalloutOpeningHoursActivity;
import com.app.barber.ui.postauth.activities.basic.PaymentTypeActivity;
import com.app.barber.ui.postauth.activities.basic.SelectZoneActivity;
import com.app.barber.ui.postauth.activities.basic.ServiceListActivity;
import com.app.barber.ui.postauth.activities.basic.WelcomeActivity;
import com.app.barber.ui.postauth.activities.home.BookingSheetFragment;
import com.app.barber.ui.postauth.activities.home.home_adapter.EditAppointmentsAdapter;
import com.app.barber.ui.postauth.activities.settings.SettingActivity;
import com.app.barber.util.iface.OnBottomDialogItemListener;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.util.weekengine.ModelDay;
import com.app.barber.util.weekengine.WeekViewAdapter;
import com.app.barber.views.CustomTextView;
import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.app.barber.core.BaseActivity.activitySwitcher;

/**
 * Created by harish on 18/12/18.
 */

public class FunctionalDialog {

    private static final String TAG = FunctionalDialog.class.getName();

    /**
     * Dialog appointment received.
     */
    public void openDialogAppointmentReceivedRequest(FragmentActivity activity, Object obj, final OnBottomDialogItemListener listener) {
        boolean isBookingCompleted;
        final Dialog mBottomSheetDialog = new Dialog(activity, R.style.MaterialDialogSheet);
        View child = activity.getLayoutInflater().inflate(R.layout.view_appointmnet_request_dialog, null);
        mBottomSheetDialog.setContentView(child);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();
        final TextView customerName = child.findViewById(R.id._about_customer_name);
        final TextView customerNumber = child.findViewById(R.id._about_customer_number);
        final TextView timeSlot = child.findViewById(R.id.booking_time_slot);
        final TextView bookingDate = child.findViewById(R.id.booking_date);
        final TextView bookingDay = child.findViewById(R.id.booking_day);
        final TextView serviceDuration = child.findViewById(R.id.booking_duration);
        final TextView bookingServices = child.findViewById(R.id.booking_services);
        final TextView bookingPrice = child.findViewById(R.id.booking_price);
        final SimpleDraweeView customerImage = child.findViewById(R.id._about_customer_image);
        final TextView completeBtn = child.findViewById(R.id.complete_btn);
        final TextView cancelBtn = child.findViewById(R.id.cancel_btn);
        final TextView statusText = child.findViewById(R.id.status_Text);
        final TextView bookingAddress = child.findViewById(R.id.booking_address);
        final ImageView messageBtn = child.findViewById(R.id.message_btn);
        final ImageView callBtn = child.findViewById(R.id.call_btn);
        final TextView distance = child.findViewById(R.id.booking_distance);
        final TextView editRequest = child.findViewById(R.id.txt_edit);
        final LinearLayout addressHolder = child.findViewById(R.id.address_holder_lay);
        final TextView messageText = child.findViewById(R.id.message_text);
        AppointmentsResponseModel.ResponseBean.BookingListBean currentData = (AppointmentsResponseModel.ResponseBean.BookingListBean) obj;
        if (currentData != null) {
            customerName.setText(currentData.getName());
            customerNumber.setText(currentData.getPhone());
            timeSlot.setText(currentData.getTimingSlot());
//            bookingDate.setText(CustomDate.formatThis(GlobalValues.DATE_FORMAT.STANDARD, currentData.getDateString()));
            customerImage.setImageURI(CommonUtils.getValidUrl(currentData.getUserImage()));
            bookingServices.setText(currentData.getServiceNames());
            bookingPrice.setText(GlobalValues.Currency.POUNDS + currentData.getAmount());
            bookingDate.setText(CustomDate.getCurrentFormat(activity, currentData.getDateString(), "MM/dd/yyyy", "EEEE,MMMM dd"));
            bookingDay.setText(CustomDate.getCurrentFormat(activity, currentData.getDateString(), "MM/dd/yyyy", "EEEE"));
            if (currentData.getBookingType() == GlobalValues.BookingTypes.CALLOUT) {
                addressHolder.setVisibility(View.VISIBLE);
                if (currentData.getAddress() != null) {
                    bookingAddress.setText(currentData.getAddress().getAddressLine1());
                    distance.setText(currentData.getDistance() + "m");
                } else bookingAddress.setText(R.string.address_not_found);

                if (currentData.isEnrouteShow()) {
                    completeBtn.setVisibility(View.VISIBLE);
                    completeBtn.setText(R.string.str_enroute);
                    completeBtn.setEnabled(true);
                    completeBtn.setBackgroundResource(R.drawable.rectangle_blue_drawable);
                } else {
                    completeBtn.setVisibility(View.VISIBLE);
                    completeBtn.setText(R.string.str_enroute);
                    completeBtn.setEnabled(false);
                    completeBtn.setBackgroundResource(R.drawable.rectangle_light_grey_drawable);
                }

            } else {
                addressHolder.setVisibility(View.GONE);
            }
            serviceDuration.setText(currentData.getTotalDuration() + " min");
            if (currentData.isCompleted()) {
                cancelBtn.setVisibility(View.GONE);
                completeBtn.setVisibility(View.GONE);
                statusText.setVisibility(View.VISIBLE);
            } else statusText.setVisibility(View.GONE);

            if (!currentData.isCompleted() && currentData.isConfirmed() && currentData.getTimeRemaining() != null &&
                    !currentData.getTimeRemaining().equals("lapsed"))
                editRequest.setVisibility(View.VISIBLE);

            //If  remaining time is less then 3 hours.
            if (CustomDate.getTimeDifferenceinHours(CustomDate.getCurrentMonth(activity, GlobalValues.DateFormats.FULL_DATE_TIME),
                    currentData.getDateString() + " " + currentData.getTimingSlot().split("-")[0], GlobalValues.DateFormats.FULL_DATE_TIME) <= 3) {
                editRequest.setVisibility(View.GONE);
            }


            if (currentData.getEditData() != null && !currentData.getEditData().equals("")) {
                editRequest.setText(R.string.str_edited);
                editRequest.setEnabled(false);
                messageText.setVisibility(View.VISIBLE);
                timeSlot.setText(currentData.getEditData().split("@")[1]);
                bookingDate.setText(CustomDate.getCurrentFormat(activity, currentData.getEditData().split("@")[0], "MM/dd/yyyy", "EEEE,MMMM dd"));
                bookingDay.setText(CustomDate.getCurrentFormat(activity, currentData.getEditData().split("@")[0], "MM/dd/yyyy", "EEEE"));

                if (currentData.getEditData().split("@")[2].equals("" + GlobalValues.UserTypes.CUSTOMER)) {
                    cancelBtn.setText(R.string.str_reject);
                    completeBtn.setText(R.string.str_accept);
                    completeBtn.setEnabled(true);
                    completeBtn.setBackgroundResource(R.drawable.rectangle_blue_drawable);
                    completeBtn.setVisibility(View.VISIBLE);
                    messageText.setText(currentData.getName() + " " + activity.getString(R.string.str_sent_you_edit_request));


                } else {
                    messageText.setText(R.string.str_you_sent_edit_request);
                    cancelBtn.setText(R.string.str_cancel_request);
                    completeBtn.setVisibility(View.GONE);
                }
            } else {
//                //If end time not reached yet hide complete button
//                if (!CustomDate.isValidTime(activity, currentData.getTimingSlot())) {
//                    completeBtn.setVisibility(View.GONE);
//                }
            }

            if (currentData.getTimeRemaining().equalsIgnoreCase("lapsed")) {//id  appointment is expired
                cancelBtn.setVisibility(View.GONE);
                completeBtn.setVisibility(View.GONE);
            }
        }
        editRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditBookingRequestModel eRequest = new EditBookingRequestModel();
                eRequest.setBarberId(currentData.getBarberId());
                eRequest.setBookingId(currentData.getId());
                eRequest.setBookingType(String.valueOf(currentData.getBookingType()));
                eRequest.setUserId(currentData.getUserId());
                BookingData bData = new BookingData();
                bData.setBookingType(String.valueOf(currentData.getBookingType()));
                bData.setTotalAmount(currentData.getAmount());
                bData.setBarberId(currentData.getBarberId());
                bData.setBookedServicesId(currentData.getServiceId());
                BookingSheetFragment bottomSheetFragment = new BookingSheetFragment();
                Bundle data = new Bundle();//create bundle instance
                data.putBoolean(GlobalValues.KEYS.IS_EDIT, true);

                data.putSerializable(GlobalValues.KEYS.EDIT_REQUEST_DATA, eRequest);//Edit request data;
                data.putSerializable(GlobalValues.KEYS.BOOKING_DATA, bData);//Booking data;
                switch (currentData.getBookingType()) {
                    case GlobalValues.BARBER_TYPES.BARBER:
                        data.putInt(GlobalValues.KEYS.BOOKING_TYPE, GlobalValues.BARBER_TYPES.BARBER);//put string to pass with a key value
                        break;
                    case GlobalValues.BARBER_TYPES.CALLOUT_BARBER:
                        data.putInt(GlobalValues.KEYS.BOOKING_TYPE, GlobalValues.BARBER_TYPES.CALLOUT_BARBER);//put string to pass with a key value
                        break;

                }
                bottomSheetFragment.setArguments(data);
                bottomSheetFragment.show(activity.getSupportFragmentManager(), bottomSheetFragment.getTag());
                mBottomSheetDialog.dismiss();
            }

        });
        bookingAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentLocationSinglton.getInstance().getCurrentLocation(activity, new CurrentLocationSinglton.CurrentLocationCallback() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            Log.d("Test", " lati " + location.getLatitude() + " long " + location.getLongitude());
                            new CommonUtils().navigateUsertoMap(activity, "" + location.getLatitude(), "" + location.getLongitude(),
                                    "" + currentData.getAddress().getLat(), "" + currentData.getAddress().getLong());
                        }
                    }

                    @Override
                    public void onFailure() {
                    }
                });
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mBottomSheetDialog.dismiss();
                if (cancelBtn.getText().toString().equals(activity.getString(R.string.str_reject))) {
                    listener.onItemClick(child, 0, GlobalValues.RequestCodes.REJECT_REQUEST, currentData);//reject edit booking request.
                } else if (cancelBtn.getText().toString().equals(activity.getString(R.string.str_cancel_request))) {
                    listener.onItemClick(child, 0, GlobalValues.RequestCodes.CANCED_REQUEST, currentData);//cancel own edit request
                } else {//cancel booking
//                    listener.onItemClick(child, 0, 0, currentData);//false
                    listener.onItemClick(child, 0, GlobalValues.RequestCodes.CANCEL, currentData);//cancel request
                }
            }
        });
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                if (completeBtn.getText().toString().equals(activity.getString(R.string.str_accept))) {
                    listener.onItemClick(child, 0, GlobalValues.RequestCodes.ACCEPT_REQUEST, currentData);//accept edit booking request.
                } else if (completeBtn.getText().toString().equals(activity.getString(R.string.str_completeed))) {
                    listener.onItemClick(child, 0, 1, currentData);//true
                } else if (completeBtn.getText().toString().equals(activity.getString(R.string.str_enroute))) {
//                    listener.onItemClick(child, 0, 1, currentData);//true
                    listener.onItemClick(child, 0, GlobalValues.RequestCodes.ENROUTE, currentData);//true
                }

            }
        });
        messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(child, 0, GlobalValues.RequestCodes.MESSAGE, currentData);//true
            }
        });
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(child, 0, GlobalValues.RequestCodes.CALL, currentData);//true
            }
        });
    }


    /**
     * Dialog appointment received.
     */
    public void openDialogCalloutReceivedRequest(Activity activity, Object obj, final OnBottomDialogItemListener listener) {
        final Dialog mBottomSheetDialog = new Dialog(activity, R.style.MaterialDialogSheet);
        View child = activity.getLayoutInflater().inflate(R.layout.view_callout_request_dialog, null);
        mBottomSheetDialog.setContentView(child);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();
        final TextView customerName = child.findViewById(R.id._about_customer_name);
        final TextView customerNumber = child.findViewById(R.id._about_customer_number);
        final TextView timeRemaining = child.findViewById(R.id.time_remaining);
        final TextView timeSlot = child.findViewById(R.id.booking_time_slot);
        final TextView serviceDuration = child.findViewById(R.id.booking_duration);
        final TextView bookingDate = child.findViewById(R.id.booking_date);
        final TextView bookingDay = child.findViewById(R.id.booking_day);
        final TextView bookingServices = child.findViewById(R.id.booking_services);
        final TextView bookingPrice = child.findViewById(R.id.booking_price);
        final SimpleDraweeView customerImage = child.findViewById(R.id._about_customer_image);
        final TextView acceptBtn = child.findViewById(R.id.accept_btn);
        final TextView rejectBtn = child.findViewById(R.id.reject_btn);
        final ImageView messageBtn = child.findViewById(R.id.message_btn);
        final ImageView callBtn = child.findViewById(R.id.call_btn);
        final TextView bookingAddress = child.findViewById(R.id.booking_address);
        final TextView distance = child.findViewById(R.id.booking_distance);
        AppointmentsResponseModel.ResponseBean.BookingListBean currentData = (AppointmentsResponseModel.ResponseBean.BookingListBean) obj;
        if (currentData != null) {
            customerName.setText(currentData.getName());
            customerNumber.setText(currentData.getPhone());
            timeSlot.setText(currentData.getTimingSlot());
//            bookingDate.setText(CustomDate.formatThis(GlobalValues.DATE_FORMAT.STANDARD, currentData.getDateString()));
            customerImage.setImageURI(CommonUtils.getValidUrl(currentData.getUserImage()));
            bookingServices.setText(currentData.getServiceNames());
            bookingPrice.setText(GlobalValues.Currency.POUNDS + currentData.getAmount());
            timeRemaining.setText(CustomDate.formatTimeRemainig(activity, currentData.getTimeRemaining()));
            bookingDate.setText(CustomDate.getCurrentFormat(activity, currentData.getDateString(), "MM/dd/yyyy", "EEEE,MMMM dd"));
            bookingDay.setText(CustomDate.getCurrentFormat(activity, currentData.getDateString(), "MM/dd/yyyy", "EEEE"));
            serviceDuration.setText(currentData.getTotalDuration() + " min");

            if (currentData.getAddress() != null) {
                bookingAddress.setText(currentData.getAddress().getAddressLine1());
                distance.setText(currentData.getDistance() + "m");
            } else bookingAddress.setText(R.string.address_not_found);

            if (timeRemaining.getText().equals(activity.getString(R.string.str_expired))) {
                acceptBtn.setVisibility(View.GONE);
                rejectBtn.setVisibility(View.GONE);
            } else {
                if (currentData.isConfirmed()) {
                    acceptBtn.setVisibility(View.VISIBLE);
                    rejectBtn.setVisibility(View.VISIBLE);
                    acceptBtn.setText(R.string.str_completed);
                    rejectBtn.setText(R.string.str_cancelled);
                }
            }
        }
        bookingAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentLocationSinglton.getInstance().getCurrentLocation(activity, new CurrentLocationSinglton.CurrentLocationCallback() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            Log.d("Test", " lati " + location.getLatitude() + " long " + location.getLongitude());
                            new CommonUtils().navigateUsertoMap(activity, "" + location.getLatitude(), "" + location.getLongitude(),
                                    "" + currentData.getAddress().getLat(), "" + currentData.getAddress().getLong());
                        }
                    }

                    @Override
                    public void onFailure() {
                    }
                });
            }
        });
        rejectBtn.setOnClickListener(v -> {
            mBottomSheetDialog.dismiss();
            listener.onItemClick(child, 0, 0, currentData);//reject,canceled
        });

        acceptBtn.setOnClickListener(v -> {
            mBottomSheetDialog.dismiss();
            listener.onItemClick(child, 0, 1, currentData);//accept,completed
        });
        messageBtn.setOnClickListener(v -> {
            listener.onItemClick(child, 0, GlobalValues.RequestCodes.MESSAGE, currentData);//true
        });
        callBtn.setOnClickListener(v -> {
            listener.onItemClick(child, 0, GlobalValues.RequestCodes.CALL, currentData);//true
        });
    }


    public static Dialog validationRequirementsDialog(FragmentActivity activity, RecentStatusResponseModel responseData,
                                                      LoginResponseModel.UserBean userData,
                                                      OnBottomDialogItemListener listner) {
        Dialog dialog = new Dialog(activity);

        // SetContentView can be set to a View can simply specify the resource ID
        // LayoutInflater
        // li=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        // View v=li.inflate(R.layout.dialog_layout, null);
        // dialog.setContentView(v);
        dialog.setContentView(R.layout.layout_basic_validation);

        dialog.setTitle(null);
        dialog.setCanceledOnTouchOutside(false);

        /*
         * Set the access window object Christmas box and parameter object to modify the layout of the dialog,
         * Can directly call getWindow (), said the Activity Window
         * Object, attribute that can change the Activity in the same way
         */
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER | Gravity.CENTER);

        dialogWindow.setAttributes(lp);

        CustomTextView addAddress = dialog.findViewById(R.id.validate_address_txt);
        CustomTextView addWorkingHours = dialog.findViewById(R.id.validate_working_hours_txt);
        CustomTextView addCalloutHours = dialog.findViewById(R.id.validate_callout_txt);
        CustomTextView addServingDistricts = dialog.findViewById(R.id.validate_district_txt);
        CustomTextView addServices = dialog.findViewById(R.id.validate_service_txt);
        CustomTextView addBarberType = dialog.findViewById(R.id.validate_barbertype_txt);
        CustomTextView addPaymentType = dialog.findViewById(R.id.validate_paymen_txt_txt);
        CustomTextView addStripe = dialog.findViewById(R.id.validate_stripe_txt);
        CustomTextView logoutBtn = dialog.findViewById(R.id.logout_btn);
        if (responseData != null) {
            if (responseData.getResponse().getValidations().isAddressAdded()) {
                addAddress.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.check, 0);
                addAddress.setEnabled(false);
            }
            if (responseData.getResponse().getValidations().isOpeningHoursAdded()) {
                addWorkingHours.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.check, 0);
                addWorkingHours.setEnabled(false);
            }
            if (responseData.getResponse().getValidations().isCallOutAdded()) {
                addCalloutHours.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.check, 0);
                addCalloutHours.setEnabled(false);
            }
            if (responseData.getResponse().getValidations().isDistrictAdded()) {
                addServingDistricts.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.check, 0);
                addServingDistricts.setEnabled(false);
            }
            if (responseData.getResponse().getValidations().isServiceAdded()) {
                addServices.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.check, 0);
                addServices.setEnabled(false);
            }
            if (!responseData.getResponse().getValidations().isZeroAmountServiceForNonTrainee()) {
                if (userData.getBarberType() != null && !userData.getBarberType().contains("3")) {
                    addServices.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                    addServices.setEnabled(true);
                }
            }
            if (responseData.getResponse().getValidations().isBarberTypeAdded()) {
                addBarberType.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.check, 0);
                addBarberType.setEnabled(false);
            }
            if (responseData.getResponse().getValidations().isPaymentTypeAdded()) {
                addPaymentType.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.check, 0);
                addPaymentType.setEnabled(false);
            }
            if (responseData.getResponse().getValidations().isStripeConnected()) {
                addStripe.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.check, 0);
                addStripe.setEnabled(false);
            }
        }
        addAddress.setOnClickListener(v -> activitySwitcher(activity, AddressSelectionActivity.class, null));
        addWorkingHours.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString(GlobalValues.KEYS.TYPE, GlobalValues.KEYS.ADD_WORKING_HOURS);
            activitySwitcher(activity, CalloutOpeningHoursActivity.class, bundle);
        });
        addCalloutHours.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString(GlobalValues.KEYS.TYPE, GlobalValues.KEYS.ADD_CALLOUT_HOURS);
            activitySwitcher(activity, CalloutOpeningHoursActivity.class, null);
        });
        addServingDistricts.setOnClickListener(v ->
        {
            Bundle bundle = new Bundle();
            bundle.putString(GlobalValues.KEYS.FROM, SettingActivity.class.getName());
            activitySwitcher(activity, SelectZoneActivity.class, bundle);
        });
        addBarberType.setOnClickListener(v -> activitySwitcher(activity, WelcomeActivity.class, null));
        addPaymentType.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString(GlobalValues.KEYS.FROM, GlobalValues.KEYS.MORE);
            activitySwitcher(activity, PaymentTypeActivity.class, bundle);
        });
        addServices.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString(GlobalValues.KEYS.FROM, SettingActivity.class.getName());
            activitySwitcher(activity, ServiceListActivity.class, bundle);
        });
        addStripe.setOnClickListener(v -> {
            new CommonUtils().ShowToast("You have to connect your stripe account to receive card payments");
            String url = GlobalValues.WEB_URL;
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            activity.startActivity(i);
        });
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                new CommonUtils().LogoutUser();
            }
        });

        dialog.show();
        return dialog;
    }

    int TIME_DIALOG_ID = 1111;
    final Calendar c = Calendar.getInstance();
    int hour;
    int minute;
    WeekViewAdapter weekAdapter;

    public void openBlockHoursSelectionsDialog(final Activity activity, boolean isWorkingHours, boolean isEdit, AddBlockHoursRequest adRequest, final OnBottomDialogItemListener listener) {
        // Current Hour
        hour = c.get(Calendar.HOUR_OF_DAY);
        // Current Minute
        minute = c.get(Calendar.MINUTE);


        final Dialog mBottomSheetDialog = new Dialog(activity, R.style.MaterialDialogSheet);
        View child = activity.getLayoutInflater().inflate(R.layout.view_block_hours_settings_dialog, null);
        mBottomSheetDialog.setContentView(child);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();
        final TextView updateBtn = child.findViewById(R.id.update_btn);
        final TextView openingTime = child.findViewById(R.id.start_hours_txt);
        final TextView closingTime = child.findViewById(R.id.end_hours_txt);
        final TextView moreDataes = child.findViewById(R.id.more_txt);
        final TextView monthName = child.findViewById(R.id.month_name);
        final RecyclerView weekRecyclar = child.findViewById(R.id.recyclar_view_lst);
        moreDataes.setVisibility(View.GONE);
        weekAdapter = new WeekViewAdapter(activity, new ArrayList<ModelDay>(), new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int type, Object o) {
                Log.e(" onItemClick ", "  -- " + weekAdapter.getAllSelectedDate());
                if (weekAdapter.getAllSelectedDate() != null) {
                    adRequest.setDate(weekAdapter.getAllSelectedDate());
                }

            }
        });

        LinearLayoutManager lManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        weekRecyclar.setLayoutManager(lManager);
        weekRecyclar.setAdapter(weekAdapter);
        weekAdapter.setCurrentWeek(35);
        weekAdapter.allowMultipleSelection();
        weekAdapter.setselectedDate(adRequest.getDate());
        weekRecyclar.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int firstVisibleItem = lManager.findFirstVisibleItemPosition();
                monthName.setText(new CustomDate().formatThis("MM/dd/yyyy", weekAdapter.getpostionData(firstVisibleItem), "MMMM yyyy"));
            }
        });
        openingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CommonUtils().showDialog(TIME_DIALOG_ID, openingTime, activity, null);
            }
        });
        closingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CommonUtils().showDialog(TIME_DIALOG_ID, closingTime, activity, null);
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (weekAdapter.getAllSelectedDate() != null && !weekAdapter.getAllSelectedDate().equals("")) {
                    mBottomSheetDialog.dismiss();
                    AddBlockHoursRequest.BlockHourListData blockHours = adRequest.new BlockHourListData();
                    blockHours.setStartTime(openingTime.getText().toString());
                    blockHours.setEndTime(closingTime.getText().toString());
                    List<AddBlockHoursRequest.BlockHourListData> bList = new ArrayList<>();
                    bList.add(blockHours);
                    adRequest.setList(bList);
                    listener.onItemClick(child, 0, GlobalValues.RequestCodes.UPDATE, adRequest);//true
                } else {
                    new CommonUtils().ShowToast(activity.getString(R.string.str_select_datee));
                }
            }
        });
    }


    public void openDateSelectionsDialog(final Activity activity, final OnBottomDialogItemListener listener) {

        final Dialog mBottomSheetDialog = new Dialog(activity, R.style.MaterialDialogSheet);
        View child = activity.getLayoutInflater().inflate(R.layout.view_date_selection_dialog, null);
        mBottomSheetDialog.setContentView(child);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();

        final DateRangeCalendarView cPicker = child.findViewById(R.id.calendar);
        final TextView confirm = child.findViewById(R.id.cnfrm_btn);
        Typeface typeface = Typeface.createFromAsset(activity.getAssets(), GlobalValues.Font.COMFORTAA_REGULAR);
        cPicker.setFonts(typeface);


        Calendar startMonth = Calendar.getInstance();
        startMonth.add(Calendar.MONTH, -0);
        Calendar endMonth = Calendar.getInstance();
        endMonth.add(Calendar.MONTH, 2);
        cPicker.setVisibleMonthRange(startMonth, endMonth);


        Calendar startSelectionDate = Calendar.getInstance();
        startSelectionDate.add(Calendar.MONTH, -2);
        Calendar endSelectionDate = (Calendar) startSelectionDate.clone();
        endSelectionDate.add(Calendar.DATE, 40);

        cPicker.setSelectedDateRange(startSelectionDate, endSelectionDate);
        cPicker.setCalendarListener(new DateRangeCalendarView.CalendarListener() {
            @Override
            public void onFirstDateSelected(Calendar startDate) {
//                Toast.makeText(activity, "Start Date: " + startDate.getTime().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDateRangeSelected(Calendar startDate, Calendar endDate) {
//                Toast.makeText(activity, "Start Date: " + startDate.getTime().toString() + " End date: " + endDate.getTime().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cPicker.getStartDate() != null && cPicker.getEndDate() != null) {
                    String startDate = null;
                    String endDate = null;
                    try {
                        startDate = cPicker.getStartDate().getTime().toString().split("\\s")[0] + " " +
                                cPicker.getStartDate().getTime().toString().split("\\s")[1] + " " +
                                cPicker.getStartDate().getTime().toString().split("\\s")[2] + " " +
                                cPicker.getStartDate().getTime().toString().split("\\s")[cPicker.getStartDate().getTime().toString().split("\\s").length - 1];
                        endDate = cPicker.getEndDate().getTime().toString().split("\\s")[0] + " " +
                                cPicker.getEndDate().getTime().toString().split("\\s")[1] + " " +
                                cPicker.getEndDate().getTime().toString().split("\\s")[2] + " " +
                                cPicker.getEndDate().getTime().toString().split("\\s")[cPicker.getEndDate().getTime().toString().split("\\s").length - 1];

                        Log.e(" ", " " + CustomDate.formatThis("EEE MMM dd yyyy",
                                cPicker.getStartDate().getTime().toString(), GlobalValues.DateFormats.DEFAULT_FORMAT_DATE));

                        startDate = CustomDate.formatThis("EEE MMM dd yyyy",
                                startDate, GlobalValues.DateFormats.DEFAULT_FORMAT_DATE);
                        endDate = CustomDate.formatThis("EEE MMM dd yyyy",
                                endDate, GlobalValues.DateFormats.DEFAULT_FORMAT_DATE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.d("onClick", "  " + startDate + "  " + endDate);
                    mBottomSheetDialog.dismiss();
                    listener.onItemClick(child, 0, GlobalValues.RequestCodes.SELECTED_DAYS, startDate + "@" + endDate);
                } else {
                    new CommonUtils().ShowToast(activity.getString(R.string.str_select_date_range));
                }

            }
        });

    }

    public AlertDialog createAppRatingDialog(Activity act) {
        AlertDialog dialog = new AlertDialog.Builder(act).setPositiveButton(act.getString(R.string.dialog_app_rate), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                openAppInPlayStore(act);

            }
        })./*setNegativeButton(act.getString(R.string.dialog_your_feedback), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                openFeedback(act);

            }
        }).*/setNeutralButton(act.getString(R.string.dialog_ask_later), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                paramAnonymousDialogInterface.dismiss();

            }
        }).setMessage(act.getString(R.string.rate_app_message)).setTitle(act.getString(R.string.rate_app_title)).create();
        return dialog;
    }

    public static void openAppInPlayStore(Activity paramContext) {
        paramContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(GlobalValues.APPLICATION_PLAYSTORE_URL_CUSTOMER)));
    }

    EditAppointmentsAdapter appAdapter;

    public Dialog openEditRequestsDialog(final Activity activity, List<AppointmentsResponseModel.ResponseBean.BookingListBean> barberEditList,
                                         LoginResponseModel.UserBean userData, final OnBottomDialogItemListener listener) {

        final Dialog mBottomSheetDialog = new Dialog(activity, R.style.MaterialDialogSheet);
        View child = activity.getLayoutInflater().inflate(R.layout.view_edit_requests_dialog, null);
        mBottomSheetDialog.setContentView(child);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();

        final RecyclerView confirm = child.findViewById(R.id.edit_recyclar_view);
        appAdapter = new EditAppointmentsAdapter((FragmentActivity) activity, null, (ArrayList<AppointmentsResponseModel.ResponseBean.BookingListBean>) barberEditList, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int type, Object o) {
                switch (type) {
                    case GlobalValues.ClickOperations.CHECK_APPOINTMENT:
                        new FunctionalDialog().openDialogAppointmentReceivedRequest((FragmentActivity) activity, o, (view1, position1, type1, t) -> {
                            AppointmentsResponseModel.ResponseBean.BookingListBean positionData = (AppointmentsResponseModel.ResponseBean.BookingListBean) o;
                            switch (type1) {
                                case 0:
//                                    presenter.updateBookingStatus(NetworkConstatnts.RequestCode.API_UPDATE_BOOKING_STATUS,
//                                            new UpdateBookingRequestModel(positionData.getId(), false, positionData.getBookingType(), getUserData().getUserID()), false);
//                                    appointmentsAdapter.remove(position1);
                                    break;
                                case 1:
//                                    presenter.updateBookingStatus(NetworkConstatnts.RequestCode.API_UPDATE_BOOKING_STATUS,
//                                            new UpdateBookingRequestModel(positionData.getId(), true, positionData.getBookingType()), false);
                                    if (CustomDate.isValidTime((FragmentActivity) activity, positionData.getTimingSlot())) {
//                                        presenter.updateBookingStatus(NetworkConstatnts.RequestCode.API_UPDATE_BOOKING_STATUS,
//                                                new UpdateBookingRequestModel(positionData.getId(), true, positionData.getBookingType(), getUserData().getUserID()), false);
//                                        listener.onItemClick(child, 0, NetworkConstatnts.RequestCode.API_UPDATE_BOOKING_STATUS, positionData);//false
                                    } else
                                        new CommonUtils().ShowToast(activity.getString(R.string.no_service_timenot_completed_yet));
                                    break;
                                case GlobalValues.RequestCodes.MESSAGE:
                                    if (positionData.getQBdialogId() != null) {
                                        listener.onItemClick(child, 1, GlobalValues.RequestCodes.MESSAGE, positionData);//true
                                    }
                                    break;
                                case GlobalValues.RequestCodes.CALL:
                                    if (positionData.getPhone() != null)
                                        listener.onItemClick(child, 1, GlobalValues.RequestCodes.CALL, (String) positionData.getPhone());//true
                                    break;
                                case GlobalValues.RequestCodes.REJECT_REQUEST:
                                    UpdateEditBookingStatusModel uRequest = new UpdateEditBookingStatusModel();
                                    uRequest.setBarberId(positionData.getBarberId());
                                    uRequest.setBookingId(positionData.getId());
                                    uRequest.setBookingType(positionData.getBookingType());
                                    uRequest.setDate(positionData.getEditData().split("@")[0]);
                                    uRequest.setTimingSlot(positionData.getEditData().split("@")[1]);
                                    uRequest.setUserId(userData.getUserID());
                                    uRequest.setEditedBy(GlobalValues.UserTypes.BARBER);
                                    uRequest.setStatus(false);
                                    listener.onItemClick(child, 1, GlobalValues.RequestCodes.REJECT_REQUEST, uRequest);//true
                                    appAdapter.remove(position);
                                    refreshAdapter(appAdapter);
                                    break;
                                case GlobalValues.RequestCodes.ACCEPT_REQUEST:
                                    uRequest = new UpdateEditBookingStatusModel();
                                    uRequest.setBarberId(positionData.getBarberId());
                                    uRequest.setBookingId(positionData.getId());
                                    uRequest.setBookingType(positionData.getBookingType());
                                    uRequest.setDate(positionData.getEditData().split("@")[0]);
                                    uRequest.setTimingSlot(positionData.getEditData().split("@")[1]);
                                    uRequest.setUserId(userData.getUserID());
                                    uRequest.setStatus(true);
                                    uRequest.setEditedBy(GlobalValues.UserTypes.BARBER);
                                    listener.onItemClick(child, 1, GlobalValues.RequestCodes.ACCEPT_REQUEST, uRequest);//true
                                    appAdapter.remove(position);
                                    refreshAdapter(appAdapter);
                                    break;
                                case GlobalValues.RequestCodes.CANCED_REQUEST:
                                    uRequest = new UpdateEditBookingStatusModel();
                                    uRequest.setBarberId(positionData.getBarberId());
                                    uRequest.setBookingId(positionData.getId());
                                    uRequest.setBookingType(positionData.getBookingType());
                                    uRequest.setDate(positionData.getEditData().split("@")[0]);
                                    uRequest.setTimingSlot(positionData.getEditData().split("@")[1]);
                                    uRequest.setUserId(userData.getUserID());
                                    uRequest.setStatus(false);
                                    uRequest.setEditedBy(GlobalValues.UserTypes.BARBER);
                                    listener.onItemClick(child, 1, GlobalValues.RequestCodes.CANCED_REQUEST, uRequest);//true
                                    appAdapter.remove(position);
                                    refreshAdapter(appAdapter);
                                    break;
                            }
                        });
                        break;
                }
            }

            private void refreshAdapter(EditAppointmentsAdapter appAdapter) {
                if (appAdapter != null && appAdapter.getItemCount() == 0) {
                    mBottomSheetDialog.dismiss();
                }
            }
        });
        confirm.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        confirm.setAdapter(appAdapter);
        return mBottomSheetDialog;
    }

    /**
     * Dialog cancel appointment.
     */


    public void openDialogCancelAppointment(FragmentActivity activity, Object obj, final OnBottomDialogItemListener listener) {
        final Dialog mBottomSheetDialog = new Dialog(activity, R.style.MaterialDialogSheet);
        View child = activity.getLayoutInflater().inflate(R.layout.view_two_buttons_dialog, null);
        mBottomSheetDialog.setContentView(child);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();
        final TextView messageText = child.findViewById(R.id.message_text);
        final TextView cancelBtn = child.findViewById(R.id.cancel_btn);
        final TextView editBtn = child.findViewById(R.id.edit_btn);
        final ImageView closeImg = child.findViewById(R.id.close_btn);

        AppointmentsResponseModel.ResponseBean.BookingListBean currentData = (AppointmentsResponseModel.ResponseBean.BookingListBean) obj;

        //Cancel  booking charges if remaining time is less than 90 min.
        if (CustomDate.getTimeDifferenceinMinutes(CustomDate.getCurrentMonth(activity, GlobalValues.DateFormats.FULL_DATE_TIME),
                currentData.getDateString() + " " + currentData.getTimingSlot().split("-")[0], GlobalValues.DateFormats.FULL_DATE_TIME) <= 90) {
            messageText.setText(R.string.str_booking_cancel_message);
            messageText.setText(activity.getString(R.string.str_you_will_be_charged) + " " +
                    GlobalValues.Currency.POUNDS + currentData.getCancellationCharge()
                    + " " + activity.getString(R.string.str_cancellation_fee));
        } else {
            messageText.setText(R.string.str_booking_cancel_message);
        }
        //Edit request button VISIBLE  only if remainig time is 3 hours.
        if (CustomDate.getTimeDifferenceinMinutes(CustomDate.getCurrentMonth(activity, GlobalValues.DateFormats.FULL_DATE_TIME),
                currentData.getDateString() + " " + currentData.getTimingSlot().split("-")[0],
                GlobalValues.DateFormats.FULL_DATE_TIME) <= 180) {
            editBtn.setVisibility(View.GONE);
        } else {
            editBtn.setVisibility(View.VISIBLE);
        }

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditBookingRequestModel eRequest = new EditBookingRequestModel();
                eRequest.setBarberId(currentData.getBarberId());
                eRequest.setBookingId(currentData.getId());
                eRequest.setBookingType(String.valueOf(currentData.getBookingType()));
                eRequest.setUserId(currentData.getUserId());
                BookingData bData = new BookingData();
                bData.setBookingType(String.valueOf(currentData.getBookingType()));
                bData.setTotalAmount(currentData.getAmount());
                bData.setBarberId(currentData.getBarberId());
                bData.setBookedServicesId(currentData.getServiceId());
                BookingSheetFragment bottomSheetFragment = new BookingSheetFragment();
                Bundle data = new Bundle();//create bundle instance
                data.putBoolean(GlobalValues.KEYS.IS_EDIT, true);

                data.putSerializable(GlobalValues.KEYS.EDIT_REQUEST_DATA, eRequest);//Edit request data;
                data.putSerializable(GlobalValues.KEYS.BOOKING_DATA, bData);//Booking data;
                switch (currentData.getBookingType()) {
                    case GlobalValues.BARBER_TYPES.BARBER:
                        data.putInt(GlobalValues.KEYS.BOOKING_TYPE, GlobalValues.BARBER_TYPES.BARBER);//put string to pass with a key value
                        break;
                    case GlobalValues.BARBER_TYPES.CALLOUT_BARBER:
                        data.putInt(GlobalValues.KEYS.BOOKING_TYPE, GlobalValues.BARBER_TYPES.CALLOUT_BARBER);//put string to pass with a key value
                        break;

                }
                bottomSheetFragment.setArguments(data);
                bottomSheetFragment.show(activity.getSupportFragmentManager(), bottomSheetFragment.getTag());
                mBottomSheetDialog.dismiss();
            }
        });
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });
        cancelBtn.setOnClickListener(v -> {
            mBottomSheetDialog.dismiss();
            listener.onItemClick(child, 0, GlobalValues.RequestCodes.CANCEL, currentData);//false
        });

    }

    long enrouteTime = 30;
    boolean timeModified;

    public void openEnrouteDialog(FragmentActivity activity, AppointmentsResponseModel.ResponseBean.BookingListBean obj
            , OnBottomDialogItemListener listener) {

        final Dialog mBottomSheetDialog = new Dialog(activity, R.style.MaterialDialogSheet);
        View child = activity.getLayoutInflater().inflate(R.layout.view_enroute_dialog, null);
        mBottomSheetDialog.setContentView(child);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();
        final TextView minusBtn = child.findViewById(R.id.minus_btn);
        final TextView addBtn = child.findViewById(R.id.add_btn);
        final TextView timeTxt = child.findViewById(R.id.timeTxt_);
        final TextView skipBtn = child.findViewById(R.id.skip_btn);
        final TextView sendBtn = child.findViewById(R.id.send_btn);

        AppointmentsResponseModel.ResponseBean.BookingListBean currentData = (AppointmentsResponseModel.ResponseBean.BookingListBean) obj;
        enrouteTime = CustomDate.getTimeDifferenceinMinutes(CustomDate.getCurrentMonth(activity, "MM/dd/yyyy hh:mm a"),
                currentData.getDateString() + " " + currentData.getTimingSlot().split("-")[0], "MM/dd/yyyy hh:mm a");
        timeTxt.setText(enrouteTime + "m");

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (enrouteTime > 0) {
                    enrouteTime--;
                    timeTxt.setText(enrouteTime + "m");
                    timeModified = true;
                }
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enrouteTime++;
                timeTxt.setText(enrouteTime + "m");
                timeModified = true;
            }
        });


        skipBtn.setOnClickListener(v -> {
            mBottomSheetDialog.dismiss();
            listener.onItemClick(child, 0, GlobalValues.RequestCodes.SKIP_IT, null);//false
        });

        sendBtn.setOnClickListener(v -> {
            if (timeModified) {
                mBottomSheetDialog.dismiss();
                listener.onItemClick(child, 0, GlobalValues.RequestCodes.SEND, enrouteTime);//false

            } else {
                new CommonUtils().ShowToast(activity.getString(R.string.enrout_time));
            }
        });

    }
}
