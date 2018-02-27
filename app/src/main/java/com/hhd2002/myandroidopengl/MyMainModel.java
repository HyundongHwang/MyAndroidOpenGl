package com.hhd2002.myandroidopengl;

public class MyMainModel {
    public enum FuncTypes {
        SIMPLE,
        PROJECTION,
        ROTATION_BY_TIMER,
        ROTATION_BY_TOUCH,
    }

    public FuncTypes funcType;

    private static MyMainModel _this;

    public static MyMainModel getInstance() {
        synchronized (MyMainModel.class) {
            if (_this == null)
                _this = new MyMainModel();

            return _this;
        }
    }
}
