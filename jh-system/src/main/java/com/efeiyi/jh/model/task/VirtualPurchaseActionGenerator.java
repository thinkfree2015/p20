package com.efeiyi.jh.model.task;

import com.efeiyi.ec.purchase.model.PurchaseOrder;
import com.efeiyi.jh.model.entity.VirtualOrderPlan;
import com.efeiyi.jh.model.entity.VirtualPlan;

import java.util.Date;

/**
 * Created by Administrator on 2015/11/26.
 */
public class VirtualPurchaseActionGenerator extends BaseTimerTask {

    private VirtualOrderPlan virtualOrderPlan;

    @Override
    public void run() {
        System.out.println(new Date() + ":买了一个！！！！！！！！！！！");
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setCreateDatetime(new Date());
        purchaseOrder.setMessage("虚拟专用");
        purchaseOrder.setOrderStatus(PurchaseOrder.ORDER_STATUS_FINISHED);
        purchaseOrder.setOrderType("虚拟专用");
        sessionHolder.getSession().saveOrUpdate(PurchaseOrder.class.getName(), purchaseOrder);
        sessionHolder.getSession().flush();
    }

    @Override
    public void setVirtualPlan(VirtualPlan virtualPlan) {
        this.virtualOrderPlan = (VirtualOrderPlan)virtualPlan;
    }
}
