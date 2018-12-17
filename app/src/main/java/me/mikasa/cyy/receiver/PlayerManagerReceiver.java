package me.mikasa.cyy.receiver;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.widget.RemoteViews;

import java.io.IOException;

import me.mikasa.cyy.R;
import me.mikasa.cyy.fragment.PlayBarFragment;
import me.mikasa.cyy.util.Constant;
import me.mikasa.cyy.util.NotificationUtil;

/**
 * Created by mikasa on 2018/11/25.
 * 管理音乐播放、暂停、停止、释放等
 */
public class PlayerManagerReceiver extends BroadcastReceiver {
    public static final String PLAYER_COMMAND="me.mikasa.cyy.receiver.playermanagerreceiver";
    private Context mContext;
    private MediaPlayer mediaPlayer;
    private NotificationUtil notificationUtil;
    public static int PLAYER_STATUS=Constant.STATUS_STOP;
    public PlayerManagerReceiver(){}
    public PlayerManagerReceiver(Context context){
        this.mContext=context;
        mediaPlayer=new MediaPlayer();
        notificationUtil=new NotificationUtil(context);
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        int command=intent.getIntExtra(Constant.PLAYER_COMMAND,3);
        switch (command){
            case Constant.COMMAND_PLAY:
                PLAYER_STATUS=Constant.STATUS_PLAY;
                int play_category=intent.getIntExtra(Constant.PLAY_CATEGORY,1);
                if (play_category==Constant.PLAY_INIT){//2
                    playMusic();
                }else {
                    if (mediaPlayer!=null){
                        mediaPlayer.start();//start()继续播放
                    }else {
                        PLAYER_STATUS=Constant.STATUS_STOP;
                    }
                }
                sendNotification(false);
                break;
            case Constant.COMMAND_PAUSE:
                PLAYER_STATUS=Constant.STATUS_PAUSE;
                if (mediaPlayer!=null){
                    mediaPlayer.pause();//pause()
                    sendNotification(true);
                }else {
                    PLAYER_STATUS=Constant.STATUS_STOP;
                }
                break;
            case Constant.COMMAND_STOP:
                PLAYER_STATUS=Constant.STATUS_STOP;
                if (mediaPlayer!=null){
                    mediaPlayer.stop();//stop()
                    mediaPlayer.release();
                    mediaPlayer=null;//回收资源
                }
                sendNotification(true);
                break;
            case Constant.COMMAND_CANCEL_NOTIFICATION:
                PLAYER_STATUS=Constant.STATUS_STOP;
                //notificationUtil.cancelNotification();
                break;
        }
        updatePlayBarUI();
    }
    private void updatePlayBarUI(){
        Intent updatePlayBar=new Intent(PlayBarFragment.PLAYBAR_UI_UPDATE);
        updatePlayBar.putExtra(Constant.PLAYER_STATUS,PLAYER_STATUS);
        mContext.sendBroadcast(updatePlayBar);
    }
    private void playMusic(){
        if (mediaPlayer!=null){
            mediaPlayer.release();
        }
        mediaPlayer=new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playMusic();
            }
        });
        try {
            AssetManager assetManager=mContext.getAssets();
            AssetFileDescriptor fileDescriptor=assetManager.openFd("happy.mp3");
            mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),
                    fileDescriptor.getStartOffset(),fileDescriptor.getLength());
            mediaPlayer.prepare();//prepare()
            mediaPlayer.start();//start()
            fileDescriptor.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void sendNotification(boolean isPaused){
        RemoteViews remoteViews=getRemoteViews(isPaused);
        notificationUtil.getBuilder().setContent(remoteViews);
        notificationUtil.sendNotification();
    }
    private RemoteViews getRemoteViews(boolean isPaused){
        PendingIntent playPauseIntent;
        RemoteViews remoteViews;
        if (Build.VERSION.SDK_INT>=26){
            remoteViews=new RemoteViews(mContext.getPackageName(),R.layout.layout_music_notification);
        }else {
            remoteViews=new RemoteViews(mContext.getPackageName(),R.layout.layout_music_notification_compat);
        }
        remoteViews.setTextViewText(R.id.music_name,"陈雨扬生日快乐");
        remoteViews.setTextViewText(R.id.music_artist,"SNH48女团");
        Intent intent=new Intent(PlayerManagerReceiver.PLAYER_COMMAND);
        if (isPaused){
            if (PlayerManagerReceiver.PLAYER_STATUS==Constant.STATUS_STOP){
                remoteViews.setImageViewResource(R.id.iv_play_pause,R.drawable.blank_white);
            }else {
                remoteViews.setImageViewResource(R.id.iv_play_pause,R.drawable.play);
                intent.putExtra(Constant.PLAYER_COMMAND,Constant.COMMAND_PLAY);
            }
        }else {
            remoteViews.setImageViewResource(R.id.iv_play_pause,R.drawable.pause);
            intent.putExtra(Constant.PLAYER_COMMAND,Constant.COMMAND_PAUSE);
        }
        playPauseIntent=PendingIntent.getBroadcast(mContext,0,
                intent,PendingIntent.FLAG_CANCEL_CURRENT);//FLAG_CANCEL_CURRENT??
        remoteViews.setOnClickPendingIntent(R.id.iv_play_pause,playPauseIntent);
        return remoteViews;
    }
}
