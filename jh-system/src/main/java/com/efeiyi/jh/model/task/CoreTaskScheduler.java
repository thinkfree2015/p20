package com.efeiyi.jh.model.task;

import com.efeiyi.jh.model.service.SessionHolder;
import com.efeiyi.jh.model.entity.VirtualPlan;
import com.efeiyi.jh.model.timer.SubTimer;
import com.efeiyi.jh.model.timer.SuperTimer;
import com.ming800.core.util.ApplicationContextUtil;
import org.hibernate.Query;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2015/11/23.
 */
public class CoreTaskScheduler extends TimerTask {
    private static CoreTaskScheduler coreTaskScheduler;
    private SessionHolder sessionHolder  = (SessionHolder) ApplicationContextUtil.getApplicationContext().getBean("mySession");

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

        DateFormat dateFormat = new SimpleDateFormat("yyyy,MM,dd");
        Date nowDate = new Date();
        String[] date = dateFormat.format(nowDate).split(",");

        Query listQuery = sessionHolder.getSession().createQuery("from VirtualPlan where status = '1'");
        List<VirtualPlan> virtualPlanList = listQuery.list();

        DateFormat timeFormat = new SimpleDateFormat("HH,mm,ss");
        Calendar startCalendarComparator = Calendar.getInstance();
        Calendar endCalendarComparator = Calendar.getInstance();
        for (VirtualPlan virtualPlan : virtualPlanList) {

            //停掉前一天的
            SubTimer subTimer = SuperTimer.getInstance().getSubTimerMap().remove(virtualPlan);
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

            BaseTimerTask subTimerTask = null;
            try {
                subTimerTask = (BaseTimerTask) Class.forName(virtualPlan.getImplementClass()).newInstance();
                subTimerTask.setVirtualPlan(virtualPlan);
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("没找到对应的定时任务处理类!!serial :" + virtualPlan.getSerial() + " description:" + virtualPlan.getDescription());
                continue;
            }

            subTimer = new SubTimer(new Timer(),new Timer());
            subTimer.setTimerTask(subTimerTask);
            subTimer.setStopTimerTask(new SubTaskStopper(virtualPlan));
            SuperTimer.getInstance().getSubTimerMap().put(virtualPlan, subTimer);

            long delay = startCalendarComparator.getTimeInMillis() - nowDate.getTime();
            long stopperDelay = endCalendarComparator.getTimeInMillis() - nowDate.getTime();
            subTimer.getTimer().schedule(subTimerTask, delay < 0 ? 0 : delay);
            subTimer.getStopTimer().schedule(subTimer.getStopTimerTask(), stopperDelay < 0 ? 0 : stopperDelay);
            System.out.println(virtualPlan.getSerial() + "启动定时：" + (delay < 0 ? 0 : delay) + "毫秒后启动");
            System.out.println(virtualPlan.getSerial() + "关闭定时：" + (stopperDelay < 0 ? 0 : stopperDelay) + "毫秒后启动");
        }

    }
}
