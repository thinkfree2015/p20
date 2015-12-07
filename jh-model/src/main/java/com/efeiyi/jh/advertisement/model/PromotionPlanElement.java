package com.efeiyi.jh.advertisement.model;

/**
 * Created by Administrator on 2015/12/7.
 * 用于页面显示 返利计划、注册量、订单数量及支付总额
 */
public class PromotionPlanElement {
    private PromotionPlan promotionPlan;//返利计划
    private String ZCL;//注册量
    private String DDL;//订单数量
    private String ZFE;//支付总额

    public PromotionPlan getPromotionPlan() {
        return promotionPlan;
    }

    public void setPromotionPlan(PromotionPlan promotionPlan) {
        this.promotionPlan = promotionPlan;
    }

    public String getDDL() {
        return DDL;
    }

    public void setDDL(String DDL) {
        this.DDL = DDL;
    }

    public String getZCL() {
        return ZCL;
    }

    public void setZCL(String ZCL) {
        this.ZCL = ZCL;
    }

    public String getZFE() {
        return ZFE;
    }

    public void setZFE(String ZFE) {
        this.ZFE = ZFE;
    }
}
