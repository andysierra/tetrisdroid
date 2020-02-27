/*
*    MainActivity
*
*/

package com.andysierra.tetris.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.andysierra.tetris.R;
import com.andysierra.tetris.models.SingletonClasses.GraphicOptions;
import com.andysierra.tetris.models.SingletonClasses.Loader;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity";
    public GraphicOptions gopts;
    public Loader loader;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread.currentThread().setName("MAINACTIVITY THREAD");

        gopts  = GraphicOptions.getInstance();
        loader = Loader.getInstance();
        loader.setActivity(this);
        if (!loader.isCalled()) {
            loader.execute();
            Log.println(Log.ASSERT, TAG, "onCreate: SE ESTÁN CARGANDO LOS ELEMENTOS");
        }
        else setContentView(R.layout.activity_main);
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState (@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Loader.getInstance().clean();
    }
}