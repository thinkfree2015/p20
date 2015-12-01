package com.efeiyi.jh.model.task;

import com.efeiyi.jh.model.PlanConst;
import com.efeiyi.jh.model.entity.VirtualPlan;
import com.efeiyi.jh.model.timer.SubTimer;
import com.efeiyi.jh.model.timer.SuperTimer;
import org.hibernate.Query;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2015/11/23.
 */
public class CoreTaskScheduler extends BaseTimerTask {
    private static CoreTaskScheduler coreTaskScheduler;
    private List<VirtualPlan> virtualPlanList;

    private CoreTaskScheduler() {
    }

    public static CoreTaskScheduler getInstance() {
        if (coreTaskScheduler == null) {
            synchronized (CoreTaskScheduler.class) {
                if (coreTaskScheduler == null) {
                    coreTaskScheduler = new CoreTaskScheduler();
                }
            }
        }
        return coreTaskScheduler;
    }

    @Override
    public void run() {
        if (session == null || !session.isOpen()) {
            session = sessionFactory.openSession();
        }
        try {
            Query listQuery = session.createQuery("from VirtualPlan where status = " + PlanConst.planStatusNormal);
            virtualPlanList = listQuery.list();
            execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    public void execute() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy,MM,dd");
        Date nowDate = new Date();
        String[] date = dateFormat.format(nowDate).split(",");

        DateFormat timeFormat = new SimpleDateFormat("HH,mm,ss");
        Calendar startCalendarComparator = Calendar.getInstance();
        Calendar endCalendarComparator = Calendar.getInstance();
        for (VirtualPlan virtualPlan : virtualPlanList) {

            //停掉前一天的
            SubTimer subTimer = SuperTimer.getInstance().getSubTimerTaskMap().remove(virtualPlan);
            if (subTimer != null) {
                subTimer.cancel();
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

            BaseTimerTask subTimerTask = null;
            try {
                subTimerTask = (BaseTimerTask) Class.forName(virtualPlan.getImplementClass()).newInstance();
                subTimerTask.setVirtualPlan(virtualPlan);
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("ClassNotFound!!serial :" + virtualPlan.getSerial() + " description:" + virtualPlan.getDescription());
                continue;
            }
            subTimer = new SubTimer(new Timer(), subTimerTask, new Timer(), new SubTaskStopper(virtualPlan));
            SuperTimer.getInstance().getSubTimerTaskMap().put(virtualPlan, subTimer);

            long delay = startCalendarComparator.getTimeInMillis() - nowDate.getTime();
            long stopperDelay = endCalendarComparator.getTimeInMillis() - nowDate.getTime();
            subTimer.getTimer().schedule(subTimerTask, delay < 0 ? 0 : delay);
            subTimer.getStopTimer().schedule(subTimer.getStopTimerTask(), stopperDelay < 0 ? 0 : stopperDelay);
            System.out.println(virtualPlan.getSerial() + " timer launch after " + (delay < 0 ? 0 : delay) + " millis seconds");
            System.out.println(virtualPlan.getSerial() + " timer off after " + (stopperDelay < 0 ? 0 : stopperDelay) + " millis seconds");
        }
    }

    @Override
    public void setVirtualPlan(VirtualPlan virtualPlan) {
    }
}
