package com.example.aigentestapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.aigentestapp.activity.AddAdvertisementActivity;
import com.example.aigentestapp.BuildConfig;
import com.example.aigentestapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.Calendar;
import java.util.regex.Pattern;

public abstract  class BaseActivity extends AppCompatActivity {
    private Activity activity;
    private File newFile ;
    private Uri mCropImageUri;
    private boolean CropingImage = false;
    public void ImagePickerActivity(Activity activity) {
        this.activity = activity;
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.camera_gallary_view, null);
        view.findViewById(R.id.camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                newFile = GetNewPrivateFileObject();
                mCropImageUri = FileProvider.getUriForFile(BaseActivity.this, BuildConfig.APPLICATION_ID + ".provider", newFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mCropImageUri);
                intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                intent.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
                intent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true);
                startActivityForResult(intent, 1111);
                bottomSheetDialog.dismiss();
            }
        });
        view.findViewById(R.id.gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2222);
                bottomSheetDialog.dismiss();
            }
        });
        view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
    }
    public File GetNewPrivateFileObject() {
        ContextWrapper cw = new ContextWrapper(activity);
        File directory = cw.getDir("profile", Context.MODE_PRIVATE);
        if (!directory.exists()) {
            directory.mkdir();
        }
        File mypath = new File(directory, "main_" + Calendar.getInstance().getTimeInMillis() + ".jpg");
        return mypath;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1111) {
                if (CropingImage) {
                    CropImage.activity(mCropImageUri)
                            .setAspectRatio(1, 1)
                            .start(this);
                } else {
                    ShowImage(mCropImageUri, newFile.getAbsolutePath());

                }
            } else if (requestCode == 2222) {
                if (CropingImage) {
                    mCropImageUri = data.getData();

                    CropImage.activity(mCropImageUri)
                            .setAspectRatio(1, 1)
                            .start(this);
                } else {
                    mCropImageUri = data.getData();
                    ShowImage(mCropImageUri, getRealPathFromURI(mCropImageUri));
                }
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            ShowImage(result.getUri(), result.getUri().getPath());
        }
    }
    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
    public void ShowImage(Uri uri, String path) {
        if (activity != null) {
            if (activity instanceof AddAdvertisementActivity) {
                ((AddAdvertisementActivity) activity).SetImage(uri, path);
            }
        }
    }

    public static boolean isValidPhoneNumber(CharSequence phone) {
        boolean check = false;
        if (!TextUtils.isEmpty(phone)) {
            if (!Pattern.matches("[a-zA-Z]+", phone)) {
                if (phone.length() < 6 || phone.length() > 10) {
                    check = false;

                } else {
                    check = true;

                }
            } else {
                check = false;
            }
        }
        return check;
    }
}
