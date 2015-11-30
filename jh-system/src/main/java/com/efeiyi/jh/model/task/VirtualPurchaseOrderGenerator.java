package com.efeiyi.jh.model.task;

import com.efeiyi.ec.product.model.ProductModel;
import com.efeiyi.ec.purchase.model.PurchaseOrder;
import com.efeiyi.ec.purchase.model.PurchaseOrderProduct;
import com.efeiyi.jh.model.entity.*;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2015/11/26.
 */
public class VirtualPurchaseOrderGenerator extends BaseTimerTask {

    private VirtualOrderPlan virtualOrderPlan;
    private List<ProductModel> productModelList;


    public VirtualPurchaseOrderGenerator(List<ProductModel> productModelList,VirtualOrderPlan virtualOrderPlan){
        super();
        this.productModelList = productModelList;
        this.virtualOrderPlan = virtualOrderPlan;
    }

    @Override
    public void run() {
        Random random = new Random();
        ProductModel productModel = productModelList.remove(random.nextInt(productModelList.size()));
        List<VirtualUser> virtualUserList = virtualOrderPlan.getVirtualUserPlan().getVirtualUserList();
        VirtualUser virtualUser = virtualUserList.remove(random.nextInt(virtualUserList.size()));

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setCreateDatetime(new Date());
        purchaseOrder.setMessage("虚拟专用");
        purchaseOrder.setOrderType("虚拟专用");
        purchaseOrder.setOrderStatus(PurchaseOrder.ORDER_STATUS_FINISHED);
        purchaseOrder.setUser(virtualUser.getUser());

        PurchaseOrderProduct purchaseOrderProduct = new PurchaseOrderProduct();
        purchaseOrderProduct.setPurchaseOrder(purchaseOrder);
        purchaseOrderProduct.setProductModel(productModel);
        purchaseOrderProduct.setPurchaseAmount(1);
        purchaseOrderProduct.setPurchasePrice(productModel.getPrice());

        VirtualPurchaseOrder virtualPurchaseOrder = new VirtualPurchaseOrder();
        virtualPurchaseOrder.setPurchaseOrder(purchaseOrder);
        virtualPurchaseOrder.setVirtualOrderPlan(virtualOrderPlan);

        System.out.println(new Date() + ":" + virtualUser.getUser().getName() + "买了一个" + purchaseOrderProduct.getProductModel().getName());
        sessionHolder.getSession().saveOrUpdate(purchaseOrder);
        sessionHolder.getSession().saveOrUpdate(purchaseOrderProduct);
        sessionHolder.getSession().saveOrUpdate(virtualPurchaseOrder);
        sessionHolder.getSession().flush();
    }

    @Override
    public void setVirtualPlan(VirtualPlan virtualPlan) {
        this.virtualOrderPlan = (VirtualOrderPlan)virtualPlan;
    }
}
