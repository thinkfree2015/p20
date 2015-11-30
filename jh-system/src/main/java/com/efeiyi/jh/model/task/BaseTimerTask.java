package com.efeiyi.jh.model.task;

import com.efeiyi.jh.service.SessionHolder;
import com.efeiyi.jh.model.entity.VirtualPlan;
import com.ming800.core.util.ApplicationContextUtil;

import java.util.TimerTask;

/**
 * Created by Administrator on 2015/11/25.
 */
public abstract  class BaseTimerTask extends TimerTask {

    protected SessionHolder sessionHolder = ((SessionHolder) ApplicationContextUtil.getApplicationContext().getBean("sessionHolder"));

    public abstract void setVirtualPlan(VirtualPlan virtualPlan);

}
