package com.example.subwayinfo.ui.login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.subwayinfo.R;
import com.example.subwayinfo.SubwayJNI;
import com.mob.MobSDK;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

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

    String Tag = "Activity";

    boolean regist_res = false;
    String SMS_ret = null;

    String code = null;

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

        //将自带的标题栏隐藏掉
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }

        // 初始化控件
        Init();

        MobSDK.submitPolicyGrantResult(true, null);


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

                //获取验证码

                Log.e("发送的手机号",username.toString());
                SMSSDK.getVerificationCode("86" , username);

                // 发送验证码
                // code = String.valueOf ((int) ((Math.random() * 9 + 1) * 100000));

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

                if(!password.equals(re_password)){
                    Log.e(Tag, "密码格式不正确");
                    Toast.makeText(getApplicationContext(), "两次输入的密码不一致, 请重新输入", Toast.LENGTH_SHORT).show();
                    tv_password.setText("");
                    tv_re_password.setText("");
                    return;
                }

                SubwayJNI.getInstance().hello_jni();
                Log.e("验证码是:" , code ,null);

                //提交验证码验证
                SMSSDK.submitVerificationCode("86",username,tv_SMS.getText().toString());


            }
        });

        EventHandler handler = new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE){

                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(RegistActivity.this,"验证成功",Toast.LENGTH_SHORT).show();
                                // 用户名 密码
                                String username = tv_username.getText().toString();
                                String password = tv_password.getText().toString();
                                // 注册
                                regist_res = SubwayJNI.getInstance().regist(username, password);
                                if (regist_res == true){
                                    // 注册成功 跳转
                                    Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                                    // 跳转动作对象
                                    Intent intent2 = new Intent();
                                    // 禁止跳回
                                    intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    // 起始、目的界面
                                    intent2.setClass(RegistActivity.this, LoginActivity.class);
                                    // 跳转
                                    startActivity(intent2);
                                }else if(regist_res == false){
                                    Toast.makeText(getApplicationContext(), "注册失败", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getApplicationContext(), "由于未知原因，注册失败", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }else if (event == SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(RegistActivity.this,"语音验证发送",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        //获取验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(RegistActivity.this,"验证码已发送",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                        Log.i("test","test");
                    }
                }else{
                    ((Throwable)data).printStackTrace();
                    Throwable throwable = (Throwable) data;
                    throwable.printStackTrace();
                    Log.e(Tag,throwable.toString());
                    try {
                        JSONObject obj = new JSONObject(throwable.getMessage());
                        final String des = obj.optString("detail");
                        if (!TextUtils.isEmpty(des)){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegistActivity.this,des,Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };


        SMSSDK.registerEventHandler(handler);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}
