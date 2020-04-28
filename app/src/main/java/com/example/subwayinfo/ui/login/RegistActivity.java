package com.example.subwayinfo.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import com.example.subwayinfo.R;
import com.example.subwayinfo.SubwayJNI;
import com.mob.MobSDK;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


public class RegistActivity extends AppCompatActivity {

    private Button bt_regist_regist = null;
    private Button bt_SMS = null;
    private TextView tv_username = null;
    private TextView tv_password = null;
    private TextView tv_re_password = null;
    private TextView tv_SMS = null;
    private EventHandler eH = null;

    String Tag = "Activity";

    boolean regist_res = false;
    boolean SMS_res = false;

    // 正则表达式
    String regex_username = "^(1[3456789])\\d{9}$";
    String regex_password = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";


    private void Init(){
        bt_regist_regist = findViewById(R.id.bt_regist_register);
        bt_SMS = findViewById(R.id.bt_SMS);
        tv_username = findViewById(R.id.tv_regist_username);
        tv_password = findViewById(R.id.tv_regist_passwrod);
        tv_re_password = findViewById(R.id.tv_re_regist_passwrod);
        tv_SMS = findViewById(R.id.tv_SMS);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        MobSDK.init(this, "2ee28d8f9d3b8", "99dfea32e0b6283e158a6a108d0e471c");

        // 初始化控件
        Init();

        // SMS注册回调
        eH = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        Log.e("SMS", "提交验证码成功", null);
                        SMS_res = true;
                        if (regist_res == true && SMS_res == true) {
                            Log.e(Tag, "注册成功");

                            // 注册成功动作
                        } else {
                            Log.e(Tag, "注册失败");
                        }
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        Log.e("SMS", "获取验证码成功", null);
                    } else {
                        Log.e("SMS", "提交验证码失败", null);
                        ((Throwable) data).printStackTrace();
                    }
                }
            }
        };

        // 注册回调监听接口
        SMSSDK.registerEventHandler(eH);


        // 验证码按钮
        bt_SMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = tv_username.getText().toString();

                if ((username.isEmpty() == true) || (username.length() != 11) || (Pattern.compile(regex_username).matcher(username).matches() == false)) {
                    //日志
                    Log.e(Tag, "手机号格式不正确");
                    //吐司
                    Toast.makeText(getApplicationContext(), "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 在尝试读取通信录时以弹窗提示用户（可选功能）
                // SMSSDK.setAskPermisionOnReadContact(true);

                // 发送验证码
                SMSSDK.getVerificationCode("86", username);
                Log.e(Tag, "发送验证码到 " + username, null);
            }
        });

        // 注册按钮
        bt_regist_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 用户名 密码
                String username = tv_username.getText().toString();
                String password = tv_password.getText().toString();
                String re_password = tv_re_password.getText().toString();


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

                if(password != re_password){
                    Log.e(Tag, "密码格式不正确");
                    Toast.makeText(getApplicationContext(), "两次输入的密码不一致, 请重新输入", Toast.LENGTH_SHORT).show();
                    tv_password.setText("");
                    tv_re_password.setText("");
                    return;
                }

                SubwayJNI.getInstance().hello_jni();

                // 注册点击事件
                regist_res = SubwayJNI.getInstance().regist(username, password);

                // 提交验证码，其中的code表示验证码，如“1357”
                SMSSDK.submitVerificationCode("86", username, tv_SMS.getText().toString());
            }

        });
    }

    @Override
    protected void onDestroy(){
        // 销毁
        SMSSDK.unregisterEventHandler(eH);
        super.onDestroy();
    }
}
