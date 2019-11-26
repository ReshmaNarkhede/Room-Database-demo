package com.example.aigentestapp.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;


public class DialogUtil {

    private static Dialog WailDialog;
    private static ProgressDialog pDialog;


    public static void showOkDialog(Context context, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static void showOkDialogWithFinish(Context context, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                ((Activity) context).finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static void showWhoopsDialog(Context context, String msg) {
        try {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showDialog(Context context) {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();
    }

    public static void dismissDialog(Context context) {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }

    public static void ShowWaitLoader(Context context) {
        if (WailDialog == null)
            WailDialog = getDialog(context, "Loading...");

        WailDialog.setCancelable(false);
        if (!WailDialog.isShowing())
            WailDialog.show();
    }

    public static void DismissWaitLoader(Context context) {
        if (WailDialog == null) {
            WailDialog.dismiss();
            WailDialog = null;
        }
    }

    public static Dialog getDialog(Context context) {
        return getDialog(context, "Loading...");
    }

    public static Dialog getDialog(Context context, String msg) {
        return getDialog(context, "Please wait...", msg);
    }

    public static Dialog getDialog(Context context, String title, String msg) {

        ProgressDialog progressDialog = ProgressDialog.show(context, title, msg, true);
        progressDialog.setCancelable(false);
        return progressDialog;
    }
}
