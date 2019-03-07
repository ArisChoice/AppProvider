package com.app.barber.util.thread;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;

import com.app.barber.util.GlobalValues;
import com.app.barber.util.PreferenceManager;
import com.app.barber.util.iface.OnCallBackResult;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GetLocationFromLatLong extends AsyncTask<Integer, Void, String> {


    Activity addressSelectionActivity;
    double latitude;
    double longitude;
    OnCallBackResult olist;

    public GetLocationFromLatLong(Activity addressSelectionActivity, double latitude, double longitude, OnCallBackResult olist) {
        this.addressSelectionActivity = addressSelectionActivity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.olist = olist;
    }

    @Override

    protected String doInBackground(Integer... params) {

        StringBuilder result = new StringBuilder();
        Address address = null;
        try {
            Geocoder geocoder = new Geocoder(addressSelectionActivity, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                address = addresses.get(0);
//                result.append(address.getAddressLine(0).replace(",","@"));
//                result.append(address.getLocality()).append("\n");
                if (address.getSubThoroughfare() != null && !address.getSubThoroughfare().equals("") && address.getThoroughfare() != null && !address.getThoroughfare().equals(""))
                    result.append(address.getSubThoroughfare() + "@" + address.getThoroughfare());
                else if (address.getThoroughfare() != null && !address.getThoroughfare().equals(""))
                    result.append(address.getThoroughfare());
                else if (address.getSubThoroughfare() != null && !address.getSubThoroughfare().equals(""))
                    result.append(address.getSubThoroughfare());
                else if (address.getFeatureName() != null && !address.getFeatureName().equals(""))
                    result.append(address.getFeatureName());

                result.append("," + address.getLocality() + ",");
                result.append(address.getPostalCode() + ",");
                result.append(address.getCountryName());
                olist.onResult(result.toString());
                Log.d("doInBackground", new Gson().toJson(address));
            }
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
            olist.onError(result.toString());
        }

//        new PreferenceManager().setPrefrencesString(GlobalValues.KEYS.LATITUDE, "" + latitude);
//        new PreferenceManager().setPrefrencesString(GlobalValues.KEYS.LONGITUDE, "" + longitude);
//        new PreferenceManager().setPrefrencesString(GlobalValues.KEYS.ADDRESS, "" + result.toString());
        Log.d("doInBackground -", result.toString());

        return result.toString();

    }

    @Override

    protected void onPostExecute(String result) {

    }

}