package com.app.barber.ui.postauth.activities.client;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.app.barber.R;
import com.app.barber.core.BarberApplication;
import com.app.barber.core.BaseActivity;
import com.app.barber.models.ContactModel;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.ui.postauth.activities.client.addclientmvp.AddClientMVPView;
import com.app.barber.ui.postauth.activities.client.addclientmvp.AddClientPresenter;
import com.app.barber.ui.postauth.activities.client.client_adapter.ContactsAdapter;
import com.app.barber.ui.postauth.fragment.ClientsFragment;
import com.app.barber.util.CommonUtils;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.util.iface.StatusCallback;
import com.app.barber.views.CustomEditText;
import com.app.barber.views.CustomTextView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import io.fabric.sdk.android.services.concurrency.AsyncTask;

/**
 * Created by harish on 28/1/19.
 */

public class MobileContactActivity extends BaseActivity implements AddClientMVPView {
    @BindView(R.id.back_toolbar)
    ImageView backToolbar;
    @BindView(R.id.txt_title_toolbar)
    CustomTextView txtTitleToolbar;
    @BindView(R.id.img_edit)
    ImageView imgEdit;
    @BindView(R.id.txt_btn)
    CustomTextView txtBtn;
    @BindView(R.id.toolbar_common)
    Toolbar toolbarCommon;
    @BindView(R.id.recyclar_view_lst)
    RecyclerView recyclarViewLst;
    @BindView(R.id.no_list_data_text)
    CustomTextView noListDataText;
    @BindView(R.id.add_customer_btn)
    CustomTextView addCustomerBtn;
    @BindView(R.id.serach_customer)
    CustomEditText serachCustomer;
    private ContactsAdapter cAdapter;
    private AddClientPresenter presenter;

    /**
     * @return layout resource id
     */
    @Override
    public int getLayoutId() {
        return R.layout.layout_phone_contact;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        txtTitleToolbar.setText(R.string.str_phone_contact);
        ((BarberApplication) getApplication()).getMyComponent(this).inject(this);
        presenter = new AddClientPresenter(this);
        presenter.attachView(this);
        initAdapter();
        initSearchFilter();

        if (Build.VERSION.SDK_INT < 23) {
            //Do not need to check the permission
            fetchContactLst();
        } else {
            if (checkAndRequestPermissions()) {
                //If you have already permitted the permission
                fetchContactLst();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
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

    private void initAdapter() {
        cAdapter = new ContactsAdapter(MobileContactActivity.this, new ArrayList<ContactModel>(), null, null, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int type, Object o) {
                switch (type) {
                    case GlobalValues.ClickOperations.DETAILS:
                        if (cAdapter.getSelectedContacts().size() > 0) {
                            addCustomerBtn.setVisibility(View.VISIBLE);
                            addCustomerBtn.setText(getString(R.string.str_add_as_customer) + " (" + cAdapter.getSelectedContacts().size() + ")");
                        } else {
                            addCustomerBtn.setVisibility(View.GONE);
                        }
                        break;
                }
            }
        });
        recyclarViewLst.setLayoutManager(new LinearLayoutManager(MobileContactActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclarViewLst.setAdapter(cAdapter);
    }

    private boolean checkAndRequestPermissions() {
        int readContact = ContextCompat.checkSelfPermission(MobileContactActivity.this, Manifest.permission.READ_CONTACTS);
        int writeContact = ContextCompat.checkSelfPermission(MobileContactActivity.this, Manifest.permission.WRITE_CONTACTS);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (readContact != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_CONTACTS);
        }
        if (writeContact != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_CONTACTS);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(MobileContactActivity.this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), GlobalValues.RequestCodes.PERMISSIONS_REQUEST_CAMERA);
            return false;
        }
        return true;
    }

    private void fetchContactLst() {
        new FetchContactList(new StatusCallback() {

            @Override
            public void onFinish(Set<ContactModel> contactModelList) {
                hideLoading();
                Log.e(" onFinish ", "  " + new Gson().toJson(contactModelList));
                new Thread() {
                    public void run() {
                        MobileContactActivity.this.runOnUiThread(() -> {
                            cAdapter.updateAll(contactModelList);
                        });
                    }
                }.start();

            }

            @Override
            public void onSuccess() {

            }

            @Override
            public void onFalure() {

            }

            @Override
            public void onStart() {
                showLoading();
            }
        }).execute();

    }

    @OnClick({R.id.back_toolbar, R.id.add_customer_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_toolbar:
                onBackPressed();
                break;
            case R.id.add_customer_btn:
                uploadContacts();
                break;

        }
    }

    private void uploadContacts() {
        presenter.addContactCustomers(NetworkConstatnts.RequestCode.API_ADD_MOBILE_CONTACTS, cAdapter.getSelectedContacts(), true);

    }

    @Override
    public void onSuccessResponse(int serviceMode, Object o) {
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_ADD_MOBILE_CONTACTS:
                ClientsFragment.NotifyCall nCall = ClientsFragment.notifyCall;
                if (nCall != null) {
                    nCall.refreshView();
                }
                onBackPressed();
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

    private class FetchContactList extends AsyncTask {
        StatusCallback statusCallback;

        public FetchContactList(StatusCallback statusCallback) {
            this.statusCallback = statusCallback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            statusCallback.onStart();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            Set<ContactModel> contactModelList = new HashSet<>();
            ContentResolver cr = getBaseContext()
                    .getContentResolver();
            Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            ContentResolver contentResolver = getContentResolver();
            String selection = ContactsContract.Contacts.HAS_PHONE_NUMBER;
            Cursor cursor = contentResolver.query(uri, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER,
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone._ID,
                            ContactsContract.Contacts._ID},
                    selection, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (cursor.isAfterLast() == false) {
                    ContactModel phContact = new ContactModel();
                    String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String contactphoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    if (contactphoneNumber != null) {
                        contactphoneNumber = contactphoneNumber.replaceAll("[\\*\\#\\()\\-\\.\\^:,]", "");
                        contactphoneNumber = contactphoneNumber.replace(" ", "");
                        if (contactphoneNumber.length() > 8) {
                            phContact.setContactName(contactName);
                            phContact.setContactNumber(contactphoneNumber);
                            contactModelList.add(phContact);
                        }
                    }
                    cursor.moveToNext();
                }
                cursor.close();
            }
            statusCallback.onFinish(contactModelList);
            return null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case GlobalValues.RequestCodes.PERMISSIONS_REQUEST_CAMERA:
                try {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        fetchContactLst();
                        //Permission Granted Successfully. Write working code here.
                    } else {
                        //You did not accept the request can not use the functionality.
                        CommonUtils.getInstance(MobileContactActivity.this).ShowToast(getString(R.string.str_permission_denied));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
