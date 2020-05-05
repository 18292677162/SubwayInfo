package com.example.subwayinfo.ui.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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

    private boolean isSMSCheck = false;

    String Tag = "Activity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tv_login_username = (EditText) findViewById(R.id.tv_login_username);
        tv_login_password = (EditText) findViewById(R.id.tv_login_passwrod);
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_regist = (Button) findViewById(R.id.bt_register);

        GetPermission();

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

                if ((password.isEmpty() == true) || (Pattern.compile(regex_password).matcher(password).matches() == false)) {
                    Log.e(Tag, "密码格式不正确");
                    Toast.makeText(getApplicationContext(), "密码格式不正确", Toast.LENGTH_SHORT).show();
                    return;
                }

                SubwayJNI.getInstance().hello_jni();
                // 登录点击事件
                login_res = SubwayJNI.getInstance().login(username, password, true);
                // 登陆成功 -- 跳转
                if (login_res == true) {
                    // 跳转动作对象
                    Intent intent2 = new Intent();
                    // 禁止跳回
                    intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    // 起始、目的界面
                    intent2.setClass(LoginActivity.this, MainActivity.class);
                    // 跳转
                    startActivity(intent2);
                    Log.e(Tag, "登录成功");
                    Toast.makeText(getApplicationContext(), "登录成功，欢迎回来！", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(Tag, "登录失败");
                    Toast.makeText(getApplicationContext(), "登录失败！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转注册界面
                // 跳转动作对象
                Intent intent = new Intent();
                // 禁止跳回
                // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                // 起始、目的界面
                intent.setClass(LoginActivity.this, RegistActivity.class);
                // 跳转
                startActivity(intent);
            }
        });
    }

    private void GetPermission()
    {
        // 获取权限

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                // 检查权限状态
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
                    //  用户彻底拒绝授予权限，一般会提示用户进入设置权限界面
                } else {
                    //  用户未彻底拒绝授予权限
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // 检查权限状态
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    //  用户彻底拒绝授予权限，一般会提示用户进入设置权限界面
                } else {
                    //  用户未彻底拒绝授予权限
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // 检查权限状态
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
                    //  用户彻底拒绝授予权限，一般会提示用户进入设置权限界面
                } else {
                    //  用户未彻底拒绝授予权限
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // 检查权限状态
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    //  用户彻底拒绝授予权限，一般会提示用户进入设置权限界面
                } else {
                    //  用户未彻底拒绝授予权限
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // 检查权限状态
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    //  用户彻底拒绝授予权限，一般会提示用户进入设置权限界面
                } else {
                    //  用户未彻底拒绝授予权限
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
                // 检查权限状态
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_NETWORK_STATE)) {
                    //  用户彻底拒绝授予权限，一般会提示用户进入设置权限界面
                } else {
                    //  用户未彻底拒绝授予权限
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 1);
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
                // 检查权限状态
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_WIFI_STATE)) {
                    //  用户彻底拒绝授予权限，一般会提示用户进入设置权限界面
                } else {
                    //  用户未彻底拒绝授予权限
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_WIFI_STATE}, 1);
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
                // 检查权限状态
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CHANGE_WIFI_STATE)) {
                    //  用户彻底拒绝授予权限，一般会提示用户进入设置权限界面
                } else {
                    //  用户未彻底拒绝授予权限
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CHANGE_WIFI_STATE}, 1);
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS) != PackageManager.PERMISSION_GRANTED) {
                // 检查权限状态
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS)) {
                    //  用户彻底拒绝授予权限，一般会提示用户进入设置权限界面
                } else {
                    //  用户未彻底拒绝授予权限
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS}, 1);
                }
            }
        }



    }

}