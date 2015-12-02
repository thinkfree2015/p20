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
        logger.info("PurchaseOrderTaskScheduler cancelling............................................");
        SuperTimer.getInstance().getSubTaskTempStoreMap().put(virtualOrderPlan, productModelList);
        if(session == null || !session.isOpen()){
            session = sessionFactory.openSession();
            virtualOrderPlan = (VirtualOrderPlan)session.get(VirtualOrderPlan.class,virtualOrderPlan.getId());
        }

        virtualOrderPlan.setStatus(PlanConst.planStatusNormal);
        session.saveOrUpdate(virtualOrderPlan);
        session.flush();
        return super.cancel();
    }

    public void execute(List<VirtualPlan> virtualPlanList) {

        virtualOrderPlan.setStatus(PlanConst.planStatusStarted);
        session.saveOrUpdate(virtualOrderPlan);
        session.flush();

        //生成ProductModel随机productModel
        productModelList = (List<ProductModel>) SuperTimer.getInstance().getSubTaskTempStoreMap().remove(virtualOrderPlan);
        if (productModelList == null || productModelList.isEmpty()) {
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
            SuperTimer.getInstance().getSubTimerMap()
                    .get(virtualOrderPlan)
                    .getTimer()
                    .schedule(new VirtualPurchaseOrderGenerator(productModelList, virtualOrderPlan), randomOrderTimePoint[x]);
        }


    }

    @Override
    public void run() {
        logger.info(" Purchase order arranging..........................................");
        try {
            execute(null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        logger.info("Purchase arranged.........................");
    }

    @Override
    public void setVirtualPlan(VirtualPlan virtualPlan) {
        if (session == null) {
            session = sessionFactory.openSession();
        }
        this.virtualOrderPlan = (VirtualOrderPlan) session.get(VirtualOrderPlan.class, virtualPlan.getId());
    }

    private List<ProductModel> generateProductModelList() {

        List<ProductModel> virtualProductModelList = new ArrayList<>();

        for (VirtualProductModel virtualProductModel : virtualOrderPlan.getVirtualProductModelList()) {
            //生成ProductModel池子
            List<ProductModel> subVirtualProductModelList = generateSubProductModelPool(virtualProductModel, virtualProductModel.getRandomAmount());
            virtualProductModelList.addAll(subVirtualProductModelList);
        }

        return virtualProductModelList;
    }

    private List<ProductModel> generateSubProductModelPool(VirtualProductModel virtualProductModel, int randomAmount) {

        List<ProductModel> subVirtualProductModelList = new LinkedList<>();
        for (int x = 0; x < randomAmount; x++) {
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


