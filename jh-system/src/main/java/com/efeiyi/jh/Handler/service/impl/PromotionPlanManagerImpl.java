package com.efeiyi.jh.Handler.service.impl;

import com.efeiyi.jh.Handler.dao.PromotionPlanDao;
import com.efeiyi.jh.Handler.service.PromotionPlanManagerService;
import com.efeiyi.jh.advertisement.model.PromotionPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2015/12/7.
 * PromotionPlanManagerService 实现类
 */

@Service
public class PromotionPlanManagerImpl implements PromotionPlanManagerService {

    @Autowired
    @Qualifier("promotionPlanDao")
    private PromotionPlanDao promotionPlanDao;

    @Override
    public String getDDL(PromotionPlan promotionPlan) throws Exception {
        return promotionPlanDao.getDDL(promotionPlan);
    }

    @Override
    public String getZFE(PromotionPlan promotionPlan) throws Exception {
        return promotionPlanDao.getZFE(promotionPlan);
    }

}
