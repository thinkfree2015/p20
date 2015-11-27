package com.efeiyi.jh.model.task;


import com.efeiyi.ec.product.model.ProductModel;
import com.efeiyi.jh.model.PlanConst;
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
public class PurchaseOrderTaskScheduler extends BaseTimerTask {

    private VirtualOrderPlan virtualOrderPlan;
    private List<ProductModel> productModelList;

    @Override
    public boolean cancel() {
        System.out.println("PurchaseOrderTaskScheduler cancelling............................................");
        SuperTimer.getInstance().getSubTaskTempStoreMap().put(virtualOrderPlan,productModelList);
        virtualOrderPlan.setStatus(PlanConst.planStatusNormal);
        sessionHolder.getSession().saveOrUpdate(virtualOrderPlan);
        sessionHolder.getSession().flush();
        return super.cancel();
    }

    @Override
    public void run() {
        System.out.println("定时生成订单安排中..........................................");
        virtualOrderPlan.setStatus(PlanConst.planStatusStarted);
        sessionHolder.getSession().saveOrUpdate(virtualOrderPlan);
        sessionHolder.getSession().flush();

        //生成ProductModel随机productModel
        if(productModelList == null) {
            productModelList = generateProductModelList();
        }
        Random random = new Random();

        //生成随机时间点
        Long[] randomOrderTimePoint = new Long[productModelList.size()];
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
            if (randomOrderTimePoint[x] < 0) {
                continue;
            }
            SuperTimer.getInstance().getSubTimerTaskMap()
                    .get(virtualOrderPlan)
                    .getTimer()
                    .schedule(new VirtualPurchaseOrderGenerator(productModelList, virtualOrderPlan), randomOrderTimePoint[x]);
        }

        System.out.println("定时生成订单安排结束.........................");
    }

    @Override
    public void setVirtualPlan(VirtualPlan virtualPlan) {
        this.virtualOrderPlan = (VirtualOrderPlan) sessionHolder.getSession().get(VirtualOrderPlan.class, virtualPlan.getId());
    }

    private List<ProductModel> generateProductModelList() {

        List<ProductModel> virtualProductModelList = new ArrayList<>();

        for (VirtualProductModel virtualProductModel : virtualOrderPlan.getVirtualProductModelList()) {
//            int randomAmount = random.nextInt(virtualProductModel.getAmountCeil() - virtualProductModel.getAmountFloor() + 1) + virtualProductModel.getAmountFloor();
//            virtualProductModel.setRandomAmount(randomAmount);
            //生成ProductModel池子
            List<ProductModel> subVirtualProductModelList = generateSubProductModelPool(virtualProductModel,virtualProductModel.getRandomAmount());
            virtualProductModelList.addAll(subVirtualProductModelList);
            sessionHolder.getSession().saveOrUpdate(virtualProductModel);
        }
        sessionHolder.getSession().flush();

        return virtualProductModelList;
    }

    private List<ProductModel> generateSubProductModelPool(VirtualProductModel virtualProductModel, int randomAmount) {

        List<ProductModel> subVirtualProductModelList = new LinkedList<>();
        for(int x = 0; x < randomAmount; x++){
            subVirtualProductModelList.add(virtualProductModel.getProductModel());
        }
        return subVirtualProductModelList;
    }

    public List<ProductModel> getProductModelList() {
        return productModelList;
    }

    public void setProductModelList(List<ProductModel> productModelList) {
        this.productModelList = productModelList;
    }
}


