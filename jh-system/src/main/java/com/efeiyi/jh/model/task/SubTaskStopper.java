package com.efeiyi.jh.model.task;

import com.efeiyi.jh.model.entity.VirtualPlan;
import com.efeiyi.jh.model.timer.SubTimer;
import com.efeiyi.jh.model.timer.SuperTimer;

import java.util.List;

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
            execute(null);
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
    public void execute(List<VirtualPlan> virtualPlanList) {
        this.cancel();
    }

    @Override
    public boolean cancel() {

        SubTimer subTimer = SuperTimer.getInstance().getSubTimerMap().remove(virtualPlan);
        if(subTimer != null) {
            synchronized (subTimer) {
//                subTimer.getTimerTask().cancel();
                subTimer.getTimer().cancel();
                logger.info("SubTaskTimer cancelled.........................");
                subTimer.getStopTimer().cancel();
                logger.info("SubTaskStopper cancelled.........................");
//                subTimer.getStopTimerTask().cancel();
            }
        }
        return super.cancel();
    }
}
