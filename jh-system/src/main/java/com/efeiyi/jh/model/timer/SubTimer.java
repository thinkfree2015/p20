package com.efeiyi.jh.model.timer;

import com.efeiyi.jh.model.task.BaseTimerTask;
import com.efeiyi.jh.model.task.SubTaskStopper;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2015/11/25.
 */
public class SubTimer {

    private Timer timer;
    private TimerTask timerTask;
    private Timer stopTimer;
    private SubTaskStopper stopTimerTask;

    public SubTimer(Timer timer, BaseTimerTask timerTask, Timer stopTimer, SubTaskStopper stopTimerTask) {
        this.timer = timer;
        this.timerTask = timerTask;
        this.stopTimer = stopTimer;
        this.stopTimerTask = stopTimerTask;
    }

    public SubTimer() {
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

    public void setStopTimerTask(SubTaskStopper stopTimerTask) {
        this.stopTimerTask = stopTimerTask;
    }

    public boolean cancel() {
        if (timerTask == null || timer == null || stopTimer == null || stopTimerTask == null) {
            System.err.println("failed to end task...................");
            return false;
        }
        stopTimerTask.cancel();
        System.out.println("subTask ended...................");
        return true;
    }
}
