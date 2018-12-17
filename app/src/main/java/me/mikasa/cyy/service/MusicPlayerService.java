package me.mikasa.cyy.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import me.mikasa.cyy.receiver.PlayerManagerReceiver;

/**
 * Created by mikasa on 2018/11/25.
 */
public class MusicPlayerService extends Service {
    private PlayerManagerReceiver receiver;
    private MusicBinder mBinder=new MusicBinder();
    public MusicPlayerService(){}
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        register();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegister();
    }
    public class MusicBinder extends Binder{
        public MusicBinder() {
            super();
        }
    }

    private void register(){
        receiver=new PlayerManagerReceiver(MusicPlayerService.this);
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(PlayerManagerReceiver.PLAYER_COMMAND);
        registerReceiver(receiver,intentFilter);
    }
    private void unRegister(){
        if (receiver!=null){
            unregisterReceiver(receiver);
        }
    }
}
