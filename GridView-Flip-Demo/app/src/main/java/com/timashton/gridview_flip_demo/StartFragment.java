package com.timashton.gridview_flip_demo;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*
 * Created by Tim Ashton on 25/06/15.
 *
 * This fragment is the opening fragment for the application.
 */
public class StartFragment extends Fragment {

    public static final String TAG = StartFragment.class.getName();

    public static StartFragment newInstance(){
        return new StartFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        return v;
    }
}
