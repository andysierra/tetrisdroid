package com.andysierra.tetris.models.Threads;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.andysierra.tetris.R;
import com.andysierra.tetris.models.SingletonClasses.Loader;
import com.andysierra.tetris.sprites.Sprite;

public class GameRunnable implements Runnable {

    private static final String TAG = "GameRunnable";
    private Canvas c;

    public GameRunnable() {}

    @Override
    public void run() {

        // ATTENTION: Attributes member of this class won't change at playtime

        try {
            Paint p = new Paint();
            p.setColor(Color.MAGENTA);
            p.setStyle(Paint.Style.FILL);
            int i = 0;

            while(!Thread.currentThread().isInterrupted()) {
                if(Loader.getInstance().isPlaying()) {

                    // GAME LOOP HERE

                    // only performs drawing operations while surface is not destroying
                    if(Loader.getInstance().getHolder() != null) {
                        synchronized (Loader.getInstance().getHolder()) {

                            c = Loader.getInstance().getHolder().lockCanvas();

                            if(c != null) {
                                c.drawBitmap(Bitmap.createScaledBitmap(Loader.getInstance().getBackground(),
                                        Loader.getInstance().getWidth(), Loader.getInstance().getHeight(), false),
                                        0,0,p);
                                (new Sprite(c, BitmapFactory.decodeResource(Loader.getInstance().getResources(),
                                    R.drawable.mario)))
                                            .rotate(i*10, false)
                                            .drawSprite(0,0);
                                i++;

                                Loader.getInstance().getHolder().unlockCanvasAndPost(c);
                            }

                        }
                    }
                }
            }
            throw new InterruptedException();
        } catch (InterruptedException ie) {
            // CLEAN THIS RUNNABLE HERE
            Log.println(Log.ASSERT, TAG, "run: CLEANING GAME RUNNABLE");
        }
    }
}