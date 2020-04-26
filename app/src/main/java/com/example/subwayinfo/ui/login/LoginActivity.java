package com.example.subwayinfo.ui.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.subwayinfo.R;
import com.example.subwayinfo.SubwayJNI;
import com.example.subwayinfo.ui.MainActivity;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private EditText tv_login_username = null;
    private EditText tv_login_password = null;

    private Button bt_login = null;
    private Button bt_regist = null;

    String Tag = "Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tv_login_username = (EditText)findViewById(R.id.tv_login_username);
        tv_login_password = (EditText)findViewById(R.id.tv_login_passwrod);
        bt_login = (Button)findViewById(R.id.bt_login);
        bt_regist = (Button)findViewById(R.id.bt_register);

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean login_res = true;

                // 用户名 密码
                String username = tv_login_username.getText().toString();
                String password = tv_login_password.getText().toString();

                // 正则表达式
                String regex_username = "^(1[3456789])\\d{9}$";
                String regex_password = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";

                if ((username.isEmpty() == true) || (username.length() != 11) || (Pattern.compile(regex_username).matcher(username).matches() == false)) {
                    //日志
                    Log.e(Tag, "手机号格式不正确");
                    //吐司
                    Toast.makeText(getApplicationContext(), "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if((password.isEmpty() == true) || (Pattern.compile(regex_password).matcher(password).matches() == false)){
                    Log.e(Tag, "密码格式不正确");
                    Toast.makeText(getApplicationContext(), "密码格式不正确", Toast.LENGTH_SHORT).show();
                    return;
                }

                SubwayJNI.getInstance().hello_jni();
                // 登录点击事件
                login_res = SubwayJNI.getInstance().login(username, password, true);
                // 登陆成功 -- 跳转
                if(login_res == true){
                    // 跳转动作对象
                    Intent intent = new Intent();
                    // 禁止跳回
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    // 起始、目的界面
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    // 跳转
                    startActivity(intent);
                    Log.e(Tag, "登录成功");
                    Toast.makeText(getApplicationContext(), "登录成功，欢迎回来！", Toast.LENGTH_SHORT).show();
                }else{
                    Log.e(Tag, "登录失败");
                    Toast.makeText(getApplicationContext(), "登录失败！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean regist_res = true;
                // 用户名 密码
                String username = tv_login_username.getText().toString();
                String password = tv_login_password.getText().toString();

                // 正则表达式
                String regex_username = "^(1[3456789])\\d{9}$";
                String regex_password = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";

                if ((username.isEmpty() == true) || (username.length() != 11) || (Pattern.compile(regex_username).matcher(username).matches() == false)) {
                    //日志
                    Log.e(Tag, "手机号格式不正确");
                    //吐司
                    Toast.makeText(getApplicationContext(), "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if((password.isEmpty() == true) || (Pattern.compile(regex_password).matcher(password).matches() == false)){
                    Log.e(Tag, "密码格式不正确");
                    Toast.makeText(getApplicationContext(), "密码格式不正确", Toast.LENGTH_SHORT).show();
                    return;
                }

                SubwayJNI.getInstance().hello_jni();
                // 注册点击事件
                regist_res = SubwayJNI.getInstance().regist(username, password);
                if(regist_res == true){
                    Log.e(Tag, "注册成功");
                }else{
                    Log.e(Tag, "注册失败");
                }
            }
        });
    }
}
