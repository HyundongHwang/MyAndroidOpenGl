package com.hhd2002.myandroidopengl;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class MyTriangle {
    private final int COORDS_PER_VERTEX = 3;

    private final float COORDS_DATA[] = {
            0.0f, 0.622008459f, 0.0f, // top
            -0.5f, -0.311004243f, 0.0f, // bottom left
            0.5f, -0.311004243f, 0.0f  // bottom right
    };

    float COLOR[] = {0.63671875f, 0.76953125f, 0.22265625f, 1.0f};

    private final int VERTEX_COUNT = COORDS_DATA.length;

    private final int VERTEX_STRIDE = COORDS_PER_VERTEX * 4;

    private final String VERTEX_SHADER_CODE =
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = vPosition;" +
                    "}";

    private final String VERTEX_MATRIX_SHADER_CODE =
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "}";


    private final String FRAGMENT_SHADER_CODE =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    private final FloatBuffer _vertexBuffer;
    private final int _program;
    private int _vPosition;
    private int _vColor;
    private int _matrixHandle;


    public MyTriangle() {
        ByteBuffer bb = ByteBuffer.allocateDirect(COORDS_DATA.length * 4);
        bb.order(ByteOrder.nativeOrder());
        _vertexBuffer = bb.asFloatBuffer();
        _vertexBuffer.put(COORDS_DATA);
        _vertexBuffer.position(0);
        int vertexShader = 0;

        if (MyMainModel.getInstance().funcType == MyMainModel.FuncTypes.SIMPLE) {
            vertexShader = MyOpenglUtils.loadShader(GLES20.GL_VERTEX_SHADER, VERTEX_SHADER_CODE);
        } else {
            vertexShader = MyOpenglUtils.loadShader(GLES20.GL_VERTEX_SHADER, VERTEX_MATRIX_SHADER_CODE);
        }

        int fragmentShader = MyOpenglUtils.loadShader(GLES20.GL_FRAGMENT_SHADER, FRAGMENT_SHADER_CODE);

        _program = GLES20.glCreateProgram();
        GLES20.glAttachShader(_program, vertexShader);
        GLES20.glAttachShader(_program, fragmentShader);
        GLES20.glLinkProgram(_program);
    }

    public void draw() {
        GLES20.glUseProgram(_program);
        _vPosition = GLES20.glGetAttribLocation(_program, "vPosition");
        GLES20.glEnableVertexAttribArray(_vPosition);
        GLES20.glVertexAttribPointer(_vPosition, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, VERTEX_STRIDE, _vertexBuffer);
        _vColor = GLES20.glGetUniformLocation(_program, "vColor");
        GLES20.glUniform4fv(_vColor, 1, COLOR, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, VERTEX_COUNT);
        GLES20.glDisableVertexAttribArray(_vPosition);
    }

    public void drawWithMvpMatrix(float[] mvpMatrix) {
        GLES20.glUseProgram(_program);
        _vPosition = GLES20.glGetAttribLocation(_program, "vPosition");
        GLES20.glEnableVertexAttribArray(_vPosition);
        GLES20.glVertexAttribPointer(_vPosition, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, VERTEX_STRIDE, _vertexBuffer);
        _vColor = GLES20.glGetUniformLocation(_program, "vColor");
        GLES20.glUniform4fv(_vColor, 1, COLOR, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, VERTEX_COUNT);

        _matrixHandle = GLES20.glGetUniformLocation(_program, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(_matrixHandle, 1, false, mvpMatrix, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, VERTEX_COUNT);
        GLES20.glDisableVertexAttribArray(_vPosition);
    }
}
