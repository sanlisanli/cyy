package me.mikasa.cyy.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.mikasa.cyy.R;

/**
 * Created by mikasa on 2018/11/24.
 */
public class Constant {
    public static final String isMusicServiceKilled="isKilled";
    //player command
    public static final String PLAYER_COMMAND="player_command";
    public static final int COMMAND_PLAY=1;
    public static final int COMMAND_PAUSE=2;
    public static final int COMMAND_STOP=3;
    public static final int COMMAND_CANCEL_NOTIFICATION=4;
    //status
    public static final String PLAYER_STATUS="player_status";
    public static final int STATUS_PLAY=1;
    public static final int STATUS_PAUSE=2;
    public static final int STATUS_STOP=3;
    public static final int STATUS_RUNNNING=4;
    //play的两种状态
    public static final String PLAY_CATEGORY="play_category";
    public static final int PLAY_CONTINUE=1;
    public static final int PLAY_INIT=2;
    public static List<Integer>getBannerImgs(){
        Integer[] imgIds={R.drawable.baloon01,R.drawable.cyy02,R.drawable.baloon03,R.drawable.cake03,R.drawable.cyy01,
                R.drawable.dis05,R.drawable.banner05,R.drawable.cake03,R.drawable.baloon01};
        List<Integer>ids=new ArrayList<>(imgIds.length);
        Collections.addAll(ids,imgIds);
        return ids;
    }
    public static List<Integer>getDuoLaImgs(){
        Integer[]imgIds={R.drawable.baloon01,R.drawable.cyy02,R.drawable.baloon01,R.drawable.cyy01};
        List<Integer>ids=new ArrayList<>(imgIds.length);
        Collections.addAll(ids,imgIds);
        return ids;
    }
    public static List<Integer>getFiveFlowersImgs(){
        Integer[] imgIds={R.drawable.dis01,R.drawable.dis02,R.drawable.dis03,R.drawable.dis04,R.drawable.dis05};
        List<Integer>ids=new ArrayList<>(imgIds.length);
        Collections.addAll(ids,imgIds);
        return ids;
    }
    public static List<Integer>getFlowersImgs(){
        Integer[] imgIds={R.drawable.dis01,R.drawable.dis02,R.drawable.dis03,R.drawable.dis04,R.drawable.dis05,
                R.drawable.dis06,R.drawable.dis07};
        List<Integer>ids=new ArrayList<>(imgIds.length);
        Collections.addAll(ids,imgIds);
        return ids;
    }
}
