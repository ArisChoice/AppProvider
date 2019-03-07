package com.app.barber.util.iface;

import com.app.barber.models.ContactModel;

import java.util.Set;

/**
 * Created by harish on 28/1/19.
 */

public interface StatusCallback{
    void onFinish(Set<ContactModel> contactModelList);

    void onSuccess();

    void onFalure();
    void onStart();
}
