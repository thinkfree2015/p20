package com.efeiyi.jh.model.task;

import com.efeiyi.jh.model.entity.VirtualPlan;
import com.ming800.core.util.ApplicationContextUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.TimerTask;

/**
 * Created by Administrator on 2015/11/25.
 */
public abstract class AbstractTimerTask extends TimerTask {

    private SessionFactory sessionFactory = (SessionFactory) ApplicationContextUtil.getApplicationContext().getBean("sessionFactory");
    private Session session = sessionFactory.openSession();

    public abstract void setVirtualPlan(VirtualPlan virtualPlan);

    public Session getSession() {
        return session;
    }

    @Override
    public boolean cancel() {
        session.close();
        return super.cancel();
    }
}
