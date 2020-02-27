package com.andysierra.tetris.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.andysierra.tetris.R;
import com.andysierra.tetris.models.SingletonClasses.Loader;
import com.andysierra.tetris.models.Threads.GameRunnable;

public class ViewGame extends SurfaceView
{
    private static final String TAG = "ViewGame";
    private static final R.styleable styleable = new R.styleable();
    private TypedArray xml;
    private AttributeSet attrs;
    private int nRows = 0;
    private int nCols = 0;

    // CONSTRUCTORS
    public ViewGame(Context context) {
        super(context);
        this.attrs = null;
    }

    public ViewGame(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.attrs = attrs;
    }

    public ViewGame(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attrs = attrs;
    }

    public ViewGame(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.attrs = attrs;
    }


    /**
     *  init()
     *   This method handles all view's xml properties
     *   and view elements
     */
    private void init() {
        Log.println(Log.ASSERT, TAG, "init: ");
    }


    /**
     * This function resizes the view, then resizes the surface, affecting the drawing loop
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(final int w, final int h, int oldw, int oldh) {
        this.init();

        // After change size, make a surface
        this.getHolder().addCallback(new SurfaceHolder.Callback()
        {
            @Override // threadGame recalculate
            public void surfaceCreated(SurfaceHolder holder) {
                Log.println(Log.ASSERT, TAG, "surfaceCreated: ");

                // If Drawing thread is null, instantiate!
                if(Loader.getInstance().getGameThread() == null) {
                    Loader.getInstance().setGameThread(new Thread(new GameRunnable()));
                    Loader.getInstance().getGameThread().setName("GAME THREAD");
                }

                // Recalculate
                Loader.getInstance().updateHolder(getHolder());
                Loader.getInstance().setBgDrawn(false);
                Loader.getInstance().setWidth(w);
                Loader.getInstance().setHeight(h);
            }

            @Override // threadGame loop
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.println(Log.ASSERT, TAG, "surfaceChanged: ");
                // If Drawing thread is not alive, start it!
                if(!Loader.getInstance().getGameThread().isAlive()) {
                    Log.println(Log.ASSERT, TAG, "surfaceChanged: STARTING THE GAME THREAD");
                    Loader.getInstance().getGameThread().start();
                }
            }

            @Override // threadGame stop loop
            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.println(Log.ASSERT, TAG, "surfaceDestroyed: ");
                Loader.getInstance().updateHolder(null);
                Loader.getInstance().setPlaying(false);
            }
        });

        Loader.getInstance().setPlaying(true);
    }
}

