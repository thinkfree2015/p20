package com.efeiyi.jh.model.task;

import com.efeiyi.ec.organization.model.User;
import com.efeiyi.jh.model.entity.VirtualPlan;
import com.efeiyi.jh.model.entity.VirtualUser;
import com.efeiyi.jh.model.entity.VirtualUserPlan;

import java.util.Random;

/**
 * Created by Administrator on 2015/11/26.
 */
public class VirtualUserGenerator extends BaseTimerTask {
    private VirtualUserPlan virtualUserPlan;
    private String [] prefixes = {"134","135","136","137","138","139","150","151","152","158","159","157","182","187","188","147","130","131","132","155","156","185","186","133","153","180","189"};
    @Override
    public void run() {

        Random random = new Random();
        for(int x = 0; x < virtualUserPlan.getCount(); x++){
            User user = new User();
            String prefix = prefixes[random.nextInt(prefixes.length)];
            String suffix = leftPad(Integer.toString(random.nextInt(10000)),4,"0");
            user.setUsername(prefix + "****" + suffix);
            user.setStatus("2");
            VirtualUser virtualUser = new VirtualUser();
            virtualUser.setUser(user);
            virtualUser.setVirtualUserPlan(virtualUserPlan);
            virtualUserPlan.getVirtualUserList().add(virtualUser);
            sessionHolder.getSession().saveOrUpdate(user.getClass().getName(),user);
            sessionHolder.getSession().saveOrUpdate(virtualUser.getClass().getName(), virtualUser);
        }
        //执行一次不再重复执行
        virtualUserPlan = (VirtualUserPlan)sessionHolder.getSession().get(VirtualUserPlan.class,virtualUserPlan.getId());
        virtualUserPlan.setStatus("2");
        sessionHolder.getSession().saveOrUpdate(virtualUserPlan.getClass().getName(),virtualUserPlan);
        sessionHolder.getSession().flush();
    }

    private String leftPad(String stringBefore,int length,String appender) {
        int len = stringBefore.getBytes().length;
        StringBuilder stringAfter = new StringBuilder();
        while(len++ < length){
            stringAfter.insert(0,appender);
        }
        return stringBefore;
    }

    @Override
    public void setVirtualPlan(VirtualPlan virtualPlan) {
        this.virtualUserPlan = (VirtualUserPlan)virtualPlan;
    }
}
