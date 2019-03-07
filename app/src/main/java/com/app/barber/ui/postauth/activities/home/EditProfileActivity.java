package com.app.barber.ui.postauth.activities.home;

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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.barber.BuildConfig;
import com.app.barber.R;
import com.app.barber.core.BarberApplication;
import com.app.barber.core.BaseActivity;
import com.app.barber.models.response.BaseResponse;
import com.app.barber.models.response.LoginResponseModel;
import com.app.barber.models.response.MyImagesResponseModel;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.ui.adapters.MyPortfolioAdapter;
import com.app.barber.ui.postauth.activities.home.homemvp.HomeAuthMVPView;
import com.app.barber.ui.postauth.activities.home.homemvp.HomeAuthPresenter;
import com.app.barber.util.CommonUtils;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.ImageUtility;
import com.app.barber.util.iface.OnBottomDialogItemListener;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.views.CustomEditText;
import com.app.barber.views.CustomTextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
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
 * Created by harish on 6/12/18.
 */

public class EditProfileActivity extends BaseActivity implements HomeAuthMVPView {
    @BindView(R.id.back_toolbar)
    ImageView backToolbar;
    @BindView(R.id.txt_title_toolbar)
    CustomTextView txtTitleToolbar;
    @BindView(R.id.img_edit)
    ImageView imgEdit;
    @BindView(R.id.toolbar_root)
    Toolbar toolbarRoot;
    @BindView(R.id.banner_image_profile)
    SimpleDraweeView bannerImageProfile;
    @BindView(R.id.image_profile)
    SimpleDraweeView imageProfile;
    @BindView(R.id.change_profile_image_btn)
    ImageView changeProfileImageBtn;
    @BindView(R.id.change_banner_btn)
    CustomTextView changeBannerBtn;
    @BindView(R.id.recyclar_view_lst)
    RecyclerView recyclarViewLst;
    @BindView(R.id.no_list_data_text)
    CustomTextView noListDataText;
    @BindView(R.id.user_name_edtxt)
    CustomEditText userNameEdtxt;
    @BindView(R.id.shop_name_edtxt)
    CustomEditText shopNameEdtxt;
    @BindView(R.id.desc_edtxt)
    CustomEditText descEdtxt;
    @BindView(R.id.twitter_edtxt)
    CustomEditText twitterEdtxt;
    @BindView(R.id.facebook_edtxt)
    CustomEditText facebookEdtxt;
    @BindView(R.id.continue_btn)
    CustomTextView continueBtn;
    @BindView(R.id.add_image_btn_lay)
    LinearLayout addImageBtnLay;
    @BindView(R.id.listHolder_lay)
    LinearLayout listHolderLay;
    @BindView(R.id.add_image_lay)
    LinearLayout addImageLay;
    private HomeAuthPresenter presenter;
    private MyPortfolioAdapter myPortfolioAdapter;
    private ArrayList<MyImagesResponseModel.ImagesBean> imageList;
    private int imageType;

