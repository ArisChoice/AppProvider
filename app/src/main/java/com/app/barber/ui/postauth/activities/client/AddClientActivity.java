package com.app.barber.ui.postauth.activities.client;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.app.barber.BuildConfig;
import com.app.barber.R;
import com.app.barber.core.BarberApplication;
import com.app.barber.core.BaseActivity;
import com.app.barber.models.request.AddClientRequestModel;
import com.app.barber.models.response.BaseResponse;
import com.app.barber.models.response.ClientsListResponseModel;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.ui.postauth.activities.basic.AddWorkspacePhotos;
import com.app.barber.ui.postauth.activities.basic.AddressActivity;
import com.app.barber.ui.postauth.activities.basic.AddressSelectionActivity;
import com.app.barber.ui.postauth.activities.client.addclientmvp.AddClientMVPView;
import com.app.barber.ui.postauth.activities.client.addclientmvp.AddClientPresenter;
import com.app.barber.ui.postauth.activities.home.EditProfileActivity;
import com.app.barber.ui.postauth.activities.home.homemvp.HomeAuthPresenter;
import com.app.barber.ui.postauth.fragment.ClientsFragment;
import com.app.barber.util.CommonUtils;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.ImageUtility;
import com.app.barber.util.iface.OnBottomDialogItemListener;
import com.app.barber.views.CustomEditText;
import com.app.barber.views.CustomTextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.internal.PlaceEntity;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by harish on 7/1/19.
 */

public class AddClientActivity extends BaseActivity implements AddClientMVPView {
    @BindView(R.id.back_toolbar)
    ImageView backToolbar;
    @BindView(R.id.txt_title_toolbar)
    CustomTextView txtTitleToolbar;
    @BindView(R.id.img_edit)
    ImageView imgEdit;
    @BindView(R.id.toolbar_common)
    Toolbar toolbarCommon;
    @BindView(R.id.client_image)
    SimpleDraweeView clientImage;
    @BindView(R.id.add_client_image)
    ImageView addClientImage;
    @BindView(R.id.full_name_edtxt)
    CustomEditText fullNameEdtxt;
    @BindView(R.id.email_edtxt)
    CustomEditText emailEdtxt;
    @BindView(R.id.number_edtxt)
    CustomEditText numberEdtxt;
    @BindView(R.id.address_edtxt)
    CustomEditText addressEdtxt;
    @BindView(R.id.save_btn)
    CustomTextView saveBtn;
    private String outputFile = null;
    private AddClientPresenter presenter;

