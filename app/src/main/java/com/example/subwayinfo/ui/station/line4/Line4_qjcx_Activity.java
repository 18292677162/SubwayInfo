package com.example.subwayinfo.ui.station.line4;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import com.example.subwayinfo.R;

import android.os.Bundle;

public class Line4_qjcx_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line4_qjcx_);
        //将自带的标题栏隐藏掉
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
    }
}
