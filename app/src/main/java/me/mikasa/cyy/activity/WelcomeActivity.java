package me.mikasa.cyy.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import me.mikasa.cyy.R;
import woo.mikasa.lib.base.BaseActivity;

public class WelcomeActivity extends BaseActivity {
    private static final int delay=3000;

    @Override
    protected void createContentView() {
        //全屏,NoTitleTheme
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(setLayoutResId());
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        loadImage();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String[] permissions={Manifest.permission.READ_EXTERNAL_STORAGE};
                requestRuntimePermission(permissions,mListener);
            }
        },delay+500);
    }
    private void loadImage(){
        ImageView imageView=findViewById(R.id.iv_welcome);
        Glide.with(mContext).load(R.drawable.iv_welcome)
                .crossFade(delay)
                .into(imageView);
    }
    private void jumpToHome(){
        Intent intent=new Intent(mContext,HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void initListener() {
    }
    private PermissionListener mListener=new PermissionListener() {
        @Override
        public void onGranted() {
            jumpToHome();
        }

        @Override
        public void onDenied() {
            jumpToHome();
        }
    };
}
