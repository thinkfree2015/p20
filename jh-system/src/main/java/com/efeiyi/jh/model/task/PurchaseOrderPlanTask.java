package com.efeiyi.jh.model.task;


import com.efeiyi.jh.model.entity.VirtualOrderPlan;
import com.efeiyi.jh.model.entity.VirtualPlan;
import com.efeiyi.jh.model.entity.VirtualProductModel;
import com.efeiyi.jh.model.timer.SuperTimer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2015/11/20.
 */
public class PurchaseOrderPlanTask extends MyTimerTask {

    private VirtualOrderPlan virtualOrderPlan;

    @Override
    public boolean cancel() {
        System.out.println("PurchaseOrderPlanTask cancelling............................................");

        return super.cancel();
    }

    @Override
    public void run() {
        System.out.println("running..........................................");

        Random random = new Random();
        Integer totalOrderCount = 0;

        for (VirtualProductModel virtualProductModel : virtualOrderPlan.getVirtualProductModelList()) {
            totalOrderCount += virtualProductModel.getRandomCount();
        }
        Long[] randomOrderTimePoint = new Long[totalOrderCount];

        DateFormat dateFormat = new SimpleDateFormat("yyyy,MM,dd,HH,mm,ss");
        Calendar futureCalendar = Calendar.getInstance();
        String[] nowArray = dateFormat.format(futureCalendar.getTime()).split(",");
        String[] peakTimeArray = dateFormat.format(virtualOrderPlan.getPeakTime()).split(",");
        futureCalendar.set(Integer.parseInt(nowArray[0]), Integer.parseInt(nowArray[1]) - 1, Integer.parseInt(nowArray[2]), Integer.parseInt(peakTimeArray[3]), Integer.parseInt(peakTimeArray[4]), Integer.parseInt(peakTimeArray[5]));
        long now = System.currentTimeMillis();
        long future = futureCalendar.getTimeInMillis();
        long futureFromNow = future - now;

        for (int x = 0; x < randomOrderTimePoint.length; x++) {
            randomOrderTimePoint[x] = (long) (random.nextGaussian() * 60 * 60 * 1000) * virtualOrderPlan.getStandardDeviation() + futureFromNow;
        }
        Arrays.sort(randomOrderTimePoint);

        for (int x = 0; x < randomOrderTimePoint.length; x++) {
            SuperTimer.getInstance().getSubTimerMap().get(virtualOrderPlan).getTimer().schedule(new VirtualPurchaseActionTask(), randomOrderTimePoint[x] >= 0 ? randomOrderTimePoint[x] : 0);
        }

        System.out.println("一个循环结束.........................");
    }

    @Override
    public void setVirtualPlan(VirtualPlan virtualPlan) {
        this.virtualOrderPlan = (VirtualOrderPlan) virtualPlan;
    }
}