    @Override
    public int getLayoutId() {
        return R.layout.layout_edit_profile;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BarberApplication) getApplication()).getMyComponent(this).inject(this);
        presenter = new HomeAuthPresenter(this);
        presenter.attachView(this);
        txtTitleToolbar.setText(R.string.str_edit_profile);
        txtTitleToolbar.setVisibility(View.VISIBLE);
        toolbarRoot.bringToFront();
        getIntentData(getIntent());
        initAdapter();
        setupUserData(getUserData());

    }

    private void setupUserData(LoginResponseModel.UserBean userData) {
        if (userData != null) {
            userNameEdtxt.setText(userData.getUserName());
            if (userData.getProfileImage() != null && !userData.getProfileImage().equals("")) {
                imageProfile.setImageURI(ImageUtility.getValidUrl(userData.getProfileImage()));
            }
            if (userData.getBannerImage() != null && !userData.getBannerImage().equals("")) {
                bannerImageProfile.setImageURI(ImageUtility.getValidUrl(userData.getBannerImage()));
            }
            if (userData.getDescription() != null && !userData.getDescription().equals("")) {
                descEdtxt.setText(userData.getDescription());
            }
            if (userData.getShopName() != null && !userData.getShopName().equals("")) {
                shopNameEdtxt.setText(userData.getShopName());
            }
            if (userData.getFbUrl() != null && !userData.getFbUrl().equals("")) {
                facebookEdtxt.setText(userData.getFbUrl());
            }
            if (userData.getTwtUrl() != null && !userData.getTwtUrl().equals("")) {
                twitterEdtxt.setText(userData.getTwtUrl());
            }
        }
    }

    private void getIntentData(Intent intent) {
        imageList = new ArrayList<>();
        imageList = intent.getParcelableArrayListExtra(GlobalValues.KEYS.PORTFOLIO_IMAGES);
        Log.e("getIntentData", "    " + new Gson().toJson(imageList));

    }

    private void initAdapter() {
        myPortfolioAdapter = new MyPortfolioAdapter(EditProfileActivity.this, EditProfileActivity.class.getName(), imageList,
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position, int type, Object t) {
                        switch (type) {
                            case GlobalValues.ClickOperations.ADD_IMAGE:
                                imageType = 3;//Portfolio image
                                imagePickerCall();
                                break;
                            case GlobalValues.ClickOperations.REMOVE:
                                presenter.deleteImage(NetworkConstatnts.RequestCode.API_DELETE_IMAGE, String.valueOf(myPortfolioAdapter.getpositionData(position).getMItem2()),true);
                                myPortfolioAdapter.remove(position);
                                refresh();
                                break;

                        }
                    }
                });
        recyclarViewLst.setLayoutManager(new LinearLayoutManager(EditProfileActivity.this, LinearLayoutManager.HORIZONTAL, false));
        recyclarViewLst.setAdapter(myPortfolioAdapter);
        recyclarViewLst.setNestedScrollingEnabled(false);
        refresh();
    }

    private void refresh() {
        if (myPortfolioAdapter.getItemCount() == 0) {
            addImageLay.setVisibility(View.VISIBLE);
            listHolderLay.setVisibility(View.GONE);
        } else {
            addImageLay.setVisibility(View.GONE);
            listHolderLay.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.back_toolbar, R.id.image_profile, R.id.change_banner_btn, R.id.continue_btn, R.id.add_image_btn_lay, R.id.add_image_lay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_toolbar:
                onBackPressed();
                break;
            case R.id.image_profile:
                imageType = 1;//profile image
                imagePickerCall();
                break;
            case R.id.change_banner_btn:
                imageType = 2;//Banner image
                imagePickerCall();
                break;
            case R.id.continue_btn:
                if (validated())
                    updateProfileData();
                break;
            case R.id.add_image_btn_lay:
                imageType = 3;//Portfolio image
                imagePickerCall();
                break;
            case R.id.add_image_lay:
                imageType = 3;//Portfolio image
                imagePickerCall();
                break;
        }
    }

    private boolean validated() {
        if (CommonUtils.isEmpty(userNameEdtxt)) {
            CommonUtils.getInstance(EditProfileActivity.this).displayMessage(EditProfileActivity.this, getString(R.string.error_fullName));
            return false;
        } else if (CommonUtils.isEmpty(descEdtxt)) {
            CommonUtils.getInstance(EditProfileActivity.this).displayMessage(EditProfileActivity.this, getString(R.string.err_desc));
            return false;
        } else
            return true;
    }

    private void updateProfileData() {
        List<MultipartBody.Part> list = new ArrayList<MultipartBody.Part>(5);
        Map<String, RequestBody> params = new HashMap<>();
        // add_ Parameter
        params.put(NetworkConstatnts.Params.fullname, RequestBody.create(MediaType.parse("text/plain"), userNameEdtxt.getText().toString()));
        params.put(NetworkConstatnts.Params.shopName, RequestBody.create(MediaType.parse("text/plain"), shopNameEdtxt.getText().toString()));
        params.put(NetworkConstatnts.Params.email, RequestBody.create(MediaType.parse("text/plain"), ""));
        params.put(NetworkConstatnts.Params.desc, RequestBody.create(MediaType.parse("text/plain"), descEdtxt.getText().toString()));
        params.put(NetworkConstatnts.Params.twitter, RequestBody.create(MediaType.parse("text/plain"), twitterEdtxt.getText().toString()));
        params.put(NetworkConstatnts.Params.facebook, RequestBody.create(MediaType.parse("text/plain"), facebookEdtxt.getText().toString()));
        params.put(NetworkConstatnts.Params.insta, RequestBody.create(MediaType.parse("text/plain"), "www.google.com"));
        params.put(NetworkConstatnts.Params.other, RequestBody.create(MediaType.parse("text/plain"), "www.google.com"));
        params.put(NetworkConstatnts.Params.userType, RequestBody.create(MediaType.parse("text/plain"), String.valueOf(GlobalValues.UserTypes.BARBER)));

        MultipartBody.Part partData = null;
        if (outputFile != null) {
            File file = new File(outputFile);
            partData = MultipartBody.Part.
                    createFormData("image", file.getName(),
                            RequestBody.create(MediaType.parse("file"), file));
        }
        presenter.updateProfileRequest(NetworkConstatnts.RequestCode.API_UPDATE_PROFILE, params, partData, true);
    }

    private void imagePickerCall() {

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
    public void onSuccessResponse(int serviceMode, Object model) {
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_UPDATE_PROFILE:
                if (((LoginResponseModel) model).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    new CommonUtils().displayMessage(EditProfileActivity.this, ((LoginResponseModel) model).getMessage());
                } else
                    new CommonUtils().displayMessage(EditProfileActivity.this, ((LoginResponseModel) model).getMessage());
                break;
            case NetworkConstatnts.RequestCode.API_POST_WORKSPACE_IMAGES:
                if (((BaseResponse) model).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    new CommonUtils().displayMessage(EditProfileActivity.this, ((BaseResponse) model).getMessage());
                } else
                    new CommonUtils().displayMessage(EditProfileActivity.this, ((BaseResponse) model).getMessage());
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

    /**
     * MULTI IMAGE PICKER CALL
     *
     * @param i
     */
    private void openPickAlert(int i) {
        String[] name = {"TAKE A PHOTO", "CHOOSE FROM GALLERY"};
        int[] icons = {R.drawable.ic_menu_camera, R.drawable.ic_menu_gallery};
        CommonUtils.getInstance(EditProfileActivity.this).openDialog(this, name, icons, new OnBottomDialogItemListener() {
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
        File f = null;
        //            f = storage.setUpPhotoFile();
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
            Uri photoURI = FileProvider.getUriForFile(EditProfileActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);

            takePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        } else
            takePicture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        takePicture.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(takePicture, GlobalValues.RequestCodes.REQUEST_TAKE_IMAGE);
    }

    private String outputFile;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GlobalValues.RequestCodes.REQUEST_TAKE_IMAGE && resultCode == RESULT_OK) {
            Log.e("onActivityResult ", " REQUEST_TAKE_IMAGE " + outputFile);
            cropRequest(outputFile);
        } else if (requestCode == GlobalValues.RequestCodes.GALLERY_REQUEST && resultCode == RESULT_OK) {
            outputFile = ImageUtility.getString(getApplicationContext(), data.getData());
            Log.e("onActivityResult ", " REQUEST_TAKE_IMAGE " + outputFile);
            cropRequest(outputFile);
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Log.e("onActivityResult ", " --Icon Image Replaced --> " + resultUri + "  " + resultUri.getPath());
//                pickedImage = new ImagePickerModel();
//                pickedImage.setImageCroped(true);
//                pickedImage.setImagePath(resultUri.getPath());
//                pickedImage.setType(Model.SELECTED_IMAGE);
             /*   ArrayList<ImagePickerModel> imageList = new ArrayList<>();
                imageList.add(pickedImage);
                imageAdapter.updateAll(imageList);
                if (imageAdapter.getItemCount() == 1) {
                    ImagePickerModel ivModel = new ImagePickerModel();
                    ivModel.setType(Model.TEXT_ADD);
                    imageAdapter.addItem(ivModel);
                }
                notifiScreenView();*/

                switch (imageType) {
                    case 1:
                        imageProfile.setImageURI(resultUri);
                        break;
                    case 2:
                        bannerImageProfile.setImageURI(resultUri);
                        saveImage(resultUri, "banner");
                        break;
                    case 3:
                        saveImage(resultUri, "portfolio");
                        break;
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void saveImage(Uri resultUri, String iType) {
        List<MultipartBody.Part> list = new ArrayList<MultipartBody.Part>(5);
        Map<String, RequestBody> params = new HashMap<>();
        // add_ Parameter
        params.put(NetworkConstatnts.Params.value, RequestBody.create(MediaType.parse("text/plain"), iType));
        File file = new File(resultUri.getPath());
        list.add(MultipartBody.Part.
                createFormData("image", file.getName(),
                        RequestBody.create(MediaType.parse("file"), file)));
        presenter.postImages(NetworkConstatnts.RequestCode.API_POST_WORKSPACE_IMAGES, params, list, true);

    }

    private void cropRequest(String outputFile) {
        switch (imageType) {
            case 1:
                CropImage.activity(Uri.fromFile(new File(outputFile))).setAspectRatio(1,
                        1).setFixAspectRatio(true).start(EditProfileActivity.this);
                break;
            case 2:
                CropImage.activity(Uri.fromFile(new File(outputFile))).setAspectRatio(16,
                        9).setFixAspectRatio(true).start(EditProfileActivity.this);
                break;
            case 3:
                CropImage.activity(Uri.fromFile(new File(outputFile))).setAspectRatio(1,
                        1).setFixAspectRatio(true).start(EditProfileActivity.this);
                break;
        }


    }
}
