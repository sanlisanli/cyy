package me.mikasa.cyy.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.CustomFooterViewCallBack;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.mikasa.cyy.R;
import me.mikasa.cyy.adapter.ItemAdapter;
import me.mikasa.cyy.util.Constant;
import me.mikasa.cyy.util.topSnackbar.BaseTransientBottomBar;
import me.mikasa.cyy.util.topSnackbar.TopSnackBar;
import woo.mikasa.lib.base.BaseFragment;
import woo.mikasa.lib.base.BaseRvAdapter;

/**
 * Created by mikasa on 2018/11/24.
 */
public class MusicFragment extends BaseFragment implements BaseRvAdapter.OnRvItemClickListener {
    private static final int fade=3000;
    private ItemAdapter mAdapter;
    private List<Integer>itemList=new ArrayList<>();
    private static final List<Integer>appendList=Constant.getDuoLaImgs();
    private XRecyclerView recyclerView;
    private Banner banner;
    //private View footerView=null;
    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_music;
    }

    @Override
    protected void initData(Bundle bundle) {
        mAdapter=new ItemAdapter(mBaseActivity);
    }

    @Override
    protected void initView() {
        recyclerView=mRootView.findViewById(R.id.xrv_music);
        recyclerView.setLayoutManager(new LinearLayoutManager(mBaseActivity));//layoutManager
        recyclerView.setAdapter(mAdapter);//adapter
        //recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerView.setLimitNumberToCallLoadMore(0);
        View bannerView=LayoutInflater.from(mBaseActivity).inflate(R.layout.layout_fra_music_header,recyclerView,false);
        View footerView=LayoutInflater.from(mBaseActivity).inflate(R.layout.layout_rv_footer,recyclerView,false);
        recyclerView.addHeaderView(bannerView);
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
        banner=bannerView.findViewById(R.id.fra_music_banner);
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
        itemList.addAll(appendList);
        mAdapter.appendData(appendList);
    }

    @Override
    protected void setListener() {
        mAdapter.setOnRvItemClickListener(this);
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {
                appendList();
            }
        });
        recyclerView.refresh();//需要在setLoadingListener后面
    }
    private void refresh(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                banner.setImages(Constant.getBannerImgs()).setImageLoader(new GlideImageLoader()).start();
                itemList=Constant.getDuoLaImgs();
                mAdapter.refreshData(itemList);
                recyclerView.refreshComplete();
            }
        },2000);
    }

    @Override
    public void onItemClick(int pos) {
        TopSnackBar topSnackBar=TopSnackBar.make(recyclerView,"陈雨扬生日快乐",BaseTransientBottomBar.LENGTH_LONG);
        topSnackBar.getView().setBackgroundColor(Color.parseColor("#69f0ae"));
        topSnackBar.show();
    }

    private class GlideImageLoader extends ImageLoader{
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(mBaseActivity).load(path).crossFade(fade).into(imageView);
        }
    }
}
