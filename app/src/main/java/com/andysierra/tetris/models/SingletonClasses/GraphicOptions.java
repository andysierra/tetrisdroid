package com.andysierra.tetris.models.SingletonClasses;

public class GraphicOptions
{
    private static final String TAG = "GraphicOptions";

    private static GraphicOptions instance;
    public boolean android_graphics_paint_antialias;

    private GraphicOptions() {
        android_graphics_paint_antialias = true;
    }

    public static GraphicOptions getInstance() {
        if(instance == null)
            instance = new GraphicOptions();
        return instance;
    }

    // SETTERS Y GETTERS
    public boolean isAndroid_graphics_paint_antialias() {
        return android_graphics_paint_antialias;
    }

    public void setAndroid_graphics_paint_antialias(boolean android_graphics_paint_antialias) {
        this.android_graphics_paint_antialias = android_graphics_paint_antialias;
    }
}
