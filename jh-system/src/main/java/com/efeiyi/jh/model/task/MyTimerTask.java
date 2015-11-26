package com.efeiyi.jh.model.task;

import com.efeiyi.jh.model.service.SessionHolder;
import com.efeiyi.jh.model.entity.VirtualPlan;
import com.ming800.core.util.ApplicationContextUtil;

import java.util.TimerTask;

/**
 * Created by Administrator on 2015/11/25.
 */
public class MyTimerTask extends TimerTask {

    protected SessionHolder sessionHolder = ((SessionHolder) ApplicationContextUtil.getApplicationContext().getBean("sessionHolder"));

    public void setVirtualPlan(VirtualPlan virtualPlan){}


    @Override
    public void run() {}

    @Override
    public boolean cancel() {
        return super.cancel();
    }
}
