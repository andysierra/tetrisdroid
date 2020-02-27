package com.andysierra.tetris.sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Sprite
{
    private static final String TAG = "Sprite";
    private Canvas canvas;
    private Bitmap bitmap;

    public Sprite (Canvas canvas, Bitmap bitmap) {
        this.canvas = canvas;
        this.bitmap = bitmap;
    }

    public Sprite scale (int w, int h, boolean filter) {
         this.bitmap = Bitmap.createScaledBitmap(this.bitmap, w, h, filter);
        return this;
    }

    public Sprite rotate (int degree, boolean filter) {
        Matrix mat = new Matrix();
        mat.postRotate(degree);
        this.bitmap = Bitmap.createBitmap(this.bitmap, 0, 0, this.bitmap.getWidth(), this.bitmap.getHeight(), mat, filter);
        return this;
    }

    public void drawSprite (int x, int y) {
        this.canvas.drawBitmap(this.bitmap, x, y, null);
    }
}
