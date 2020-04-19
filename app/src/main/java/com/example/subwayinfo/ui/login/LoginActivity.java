package com.example.subwayinfo.ui.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.subwayinfo.R;
import com.example.subwayinfo.SubwayJNI;
import com.example.subwayinfo.ui.MainActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText tv_login_username = null;
    private EditText tv_login_password = null;

    private Button bt_login = null;

    String Tag = "Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tv_login_username = (EditText)findViewById(R.id.tv_login_username);
        tv_login_password = (EditText)findViewById(R.id.tv_login_passwrod);
        bt_login = (Button)findViewById(R.id.bt_login);


        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean login_res = true;

                SubwayJNI.getInstance().hello_jni();
                // 登录点击事件
                login_res = SubwayJNI.getInstance().login(tv_login_username.getText().toString(), tv_login_password.getText().toString(), true);
                // 登陆成功 -- 跳转
                // 跳转动作对象
                Intent intent = new Intent();
                // 起始、目的界面
                intent.setClass(LoginActivity.this, MainActivity.class);
                // 跳转
                startActivity(intent);

                Log.e(Tag, "登录成功");

            }
        });
    }
}
