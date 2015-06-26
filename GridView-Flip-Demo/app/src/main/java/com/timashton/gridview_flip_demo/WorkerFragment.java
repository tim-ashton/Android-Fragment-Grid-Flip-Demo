package com.timashton.gridview_flip_demo;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.lang.ref.WeakReference;

/*
 * Created by Tim Ashton on 25/06/15.
 */
public class WorkerFragment extends Fragment {

    public static final String TAG = WorkerFragment.class.getName();

    private WorkerHandler mHandler;
    private static WorkerCallbacks mCallbacks;
    private WorkerRunnable mRunnable;

    public WorkerFragment newInstance() {
        return new WorkerFragment();
    }

    /*
    Callbacks to communicate back to the activity
    */
    public interface WorkerCallbacks {
        void addItem(String text);
        void dummyRunnableFinished();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "onAttach(Activity)");

        if (!(activity instanceof WorkerCallbacks)) {
            throw new IllegalStateException("Activity must implement the TaskCallbacks interface.");
        }
        // get a reference if attached.
        mCallbacks = (WorkerCallbacks) activity;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle)");
        setRetainInstance(true);

        if (savedInstanceState == null) {
            mHandler = new WorkerHandler(this);
        }
    }

    /*
     * Set the callback to null on detach
     * so not to accidentally leak the
     * Activity instance.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach()");
        mCallbacks = null;
    }


    /*
     * Call the onPause() of the DemoThread to pause execution
     * of the thread.
     */
    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");

        if ((mRunnable != null) && !mRunnable.mFinished) {
            mRunnable.onPause();
        }
    }

    /*
     * Call the onResume() of the DemoThread to resume execution
     * of the thread.
     */
    @Override
    public void onResume() {
        Log.d(TAG, "onResume()");
        super.onResume();

        if ((mRunnable != null) && !mRunnable.mFinished) {
            mRunnable.onResume();
        }
    }


    public void startAddItemsRunnable() {
        Log.d(TAG, "startAddItemsRunnable()");
        mRunnable = new WorkerRunnable();
        mRunnable.start();
    }


    /*
     * Inner Class WorkerRunnable
     *
     * Inner thread class to run the dummy thread by implementing runnable run method.
     *
     * Allows parent to pause and restart the thread by calling the appropriate methods.
     *
     */
    private class WorkerRunnable extends Thread {

        public final String TAG = WorkerRunnable.class.getName();

        private final Object mPauseLock;
        private boolean mPaused;
        private boolean mFinished;

        public WorkerRunnable() {
            mPauseLock = new Object();
            mPaused = false;
            mFinished = false;
        }

        @Override
        public void run() {
            Log.d(TAG, "run()");

            try {
                int i = 0;
                while (!mFinished) {

                    Message msg = new Message();
                    msg.obj = Integer.toString(i++);
                    mHandler.sendMessage(msg);

                    Thread.sleep(100);

                    if (i == 50) {
                        mFinished = true;
                    }

                    synchronized (mPauseLock) {
                        while (mPaused) {
                            try {
                                mPauseLock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            finally {
                mCallbacks.dummyRunnableFinished();
            }
        }

        /*
         * Called on fragment pause.
         *
         * Pauses the notifies implementer of callback.
         */
        public void onPause() {
            Log.d(TAG, "onPause()");
            synchronized (mPauseLock) {
                mPaused = true;
            }
        }

        /*
         * Called on fragment resume.
         *
         * Resumes the thread and notifies implementer of callback.
         */
        public void onResume() {
            Log.d(TAG, "onResume()");
            synchronized (mPauseLock) {
                mPaused = false;
                mPauseLock.notifyAll();
            }
        }
    }


    /*
    A Handler to handle callback message passing to the UI thread.

    Use this inner class with a weak reference to prevent memory leaks.
     */
    private static class WorkerHandler extends Handler {

        private final WeakReference<WorkerFragment> scanWorkerFragmentWeakReference;

        public WorkerHandler(WorkerFragment myClassInstance) {
            scanWorkerFragmentWeakReference = new WeakReference<>(myClassInstance);
        }

        @Override
        public void handleMessage(Message msg) {
            WorkerFragment myClass = scanWorkerFragmentWeakReference.get();
            if (myClass != null) {

                mCallbacks.addItem(msg.obj.toString());
            }
        }
    }
}
