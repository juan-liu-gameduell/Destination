package com.liujuan.destination.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;

/**
 * Created by Administrator on 2016/9/9.
 */
public class SimpleDialogWithoutConfirmButton extends DialogFragment {

    public static final String SIMPLE_DIALOG_KEY_MESSAGE = "SimpleDialog.KEY_MESSAGE";
    public static final String SIMPLE_DIALOG_TAG = "SimpleDialog.TAG";

    public static SimpleDialogWithoutConfirmButton showDialog(FragmentManager fm, String message) {
        Bundle bundle = new Bundle();
        bundle.putString(SIMPLE_DIALOG_KEY_MESSAGE, message);

        SimpleDialogWithoutConfirmButton dialog = new SimpleDialogWithoutConfirmButton();
        dialog.setArguments(bundle);

        dialog.show(fm, SIMPLE_DIALOG_TAG);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = getArguments().getString(SIMPLE_DIALOG_KEY_MESSAGE);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message);
        return builder.create();
    }

}
