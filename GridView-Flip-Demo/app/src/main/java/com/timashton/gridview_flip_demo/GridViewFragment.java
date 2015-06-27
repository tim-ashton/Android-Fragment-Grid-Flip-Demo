package com.timashton.gridview_flip_demo;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

/*
 * Created by Tim Ashton on 26/06/15.
 *
 * This fragment display the grid view and hancles storing the state of the grid
 * view items on orientation change.
 */
public class GridViewFragment extends Fragment{

    public static final String TAG = GridViewFragment.class.getName();
    private final String TAG_SAVED_LIST = "saved_items_list";

    private GridViewAdapter mGridViewAdapter;
    private ArrayList<GridViewItem> mList;
    private GridView mGridView;


    public static GridViewFragment newInstance(){
        return new GridViewFragment();
    }

    public void addGridItem(String text) {
        Log.d(TAG, "addGridItem()");
        GridViewItem item = new GridViewItem(text);
        mList.add(item);
        mGridViewAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState){
        super.onCreateView(inflater, container, savedState);

        View v = inflater.inflate(R.layout.fragment_gridview, container, false);

        mGridView = (GridView)v.findViewById(R.id.fragment_gridview_gridview);

        if (savedState != null) {
            mList = savedState.getParcelableArrayList(TAG_SAVED_LIST);
        } else {
            mList = new ArrayList<>();
        }

        mGridViewAdapter = new GridViewAdapter(getActivity(), mList);
        mGridView.setAdapter(mGridViewAdapter);

        return v;
    }

    /*
    Store the parcelable GridViewItems so they can be retrieved and re-created.
     */
    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        ArrayList<GridViewItem> bundledListItems = mGridViewAdapter.getList();
        savedState.putParcelableArrayList(TAG_SAVED_LIST, bundledListItems);
    }

}

