package com.app.barber.ui.postauth.activities.home;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.app.barber.BuildConfig;
import com.app.barber.R;
import com.app.barber.core.BarberApplication;
import com.app.barber.core.BaseActivity;
import com.app.barber.models.response.BaseResponse;
import com.app.barber.models.response.LoginResponseModel;
import com.app.barber.models.response.MyImagesResponseModel;
import com.app.barber.models.response.ProfileResponseModel;
import com.app.barber.net.NetworkConstatnts;
import com.app.barber.ui.adapters.MyPortfolioAdapter;
import com.app.barber.ui.postauth.activities.home.homemvp.HomeAuthMVPView;
import com.app.barber.ui.postauth.activities.home.homemvp.HomeAuthPresenter;
import com.app.barber.util.CommonUtils;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.ImageUtility;
import com.app.barber.util.PreferenceManager;
import com.app.barber.util.iface.OnBottomDialogItemListener;
import com.app.barber.util.iface.OnItemClickListener;
import com.app.barber.views.CustomTextView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by harish on 5/11/18.
 */

public class ProfileScreenActivity extends BaseActivity implements HomeAuthMVPView {
    @Inject
    PreferenceManager mPref;
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
    @BindView(R.id.user_name)
    CustomTextView userName;
    @BindView(R.id.recyclar_view_lst)
    RecyclerView recyclarViewLst;
    @BindView(R.id.no_list_data_text)
    CustomTextView noListDataText;
    @BindView(R.id.profile_desc)
    CustomTextView profileDesc;
    @BindView(R.id.user_profile)
    CustomTextView userProfile;
    @BindView(R.id.facebook_btn)
    ImageView facebookBtn;
    @BindView(R.id.twitter_btn)
    ImageView twitterBtn;
    @BindView(R.id.insta_btn)
    ImageView instaBtn;

    private HomeAuthPresenter presenter;
    private MyPortfolioAdapter myPortfolioAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.layout_profile_screen;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BarberApplication) getApplication()).getMyComponent(this).inject(this);
        presenter = new HomeAuthPresenter(this);
        presenter.attachView(this);
        txtTitleToolbar.setText(R.string.profile);
        txtTitleToolbar.setVisibility(View.VISIBLE);
        toolbarRoot.bringToFront();
        imgEdit.setVisibility(View.VISIBLE);
        imgEdit.setImageResource(R.drawable.pen_01);
