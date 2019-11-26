package com.example.aigentestapp.utils;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class UtilPermission {
    public final static int MY_PERMISSIONS_REQUEST = 5001;

    public interface PermissionCallBack {
        public void HavePermission();

        public void NotAcceptPermission();

        public void NotHavePermission();
    }

    private Activity context;
    private String[] permission;
    private PermissionCallBack permissionCallBack;

    public UtilPermission(Activity context, PermissionCallBack permissionCallBack, String[] permission) {
        this.context = context;
        this.permissionCallBack = permissionCallBack;
        this.permission = permission;
    }

    public void CheckPemissionResult(int requestCode,
                                     String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                boolean haveper = true;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        haveper = false;
                        permissionCallBack.NotAcceptPermission();
                        break;
                    }
                }
                if (haveper) {
                    permissionCallBack.HavePermission();
                }
                return;
            }

        }
    }


    public void CheckPemission() {
        boolean haveper = true;
        for (int i = 0; i < permission.length; i++) {
            int permissionCheck = ContextCompat.checkSelfPermission(context, permission[i]);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                haveper = false;
                permissionCallBack.NotHavePermission();
                break;
            }
        }
        if (haveper) {
            permissionCallBack.HavePermission();
        }
    }

    public void ShowPermissionDialog() {
        ActivityCompat.requestPermissions(context,
                permission,
                MY_PERMISSIONS_REQUEST);
    }
}
