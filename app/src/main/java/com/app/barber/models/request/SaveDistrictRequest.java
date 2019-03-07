package com.app.barber.models.request;

/**
 * Created by harish on 21/1/19.
 */

public class SaveDistrictRequest {
    public SaveDistrictRequest(String DistrictId, boolean isChecked) {
        this.DistrictId = DistrictId;
        this.isChecked = isChecked;
    }

    String DistrictId;
    boolean isChecked;
}
