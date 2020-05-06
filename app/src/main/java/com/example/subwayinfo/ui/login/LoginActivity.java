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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private EditText tv_login_username = null;
    private EditText tv_login_password = null;

    private Button bt_login = null;
    private Button bt_regist = null;

    private boolean isSMSCheck = false;

    String Tag = "Activity";

    String[] permissions = new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
            Manifest.permission.READ_CONTACTS,};
    //2、创建一个mPermissionList，逐个判断哪些权限未授予，未授予的权限存储到mPerrrmissionList中
    List<String> mPermissionList = new ArrayList<>();

    private final int mRequestCode = 100;//权限请求码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tv_login_username = (EditText) findViewById(R.id.tv_login_username);
        tv_login_password = (EditText) findViewById(R.id.tv_login_passwrod);
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_regist = (Button) findViewById(R.id.bt_register);

        //获取权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            initPermission();
        }

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

    //权限判断和申请
    private void initPermission() {

        mPermissionList.clear();//清空没有通过的权限

        //逐个判断你要的权限是否已经通过
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);//添加还未授予的权限
            }
        }

        //申请权限
        if (mPermissionList.size() > 0) {//有权限没有通过，需要申请
            ActivityCompat.requestPermissions(this, permissions, mRequestCode);
        }else{
            //说明权限都已经通过，可以做你想做的事情去
        }
    }

}