package com.efeiyi.jh.model.timer;


import com.efeiyi.jh.model.entity.VirtualPlan;
import com.ming800.core.util.ApplicationContextUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2015/11/25.
 */
public class SuperTimer {

    private static SuperTimer superTimer;
    private Timer timer = new Timer();
    private Map<VirtualPlan, SubTimer> subTimerTaskMap = new HashMap<VirtualPlan, SubTimer>();
    private Map<VirtualPlan, Object> subTaskTempStoreMap = new ConcurrentHashMap<VirtualPlan, Object>();
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

    public Map<VirtualPlan, SubTimer> getSubTimerTaskMap() {
        return subTimerTaskMap;
    }

    public Timer getTimer(){return timer;}

    public Map<VirtualPlan, Object> getSubTaskTempStoreMap() {
        return subTaskTempStoreMap;
    }
}
