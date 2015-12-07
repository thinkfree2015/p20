package com.efeiyi.jh.Handler.service;

import com.efeiyi.jh.advertisement.model.PromotionPlan;

/**
 * Created by Administrator on 2015/12/7.
 * 返利链接 Service 获取注册量   订单数量    支付总额
 */
public interface PromotionPlanManagerService {

    /**
     * 过去订单数量
     * @param promotionPlan
     * @return 订单数量
     * @throws Exception
     */
    String getDDL(PromotionPlan promotionPlan)throws Exception;

    /**
     * 获取支付总额
     * @param promotionPlan 返利链接
     * @return 支付总额
     * @throws Exception
     */
    String getZFE(PromotionPlan promotionPlan)throws Exception;

}
