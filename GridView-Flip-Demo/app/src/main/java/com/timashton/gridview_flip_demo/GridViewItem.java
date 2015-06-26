package com.timashton.gridview_flip_demo;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/*
 * Created by Tim Ashton on 25/06/15.
 */
public class GridViewItem implements Parcelable {

    private static final String TAG = GridViewItem.class.getName();
    private static final String NEW_ITEM_TAG = "new_item";
    private static final String TEXT_TAG = "item_text";

    private boolean mNewItem;
    private int mFlipperPosition;
    private String mText;

    public GridViewItem(String text){
        mNewItem = true;
        mText = text;
    }

    public GridViewItem(boolean newItem, String text){
        mNewItem = newItem;
        mText = text;
    }

    private GridViewItem(Parcel in) {
        Bundle bundle = in.readBundle();
        mNewItem = bundle.getBoolean(NEW_ITEM_TAG);
        mText = bundle.getString(TEXT_TAG);
    }

    public int getFlipperPosition(){
        return mFlipperPosition;
    }

    public void setFlipperPosition(int position){
        mFlipperPosition = position;
    }

    public boolean isNewItem(){
        return mNewItem;
    }

    public String getItemText(){
        return mText;
    }

    public void setIsNewItem(boolean newItem){
        mNewItem = newItem;
    }

    public void setItemText(String text){
        mText = text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(NEW_ITEM_TAG, mNewItem);
        bundle.putString(TEXT_TAG, mText);
        dest.writeBundle(bundle);
    }

    public static final Parcelable.Creator<GridViewItem> CREATOR
            = new Parcelable.Creator<GridViewItem>() {
        public GridViewItem createFromParcel(Parcel in) {
            return new GridViewItem(in);
        }

        public GridViewItem[] newArray(int size) {
            return new GridViewItem[size];
        }
    };
}
