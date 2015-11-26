package com.efeiyi.jh.model.task;

import com.efeiyi.jh.model.entity.VirtualPlan;
import com.efeiyi.jh.model.timer.SubTimer;
import com.efeiyi.jh.model.timer.SuperTimer;

/**
 * Created by Administrator on 2015/11/25.
 */
public class StopTimerTask extends MyTimerTask {

    private VirtualPlan virtualPlan;

    public StopTimerTask(VirtualPlan virtualPlan) {
        this.virtualPlan = virtualPlan;
    }

    @Override
    public void run() {
        SubTimer subTimer = SuperTimer.getInstance().getSubTimerMap().remove(virtualPlan);
        subTimer.getTimerTask().cancel();
        subTimer.getTimer().cancel();
        subTimer.getStopTimer().cancel();
        subTimer.getStopTimerTask().cancel();
    }

    @Override
    public void setVirtualPlan(VirtualPlan virtualPlan) {
        this.virtualPlan = virtualPlan;
    }

    @Override
    public boolean cancel() {
        System.out.println("stopTimerTask cancelling.........................");
        return super.cancel();
    }
}
