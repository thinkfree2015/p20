package com.efeiyi.jh.service.promotionPlan;

import com.efeiyi.ec.organization.model.User;
import com.efeiyi.ec.purchase.model.PurchaseOrder;
import com.efeiyi.jh.advertisement.model.PromotionPlan;
import com.ming800.core.taglib.PageEntity;

import java.util.List;

/**
 * Created by Administrator on 2015/12/7.
 * 返利链接 Service 获取注册量   订单数量    支付总额
 */
public interface PromotionPlanManagerService {

    /**
     * 获取订单数量
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

    /**
     * 获取通过返利链接注册的用户
     * @param promotionPlan 返利链接
     * @param pageEntity 分页信息
     * @return 注册用户列表
     * @throws Exception
     */
    List<User> getZCLInfomation(PromotionPlan promotionPlan, PageEntity pageEntity)throws Exception;

    /**
     * 获取通过返利链接购物的订单
     * @param promotionPlan 返利链接
     * @param pageEntity 分页信息
     * @return 订单详情列表
     * @throws Exception
     */
    List<PurchaseOrder> getDDLInfomation(PromotionPlan promotionPlan, PageEntity pageEntity)throws Exception;
}
