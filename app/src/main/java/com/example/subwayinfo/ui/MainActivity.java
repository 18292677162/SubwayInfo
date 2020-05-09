package com.example.subwayinfo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.subwayinfo.R;
import com.example.subwayinfo.ui.station.line1.Line1_bdj_Activity;
import com.example.subwayinfo.ui.station.line1.Line1_bp_Activity;
import com.example.subwayinfo.ui.station.line1.Line1_ch_Activity;
import com.example.subwayinfo.ui.station.line1.Line1_clp_Activity;
import com.example.subwayinfo.ui.station.line1.Line1_cym_Activity;
import com.example.subwayinfo.ui.station.line1.Line1_hcl_Activity;
import com.example.subwayinfo.ui.station.line1.Line1_kfl_Activity;
import com.example.subwayinfo.ui.station.line1.Line1_kym_Activity;
import com.example.subwayinfo.ui.station.line1.Line1_ldl_Activity;
import com.example.subwayinfo.ui.station.line1.Line1_sjq_Activity;
import com.example.subwayinfo.ui.station.line1.Line1_sq_Activity;
import com.example.subwayinfo.ui.station.line1.Line1_thm_Activity;
import com.example.subwayinfo.ui.station.line1.Line1_wlk_Activity;
import com.example.subwayinfo.ui.station.line1.Line1_wsl_Activity;
import com.example.subwayinfo.ui.station.line1.Line1_yxm_Activity;
import com.example.subwayinfo.ui.station.line1.Line1_zy_Activity;
import com.example.subwayinfo.ui.station.line1.Line1_hwz_Activity;
import com.example.subwayinfo.ui.station.line1.Line1_zh_Activity;
import com.example.subwayinfo.ui.station.line2.Line2_aym_Activity;
import com.example.subwayinfo.ui.station.line2.Line2_bkz_Activity;
import com.example.subwayinfo.ui.station.line2.Line2_by_Activity;
import com.example.subwayinfo.ui.station.line2.Line2_dmgx_Activity;
import com.example.subwayinfo.ui.station.line2.Line2_fcwl_Activity;
import com.example.subwayinfo.ui.station.line2.Line2_fxy_Activity;
import com.example.subwayinfo.ui.station.line2.Line2_htc_Activity;
import com.example.subwayinfo.ui.station.line2.Line2_hzzx_Activity;
import com.example.subwayinfo.ui.station.line2.Line2_lsy_Activity;
import com.example.subwayinfo.ui.station.line2.Line2_nsm_Activity;
import com.example.subwayinfo.ui.station.line2.Line2_stsg_Activity;
import com.example.subwayinfo.ui.station.line2.Line2_sy_Activity;
import com.example.subwayinfo.ui.station.line2.Line2_tyc_Activity;
import com.example.subwayinfo.ui.station.line2.Line2_wqn_Activity;
import com.example.subwayinfo.ui.station.line2.Line2_wyj_Activity;
import com.example.subwayinfo.ui.station.line2.Line2_xz_Activity;
import com.example.subwayinfo.ui.station.line2.Line2_xzzx_Activity;
import com.example.subwayinfo.ui.station.line2.Line2_ydgy_Activity;
import com.example.subwayinfo.ui.station.line2.Line2_ynm_Activity;
import com.example.subwayinfo.ui.station.line2.Line2_zl_Activity;
import com.example.subwayinfo.ui.station.line3.Line3_bct_Activity;
import com.example.subwayinfo.ui.station.line3.Line3_cbzx_Activity;
import com.example.subwayinfo.ui.station.line3.Line3_clgy_Activity;
import com.example.subwayinfo.ui.station.line3.Line3_dyt_Activity;
import com.example.subwayinfo.ui.station.line3.Line3_gtm_Activity;
import com.example.subwayinfo.ui.station.line3.Line3_hjm_Activity;
import com.example.subwayinfo.ui.station.line3.Line3_jxc_Activity;
import com.example.subwayinfo.ui.station.line3.Line3_kjl_Activity;
import com.example.subwayinfo.ui.station.line3.Line3_qls_Activity;
import com.example.subwayinfo.ui.station.line3.Line3_sjj_Activity;
import com.example.subwayinfo.ui.station.line3.Line3_tbnl_Activity;
import com.example.subwayinfo.ui.station.line3.Line3_tht_Activity;
import com.example.subwayinfo.ui.station.line3.Line3_xhw_Activity;
import com.example.subwayinfo.ui.station.line3.Line3_xjm_Activity;
import com.example.subwayinfo.ui.station.line3.Line3_xnl_Activity;
import com.example.subwayinfo.ui.station.line3.Line3_yhz_Activity;
import com.example.subwayinfo.ui.station.line3.Line3_ypm_Activity;
import com.example.subwayinfo.ui.station.line3.Line3_yxm_Activity;
import com.example.subwayinfo.ui.station.line3.Line3_zbbl_Activity;
import com.example.subwayinfo.ui.station.line4.Line4_bhc_Activity;
import com.example.subwayinfo.ui.station.line4.Line4_bkz_Activity;
import com.example.subwayinfo.ui.station.line4.Line4_cql_Activity;
import com.example.subwayinfo.ui.station.line4.Line4_dcaj_Activity;
import com.example.subwayinfo.ui.station.line4.Line4_dcs_Activity;
import com.example.subwayinfo.ui.station.line4.Line4_dmg_Activity;
import com.example.subwayinfo.ui.station.line4.Line4_dmgb_Activity;
import com.example.subwayinfo.ui.station.line4.Line4_dtfry_Activity;
import com.example.subwayinfo.ui.station.line4.Line4_fcjl_Activity;
import com.example.subwayinfo.ui.station.line4.Line4_fcsel_Activity;
import com.example.subwayinfo.ui.station.line4.Line4_ftl_Activity;
import com.example.subwayinfo.ui.station.line4.Line4_hpm_Activity;
import com.example.subwayinfo.ui.station.line4.Line4_htdd_Activity;
import com.example.subwayinfo.ui.station.line4.Line4_htdl_Activity;
import com.example.subwayinfo.ui.station.line4.Line4_htxc_Activity;
import com.example.subwayinfo.ui.station.line4.Line4_hyd_Activity;
import com.example.subwayinfo.ui.station.line4.Line4_jht_Activity;
import com.example.subwayinfo.ui.station.line4.Line4_jzkjdx_Activity;
import com.example.subwayinfo.ui.station.line4.Line4_qjcx_Activity;
import com.example.subwayinfo.ui.station.line4.Line4_szdd_Activity;
import com.example.subwayinfo.ui.station.line4.Line4_szyyy_Activity;
import com.example.subwayinfo.ui.station.line4.Line4_wjl_Activity;
import com.example.subwayinfo.ui.station.line4.Line4_xakjdx_Activity;
import com.example.subwayinfo.ui.station.line4.Line4_yjz_Activity;
import com.example.subwayinfo.ui.station.line4.Line4_ysl_Activity;
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
                intent1.setClass(MainActivity.this, Line1_hwz_Activity.class);
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
            case R.id.line1_hwz:
                Toast.makeText(MainActivity.this, "一号线-后卫寨", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line1_hwz_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line1_sq:
                Toast.makeText(MainActivity.this, "一号线-三桥", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line1_sq_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line1_zh:
                Toast.makeText(MainActivity.this, "一号线-皂河", Toast.LENGTH_LONG).show();
                intent.setClass(MainActivity.this, Line1_zh_Activity.class);
                // 跳转
                startActivity(intent);
            case R.id.line1_zy:
                Toast.makeText(MainActivity.this, "一号线-枣园", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line1_zy_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line1_hcl:
                Toast.makeText(MainActivity.this, "一号线-汉城路", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line1_hcl_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line1_kym:
                Toast.makeText(MainActivity.this, "一号线-开远门", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line1_kym_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line1_ldl:
                Toast.makeText(MainActivity.this, "一号线-劳动路", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line1_ldl_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line1_yxm:
                Toast.makeText(MainActivity.this, "一号线-玉祥门", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line1_yxm_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line1_sjq:
                Toast.makeText(MainActivity.this, "一号线-洒金桥", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line1_sjq_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line1_bdj:
                Toast.makeText(MainActivity.this, "一号线-北大街", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line1_bdj_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line1_wlk:
                Toast.makeText(MainActivity.this, "一号线-五路口", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line1_wlk_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line1_cym:
                Toast.makeText(MainActivity.this, "一号线-朝阳门", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line1_cym_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line1_kfl:
                Toast.makeText(MainActivity.this, "一号线-康复路", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line1_kfl_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line1_thm:
                Toast.makeText(MainActivity.this, "一号线-通化门", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line1_thm_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line1_wsl:
                Toast.makeText(MainActivity.this, "一号线-万寿路", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line1_wsl_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line1_clp:
                Toast.makeText(MainActivity.this, "一号线-长乐坡", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line1_clp_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line1_ch:
                Toast.makeText(MainActivity.this, "一号线-浐河", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line1_ch_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line1_bp:
                Toast.makeText(MainActivity.this, "一号线-半坡", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line1_bp_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line2_bkz:
                Toast.makeText(MainActivity.this, "二号线-北客站", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line2_bkz_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line2_by:
                Toast.makeText(MainActivity.this, "二号线-北苑", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line2_by_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line2_ydgy:
                Toast.makeText(MainActivity.this, "二号线-运动公园", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line2_ydgy_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line2_xzzx:
                Toast.makeText(MainActivity.this, "二号线-行政中心", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line2_xzzx_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line2_fcwl:
                Toast.makeText(MainActivity.this, "二号线-凤城五路", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line2_fcwl_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line2_stsg:
                Toast.makeText(MainActivity.this, "二号线-市图书馆", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line2_stsg_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line2_dmgx:
                Toast.makeText(MainActivity.this, "二号线-大明宫西", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line2_dmgx_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line2_lsy:
                Toast.makeText(MainActivity.this, "二号线-龙首原", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line2_lsy_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line2_aym:
                Toast.makeText(MainActivity.this, "二号线-安远门", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line2_aym_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line2_bdj:
                Toast.makeText(MainActivity.this, "二号线-北大街", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line1_bdj_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line2_zl:
                Toast.makeText(MainActivity.this, "二号线-钟楼", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line2_zl_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line2_ynm:
                Toast.makeText(MainActivity.this, "二号线-永宁门", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line2_ynm_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line2_nsm:
                Toast.makeText(MainActivity.this, "二号线-南稍门", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line2_nsm_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line2_tyc:
                Toast.makeText(MainActivity.this, "二号线-体育场", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line2_tyc_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line2_xz:
                Toast.makeText(MainActivity.this, "二号线-小寨", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line2_xz_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line2_wyj:
                Toast.makeText(MainActivity.this, "二号线-纬一街", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line2_wyj_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line2_hzzx:
                Toast.makeText(MainActivity.this, "二号线-会展中心", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line2_hzzx_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line2_sy:
                Toast.makeText(MainActivity.this, "二号线-三爻", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line2_sy_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line2_fxy:
                Toast.makeText(MainActivity.this, "二号线-凤栖原", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line2_fxy_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line2_htc:
                Toast.makeText(MainActivity.this, "二号线-航天城", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line2_htc_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line2_wqn:
                Toast.makeText(MainActivity.this, "二号线-韦曲南", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line2_wqn_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line3_yhz:
                Toast.makeText(MainActivity.this, "三号线-鱼化寨", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line3_yhz_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line3_zbbl:
                Toast.makeText(MainActivity.this, "三号线-丈八北路", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line3_zbbl_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line3_ypm:
                Toast.makeText(MainActivity.this, "三号线-延平门", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line3_ypm_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line3_kjl:
                Toast.makeText(MainActivity.this, "三号线-科技路", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line3_kjl_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line3_tbnl:
                Toast.makeText(MainActivity.this, "三号线-太白南路", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line3_tbnl_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line3_jxc:
                Toast.makeText(MainActivity.this, "三号线-吉祥村", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line3_jxc_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line3_xz:
                Toast.makeText(MainActivity.this, "三号线-小寨", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line2_xz_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line3_dyt:
                Toast.makeText(MainActivity.this, "三号线-大雁塔", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line3_dyt_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line3_bct:
                Toast.makeText(MainActivity.this, "三号线-北池头", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line3_bct_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line3_qls:
                Toast.makeText(MainActivity.this, "三号线-青龙寺", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line3_qls_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line3_yxm:
                Toast.makeText(MainActivity.this, "三号线-延兴门", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line3_yxm_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line3_xnl:
                Toast.makeText(MainActivity.this, "三号线-咸宁路", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line3_xnl_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line3_clgy:
                Toast.makeText(MainActivity.this, "三号线-长乐公园", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line3_clgy_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line3_thm:
                Toast.makeText(MainActivity.this, "三号线-通化门", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line1_thm_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line3_hjm:
                Toast.makeText(MainActivity.this, "三号线-胡家庙", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line3_hjm_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line3_sjj:
                Toast.makeText(MainActivity.this, "三号线-石家街", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line3_sjj_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line3_xjm:
                Toast.makeText(MainActivity.this, "三号线-辛家庙", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line3_xjm_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line3_gtm:
                Toast.makeText(MainActivity.this, "三号线-广泰门", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line3_gtm_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line3_tht:
                Toast.makeText(MainActivity.this, "三号线-桃花潭", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line3_tht_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line3_cbzx:
                Toast.makeText(MainActivity.this, "三号线-浐灞中心", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line3_cbzx_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line3_xhw:
                Toast.makeText(MainActivity.this, "三号线-香湖湾", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line3_xhw_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line4_htxc:
                Toast.makeText(MainActivity.this, "四号线-航天新城", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line4_htxc_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line4_htdl:
                Toast.makeText(MainActivity.this, "四号线-航天东路", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line4_htdl_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line4_szdd:
                Toast.makeText(MainActivity.this, "四号线-神舟大道", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line4_szdd_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line4_dcaj:
                Toast.makeText(MainActivity.this, "四号线-东长安街", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line4_dcaj_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line4_ftl:
                Toast.makeText(MainActivity.this, "四号线-飞天路", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line4_ftl_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line4_htdd:
                Toast.makeText(MainActivity.this, "四号线-航天大道", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line4_htdd_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line4_jht:
                Toast.makeText(MainActivity.this, "四号线-金滹沱", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line4_jht_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line4_qjcx:
                Toast.makeText(MainActivity.this, "四号线-曲江池西", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line4_qjcx_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line4_dtfry:
                Toast.makeText(MainActivity.this, "四号线-大唐芙蓉园", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line4_dtfry_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line4_dyt:
                Toast.makeText(MainActivity.this, "四号线-大雁塔", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line3_dyt_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line4_xakjdx:
                Toast.makeText(MainActivity.this, "四号线-西安科技大学", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line4_xakjdx_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line4_jzkjdx:
                Toast.makeText(MainActivity.this, "四号线-建筑科技大学", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line4_jzkjdx_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line4_hpm:
                Toast.makeText(MainActivity.this, "四号线-和平门", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line4_hpm_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line4_dcs:
                Toast.makeText(MainActivity.this, "四号线-大差市", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line4_dcs_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line4_wlk:
                Toast.makeText(MainActivity.this, "四号线-五路口", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line1_wlk_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line4_hyd:
                Toast.makeText(MainActivity.this, "四号线-含元殿", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line4_hyd_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line4_dmg:
                Toast.makeText(MainActivity.this, "四号线-大明宫", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line4_dmg_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line4_dmgb:
                Toast.makeText(MainActivity.this, "四号线-大明宫北", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line4_dmgb_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line4_yjz:
                Toast.makeText(MainActivity.this, "四号线-余家寨", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line4_yjz_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line4_bhc:
                Toast.makeText(MainActivity.this, "四号线-百花村", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line4_bhc_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line4_cql:
                Toast.makeText(MainActivity.this, "四号线-常青路", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line4_cql_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line4_szyyy:
                Toast.makeText(MainActivity.this, "四号线-市中医医院", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line4_szyyy_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line4_xzzx:
                Toast.makeText(MainActivity.this, "四号线-行政中心", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line2_xzzx_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line4_wjl:
                Toast.makeText(MainActivity.this, "四号线-文景路", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line4_wjl_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line4_fcjl:
                Toast.makeText(MainActivity.this, "四号线-凤城九路", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line4_fcjl_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line4_fcsel:
                Toast.makeText(MainActivity.this, "四号线-凤城十二路", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line4_fcsel_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line4_ysl:
                Toast.makeText(MainActivity.this, "四号线-元朔路", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line4_ysl_Activity.class);
                // 跳转
                startActivity(intent);
                break;
            case R.id.line4_bkz:
                Toast.makeText(MainActivity.this, "四号线-北客站", Toast.LENGTH_LONG).show();
                // 起始、目的界面
                intent.setClass(MainActivity.this, Line4_bkz_Activity.class);
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
