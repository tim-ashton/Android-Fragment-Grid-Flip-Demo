package com.timashton.gridview_flip_demo;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;


public class MainActivity extends Activity implements WorkerFragment.WorkerCallbacks{

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getFragmentManager();

        if (savedInstanceState == null) {
            fm.beginTransaction()
                    .add(R.id.container, new StartFragment())
                    .commit();
        }

        // If the worker fragment is null a new one is required
        // Because this is not a configuration change
        WorkerFragment workerFragment = (WorkerFragment) fm.findFragmentByTag(WorkerFragment.TAG);
        if (workerFragment == null) {
            workerFragment = new WorkerFragment();
            fm.beginTransaction().add(workerFragment, WorkerFragment.TAG).commit();
        }
    }


    /*
    OnClick event on fragment_main_start_demo_button

    Create the SpinnerDialogFragment.
    Replace the start fragment with the grid fragment.
    Start the runnable in the WorkerFragment
     */
    public void startDemo(View view) {

        FragmentManager fm = getFragmentManager();

        SpinnerDialogFragment spinnerDialogFragment = SpinnerDialogFragment.newInstance();
        spinnerDialogFragment.setCancelable(false);
        spinnerDialogFragment.show(fm.beginTransaction(), SpinnerDialogFragment.TAG);

        fm.beginTransaction()
                .setCustomAnimations(
                        R.animator.grow_in,
                        R.animator.spin_shrink_out,
                        R.animator.spin_grow_in,
                        R.animator.spin_shrink_out)
                .replace(R.id.container, GridViewFragment.newInstance(), GridViewFragment.TAG)
                .addToBackStack(null)
                .commit();

        final WorkerFragment workerFragment =
                (WorkerFragment) fm.findFragmentByTag(WorkerFragment.TAG);

        int delay = getResources().getInteger(R.integer.show_grid_view_animation_time)
                + getResources().getInteger(R.integer.spin_shrink_animation_time);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                workerFragment.startAddItemsRunnable();
            }
        }, delay );


    }

    @Override
    public void addItem(String text) {
        GridViewFragment gridViewFragment = (GridViewFragment) getFragmentManager()
                .findFragmentByTag(GridViewFragment.TAG);

        if (gridViewFragment != null) {
            gridViewFragment.addGridItem(text);
        }
    }

    @Override
    public void dummyRunnableFinished() {
        SpinnerDialogFragment spinnerDialogFragment =
                (SpinnerDialogFragment) getFragmentManager()
                        .findFragmentByTag(SpinnerDialogFragment.TAG);

        if (spinnerDialogFragment != null) {
            spinnerDialogFragment.dismiss();
        }
    }
}