    /**
     * @return layout resource id
     */
    @Override
    public int getLayoutId() {
        return R.layout.add_client_screen;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BarberApplication) getApplication()).getMyComponent(this).inject(this);
        presenter = new AddClientPresenter(this);
        presenter.attachView(this);
        txtTitleToolbar.setText(R.string.add_client);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @OnClick({R.id.back_toolbar, R.id.address_edtxt, R.id.save_btn, R.id.add_client_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_toolbar:
                onBackPressed();
                break;
            case R.id.address_edtxt:
                selectAddress();
                break;
            case R.id.save_btn:
                if (validated()) {
                    saveClient();
                }
                break;
            case R.id.add_client_image:
                imagePickCall();
                break;
        }
    }

    private void selectAddress() {
        double currentLat = 0.0;
        double currentLong = 0.0;
        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            if (mPref.getPrefrencesString(GlobalValues.KEYS.LATITUDE) != null) {
                currentLat = Double.parseDouble(mPref.getPrefrencesString(GlobalValues.KEYS.LATITUDE));
                currentLong = Double.parseDouble(mPref.getPrefrencesString(GlobalValues.KEYS.LONGITUDE));
            }
            try {
                LatLng latLng = new LatLng(currentLat, currentLong);
                LatLngBounds latLngBounds = new LatLngBounds(latLng, latLng);
                builder.setLatLngBounds(latLngBounds);
            } catch (Exception e) {

            }
            startActivityForResult(builder.build(this), GlobalValues.RequestCodes.LOCATION_PICKER);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }

    }

    private void saveClient() {
        List<MultipartBody.Part> list = new ArrayList<MultipartBody.Part>(5);
        Map<String, RequestBody> params = new HashMap<>();
        // add_ Parameter
        params.put("Name", RequestBody.create(MediaType.parse("text/plain"), fullNameEdtxt.getText().toString()));
        params.put("Email", RequestBody.create(MediaType.parse("text/plain"), emailEdtxt.getText().toString()));
        params.put("Contact", RequestBody.create(MediaType.parse("text/plain"), numberEdtxt.getText().toString()));
        params.put("Address", RequestBody.create(MediaType.parse("text/plain"), addressEdtxt.getText().toString()));
        params.put("Id", RequestBody.create(MediaType.parse("text/plain"), "0"));

        MultipartBody.Part partData = null;
        if (outputFile != null) {
            File file = new File(outputFile);
            partData = MultipartBody.Part.
                    createFormData("image", file.getName(),
                            RequestBody.create(MediaType.parse("file"), file));
        }
        presenter.addClientRequest(NetworkConstatnts.RequestCode.API_ADD_CLIENT, params, partData, true);
    }

    private boolean validated() {
        if (CommonUtils.isEmpty(fullNameEdtxt)) {
            CommonUtils.getInstance(this).displayMessage(this, getString(R.string.error_fullName));
            return false;
        } /*else if (CommonUtils.isEmpty(emailEdtxt)) {
            CommonUtils.getInstance(this).displayMessage(this, getString(R.string.error_user_name));
            return false;
        } else if (!CommonUtils.isEmpty(emailEdtxt) && !CommonUtils.isEmailValid(emailEdtxt.getText().toString())) {
            CommonUtils.getInstance(this).displayMessage(this, getString(R.string.error_email));
            return false;
        } else if (CommonUtils.isEmpty(numberEdtxt) && numberEdtxt.getText().toString().length() > 7) {
            CommonUtils.getInstance(this).displayMessage(this, getString(R.string.error_phone_number));
            return false;
        }*/ else if (outputFile == null) {
            CommonUtils.getInstance(this).displayMessage(this, getString(R.string.error_user_image));
            return false;
        } else
            return true;
    }

    private void imagePickCall() {
        if (Build.VERSION.SDK_INT < 23) {
            //Do not need to check the permission
            openPickAlert(1);
        } else {
            if (checkAndRequestPermissions()) {
                //If you have already permitted the permission
                openPickAlert(1);
            }
        }
    }

    /**
     * MULTI IMAGE PICKER CALL
     *
     * @param i
     */
    private void openPickAlert(int i) {
        String[] name = {"TAKE A PHOTO", "CHOOSE FROM GALLERY"};
        int[] icons = {R.drawable.ic_menu_camera, R.drawable.ic_menu_gallery};
        CommonUtils.getInstance(AddClientActivity.this).openDialog(this, name, icons, new OnBottomDialogItemListener() {
            @Override
            public void onItemClick(View view, int position, int type, Object t) {
                switch (type) {
                    case GlobalValues.ClickOperations.APAPTER_BOTTOM_DIALOG_CLICK:
                        switch (position) {
                            case 0:
                                openCamera();
                                break;
                            case 1:
                                openGallerypicker();
                                break;
                        }
                        break;
                }
            }
        });

    }

    private void openGallerypicker() {
        // Intent to gallery
        Intent in = new Intent(Intent.ACTION_PICK);
        in.setType("image/*");
        startActivityForResult(in, GlobalValues.RequestCodes.GALLERY_REQUEST);// start
    }

    private void openCamera() {
        File direct = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Barber");
        File wallpaperDirectory = direct;
        if (!direct.exists()) {
            wallpaperDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Barber");
            wallpaperDirectory.mkdirs();
        }
        String fileName = ImageUtility.getFileName();
        File file = new File(wallpaperDirectory, fileName + ImageUtility.JPEG_FILE_SUFFIX);
        if (file.exists()) {
            file.delete();
        }
        outputFile = file.getPath();
//      mCurrentCameraPhotoPath = f.getAbsolutePath();
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri photoURI = FileProvider.getUriForFile(AddClientActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);

            takePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        } else
            takePicture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        takePicture.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(takePicture, GlobalValues.RequestCodes.REQUEST_TAKE_IMAGE);
    }

    private boolean checkAndRequestPermissions() {
        int camPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int readstoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int writestoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (readstoragePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (writestoragePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (camPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), GlobalValues.RequestCodes.PERMISSIONS_REQUEST_CAMERA);
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case GlobalValues.RequestCodes.PERMISSIONS_REQUEST_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    openPickAlert(1);
                    //Permission Granted Successfully. Write working code here.
                } else {
                    //You did not accept the request can not use the functionality.
                    CommonUtils.getInstance(this).ShowToast(getString(R.string.str_permission_denied));
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GlobalValues.RequestCodes.REQUEST_TAKE_IMAGE && resultCode == RESULT_OK) {
//            Log.d("onActivityResult ", " REQUEST_TAKE_IMAGE " + outputFile);
            cropRequest(outputFile);
        } else if (requestCode == GlobalValues.RequestCodes.GALLERY_REQUEST && resultCode == RESULT_OK) {
            outputFile = ImageUtility.getString(getApplicationContext(), data.getData());
//            Log.d("onActivityResult ", " REQUEST_TAKE_IMAGE " + outputFile);
            cropRequest(outputFile);
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
//                Log.d("onActivityResult ", " --Icon Image Replaced --> " + resultUri + "  " + resultUri.getPath());
                clientImage.setImageURI(resultUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        } else if (requestCode == GlobalValues.RequestCodes.LOCATION_PICKER)
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                double latitude = place.getLatLng().latitude;
                double longitude = place.getLatLng().longitude;
                String fullAddress = place.getAddress().toString();
                addressEdtxt.setText(fullAddress);

                /*Bundle bundle = new Bundle();
                bundle.putString(GlobalValues.KEYS.PLACE_DETAIL, place.getAddress().toString());
                bundle.putDouble(GlobalValues.KEYS.LATITUDE, latitude);
                bundle.putDouble(GlobalValues.KEYS.LONGITUDE, longitude);
                bundle.putBoolean(GlobalValues.KEYS.CURRENT_ADDRESS, true);*/


            }
    }

    private void cropRequest(String outputFile) {
        CropImage.activity(Uri.fromFile(new File(outputFile))).setAspectRatio(1,
                1).setFixAspectRatio(true).start(AddClientActivity.this);
    }

    @Override
    public void onSuccessResponse(int serviceMode, Object o) {
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_ADD_CLIENT:
                BaseResponse responseData = (BaseResponse) o;
                new CommonUtils().displayMessage(AddClientActivity.this, responseData.getMessage());
                clearFields();
                break;
        }
    }

    private void clearFields() {
        fullNameEdtxt.setText("");
        emailEdtxt.setText("");
        numberEdtxt.setText("");
        outputFile = null;
        clientImage.setImageURI("");
        addressEdtxt.setText("");

        //Used to notify  client fragment screen.
        ClientsFragment.NotifyCall nInterface = ClientsFragment.notifyCall;
        if (nInterface != null)
            nInterface.refreshView();
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


}
