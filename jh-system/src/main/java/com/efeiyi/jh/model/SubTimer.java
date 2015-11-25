package com.efeiyi.jh.model;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2015/11/25.
 */
public class SubTimer {

    private Timer timer = new Timer();
    private TimerTask timerTask;

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public TimerTask getTimerTask() {
        return timerTask;
    }

    public void setTimerTask(TimerTask timerTask) {
        this.timerTask = timerTask;
    }
}
