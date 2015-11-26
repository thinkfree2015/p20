package com.efeiyi.jh.model.timer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2015/11/25.
 */
public class SubTimer {

    private Timer timer;
    private TimerTask timerTask;
    private Timer stopTimer;
    private TimerTask stopTimerTask;

    public SubTimer(Timer timer,Timer stopTimer){
        this.timer = timer;
        this.stopTimer = stopTimer;
    }

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

    public Timer getStopTimer() {
        return stopTimer;
    }

    public void setStopTimer(Timer stopTimer) {
        this.stopTimer = stopTimer;
    }

    public TimerTask getStopTimerTask() {
        return stopTimerTask;
    }

    public void setStopTimerTask(TimerTask stopTimerTask) {
        this.stopTimerTask = stopTimerTask;
    }
}
