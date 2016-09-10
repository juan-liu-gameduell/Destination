package com.liujuan.destination.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;

import com.liujuan.destination.R;

/**
 * Created by Administrator on 2016/9/9.
 */
public class SimpleDialog extends DialogFragment {

    public static final String SIMPLE_DIALOG_KEY_TITLE = "SimpleDialog.KEY_TITLE";
    public static final String SIMPLE_DIALOG_KEY_MESSAGE = "SimpleDialog.KEY_MESSAGE";
    public static final String SIMPLE_DIALOG_TAG = "SimpleDialog.TAG";

    public static void showDialog(FragmentManager fm, String title, String message) {
        Bundle bundle = new Bundle();
        bundle.putString(SIMPLE_DIALOG_KEY_TITLE, title);
        bundle.putString(SIMPLE_DIALOG_KEY_MESSAGE, message);

        SimpleDialog dialog = new SimpleDialog();
        dialog.setArguments(bundle);

        dialog.show(fm, SIMPLE_DIALOG_TAG);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString(SIMPLE_DIALOG_KEY_TITLE);
        String message = getArguments().getString(SIMPLE_DIALOG_KEY_MESSAGE);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        return builder.setPositiveButton(getActivity().getString(R.string.dialog_confirm), null).create();
    }
}
