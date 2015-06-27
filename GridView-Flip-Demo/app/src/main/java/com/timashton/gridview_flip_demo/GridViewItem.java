package com.timashton.gridview_flip_demo;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/*
 * Created by Tim Ashton on 25/06/15.
 *
 * Parcelable items to store the state of each item in the grid view.
 */
public class GridViewItem implements Parcelable {

    private static final String NEW_ITEM_TAG = "new_item";
    private static final String TEXT_TAG = "item_text";

    private boolean mNewItem;
    private ItemFace mItemFace;
    private String mText;

    public enum ItemFace{
        FRONT,
        BACK
    }

    public GridViewItem(String text){
        mNewItem = true;
        mText = text;
        mItemFace = ItemFace.FRONT;
    }

    private GridViewItem(Parcel in) {
        Bundle bundle = in.readBundle();
        mNewItem = bundle.getBoolean(NEW_ITEM_TAG);
        mText = bundle.getString(TEXT_TAG);
    }

    public ItemFace getFace(){
        return mItemFace;
    }

    public void setFace(ItemFace face){
        mItemFace = face;
    }

    public String getItemText(){
        return mText;
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
