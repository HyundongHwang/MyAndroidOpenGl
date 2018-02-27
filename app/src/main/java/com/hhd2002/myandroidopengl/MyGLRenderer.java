package com.hhd2002.myandroidopengl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by hhd20 on 2/23/2018.
 */

public class MyGLRenderer implements GLSurfaceView.Renderer {
    private MyTriangle _triangle;

    private final float[] _mVPMatrix = new float[16];
    private final float[] _projectionMatrix = new float[16];
    private final float[] _viewMatrix = new float[16];
    private final float[] _rotationMatrix = new float[16];
    private float _angle;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        _triangle = new MyTriangle();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        Matrix.frustumM(_projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        if (MyMainModel.getInstance().funcType == MyMainModel.FuncTypes.SIMPLE) {
            _triangle.draw();


        } else if (MyMainModel.getInstance().funcType == MyMainModel.FuncTypes.PROJECTION) {
            Matrix.setLookAtM(_viewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
            Matrix.multiplyMM(_mVPMatrix, 0, _projectionMatrix, 0, _viewMatrix, 0);
            _triangle.drawWithMvpMatrix(_mVPMatrix);


        } else if (MyMainModel.getInstance().funcType == MyMainModel.FuncTypes.ROTATION_BY_TIMER) {
            Matrix.setLookAtM(_viewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
            Matrix.multiplyMM(_mVPMatrix, 0, _projectionMatrix, 0, _viewMatrix, 0);
            float[] scratch = new float[16];
            long time = SystemClock.uptimeMillis() % 4000L;
            float angle = 0.090f * time;
            Matrix.setRotateM(_rotationMatrix, 0, angle, 0, 0, -1.0f);
            Matrix.multiplyMM(scratch, 0, _mVPMatrix, 0, _rotationMatrix, 0);
            _triangle.drawWithMvpMatrix(scratch);


        } else if (MyMainModel.getInstance().funcType == MyMainModel.FuncTypes.ROTATION_BY_TOUCH) {
            Matrix.setLookAtM(_viewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
            Matrix.multiplyMM(_mVPMatrix, 0, _projectionMatrix, 0, _viewMatrix, 0);
            float[] scratch = new float[16];
            Matrix.setRotateM(_rotationMatrix, 0, _angle, 0, 0, -1.0f);
            Matrix.multiplyMM(scratch, 0, _mVPMatrix, 0, _rotationMatrix, 0);
            _triangle.drawWithMvpMatrix(scratch);
        }


    }

    public float getAngle() {
        return _angle;
    }

    public void setAngle(float angle) {
        this._angle = angle;
    }
}

