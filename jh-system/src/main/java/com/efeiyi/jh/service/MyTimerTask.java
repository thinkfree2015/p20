package com.efeiyi.jh.service;

import com.efeiyi.jh.model.VirtualPlan;
import com.ming800.core.base.service.BaseManager;
import com.ming800.core.util.ApplicationContextUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Administrator on 2015/11/23.
 */
//@Service
public class MyTimerTask extends TimerTask {
    private BaseManager baseManager = (BaseManager) ApplicationContextUtil.getApplicationContext().getBean("baseManagerImpl");
    private static Map<String, Timer> subTimerMap = new HashMap<String, Timer>();
    private SessionFactory sessionFactory = (SessionFactory) ApplicationContextUtil.getApplicationContext().getBean("sessionFactory");
    @Override
    @Transactional
    public void run() {

        Session session = sessionFactory.openSession();
//        Session session = sessionFactory.getCurrentSession();
        Query listQuery = session.createQuery("from VirtualPlan where status <> '0'");
//        List<VirtualPlan> virtualPlanList = baseManager.listObject(new XQuery());
        List<VirtualPlan> virtualPlanList = listQuery.list();
        Calendar now = Calendar.getInstance();

        for (VirtualPlan virtualPlan : virtualPlanList) {

            Timer subTimer = subTimerMap.remove(virtualPlan.getId());
            if (subTimer != null) {
                subTimer.cancel();
            }

            TimerTask subTimerTasker = null;

            try {
                subTimerTasker = (TimerTask) Class.forName(virtualPlan.getImplementHandler()).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("没找到对应的定时任务处理类!!serial :" + virtualPlan.getSerial() + " description:" + virtualPlan.getDescription());
                continue;
            }
            
            Calendar startTime = Calendar.getInstance();
            subTimer.schedule(subTimerTasker, startTime.getTimeInMillis() - now.getTimeInMillis());
            virtualPlan.setRunning(true);
            baseManager.saveOrUpdate(VirtualPlan.class.getName(), virtualPlan);
        }


    }
}
