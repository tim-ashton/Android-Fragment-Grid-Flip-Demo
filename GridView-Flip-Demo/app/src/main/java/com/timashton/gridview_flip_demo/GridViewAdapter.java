package com.timashton.gridview_flip_demo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
        GridViewItem item = mList.get(position);
        ViewHolder holder;

        // If it is a new item, create it otherwise use the existing item previously created in
        // this method.
        if (convertView == null) {

            LayoutInflater li = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.grid_view_item, null);

            holder = new ViewHolder();
            holder.mListItemText = (TextView) convertView.findViewById(R.id.grid_view_item_text);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mListItemText.setText(item.getItemText());
        return convertView;
    }


    private static class ViewHolder {
        public TextView mListItemText;

    }

    public ArrayList<GridViewItem> getList(){
        return mList;
    }
}
