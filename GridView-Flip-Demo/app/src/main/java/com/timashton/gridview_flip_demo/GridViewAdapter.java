package com.timashton.gridview_flip_demo;

import android.animation.Animator;
import android.animation.AnimatorInflater;
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
 *
 * Adapter for the gridview. Holds a list of grid view items and applies the card flip
 * animations to the list item views. On screen orientation, this adapter uses stored
 * items to re-create the state that the items should be in.
 */
public class GridViewAdapter extends BaseAdapter{

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

        holder.holderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipGridViewItem(v, item);
            }
        });

        if(item.getFace() == GridViewItem.ItemFace.FRONT){ // Showing the front of the card

            holder.holderView.findViewById(R.id.back).setVisibility(View.GONE);
            holder.holderView.findViewById(R.id.front).setVisibility(View.VISIBLE);

            TextView gridItemText = (TextView) holder.holderView.findViewById(R.id.grid_view_item_text);
            gridItemText.setText(item.getItemText());
        }
        else{
            holder.holderView.findViewById(R.id.front).setVisibility(View.GONE);
            holder.holderView.findViewById(R.id.back).setVisibility(View.VISIBLE);
        }

        return convertView;
    }


    private static class ViewHolder {
        public View holderView;
    }

    public ArrayList<GridViewItem> getList(){
        return mList;
    }


    private void flipGridViewItem(final View v, GridViewItem item){

        v.clearAnimation();

        if(item.getFace() == GridViewItem.ItemFace.FRONT){

            // Set the item to back
            item.setFace(GridViewItem.ItemFace.BACK);

            final Animator in = AnimatorInflater.loadAnimator(mContext,
                    R.animator.card_flip_left_in);
            in.setTarget(v);


            Animator.AnimatorListener flipOutListener = new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    v.findViewById(R.id.front).setVisibility(View.GONE);
                    v.findViewById(R.id.back).setVisibility(View.VISIBLE);
                    in.start();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            };

            Animator out = AnimatorInflater.loadAnimator(mContext,
                    R.animator.card_flip_left_out);
            out.setTarget(v);
            out.addListener(flipOutListener);
            out.start();
        }
        else{

            // Set the item to front
            item.setFace(GridViewItem.ItemFace.FRONT);

            final Animator in = AnimatorInflater.loadAnimator(mContext,
                    R.animator.card_flip_right_in);
            in.setTarget(v);


            // A listener is required to determine the end fo the first animation
            Animator.AnimatorListener flipOutListener = new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    v.findViewById(R.id.back).setVisibility(View.GONE);
                    v.findViewById(R.id.front).setVisibility(View.VISIBLE);
                    in.start();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            };

            Animator out = AnimatorInflater.loadAnimator(mContext,
                    R.animator.card_flip_right_out);
            out.setTarget(v);
            out.addListener(flipOutListener);
            out.start();

            // set the text view for the front of the card
            TextView gridItemText = (TextView) v.findViewById(R.id.grid_view_item_text);
            gridItemText.setText(item.getItemText());
        }
    }
}
