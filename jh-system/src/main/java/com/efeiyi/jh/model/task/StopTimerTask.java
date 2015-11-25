package com.efeiyi.jh.model.task;

import com.efeiyi.jh.model.timer.SubTimer;

import java.util.TimerTask;

/**
 * Created by Administrator on 2015/11/25.
 */
public class StopTimerTask extends TimerTask {

    private SubTimer subTimer;

    public StopTimerTask(SubTimer subTimer) {
        this.subTimer = subTimer;
    }

    @Override
    public void run() {
        subTimer.getTimerTask().cancel();
        subTimer.getTimer().cancel();
        subTimer.getStopTimer().cancel();
        subTimer.getStopTimerTask().cancel();

    }

    @Override
    public boolean cancel() {
        System.out.println("stopTimerTask cancelling.........................");
        return super.cancel();
    }
}
