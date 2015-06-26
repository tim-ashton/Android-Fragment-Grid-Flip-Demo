package com.timashton.gridview_flip_demo;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/*
 * Created by Tim Ashton on 25/06/15.
 *
 * A dialog fragment for showing a spinner style progress bar while
 * the thread is running.
 */

public class SpinnerDialogFragment extends DialogFragment {

    public static final String TAG = SpinnerDialogFragment.class.getName();

    public static SpinnerDialogFragment newInstance() {
        return new SpinnerDialogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.spinner_dialog_fragment, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCancelable(false);

        return v;
    }
}
