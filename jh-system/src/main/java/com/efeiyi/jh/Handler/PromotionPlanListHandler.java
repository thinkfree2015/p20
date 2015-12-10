package com.efeiyi.jh.Handler;

import com.efeiyi.jh.advertisement.model.PromotionPlan;
import com.efeiyi.jh.advertisement.model.PromotionPlanElement;
import com.efeiyi.jh.service.promotionPlan.PromotionPlanManagerService;
import com.ming800.core.does.model.PageInfo;
import com.ming800.core.does.service.DoHandler;
import com.ming800.core.util.ApplicationContextUtil;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/7/23.
 * 获取注册量、订单数量及订单总金额 Handler
 */
public class PromotionPlanListHandler implements DoHandler {

    private PromotionPlanManagerService promotionPlanManagerService = (PromotionPlanManagerService) ApplicationContextUtil.getApplicationContext().getBean("promotionPlanManagerImpl");

    @Override
    public ModelMap handle(ModelMap modelMap, HttpServletRequest request) throws Exception {
        modelMap.put("PPEList", getPromotionPlanElementList(modelMap));
        return modelMap;
    }

    private List<PromotionPlanElement> getPromotionPlanElementList(ModelMap modelMap) throws Exception {
        List<PromotionPlanElement> ppeList = new ArrayList<>();
        List<PromotionPlan> promotionPlanList = ((PageInfo)modelMap.get("pageInfo")).getList();

        if (!promotionPlanList.isEmpty()){
            for (PromotionPlan pp:promotionPlanList){
                PromotionPlanElement promotionPlanElement = new PromotionPlanElement();
                promotionPlanElement.setPromotionPlan(pp);
                if (!pp.getPromotionUserRecordList().isEmpty()){//设置注册量
                    promotionPlanElement.setZCL(String.valueOf(pp.getPromotionUserRecordList().size()));
                }

                if (!pp.getPromotionPurchaseRecordList().isEmpty()){//设置订单数、支付总额
                    promotionPlanElement = setDDLAndZFE(promotionPlanElement, pp);
                }
                ppeList.add(promotionPlanElement);
            }
        }

        return ppeList;
    }

    private PromotionPlanElement setDDLAndZFE(PromotionPlanElement promotionPlanElement, PromotionPlan promotionPlan) throws Exception {
        promotionPlanElement.setDDL(promotionPlanManagerService.getDDL(promotionPlan));//设置订单数
        promotionPlanElement.setZFE(promotionPlanManagerService.getZFE(promotionPlan));//设置支付总额
        return promotionPlanElement;
    }

}
