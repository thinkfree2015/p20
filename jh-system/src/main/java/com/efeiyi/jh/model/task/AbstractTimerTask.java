package com.efeiyi.jh.model.task;

import com.efeiyi.jh.model.entity.VirtualPlan;

import java.util.TimerTask;

/**
 * Created by Administrator on 2015/11/25.
 */
public abstract class AbstractTimerTask extends TimerTask {

    public abstract void setVirtualPlan(VirtualPlan virtualPlan);
}
