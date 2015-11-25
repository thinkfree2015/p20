package com.efeiyi.jh.model.timer;


import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

/**
 * Created by Administrator on 2015/11/25.
 */
public class SuperTimer {

    private static SuperTimer superTimer;
    private Timer timer = new Timer();
    private Map<String, SubTimer> subTimerMap = new HashMap<String, SubTimer>();
    private long taskExecuteCycle = 86400000;

    public long getTaskExecuteCycle() {
        return taskExecuteCycle;
    }

    private SuperTimer(){}

    public static SuperTimer getInstance(){
        if(superTimer == null){
            synchronized (SuperTimer.class){
                if(superTimer == null){
                    superTimer = new SuperTimer();
                }
            }
        }
        return superTimer;
    }

    public Map<String, SubTimer> getSubTimerMap() {
        return subTimerMap;
    }

    public Timer getTimer(){return timer;}

}
