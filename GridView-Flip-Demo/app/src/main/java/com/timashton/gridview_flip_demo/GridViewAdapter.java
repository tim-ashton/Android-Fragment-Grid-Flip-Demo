package com.timashton.gridview_flip_demo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;

/*
 * Created by Tim Ashton on 25/06/15.
 */
public class GridViewAdapter extends BaseAdapter{

    public static final String TAG = GridViewAdapter.class.getName();

    private Activity mContext;
    private ArrayList<GridViewItem> mList;

    public GridViewAdapter(Activity context, ArrayList<GridViewItem> adapterList){
        mContext = context;
        mList = adapterList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final GridViewItem item = mList.get(position);
        ViewHolder holder;

        // If it is a new item, create it otherwise use the existing item previously created in
        // this method.
        if (convertView == null) {

            LayoutInflater li = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.grid_view_item, parent, false);

            holder = new ViewHolder();
            holder.holderView = convertView;

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        TextView gridItemText = (TextView) holder.holderView.findViewById(R.id.grid_view_item_text);
        gridItemText.setText(item.getItemText());

        //reference the viewFlipper
        final ViewFlipper flipper = (ViewFlipper) holder.holderView.
                findViewById(R.id.grid_item_view_flipper);

        // Set the current view of the flipper
        flipper.setDisplayedChild(item.getFlipperPosition());

        //now you set your onclick and pass it the current viewflipper to control the displayed child
        flipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View click) {


                flipViewFlipper(flipper, item);
            }
        });

        //holder.mListItemText.setText(item.getItemText());
        return convertView;
    }


    private static class ViewHolder {
        public TextView mListItemText;
        public View holderView;
    }

    public ArrayList<GridViewItem> getList(){
        return mList;
    }

    private void flipViewFlipper(ViewFlipper flipper, GridViewItem item){

        if(flipper.getDisplayedChild() == 0){
            flipper.setDisplayedChild(1);
            item.setFlipperPosition(1);
        }
        else{
            flipper.setDisplayedChild(0);
            item.setFlipperPosition(0);
        }

    }
}
