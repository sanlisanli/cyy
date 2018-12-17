package me.mikasa.cyy.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import com.jcodecraeer.xrecyclerview.CustomFooterViewCallBack;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.mikasa.cyy.R;
import me.mikasa.cyy.adapter.FlowerAdapter;
import me.mikasa.cyy.util.Constant;
import me.mikasa.cyy.util.topSnackbar.BaseTransientBottomBar;
import me.mikasa.cyy.util.topSnackbar.TopSnackBar;
import woo.mikasa.lib.base.BaseFragment;
import woo.mikasa.lib.base.BaseRvAdapter;

/**
 * Created by mikasa on 2018/11/24.
 */
public class DisFragment extends BaseFragment implements BaseRvAdapter.OnRvItemClickListener {
    private FlowerAdapter mAdapter;
    private XRecyclerView recyclerView;
    private static final List<Integer>appendList=Constant.getFlowersImgs();
    private List<Integer>flowerList=new ArrayList<>();
    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_dis;
    }

    @Override
    protected void initData(Bundle bundle) {
        mAdapter=new FlowerAdapter(mBaseActivity);
    }

    @Override
    protected void initView() {
        recyclerView=mRootView.findViewById(R.id.rv_dis);
        recyclerView.setLayoutManager(new LinearLayoutManager(mBaseActivity));
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLimitNumberToCallLoadMore(0);
        View footerView=LayoutInflater.from(mBaseActivity).inflate(R.layout.layout_rv_footer,recyclerView,false);
        recyclerView.setFootView(footerView, new CustomFooterViewCallBack() {
            @Override
            public void onLoadingMore(View yourFooterView) {
            }

            @Override
            public void onLoadMoreComplete(View yourFooterView) {
            }

            @Override
            public void onSetNoMore(View yourFooterView, boolean noMore) {
            }
        });
        flowerList=Constant.getFiveFlowersImgs();
        Collections.shuffle(flowerList);
        mAdapter.refreshData(flowerList);
    }

    @Override
    protected void setListener() {
        mAdapter.setOnRvItemClickListener(this);
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                appendList();
            }
        });
    }
    private void appendList(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.loadMoreComplete();
                loadMore();
            }
        },2200);
    }
    private void loadMore(){
        Collections.shuffle(appendList);
        flowerList.addAll(appendList);
        mAdapter.appendData(appendList);
    }
    @Override
    public void onItemClick(int pos) {
        TopSnackBar topSnackBar=TopSnackBar.make(recyclerView,"陈雨扬生日快乐",BaseTransientBottomBar.LENGTH_LONG);
        topSnackBar.getView().setBackgroundColor(Color.parseColor("#69f0ae"));
        topSnackBar.show();
    }
}
