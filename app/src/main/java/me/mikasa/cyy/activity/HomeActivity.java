package me.mikasa.cyy.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.mikasa.cyy.R;
import me.mikasa.cyy.fragment.DisFragment;
import me.mikasa.cyy.fragment.MineFragment;
import me.mikasa.cyy.fragment.MusicFragment;
import me.mikasa.cyy.fragment.PlayBarFragment;
import me.mikasa.cyy.receiver.PlayerManagerReceiver;
import me.mikasa.cyy.service.MusicPlayerService;
import me.mikasa.cyy.util.Constant;
import woo.mikasa.lib.base.BaseActivity;

public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;
    private List<Fragment> fragmentList=new ArrayList<>(3);
    private TextView mineTitle,musicTitle,discoveryTitle;
    private static long lastPressed=0;
    private Intent musicServiceIntent;
    //private MusicPlayerService.MusicBinder mBinder;
    private ServiceConnection musicConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            //mBinder=(MusicPlayerService.MusicBinder)binder;
            //mBinder.doSomething();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initData() {
        fragmentList.add(new MineFragment());
        fragmentList.add(new MusicFragment());
        fragmentList.add(new DisFragment());
        musicServiceIntent=new Intent(HomeActivity.this,MusicPlayerService.class);
        bindService(musicServiceIntent,musicConnection,BIND_AUTO_CREATE);
    }

    @Override
    protected void initView() {
        initToolbar();
        drawerLayout=findViewById(R.id.drawerLayout);
        viewPager=findViewById(R.id.home_vp);
        viewPager.setAdapter(new HomeAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(1);
        musicTitle.setTextSize(18);
        initPlayBar();
        initDrawerLeft();
    }

    @Override
    protected void initListener() {
        setOpenDrawerListener(new OpenDrawer() {
            @Override
            public void openDrawer() {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        mineTitle.setOnClickListener(this);
        musicTitle.setOnClickListener(this);
        discoveryTitle.setOnClickListener(this);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        mineTitle.setTextSize(18);
                        musicTitle.setTextSize(14);
                        discoveryTitle.setTextSize(14);
                        break;
                    case 1:
                        mineTitle.setTextSize(14);
                        musicTitle.setTextSize(18);
                        discoveryTitle.setTextSize(14);
                        break;
                    case 2:
                        mineTitle.setTextSize(14);
                        musicTitle.setTextSize(14);
                        discoveryTitle.setTextSize(18);
                        break;
                }
            }
        });
        findViewById(R.id.drawer_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void initToolbar(){
        Toolbar toolbar=findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        mineTitle=findViewById(R.id.tv_mine);
        musicTitle=findViewById(R.id.tv_music);
        discoveryTitle=findViewById(R.id.tv_dis);
        mineTitle.setText("我的");
        musicTitle.setText("主页");
        discoveryTitle.setText("发现");
    }
    private void initPlayBar(){
        PlayBarFragment playBarFragment=new PlayBarFragment();
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.fragment_play_bar,playBarFragment).commit();
    }
    private void initDrawerLeft(){
        FrameLayout parentLayout=findViewById(R.id.drawer_left);
        View view=LayoutInflater.from(mContext).inflate(R.layout.layout_drawer_left,parentLayout,false);
        parentLayout.addView(view);
        LinearLayout loginOut=view.findViewById(R.id.home_login_out);
        loginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMusicService();
                HomeActivity.this.finish();
            }
        });
    }
    private void stopMusicService(){
        Intent intent=new Intent(PlayerManagerReceiver.PLAYER_COMMAND);
        intent.putExtra(Constant.PLAYER_COMMAND,Constant.COMMAND_STOP);//stop
        mContext.sendBroadcast(intent);
        unbindService(musicConnection);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopMusicService();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_mine:
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv_music:
                viewPager.setCurrentItem(1);
                break;
            case R.id.tv_dis:
                viewPager.setCurrentItem(2);
                break;
        }
    }

    class HomeAdapter extends FragmentPagerAdapter {
        HomeAdapter(FragmentManager fm){
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        long current=System.currentTimeMillis();
        if ((current-lastPressed)>2000){
            showToast("再点击一次退出程序");
            lastPressed=current;
        }else {
            stopMusicService();
            HomeActivity.this.finish();
        }
    }
}
