package com.efeiyi.jh.model;

import com.ming800.core.util.ApplicationContextUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2015/11/23.
 */
public class MyTimerTask extends TimerTask {
    private static MyTimerTask myTimerTask;
    private SessionFactory sessionFactory = (SessionFactory) ApplicationContextUtil.getApplicationContext().getBean("sessionFactory");

    private MyTimerTask() {
    }

    public static MyTimerTask getInstance() {
        if (myTimerTask == null) {
            synchronized (MyTimerTask.class) {
                if (myTimerTask == null) {
                    myTimerTask = new MyTimerTask();
                }
            }
        }
        return myTimerTask;
    }

    @Override
    public void run() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy,MM,dd");
        Date nowDate = new Date();
        String[] date = dateFormat.format(nowDate).split(",");

        Session session = sessionFactory.openSession();
        Query listQuery = session.createQuery("from VirtualPlan where status = '1'");
        List<VirtualPlan> virtualPlanList = listQuery.list();

        DateFormat timeFormat = new SimpleDateFormat("HH,mm,ss");
        Calendar startCalendarComparator = Calendar.getInstance();
        Calendar endCalendarComparator = Calendar.getInstance();
        for (VirtualPlan virtualPlan : virtualPlanList) {

            //停掉前一天的
            SubTimer subTimer = SuperTimer.getInstance().getSubTimerMap().remove(virtualPlan.getId());
            if (subTimer != null) {
                subTimer.getTimer().cancel();
            }

            //执行日期以外的跳过
            if (virtualPlan.getStartDate().compareTo(nowDate) > 0 || virtualPlan.getEndDate().compareTo(nowDate) < 0) {
                continue;
            }

            //执行时间以外的跳过,当前时间超过starttime的立即启动
            String[] subTaskStartTime = timeFormat.format(virtualPlan.getStartTime()).split(",");
            String[] subTaskEndTime = timeFormat.format(virtualPlan.getEndTime()).split(",");
            startCalendarComparator.set(Integer.parseInt(date[0]), Integer.parseInt(date[1]) - 1, Integer.parseInt(date[2]), Integer.parseInt(subTaskStartTime[0]), Integer.parseInt(subTaskStartTime[1]), Integer.parseInt(subTaskStartTime[2]));
            endCalendarComparator.set(Integer.parseInt(date[0]), Integer.parseInt(date[1]) - 1, Integer.parseInt(date[2]), Integer.parseInt(subTaskEndTime[0]), Integer.parseInt(subTaskEndTime[1]), Integer.parseInt(subTaskEndTime[2]));
            if (endCalendarComparator.getTime().compareTo(nowDate) < 0) {
                continue;
            }

            TimerTask subTimerTask = null;
            try {
                subTimerTask = (TimerTask) Class.forName(virtualPlan.getImplementClass()).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("没找到对应的定时任务处理类!!serial :" + virtualPlan.getSerial() + " description:" + virtualPlan.getDescription());
                continue;
            }

            subTimer = new SubTimer();
            subTimer.setTimer(new Timer());
            subTimer.setTimerTask(subTimerTask);
            SuperTimer.getInstance().getSubTimerMap().put(virtualPlan.getId(), subTimer);
            long delay = 0;
            subTimer.getTimer().schedule(subTimerTask, (delay = startCalendarComparator.getTimeInMillis() - nowDate.getTime()) < 0 ? 0 : delay, SuperTimer.getInstance().getTaskExecuteCycle());
            System.out.println(virtualPlan.getSerial() + "启动定时：" + (delay < 0 ? 0 : delay) + "毫秒后启动");
        }
        session.close();

    }
}
