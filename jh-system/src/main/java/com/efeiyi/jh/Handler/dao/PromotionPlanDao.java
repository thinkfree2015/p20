package com.efeiyi.jh.Handler.dao;

import com.efeiyi.jh.advertisement.model.PromotionPlan;

/**
 * Created by Administrator on 2015/12/7.
 * 返利链接 Dao
 */
public interface PromotionPlanDao {

    /**
     * 获取订单数量
     * @param promotionPlan 返利链接
     * @return 订单数量
     * @throws Exception
     */
    String getDDL(PromotionPlan promotionPlan)throws Exception;

    /**
     * 获取支付总额
     * @param promotionPlan 返利链接
     * @return 总额
     * @throws Exception
     */
    String getZFE(PromotionPlan promotionPlan)throws Exception;

}
