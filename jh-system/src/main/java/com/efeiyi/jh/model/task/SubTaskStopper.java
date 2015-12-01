package com.efeiyi.jh.model.task;

import com.efeiyi.jh.model.entity.VirtualPlan;
import com.efeiyi.jh.model.timer.SubTimer;
import com.efeiyi.jh.model.timer.SuperTimer;

/**
 * Created by Administrator on 2015/11/25.
 */
public class SubTaskStopper extends BaseTimerTask {

    private VirtualPlan virtualPlan;

    public SubTaskStopper(VirtualPlan virtualPlan) {
        this.virtualPlan = virtualPlan;
    }

    @Override
    public void run() {
        try {
            execute();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            super.cancel();
        }
    }

    @Override
    public void setVirtualPlan(VirtualPlan virtualPlan) {
        this.virtualPlan = virtualPlan;
    }

    @Override
    public void execute() {
        this.cancel();
    }

    @Override
    public boolean cancel() {
        System.out.println("SubTaskStopper cancelling.........................");
        SubTimer subTimer = SuperTimer.getInstance().getSubTimerTaskMap().remove(virtualPlan);
        if(subTimer != null) {
            synchronized (subTimer) {
                subTimer.getTimerTask().cancel();
                subTimer.getTimer().cancel();
                subTimer.getStopTimer().cancel();
                subTimer.getStopTimerTask().cancel();
            }
        }
        return true;
    }
}