//        setupUserData(getUserData());
        initRecyclar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserProfile();
    }

    private void getUserProfile() {
        presenter.getUserProfile(NetworkConstatnts.RequestCode.API_GET_PROFILE, true);
        presenter.getMyStyleList(NetworkConstatnts.RequestCode.API_GET_IMAGES, null, false);
    }

    private void initRecyclar() {
        myPortfolioAdapter = new MyPortfolioAdapter(ProfileScreenActivity.this, ProfileScreenActivity.class.getName(), new ArrayList<MyImagesResponseModel.ImagesBean>(),
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position, int type, Object t) {
                        switch (type) {
                            case GlobalValues.ClickOperations.FULL_IMAGE:
                                new CommonUtils().FullImageScreem(ProfileScreenActivity.this, ImageUtility.getValidUrl(myPortfolioAdapter.getpositionData(position).getMItem1()));
                                break;
                        }
                    }
                });
        recyclarViewLst.setLayoutManager(new LinearLayoutManager(ProfileScreenActivity.this, LinearLayoutManager.HORIZONTAL, false));
        recyclarViewLst.setAdapter(myPortfolioAdapter);
    }

    private void setupUserData(LoginResponseModel.UserBean userData) {
        userName.setText(userData.getFullName());
        if (userData.getProfileImage() != null && !userData.getProfileImage().equals("")) {
            imageProfile.setImageURI(ImageUtility.getValidUrl(userData.getProfileImage()));
        }
        if (userData.getBannerImage() != null && !userData.getBannerImage().equals("")) {
            bannerImageProfile.setImageURI(ImageUtility.getValidUrl(userData.getBannerImage()));
        }
        if (userData.getDescription() != null && !userData.getDescription().equals("")) {
            profileDesc.setText(userData.getDescription());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @OnClick({R.id.back_toolbar, R.id.img_edit, R.id.facebook_btn, R.id.twitter_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_toolbar:
                onBackPressed();
                break;
            case R.id.img_edit:
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(GlobalValues.KEYS.PORTFOLIO_IMAGES, (ArrayList<? extends Parcelable>) myPortfolioAdapter.getAll());
                activitySwitcher(ProfileScreenActivity.this, EditProfileActivity.class, bundle);
                break;
            case R.id.facebook_btn:
                if (getUserData() != null && getUserData().getFbUrl() != null)
                    goNext(getUserData().getFbUrl());
                break;
            case R.id.twitter_btn:
                if (getUserData() != null && getUserData().getTwtUrl() != null)
                    goNext(getUserData().getTwtUrl());
                break;

        }
    }

    private void goNext(String s) {
        try {
            String url = "http://" + s;
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        } catch (ActivityNotFoundException e) {

        }
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

    /**
     * MULTI IMAGE PICKER CALL
     *
     * @param i
     */
    private void openPickAlert(int i) {
        String[] name = {"TAKE A PHOTO", "CHOOSE FROM GALLERY"};
        int[] icons = {R.drawable.ic_menu_camera, R.drawable.ic_menu_gallery};
        CommonUtils.getInstance(ProfileScreenActivity.this).openDialog(this, name, icons, new OnBottomDialogItemListener() {
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
            Uri photoURI = FileProvider.getUriForFile(ProfileScreenActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);

            takePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        } else
            takePicture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        takePicture.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(takePicture, GlobalValues.RequestCodes.REQUEST_TAKE_IMAGE);
    }

    private String outputFile;


    @Override
    public void onSuccessResponse(int serviceMode, Object model) {
        switch (serviceMode) {
            case NetworkConstatnts.RequestCode.API_UPDATE_PROFILE:
                if (((LoginResponseModel) model).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    new CommonUtils().displayMessage(ProfileScreenActivity.this, ((LoginResponseModel) model).getMessage());
                    LoginResponseModel.UserBean userData = getUserData();
                    LoginResponseModel.UserBean updatedData = ((LoginResponseModel) model).getUser();
                    updatedData.setSessionId(userData.getSessionId());
                    presenter.saveUserData(updatedData);
                } else
                    new CommonUtils().displayMessage(ProfileScreenActivity.this, ((BaseResponse) model).getMessage());
                break;
            case NetworkConstatnts.RequestCode.API_GET_PROFILE:
                if (((ProfileResponseModel) model).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    LoginResponseModel.UserBean userData = getUserData();
                    LoginResponseModel.UserBean updatedData = ((ProfileResponseModel) model).getUser();
                    updatedData.setSessionId(userData.getSessionId());
                    presenter.saveUserData(updatedData);
                    setupUserData(((ProfileResponseModel) model).getUser());
                } else
                    new CommonUtils().displayMessage(ProfileScreenActivity.this, ((BaseResponse) model).getMessage());
                break;
            case NetworkConstatnts.RequestCode.API_GET_IMAGES:
                if (((MyImagesResponseModel) model).getStatus() == NetworkConstatnts.ResponseCode.success) {
                    if (((MyImagesResponseModel) model).getImages() != null && ((MyImagesResponseModel) model).getImages().size() > 0) {
                        myPortfolioAdapter.updateAll(((MyImagesResponseModel) model).getImages());
                        noListDataText.setVisibility(View.GONE);
                        recyclarViewLst.setVisibility(View.VISIBLE);
                    } else {
                        noListDataText.setVisibility(View.VISIBLE);
                        noListDataText.setBackgroundResource(R.drawable.rectangle_white_border);
                        noListDataText.setText(R.string.str_no_image_found);
                        noListDataText.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.camera_blue, 0, 0, 0);
                        recyclarViewLst.setVisibility(View.GONE);
                    }
                } else
                    new CommonUtils().displayMessage(ProfileScreenActivity.this, ((BaseResponse) model).getMessage());
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
}
