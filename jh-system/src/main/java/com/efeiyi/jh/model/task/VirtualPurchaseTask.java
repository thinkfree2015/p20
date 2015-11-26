package com.efeiyi.jh.model.task;

import com.efeiyi.ec.purchase.model.PurchaseOrder;
import com.efeiyi.jh.model.entity.VirtualOrderPlan;
import com.efeiyi.jh.model.entity.VirtualPlan;

import java.util.Date;

/**
 * Created by Administrator on 2015/11/26.
 */
public class VirtualPurchaseTask extends MyTimerTask {

    private VirtualOrderPlan virtualOrderPlan;

    @Override
    public void setVirtualPlan(VirtualPlan virtualPlan) {
        this.virtualOrderPlan = (VirtualOrderPlan) virtualPlan;
    }

    @Override
    public void run() {
        System.out.println(new Date() + ":买了一个！！！！！！！！！！！");
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setCreateDatetime(new Date());
        sessionHolder.getSession().saveOrUpdate(PurchaseOrder.class.getName(), purchaseOrder);
    }

}
