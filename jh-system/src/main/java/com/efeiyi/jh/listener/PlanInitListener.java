package com.efeiyi.jh.listener;

import com.efeiyi.jh.model.task.CoreTaskScheduler;
import com.efeiyi.jh.model.timer.SuperTimer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2015/11/24.
 */
public class PlanInitListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            //刚启动执行一次
            CoreTaskScheduler coreTaskScheduler = CoreTaskScheduler.getInstance();
            coreTaskScheduler.run();

            Calendar todayStartTime = Calendar.getInstance();
            DateFormat dateFormat = new SimpleDateFormat("yyyy,MM,dd,HH,mm,ss");
            String[] dateString = dateFormat.format(new Date()).split(",");
            //Calendar的月从0-11
            todayStartTime.set(Integer.parseInt(dateString[0]), Integer.parseInt(dateString[1]) - 1, Integer.parseInt(dateString[2]), 00, 00, 00);
            Date tomorrowStartTime = new Date(todayStartTime.getTimeInMillis() + SuperTimer.getInstance().getTaskExecuteCycle());

            //定时到明日0时每24小时一次
            SuperTimer.getInstance().getTimer().scheduleAtFixedRate(coreTaskScheduler, tomorrowStartTime, SuperTimer.getInstance().getTaskExecuteCycle());

        } catch (Exception e) {
            System.err.println("任务启动监听出现异常！！！！！！！！！！！！！！！！！");
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {}
}
