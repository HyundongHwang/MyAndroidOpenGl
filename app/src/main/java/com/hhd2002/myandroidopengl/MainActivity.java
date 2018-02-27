package com.hhd2002.myandroidopengl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.lang.reflect.Field;

/**
 * Created by hhd20 on 2/23/2018.
 */

public class MainActivity extends AppCompatActivity {

    private MyGLSurfaceView _surfaceView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_main);
        _createSurfaceView();


        LinearLayout ll_btn_list = this.findViewById(R.id.ll_btn_list);
        Field[] fields = MyMainModel.FuncTypes.class.getFields();

        for (Field f : fields) {
            if (f.isSynthetic())
                continue;

            String name = f.getName();
            final Button button = new Button(this);
            button.setText(name);
            ll_btn_list.addView(button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String buttonText = button.getText().toString();
                    MyMainModel.FuncTypes funcType = Enum.valueOf(MyMainModel.FuncTypes.class, buttonText);
                    MyMainModel.getInstance().funcType = funcType;
                    _createSurfaceView();
                }
            });
        }
    }

    private void _createSurfaceView() {
        LinearLayout ll_main = this.findViewById(R.id.ll_main);

        if (_surfaceView != null)
            ll_main.removeView(_surfaceView);

        _surfaceView = new MyGLSurfaceView(this);
        ll_main.addView(_surfaceView);
        LinearLayout.LayoutParams llParams = (LinearLayout.LayoutParams) _surfaceView.getLayoutParams();
        llParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
        llParams.height = 0;
        llParams.weight = 100;
    }
}

