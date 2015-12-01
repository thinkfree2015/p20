package com.efeiyi.jh.model.task;

import com.efeiyi.jh.model.entity.VirtualPlan;
import com.ming800.core.util.ApplicationContextUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.TimerTask;

/**
 * Created by Administrator on 2015/11/25.
 */
public abstract class BaseTimerTask extends TimerTask {

    protected SessionFactory sessionFactory = ((SessionFactory) ApplicationContextUtil.getApplicationContext().getBean("scheduleSessionFactory"));
    protected Session session;

    public abstract void setVirtualPlan(VirtualPlan virtualPlan);
    public abstract void execute();

}
