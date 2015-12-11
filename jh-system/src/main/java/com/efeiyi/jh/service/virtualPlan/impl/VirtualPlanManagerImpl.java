package com.efeiyi.jh.service.virtualPlan.impl;

import com.efeiyi.ec.purchase.model.PurchaseOrderProduct;
import com.efeiyi.jh.dao.virtualPlan.VirtualPlanDao;
import com.efeiyi.jh.plan.model.VirtualOrderPlan;
import com.efeiyi.jh.plan.model.VirtualUser;
import com.efeiyi.jh.plan.model.VirtualUserPlan;
import com.efeiyi.jh.service.virtualPlan.VirtualPlanManagerService;
import com.ming800.core.taglib.PageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
 * Created by Administrator on 2015/12/9.
 * 虚拟计划 Service 实现类
 */
public class VirtualPlanManagerImpl implements VirtualPlanManagerService {

    @Autowired
    @Qualifier("virtualPlanDao")
    private VirtualPlanDao virtualPlanDao;

    @Override
    public Integer getOrderRelation(VirtualOrderPlan virtualOrderPlan) throws Exception {
        return virtualPlanDao.getOrderRelation(virtualOrderPlan);
    }

    @Override
    public List<PurchaseOrderProduct> getOrderProductList(VirtualOrderPlan virtualOrderPlan, PageEntity pageEntity) throws Exception {
        return virtualPlanDao.getOrderProductList(virtualOrderPlan, pageEntity);
    }

    @Override
    public List<VirtualUser> getVirtualUserList(VirtualUserPlan virtualUserPlan, PageEntity pageEntity) throws Exception {
        return virtualPlanDao.getVirtualUserList(virtualUserPlan, pageEntity);
    }
}