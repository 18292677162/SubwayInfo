package com.example.subwayinfo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.subwayinfo.R;
import com.example.subwayinfo.ui.station.line1.Line1_bh_Activity;
import com.example.subwayinfo.ui.station.line1.Line1_fdzmy_Activity;
import com.example.subwayinfo.ui.station.line1.Line1_fhslgy_Activity;
import com.example.subwayinfo.ui.station.line1.Line1_sll_Activity;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private ImageButton bt_star = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

/*
        bt_star = findViewById(R.id.bt_star);
        bt_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转动作对象
                Intent intent1 = new Intent();
                // 起始、目的界面
                intent1.setClass(MainActivity.this, Line1_fhslgy_Activity.class);
                // 跳转
                startActivity(intent1);
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
        Intent intent = new Intent();
        switch (item.getItemId()) {
            case R.id.line1_fhslgy:
                Toast.makeText(MainActivity.this, "一号线-沣河森林公园", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line1_fhslgy_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line1_bh:
                Toast.makeText(MainActivity.this, "一号线-北槐", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line1_bh_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line1_sll:
                Toast.makeText(MainActivity.this, "一号线-上林路", Toast.LENGTH_LONG).show();
                intent.setClass(MainActivity.this, Line1_sll_Activity.class);
                // 跳转
                startActivity(intent);
            case R.id.line1_fdzmy:
                Toast.makeText(MainActivity.this, "一号线-沣东自贸园", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line1_fdzmy_Activity.class);
                // 跳转
                startActivity(intent);
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
