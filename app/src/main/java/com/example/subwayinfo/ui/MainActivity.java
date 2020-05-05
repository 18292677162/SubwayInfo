package com.example.subwayinfo.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.subwayinfo.R;
import com.example.subwayinfo.ui.login.LoginActivity;
import com.google.android.material.navigation.NavigationView;

import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // getSupportActionBar().setDisplayShowTitleEnabled(false);//隐藏标题
        // FloatingActionButton fab = findViewById(R.id.fab);
        // 圆圈点击事件
        /*
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        // 将每个菜单ID作为一组ID传递，因为每个菜单都应被视为顶级目的地


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.line1:
                Toast.makeText(MainActivity.this, "一号线", Toast.LENGTH_LONG).show();
                // 跳转动作对象
                Intent intent = new Intent();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line1Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line2:
                Toast.makeText(MainActivity.this, "二号线", Toast.LENGTH_LONG).show();
                // 跳转动作对象
                Intent intent1 = new Intent();
                // 起始、目的界面
                intent1.setClass(MainActivity.this, Line2Activity.class);
                // 跳转
                startActivity(intent1);
                break;
            case R.id.line3:
                Toast.makeText(MainActivity.this, "三号线", Toast.LENGTH_LONG).show();
                // 跳转动作对象
                Intent intent2 = new Intent();
                // 起始、目的界面
                intent2.setClass(MainActivity.this, Line3Activity.class);
                // 跳转
                startActivity(intent2);
                break;
            case R.id.line4:
                Toast.makeText(MainActivity.this, "四号线", Toast.LENGTH_LONG).show();
                // 跳转动作对象
                Intent intent3 = new Intent();
                // 起始、目的界面
                intent3.setClass(MainActivity.this, Line4Activity.class);
                // 跳转
                startActivity(intent3);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //填充菜单, 将项目添加到操作栏
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
