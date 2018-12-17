package me.mikasa.cyy.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import me.mikasa.cyy.R;
import woo.mikasa.lib.base.BaseFragment;

/**
 * Created by mikasa on 2018/11/24.
 */
public class MineFragment extends BaseFragment{
    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {
        LinearLayout layout=mRootView.findViewById(R.id.ll_open_drawer);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBaseActivity.openDrawer();
            }
        });
    }
}
