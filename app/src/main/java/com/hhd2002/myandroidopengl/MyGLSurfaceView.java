package com.hhd2002.myandroidopengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * Created by hhd20 on 2/23/2018.
 */

public class MyGLSurfaceView extends GLSurfaceView {
    private static final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private final MyGLRenderer _renderer;
    private float _x;
    private float _y;

    public MyGLSurfaceView(Context context) {
        super(context);
        this.setEGLContextClientVersion(2);
        _renderer = new MyGLRenderer();
        this.setRenderer(_renderer);

        if (MyMainModel.getInstance().funcType == MyMainModel.FuncTypes.ROTATION_BY_TIMER) {
        } else {
            this.setRenderMode(RENDERMODE_WHEN_DIRTY);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (MyMainModel.getInstance().funcType != MyMainModel.FuncTypes.ROTATION_BY_TOUCH)
            return super.onTouchEvent(event);


        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE: {
                float dx = x - _x;
                float dy = y - _y;

                if (y > this.getHeight() / 2) {
                    dx = dx * -1;
                }

                if (x < this.getWidth() / 2) {
                    dy = dy * -1;
                }

                _renderer.setAngle(_renderer.getAngle() + ((dx + dy) * TOUCH_SCALE_FACTOR));
                this.requestRender();
            }
            break;
        }

        _x = x;
        _y = y;
        return true;
    }
}
