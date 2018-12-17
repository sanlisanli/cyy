package me.mikasa.cyy.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import me.mikasa.cyy.R;
import me.mikasa.cyy.receiver.PlayerManagerReceiver;
import me.mikasa.cyy.util.Constant;
import woo.mikasa.lib.base.BaseFragment;

import static me.mikasa.cyy.receiver.PlayerManagerReceiver.PLAYER_STATUS;

/**
 * Created by mikasa on 2018/11/24.
 */
public class PlayBarFragment extends BaseFragment {
    public static final String PLAYBAR_UI_UPDATE="me.mikasa.cyy.fragment.playbarfragment";
    private ImageView playIv;
    private PLayBarReceiver receiver;
    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_play_bar;
    }

    @Override
    protected void initData(Bundle bundle) {
        register();
    }

    @Override
    protected void initView() {
        playIv = mRootView.findViewById(R.id.play_iv);
    }

    @Override
    public void onResume() {
        super.onResume();
        initPlayIv();
    }

    private void initPlayIv(){
        switch (PlayerManagerReceiver.PLAYER_STATUS) {
            case Constant.STATUS_STOP:
                playIv.setSelected(false);
                break;
            case Constant.STATUS_PLAY:
                playIv.setSelected(true);
                break;
            case Constant.STATUS_PAUSE:
                playIv.setSelected(false);
                break;
            case Constant.STATUS_RUNNNING:
                playIv.setSelected(true);
                break;
        }
    }

    @Override
    protected void setListener() {
        playIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playOrPauseMusic();
            }
        });
    }
    private void playOrPauseMusic(){
        if (PLAYER_STATUS==Constant.STATUS_PAUSE){
            Intent intent=new Intent(PlayerManagerReceiver.PLAYER_COMMAND);
            intent.putExtra(Constant.PLAYER_COMMAND,Constant.COMMAND_PLAY);
            mBaseActivity.sendBroadcast(intent);
        }else if (PLAYER_STATUS==Constant.STATUS_PLAY){
            Intent intent=new Intent(PlayerManagerReceiver.PLAYER_COMMAND);
            intent.putExtra(Constant.PLAYER_COMMAND,Constant.COMMAND_PAUSE);
            mBaseActivity.sendBroadcast(intent);
        }else {
            Intent intent=new Intent(PlayerManagerReceiver.PLAYER_COMMAND);
            intent.putExtra(Constant.PLAYER_COMMAND,Constant.COMMAND_PLAY);
            intent.putExtra(Constant.PLAY_CATEGORY,Constant.PLAY_INIT);
            mBaseActivity.sendBroadcast(intent);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegister();
    }

    private void register(){
        receiver=new PLayBarReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(PLAYBAR_UI_UPDATE);
        mBaseActivity.registerReceiver(receiver,intentFilter);
    }
    private void unRegister(){
        if (receiver!=null){
            mBaseActivity.unregisterReceiver(receiver);
        }
    }
    class PLayBarReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            int player_status=intent.getIntExtra(Constant.PLAYER_STATUS,3);
            switch (player_status){
                case Constant.STATUS_STOP:
                    playIv.setSelected(false);
                    break;
                case Constant.STATUS_PLAY:
                    playIv.setSelected(true);
                    break;
                case Constant.STATUS_PAUSE:
                    playIv.setSelected(false);
                    break;
                case Constant.STATUS_RUNNNING:
                    playIv.setSelected(true);
                    break;
            }
        }
    }
}
