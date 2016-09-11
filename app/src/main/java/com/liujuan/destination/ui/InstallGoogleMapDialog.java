package com.liujuan.destination.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.liujuan.destination.R;

/**
 * Created by Administrator on 2016/9/9.
 */
public class InstallGoogleMapDialog extends DialogFragment {

    public static final String SIMPLE_DIALOG_TAG = "SimpleDialog.TAG";

    public static void showDialog(FragmentManager fm) {
        InstallGoogleMapDialog dialog = new InstallGoogleMapDialog();
        dialog.show(fm, SIMPLE_DIALOG_TAG);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.install_map_title)
                .setMessage(R.string.install_map_message)
                .setPositiveButton(R.string.dialog_install_map, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String appPackageName = "com.google.android.apps.maps";
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .create();
    }
}
