package com.efeiyi.jh.service;


import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

/**
 * Created by Administrator on 2015/11/25.
 */
public class MyTimer {

    private static MyTimer myTimer;
    private Timer timer = new Timer();
    private Map<String, Timer> subTimerMap = new HashMap<String, Timer>();
    private long taskExecuteCycle = 86400000;

    public long getTaskExecuteCycle() {
        return taskExecuteCycle;
    }

    private MyTimer(){}

    public static MyTimer getInstance(){
        if(myTimer == null){
            synchronized (MyTimer.class){
                if(myTimer == null){
                    myTimer = new MyTimer();
                }
            }
        }
        return myTimer;
    }

    public Map<String, Timer> getSubTimerMap() {
        return subTimerMap;
    }

    public Timer getTimer(){return timer;}

}
