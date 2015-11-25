package com.efeiyi.jh.task;

import com.efeiyi.jh.model.VirtualPlan;

import java.util.*;

/**
 * Created by Administrator on 2015/11/20.
 */
public class PurchaseOrderPlanTask extends TimerTask{

    public void execute() {

//        PurchasePlan purchasePlan = (PurchasePlan)plan;
//
//        int memberCount = 0;
//        for(MemberPlan memberPlan : purchasePlan.getMemberPlanList()){
//            memberCount += memberPlan.getMemberCount();
//        }
//
//        final SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
//        final Timer timer = new Timer();
//        final Timer shutdownTimer = new Timer();
//        final int totalMemberCount = memberCount;
//        final CountDownLatch latch = new CountDownLatch(totalMemberCount);
//        int averageHour = purchasePlan.getAverageTime();
//        int peakHour = purchasePlan.getPeakTime();



        Random random = new Random();
        Long[] d = new Long[memberCount];
        Calendar calendar = Calendar.getInstance();
        calendar.set(2015, 11 - 1, 23, peakHour, 30, 00);
        long now = System.currentTimeMillis();
        long future = calendar.getTimeInMillis();
        long futureFromNow = future - now;
        System.out.println(format.format(new Date(future)));

        //
        for (int x = 0; x < memberCount; x++) {
            d[x] = (long) (random.nextGaussian() * 60 * 60 * 1000) * averageHour / 2  + futureFromNow;
        }
        Arrays.sort(d);

        for (int x = 0; x < memberCount; x++) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("买了一个！! " + format.format(new Date()));
                    latch.countDown();
                }
            }, d[x] >= 0 ? d[x] : 0);
        }
        shutdownTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    System.out.println("latch await!" + format.format(new Date()));
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("latch end!" + format.format(new Date()));
                timer.cancel();
                shutdownTimer.cancel();
            }
        }, 3000);
    }

    @Override
    public void run() {
        System.out.println("running..........................................");

        execute();

        System.out.println("one cycle finish");
    }
}
