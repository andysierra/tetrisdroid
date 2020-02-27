package com.andysierra.tetris.models.SingletonClasses;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.SurfaceHolder;

import com.andysierra.tetris.R;
import com.andysierra.tetris.activities.MainActivity;

import java.lang.ref.WeakReference;


public class Loader extends AsyncTask<Void, Integer, Boolean>
{
    private static final String TAG = "Loader";
    private WeakReference<MainActivity> mwActivity;
    private Resources resources;
    private Thread gameThread;
    private static Loader mInstance;
    private boolean mCalled = false;
    private Bitmap background = null;

    // Things that will change on play time
    private int width=1, height=1;
    private boolean isPlaying = false;
    private boolean bgDrawn = false;
    private SurfaceHolder holder = null;

    // CONSTRUCTOR
    private Loader () {}

    // GET INSTANCE
    public static Loader getInstance() {
        if(mInstance == null)
            mInstance = new Loader();
        return mInstance;
    }

    // LOAD BACKGROUND
    private Bitmap loadBackground() {

        MainActivity activity = mwActivity.get();
        if( activity == null || activity.isFinishing() ) return null;

        this.resources = activity.getResources();
        return BitmapFactory.decodeResource(activity.getResources(), R.drawable.bg);
    }

    // CLEANER
    public void clean() {
        Log.println(Log.ASSERT, TAG, "clean: NOW LOADER MUST CLEAN");
        this.background = null;     // set background as null
        this.isPlaying = false;
        this.gameThread.interrupt();
        mInstance = null;           // set Loader instance as null
    }


    // ASYNCTASK
    @Override
    protected Boolean doInBackground(Void... voids) {
        MainActivity activity = null;

        if ( mwActivity != null ) {
            this.mCalled = true;
            activity = this.mwActivity.get();

            if( activity == null || activity.isFinishing() ) return false;

            Log.println(Log.ASSERT, TAG, "doInBackground: PERFORMING FIRST LOAD");

            publishProgress(0);
            this.background = loadBackground();
        }
        else throw new IllegalStateException("you must call Loader::setActivity(MainActivity) before call Loader::execute()");

        return true;
    }
    @Override
    protected void onProgressUpdate(Integer... values) {
    }
    @Override
    protected void onPostExecute(Boolean workResult) {
        if (workResult) {   // call elements to load here
            if( background == null ) return;


            MainActivity activity = mwActivity.get();
            if (activity != null || activity.isFinishing()) {
                activity.setContentView(R.layout.activity_main);
            }
        }
    }



    // GETTERS AND SETTERS
    public void setActivity(MainActivity activity) {
        this.mwActivity = new WeakReference<MainActivity>(activity);
    }
    public Resources getResources() {
        return resources;
    }
    public Thread getGameThread() {
        return gameThread;
    }
    public void setGameThread(Thread gameThread) {
        this.gameThread = gameThread;
    }
    public boolean isCalled () {
        return mCalled;
    }



    // Things that will change at play time
    public void updateHolder(SurfaceHolder holder) {
        this.holder = holder;
    }
    public SurfaceHolder getHolder() {
        return this.holder;
    }
    public Bitmap getBackground() {
        return this.background;
    }
    public void setBackground(Bitmap background) {
        this.background = background;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public boolean isBgDrawn() {
        return bgDrawn;
    }
    public void setBgDrawn(boolean bgDrawn) {
        this.bgDrawn = bgDrawn;
    }
    public boolean isPlaying() {
        return isPlaying;
    }
    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
}